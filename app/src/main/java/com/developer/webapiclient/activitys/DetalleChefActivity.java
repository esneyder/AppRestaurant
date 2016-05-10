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
import com.developer.webapiclient.modelo.Chef;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleChefActivity extends AppCompatActivity
        implements android.view.View.OnClickListener {

Button btnSave, btnDelete;
Button btnClose;
EditText txtName;
EditText txtLasName;
private int _chef_Id = 0;
RestClient restService;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    restService = new RestClient();
    setContentView(R.layout.activity_detalle_chef);
    btnSave = (Button) findViewById(R.id.btnSave);
    btnDelete = (Button) findViewById(R.id.btnDelete);
    btnClose = (Button) findViewById(R.id.btnClose);
    txtName = (EditText) findViewById(R.id.txtName_);
    txtLasName = (EditText) findViewById(R.id.txtCLastName_);

    btnSave.setOnClickListener(this);
    btnDelete.setOnClickListener(this);
    btnClose.setOnClickListener(this);

    _chef_Id = 0;
    Intent intent = getIntent();
    _chef_Id = intent.getIntExtra("chef_Id", 0);
    if (_chef_Id > 0) {
        Call<Chef> call = restService.getService().getChefById(_chef_Id);
        call.enqueue(new Callback<Chef>() {
            @Override
            public void onResponse(Call<Chef> call, Response<Chef> response) {
                Chef table = response.body();
                txtName.setText(table.Nombres);
                txtLasName.setText(table.Apellidos);

            }

            @Override
            public void onFailure(Call<Chef> call, Throwable t) {
                Toast.makeText(DetalleChefActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

            }
        });
    }
}


@Override
public void onClick(View v) {
    if (findViewById(R.id.btnDelete) == v) {

        Call<Chef> call = restService.getService().deleteChefById(_chef_Id);
        call.enqueue(new Callback<Chef>() {
            @Override
            public void onResponse(Call<Chef> call, Response<Chef> response) {
                Toast.makeText(DetalleChefActivity.this, "Chef Record Deleted", Toast.LENGTH_LONG).show();

            }
            @Override
            public void onFailure(Call<Chef> call, Throwable t) {
                Toast.makeText(DetalleChefActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

            }
        });
        finish();
    } else if (v == findViewById(R.id.btnClose)) {
        finish();
    } else if (findViewById(R.id.btnSave) == v) {

        Chef tables = new Chef();
        Integer status = 0;
        tables.IdCocinero = _chef_Id;
        tables.Nombres = txtName.getText().toString();
        tables.Apellidos = txtLasName.getText().toString();


        if (_chef_Id == 0) {

            restService.getService().addChef(tables).enqueue(new Callback<Chef>() {
                @Override
                public void onResponse(Call<Chef> call, Response<Chef> response) {
                    Toast.makeText(DetalleChefActivity.this, "New Chef Inserted.", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Chef> call, Throwable t) {
                    Toast.makeText(DetalleChefActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });


        } else {
            Call<Chef> call = restService.getService().updateChefById(_chef_Id, tables);
            call.enqueue(new Callback<Chef>() {
                @Override
                public void onResponse(Call<Chef> call, Response<Chef> response) {
                    Toast.makeText(DetalleChefActivity.this, "Chef Record updated.", Toast.LENGTH_LONG).show();
                    finish();
                }

                @Override
                public void onFailure(Call<Chef> call, Throwable t) {
                    Toast.makeText(DetalleChefActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });

        }
    }
}
}


