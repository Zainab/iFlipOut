package com.out.iflipout;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class LoginScreen extends Activity implements OnClickListener {
	private EditText username;
	private EditText password;
	private Button submit;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		initVariables();
	}

	private void initVariables(){
		username = (EditText)findViewById(R.id.username);
		password = (EditText)findViewById(R.id.password);

	}

	public void onClick(View view) {

	}


}
