package com.example.openfragmentdemo.activitybase;

import com.example.openfragmentdemo.R;

import android.app.Activity;
import android.os.Bundle;

public class A extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_frag);
	}
}
