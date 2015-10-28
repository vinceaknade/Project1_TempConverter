package com.beech.project1_tempconverter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.InputMismatchException;

public class TempConverterActivity
        extends AppCompatActivity
        implements TextView.OnEditorActionListener {

    //test
    //controls
    private EditText txtFahrenheit;
    private TextView lblResult;

    //Shared Preferences
    private SharedPreferences savedValues;

    //globals
    private String strInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_converter);

        //get controls
        txtFahrenheit = (EditText)findViewById(R.id.txtFahrenheit);
        lblResult = (TextView)findViewById(R.id.lblResult);

        //establish listeners
        txtFahrenheit.setOnEditorActionListener(this);

        //get shared Preferences object
        savedValues = getSharedPreferences("savedValues", MODE_PRIVATE);

        //load string object
        strInput = txtFahrenheit.getText().toString();;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        strInput = txtFahrenheit.getText().toString();;
        //calculate
        calculateAndDisplay();

        return false;
    }

    private void calculateAndDisplay() {
        float f,c = 0f;

        //parse data
        try
        {
            f = Float.parseFloat(strInput);
        }
        catch(NumberFormatException nfe)
        {
            f = 0f;
        }

        //convert
        c = (f-32) * 5/9;

        //update
        updateUI(c);
    }

    private void updateUI(float c) {
        //display result
        lblResult.setText("" + c);
    }

    @Override
    protected void onPause() {
        saveInstance();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //retrieve instance variables
        strInput = savedValues.getString("strInput", "");

        //apply bill amount to field
        txtFahrenheit.setText(strInput);

        //update
        calculateAndDisplay();
    }

    //save instance variables
    private void saveInstance() {

        //access file editor
        SharedPreferences.Editor editor = savedValues.edit();

        //write to file
        editor.putString("strInput", strInput);

        //commit changes
        editor.commit();
    }
}
