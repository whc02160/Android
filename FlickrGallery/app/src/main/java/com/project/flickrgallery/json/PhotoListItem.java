/*
 * PhotoListItem.java
 * Photos item in the Flickr image data
 *
 * Created by wchun on 5/20/2016
 */
package com.project.flickrgallery.json;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PhotoListItem
{
	@SerializedName("page")
	private int page;

	@SerializedName("pages")
	private int pages;

	@SerializedName("perpage")
	private int perpage;

	@SerializedName("total")
	private String total;

	@SerializedName("photo")
	private ArrayList<PhotoItem> photo;

	public int getPage() {
		return page;
	}

	public int getPages() {
		return pages;
	}

	public int getPerpage() {
		return perpage;
	}

	public String getTotal() {
		return total;
	}

	public ArrayList<PhotoItem> getPhotoList() {
		return photo;
	}
}
