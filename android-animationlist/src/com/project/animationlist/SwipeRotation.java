package com.project.animationlist;

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ListView;

import com.project.animationlist.animation.ViewRotation;

/**
 * When user swipes listView or touch the button,
 * rotate the view and swap the current view
 *
 */
public class SwipeRotation implements OnTouchListener {	
	private View _mainView;
	private View _listView;
	private Button _buttonView;
	private final GestureDetector _gestureDetector;
	
	private float _currentDegree = 0.0f;
	private boolean _flipRight = false;
	private boolean _flipView = false;
	
	private final int DURATION = 200;
	
	
	/**
	 * Initialize instance variables and also initialize button touch event
	 * Views are from MainActivity
	 * @param context
	 * @param mainView
	 * @param listView
	 * @param buttonView
	 */
	public SwipeRotation(Context context, View mainView, ListView listView, Button buttonView) {	
		_mainView = mainView;
		_listView = listView;
		_buttonView = buttonView;
		_buttonView.setOnTouchListener(new OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				// When button view is on, swap the view to the listView
				applyRotation(0, 90, _buttonView, _listView);
				return false;
			}
		});

		_gestureDetector = new GestureDetector(context, new GestureListener());
	}
	
	/**
	 * When user release (ACTION_UP) the gesture, then
	 * detect if user made a big swipe action to swap two views.
	 * If user swipe to the left, then rotate screen to the left and so on.
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP)
		{
			// Swap two views if big swipe action is made
			// Flip the screen either left or right depending on swipe direction
			if (_flipView)
			{
				float degreeToEnd = 90.0f;
				if (_flipRight)
					degreeToEnd = -90.0f;
				
				applyRotation(_currentDegree, degreeToEnd, _listView, _buttonView);
				_flipRight = false;
				_flipView = false;
			}
			// Reset the angle
			_currentDegree = 0.0f;
			_listView.setRotationY(_currentDegree);
		}
		return _gestureDetector.onTouchEvent(event);
	}
	
	/**
	 * Animate/Rotate the screen to swap two views
	 * @param degreeBegin Degree to start the animation
	 * @param degreeEnd Degree to finish the animation
	 * @param v1 Current view
	 * @param v2 View to swap with
	 */
	private void applyRotation(float degreeBegin, float degreeEnd, final View v1, final View v2) {
		// Get the center of the view
		final float centerX = (_mainView.getRight() - _mainView.getLeft()) / 2.0f;
		final float centerY = (_mainView.getBottom() - _mainView.getTop()) / 2.0f;

		// Animation for two views
		// First view (v1) rotates and disappear then the second view rotates and appears
		final ViewRotation rotationToStart = new ViewRotation(degreeBegin, degreeEnd, centerX, centerY);
		final ViewRotation rotationToEnd = new ViewRotation(-degreeEnd, 0, centerX, centerY);

		rotationToStart.setDuration(DURATION);
		rotationToEnd.setDuration(DURATION);
		rotationToStart.setFillAfter(true);
		rotationToEnd.setFillAfter(true);

		AnimationListener animationListener = new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {}

			// Animation for the second view
			@Override
			public void onAnimationEnd(Animation animation)
			{
				v2.setVisibility(View.VISIBLE);
				v2.startAnimation(rotationToEnd);
				v1.clearAnimation();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {}
		};
		rotationToStart.setAnimationListener(animationListener);
		
		// Animation for the first view
		v1.setVisibility(View.GONE);
		v1.startAnimation(rotationToStart);
	}

	/**
	 * Detect the swipe gesture and find
	 * if enough swipe action is made for swapping the view.
	 * Currently, swipe threshold is set to 400, which is about the half of the screen
	 * Also MAX_DEGREE indicates the angle user can rotate.
	 */
	private class GestureListener extends SimpleOnGestureListener {
		final float THRESHOLD = 400.0f;
		final float MAX_DEGREE = 60.0f;
		
		@Override
		public boolean onScroll (MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			float dx = (e2.getX() - e1.getX());			
			float viewWidth = (_listView.getRight() - _listView.getLeft());
			
			_currentDegree = -1*(dx / viewWidth) * MAX_DEGREE;
			_listView.setRotationY(_currentDegree);
			
			if (Math.abs(dx) > THRESHOLD)
			{
				_flipView = true;
				_flipRight = (dx > 0) ? true : false;
			}
			return false;
		}
	}
	
}