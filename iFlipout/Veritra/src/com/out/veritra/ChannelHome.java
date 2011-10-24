package com.out.veritra;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class ChannelHome extends Activity implements OnClickListener {
	Integer position = null;
	ImageView wsj = null;
	Button news = null;
	Button faceBook = null;
	Button youtube = null;
	MenuItem addNewChannel;
	MenuItem settings;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mychannelview);

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
		}else{
			Toast.makeText(view.getContext(), "Channel", Toast.LENGTH_LONG).show();
			return;
		}

		intent = new Intent(this.getBaseContext(), classToLaunch);
		startActivity(intent);

	}

	@Override
	  public boolean onCreateOptionsMenu(Menu menu) {
	    super.onCreateOptionsMenu(menu);
	    addNewChannel = menu.add(0, 0, 0, "Add New Channel");
	    addNewChannel.setIcon(android.R.drawable.ic_menu_add);
	    settings = menu.add(0, 0, 0,"Settings");
	    return true;
	  }
	

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		addNewChannel.setVisible(true);
		settings.setVisible(true);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {

		if (menuItem == addNewChannel) {
			Intent intent = new Intent(this, AddNewChannel.class);
			startActivity(intent);
			return true;
		}

		if (menuItem == settings) {
			return true;
		}

		return true;
	}




}
