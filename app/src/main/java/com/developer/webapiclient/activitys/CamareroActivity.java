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
import com.developer.webapiclient.adapdater.WaiterAdapter;
import com.developer.webapiclient.modelo.Waiter;

import com.developer.webapiclient.RestClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CamareroActivity extends AppCompatActivity  implements android.view.View.OnClickListener{
ListView listView;
Button btnAdd;
RestClient restService;
TextView camarero_Id;
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    restService = new RestClient();
    setContentView(R.layout.activity_camarero);
    btnAdd = (Button) findViewById(R.id.btnaddcamarero);
    btnAdd.setOnClickListener(this);
}

@Override
public void onResume() {

    super.onResume();
    refreshScreen();
}

private void refreshScreen() {

    Call<List<Waiter>> call = restService.getService().getWaiter();
    call.enqueue(new Callback<List<Waiter>>() {
                     @Override
                     public void onResponse(Call<List<Waiter>> call, Response<List<Waiter>> response) {
                         ListView lv = (ListView) findViewById(R.id.listcamarero);

                         WaiterAdapter customAdapter = new WaiterAdapter(CamareroActivity.this,
                                                                            R.layout.view_camarero_entry, response.body());

                         lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                             @Override
                             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                 camarero_Id = (TextView) view.findViewById(R.id.camarero_Id);
                                 String client_id = camarero_Id.getText().toString();
                                 Intent objIndent = new Intent(getApplicationContext(), DetalleCamareroActivity.class);
                                 objIndent.putExtra("camarero_Id", Integer.parseInt(client_id));
                                 startActivity(objIndent);
                             }
                         });
                         lv.setAdapter(customAdapter);
                     }

                     @Override
                     public void onFailure(Call<List<Waiter>> call, Throwable t) {
                         Toast.makeText(CamareroActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

                     }
                 }
    );

}

@Override
public void onClick(View view) {
    if (view == btnAdd) {

        Intent intent = new Intent(this, DetalleCamareroActivity.class);
        intent.putExtra("camarero_Id", 0);
        startActivity(intent);
    }
}
}

