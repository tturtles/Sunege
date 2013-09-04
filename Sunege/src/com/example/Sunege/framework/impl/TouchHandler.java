package com.example.project2nd.framework.impl;

import java.util.List;





import com.example.project2nd.framework.Input.TouchEvent;

import android.view.MotionEvent;
import android.view.View.OnTouchListener;

public interface TouchHandler extends OnTouchListener {
	public boolean isTouchDown(int pointer);
	
	public int getTouchX(int pointer);
	
	public int getTouchY(int pointer);
	
	public List<TouchEvent> getTouchEvents();

}
