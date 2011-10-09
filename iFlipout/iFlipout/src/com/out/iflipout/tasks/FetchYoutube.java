package com.out.iflipout.tasks;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ListAdapter;


import com.out.iflipout.Logger;
import com.out.iflipout.OnChannelDownload;
import com.out.iflipout.adapters.ChannelAdapter;
import com.out.iflipout.adapters.YoutubeAdapter;
import com.out.iflipout.models.Channel;
import com.out.iflipout.models.Youtube;
import com.out.iflipout.parsers.XMLParser;

public class FetchYoutube extends AsyncTask<String, Void, ListAdapter> {
  private ProgressDialog dialog = null;
  private OnChannelDownload onDocumentsDownload = null;
  private Activity activity;
  List<Youtube> channels = null;

  public FetchYoutube(Activity activity, OnChannelDownload odd) {
    this.activity = activity;
    dialog = new ProgressDialog(activity);
    onDocumentsDownload = odd;
  }

  protected void onPreExecute() {
    this.dialog.setMessage("Fetching youtube information ...");
    this.dialog.show();
  }

  @Override
  protected ListAdapter doInBackground(String... args) {
    XMLParser parser = new XMLParser(args[0]);
    channels = parser.parseYoutubeChannels();
    Logger.i("****** SIZE ??******" + channels.size());
    return new YoutubeAdapter(activity, channels);
  }

  protected void onPostExecute(final ListAdapter adapter) {
    if (this.dialog.isShowing()) {
      this.dialog.dismiss();
    }
    onDocumentsDownload.onDownloadCompleteYoutube(adapter, channels);

  }
  public List<Youtube> getDocuments(){
    return channels;

  }
}
