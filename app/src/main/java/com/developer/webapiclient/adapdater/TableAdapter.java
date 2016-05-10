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
import com.developer.webapiclient.modelo.Table;


import java.util.List;

public  class TableAdapter extends ArrayAdapter<Table> {

    public TableAdapter(Context context, int resource, List<Table> tables) {
        super(context, resource, tables);
    }



@Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.view_mesa_entry, parent, false);
        }

        Table table = getItem(position);

        if (table != null) {
            TextView tvSClient = (TextView) v.findViewById(R.id.mesa_Id);
            TextView tvClientName = (TextView) v.findViewById(R.id.numcomensales);
            TextView tvClientLastName = (TextView) v.findViewById(R.id.ubicacion);
            tvSClient.setText( Integer.toString(table.IdMesa));
            tvClientName.setText(Integer.toString(table.NumMaxComensales));
            tvClientLastName.setText(table.Ubicacion);
        }

        return v;
    }
}

