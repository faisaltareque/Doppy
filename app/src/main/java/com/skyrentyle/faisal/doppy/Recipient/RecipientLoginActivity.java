package com.skyrentyle.faisal.doppy.Recipient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.skyrentyle.faisal.doppy.DatBaseHelper;
import com.skyrentyle.faisal.doppy.R;

public class RecipientLoginActivity extends AppCompatActivity {
    DatBaseHelper dataBaseHelper;
    private Button loginbtn;
    private EditText emailD;
    private  EditText passwordD;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec_login);

        loginbtn=(Button)findViewById(R.id.RecLoginButton);
        emailD=(EditText)findViewById(R.id.RecLoginEmail);
        passwordD=(EditText)findViewById(R.id.RecLoginPassword);

        dataBaseHelper= new DatBaseHelper(this);


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailD.getText().toString();
                String password = passwordD.getText().toString();


                Boolean result = dataBaseHelper.findpassword(email, password);
                if (result == true) {
                    Intent intent = new Intent(RecipientLoginActivity.this, RecipientProfilePageActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "email and password didnt match", Toast.LENGTH_LONG).show();
                }


            }
        });

    }

}
