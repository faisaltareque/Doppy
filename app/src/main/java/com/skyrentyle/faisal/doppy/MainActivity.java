package com.skyrentyle.faisal.doppy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.skyrentyle.faisal.doppy.DonorLoginSignup.DonorSignUpActivity;
import com.skyrentyle.faisal.doppy.Recipient.RecipientRegisterActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button gotoDonorSignUp=findViewById(R.id.mainPageToDonorSignUpButton);
        gotoDonorSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("INFO","Going to Donor SignUp Page");
                //Toast.makeText(MainActivity.this, "Going to Donor SignUp Page", Toast.LENGTH_SHORT).show();
                Intent toDonorSignUp=new Intent(MainActivity.this, DonorSignUpActivity.class);
                startActivity(toDonorSignUp);
            }
        });
        Button gotoRecipientSignUp=findViewById(R.id.mainPageToRecipientSignUpButton);
        gotoRecipientSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("INFO","Going to Recipient SignUp Page");
                //Toast.makeText(MainActivity.this, "Going to Recipient SignUp Page", Toast.LENGTH_SHORT).show();
                Intent toRecipientSignUp=new Intent(MainActivity.this, RecipientRegisterActivity.class);
                startActivity(toRecipientSignUp);
            }
        });
    }
}
