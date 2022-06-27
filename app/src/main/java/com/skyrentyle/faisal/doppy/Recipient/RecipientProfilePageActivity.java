package com.skyrentyle.faisal.doppy.Recipient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.skyrentyle.faisal.doppy.PostActivity.PostsActivity;
import com.skyrentyle.faisal.doppy.R;

public class RecipientProfilePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec_profile_page);
        final TextView donorName=findViewById(R.id.recProfilePageDonorName);
        final Button editInfo=findViewById(R.id.recProfilePageEditButton);
        //final Button myPost=findViewById(R.id.recProfilePageMyPostButton);
        final Button newPost=findViewById(R.id.recProfilePageNewPostButton);

        editInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(RecipientProfilePageActivity.this, "Editing Info", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(RecipientProfilePageActivity.this, RecipientInfoEditActivity.class);
                startActivity(intent);
            }
        });

//        myPost.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Toast.makeText(RecipientProfilePageActivity.this, "Going To MY Post", Toast.LENGTH_SHORT).show();
//            }
//        });

        newPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(RecipientProfilePageActivity.this, "Going To New Post", Toast.LENGTH_SHORT).show();
//                Toast.makeText(RecipientProfilePageActivity.this, "Going to Donor List Item View Page ", Toast.LENGTH_SHORT).show();
//                Log.i("INFO","Going To Donor List Item View Page.");
//                Intent toDonorListItemViewPage=new Intent(RecipientProfilePageActivity.this, RecipientRegisterActivity.class);
//                startActivity(toDonorListItemViewPage);

                startActivity(new Intent(getApplicationContext(), PostsActivity.class));
            }
        });
    }
}
