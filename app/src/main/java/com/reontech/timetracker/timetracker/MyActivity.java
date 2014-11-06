package com.reontech.timetracker.timetracker;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
import java.util.Scanner;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MyActivity extends Activity {
    EditText loginField;
    Button loginButton,cancelButton,assignButton,logoutButton;
    String baseurl = "http://timetracker.is/testcompany/user/";
    TextView welcome;
    RelativeLayout loginscreen,logoutscreen;
    Spinner projectSpinner,assignmentSpinner;
    TrackerObjects trackerObj;
    Chronometer chrono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_assign);

        loginscreen = (RelativeLayout)findViewById(R.id.login_screen);
        logoutscreen = (RelativeLayout)findViewById(R.id.screen_logout);
        welcome = (TextView)findViewById(R.id.welcome);
        projectSpinner = (Spinner)findViewById(R.id.project);
        assignmentSpinner = (Spinner)findViewById(R.id.assignment);
        chrono = (Chronometer)findViewById(R.id.timecounter);

        loginField = (EditText)findViewById(R.id.login);
        loginButton= (Button)findViewById(R.id.fetch_kennitala);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = baseurl+loginField.getText().toString();
                new LogIn().execute(url);


            }
        });
        cancelButton = (Button)findViewById(R.id.back_out);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MyActivity.this, "Eigðu góðan dag "+trackerObj.getUser().getName(), Toast.LENGTH_LONG).show();
                loginscreen.setVisibility(View.VISIBLE);


            }
        });
        assignButton = (Button)findViewById(R.id.login_button);
        assignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutscreen.setVisibility(View.VISIBLE);
                chrono.setBase(SystemClock.elapsedRealtime());
                chrono.start();


            }
        });
        logoutButton = (Button)findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutscreen.setVisibility(View.GONE);
                chrono.stop();
                Toast.makeText(MyActivity.this, trackerObj.getUser().getName()+" hefur unnið "+chrono.getText()+" tíma", Toast.LENGTH_LONG).show();


            }
        });


    }

    public void fillSpinners(){

        welcome.setText("Velkomin/n "+trackerObj.getUser().getName());
        ArrayAdapter<String> project_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, trackerObj.getUser().getTasks());
        projectSpinner.setAdapter(project_adapter);
        projectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> assign_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, trackerObj.getUser().getProject());
        assignmentSpinner.setAdapter(assign_adapter);
        assignmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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


    public TrackerObjects parseTracker(String url) {
        InputStream source = retrieveStream(url);
        Reader reader = new InputStreamReader(source);
        Gson gson1 = new Gson();
        Type collectionType = new TypeToken<TrackerObjects>() {}.getType();
        TrackerObjects details = gson1.fromJson(reader, collectionType);

        return details;
    }

    private class LogIn extends AsyncTask<String, Void, TrackerObjects> {

        @Override
        protected void onPostExecute(TrackerObjects params) {

            trackerObj = params;
            loginscreen.setVisibility(View.GONE);
            fillSpinners();
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(loginField.getWindowToken(), 0);

        }

        @Override
        protected TrackerObjects doInBackground(String... params) {
            // TODO Auto-generated method stub

            //isJson = isJSONValid("http://www.json-generator.com/api/json/get/crOilFKrvS?indent=2");
            boolean hasNetwork = haveNetworkConnection();
            TrackerObjects result = null;
            if(hasNetwork){

                result = parseTracker(params[0]);
            }
            return result;
        }
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    public boolean isJSONValid(String test)
    {
        InputStream source = retrieveStream(test);
        String inputString = new Scanner(source, "UTF-8").useDelimiter("\\A").next();
        boolean mix = inputString.substring(0, 1).equals("[") || inputString.substring(0, 2).equals("[{") || inputString.substring(0, 2).equals("[\"") || inputString.substring(0, 3).equals("[ \"");
        return mix;
    }

    //setur url inn í parseTopItems og fyllir database með gögnunum
    private InputStream retrieveStream(String url) {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet getRequest = new HttpGet(url);
        try {
            HttpResponse getResponse = client.execute(getRequest);
            final int statusCode = getResponse.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                Log.w(getClass().getSimpleName(), "Error " + statusCode
                        + " for URL " + url);
                return null;
            }
            HttpEntity getResponseEntity = getResponse.getEntity();
            return getResponseEntity.getContent();
        } catch (IOException e) {
            getRequest.abort();
            Log.w(getClass().getSimpleName(), "Error for URL " + url, e);
        }
        return null;
    }
}
