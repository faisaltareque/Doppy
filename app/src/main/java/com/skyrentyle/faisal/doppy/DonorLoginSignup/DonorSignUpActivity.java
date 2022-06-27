package com.skyrentyle.faisal.doppy.DonorLoginSignup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.skyrentyle.faisal.doppy.DonorActivities.DonorProfilePageActivity;
import com.skyrentyle.faisal.doppy.DonorClass.Donor;
import com.skyrentyle.faisal.doppy.R;

public class DonorSignUpActivity extends AppCompatActivity {
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    boolean isNumberValid(String number){
        if(number.length()==11&&number.charAt(0)=='0'&&number.charAt(1)=='1'){
            if(number.charAt(2)=='5'||number.charAt(2)=='6'||number.charAt(2)=='7'||number.charAt(2)=='8'||number.charAt(2)=='9'){
                return true;
            }
        }
        return false;
    }

    private FirebaseAuth mAuth;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private EditText contact;
    private Button donorSignUpButton;
    private Button gotoDonorLoginPageButton;
    private ProgressDialog progressDialog;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=mAuth.getCurrentUser();
        if(user!=null){
            startActivity(new Intent(getApplicationContext(), DonorProfilePageActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_sign_up);

        firstName=findViewById(R.id.donorSignUPFirstName);
        lastName=findViewById(R.id.donorSignUpLastName);
        email=findViewById(R.id.donorSignUpEmail);
        password=findViewById(R.id.donorSignUpPassword);
        confirmPassword=findViewById(R.id.donorSignUpConfirmPassword);
        contact=findViewById(R.id.donorSignUpContact);
        donorSignUpButton=findViewById(R.id.donorSignUpButton);
        gotoDonorLoginPageButton=findViewById(R.id.donorSignUpToLoginPageButton);
        progressDialog=new ProgressDialog(this);

        mAuth=FirebaseAuth.getInstance();

        gotoDonorLoginPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("INFO","Going to Donor Login Page");
                Toast.makeText(DonorSignUpActivity.this, "Going to Donor Login Page", Toast.LENGTH_SHORT).show();
                Intent toDonorLogin=new Intent(DonorSignUpActivity.this, DonorLoginActivity.class);
                startActivity(toDonorLogin);
            }
        });

        donorSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strFirstName=firstName.getText().toString();
                String strLastName=lastName.getText().toString();
                String strEmail=email.getText().toString();
                String strPassword=password.getText().toString();
                String strConfirmPassword=confirmPassword.getText().toString();
                String strContact=contact.getText().toString();


                if (strFirstName.length() == 0 || strLastName.length() == 0) {
                    Toast.makeText(DonorSignUpActivity.this, "Enter First Name And Last Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (strFirstName.equals(strLastName)) {
                    Toast.makeText(DonorSignUpActivity.this, "Enter different First Name And Last Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isEmailValid(strEmail)) {
                    Toast.makeText(DonorSignUpActivity.this, "Invalid email Format", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isNumberValid(strContact)) {
                    Toast.makeText(DonorSignUpActivity.this, "Invalid Contact format", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(strPassword.length()<6){
                    Toast.makeText(DonorSignUpActivity.this, "Password length is less than 6", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (strPassword.equals(strConfirmPassword)) {
                    Toast.makeText(DonorSignUpActivity.this, "Going to create user", Toast.LENGTH_SHORT).show();
                    final Donor donor = new Donor(strFirstName, strLastName, strEmail, strPassword, strContact);
                    createUser(donor);
                } else {
                    Toast.makeText(DonorSignUpActivity.this, "Password Does not Match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void createUser(final Donor donor){
        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(donor.getEmail(),donor.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(DonorSignUpActivity.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                    updateUserInfo(donor);
                }
                else{
                    Toast.makeText(DonorSignUpActivity.this, "SignUp Failed", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }


    private void updateUserInfo(Donor donor){
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference().child("donors");
        FirebaseUser user=mAuth.getCurrentUser();
        donor.setKey(user.getUid().toString());
        try{
            myRef.child(user.getUid()).setValue(donor).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        //Toast.makeText(DonorSignUpActivity.this, "Info Updated", Toast.LENGTH_SHORT).show();
                    }else{
                        //Toast.makeText(DonorSignUpActivity.this, "Updated Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch (Exception e){
            Toast.makeText(DonorSignUpActivity.this, e.fillInStackTrace().toString(), Toast.LENGTH_SHORT).show();
            Log.i("INFO",e.fillInStackTrace().toString());
        }

    }

}

