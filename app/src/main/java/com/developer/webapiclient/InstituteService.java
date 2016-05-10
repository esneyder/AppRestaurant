package com.developer.webapiclient;


/**
 * Created by Tan on 6/26/2015.
 */

import com.developer.webapiclient.modelo.DetalleFactura;
import com.developer.webapiclient.modelo.Table;
import com.developer.webapiclient.modelo.TikectFinal;
import com.developer.webapiclient.modelo.Waiter;
import com.developer.webapiclient.modelo.Chef;
import com.developer.webapiclient.modelo.Client;
import com.developer.webapiclient.modelo.Facturas;
import com.developer.webapiclient.modelo.Student;
import com.developer.webapiclient.modelo.Tikect;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface InstituteService {

    @GET("institute/Students")
    Call<List<Student>> getStudent();

    @GET("institute/Students/{id}")
    Call<Student> getStudentById(@Path("id") Integer id);


    @DELETE("institute/Students/{id}")
    Call<Student> deleteStudentById(@Path("id") Integer id);


    @PUT("institute/Students/{id}")
    Call<Student> updateStudentById(@Path("id") Integer id,@Body Student student);

    @POST("institute/Students")
    Call<Student> addStudent(@Body Student student);

//____________________________________________________________


@GET("institute/Clientes")
Call<List<Client>> getclient();

@GET("institute/Clientes/{id}")
Call<Client> getClientById(@Path("id") Integer id);

@DELETE("institute/Clientes/{id}")
Call<Client> deleteClientById(@Path("id") Integer id);

@PUT("institute/Clientes/{id}")
Call<Client> updateClientById(@Path("id") Integer id,@Body Client client);

@POST("institute/Clientes")
Call<Client> addClient(@Body Client client);

//______________________________________________________

@GET("institute/Mesas")
Call<List<Table>> getTable();

@GET("institute/Mesas/{id}")
Call<Table> getTableById(@Path("id") Integer id);

@DELETE("institute/Mesas/{id}")
Call<Table> deleteTableById(@Path("id") Integer id);

@PUT("institute/Mesas/{id}")
Call<Table> updateTableById(@Path("id") Integer id,@Body Table table);

@POST("institute/Mesas")
Call<Table> addTable(@Body Table table);

//______________________________________________________

@GET("institute/Cocineroes")
Call<List<Chef>> getChef();

@GET("institute/Cocineroes/{id}")
Call<Chef> getChefById(@Path("id") Integer id);

@DELETE("institute/Cocineroes/{id}")
Call<Chef> deleteChefById(@Path("id") Integer id);

@PUT("institute/Cocineroes/{id}")
Call<Chef> updateChefById(@Path("id") Integer id,@Body Chef chef);

@POST("institute/Cocineroes")
Call<Chef> addChef(@Body Chef chef);


//______________________________________________________

@GET("institute/Camareroes")
Call<List<Waiter>> getWaiter();

@GET("institute/Camareroes/{id}")
Call<Waiter> getWaiterById(@Path("id") Integer id);

@DELETE("institute/Camareroes/{id}")
Call<Waiter> deleteWaiterById(@Path("id") Integer id);

@PUT("institute/Camareroes/{id}")
Call<Waiter> updateWaiterById(@Path("id") Integer id,@Body Waiter waiter);

@POST("institute/Camareroes")
Call<Waiter> addWaiter(@Body Waiter waiter);


//______________________________________________________

@GET("institute/Tikect")
Call<List<Tikect>> getFacturas();//consulta a tikect

@GET("institute/Facturas/{id}")
Call<Facturas> getFacturasById(@Path("id") Integer id);

@DELETE("institute/Facturas/{id}")
Call<Facturas> deleteFacturasById(@Path("id") Integer id);

@PUT("institute/Facturas/{id}")
Call<Facturas> updateFacturasById(@Path("id") Integer id,@Body Facturas facturas);

@POST("institute/Facturas")
Call<Facturas> addFacturas(@Body Facturas facturas);
///---------------------------------------------------


@GET("institute/Tikects")
Call<List<TikectFinal>> getDetalleFactura();//consulta a tikect

@GET("institute/DetalleFacturas/{id}")
Call<DetalleFactura> getDetalleFacturasById(@Path("id") Integer id);

@DELETE("institute/DetalleFacturas/{id}")
Call<DetalleFactura> deleteDetalleFacturasById(@Path("id") Integer id);

@PUT("institute/DetalleFacturas/{id}")
Call<DetalleFactura> updateDetalleFacturasById(@Path("id") Integer id,@Body DetalleFactura facturas);

@POST("institute/DetalleFacturas")
Call<DetalleFactura> addDetalleFacturas(@Body DetalleFactura facturas);


}
