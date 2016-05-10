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
import com.developer.webapiclient.adapdater.ChefAdapter;

import com.developer.webapiclient.RestClient;
import com.developer.webapiclient.modelo.Chef;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChefActivity extends AppCompatActivity
        implements android.view.View.OnClickListener{
ListView listView;
Button  btnAdd;
RestClient restService;
TextView chef_Id;
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    restService = new RestClient();
    setContentView(R.layout.activity_chef);
    btnAdd = (Button) findViewById(R.id.btnaddchef);
    btnAdd.setOnClickListener(this);
}

@Override
public void onResume() {

    super.onResume();
    refreshScreen();
}

private void refreshScreen() {
    //Call to server to grab list of student records. this is a asyn
    Call<List<Chef>> call = restService.getService().getChef();
    call.enqueue(new Callback<List<Chef>>() {
                     @Override
                     public void onResponse(Call<List<Chef>> call, Response<List<Chef>> response) {
                         ListView lv = (ListView) findViewById(R.id.listchef);

                         ChefAdapter customAdapter = new ChefAdapter(ChefActivity.this,
                                                                              R.layout.view_mesa_entry, response.body());

                         lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                             @Override
                             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                 chef_Id = (TextView) view.findViewById(R.id.chef_Id);
                                 String client_id = chef_Id.getText().toString();
                                 Intent objIndent = new Intent(getApplicationContext(), DetalleChefActivity.class);
                                 objIndent.putExtra("chef_Id", Integer.parseInt(client_id));
                                 startActivity(objIndent);
                             }
                         });
                         lv.setAdapter(customAdapter);
                     }

                     @Override
                     public void onFailure(Call<List<Chef>> call, Throwable t) {
                         Toast.makeText(ChefActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

                     }
                 }
    );

}

@Override
public void onClick(View view) {
    if (view == btnAdd) {

        Intent intent = new Intent(this, DetalleChefActivity.class);
        intent.putExtra("chef_Id", 0);
        startActivity(intent);
    }
}
}
