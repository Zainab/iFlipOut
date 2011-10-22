package com.out.veritra.tasks;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;



public class DocumentDownload  extends AsyncTask <String, Void, String> {

	private String documentUrl = null;
	private Context context = null;
	private ProgressDialog dialog = null;
	private static final String scribdPath = "scribd";


	public DocumentDownload(Context context, String url) {
		this.documentUrl = url;
		this.context = context;
		dialog = new ProgressDialog(context);
	}

	protected void onPreExecute() {
		this.dialog.setMessage("Downloading document ...");
		this.dialog.show();
	}

	protected String doInBackground(String... args) {
		String absoluteFilePath = null;
		try{
			URL url = new URL(documentUrl);
			String filename = url.getPath().split("/")[2];
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.connect();
			File folder = new File(Environment.getExternalStorageDirectory(), scribdPath);
			folder.mkdir();
			File file = new File(Environment.getExternalStorageDirectory(), scribdPath + 
					File.separator + filename);
			absoluteFilePath = file.getAbsolutePath();
			if (file.exists()) return absoluteFilePath;

			FileOutputStream fileOutputStream = new FileOutputStream(file);
			InputStream inputStream = connection.getInputStream();

			byte[] buffer = new byte[1024];
			int length = 0;
			while ( (length = inputStream.read(buffer)) > 0 ) {
				fileOutputStream.write(buffer,0, length);
			}

			fileOutputStream.close();
			inputStream.close();

		} catch (MalformedURLException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		}

		return absoluteFilePath;

	}

	protected void onPostExecute(final String filePath) {
		if (this.dialog.isShowing()) {
			this.dialog.dismiss();
		}
	}

}
