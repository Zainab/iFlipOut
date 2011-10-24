package com.out.veritra;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.keyes.youtube.OpenYouTubePlayerActivity;
import com.out.veritra.adapters.ChannelAdapter;
import com.out.veritra.adapters.YoutubeAdapter;
import com.out.veritra.models.Channel;
import com.out.veritra.models.Youtube;
import com.out.veritra.tasks.FetchYoutube;
import com.out.veritra.utils.Logger;
import com.out.veritra.utils.UrlBuilder;

public class YoutubeList extends ListActivity implements OnClickListener,
OnItemClickListener, OnChannelDownload, ListView.OnScrollListener  {
	private String url = null;
	private UrlBuilder builder = new UrlBuilder();
	ArrayList<Youtube> documents = null;
	View footerView = null;
	ImageView search = null;
	TextView refresh = null;
	ChannelAdapter adapter = null;
	private int offset = 0;
	private  LayoutInflater mLayoutInflater;
	TextView loadingView = null;
	ProgressBar progressView = null;

	// Let the last viewed position be 1 so that we display the 0th element on start.
	private int lastViewedPosition = 1;
	private int firstViewedPosition = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);

		this.getListView().setCacheColorHint(Color.WHITE);
		setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);	
		this.getListView().setOnItemClickListener(this);

		this.getListView().setOnScrollListener(this);
		builder = new UrlBuilder();
		url = builder.getYoutubeURL();
		Logger.i(url);
		new FetchYoutube(this, this).execute(url);
		documents = new ArrayList<Youtube>(); 
		Logger.i("Documents Size " + documents.size());
	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//		Toast.makeText(this, "Yet to be implemented", Toast.LENGTH_LONG).show();
		String dummyURL = "http://www.youtube.com/watch?v=KUeM808HTuk&feature=related";
		Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(dummyURL));
		startActivity(myIntent);
//		Intent lVideoIntent = new Intent(null, Uri.parse(dummyURL, view.getContext(), OpenYouTubePlayerActivity.class));
//		startActivity(lVideoIntent);
//		Youtube document = documents.get(position);
//		Intent intent = new Intent(Intent.ACTION_VIEW);
//		intent.setAction("android.intent.action.VIEW"); 
//        intent.addCategory("android.intent.category.BROWSABLE");
//		intent.setData(Uri.parse(document.getId().trim()));
//		startActivity(intent);
	}


	public void onDownloadCancel() {
		return;
	}


	public void onClick(View view) {
		onSearchRequested();
	}

	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
	}

	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if(scrollState == OnScrollListener.SCROLL_STATE_IDLE){
			int size = documents.size();

			// Check to see if we are displaying the last cell in the ListView and do 
			// a fetch only if we are really displaying it.
			lastViewedPosition = this.getListView().getLastVisiblePosition();
			firstViewedPosition = this.getListView().getFirstVisiblePosition();
			Logger.i("Last Viewed Position:" + lastViewedPosition);
			if (lastViewedPosition >= size) {
				Logger.i("LastViewedPosition" + lastViewedPosition); 
				Logger.i("Last element visible, fetching new data. ");
				//        url = builder.getFeaturedDocumentUrl(offset, Constants.MAX_COUNT );
				Logger.i(url);
				//        new FetchDocumentsService(this, this).execute(url);
			}
			return;
		}
	}

	public View showProgressIndicator(boolean status) {
		mLayoutInflater = LayoutInflater.from(this);
		setProgressBarIndeterminateVisibility(status);
		Logger.i("Adding footer view");
		View view = mLayoutInflater.inflate(R.layout.footerview, null); 
		Logger.i("Adding footer view " + view);
		loadingView = (TextView) view.findViewById(R.id.loadingView);
		loadingView.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/georgia.ttf"));
		loadingView.setText("Loading....");
		loadingView.setTextSize(20);
		loadingView.setTextColor(Color.BLACK);
		progressView = (ProgressBar) view.findViewById(R.id.progressView);
		progressView.setIndeterminate(status);
		return view;
	}

	public void onDownloadComplete(SimpleAdapter adapter) {
		// TODO Auto-generated method stub
		
	}

	public void onDownloadCompleteYoutube(ListAdapter adapter,
			List<Youtube> documents) {
		// Add the new document list to the already existing list of documents.
		this.documents.addAll(documents);

		// Set the ListAdapter with the contents of the modified documents list.
		this.setListAdapter(new YoutubeAdapter(this, this.documents));

		// The new offset is the number of elements in the adapter after we add
		// the documents we fetched.
		offset = this.documents.size();

		// After loading up the new data, go back to the last viewed cell.
		this.getListView().setSelection(firstViewedPosition);

		if (footerView != null) {
			Logger.i("Removing footer view");
			this.getListView().removeFooterView(footerView);
			footerView = null;
		}

		Logger.i("Adding footer view");
		footerView = showProgressIndicator(true);
		this.getListView().addFooterView(footerView);
		if (footerView != null) {
			Logger.i("Footerview should have been created and added already.");
		}
		
	}

	public void onDownloadComplete(ListAdapter adapter, List<Channel> documents) {
		// TODO Auto-generated method stub
		
	}

}
