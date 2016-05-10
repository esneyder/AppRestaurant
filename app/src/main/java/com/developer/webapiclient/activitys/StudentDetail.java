package com.developer.webapiclient.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.developer.webapiclient.R;
import com.developer.webapiclient.modelo.Student;

import com.developer.webapiclient.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Tan on 3/4/2016.
 */

public class StudentDetail extends AppCompatActivity implements android.view.View.OnClickListener{

    Button btnSave ,  btnDelete;
    Button btnClose;
    EditText editTextName;
    EditText editTextEmail;
    EditText editTextAge;
    private int _Student_Id=0;
    RestClient restService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restService = new RestClient();
        setContentView(R.layout.activity_student_detail);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnClose = (Button) findViewById(R.id.btnClose);

        editTextName = (EditText) findViewById(R.id.txtName);
        editTextEmail = (EditText) findViewById(R.id.txtEmail);
        editTextAge = (EditText) findViewById(R.id.editTextAge);

        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);


        _Student_Id =0;
        Intent intent = getIntent();
        _Student_Id =intent.getIntExtra("student_Id", 0);
        if (_Student_Id>0){
            Call<Student> call = restService.getService().getStudentById(_Student_Id);
            call.enqueue(new Callback<Student>() {
                @Override
                public void onResponse(Call<Student> call, Response<Student> response) {
                    Student student =response.body();
                    editTextAge.setText(String.valueOf(student.password));
                    editTextName.setText(student.Name);
                    editTextEmail.setText(student.Email);
                }

                @Override
                public void onFailure(Call<Student> call, Throwable t) {
                    Toast.makeText(StudentDetail.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

                }
            });


        }
    }



    @Override
    public void onClick(View v) {
        if (findViewById(R.id.btnDelete)==v){

            Call<Student> call = restService.getService().deleteStudentById(_Student_Id);
            call.enqueue(new Callback<Student>() {
                @Override
                public void onResponse(Call<Student> call, Response<Student> response) {
                    Toast.makeText(StudentDetail.this, "Student Record Deleted", Toast.LENGTH_LONG).show();

                }

                @Override
                public void onFailure(Call<Student> call, Throwable t) {
                    Toast.makeText(StudentDetail.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();

                }
            });
            finish();
        }else if (v== findViewById(R.id.btnClose)){
            finish();
        }else if (findViewById(R.id.btnSave)==v){

            Student student=new Student();
            Integer status =0;
            student.Email= editTextEmail.getText().toString();
            student.Name=editTextName.getText().toString();
            student.password = editTextAge.getText().toString();
            student.Id = _Student_Id;

            if (_Student_Id == 0) {

                restService.getService().addStudent(student).enqueue(new Callback<Student>() {
                    @Override
                    public void onResponse(Call<Student> call, Response<Student> response) {
                        Toast.makeText(StudentDetail.this, "New Student Inserted.", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<Student> call, Throwable t) {
                        Toast.makeText(StudentDetail.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });


            }else{
                Call<Student> call = restService.getService().updateStudentById(_Student_Id,student);
                call.enqueue(new Callback<Student>() {
                    @Override
                    public void onResponse(Call<Student> call, Response<Student> response) {
                        Toast.makeText(StudentDetail.this, "Student Record updated.", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<Student> call, Throwable t) {
                        Toast.makeText(StudentDetail.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });


            }
        }
    }
}

