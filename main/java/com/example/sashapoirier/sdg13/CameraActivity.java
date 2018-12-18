package com.example.sashapoirier.sdg13;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

public class CameraActivity extends AppCompatActivity {
    Button btnTakePhoto;
    Spinner spinnerType, spinnerReason;
    public static final int REQUEST_PERM_WRITE_STORAGE = 102;
    public static final int REQUEST_GPS_LOCATION = 1030;
    private final int CAPTURE_PHOTO = 104;

    Bitmap resizeImage;
    ImageView imageViewExample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        initializeUI();

        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Set permission To access gallery and Camera
                boolean preconditions = false;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                    {
                        ActivityCompat.requestPermissions(CameraActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                    }
                }
                if(ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(CameraActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERM_WRITE_STORAGE);
                }
                else
                {
                    takePhoto();
                }
            }
        });
    }

    public void initializeUI()
    {
        btnTakePhoto = (Button)findViewById(R.id.btn_take_photo);
        imageViewExample = (ImageView)findViewById(R.id.imgv_example);
        spinnerType = findViewById(R.id.spinnerType);
        spinnerReason = findViewById(R.id.spinnerReason);
        String[] itemsType = new String[] {"Tree", "Underbrush", "Other"};
        String[] itemsReason = new String[] {"Sick", "Dead", "Dying", "Nuisance", "Other"};
        ArrayAdapter<String> adapterType = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsType);
        ArrayAdapter<String> adapterReason = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsReason);
        spinnerType.setAdapter(adapterType);
        spinnerReason.setAdapter(adapterReason);
    }

    public void takePhoto()
    {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAPTURE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent returnIntent)
    {
        super.onActivityResult(requestCode, resultCode, returnIntent);

        if(resultCode == RESULT_OK)
        {
            switch (requestCode)
            {
                case CAPTURE_PHOTO:
                    Bitmap capturedBitmap = (Bitmap) returnIntent.getExtras().get("data");
                    imageViewExample.setImageBitmap(capturedBitmap);
                    saveImageToGallery(capturedBitmap);
                    break;
                default:
                    break;
            }
        }
    }

    private void saveImageToGallery (Bitmap finalBitmap)
    {
        //String root = Environment.getExternalStorageState();
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/OpenScience");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String imageName = "Image-" + n + ".jpg";
        File file = new File (myDir, imageName);
        if (file.exists()) file.delete();
        try
        {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            String resizeCoolerImagePath = file.getAbsolutePath();
            out.flush();
            out.close();

            String selections = spinnerType.getSelectedItem().toString() + "," + spinnerReason.getSelectedItem().toString();

            ExifInterface exif = new ExifInterface(file.toString());
            Log.d("error2", exif.toString());
            exif.setAttribute(ExifInterface.TAG_IMAGE_DESCRIPTION, selections);

            String altitude= "", latitude= "", longitude = "", orientation = "139";
            exif.setAttribute(ExifInterface.TAG_GPS_ALTITUDE, altitude);
            exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, latitude);
            exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, longitude);
            exif.setAttribute(ExifInterface.TAG_ORIENTATION, orientation);
            exif.saveAttributes();
            String orientationS = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
            Log.d("error2", orientationS);

            Toast.makeText(CameraActivity.this, "Photo saved ", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(CameraActivity.this, "Exception Throw ", Toast.LENGTH_SHORT).show();
        }
    }
}

