package com.out.veritra.parsers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.out.veritra.models.Channel;
import com.out.veritra.models.Youtube;


public class XMLParser {


  private final URL feedUrl;

  public XMLParser(String feedUrl){
    try {
      this.feedUrl = new URL(feedUrl);
      } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }

  
  public List<Channel> parseChannels() {
	    SAXParserFactory factory = SAXParserFactory.newInstance();
	    ChannelHandler ch = new ChannelHandler();
	    try {
	      SAXParser parser = factory.newSAXParser();
	      parser.parse(getInputStream(), ch);
	      return ch.getChannels();
	    } catch (Exception e) {
//	      throw new RuntimeException(e);
	      e.printStackTrace();
	    } 
	    return ch.getChannels();
	  }
  
  public List<Youtube> parseYoutubeChannels() {
	    SAXParserFactory factory = SAXParserFactory.newInstance();
	    YoutubeHandler ch = new YoutubeHandler();
	    try {
	      SAXParser parser = factory.newSAXParser();
	      parser.parse(getInputStream(), ch);
	      return ch.getChannels();
	    } catch (Exception e) {
//	      throw new RuntimeException(e);
	      e.printStackTrace();
	    } 
	    return ch.getChannels();
	  }
  
  private InputStream getInputStream() {
    try {
      return feedUrl.openConnection().getInputStream();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  private static String convertStreamToString(InputStream is) {
    /*
     * To convert the InputStream to String we use the BufferedReader.readLine()
     * method. We iterate until the BufferedReader return null which means
     * there's no more data to read. Each line will appended to a StringBuilder
     * and returned as String.
     */
    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    StringBuilder sb = new StringBuilder();

    String line = null;
    try {
      while ((line = reader.readLine()) != null) {
        sb.append(line + "\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        is.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return sb.toString();
  }

}
