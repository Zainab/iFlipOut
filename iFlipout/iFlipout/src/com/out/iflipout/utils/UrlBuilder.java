package com.out.iflipout.utils;


public class UrlBuilder {

   public String getChannelURL() {
	    StringBuilder builder = new StringBuilder();
	    builder.append(Constants.WSJ_URL);
	    return builder.toString();
	  }
   public String getYoutubeURL() {
	    StringBuilder builder = new StringBuilder();
	    builder.append(Constants.YOUTUBE_URL);
	    return builder.toString();
	  }
  
}
