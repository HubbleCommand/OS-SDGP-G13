package com.example.sashapoirier.sdg13;

import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONObject;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import static java.lang.StrictMath.abs;

public class CameraActivity extends AppCompatActivity implements SensorEventListener
{
    Button btnTakePhoto;
    Spinner spinnerType, spinnerReason;
    private final int CAPTURE_PHOTO = 104;
    double Dlatitude, Dlongitude, Daltitude;

    Bitmap resizeImage;
    ImageView imageViewExample;

    TextView textSensor21;
    TextView textSensor22;
    TextView textSensor23;
    SensorManager SM;
    Sensor orientationSensor;
    ProgressBar progressBar21;
    ProgressBar progressBar22;
    ProgressBar progressBar23;
    Button button;

    int value1; // pour la convertion des valeurs pour nos progressBar
    int value2;
    int value3;
    double x_val = 0;
    double y_val = 0;
    double z_val = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        SM = (SensorManager) getSystemService(SENSOR_SERVICE); // on crée notre sensor manager
        orientationSensor = SM.getDefaultSensor( Sensor.TYPE_ORIENTATION ); // on désigne le type de sensor en créen le contenaire du sensor
        SM.registerListener(this, orientationSensor, SensorManager.SENSOR_DELAY_NORMAL); //on définit un délait d'enregistrement

        // pour chaque view on attribue l'identifiant qui fait le lien avec le manifeste
        /*
        textSensor21 = (TextView) findViewById(R.id.textView_sensor21); // on définit le contenaire du layout pour lequel on attribut notre text du sensor
        textSensor22 = (TextView) findViewById(R.id.textView_sensor22); // on définit le contenaire du layout pour lequel on attribut notre text du sensor
        textSensor23 = (TextView) findViewById(R.id.textView_sensor23); // on définit le contenaire du layout pour lequel on attribut notre text du sensor
        progressBar21 = (ProgressBar) findViewById(R.id.progressBar_sensor21); // on définit le contenaire du layout pour lequel on attribut notre valeur
        progressBar22 = (ProgressBar) findViewById(R.id.progressBar_sensor22); // on définit le contenaire du layout pour lequel on attribut notre valeur
        progressBar23 = (ProgressBar) findViewById(R.id.progressBar_sensor23); // on définit le contenaire du layout pour lequel on attribut notre valeur
        button = (Button) findViewById(R.id.button_returnSensor2);
        */
        initializeUI();
        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
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
        Log.d("error2", "1");
        GPSImplementation gps = new GPSImplementation(getApplicationContext());
        Location location = gps.getLocation();
        Dlatitude = location.getLatitude();
        Dlongitude = location.getLongitude();
        Daltitude = location.getAltitude();
        Log.d("error2", "Latitude: "+Double.toString(Dlatitude));
        Log.d("error2", "1");
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

            //gets all GPS data
            String altitude= Double.toString(Daltitude);
            String latitude= Double.toString(Dlatitude);
            String longitude = Double.toString(Dlongitude);

            //Creates JSON object, puts all metadata into it
            JSONObject metadata = new JSONObject();
            metadata.put("Orientation", ""+x_val+","+y_val+","+z_val);
            metadata.put("Latitude", latitude);
            metadata.put("Longitude", longitude);
            metadata.put("Altitude", altitude);
            metadata.put("Descriptors", selections);
            Log.d("error2", "Metadata JSON: "+metadata.toString());

            //Setting EXIF values
            exif.setAttribute(ExifInterface.TAG_IMAGE_DESCRIPTION, metadata.toString());
            exif.saveAttributes();
            //Checking EXIF tag
            String tags = exif.getAttribute(ExifInterface.TAG_IMAGE_DESCRIPTION);
            Log.d("error2", "Exif tags:"+tags);
            JSONObject tag_result = new JSONObject(tags);
            Log.d("error2", "Exif tags as JSON: "+ tag_result.toString());

            //prints success to user
            Toast.makeText(CameraActivity.this, "Photo saved ", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(CameraActivity.this, "Exception Throw ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void  onPause(){
        SM.unregisterListener(this, orientationSensor);//pour ne pas gaspiller des ressources on désactive l'enregistrement des valeurs
        super.onPause();
    }

    @Override
    protected void  onResume(){
        SM.registerListener(this, orientationSensor, SensorManager.SENSOR_DELAY_NORMAL); //on définit un délait d'enregistrement
        super.onResume();
    }

    @Override // pour le bon fonctionnement de l'activité il est necessaire pour l'appel de classe de crée la méthode, même si on l'utilise pas.
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        /*
        textSensor21.setText("X :"+event.values[0]) ; //ICI on concatène la valeur stocker dans le tab sensor avec le text du textView
        textSensor22.setText("Y :"+event.values[1]) ; //ICI on concatène la valeur stocker dans le tab sensor avec le text du textView
        textSensor23.setText("Z :"+event.values[2]) ; //ICI on concatène la valeur stocker dans le tab sensor avec le text du textView
        progressBar21.setProgress((int) abs(event.values[0]*10));// on donne la valeur à la progressbar  pour qu'elle l'évalue.
        progressBar22.setProgress((int) abs(event.values[1]*10));
        progressBar23.setProgress((int) abs(event.values[2]*10));*/

        x_val = event.values[0];
        y_val = event.values[1];
        z_val = event.values[2];
    }
    public void onClick(View view)
    {
        SM.unregisterListener(this, orientationSensor);//pour ne pas gaspiller des ressources on désactive l'enregistrement des valeurs
        //on ferme l'activité quand on clic
        finish();
    }
}
