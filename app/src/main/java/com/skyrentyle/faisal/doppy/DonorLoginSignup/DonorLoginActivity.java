package com.skyrentyle.faisal.doppy.DonorLoginSignup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.skyrentyle.faisal.doppy.DonorActivities.DonorProfilePageActivity;
import com.skyrentyle.faisal.doppy.R;

public class DonorLoginActivity extends AppCompatActivity {


    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_login);
        final EditText email=findViewById(R.id.donorLoginEmail);
        final EditText password=findViewById(R.id.donorLoginPassword);
        final Button donorLoginButton=findViewById(R.id.donorLoginButton);
        progressDialog=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();


        donorLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strEmail=email.getText().toString();
                String strPassword=password.getText().toString();
                if(!isEmailValid(strEmail)){
                    Toast.makeText(DonorLoginActivity.this, "Invalid email Format", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(strPassword.length()==0){
                    Toast.makeText(DonorLoginActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressDialog.setMessage("Logging in Please Wait...");
                progressDialog.show();
                mAuth.signInWithEmailAndPassword(strEmail,strPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Toast.makeText(DonorLoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(), DonorProfilePageActivity.class));
                        }
                    }

                });
                progressDialog.dismiss();
            }

        });
    }
}
