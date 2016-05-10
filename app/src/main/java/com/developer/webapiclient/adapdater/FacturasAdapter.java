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
import com.developer.webapiclient.RestClient;
import com.developer.webapiclient.modelo.Tikect;


import java.util.List;

public  class FacturasAdapter extends ArrayAdapter<Tikect> {
RestClient restService;
    public FacturasAdapter(Context context, int resource, List<Tikect> facturases) {
        super(context, resource, facturases);

    }



@Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
    restService = new RestClient();
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.view_facturas_entry, parent, false);
        }

    Tikect facturas = getItem(position);

        if (facturas != null) {
            TextView tvSClient = (TextView) v.findViewById(R.id.factura_Id);
            final TextView tvClientName = (TextView) v.findViewById(R.id.cliente_name);
            TextView tvCamareroName = (TextView) v.findViewById(R.id.camarero_name);
            TextView tvMesaName = (TextView) v.findViewById(R.id.mesa_name);
            TextView tvFechaFactura = (TextView) v.findViewById(R.id.fechafactura);
            tvSClient.setText(facturas.id + "");
            tvClientName.setText(facturas.Cliente);
            tvCamareroName.setText(facturas.Camarero);
            tvMesaName.setText(facturas.Ubicacion);
            tvFechaFactura.setText(facturas.FechaFactura);

        }

        return v;
    }


}

