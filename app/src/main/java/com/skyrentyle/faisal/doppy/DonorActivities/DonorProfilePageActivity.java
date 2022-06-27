package com.skyrentyle.faisal.doppy.DonorActivities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skyrentyle.faisal.doppy.DonorLoginSignup.DonorSignUpActivity;
import com.skyrentyle.faisal.doppy.MainActivity;
import com.skyrentyle.faisal.doppy.R;

public class DonorProfilePageActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private TextView donorName;
    private TextView donorEmail;
    private TextView donorContact;
    private Button editInfo;
    private Button myPost;
    private Button newPost;
    private Button logOutUser;
    private Button home;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_profile_page);

        donorName=findViewById(R.id.donorProfilePageDonorName);
        donorEmail=findViewById(R.id.donorProfilePageDonorEmail);
        donorContact=findViewById(R.id.donorProfilePageDonorContact);
        editInfo=findViewById(R.id.donorProfilePageEditButton);
        myPost=findViewById(R.id.donorProfilePageMyPostButton);
        newPost=findViewById(R.id.donorProfilePageNewPostButton);
        home=findViewById(R.id.donorProfilePageHomeButton);
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("donors").child(user.getUid());
        logOutUser=findViewById(R.id.donorProfilePageLogoutButton);
        logOutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(getApplicationContext(), DonorSignUpActivity.class));
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String firstName=dataSnapshot.child("firstName").getValue().toString();
                String lastName=dataSnapshot.child("lastName").getValue().toString();
                String email=dataSnapshot.child("email").getValue().toString();
                String contact=dataSnapshot.child("contact").getValue().toString();
                String name=firstName+" "+lastName;
                donorName.setText(name);
                donorEmail.setText(email);
                donorContact.setText(contact);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Database Error", Toast.LENGTH_SHORT).show();
            }
        });


        editInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(DonorProfilePageActivity.this, "Editing Info", Toast.LENGTH_SHORT).show();
            }
        });

        myPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(DonorProfilePageActivity.this, "Going To MY Post", Toast.LENGTH_SHORT).show();
            }
        });

        newPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(DonorProfilePageActivity.this, "Going To New Post", Toast.LENGTH_SHORT).show();
                //Toast.makeText(DonorProfilePageActivity.this, "Going to Donor List Item View Page ", Toast.LENGTH_SHORT).show();
                Log.i("INFO","Going To Donor List Item View Page.");
                Intent toDonorListItemViewPage=new Intent(DonorProfilePageActivity.this, DonorCreateNewPostActivity.class);
                startActivity(toDonorListItemViewPage);
            }
        });
    }

}
