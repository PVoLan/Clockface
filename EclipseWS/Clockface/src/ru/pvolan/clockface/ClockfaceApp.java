package ru.pvolan.clockface;

import java.lang.Thread.UncaughtExceptionHandler;

import ru.pvolan.trace.Trace;

import android.app.Application;
import android.util.Log;

public class ClockfaceApp extends Application {
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			
			@Override
			public void uncaughtException(Thread thread, Throwable ex) {
				// TODO Auto-generated method stub
				Trace.Print("*******Error******");
				Trace.Print(ex);
			}
		});
	}
}
