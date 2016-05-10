package com.developer.webapiclient.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.webapiclient.R;
import com.developer.webapiclient.modelo.Client;
import com.developer.webapiclient.adapdater.ClientAdapter;

import com.developer.webapiclient.RestClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientActivity extends AppCompatActivity implements android.view.View.OnClickListener {
ListView listView;
Button btnGetAll, btnAdd;
RestClient restService;
TextView client_Id;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    restService = new RestClient();
    setContentView(R.layout.activity_client);
    btnGetAll = (Button) findViewById(R.id.btnclient);
    btnAdd = (Button) findViewById(R.id.btnaddclient);

    btnGetAll.setOnClickListener(this);
    btnAdd.setOnClickListener(this);


}

@Override
public void onResume() {

    super.onResume();
    refreshScreen();
}

private void refreshScreen() {
    //Call to server to grab list of student records. this is a asyn
    Call<List<Client>> call = restService.getService().getclient();
    call.enqueue(new Callback<List<Client>>() {
                     @Override
                     public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                         ListView lv = (ListView) findViewById(R.id.listclient);

                         ClientAdapter customAdapter = new ClientAdapter(ClientActivity.this, R.layout.view_client_entry, response.body());

                         lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                             @Override
                             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                 client_Id = (TextView) view.findViewById(R.id.client_Id);
                                 String client_id = client_Id.getText().toString();
                                 Intent objIndent = new Intent(getApplicationContext(), ClienteDetailtActivity.class);
                                 objIndent.putExtra("client_Id", Integer.parseInt(client_id));
                                 startActivity(objIndent);
                             }
                         });
                         lv.setAdapter(customAdapter);
                     }

                     @Override
                     public void onFailure(Call<List<Client>> call, Throwable t) {
                         Toast.makeText(ClientActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

                     }
                 }
    );

}

private void refreshScreen_SimpleWay() {
    Call<List<Client>> call = restService.getService().getclient();
    call.enqueue(new Callback<List<Client>>() {
                     @Override
                     public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                         ListView lv = (ListView) findViewById(R.id.listclient);


                         ArrayList<HashMap<String, String>> clientList = new ArrayList<HashMap<String, String>>();

                         for (int i = 0; i < response.body().size(); i++) {
                             HashMap<String, String> client = new HashMap<String, String>();
                             client.put("IdCliente", String.valueOf(response.body().get(i).IdCliente));
                             client.put("Nombres", String.valueOf(response.body().get(i).Nombres));
                             client.put("Apellidos", String.valueOf(response.body().get(i).Apellidos));

                             clientList.add(client);
                         }

                         lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                             @Override
                             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                 client_Id = (TextView) view.findViewById(R.id.client_Id);
                                 String client_id = client_Id.getText().toString();
                                 Intent objIndent = new Intent(getApplicationContext(), ClienteDetailtActivity.class);
                                 objIndent.putExtra("client_Id", Integer.parseInt(client_id));
                                 startActivity(objIndent);
                             }
                         });
                         ListAdapter adapter = new
                                                       SimpleAdapter(ClientActivity.this, clientList, R.layout.view_client_entry,
                                                                            new String[]{"IdCliente", "Nombres", "Apellidos"},
                                                                            new int[]{R.id.client_Id, R.id.client_name, R.id.txt_lastname});
                         lv.setAdapter(adapter);
                     }

                     @Override
                     public void onFailure(Call<List<Client>> call, Throwable t) {
                         Toast.makeText(ClientActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

                     }
                 }
    );


}

@Override
public void onClick(View view) {
    if (view == btnGetAll) {
        refreshScreen_SimpleWay();
    } else {

        Intent intent = new Intent(this, ClienteDetailtActivity.class);
        intent.putExtra("client_Id", 0);
        startActivity(intent);
    }
}
}
