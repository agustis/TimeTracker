package com.reontech.timetracker.timetracker;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MyActivity extends Activity implements AdapterView.OnItemSelectedListener {
    Spinner projectSpinner,assignmentSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_assign);

        projectSpinner = (Spinner)findViewById(R.id.project);
        assignmentSpinner = (Spinner)findViewById(R.id.assignment);

        ArrayAdapter project_adapter = ArrayAdapter.createFromResource(this,R.array.project,android.R.layout.simple_spinner_item);
        projectSpinner.setAdapter(project_adapter);
        projectSpinner.setOnItemSelectedListener(this);

        ArrayAdapter assign_adapter = ArrayAdapter.createFromResource(this,R.array.assignment,android.R.layout.simple_spinner_item);
        assignmentSpinner.setAdapter(assign_adapter);
        assignmentSpinner.setOnItemSelectedListener(this);

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
