package com.example.apple.khaalismilk;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by apple on 10/26/16.
 */

public class AccountHistoryAdaptor extends ArrayAdapter<data> {

    public AccountHistoryAdaptor(Context context, ArrayList<data> data)
    {
        super(context,0,data);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.accounthis_listitems, parent, false);
        }

        data curruentdata = getItem(position);

        TextView date = (TextView)listItemView.findViewById(R.id.datetext);
        date.setText(data.getDate());

        TextView message = (TextView)listItemView.findViewById(R.id.quantitytext);
        message.setText(data.getMessage());


        return listItemView;
    }
}