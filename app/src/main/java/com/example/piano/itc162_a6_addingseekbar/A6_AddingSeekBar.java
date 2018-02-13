package com.example.piano.itc162_a6_addingseekbar;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class A6_AddingSeekBar extends Activity
        implements TextView.OnEditorActionListener, SeekBar.OnSeekBarChangeListener {
    //define variable for the widgets-input
    private EditText inputNumber;
    private Button clearButton;
    //define variable for the widgets-output
    private TextView displyResult;

    // define the Shared Preferences object
    private SharedPreferences saveInput;

    // define instance variables that should keep
    private String inputString = "";

    // define seekbar instance variable
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a6__adding_seek_bar);

        // get reference to the widgets - input
        inputNumber = (EditText) findViewById(R.id.inputNumber);
        clearButton = (Button)findViewById(R.id.clearButton);
        // get reference to the widgets-ouput
        displyResult = (TextView) findViewById(R.id.displyResult);

        //get seekbar references to widgets
        seekBar = (SeekBar)findViewById(R.id.seekBar);

        //set seekbar listener
        seekBar.setOnSeekBarChangeListener(this);

        //set the listeners
        inputNumber.setOnEditorActionListener(this);
        //get shareprefeences object
        saveInput = getSharedPreferences("saveInput", MODE_PRIVATE);


    }
    @Override
    public void onPause(){
        super.onPause();
        //save the instance variables
        SharedPreferences.Editor editor = saveInput.edit();
        editor.putString("inputString", inputString);
        editor.commit();
    }
    @Override
    public void onResume(){
        super.onResume();
        //get instance values
        inputString = saveInput.getString("inputString", "");
        inputNumber.setText(inputString);
        // to converDegreeThenDisplay
        converDegreeThenDisplay();

    }
    private void converDegreeThenDisplay() {
        //get the inputNumber value
        inputString = inputNumber.getText().toString();
        float turnInputNumberToFloat;
        if (inputString.equals("")){
            turnInputNumberToFloat = 0;
        }
        else{
            turnInputNumberToFloat = Float.parseFloat(inputString);
        }
        // f2c convert into formula  (f-32)*5/9
        float f2c = (turnInputNumberToFloat-32)*5/9;

        // display the result C        
        NumberFormat degreeC = NumberFormat.getNumberInstance();
        displyResult.setText(degreeC.format(f2c)+ " \u00b0C");

        // display the instance input F
        NumberFormat degreeF = NumberFormat.getNumberInstance();
        inputNumber.setText(degreeF.format(turnInputNumberToFloat)+ " \u00b0F");
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_DONE ||
                i == EditorInfo.IME_ACTION_UNSPECIFIED){
            converDegreeThenDisplay();
        }
        return false;
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        Toast.makeText(getApplicationContext(),"Input Degree Fahrenheit of: " + i, Toast.LENGTH_SHORT).show();
        inputNumber.setText(i + " \u00b0F");
        displyResult.setText((i-32)*5/9 + " \u00b0C");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        Toast.makeText(getApplicationContext(),"seekbar touch started!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int i = seekBar.getProgress();
        Toast.makeText(getApplicationContext(),"seekbar touch stopped!", Toast.LENGTH_SHORT).show();
        if (i >= 32) {
            Toast.makeText(getApplicationContext(), "Cool", Toast.LENGTH_SHORT).show();
        }
        if (i <= 87){
            Toast.makeText(getApplicationContext(), "Hot", Toast.LENGTH_LONG).show();
        }

    }

    //for the clear button
    public void Clear(View clear){
        inputNumber.setText("");
        displyResult.setText("");
    }
}
