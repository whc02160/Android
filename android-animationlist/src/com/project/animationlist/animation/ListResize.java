package com.project.animationlist.animation;

import com.project.animationlist.ListAdapter;
import com.project.animationlist.ListItem;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * When user selects the list item,
 * expand or collapse current list item view
 *
 */
public class ListResize extends Animation {	

	private final int DURATION = 200;
	private ListAdapter _listAdapter;
	private ListItem _listItem;	
	
	/**
	 * Initialize the view and animation
	 * @param listAdapter
	 * @param listItem
	 */
	public ListResize(ListAdapter listAdapter, ListItem listItem) {
		_listAdapter = listAdapter;
		_listItem = listItem;
		setDuration(DURATION);
	}
	
	/**
	 * Apply animation to current selected list item
	 */
	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		int fromHeight = _listItem.getFromHeight();
		int toHeight = _listItem.getToHeight();
		
		float height = (toHeight - fromHeight) * interpolatedTime + fromHeight;
		
		View view = _listItem.getHolder().getListLayout();
		LayoutParams param = (LayoutParams)view.getLayoutParams();
		param.height = (int)height;
		_listItem.setCurrentHeight(param.height);
		_listAdapter.notifyDataSetChanged();
	}
}
