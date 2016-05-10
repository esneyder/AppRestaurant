package com.developer.webapiclient.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.developer.webapiclient.R;
import com.developer.webapiclient.modelo.Waiter;

import com.developer.webapiclient.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleCamareroActivity extends AppCompatActivity
        implements android.view.View.OnClickListener {

Button btnSave, btnDelete;
Button btnClose;
EditText txtName;
EditText txtLasName;
private int _camarero_Id = 0;
RestClient restService;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    restService = new RestClient();
    setContentView(R.layout.activity_detalle_camarero);

    btnSave = (Button) findViewById(R.id.btnSave);
    btnDelete = (Button) findViewById(R.id.btnDelete);
    btnClose = (Button) findViewById(R.id.btnClose);
    txtName = (EditText) findViewById(R.id.txtnamecamarero_);
    txtLasName = (EditText) findViewById(R.id.txtCLastNameCamarero_);
    btnSave.setOnClickListener(this);
    btnDelete.setOnClickListener(this);
    btnClose.setOnClickListener(this);
    _camarero_Id = 0;
    Intent intent = getIntent();
    _camarero_Id = intent.getIntExtra("camarero_Id", 0);
    if (_camarero_Id > 0) {
        Call<Waiter> call = restService.getService().getWaiterById(_camarero_Id);
        call.enqueue(new Callback<Waiter>() {
            @Override
            public void onResponse(Call<Waiter> call, Response<Waiter> response) {
                Waiter table = response.body();
                txtName.setText(table.Nombres);
                txtLasName.setText(table.Apellidos);

            }

            @Override
            public void onFailure(Call<Waiter> call, Throwable t) {
                Toast.makeText(DetalleCamareroActivity.this,
                        t.getMessage().toString(), Toast.LENGTH_LONG).show();

            }
        });
    }
}


@Override
public void onClick(View v) {
    if (findViewById(R.id.btnDelete) == v) {

        Call<Waiter> call = restService.getService().deleteWaiterById(_camarero_Id);
        call.enqueue(new Callback<Waiter>() {
            @Override
            public void onResponse(Call<Waiter> call, Response<Waiter> response) {
                Toast.makeText(DetalleCamareroActivity.this, "Waiter Record Deleted", Toast.LENGTH_LONG).show();

            }
            @Override
            public void onFailure(Call<Waiter> call, Throwable t) {
                Toast.makeText(DetalleCamareroActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

            }
        });
        finish();
    } else if (v == findViewById(R.id.btnClose)) {
        finish();
    } else if (findViewById(R.id.btnSave) == v) {

        Waiter tables = new Waiter();
        Integer status = 0;
        tables.IdCamarero = _camarero_Id;
        tables.Nombres = txtName.getText().toString();
        tables.Apellidos = txtLasName.getText().toString();


        if (_camarero_Id == 0) {

            restService.getService().addWaiter(tables).enqueue(new Callback<Waiter>() {
                @Override
                public void onResponse(Call<Waiter> call, Response<Waiter> response) {
                    Toast.makeText(DetalleCamareroActivity.this, "New Waiter Inserted.",
                            Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Waiter> call, Throwable t) {
                    Toast.makeText(DetalleCamareroActivity.this, t.getMessage().toString(),
                            Toast.LENGTH_LONG).show();
                }
            });


        } else {
            Call<Waiter> call = restService.getService().updateWaiterById(_camarero_Id, tables);
            call.enqueue(new Callback<Waiter>() {
                @Override
                public void onResponse(Call<Waiter> call, Response<Waiter> response) {
                    Toast.makeText(DetalleCamareroActivity.this, "Waiter Record updated.", Toast.LENGTH_LONG).show();
                    finish();
                }

                @Override
                public void onFailure(Call<Waiter> call, Throwable t) {
                    Toast.makeText(DetalleCamareroActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });

        }
    }
}
}


