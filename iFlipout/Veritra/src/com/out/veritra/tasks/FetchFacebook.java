package com.out.veritra.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.location.Address;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.SimpleAdapter;

import com.out.veritra.JSONfunctions;
import com.out.veritra.OnChannelDownload;
import com.out.veritra.R;

public class FetchFacebook extends AsyncTask<String, Void, SimpleAdapter> {
    private ProgressDialog dialog ;
    List<Address> addresses = null;
    private Activity act;
    private OnChannelDownload onDocumentsDownload = null;
    public FetchFacebook(Activity act, OnChannelDownload odd){
    	this.dialog = new ProgressDialog(act);
    	this.act = act;
    	this.onDocumentsDownload = odd;
    }
    protected void onPreExecute() {
    	
      this.dialog.setMessage("Fetching facebook information ...");
      this.dialog.show();
    }

    @Override
    protected SimpleAdapter doInBackground(String... args) {
    	 ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
         JSONObject json = JSONfunctions.getJSONfromURL("http://graph.facebook.com/search?q=wsj&type=post&fields=link,message");
  
         try{
           JSONArray  earthquakes = json.getJSONArray("data");
           
 	        for(int i=0;i<earthquakes.length();i++){						
 				HashMap<String, String> map = new HashMap<String, String>();	
 				JSONObject e = earthquakes.getJSONObject(i);
 				android.util.Log.i("**** TAG *******", "***  Message *****" + e.getString("message"));
 				map.put("id",  String.valueOf(i));
 	        	map.put("name", "Message:" + e.getString("message"));
 	        	map.put("magnitude", "Created Time : " +  e.getString("created_time"));
 	        	mylist.add(map);			
 			}
 	       
         }catch(JSONException e)        {
         	 Log.e("log_tag", "Error parsing data "+ e.toString());
         }
         return new SimpleAdapter(act, mylist , R.layout.singlefacebookitem, 
	              new String[] { "name", "magnitude" }, 
	              new int[] { R.id.item_title, R.id.item_subtitle });
      
    }

    protected void onPostExecute(final SimpleAdapter adapter) {
      if (this.dialog.isShowing()) {
        this.dialog.dismiss();
      }
      onDocumentsDownload.onDownloadComplete(adapter);
    }

  }
