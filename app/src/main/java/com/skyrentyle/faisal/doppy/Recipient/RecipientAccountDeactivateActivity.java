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

public class RecipientAccountDeactivateActivity extends AppCompatActivity {
    DatBaseHelper dBH;
    private Button Deactt;
    private EditText em;
    private  EditText pass;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deacti);

        Deactt=(Button)findViewById(R.id.DeactivateFinal);
        em=(EditText)findViewById(R.id.DeleteEmail);
        pass=(EditText)findViewById(R.id.DeletePAssword);

        dBH= new DatBaseHelper(this);



        //loginbtn.setOnClickListener(this);


        Deactt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String E = em.getText().toString();
                String p = pass.getText().toString();


                Boolean R = dBH.findpassword(E, p);
                if (R == true) {



                    Toast.makeText(getApplicationContext(), "ACCOUNT DEACTIVATION SUCCESSFUL!!", Toast.LENGTH_LONG).show();
                    dBH.delete(E);
                    Intent intent = new Intent(RecipientAccountDeactivateActivity.this, RecipientProfilePageActivity.class);
                    startActivity(intent);
                } else {




                    Toast.makeText(getApplicationContext(), "email and password didnt match", Toast.LENGTH_LONG).show();
                }


            }
        });

    }



   /* @Override
    public void onClick(View v) {


        String email = emailrec.getText().toString();
        String password = passwordrec.getText().toString();
        if(v.getId()==R.id.recipientLoginButton)
        {

            Boolean result= dataBaseHelper.findpassword(email,password);
            if(result==true)
            {
                Intent intent= new Intent(RecipientLogin.this,ListViewitems.class);
                startActivity(intent);
            }
            else

            {
                Toast.makeText(getApplicationContext(),"email and password didnt match",Toast.LENGTH_LONG).show();
            }


        }*/



}
