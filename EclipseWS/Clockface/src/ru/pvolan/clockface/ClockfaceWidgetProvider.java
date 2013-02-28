package ru.pvolan.clockface;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ru.pvolan.trace.Trace;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.RemoteViews;

public class ClockfaceWidgetProvider extends AppWidgetProvider 
{
	
	//***************************************************
	//Common
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		
		
        // Perform this loop procedure for each App Widget that belongs to this provider
		for (int appWidgetId : appWidgetIds) 
        {
			/*
           	RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_clockface);
			views.setInt(R.id.widgetRoot, "setBackgroundColor", Color.MAGENTA);

			appWidgetManager.updateAppWidget(appWidgetId, views);
			 */
			updateClockValue(context, appWidgetManager, appWidgetId);
        }
		
		registerNextTick(context);
	}
	
	@Override
	public void onEnabled(Context context) {
		// TODO Auto-generated method stub
		super.onEnabled(context);
		Trace.Print("ClockfaceWidgetProvider.onEnabled");
		registerNextTick(context);
	}
	
	
	@Override
	public void onDisabled(Context context) {
		// TODO Auto-generated method stub
		super.onDisabled(context);
		Trace.Print("ClockfaceWidgetProvider.onDisabled");
		unregisterTick(context);
	}
	
	
	@Override
	public void onReceive(Context context, Intent intent) 
	{
		Trace.Print("ClockfaceWidgetProvider.onReceive " + intent.getAction());
		super.onReceive(context, intent);

		if (getTickActionName()
				.equals(intent.getAction())) 
		{
			onReceiveTick(context, intent);		
		}
	}

	
	//***************************************************
	//Set time
	
	private static String fileToUse = "";
	private final static String tempImageFileName1 = "clockImage1.png";
	private final static String tempImageFileName2 = "clockImage2.png";
	private final static String tempImageFileName3 = "clockImage3.png";
	
	private void updateClockValue(Context context, AppWidgetManager appWidgetManager,	int appWidgetId)
	{
		Trace.Print("ClockfaceWidgetProvider.updateClockValue");
		
		if(fileToUse.equals(tempImageFileName1)){
			fileToUse = tempImageFileName2;
		}
		else if(fileToUse.equals(tempImageFileName2))
		{
			fileToUse = tempImageFileName3;
		}
		else
		{
			fileToUse = tempImageFileName1;
		}
		
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_clockface);
		
		Bitmap b = drawClockface();
		
		try {
		       FileOutputStream out = context.openFileOutput(fileToUse, Context.MODE_WORLD_READABLE);
		       b.compress(Bitmap.CompressFormat.PNG, 100, out);
		} catch (Exception e) {
		       e.printStackTrace();
		}


		
		views.setImageViewUri(R.id.imageClock, Uri.fromFile( context.getFileStreamPath(fileToUse)  ) );

		appWidgetManager.updateAppWidget(appWidgetId, views);
		Trace.Print("ClockfaceWidgetProvider.updateClockValue completed");
	}
	
	
	private Bitmap drawClockface()
	{
		ClockfaceDrawer drawer = ClockfaceApp.App.getClockfaceDrawer();
		Bitmap b = drawer.getClockfaceBitmap();
		Canvas c = new Canvas(b);
		
		drawer.drawClockface(c);		
		
		return b;
	}
	
	
	//***************************************************
	// Ticks
	
	private void registerNextTick(Context context)
	{
		 Trace.Print("ClockfaceWidgetProvider.registerTickUpdates()");
		 AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		    
	     Calendar calendar = Calendar.getInstance();
	     calendar.set(
	    		 calendar.get(Calendar.YEAR), 
	    		 calendar.get(Calendar.MONTH), 
	    		 calendar.get(Calendar.DAY_OF_MONTH), 
	    		 calendar.get(Calendar.HOUR_OF_DAY), 
	    		 calendar.get(Calendar.MINUTE), 
	    		 calendar.get(Calendar.SECOND)
	    		 );
	     calendar.add(Calendar.SECOND, 1);
	     alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), createTickIntent(context));
	}
	
	
	private void unregisterTick(Context context) 
	{
		Trace.Print("ClockfaceWidgetProvider.unregisterTickUpdates()");
		AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(createTickIntent(context));	
	}
	
	
	private void onReceiveTick(Context context, Intent intent) {
		Trace.Print("Tick!");
		registerNextTick(context);
		
		// Get the widget manager and ids for this widget provider, then call the shared
		// clock update method.
		ComponentName thisAppWidget = new ComponentName(context.getPackageName(), getClass().getName());
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
		int ids[] = appWidgetManager.getAppWidgetIds(thisAppWidget);
		for (int appWidgetID: ids) {
			updateClockValue(context, appWidgetManager, appWidgetID);
		}
		
		
	}
	
	
	private PendingIntent createTickIntent(Context context)
	{
		Intent intent = new Intent(getTickActionName());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        return pendingIntent;		
	}

	
	//Duplicated in manifest
	private String getTickActionName() {
		return "ru.pvolan.clockface.TICK_WIDGET_UPDATE";
	}
}
