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

public class ComplaintAdaptor extends ArrayAdapter<data> {

    public ComplaintAdaptor(Context context, ArrayList<data> data)
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
                    R.layout.complaint_listview_item, parent, false);
        }

        data curruentdata = getItem(position);

        TextView date = (TextView)listItemView.findViewById(R.id.datetv);
        date.setText(data.getDate());

        TextView message = (TextView)listItemView.findViewById(R.id.messagetv);
        message.setText(data.getMessage());

        TextView status = (TextView)listItemView.findViewById(R.id.statustv);
        status.setText(data.getStatus());

        return listItemView;
    }
}
