package com.example.sashapoirier.sdg13;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LegalActivity extends AppCompatActivity {

    ImageView imView1, imViewlast;
    TextView tvLaw1, tvLaw2, tvLaw3;
    Button btn_legal_1, btn_entretien, btn_protection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legal);
        btn_legal_1 = findViewById(R.id.btn_legal_1);
        btn_entretien = findViewById(R.id.btn_entretien);
        btn_protection = findViewById(R.id.btn_protection);

        btn_legal_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent legalIntent = new Intent(getApplicationContext(), Legal1.class);
                startActivity(legalIntent);
            }
        });
        btn_entretien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent entretienIntent = new Intent(getApplicationContext(), ActivityEntretien.class);
                startActivity(entretienIntent);
            }
        });
        btn_protection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent protectionIntent = new Intent(getApplicationContext(), ProtectionActivity.class);
                startActivity(protectionIntent);
            }
        });
    }
}
