package com.developer.webapiclient.adapdater;

/**
 * Created by Tan on 3/4/2016.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.developer.webapiclient.R;
import com.developer.webapiclient.modelo.Chef;

import java.util.List;

public  class ChefAdapter extends ArrayAdapter<Chef> {

    public ChefAdapter(Context context, int resource, List<Chef> chefs) {
        super(context, resource, chefs);
    }



@Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.view_chef_entry, parent, false);
        }

        Chef chef = getItem(position);

        if (chef != null) {
            TextView tvSClient = (TextView) v.findViewById(R.id.chef_Id);
            TextView tvClientName = (TextView) v.findViewById(R.id.txtnamechef);
            TextView tvClientLastName = (TextView) v.findViewById(R.id.txtlasnamechef);
            tvSClient.setText( Integer.toString(chef.IdCocinero));
            tvClientName.setText(chef.Nombres);
            tvClientLastName.setText(chef.Apellidos);
        }

        return v;
    }
}

