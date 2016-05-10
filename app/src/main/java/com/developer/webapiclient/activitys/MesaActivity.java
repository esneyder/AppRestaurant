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
import com.developer.webapiclient.adapdater.TableAdapter;
import com.developer.webapiclient.modelo.Table;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MesaActivity extends AppCompatActivity
        implements android.view.View.OnClickListener {
ListView listView;
Button  btnAdd;
RestClient restService;
TextView mesa_Id;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    restService = new RestClient();
    setContentView(R.layout.activity_mesa);
    btnAdd = (Button) findViewById(R.id.btnaddmesa);

    btnAdd.setOnClickListener(this);

}

@Override
public void onResume() {

    super.onResume();
    refreshScreen();
}

private void refreshScreen() {
    //Call to server to grab list of student records. this is a asyn
    Call<List<Table>> call = restService.getService().getTable();
    call.enqueue(new Callback<List<Table>>() {
                     @Override
                     public void onResponse(Call<List<Table>> call, Response<List<Table>> response) {
                         ListView lv = (ListView) findViewById(R.id.listmesa);

                         TableAdapter customAdapter = new TableAdapter(MesaActivity.this,
                                                                                R.layout.view_mesa_entry, response.body());

                         lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                             @Override
                             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                 mesa_Id = (TextView) view.findViewById(R.id.mesa_Id);
                                 String client_id = mesa_Id.getText().toString();
                                 Intent objIndent = new Intent(getApplicationContext(), DetalleMesaActivity.class);
                                 objIndent.putExtra("mesa_Id", Integer.parseInt(client_id));
                                 startActivity(objIndent);
                             }
                         });
                         lv.setAdapter(customAdapter);
                     }

                     @Override
                     public void onFailure(Call<List<Table>> call, Throwable t) {
                         Toast.makeText(MesaActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

                     }
                 }
    );

}


@Override
public void onClick(View view) {
    if (view == btnAdd) {
       Intent intent = new Intent(this, DetalleMesaActivity.class);
        intent.putExtra("mesa_Id", 0);
        startActivity(intent);
    }
}
}

