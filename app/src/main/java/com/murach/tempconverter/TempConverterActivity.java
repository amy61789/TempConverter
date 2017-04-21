package com.murach.tempconverter;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;


public class TempConverterActivity extends Activity
implements TextView.OnEditorActionListener {


	//create instance variables
	private EditText fahrenheitEditText;
	private TextView celciusTextView;
	private SharedPreferences savedValues;
	private String fahrenheitString;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_temp_converter);

		fahrenheitEditText = (EditText) findViewById(R.id.fahrenheitEditText);
		celciusTextView = (TextView) findViewById(R.id.celciusTextView);

		fahrenheitEditText.setOnEditorActionListener(this);

		savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
	}

	@Override
	public void onPause() {
		SharedPreferences.Editor editor = savedValues.edit();
		editor.putString("fahrenheitString", fahrenheitString);
		editor.commit();

		super.onPause();
	}

	@Override
	public void onResume(){
		super.onResume();
		fahrenheitString = savedValues.getString("fahrenheitString", "");
		fahrenheitEditText.setText(fahrenheitString);
		calculateAndDisplay();
	}

	private void calculateAndDisplay(){
		fahrenheitString = fahrenheitEditText.getText().toString();

		float fahrenheit;
//
//		if(fahrenheitString.equals("")){
//			fahrenheit = 0;
//		}else{
			fahrenheit = Float.parseFloat(fahrenheitString);
//		}

		float celcius = (fahrenheit - 32) * 5/9;


		//U+00B0
		DecimalFormat df = new DecimalFormat("#.##");
		celciusTextView.setText(df.format(celcius));

	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
		if(actionId == EditorInfo.IME_ACTION_DONE ||
				actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
			calculateAndDisplay();
		}
		return false;
	}
}