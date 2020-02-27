package com.example.college_management;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class Configurations {

    private  String domain = "https://becolacindoorpositioning.000webhostapp.com/";
    private  String sub_root = domain+"Becolac/";
    private  String register_api = sub_root+"register.php";
    private  String login_api = sub_root+"login.php?";
    private  String addbecon_api = sub_root+"addbecon.php";
    private  Context context;
    ProgressDialog progressDialog;
    SQLiteDatabase db;

    public void addUser(final Context context, final String name, final String mail, final String mob, final String pass, final String con_pass){
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Registering...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, register_api+"?Name="+name+"&Email="+mail+"&Mobile="+mob+"&Password="+pass+"&Confirm_Pass="+con_pass, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    if(response.equals("Success")){
                        progressDialog.dismiss();
                        Toast.makeText(context, "Registered Successfully", Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(context, Login.class));
                    }else{
                        Log.d("Response", response);
                        progressDialog.dismiss();
                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Log.d("Exception", e.toString());
                    progressDialog.dismiss();
                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d("Error", error.toString());
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public void login(final Context context, String name, String pass){
        this.context = context;

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Validating User...");
        progressDialog.setCancelable(false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, login_api+"Name="+name+"&Pass="+pass, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    if(response.equals("Success")){
                        progressDialog.dismiss();
                        db = context.openOrCreateDatabase("loginStatus", context.MODE_PRIVATE, null);
                        db.execSQL("Insert into Tables(Status)values('1')");
                        db.close();
                        Toast.makeText(context, "Login Successfull", Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(context, HomePage.class));

                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show();
                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public void addBecons(final Context context, final String name, final String place){
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Adding Becons...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, addbecon_api+"?Name="+name+"&Place="+place, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    if(response.equals("Success")){
                        progressDialog.dismiss();
                        Toast.makeText(context, "Registered Successfully", Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(context, Login.class));
                    }else{
                        Log.d("Response", response);
                        progressDialog.dismiss();
                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Log.d("Exception", e.toString());
                    progressDialog.dismiss();
                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d("Error", error.toString());
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
