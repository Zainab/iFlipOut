package com.out.veritra;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.out.veritra.models.Channel;
import com.out.veritra.models.Youtube;
import com.out.veritra.tasks.FetchFacebook;

public class Main extends ListActivity implements OnChannelDownload {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listplaceholder);
		new FetchFacebook(this, this).execute();
	}

	public void onDownloadCancel() {
		// TODO Auto-generated method stub

	}

	public void onDownloadComplete(ListAdapter adapter, List<Channel> documents) {
		// TODO Auto-generated method stub

	}

	public void onDownloadComplete(SimpleAdapter adapter) {
		setListAdapter(adapter);

		final ListView lv = getListView();
		lv.setTextFilterEnabled(true);	

	}

	public void onDownloadCompleteYoutube(ListAdapter adapter,
			List<Youtube> documents) {
		// TODO Auto-generated method stub
		
	}
}