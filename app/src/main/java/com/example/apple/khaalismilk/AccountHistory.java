package com.example.apple.khaalismilk;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class AccountHistory extends AppCompatActivity {

    FloatingActionButton button;
    Spinner month;
    Spinner year;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_history);

        button = (FloatingActionButton)findViewById(R.id.floatingActionButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountHistory.this,SettingsActivity.class);
                startActivity(intent);
            }
        });

        month = (Spinner)findViewById(R.id.monthspinner);
        year = (Spinner)findViewById(R.id.yearspinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.month_array,android.R.layout.simple_dropdown_item_1line);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.year_array,android.R.layout.simple_dropdown_item_1line);

        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        month.setPrompt("month");
        year.setPrompt("year");

        month.setAdapter(adapter);
        year.setAdapter(adapter2);

        final ArrayList<data> dummy = new ArrayList<data>();

        dummy.add(new data("kuch b","kuch b"));
        dummy.add(new data("kuch b","kuch b"));
        dummy.add(new data("kuch b","kuch b"));
        dummy.add(new data("kuch b","kuch b"));
        dummy.add(new data("kuch b","kuch b"));
        dummy.add(new data("kuch b","kuch b"));

        AccountHistoryAdaptor adaptor = new AccountHistoryAdaptor(this,dummy);
        ListView listview = (ListView)findViewById(R.id.acclistview);
        listview.setAdapter(adaptor);
    }
}
