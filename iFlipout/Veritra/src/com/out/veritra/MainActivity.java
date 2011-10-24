package com.out.veritra;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

public class MainActivity extends Activity implements OnClickListener {
	ImageView wsj = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.home_view);
		wsj = (ImageView) findViewById(R.id.wsj_image_view);
		wsj.setOnClickListener(this);
	}

	public void onClick(View view) {
		Intent intent = null;
		Class<?> classToLaunch = null;
		classToLaunch = ChannelHome.class;
		intent = new Intent(this.getBaseContext(), classToLaunch);
		startActivity(intent);

	}


}
