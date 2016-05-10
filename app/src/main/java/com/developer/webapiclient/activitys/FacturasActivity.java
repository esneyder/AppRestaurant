package com.developer.webapiclient.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.developer.webapiclient.R;
import com.developer.webapiclient.RestClient;
import com.developer.webapiclient.adapdater.FacturasAdapter;
import com.developer.webapiclient.modelo.Tikect;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FacturasActivity extends AppCompatActivity
        implements android.view.View.OnClickListener {
ListView listView;
Button btnAdd;
RestClient restService;
TextView mesa_Id;
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    restService = new RestClient();
    setContentView(R.layout.activity_facturas);
    btnAdd = (Button) findViewById(R.id.btnaddfactura);
    btnAdd.setOnClickListener(this);
}

@Override
public void onResume() {

    super.onResume();
    refreshScreen();
}

private void refreshScreen() {
    //Call to server to grab list of student records. this is a asyn
    Call<List<Tikect>> call = restService.getService().getFacturas();
    call.enqueue(new Callback<List<Tikect>>() {
                     @Override
                     public void onResponse(Call<List<Tikect>> call, Response<List<Tikect>> response) {
                         ListView lv = (ListView) findViewById(R.id.listfactura);

                         FacturasAdapter customAdapter = new FacturasAdapter(FacturasActivity.this,
                                                                              R.layout.view_facturas_entry, response.body());

                         lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                             @Override
                             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                 mesa_Id = (TextView) view.findViewById(R.id.factura_Id);
                                 String client_id = mesa_Id.getText().toString();
                                 Intent objIndent = new Intent(getApplicationContext(), DetalleFacturaActivity.class);
                                 objIndent.putExtra("factura_Id", Integer.parseInt(client_id));
                                 startActivity(objIndent);
                             }
                         });
                         lv.setAdapter(customAdapter);
                     }

                     @Override
                     public void onFailure(Call<List<Tikect>> call, Throwable t) {
                         Toast.makeText(FacturasActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

                     }
                 }
    );

}


@Override
public void onClick(View view) {
    if (view == btnAdd) {
        Intent intent = new Intent(this, DetalleFacturaActivity.class);
        intent.putExtra("factura_Id", 0);
        startActivity(intent);
    }
}
}


