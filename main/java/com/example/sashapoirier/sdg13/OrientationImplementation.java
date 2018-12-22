package com.example.sashapoirier.sdg13;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.StrictMath.abs;

public class OrientationImplementation implements SensorEventListener
{
    SensorManager SensorManager;
    private final float[] accelerometerReading = new float[3];
    private final float[] magnetometerReading = new float[3];

    private final float[] mRotationMatrix = new float[9];
    private final float[] mOrientationAngles = new float[3];

    Sensor accelerometer;

    Context context;

    public OrientationImplementation(Context context)
    {
        super();
        context = this.context;
        SensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = SensorManager.getDefaultSensor( Sensor.TYPE_ACCELEROMETER );
    }

    public float[] GetOrientation ()
    {
        float[] axis_angles = new float[3];
        SensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

// Rotation matrix based on current readings from accelerometer and magnetometer.
        final float[] rotationMatrix = new float[9];
        SensorManager.getRotationMatrix(rotationMatrix, null,
                accelerometerReading, magnetometerReading);

// Express the updated rotation matrix as three orientation angles.
        final float[] orientationAngles = new float[3];
        SensorManager.getOrientation(rotationMatrix, orientationAngles);

        SensorManager.unregisterListener(this, accelerometer);
        return orientationAngles;
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        Log.d("error2", "X :"+event.values[0]);
        Log.d("error2", "Y :"+event.values[1]);
        Log.d("error2", "Z :"+event.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
