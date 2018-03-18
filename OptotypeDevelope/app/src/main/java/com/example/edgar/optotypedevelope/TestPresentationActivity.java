package com.example.edgar.optotypedevelope;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class TestPresentationActivity extends AppCompatActivity {

    ImageView testOrOptotypes;
    Bitmap image = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_test_presentation);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().hide();

        setContentView(R.layout.activity_test_presentation);

        testOrOptotypes = (ImageView) findViewById(R.id.optotypeTestInPresentation);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        // se considera una buena practica y debe hacerse
        if (extras != null){
            String receiver = extras.getString("imageTest");
            byte[] byteCode = Base64.decode(receiver.toString(), Base64.DEFAULT);
            image = BitmapFactory.decodeByteArray(byteCode, 0 , byteCode.length);
        }

        if (image != null)
            testOrOptotypes.setImageBitmap(image);
        else
            testOrOptotypes.setImageResource(R.drawable.imagenotfoud);


    }
}
