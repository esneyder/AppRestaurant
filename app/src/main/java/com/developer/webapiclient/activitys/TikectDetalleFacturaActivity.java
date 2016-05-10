package com.developer.webapiclient.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.developer.webapiclient.R;
import com.developer.webapiclient.RestClient;
import com.developer.webapiclient.modelo.DetalleFactura;
import com.developer.webapiclient.utl.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class TikectDetalleFacturaActivity extends AppCompatActivity  implements Spinner.OnItemSelectedListener,
                                                                                        android.view.View.OnClickListener {

Button btnSave, btnDelete, btnUpdate;
Button btnClose;
EditText plato;
EditText importe;
private List<datosfactura> mListafactura;
private List<datoscocinero> mListacocinero;
private int _factura_Id = 0;
RestClient restService;
Spinner spinnerfactura;
Spinner spinnercocinero;
private String IdFactura="";
private String IdCocinero="";
private ArrayList<String> facturas;
private ArrayList<String> cocineros;
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    restService = new RestClient();
    setContentView(R.layout.activity_tikect_detalle_factura);
    btnSave = (Button) findViewById(R.id.btnSave);
    btnUpdate = (Button) findViewById(R.id.btnUpdate);
    btnDelete = (Button) findViewById(R.id.btnDelete);
    btnClose = (Button) findViewById(R.id.btnClose);
    plato=(EditText) findViewById(R.id.txtplato);
    importe=(EditText) findViewById(R.id.txtimporte);
    btnSave.setOnClickListener(this);
    btnUpdate.setOnClickListener(this);
    btnDelete.setOnClickListener(this);
    btnClose.setOnClickListener(this);
    facturas = new ArrayList<String>();
    cocineros = new ArrayList<String>();
    spinnerfactura = (Spinner) findViewById(R.id.spinnerfactura);
    spinnerfactura.setOnItemSelectedListener(this);
    spinnercocinero = (Spinner) findViewById(R.id.spinnerCocinero);
    spinnercocinero.setOnItemSelectedListener(this);
    mListafactura = new ArrayList<>();
    mListacocinero = new ArrayList<>();
    getFacturas();
    getCocineros();
    _factura_Id = 0;
    Intent intent = getIntent();
    _factura_Id = intent.getIntExtra("factura_Id", 0);
}

private void getFacturas() {
    //Creating a string request
    StringRequest stringRequest = new StringRequest(Config.dataUrl + "Facturas",
                                                           new Response.Listener<String>() {
                                                               @Override
                                                               public void onResponse(String response) {
                                                                   JSONArray result;
                                                                   try {
                                                                       result = new JSONArray(response);
                                                                       getFacturas(result);
                                                                   } catch (JSONException e) {
                                                                       e.printStackTrace();
                                                                   }
                                                               }
                                                           },
                                                           new Response.ErrorListener() {
                                                               @Override
                                                               public void onErrorResponse(VolleyError error) {

                                                               }
                                                           });

    //Creating a request queue
    RequestQueue requestQueue = Volley.newRequestQueue(this);

    //Adding request to the queue
    requestQueue.add(stringRequest);

}

private void getCocineros() {
    //Creating a string request
    StringRequest stringRequest = new StringRequest(Config.dataUrl + "Cocineroes",
                                                           new Response.Listener<String>() {
                                                               @Override
                                                               public void onResponse(String response) {
                                                                   JSONArray result;
                                                                   try {
                                                                       result = new JSONArray(response);
                                                                       getCocinero(result);
                                                                   } catch (JSONException e) {
                                                                       e.printStackTrace();
                                                                   }
                                                               }
                                                           },
                                                           new Response.ErrorListener() {
                                                               @Override
                                                               public void onErrorResponse(VolleyError error) {

                                                               }
                                                           });

    //Creating a request queue
    RequestQueue requestQueue = Volley.newRequestQueue(this);

    //Adding request to the queue
    requestQueue.add(stringRequest);

}

private void getFacturas(JSONArray j) {
    //Traversing through all the items in the json array
    for (int i = 0; i < j.length(); i++) {
        try {
            //Getting json object
            JSONObject json = j.getJSONObject(i);
            datosfactura f=new datosfactura(json.getString("IdFactura"));
            mListafactura.add(f);
            facturas.add(json.getString("FechaFactura"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    spinnerfactura.setAdapter(new
                                      ArrayAdapter<String>(TikectDetalleFacturaActivity.this,
                                                                  android.R.layout.simple_spinner_dropdown_item, facturas));


}

private void getCocinero(JSONArray j) {
    //Traversing through all the items in the json array
    for (int i = 0; i < j.length(); i++) {
        try {
            //Getting json object
            JSONObject json = j.getJSONObject(i);
            datoscocinero c=new datoscocinero(json.getString("IdCocinero"));
            mListacocinero.add(c);
            cocineros.add(json.getString("Nombres"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    spinnercocinero.setAdapter(new
                                      ArrayAdapter<String>(TikectDetalleFacturaActivity.this,
                                                                  android.R.layout.simple_spinner_dropdown_item, cocineros));


}



@Override
public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    switch (adapterView.getId()) {
        case R.id.spinnerfactura:
            datosfactura f=mListafactura.get(i);
            IdFactura = f.getIdFactura();
            break;
        case R.id.spinnerCocinero:
            datoscocinero c=mListacocinero.get(i);
            IdCocinero = c.getIdCocinero();
            break;

    }
}

@Override
public void onNothingSelected(AdapterView<?> adapterView) {

}

public void onClick(View view) {
    if (findViewById(R.id.btnDelete) == view) {

        Call<DetalleFactura> call = restService.getService().deleteDetalleFacturasById(_factura_Id);
        call.enqueue(new Callback<DetalleFactura>() {
            @Override
            public void onResponse(Call<DetalleFactura> call, retrofit2.Response<DetalleFactura> response) {
                Toast.makeText(TikectDetalleFacturaActivity.this, "DetalleFactura Record Deleted", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<DetalleFactura> call, Throwable t) {
                Toast.makeText(TikectDetalleFacturaActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

            }
        });
        finish();
    } else if (view == findViewById(R.id.btnClose)) {
        finish();
    } else if (findViewById(R.id.btnSave) == view) {

        DetalleFactura facturas = new DetalleFactura();

        facturas.IdDetalleFactura = _factura_Id;
        facturas.IdFactura = IdFactura;
        facturas.IdCocinero = IdCocinero;
         facturas.Plato = plato.getText().toString();
        facturas.Importe=importe.getText().toString();
        restService.getService().addDetalleFacturas(facturas).enqueue(new Callback<DetalleFactura>() {
            @Override
            public void onResponse(Call<DetalleFactura> call, retrofit2.Response<DetalleFactura> response) {
                Toast.makeText(TikectDetalleFacturaActivity.this, "New DetalleFactura Inserted.",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<DetalleFactura> call, Throwable t) {
                Toast.makeText(TikectDetalleFacturaActivity.this, t.getMessage().toString(),
                        Toast.LENGTH_LONG).show();
            }
        });


    } else if (findViewById(R.id.btnUpdate) == view) {
        DetalleFactura facturas = new DetalleFactura();
        Integer status = 0;
        facturas.IdFactura = String.valueOf(_factura_Id);
        facturas.IdCocinero = IdCocinero;
        facturas.Plato = plato.getText().toString();
        facturas.Importe = importe.getText().toString();


        Call<DetalleFactura> call = restService.getService().updateDetalleFacturasById(_factura_Id, facturas);
        call.enqueue(new Callback<DetalleFactura>() {
            @Override
            public void onResponse(Call<DetalleFactura> call, retrofit2.Response<DetalleFactura> response) {
                Toast.makeText(TikectDetalleFacturaActivity.this, "DetalleFactura Record updated.", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Call<DetalleFactura> call, Throwable t) {
                Toast.makeText(TikectDetalleFacturaActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}


private class datosfactura{
    private String IdFactura;

    public datosfactura(String idFactura) {
        IdFactura = idFactura;
    }

    public String getIdFactura() {
        return IdFactura;
    }
}

private class  datoscocinero{
    private String IdCocinero;

    public datoscocinero(String idCocinero) {
        IdCocinero = idCocinero;
    }

    public String getIdCocinero() {
        return IdCocinero;
    }
}
}
