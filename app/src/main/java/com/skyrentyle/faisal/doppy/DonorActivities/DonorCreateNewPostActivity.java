package com.skyrentyle.faisal.doppy.DonorActivities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.skyrentyle.faisal.doppy.PostClass.Post;
import com.skyrentyle.faisal.doppy.R;


public class DonorCreateNewPostActivity extends AppCompatActivity {

    static int PReqCode=1;
    static int REQUESTCODE=1;

    private EditText getTitle;
    private EditText getDescription;
    private ImageView pickedImage;
    private Button pickImageButton;
    private Button postButton;
    private String con="Meta";

    Uri pickedImageUri;

    FirebaseAuth mAuth;
    FirebaseUser user;



    private void checkAndRequestForPermission(){
        if(ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(DonorCreateNewPostActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)){
                Toast.makeText(getApplicationContext(), "Please accept for required permission", Toast.LENGTH_SHORT).show();
            }
            else{
                ActivityCompat.requestPermissions(DonorCreateNewPostActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PReqCode);
            }
        }else{
            openGalary();
        }
    }

    private void openGalary(){
        Intent galaryIntent=new Intent(Intent.ACTION_GET_CONTENT);
        galaryIntent.setType("image/*");
        startActivityForResult(galaryIntent,REQUESTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==REQUESTCODE && data!=null){
            pickedImageUri=data.getData();
            pickedImage.setImageURI(pickedImageUri);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_create_new_post);
        getTitle=findViewById(R.id.newPostTitle);
        getDescription=findViewById(R.id.newPostDescription);
        pickedImage=findViewById(R.id.pickNewPostImage);
        pickImageButton=findViewById(R.id.newPostPickImage);
        postButton=findViewById(R.id.newPostPost);
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();

        pickImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=22){
                    checkAndRequestForPermission();
                }else{
                    openGalary();
                }
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String strTitle=getTitle.getText().toString();
                final String strDescription=getDescription.getText().toString();

                if(!strTitle.isEmpty()&&!strDescription.isEmpty()&&pickedImageUri!=null){

                    try{
                        StorageReference storageReference= FirebaseStorage.getInstance().getReference().child("post_images");
                        final StorageReference imageFilePath=storageReference.child(pickedImageUri.getLastPathSegment());
                        imageFilePath.putFile(pickedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String imadeDownloadLink=uri.toString();
                                        addPost(strTitle,strDescription,imadeDownloadLink);
                                        startActivity(new Intent(getApplicationContext(),DonorProfilePageActivity.class));
                                    }
                                });
                            }
                        });

                    }catch (Exception e){
                        Log.i("INFO",e.getStackTrace().toString());
                    }

                }
                else{
                    Toast.makeText(getApplicationContext(), "Fill up all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addPost(final String title, final String description, final String imageDownloadLink){
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        final DatabaseReference myRef=database.getReference("posts").push();

        FirebaseDatabase db= FirebaseDatabase.getInstance();
        DatabaseReference mr=db.getReference().child("donors").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        mr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                con=dataSnapshot.child("contact").getValue().toString();
                Post post=new Post(title,description,user.getUid(),imageDownloadLink,con);
                post.setPostID(myRef.getKey());
                myRef.setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Post Added Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Database Error", Toast.LENGTH_SHORT).show();
            }
        });



    }

    public void getContact(final Post post){

    }
}
