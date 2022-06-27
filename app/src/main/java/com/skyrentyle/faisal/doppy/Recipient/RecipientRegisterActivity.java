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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecipientRegisterActivity extends AppCompatActivity implements View.OnClickListener {


    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    private EditText dfirstName, dlastName, demail, dpassword, dconfirmPassword, dcontact;
    private Button gotoRecipLogin, recipientsignupbtn;
    DatBaseHelper datBaseHelper;
    RecipientDetails recpdetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec_register);
        recpdetails = new RecipientDetails();
        datBaseHelper = new DatBaseHelper(this);

        dfirstName = (EditText) findViewById(R.id.RecSignUPFirstName);
        dlastName = (EditText) findViewById(R.id.RecSignUpLastName);
        demail = (EditText) findViewById(R.id.RecSignUpEmail);
        dpassword = (EditText) findViewById(R.id.RecSignUpPassword);
        dconfirmPassword = (EditText) findViewById(R.id.RecSignUpConfirmPassword);
        recipientsignupbtn = (Button) findViewById(R.id.recipientsignupbtn);
        gotoRecipLogin = (Button) findViewById(R.id.recSignUpToLoginPageButton);
        dcontact = (EditText) findViewById(R.id.RecSignUpContact);

        gotoRecipLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipientRegisterActivity.this, RecipientLoginActivity.class);
                startActivity(intent);
            }
        });


        recipientsignupbtn.setOnClickListener((View.OnClickListener) this);
        //gotoRecipientLoginPageButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        String strFirstName = dfirstName.getText().toString();
        String strLastName = dlastName.getText().toString();
        String strEmail = demail.getText().toString();
        String strPassword = dpassword.getText().toString();
        String strConfirmPassword = dconfirmPassword.getText().toString();
        String strcontact = dcontact.getText().toString();
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

            if (strPassword.length() < 8 && !isValidPassword(strPassword)) {
                Toast.makeText(getApplicationContext(), "Password should be longer than 8 chars and must contain alphanumeric chars", Toast.LENGTH_LONG).show();
            }


            else if (!isEmailValid(strEmail)) {
                Toast.makeText(getApplicationContext(), "INVALID EMAIL FORMAT", Toast.LENGTH_LONG).show();
            } else if (strFirstName.length() > 0 || strLastName.length() > 0) {
                if (strFirstName.equals(strLastName)) {
                    Toast.makeText(getApplicationContext(), "ENTER DIFFERENT FIRST AND LAST NAME", Toast.LENGTH_LONG).show();
                } else {


                    recpdetails.setEmail(strEmail);
                    recpdetails.setFirstname(strFirstName);
                    recpdetails.setLastname(strLastName);
                    recpdetails.setPassword(strPassword);
                    recpdetails.setContact(strcontact);
                    //recdetails.setPassword(strConfirmPassword);
                    long rowidw = (long) datBaseHelper.insertData(recpdetails);

                    if (rowidw > 0) {
                        Toast.makeText(getApplicationContext(), "Row " + rowidw + " is successfully inserted ", Toast.LENGTH_LONG);

                        Intent intent = new Intent(RecipientRegisterActivity.this, RecipientLoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Row Insertion Failed", Toast.LENGTH_LONG);


                    }


                }

            }
        }
    }


}
