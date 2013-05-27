package org.shortlets.simplescalculo.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.sax.StartElementListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.Toast;


public class ButtonUtil{
 	public static OnLongClickListener  getOnLongClickListener(final String msg){
		OnLongClickListener onLongClickListener = new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				Toast.makeText(v.getContext(), msg, Toast.LENGTH_LONG).show();
				return true;
			}
		};
		return onLongClickListener;
	}
	
	public static OnTouchListener getOnTouchListenerColor(final int   _actionDownColor,final  int _actionUpColor){
		OnTouchListener  onTouchButaoListener = new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundColor(_actionDownColor);
					break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundColor(_actionUpColor);
					break;
				}
				return false;
			}
		}; 
		return onTouchButaoListener;
	}
	
	public static OnClickListener getOnClickListenerIntent(final Context context,final Class activityClassIntent,final Activity activityAtual){
		
		 OnClickListener listerner = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
	          Intent intent = new Intent(context,activityClassIntent);
	          activityAtual.startActivity(intent);
	          
			}
			
		};
		return listerner;
	}
	
	public static OnClickListener getOnClickListenerIntent(final String actionInsert,final Uri uri,final Activity activityAtual){
		
		 OnClickListener listener = new OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
	          Intent intent = new Intent(actionInsert);
	          intent.setData(uri);
	          activityAtual.startActivity(intent);
	          
			}
		};
		return listener;
	}
	
	
	public static OnClickListener openCaledar(final Activity activityAtual){
		
		 OnClickListener listener = new OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClassName("com.android.calendar","com.android.calendar.LaunchActivity");
				activityAtual.startActivity(i);
	          
	          
			}
		};
		return listener;
	}

}
