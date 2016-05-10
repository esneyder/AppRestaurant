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
import com.developer.webapiclient.modelo.Waiter;

import java.util.List;

public  class WaiterAdapter extends ArrayAdapter<Waiter> {

    public WaiterAdapter(Context context, int resource, List<Waiter> waiters) {
        super(context, resource, waiters);
    }



@Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.view_camarero_entry, parent, false);
        }

    Waiter waiter = getItem(position);

        if (waiter != null) {
            TextView tvSClient = (TextView) v.findViewById(R.id.camarero_Id);
            TextView tvClientName = (TextView) v.findViewById(R.id.txtnamecamarero);
            TextView tvClientLastName = (TextView) v.findViewById(R.id.txtlasnamecamarero);
            tvSClient.setText( Integer.toString(waiter.IdCamarero));
            tvClientName.setText(waiter.Nombres);
            tvClientLastName.setText(waiter.Apellidos);
        }

        return v;
    }
}

