package com.out.iflipout.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.out.iflipout.R;
import com.out.iflipout.models.Channel;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import java.util.List;

public class ChannelAdapter extends BaseAdapter { 


  private Typeface georgia = null;
  private final LayoutInflater mLayoutInflater;
  private List<Channel> channels;
  private Hashtable<String, Drawable> images;
  private Context context;

  public ChannelAdapter(Activity context, List<Channel> channels) {
    this.channels = channels;
    this.context = context;
    mLayoutInflater = LayoutInflater.from(context);
    images = new Hashtable<String, Drawable>();
  }

  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder;
    if (convertView == null) {
      convertView = mLayoutInflater.inflate(R.layout.row_multi_field, null);

      holder = new ViewHolder();
      holder.cover = (ImageView) convertView.findViewById(R.id.image_cover);
      holder.title = (TextView) convertView.findViewById(R.id.documentTitle);
      Typeface georgiaBold = Typeface.createFromAsset(context.getAssets(), "fonts/georgiab.ttf");
      holder.title.setTypeface(georgiaBold);
      
      holder.pageCount = (TextView) convertView.findViewById(R.id.documentDescription);
      georgia = Typeface.createFromAsset(context.getAssets(), "fonts/georgia.ttf");
      holder.pageCount.setTypeface(georgia);

      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }

    final Channel book = channels.get(position);
    holder.title.setText(book.getTitle());
    holder.pageCount.setText(book.getDescription());
    return convertView;
  }

  public int getCount() {
    return channels.size();
  }

  public Object getItem(int position) {
    // return documents.get(position);
    return position;
  }

  public long getItemId(int position) {
    return position;
  }

  private Drawable ImageOperations(Context ctx, String url) {
    try {
      InputStream is = (InputStream) this.fetch(url);
      Drawable d = Drawable.createFromStream(is, "src");
      return d;
    } catch (MalformedURLException e) {
      e.printStackTrace();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public Object fetch(String address) throws MalformedURLException, IOException {
    URL url = new URL(address);
    Object content = url.getContent();
    return content;
  }

  private static class ViewHolder {
    ImageView cover;
    TextView title;
    TextView pageCount;
  }

  private class ImageDownloadTask extends AsyncTask<ContainerObject, Void, Drawable> {
    ContainerObject co = null;

    protected void onPreExecute() {
    }

    protected Drawable doInBackground(ContainerObject... args) {
      co = args[0];
      return ImageOperations(null, args[0].url);
    }

    protected void onPostExecute(final Drawable drawable) {
      if (drawable != null && co != null && co.holder != null) {
        images.put(co.url, drawable);
        co.holder.cover.setImageDrawable(drawable);
      }
    }

  }

  private class ContainerObject {
    public ViewHolder holder;
    public String url;
  }

}