package com.skyrentyle.faisal.doppy.Recipient;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.skyrentyle.faisal.doppy.DatBaseHelper;
import com.skyrentyle.faisal.doppy.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class RecipientInfoEditActivity extends AppCompatActivity {
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    EditText editFName,editLname,editEmail,editContact,edittPass,editconpass;
    Button save,Display;
    ImageView dp;
    Button pickImage;
    Button deact;
    private ProgressDialog progressbar;
    private int progressBarStatus=0;
    private Handler progressbarhandler=new Handler();
    private boolean ImageChange=false;
    private static final int SELECT_PHOTO=1;
    private static final int CAPTURE_PHOTO=2;
    Bitmap thumbnail;
    DatBaseHelper DBH;
    final int REQUEST_CODE_GALLERY=999;
    RecipientDetails recpDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_rec_info);
        DBH=new DatBaseHelper(this);
        editFName=findViewById(R.id.Editfirstname);
        editLname=findViewById(R.id.EditLastName);
        editEmail=findViewById(R.id.editEmail);
        editContact=findViewById(R.id.editContact);
        edittPass=findViewById(R.id.editPass);
        save=findViewById(R.id.savee);
        dp=findViewById(R.id.ProfileImage);
        deact=findViewById(R.id.Deactivate);
        editconpass=findViewById(R.id.editconPass);
        Display=findViewById(R.id.Display);
        recpDetails = new RecipientDetails();

        Display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RecipientInfoEditActivity.this, ShowDatabaseElementActivity.class);
                startActivity(intent);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String strFirstName = editFName.getText().toString();
                String strLastName = editLname.getText().toString();
                String strEmail = editEmail.getText().toString();
                String strPassword = edittPass.getText().toString();
                String strConfirmPassword = editconpass.getText().toString();
                String strcontact = editContact.getText().toString();
                if (!strConfirmPassword.equals(strPassword)) {


                    Toast.makeText(getApplicationContext(), "PASSWORDS DON'T MATCH", Toast.LENGTH_LONG).show();
                } else if (strFirstName.length() == 0) {
                    Toast.makeText(getApplicationContext(), "FIRST NAME CANNOT BE EMPTY", Toast.LENGTH_LONG).show();

                } else if (strLastName.length() == 0) {
                    Toast.makeText(getApplicationContext(), "LAST NAME CANNOT BE EMPTY", Toast.LENGTH_LONG).show();

                } else if (strEmail.length() == 0) {
                    Toast.makeText(getApplicationContext(), "EMAIL CANNOT BE EMPTY", Toast.LENGTH_LONG).show();

                } else if (strPassword.length() == 0) {
                    Toast.makeText(getApplicationContext(), "PASSWORD CANNOT BE EMPTY", Toast.LENGTH_LONG).show();

                } else {


                    if (!isEmailValid(strEmail)) {
                        Toast.makeText(getApplicationContext(), "INVALID EMAIL FORMAT", Toast.LENGTH_LONG).show();
                    } else if (strFirstName.length() > 0 || strLastName.length() > 0) {
                        if (strFirstName.equals(strLastName)) {
                            Toast.makeText(getApplicationContext(), "ENTER DIFFERENT FIRST AND LAST NAME", Toast.LENGTH_LONG).show();
                        } else {


                            recpDetails.setEmail(strEmail);
                            recpDetails.setFirstname(strFirstName);
                            recpDetails.setLastname(strLastName);
                            recpDetails.setPassword(strPassword);
                            recpDetails.setContact(strcontact);
                            //recdetails.setPassword(strConfirmPassword);
                            long rowidw = DBH.update(recpDetails,strEmail);


                            if (rowidw > 0) {
                                Toast.makeText(getApplicationContext(), "Row " + rowidw + " is successfully inserted ", Toast.LENGTH_LONG);
                                Intent intent=new Intent(RecipientInfoEditActivity.this, RecipientLoginActivity.class);
                                startActivity(intent);



                            } else {
                                Toast.makeText(getApplicationContext(), "Row Insertion Failed", Toast.LENGTH_LONG);


                            }


                        }

                    }
                }


            }


        })  ;
        deact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Enter Email and Password to continue ", Toast.LENGTH_LONG);
                Intent intent = new Intent(RecipientInfoEditActivity.this, RecipientAccountDeactivateActivity.class);
                startActivity(intent);




            }
        });
        dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ActivityCompat.requestPermissions(
                        RecipientInfoEditActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY



                );



            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(this, "Don't have permission to access file location", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==REQUEST_CODE_GALLERY&& resultCode==RESULT_OK)
        {
            Uri imageUri=data.getData();
            CropImage.activity(imageUri).setGuidelines(CropImageView.Guidelines.ON).
                    setAspectRatio(1,1)
                    .start(this);

        }
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result= CropImage.getActivityResult(data);
            if(resultCode==RESULT_OK)
            {
                Uri resultUri=result.getUri();
                dp.setImageURI(resultUri);
            }
            else
            if(resultCode==CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE)
            {
                Exception error= result.getError();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
