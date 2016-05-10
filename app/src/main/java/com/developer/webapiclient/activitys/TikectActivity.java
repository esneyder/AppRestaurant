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
import com.developer.webapiclient.adapdater.DetalleFacturasAdapter;
import com.developer.webapiclient.modelo.TikectFinal;

import com.developer.webapiclient.RestClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TikectActivity extends AppCompatActivity implements android.view.View.OnClickListener {
ListView listView;
Button btnAdd;
RestClient restService;
TextView mesa_Id;
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    restService = new RestClient();
    setContentView(R.layout.activity_tikect);
    btnAdd = (Button) findViewById(R.id.btnaddtikect);
    btnAdd.setOnClickListener(this);
}

@Override
public void onResume() {

    super.onResume();
    refreshScreen();
}

private void refreshScreen() {
    //Call to server to grab list of student records. this is a asyn
    Call<List<TikectFinal>> call = restService.getService().getDetalleFactura();
    call.enqueue(new Callback<List<TikectFinal>>() {
                     @Override
                     public void onResponse(Call<List<TikectFinal>> call, Response<List<TikectFinal>> response) {
                         ListView lv = (ListView) findViewById(R.id.listtikect);

                         DetalleFacturasAdapter customAdapter = new DetalleFacturasAdapter(TikectActivity.this,
                                                                                    R.layout.view_detallefacturas_entry, response.body());

                         lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                             @Override
                             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                 mesa_Id = (TextView) view.findViewById(R.id.detallefactura_Id);
                                 String client_id = mesa_Id.getText().toString();
                                 Intent objIndent = new Intent(getApplicationContext(), DetalleFacturaActivity.class);
                                 objIndent.putExtra("factura_Id", Integer.parseInt(client_id));
                                 startActivity(objIndent);
                             }
                         });
                         lv.setAdapter(customAdapter);
                     }

                     @Override
                     public void onFailure(Call<List<TikectFinal>> call, Throwable t) {
                         Toast.makeText(TikectActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

                     }
                 }
    );

}


@Override
public void onClick(View view) {
    if (view == btnAdd) {
        Intent intent = new Intent(this, TikectDetalleFacturaActivity.class);
        intent.putExtra("factura_Id", 0);
        startActivity(intent);
    }
}
}


