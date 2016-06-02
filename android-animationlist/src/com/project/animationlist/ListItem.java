package com.project.animationlist;

/**
 * Get and Set the list item content
 *
 */
public class ListItem {

	private String _title;
	private String _body;
	
	private boolean _isOpen;
	public int _icon;
	private ListHolder _holder;
	
	private int _fromHeight;
	private int _toHeight;
	private int _currentHeight;
		
	public ListItem(String title, String body) {
		_title = title;
		_body = body;
		_isOpen = false;
		_icon = R.drawable.ic_action_expand;
	}
	
	public String getTitle() {
		return _title;
	}
	
	public void setTitle(String title) {
		_title = title;
	}
	
	public String getBody() {
		return _body;
	}
	
	public void setBody(String body) {
		_body = body;
	}
	
	public boolean getIsOpen() {
		return _isOpen;
	}
	
	public void setIsOpen(boolean isOpen) {
		_isOpen = isOpen;
	}
	
	public int getIcon() {
		return _icon;
	}
	
	public void setIcon(int icon) {
		_icon = icon;
	}
	
	public ListHolder getHolder() {
		return _holder;
	}
	
	public void setHolder(ListHolder holder) {
		_holder = holder;
	}
	
	public int getFromHeight() {
		return _fromHeight;
	}
	
	public void setFromHeight(int fromHeight) {
		_fromHeight = fromHeight;
	}
	
	public int getToHeight() {
		return _toHeight;
	}
	
	public void setToHeight(int toHeight) {
		_toHeight = toHeight;
	}
	
	public int getCurrentHeight() {
		return _currentHeight;
	}
	
	public void setCurrentHeight(int currentHeight) {
		_currentHeight = currentHeight;
	}
}
