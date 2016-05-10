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
import com.developer.webapiclient.modelo.Client;


import java.util.List;

public  class ClientAdapter extends ArrayAdapter<Client> {

    public ClientAdapter(Context context, int resource, List<Client> clients) {
        super(context, resource, clients);
    }



@Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.view_client_entry, parent, false);
        }

        Client client = getItem(position);

        if (client != null) {
            TextView tvSClient = (TextView) v.findViewById(R.id.client_Id);
            TextView tvClientName = (TextView) v.findViewById(R.id.client_name);
            TextView tvClientLastName = (TextView) v.findViewById(R.id.txt_lastname);
            tvSClient.setText( Integer.toString(client.IdCliente));
            tvClientName.setText(client.Nombres);
            tvClientLastName.setText(client.Apellidos);
        }

        return v;
    }
}

