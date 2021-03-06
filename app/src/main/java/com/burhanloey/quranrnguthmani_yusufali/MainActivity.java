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

    private TextView titleTextView;
    private TextView arabicTextView;
    private TextView englishTextView;

    private int chosenSurah = 1;
    private int chosenVerse = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (titleTextView == null) { titleTextView = findViewById(R.id.title_textview); }
        if (arabicTextView == null) { arabicTextView = findViewById(R.id.arabic_textview); }
        if (englishTextView == null) { englishTextView = findViewById(R.id.english_textview); }

        if (savedInstanceState != null) {
            chosenSurah = savedInstanceState.getInt("chosen_surah", 1);
            chosenVerse = savedInstanceState.getInt("chosen_verse", 1);
            String title = savedInstanceState.getString("title");
            String arabicText = savedInstanceState.getString("arabic_text");
            String englishText = savedInstanceState.getString("english_text");

            titleTextView.setText(title);
            arabicTextView.setText(arabicText);
            englishTextView.setText(englishText);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("chosen_surah", chosenSurah);
        outState.putInt("chosen_verse", chosenVerse);
        outState.putString("title", titleTextView.getText().toString());
        outState.putString("arabic_text", arabicTextView.getText().toString());
        outState.putString("english_text", englishTextView.getText().toString());
    }

    /**
     * Click event for prev_button.
     *
     * @param view View
     */
    public void prev(View view) {
        if (chosenVerse > 1) {
            chosenVerse--;
        } else if (chosenSurah > 1) {
            chosenSurah--;
            chosenVerse = numberOfAyahs[chosenSurah - 1];
        } else {
            return;
        }

        loadVerse();
    }

    /**
     * Click event for randomize_button.
     *
     * @param view View
     */
    public void randomize(View view) {
        chosenSurah = random.nextInt(numberOfAyahs.length) + 1;
        chosenVerse = random.nextInt(numberOfAyahs[chosenSurah - 1]) + 1;

        loadVerse();
    }

    /**
     * Click event for next_button.
     *
     * @param view View
     */
    public void next(View view) {
        if (chosenVerse < numberOfAyahs[chosenSurah - 1]) {
            chosenVerse++;
        } else if (chosenSurah < numberOfAyahs.length) {
            chosenSurah++;
            chosenVerse = 1;
        } else {
            return;
        }

        loadVerse();
    }



    private void loadVerse() {
        String title = getStringResourceByName("title_" + chosenSurah) + " " +
                getString(R.string.verse) + " " + chosenVerse;
        String arabicText = getStringResourceByName("ar_" + chosenSurah + "_" + chosenVerse);
        String englishText = getStringResourceByName("en_" + chosenSurah + "_" + chosenVerse);

        titleTextView.setText(title);
        arabicTextView.setText(arabicText);
        englishTextView.setText(englishText);
    }

    private String getStringResourceByName(String name) {
        int resId = getResources().getIdentifier(name, "string", getPackageName());

        if (resId == 0) {
            return name;
        } else {
            return getString(resId);
        }
    }
}
