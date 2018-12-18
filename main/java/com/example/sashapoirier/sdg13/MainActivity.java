package com.example.sashapoirier.sdg13;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Button btnCamera, btnLegal;
    ImageView imVCamera, imVLegal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imVCamera = (ImageView) findViewById(R.id.imVCamera);
        imVLegal = (ImageView) findViewById(R.id.imVLegal);

        btnCamera = findViewById(R.id.btnCamera);
        btnLegal = findViewById(R.id.btnLegal);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(getApplicationContext(), CameraActivity.class);
                startActivity(cameraIntent);
            }
        });

        btnLegal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent legalIntent = new Intent(getApplicationContext(), LegalActivity.class);
                startActivity(legalIntent);
            }
        });
    }
}
