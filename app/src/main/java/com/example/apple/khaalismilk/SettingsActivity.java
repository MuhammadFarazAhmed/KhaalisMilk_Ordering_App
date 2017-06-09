package com.example.apple.khaalismilk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;

public class SettingsActivity extends AppCompatActivity {

    public static final String LOG_TAG = SettingsActivity.class.getSimpleName();

    TextView managesubscription;
    TextView complaints;
    TextView accounthis;
    TextView logout;
     session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        session = new session(this);
        session.checkLogin();

        HashMap<String, String> user = session.getUserDetails();

        // name
        String name = user.get(session.KEY_NAME);

        // email
        String email = user.get(session.KEY_PASSWORD);

        managesubscription = (TextView)findViewById(R.id.managesubscription);

        managesubscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this,SubscriptionScreen.class);
                startActivity(intent);
            }
        });

        complaints = (TextView)findViewById(R.id.complaints);

        complaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this,Complaints.class);
                startActivity(intent);
            }
        });

        accounthis = (TextView)findViewById(R.id.account_history);

        accounthis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this,AccountHistory.class);
                startActivity(intent);
            }
        });

        logout = (TextView)findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                  session.logoutUser();
            }
        });

    }
}
