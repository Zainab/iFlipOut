package com.out.veritra.tasks;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ListAdapter;


import com.out.veritra.Logger;
import com.out.veritra.OnChannelDownload;
import com.out.veritra.adapters.ChannelAdapter;
import com.out.veritra.models.Channel;
import com.out.veritra.parsers.XMLParser;

public class FetchChannel extends AsyncTask<String, Void, ListAdapter> {
  private ProgressDialog dialog = null;
  private OnChannelDownload onDocumentsDownload = null;
  private Activity activity;
  List<Channel> channels = null;

  public FetchChannel(Activity activity, OnChannelDownload odd) {
    this.activity = activity;
    dialog = new ProgressDialog(activity);
    onDocumentsDownload = odd;
  }

  protected void onPreExecute() {
    this.dialog.setMessage("Fetching news information ...");
    this.dialog.show();
  }

  @Override
  protected ListAdapter doInBackground(String... args) {
    XMLParser parser = new XMLParser(args[0]);
    channels = parser.parseChannels();
    Logger.i("****** SIZE ??******" + channels.size());
    return new ChannelAdapter(activity, channels);
  }

  protected void onPostExecute(final ListAdapter adapter) {
    if (this.dialog.isShowing()) {
      this.dialog.dismiss();
    }
    onDocumentsDownload.onDownloadComplete(adapter, channels);

  }
  public List<Channel> getDocuments(){
    return channels;

  }
}
