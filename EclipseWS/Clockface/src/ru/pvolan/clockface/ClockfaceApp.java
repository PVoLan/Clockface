package ru.pvolan.clockface;

import java.lang.Thread.UncaughtExceptionHandler;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.ExceptionReporter;
import com.google.analytics.tracking.android.GAServiceManager;

import ru.pvolan.trace.Trace;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.util.Log;

public class ClockfaceApp extends Application 
{
	public static ClockfaceApp App;
		
	
	@Override
	public void onCreate() 
	{
		App = this;
		super.onCreate();
		Trace.Print("ClockfaceApp.onCreate");
		
		
		
		final UncaughtExceptionHandler systemHandler = Thread.getDefaultUncaughtExceptionHandler(); 
		
		final UncaughtExceptionHandler logHandler = new Thread.UncaughtExceptionHandler() {
			
			@Override
			public void uncaughtException(Thread thread, Throwable ex) {
				// TODO Auto-generated method stub
				Trace.Print("*******Error******");
				Trace.Print(ex);
				systemHandler.uncaughtException(thread, ex);
			}
		};
		

		
		EasyTracker.getInstance().setContext(getApplicationContext());
			
		UncaughtExceptionHandler gaHandler = new ExceptionReporter(
			    EasyTracker.getTracker(),
			    GAServiceManager.getInstance(),
			    logHandler);

		 // Make myHandler the new default uncaught exception handler.
		Thread.setDefaultUncaughtExceptionHandler(gaHandler);
	}
	
}
