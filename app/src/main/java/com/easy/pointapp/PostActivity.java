package com.easy.pointapp;

import com.easy.pointapp.model.api.v1.Post;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by nixan on 05.04.16.
 */
public class PostActivity extends AppCompatActivity {

    public static final String EXTRA_POST = "post";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_activity);
        getSupportFragmentManager().beginTransaction().add(R.id.container,
                PostFragment.newInstance((Post) getIntent().getSerializableExtra(EXTRA_POST)))
                .commit();
    }
}
