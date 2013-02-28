package ru.pvolan.clockface;

import java.lang.Thread.UncaughtExceptionHandler;

import ru.pvolan.trace.Trace;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.util.Log;

public class ClockfaceApp extends Application 
{
	public static ClockfaceApp App;
	
	
	private ClockfaceDrawer drawer;
	
	@Override
	public void onCreate() 
	{
		App = this;
		super.onCreate();
		Trace.Print("ClockfaceApp.onCreate");
		
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			
			@Override
			public void uncaughtException(Thread thread, Throwable ex) {
				// TODO Auto-generated method stub
				Trace.Print("*******Error******");
				Trace.Print(ex);
			}
		});
			
		
	}
	
	
	public ClockfaceDrawer getClockfaceDrawer() 
	{
		if(drawer == null){
			drawer = new ClockfaceDrawer();
		}
		return drawer;
	}
}
