package com.skyrentyle.faisal.doppy.PostActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.skyrentyle.faisal.doppy.R;

public class PostsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new DonationPostFragment()).commit();
    }
}
