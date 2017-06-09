package com.example.apple.khaalismilk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;

public class SignUp extends AppCompatActivity {

    Spinner town;
    int defaultvalue = 0;
    String defaultval = "Select Town";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        town = (Spinner)findViewById(R.id.town);
        town.setPrompt(defaultval);
    }
}
