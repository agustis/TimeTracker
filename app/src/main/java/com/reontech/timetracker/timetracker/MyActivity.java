package com.reontech.timetracker.timetracker;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import Objects.TrackerObjects;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Scanner;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;




public class MyActivity extends Activity implements AdapterView.OnItemSelectedListener {
    Spinner projectSpinner,assignmentSpinner;
    EditText loginField;
    Button loginButton;
    String baseurl = "http://timetracker.olafura.com/reontech/user/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);


        loginField = (EditText)findViewById(R.id.login);
        loginButton= (Button)findViewById(R.id.fetch_kennitala);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = baseurl+loginField.getText().toString();
                asynctask(url);
            }
        });
        /*
        projectSpinner = (Spinner)findViewById(R.id.project);
        assignmentSpinner = (Spinner)findViewById(R.id.assignment);

        ArrayAdapter project_adapter = ArrayAdapter.createFromResource(this,R.array.project,android.R.layout.simple_spinner_item);
        projectSpinner.setAdapter(project_adapter);
        projectSpinner.setOnItemSelectedListener(this);

        ArrayAdapter assign_adapter = ArrayAdapter.createFromResource(this,R.array.assignment,android.R.layout.simple_spinner_item);
        assignmentSpinner.setAdapter(assign_adapter);
        assignmentSpinner.setOnItemSelectedListener(this); "@+id/login"*/

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        TextView myText = (TextView) view;
        Toast.makeText(this,"you selected "+myText, Toast.LENGTH_SHORT).show();

    }

    public void onNothingSelected(AdapterView<?> adapterView){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
