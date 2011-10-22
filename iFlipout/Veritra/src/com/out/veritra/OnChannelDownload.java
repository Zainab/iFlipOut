package com.out.veritra;

import java.util.List;

import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import com.out.veritra.models.Channel;
import com.out.veritra.models.Youtube;

public interface OnChannelDownload {

  public void onDownloadComplete(ListAdapter adapter, List<Channel> documents);
  
  public void onDownloadCompleteYoutube(ListAdapter adapter, List<Youtube> documents);

  public void onDownloadCancel();
  
  public void onDownloadComplete(SimpleAdapter adapter);
}
