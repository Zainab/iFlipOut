package com.out.veritra;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginScreen extends Activity  {
	private EditText username;
	private EditText password;
	private Button submit;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.new_view);
		initVariables();
	}

	private void initVariables(){
		username = (EditText)findViewById(R.id.username);
		password = (EditText)findViewById(R.id.password);
		submit = (Button)findViewById(R.id.signin);
		submit.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if(!username.getText().toString().equalsIgnoreCase("") & !password.getText().toString().equalsIgnoreCase("")){
					Intent intent = new Intent(v.getContext(), ChannelHome.class);
					startActivity(intent);
					return;
				}
               Toast.makeText(v.getContext(), "Username and Password cannot be empty", Toast.LENGTH_LONG).show();
			}
		});

	}


}
