package com.out.veritra.parsers;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.out.veritra.Logger;
import com.out.veritra.models.Channel;


@SuppressWarnings("unused")
public class ChannelHandler extends DefaultHandler {
	private List<Channel> documents;
	private Channel currentDocument;
	private StringBuilder builder;
	static final String DESCRIPTION = "description";
	static final String TITLE = "title";
	static final String LINK = "link";
	static final String ITEM = "item";

	public List<Channel> getChannels(){
		return this.documents;
	}

	@Override
	public void characters(char[] ch, int start, int length)
	throws SAXException {
		super.characters(ch, start, length);
		builder.append(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String name)
	throws SAXException {
		super.endElement(uri, localName, name);
		if (this.currentDocument != null){
			if (localName.equalsIgnoreCase(TITLE)){
				currentDocument.setTitle(builder.toString());
				
			}else if (localName.equalsIgnoreCase(LINK)){
				currentDocument.setLink(builder.toString());
				
			}else if (localName.equalsIgnoreCase(DESCRIPTION)){
				currentDocument.setDescription(builder.toString());
				
			}
			else if (localName.equalsIgnoreCase(ITEM)){
				documents.add(currentDocument);
			}

			builder.setLength(0);	
		}
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		documents = new ArrayList<Channel>();
		builder = new StringBuilder();
	}

	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, name, attributes);
		if (localName.equalsIgnoreCase(TITLE)) {
			this.currentDocument = new Channel();
		}
	}
}