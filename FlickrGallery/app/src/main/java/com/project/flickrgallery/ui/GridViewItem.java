/*
 * GridViewItem.java
 * Layout for each image grid view item
 *
 * Created by wchun on 5/20/2016
 */
package com.project.flickrgallery.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class GridViewItem extends ImageView
{
	public GridViewItem(Context context) {
		super(context);
	}

	public GridViewItem(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public GridViewItem(Context context, AttributeSet attrs, int style) {
		super(context, attrs, style);
	}

	@Override
	public void onMeasure(int width, int height) {
		// Show grid in square size
		super.onMeasure(width, width);
	}
}
