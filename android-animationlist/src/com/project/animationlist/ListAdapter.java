package com.project.animationlist;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Set list item view
 *
 */
public class ListAdapter extends BaseAdapter {

	private Context _context;
	private ArrayList<ListItem> _listItem;
	
	public ListAdapter(Context context, ArrayList<ListItem> listItem) {
		_context = context;
		_listItem = listItem;
	}
	
	@Override
	public int getCount() {
		return _listItem.size();
	}

	@Override
	public Object getItem(int position) {
		return _listItem.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * Get the list data and set the view
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ListItem item = _listItem.get(position);
		ListHolder holder = null;
		
		if (convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater)_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_item_layout, parent, false);
			
			LinearLayout listLayout = (LinearLayout)convertView.findViewById(R.id.listItemLayout);
			TextView listTitle = (TextView)convertView.findViewById(R.id.listItemTitle);
			TextView listBody = (TextView)convertView.findViewById(R.id.listItemBody);
			ImageView listIcon = (ImageView)convertView.findViewById(R.id.listItemIcon);
			
			holder = new ListHolder(listLayout, listTitle, listBody, listIcon);
		}
		else
		{
			holder = (ListHolder)(convertView.getTag());
		}
		
		holder.getListLayout().setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, item.getCurrentHeight()));
		holder.getTitleTextView().setText(item.getTitle());
		holder.getBodyTextView().setText(item.getBody());
		holder.getIconImageView().setImageResource(item.getIcon());
		
		convertView.setTag(holder);
		item.setHolder(holder);
		
		return convertView;
	}
}
