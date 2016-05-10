package com.developer.webapiclient.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.developer.webapiclient.R;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
private Button btnuser;
private Button btncliente;
private Button btnmesa;
private Button btncherf;
private Button btncamarero;
private Button btnfacturas;
private Button btndetallefacrturas;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    btnuser = (Button) findViewById(R.id.btnUsuarios);
    btncliente = (Button) findViewById(R.id.btnclientes);
    btncherf = (Button) findViewById(R.id.btnChef);
    btnmesa = (Button) findViewById(R.id.btnMesas);
    btncamarero = (Button) findViewById(R.id.btnCamarero);
    btnfacturas = (Button) findViewById(R.id.btnFacturas);
    btndetallefacrturas = (Button) findViewById(R.id.btndetallefacturas);

    btnuser.setOnClickListener(this);
    btncliente.setOnClickListener(this);
    btnmesa.setOnClickListener(this);
    btncherf.setOnClickListener(this);
    btncamarero.setOnClickListener(this);
    btnfacturas.setOnClickListener(this);
    btndetallefacrturas.setOnClickListener(this);
}

@Override
public void onClick(View view) {
    if (view == btnuser) {
        startActivity(new Intent(this, MainActivity.class));
    }
    if (view == btncliente) {
        startActivity(new Intent(this, ClientActivity.class));
    }
    if (view==btnmesa){
        startActivity(new Intent(this,MesaActivity.class));
    }
    if (view==btncherf){
        startActivity(new Intent(this,ChefActivity.class));
    }
    if (view==btncamarero){
        startActivity(new Intent(this,CamareroActivity.class));
    }
    if (view==btnfacturas){
        startActivity(new Intent(this,FacturasActivity.class));
    }
    if (view==btndetallefacrturas){
        startActivity(new Intent(this,TikectActivity.class));
    }
}
}
