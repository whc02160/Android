package com.project.animationlist;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.project.animationlist.animation.ListResize;

/**
 * Main Activity for this app
 *
 */
public class MainActivity extends Activity {

	private final int COLLAPSE_HEIGHT = LayoutParams.MATCH_PARENT;
	private final int EXPAND_HEIGHT = 500;
	private final int COLLAPSE_ICON = R.drawable.ic_action_collapse;
	private final int EXPAND_ICON = R.drawable.ic_action_expand;
	private final int NUM_LIST_ITEM = 30;
	private int _liked = 0;
	
	private ViewGroup _mainLayout;
	private ListView _listView;
	private Button _button;
	private ListAdapter _listAdapter;
	private ArrayList<ListItem> _listItem;	
	private SwipeRotation _swipeRotation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Initialize View
		_mainLayout = (ViewGroup)this.findViewById(R.id.mainLayout);
		_listView = (ListView)this.findViewById(R.id.mainList);
		_button = (Button)this.findViewById(R.id.mainButton);
		
		// Initialize List item
		_listItem = populateListData();
		_listAdapter = new ListAdapter(this, _listItem);
		_listView.setAdapter(_listAdapter);
		
		// Register listeners for listView
		registerOnClickListener();
		registerOnScrollListener();
		
		// Register swipe animation
		_swipeRotation = new SwipeRotation(this, _mainLayout, _listView, _button);
		_listView.setOnTouchListener(_swipeRotation);
	}
	
	/**
	 * Action menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_good) {
			_liked++;
			Toast.makeText(this, "Thanks for liking this app! \nYou liked: " +
					_liked + " times!", Toast.LENGTH_SHORT).show();
			return true;
		}
		else if (id == R.id.action_quit) {
			new AlertDialog.Builder(this)
			.setTitle("Quit")
			.setMessage("Are you sure you want to quit the application?.")
			.setPositiveButton(R.string.action_quit_pos, new DialogInterface.OnClickListener() {				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finishApp();
				}
			})
			.setNegativeButton(R.string.action_quit_neg, new DialogInterface.OnClickListener() {				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// Do nothing				
				}
			}).setIcon(R.drawable.ic_action_warning).show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Register onClickListener for listItem
	 * When item is collapsed, expand it and vice versa.
	 */
	private void registerOnClickListener(){
		_listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ListItem item = _listItem.get(position);
				item.getHolder().setListLayout((LinearLayout)view);
				
				if (item.getIsOpen()) {
					collapseListItem(item);
				}
				else {
					closeAll();
					expandListItem(item);
				}
				updateListAnimation(item);
			}
		});
	}
	
	/**
	 * Hide the action menu when scroll down
	 */
	private void registerOnScrollListener() {
		final ActionBar actionBar = this.getActionBar();
		_listView.setOnScrollListener(new OnScrollListener() {
			int lastFirstVisiblePosition = 0;
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState){}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				int currentFirstVisiblePosition = _listView.getFirstVisiblePosition();
				if (currentFirstVisiblePosition > lastFirstVisiblePosition) {
					actionBar.hide();
				}
				else if (currentFirstVisiblePosition < lastFirstVisiblePosition) {
					actionBar.show();
				}
				lastFirstVisiblePosition = currentFirstVisiblePosition;
			}
		});
	}
	
	/**
	 * Animate the listView/listItem
	 * @param item
	 */
	private void updateListAnimation(ListItem item) {
		ListResize resizeAnimation = new ListResize(_listAdapter, item);
		item.getHolder().getListLayout().startAnimation(resizeAnimation);
	}
	
	/**
	 * When listItem is opened and another item is clicked,
	 * close the item in order to open another one
	 */
	private void closeAll() {
		for(ListItem item : _listItem) {
			if (item.getIsOpen()) {
				collapseListItem(item);
				updateListAnimation(item);
			}
		}
	}
	
	/**
	 * Collapse the selected item
	 * @param item ListItem that needs to be collapsed
	 */
	private void collapseListItem(ListItem item) {
		item.setFromHeight(EXPAND_HEIGHT);
		item.setToHeight(COLLAPSE_HEIGHT);
		item.setIcon(EXPAND_ICON);		
		item.setIsOpen(false);
	}
	
	/**
	 * Expand the selected item
	 * @param item ListItem that needs to be expanded
	 */
	private void expandListItem(ListItem item) {
		item.setFromHeight(COLLAPSE_HEIGHT);
		item.setToHeight(EXPAND_HEIGHT);
		item.setIcon(COLLAPSE_ICON);		
		item.setIsOpen(true);
	}
	
	/**
	 * Populate the listView with n items
	 * @return Populated listView
	 */
	private ArrayList<ListItem> populateListData() {
		ArrayList<ListItem> listItem = new ArrayList<ListItem>();		
		for(int i = 0; i < NUM_LIST_ITEM; i++) {
			String title = "Here is Title "+(i+1);
			String body = "Here is body "+(i+1);
			listItem.add(new ListItem(title, body));
		}
		
		return listItem;
	}
	
	private void finishApp()
	{
		this.finish();
	}
}
