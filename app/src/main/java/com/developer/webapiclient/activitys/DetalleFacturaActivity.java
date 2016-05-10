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
import com.developer.webapiclient.modelo.Facturas;
import com.developer.webapiclient.utl.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class DetalleFacturaActivity extends AppCompatActivity
        implements Spinner.OnItemSelectedListener,
                           android.view.View.OnClickListener {

Button btnSave, btnDelete, btnUpdate;
Button btnClose;

private int _factura_Id = 0;
RestClient restService;
Spinner spinnercliente;
Spinner spinnercamarero;
Spinner spinnermesa;
EditText txtfecha;
private List<clasecliente> mListaclientes;
private List<clasecamarero> mListacamareros;
private List<clasemesa> mListamesa;
private ArrayList<String> clientes;
private ArrayList<String> camarero;
private ArrayList<String> mesa;
private String IdCliente = "";
private String IdCamarero = "";
private String IdMesa = "";
//JSON Array


@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    restService = new RestClient();
    setContentView(R.layout.activity_detalle_factura);
    txtfecha = (EditText) findViewById(R.id.txtfechafactura);
    Calendar c = Calendar.getInstance();
    SimpleDateFormat df = new SimpleDateFormat("yyyy/MMM/dd");
    String formattedDate = df.format(c.getTime());
    txtfecha.setText(formattedDate);
    txtfecha.setEnabled(false);
    btnSave = (Button) findViewById(R.id.btnSave);
    btnUpdate = (Button) findViewById(R.id.btnUpdate);
    btnDelete = (Button) findViewById(R.id.btnDelete);
    btnClose = (Button) findViewById(R.id.btnClose);

    btnSave.setOnClickListener(this);
    btnUpdate.setOnClickListener(this);
    btnDelete.setOnClickListener(this);
    btnClose.setOnClickListener(this);
    mListaclientes = new ArrayList<>();
    mListacamareros = new ArrayList<>();
    mListamesa = new ArrayList<>();
    clientes = new ArrayList<String>();
    camarero = new ArrayList<String>();
    mesa = new ArrayList<String>();
    spinnercliente = (Spinner) findViewById(R.id.spinnercliente);
    spinnercliente.setOnItemSelectedListener(this);
    spinnercamarero = (Spinner) findViewById(R.id.spinnerCamarero);
    spinnercamarero.setOnItemSelectedListener(this);
    spinnermesa = (Spinner) findViewById(R.id.spinnermesa);
    spinnermesa.setOnItemSelectedListener(this);

    getCliente();
    getCamarero();
    getMesa();

    _factura_Id = 0;
    Intent intent = getIntent();
    _factura_Id = intent.getIntExtra("factura_Id", 0);
}


private void getCliente() {
    //Creating a string request
    StringRequest stringRequest = new StringRequest(Config.dataUrl + "Clientes",
                                                           new Response.Listener<String>() {
                                                               @Override
                                                               public void onResponse(String response) {
                                                                   JSONArray result;
                                                                   try {
                                                                       result = new JSONArray(response);

                                                                       getClient(result);
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


private void getCamarero() {
    //Creating a string request
    StringRequest stringRequest = new StringRequest(Config.dataUrl + "Camareroes",
                                                           new Response.Listener<String>() {
                                                               @Override
                                                               public void onResponse(String response) {
                                                                   JSONArray result;
                                                                   try {
                                                                       result = new JSONArray(response);

                                                                       getCamarero(result);
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


private void getMesa() {
    //Creating a string request
    StringRequest stringRequest = new StringRequest(Config.dataUrl + "Mesas",
                                                           new Response.Listener<String>() {
                                                               @Override
                                                               public void onResponse(String response) {
                                                                   JSONArray result;
                                                                   try {
                                                                       result = new JSONArray(response);

                                                                       getMesa(result);
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


private void getClient(JSONArray j) {
    //Traversing through all the items in the json array
    for (int i = 0; i < j.length(); i++) {
        try {
            //Getting json object
            JSONObject json = j.getJSONObject(i);

            clasecliente c = new clasecliente(json.getString("IdCliente"), json.getString("Nombres"));
            mListaclientes.add(c);
            clientes.add(json.getString("Nombres"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    spinnercliente.setAdapter(new
                                      ArrayAdapter<String>(DetalleFacturaActivity.this,
                                                                  android.R.layout.simple_spinner_dropdown_item, clientes));


}

private void getCamarero(JSONArray j) {
    //Traversing through all the items in the json array
    for (int i = 0; i < j.length(); i++) {
        try {
            //Getting json object
            JSONObject json = j.getJSONObject(i);
            clasecamarero c = new clasecamarero(json.getString("IdCamarero"),
                                                       json.getString("Nombres"));
            mListacamareros.add(c);
            camarero.add(json.getString("Nombres"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    spinnercamarero.setAdapter(new
                                       ArrayAdapter<String>(DetalleFacturaActivity.this,
                                                                   android.R.layout.simple_spinner_dropdown_item, camarero));


}

private void getMesa(JSONArray j) {
    //Traversing through all the items in the json array
    for (int i = 0; i < j.length(); i++) {
        try {
            //Getting json object
            JSONObject json = j.getJSONObject(i);
            clasemesa m = new clasemesa(json.getString("IdMesa"), json.getString("Ubicacion"));
            mListamesa.add(m);
            mesa.add(json.getString("Ubicacion"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    spinnermesa.setAdapter(new
                                   ArrayAdapter<String>(DetalleFacturaActivity.this,
                                                               android.R.layout.simple_spinner_dropdown_item, mesa));


}

@Override
public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    switch (adapterView.getId()) {
        case R.id.spinnercliente:
            clasecliente c = mListaclientes.get(i);
            IdCliente = c.getId();
            break;
        case R.id.spinnerCamarero:
            clasecamarero cc = mListacamareros.get(i);
            IdCamarero = cc.getId();
            break;
        case R.id.spinnermesa:
            clasemesa m = mListamesa.get(i);
            IdMesa = m.getId();
            break;
    }
}

@Override
public void onNothingSelected(AdapterView<?> adapterView) {

}

@Override
public void onClick(View view) {
    if (findViewById(R.id.btnDelete) == view) {

        Call<Facturas> call = restService.getService().deleteFacturasById(_factura_Id);
        call.enqueue(new Callback<Facturas>() {
            @Override
            public void onResponse(Call<Facturas> call, retrofit2.Response<Facturas> response) {
                Toast.makeText(DetalleFacturaActivity.this, "Facturas Record Deleted", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Facturas> call, Throwable t) {
                Toast.makeText(DetalleFacturaActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

            }
        });
        finish();
    } else if (view == findViewById(R.id.btnClose)) {
        finish();
    } else if (findViewById(R.id.btnSave) == view) {

        Facturas facturas = new Facturas();

        facturas.IdFactura = _factura_Id;
        facturas.IdCliente = Integer.parseInt(IdCliente);
        facturas.IdCamarero = Integer.parseInt(IdCamarero);
        facturas.IdMesa = Integer.parseInt(IdMesa);
        facturas.FechaFactura=txtfecha.getText().toString();
        restService.getService().addFacturas(facturas).enqueue(new Callback<Facturas>() {
            @Override
            public void onResponse(Call<Facturas> call, retrofit2.Response<Facturas> response) {
                Toast.makeText(DetalleFacturaActivity.this, "New Facturas Inserted.",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Facturas> call, Throwable t) {
                Toast.makeText(DetalleFacturaActivity.this, t.getMessage().toString(),
                        Toast.LENGTH_LONG).show();
            }
        });


    } else if (findViewById(R.id.btnUpdate) == view) {
        Facturas facturas = new Facturas();
        Integer status = 0;
        facturas.IdFactura = _factura_Id;
        facturas.IdCliente = Integer.parseInt(IdCliente);
        facturas.IdCamarero = Integer.parseInt(IdCamarero);
        facturas.IdMesa = Integer.parseInt(IdMesa);
        facturas.FechaFactura=txtfecha.getText().toString();

        Call<Facturas> call = restService.getService().updateFacturasById(_factura_Id, facturas);
        call.enqueue(new Callback<Facturas>() {
            @Override
            public void onResponse(Call<Facturas> call, retrofit2.Response<Facturas> response) {
                Toast.makeText(DetalleFacturaActivity.this, "Facturas Record updated.", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Call<Facturas> call, Throwable t) {
                Toast.makeText(DetalleFacturaActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

private class clasecliente {
    private String Id;
    private String Nombres;

    public clasecliente(String id, String nombres) {
        Id = id;
        Nombres = nombres;
    }

    public String getId() {
        return Id;
    }

    public String getNombres() {
        return Nombres;
    }
}

private class clasecamarero {
    private String Id;
    private String Nombres;

    public clasecamarero(String id, String nombres) {
        Id = id;
        Nombres = nombres;
    }

    public String getId() {
        return Id;
    }

    public String getNombres() {
        return Nombres;
    }
}

private class clasemesa {
    private String Id;
    private String Ubicacion;

    public clasemesa(String id, String ubicacion) {
        Id = id;
        Ubicacion = ubicacion;
    }

    public String getId() {
        return Id;
    }

    public String getUbicacion() {
        return Ubicacion;
    }
}
}
