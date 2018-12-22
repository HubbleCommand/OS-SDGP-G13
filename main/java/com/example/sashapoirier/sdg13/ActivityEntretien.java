package com.example.sashapoirier.sdg13;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ActivityEntretien extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entretien);
        TextView description = findViewById(R.id.textView7);

        String stuff =
                "La ville de Genève met en place plein de mésures pour entretenir nos forêts.\n"+
                "Des particuliers peuvent demander l'entretien de leurs propres terres:\n"+
                "   -Débroussaillage et évacuation des déchets: 1.5 à 5chf par m2\n"+
                "   -Abattage: 70CHF par m3\n"+
                "Evidamment, la ville entretien aussi les espaces naturels publiques."+
                "Lors de l'entretien de toute espace naturel, il faut faire attention à la biodiversité locale."+
                "Les entreteneurs font aussi très attention aux espèces protégées, qui ne doivent pas etre enlevées."+
                "L'entretien doit etre fait assez souvent pour pas que des especes envahissant comme des vignes."+
                "\n"+
                "Le canton peut découper des arbres pour les raisons suivantes:\n "+
                "   -l’arbre est dangereux\n"+
                "   -permettre le développement d’autres plantes\n"+
                "   -rajeunir le foret.\n"+
                "43% des forêts dans le canton sont consacrées pour le bois-énergie.\n";

        description.setText(stuff);
    }
}
