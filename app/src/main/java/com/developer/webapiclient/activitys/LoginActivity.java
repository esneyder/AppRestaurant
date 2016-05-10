package com.developer.webapiclient.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.developer.webapiclient.R;
import com.developer.webapiclient.RestClient;
import com.developer.webapiclient.utl.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
Button login;
EditText usuario;
EditText password;
RestClient restService;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    restService = new RestClient();
    login = (Button) findViewById(R.id.btningresar);
    usuario=(EditText) findViewById(R.id.txtusuariologin);
    password=(EditText) findViewById(R.id.txtpasswordlogin);
    login.setOnClickListener(this);

}

@Override
public void onClick(View view) {
    if (view == login) {
        startActivity(new Intent(this,HomeActivity.class));
        //ValidarAcceso(); habilitar para hacer el login con la base de datos
    }
}

private void ValidarAcceso() {
    //Creating a string request
    StringRequest stringRequest = new StringRequest(Config.dataUrl + "Students",
                                                           new com.android.volley.Response.Listener<String>() {
                                                               @Override
                                                               public void onResponse(String response) {
                                                                   JSONArray result;
                                                                   try {
                                                                       result = new JSONArray(response);
                                                                       Login(result);
                                                                   } catch (JSONException e) {
                                                                       e.printStackTrace();
                                                                   }
                                                               }
                                                           },
                                                           new com.android.volley.Response.ErrorListener() {
                                                               @Override
                                                               public void onErrorResponse(VolleyError error) {

                                                               }
                                                           });

    //Creating a request queue
    RequestQueue requestQueue = Volley.newRequestQueue(this);

    //Adding request to the queue
    requestQueue.add(stringRequest);

}

private void Login(JSONArray j) {
    //Traversing through all the items in the json array
    for (int i = 0; i < j.length(); i++) {
        try {
            //Getting json object
            JSONObject json = j.getJSONObject(i);
            String musuario=json.getString("Email");
            String mpassword= json.getString("password");
            String datousuario= String.valueOf(usuario.getText());
            String datopassword=String.valueOf(password.getText());
            if ( musuario==datousuario && mpassword==datopassword) {
                Toast.makeText(LoginActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,HomeActivity.class));
            } else {
            Toast.makeText(LoginActivity.this, "Error, datos incorrectos", Toast.LENGTH_SHORT).show();
        }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
}
