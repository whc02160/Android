/*
 * PhotoItem.java
 * Bottom level of the Flickr image data.
 * In other words, Each Photo item in the Flickr image data
 *
 * Created by wchun on 5/20/2016
 */
package com.project.flickrgallery.json;

import com.google.gson.annotations.SerializedName;

public class PhotoItem
{
	@SerializedName("id")
	private String id;

	@SerializedName("owner")
	String owner;

	@SerializedName("secret")
	String secret;

	@SerializedName("server")
	String server;

	@SerializedName("farm")
	int farm;

	@SerializedName("title")
	String title;

	@SerializedName("isPublic")
	boolean isPublic;

	@SerializedName("isFriend")
	boolean isFriend;

	@SerializedName("isFamily")
	boolean isFamily;

	public String getId() {
		return id;
	}

	public String getOwner() {
		return owner;
	}

	public String getSecret() {
		return secret;
	}

	public String getServer() {
		return server;
	}

	public int getFarm() {
		return farm;
	}

	public String getTitle() {
		return title;
	}

	public boolean getIsPublic() {
		return isPublic;
	}

	public boolean getIsFriend() {
		return isFriend;
	}

	public boolean getIsFamily() {
		return isFamily;
	}
}
