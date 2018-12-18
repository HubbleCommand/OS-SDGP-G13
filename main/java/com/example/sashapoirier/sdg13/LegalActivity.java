package com.example.sashapoirier.sdg13;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class LegalActivity extends AppCompatActivity {

    ImageView imView1, imViewlast;
    TextView tvLaw1, tvLaw2, tvLaw3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legal);

        //imView1 = findViewById(R.id.imageView);
        //imView1.setImageDrawable(ic);

        imViewlast = findViewById(R.id.imageView7);

        tvLaw3 = findViewById(R.id.textView3);
        tvLaw3.setText("Law 3: W");
    }
}
