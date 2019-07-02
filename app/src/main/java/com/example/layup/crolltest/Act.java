package com.example.layup.crolltest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by layup on 2019-03-22.
 */

public class Act extends AppCompatActivity implements Act2.test{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public void avg(int a) {
        Log.i("TAG", "ㅇㄴㅁㅇㄴㅁ : " + a );
    }

    @Override
    public void two(int c, int d) {

    }
}
