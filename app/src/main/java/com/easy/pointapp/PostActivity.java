package com.easy.pointapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by nixan on 05.04.16.
 */
public class PostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_activity);
        getSupportFragmentManager().beginTransaction().add(R.id.container, new PostFragment())
                .commit();
    }
}
