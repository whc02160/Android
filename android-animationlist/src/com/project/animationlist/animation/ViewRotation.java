package com.project.animationlist.animation;

import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.graphics.Camera;
import android.graphics.Matrix;

/**
 * Animation class for view rotation
 */
public class ViewRotation extends Animation {
	
	private final float _fromDegrees;
	private final float _toDegrees;
	private final float _centerX;
	private final float _centerY;
	private Camera _camera; 

	/**
	 * Initialize variables for ViewRotation class
	 * @param fromDegrees
	 * @param toDegrees
	 * @param centerX
	 * @param centerY
	 */
	public ViewRotation(float fromDegrees, float toDegrees, float centerX, float centerY) {
		_fromDegrees = fromDegrees;
		_toDegrees = toDegrees;
		_centerX = centerX;
		_centerY = centerY;
	}

	/**
	 * Initialize Camera one time only
	 */
	@Override
	public void initialize(int width, int height, int parentWidth, int parentHeight) {
		super.initialize(width, height, parentWidth, parentHeight);
		_camera = new Camera();
	}

	/**
	 * Calculate current view angle and rotate it
	 */
	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		float degrees = _fromDegrees + ((_toDegrees - _fromDegrees) * interpolatedTime); 
		final Camera camera = _camera; 
		final Matrix matrix = t.getMatrix();

		camera.save();
		camera.translate(0.0f, 0.0f, 0.0f);
		camera.rotateY(degrees);
		camera.getMatrix(matrix);
	camera.restore();

		matrix.preTranslate(-(_centerX), -(_centerY));
		matrix.postTranslate(_centerX, _centerY);
	}
}