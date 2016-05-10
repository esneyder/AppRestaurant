package com.developer.webapiclient.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.developer.webapiclient.R;
import com.developer.webapiclient.RestClient;
import com.developer.webapiclient.modelo.Client;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClienteDetailtActivity extends
        AppCompatActivity implements android.view.View.OnClickListener {

Button btnSave ,  btnDelete;
Button btnClose;
EditText txtName;
EditText txtLastname;
EditText txtNote;
private int _client_Id =0;
RestClient restService;
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    restService = new RestClient();
    setContentView(R.layout.activity_cliente_detailt);

    btnSave = (Button) findViewById(R.id.btnSave);
    btnDelete = (Button) findViewById(R.id.btnDelete);
    btnClose = (Button) findViewById(R.id.btnClose);

    txtName = (EditText) findViewById(R.id.txtCName);
    txtLastname = (EditText) findViewById(R.id.txtCLastName);
    txtNote = (EditText) findViewById(R.id.txtNote);

    btnSave.setOnClickListener(this);
    btnDelete.setOnClickListener(this);
    btnClose.setOnClickListener(this);

    _client_Id =0;
    Intent intent = getIntent();
    _client_Id =intent.getIntExtra("client_Id", 0);
    if (_client_Id >0){
        Call<Client> call = restService.getService().getClientById(_client_Id);
        call.enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                Client client =response.body();
                txtName.setText(client.Nombres);
                txtLastname.setText(client.Apellidos);
                txtNote.setText(client.Nota);
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                Toast.makeText(ClienteDetailtActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

            }
        });


    }
}



@Override
public void onClick(View v) {
    if (findViewById(R.id.btnDelete)==v){

        Call<Client> call = restService.getService().deleteClientById(_client_Id);
        call.enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                Toast.makeText(ClienteDetailtActivity.this, "Client Record Deleted", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                Toast.makeText(ClienteDetailtActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

            }
        });
        finish();
    }else if (v== findViewById(R.id.btnClose)){
        finish();
    }else if (findViewById(R.id.btnSave)==v){

        Client client=new Client();
        Integer status =0;
        client.IdCliente = _client_Id;
        client.Nombres= txtName.getText().toString();
        client.Apellidos=txtLastname.getText().toString();
        client.Nota= txtNote.getText().toString();


        if (_client_Id == 0) {

            restService.getService().addClient(client).enqueue(new Callback<Client>() {
                @Override
                public void onResponse(Call<Client> call, Response<Client> response) {
                    Toast.makeText(ClienteDetailtActivity.this, "New Client Inserted.", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Client> call, Throwable t) {
                    Toast.makeText(ClienteDetailtActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });


        }else{
            Call<Client> call = restService.getService().updateClientById(_client_Id,client);
            call.enqueue(new Callback<Client>() {
                @Override
                public void onResponse(Call<Client> call, Response<Client> response) {
                    Toast.makeText(ClienteDetailtActivity.this, "Client Record updated.", Toast.LENGTH_LONG).show();
                    finish();
                }

                @Override
                public void onFailure(Call<Client> call, Throwable t) {
                    Toast.makeText(ClienteDetailtActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });



        }
    }
}
}
