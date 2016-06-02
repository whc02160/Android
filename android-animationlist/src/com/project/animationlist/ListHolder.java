package com.project.animationlist;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Get and Set the list item view
 *
 */
public class ListHolder {
	
	private LinearLayout _layout;
	private TextView _title;
	private TextView _body;
	private ImageView _icon;
	
	public ListHolder(LinearLayout layout, TextView title, TextView body, ImageView icon) {
		_layout = layout;
		_title = title;
		_body = body;
		_icon = icon;
	}
	
	public LinearLayout getListLayout() {
		return _layout;
	}
	
	public void setListLayout(LinearLayout layout) {
		_layout = layout;
	}
	
	public TextView getTitleTextView() {
		return _title;
	}
	
	public void setTitleTextView(TextView title) {
		_title = title;
	}

	public TextView getBodyTextView() {
		return _body;
	}
	
	public void setBodyTextView(TextView body) {
		_body = body;
	}
	
	public ImageView getIconImageView() {
		return _icon;
	}
	
	public void setIconImageView(ImageView icon) {
		_icon = icon;
	}
}
