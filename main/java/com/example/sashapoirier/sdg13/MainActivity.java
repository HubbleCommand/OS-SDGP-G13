package com.example.sashapoirier.sdg13;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnCamera, btnLegal, btnGyro;
    ImageView imVCamera, imVLegal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imVCamera = (ImageView) findViewById(R.id.imVCamera);
        imVLegal = (ImageView) findViewById(R.id.imVLegal);

        btnLegal = findViewById(R.id.btnLegal);

        btnGyro = findViewById(R.id.btn_gyro);
        btnGyro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean preconditions = false;
                int PERMISSION_ALL = 1;
                String[] PERMISSIONS =
                        {
                                Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.ACCESS_FINE_LOCATION
                        };

                if(!hasPermissions(MainActivity.this, PERMISSIONS))
                {
                    ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS, PERMISSION_ALL);
                }
                else
                {
                    preconditions = true;
                }
                if (preconditions)
                {
                    Intent CameraGyroIntent = new Intent(getApplicationContext(), CameraActivity.class);
                    startActivity(CameraGyroIntent);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Needed Permissions have not been granted!", Toast.LENGTH_SHORT).show();
                }
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

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
