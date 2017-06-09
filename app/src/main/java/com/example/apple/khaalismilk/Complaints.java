package com.example.apple.khaalismilk;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Complaints extends AppCompatActivity {

    FloatingActionButton button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);

        final ArrayList<data> dummy = new ArrayList<data>();

        dummy.add(new data("kuch b","Zalmo mera doodh dedo","kuch b"));
        dummy.add(new data("kuch b","Zalmo mera doodh dedo","kuch b"));
        dummy.add(new data("kuch b","Zalmo mera doodh dedo","kuch b"));
        dummy.add(new data("kuch b","Zalmo mera doodh dedo","kuch b"));
        dummy.add(new data("kuch b","Zalmo mera doodh dedo","kuch b"));


        ComplaintAdaptor adaptor = new ComplaintAdaptor(this,dummy);
        ListView listview = (ListView)findViewById(R.id.listview);
        listview.setAdapter(adaptor);

        button = (FloatingActionButton)findViewById(R.id.floatingActionButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Complaints.this,SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}
