package com.yoji.changelanguagespinner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Spinner langSpinner;

    ArrayAdapter<CharSequence> langSpinnerAdapter;
    private SharedPreferences langSharedPrefs;
    private final String LOCALE_KEY = "locale_key";
    private Locale locale;
    private String chosenLocale;

    private AdapterView.OnItemSelectedListener langSpinnerOnItemClickListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case Language.RUSSIAN:
                    chosenLocale = "ru";
                    break;
                case Language.ENGLISH:
                    chosenLocale = "en";
                    break;
                case Language.SPANISH:
                    chosenLocale = "es";
                    break;
                case Language.GERMAN:
                    chosenLocale = "de";
                    break;
                case Language.FRENCH:
                    chosenLocale = "fr";
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };


    private View.OnClickListener okBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (chosenLocale != null) {
                locale = new Locale(chosenLocale);
                Configuration config = new Configuration();
                config.setLocale(locale);
                SharedPreferences.Editor editor = langSharedPrefs.edit();
                editor.putString(LOCALE_KEY, chosenLocale);
                editor.apply();
                getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                recreate();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    public void init() {
        langSpinner = findViewById(R.id.langSpinnerId);
        Button okButton = findViewById(R.id.okBtnId);

        langSharedPrefs = getSharedPreferences("Language", MODE_PRIVATE);

        langSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.language_array,
                android.R.layout.simple_spinner_dropdown_item);

        langSpinner.setAdapter(langSpinnerAdapter);
        langSpinner.setOnItemSelectedListener(langSpinnerOnItemClickListener);
        setLangSpinnerItem();
        okButton.setOnClickListener(okBtnOnClickListener);
    }

    public void setLangSpinnerItem() {
        String currentLanguage;
        String[] languageArray = getResources().getStringArray(R.array.language_array);
        if (langSharedPrefs.getString(LOCALE_KEY, "").matches("")) {
            locale = Locale.getDefault();
        } else {
            locale = new Locale(langSharedPrefs.getString(LOCALE_KEY, ""));
        }
        switch (locale.getLanguage()) {
            case "ru":
                currentLanguage = languageArray[Language.RUSSIAN];
                break;
            case "en":
                currentLanguage = languageArray[Language.ENGLISH];
                break;
            case "es":
                currentLanguage = languageArray[Language.SPANISH];
                break;
            case "de":
                currentLanguage = languageArray[Language.GERMAN];
                break;
            case "fr":
                currentLanguage = languageArray[Language.FRENCH];
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + locale.getLanguage());
        }
        langSpinner.setSelection(langSpinnerAdapter.getPosition(currentLanguage));
        String currentLocale = getResources().getConfiguration().locale.getLanguage();
        if (!currentLocale.equals(langSharedPrefs.getString(LOCALE_KEY, "")) && !langSharedPrefs.getString(LOCALE_KEY, "").matches("")) {
            Configuration config = new Configuration();
            config.setLocale(locale);
            getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
            recreate();
        }
    }
}