package com.example.sashapoirier.sdg13;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Legal1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legal1);
        TextView legal_text = findViewById(R.id.legal_description);
        String text = "\n" +
                "En Suisse, toute abattage/élagage d'arbres a besoin d'une autorisation \n\n" +
                "Il y a deux types de demandes;\n" +
                "  -celles liés à un projet de construction \n" +
                "  -celles pas liés à du construction \n\n" +
                "Des demandes non liées à des projets de constructions sont surtout pour des arbres dangereux ou malade.\n\n" +
                "Pour faire un demande d'abattage, il y a un frais entre 150CHF et 1000CHF \n\n" +
                "Si la requête est acceptée, c’est contre une valeur de remplacement\n\n" +
                "Si la requête est refusé, on n'est pas remboursé\n";
        legal_text.setText(text);

    }
}
