package com.burhanloey.quranrnguthmani_yusufali;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final Random random = new Random();

    private final int[] numberOfAyahs = { 7, 286, 200, 176, 120, 165, 206, 75, 129, 109, 123, 111,
            43, 52, 99, 128, 111, 110, 98, 135, 112, 78, 118, 64, 77, 227, 93, 88, 69, 60, 34, 30,
            73, 54, 45, 83, 182, 88, 75, 85, 54, 53, 89, 59, 37, 35, 38, 29, 18, 45, 60, 49, 62, 55,
            78, 96, 29, 22, 24, 13, 14, 11, 11, 18, 12, 12, 30, 52, 52, 44, 28, 28, 20, 56, 40, 31,
            50, 40, 46, 42, 29, 19, 36, 25, 22, 17, 19, 26, 30, 20, 15, 21, 11, 8, 8, 19, 5, 8, 8,
            11, 11, 8, 3, 9, 5, 4, 7, 3, 6, 3, 5, 4, 5, 6 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void randomize(View view) {
        TextView arabicTextView = findViewById(R.id.arabic_textview);
        TextView englishTextView = findViewById(R.id.english_textview);

        int chosenSurah = random.nextInt(numberOfAyahs.length) + 1;
        int chosenVerse = random.nextInt(numberOfAyahs[chosenSurah - 1]) + 1;

        arabicTextView.setText(getStringResourceByName("ar_" + chosenSurah + "_" + chosenVerse));
        englishTextView.setText(getStringResourceByName("en_" + chosenSurah + "_" + chosenVerse));
    }

    private String getStringResourceByName(String name) {
        int id = getResources().getIdentifier(name, "string", getPackageName());

        if (id == 0) {
            return name;
        } else {
            return getString(id);
        }
    }
}
