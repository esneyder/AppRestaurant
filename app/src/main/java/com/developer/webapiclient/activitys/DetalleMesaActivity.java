package com.developer.webapiclient.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.developer.webapiclient.R;
import com.developer.webapiclient.modelo.Table;

import com.developer.webapiclient.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleMesaActivity extends
        AppCompatActivity implements android.view.View.OnClickListener {

Button btnSave ,  btnDelete;
Button btnClose;
EditText txtNumComensales;
EditText txtUbicacion;
private int _mesa_Id =0;
RestClient restService;
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    restService = new RestClient();
    setContentView(R.layout.activity_detalle_mesa);
    btnSave = (Button) findViewById(R.id.btnSave);
    btnDelete = (Button) findViewById(R.id.btnDelete);
    btnClose = (Button) findViewById(R.id.btnClose);

    txtNumComensales = (EditText) findViewById(R.id.txtNumComensales);
    txtUbicacion = (EditText) findViewById(R.id.txtUbicacion);

    btnSave.setOnClickListener(this);
    btnDelete.setOnClickListener(this);
    btnClose.setOnClickListener(this);

    _mesa_Id =0;
    Intent intent = getIntent();
    _mesa_Id =intent.getIntExtra("mesa_Id", 0);
    if (_mesa_Id >0){
        Call<Table> call = restService.getService().getTableById(_mesa_Id);
        call.enqueue(new Callback<Table>() {
            @Override
            public void onResponse(Call<Table> call, Response<Table> response) {
                Table table =response.body();
                txtNumComensales.setText(table.NumMaxComensales +"");
                txtUbicacion.setText(table.Ubicacion);

            }

            @Override
            public void onFailure(Call<Table> call, Throwable t) {
                Toast.makeText(DetalleMesaActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

            }
        });


    }
}


@Override
public void onClick(View v) {
    if (findViewById(R.id.btnDelete)==v){

        Call<Table> call = restService.getService().deleteTableById(_mesa_Id);
        call.enqueue(new Callback<Table>() {
            @Override
            public void onResponse(Call<Table> call, Response<Table> response) {
                Toast.makeText(DetalleMesaActivity.this, "Table Record Deleted", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Table> call, Throwable t) {
                Toast.makeText(DetalleMesaActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

            }
        });
        finish();
    }else if (v== findViewById(R.id.btnClose)){
        finish();
    }else if (findViewById(R.id.btnSave)==v){

        Table tables=new Table();
        Integer status =0;
        tables.IdMesa = _mesa_Id;
        tables.NumMaxComensales= Integer.parseInt(txtNumComensales.getText().toString());
        tables.Ubicacion=txtUbicacion.getText().toString();


        if (_mesa_Id == 0) {

            restService.getService().addTable(tables).enqueue(new Callback<Table>() {
                @Override
                public void onResponse(Call<Table> call, Response<Table> response) {
                    Toast.makeText(DetalleMesaActivity.this, "New Table Inserted.", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Table> call, Throwable t) {
                    Toast.makeText(DetalleMesaActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });


        }else{
            Call<Table> call = restService.getService().updateTableById(_mesa_Id,tables);
            call.enqueue(new Callback<Table>() {
                @Override
                public void onResponse(Call<Table> call, Response<Table> response) {
                    Toast.makeText(DetalleMesaActivity.this, "Table Record updated.", Toast.LENGTH_LONG).show();
                    finish();
                }

                @Override
                public void onFailure(Call<Table> call, Throwable t) {
                    Toast.makeText(DetalleMesaActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });



        }
    }
}
}

