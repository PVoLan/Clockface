package ru.pvolan.clockface;

import java.util.Locale;

import android.content.Context;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.GAServiceManager;

import ru.pvolan.trace.Trace;

public class GAHelper 
{
	
	public static void TrackWidgetUpdate(Context context, Object providerObject)
	{
		//TODO Actual app version
		String className = providerObject.getClass().getSimpleName();
		String androidVersion = "X.X";
		String appVersion = "0.0";
		String buildType = "DEV";
		String viewStr = String.format(Locale.US, "%s/%s/%s/%s", buildType, appVersion, className, androidVersion);
		
		//Trace.Print(viewStr);
		
		EasyTracker.getInstance().setContext(context);
		EasyTracker.getTracker().sendView(viewStr);
	}
}
