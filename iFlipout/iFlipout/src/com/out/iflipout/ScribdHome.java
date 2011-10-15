package com.out.iflipout;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class ScribdHome extends Activity implements OnClickListener {
	Integer position = null;
	ImageView wsj = null;
	Button news = null;
	Button faceBook = null;
	Button youtube = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.scribd_home);

		wsj = (ImageView) findViewById(R.id.wsj_image_view);
		wsj.setOnClickListener(this);

		news = (Button)findViewById(R.id.news);
		news.setOnClickListener(this);

		faceBook = (Button) findViewById(R.id.faceBook);
		faceBook.setOnClickListener(this);

		youtube = (Button)findViewById(R.id.youtube);
		youtube.setOnClickListener(this);

	}

	public void onClick(View view) {
		Intent intent = null;
		Class<?> classToLaunch = null;
		if (view == news) {
			classToLaunch = ChannelList.class;
		} else if (view == faceBook) {
			classToLaunch = Main.class;
		}  else if(view == youtube) {
			classToLaunch = YoutubeList.class;
		}

		intent = new Intent(this.getBaseContext(), classToLaunch);
		startActivity(intent);

	}


}
