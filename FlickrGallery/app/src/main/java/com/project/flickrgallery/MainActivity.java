/*
 * MainActivity.java
 * Main class that does magic.
 *
 * Created by wchun on 5/20/2016
 */
package com.project.flickrgallery;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends Activity {

	private static final String FLICKR_SEARCH_URL = "https://api.flickr.com/services/rest/?method=flickr.photos.search" +
			"&api_key=3e7cc266ae2b0e0d78e279ce8e361736&format=json&nojsoncallback=1&text=";
	private static final String DEFAULT_KEYWORD = "kittens";

	private ArrayList<String> mImageList = null;
	private Context mContext = null;
	private GalleryListAdapter mListAdapter;
	private GridView mGridView = null;
	private EditText mSearchEditText = null;
	private TextView mImageResultTextView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mContext = this;
		mListAdapter = new GalleryListAdapter(this);
		mGridView = (GridView)findViewById(R.id.imageGridView);
		mGridView.setAdapter(mListAdapter);

		mSearchEditText = (EditText)findViewById(R.id.searchEditText);
		mImageResultTextView = (TextView)findViewById(R.id.imageResultTextView);
		registerSearchEditTextListener();
	}

	@Override
	protected void onPause()
	{
		super.onPause();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		//Show default search
		searchImage(DEFAULT_KEYWORD);
	}

	/**
	 * Run search (Update URL, and run search to update the image list).
	 * @param v
	 */
	public void onSearchClick(View v)
	{
		hideKeyboard();

		String keyword = mSearchEditText.getText().toString();
		searchImage(keyword);
	}

	/**
	 * Update the image list and update the view,
	 * @param imageList New image list from the search
	 */
	public void updateImageList(ArrayList<String> imageList)
	{
		if (imageList == null || imageList.size() == 0)
		{
			mImageResultTextView.setVisibility(View.VISIBLE);
			mGridView.setVisibility(View.GONE);
		}
		else
		{
			mImageResultTextView.setVisibility(View.GONE);
			mGridView.setVisibility(View.VISIBLE);
		}
		mImageList = imageList;
		mListAdapter.notifyDataSetChanged();
	}

	/**
	 * Update search URL with the keyword and run search.
	 * Ensure that when there is no keyword, show an Error message.
	 * @param keyword Keyword from the search
	 */
	private void searchImage(String keyword)
	{
		if (keyword.length() == 0)
			Toast.makeText(mContext, "Please type the keyword", Toast.LENGTH_SHORT).show();
		else
		{
			String searchURL = FLICKR_SEARCH_URL + keyword;
			ImageSearchTask mImageSearchTask = new ImageSearchTask(this);
			mImageSearchTask.execute(searchURL);
		}
	}

	/**
	 * Hide keybaord when ENTER or DONE is pressed
	 */
	private void registerSearchEditTextListener()
	{
		mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
					hideKeyboard();
					return true;
				}
				String keyword = mSearchEditText.getText().toString();
				searchImage(keyword);
				return false;
			}
		});
	}

	/**
	 * Hide keyboard when ENTER button on the keybaord or search button is pressed
	 */
	private void hideKeyboard()
	{
		InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(mSearchEditText.getWindowToken(), 0);
	}

	/**
	 * Class that handles Image grid view.
	 */
	class GalleryListAdapter extends BaseAdapter
	{
		private Context mContext = null;

		GalleryListAdapter(Context context) {
			mContext = context;
		}

		@Override
		public int getCount() {
			return (mImageList == null) ? 0 : mImageList.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View gridItemView = null;

			if (convertView == null)
				gridItemView = inflater.inflate(R.layout.grid_view_item, parent, false);
			else
				gridItemView = convertView;

			// We use Picasso to load image URL to ImageView
			if (mImageList != null) {
				ImageView gridItemImageView = (ImageView)gridItemView.findViewById(R.id.item_image);
				Picasso.with(mContext).load(mImageList.get(position)).into(gridItemImageView);
			}
			return gridItemView;
		}
	}
}
