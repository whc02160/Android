/*
 * ImageSearchTask.java
 * GET JSON data from URL (flickr URL + keyword),
 * and parse the data into ArrayList with image URL list,
 * then update the list view.
 *
 * Created by wchun on 5/20/2016
 */
package com.project.flickrgallery;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.flickrgallery.json.PhotoItem;
import com.project.flickrgallery.json.SearchItem;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ImageSearchTask extends AsyncTask<String, Void, String>
{
	private OkHttpClient mClient = null;
	private MainActivity mMainActivity = null;

	ImageSearchTask(MainActivity activity)
	{
		mClient =  new OkHttpClient();
		mMainActivity = activity;
	}

	/**
	 * GET JSON data from the web using the Flickr image search URL
	 * @param params URL that has keyword image data
	 * @return String format of JSON data
	 */
	@Override
	protected String doInBackground(String... params)
	{
		Request.Builder builder = new Request.Builder();
		builder.url(params[0]);
		Request request = builder.build();
		try {
			Response response = mClient.newCall(request).execute();
			return response.body().string();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * After GET, parse JSON data to extract each image information,
	 * to get image URL and store it to the list, then update the GridView.
	 * @param result String format of JSON data of search result
	 */
	@Override
	protected void onPostExecute(String result)
	{
		Gson gson = new GsonBuilder().create();
		SearchItem dataList = gson.fromJson(result, SearchItem.class);

		// Ensure that we only update it when the current stat is OK
		if (dataList.getStat().compareTo("ok") == 0)
		{
			ArrayList<PhotoItem> photoList = dataList.getPhotos().getPhotoList();
			ArrayList<String> imageList = getImageList(photoList);
			mMainActivity.updateImageList(imageList);
		}
	}

	/**
	 * Extract each image information to get image url.
	 * URL Format: http://farm{farm}.static.flickr.com/{server}/{id}_{secret}.jpg
	 * @param photoList
	 * @return
	 */
	private ArrayList<String> getImageList(ArrayList<PhotoItem> photoList)
	{
		int n = photoList.size();
		if (n == 0)
			return null;
		ArrayList<String> imageList = new ArrayList<String>();
		for(int i = 0; i < n; i++)
		{
			PhotoItem currItem = photoList.get(i);
			String imageURL = "http://farm" + currItem.getFarm() + ".static.flickr.com/" +
					currItem.getServer() + "/" + currItem.getId() + "_" + currItem.getSecret() + ".jpg";
			imageList.add(imageURL);
		}
		return imageList;
	}
}