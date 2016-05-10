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
import com.developer.webapiclient.modelo.TikectFinal;

import com.developer.webapiclient.RestClient;

import java.util.List;

public  class DetalleFacturasAdapter extends ArrayAdapter<TikectFinal> {
RestClient restService;
    public DetalleFacturasAdapter(Context context, int resource, List<TikectFinal> facturases) {
        super(context, resource, facturases);

    }



@Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
    restService = new RestClient();
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.view_detallefacturas_entry, parent, false);
        }

    TikectFinal facturas = getItem(position);

        if (facturas != null) {
            TextView tvSClient = (TextView) v.findViewById(R.id.detallefactura_Id);
            final TextView tvClientName = (TextView) v.findViewById(R.id.cocinero_name);
            TextView tvCamareroName = (TextView) v.findViewById(R.id.plato_name);
            TextView tvMesaName = (TextView) v.findViewById(R.id.Importe);
            TextView tvFechaFactura = (TextView) v.findViewById(R.id.fecha);
            tvSClient.setText(facturas.IdDetalleFactura + "");
            tvClientName.setText(facturas.Cocinero);
            tvCamareroName.setText(facturas.Plato);
            tvMesaName.setText(facturas.Importe);
            tvFechaFactura.setText(facturas.FechaFactura);

        }

        return v;
    }


}

