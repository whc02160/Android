/*
 * SearchItem.java
 * Top level of the Flickr image data.
 *
 * Created by wchun on 5/20/2016
 */
package com.project.flickrgallery.json;

import com.google.gson.annotations.SerializedName;

public class SearchItem {
	@SerializedName("photos")
	private PhotoListItem photos;

	@SerializedName("stat")
	private String stat;

	public SearchItem() {
		photos = new PhotoListItem();
		stat = "";
	}

	public PhotoListItem getPhotos() {
		return photos;
	}

	public String getStat() {
		return stat;
	}
}
