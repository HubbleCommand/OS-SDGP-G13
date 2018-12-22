package com.example.sashapoirier.sdg13;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ProtectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protection);
        TextView description = findViewById(R.id.textView3);

        String stuff =
                "La loi principale qui protège nos forets cantonals est la 'Loi sur les forets'.\n"+
                "Des défrichements sont indemnisé, soit par somme en argent, soit par restauration du milieu naturel touché.\n"+
                "La loi limite la construction dans les espaces naturels. Meme le placement des tentes est controlé!\n"+
                "La circulation est aussi controlé; seulement les véhicules qui ont pour but d'entretenir ou surveiller les espaces naturels sont permis d'y entrer. La seule exception est pour les véhicueles nécessaires à l'exploitation agricole."+
                "L'acces au forets est aussi restrein";

        description.setText(stuff);
    }
}
