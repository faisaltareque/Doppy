package com.skyrentyle.faisal.doppy.PostActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.skyrentyle.faisal.doppy.R;

public class PostViewActivity extends AppCompatActivity {

    ImageView imagePost;
    TextView title,description,contact;
    Button contactButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);
        imagePost=findViewById(R.id.postViewImage);
        title=findViewById(R.id.postViewTitle);
        description=findViewById(R.id.postViewDescription);
        contact=findViewById(R.id.postViewContact);
        contactButton=findViewById(R.id.postViewContactButton);

        String postImage=getIntent().getExtras().getString("postImage");
        String postTitle=getIntent().getExtras().getString("title");
        String postDescription=getIntent().getExtras().getString("description");
        final String postContact=getIntent().getExtras().getString("contact");

        Glide.with(this).load(postImage).into(imagePost);
        title.setText(postTitle);
        description.setText(postDescription);

        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contact.setText(postContact);
            }
        });
    }
}
