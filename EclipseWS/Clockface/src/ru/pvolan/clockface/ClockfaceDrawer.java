package ru.pvolan.clockface;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.view.WindowManager;

public class ClockfaceDrawer {
	
	
	
	public ClockfaceDrawer() 
	{
		initPaints();
	}
	
	
	//****************************
	//TODO move outside
	private Bitmap clockfaceBitmap = null;
	
	public Bitmap getClockfaceBitmap()
	{
		if(clockfaceBitmap == null)
		{
			int maxScreenSizeNormalized = Math.max( getScreenWidth()/3, getScreenHeight()/4 );
			clockfaceBitmap = Bitmap.createBitmap(maxScreenSizeNormalized*3, maxScreenSizeNormalized*4, Config.ARGB_8888);
		}
		
		return clockfaceBitmap;
	}
	
	//**********************************
	//paints
	
	private Paint testPaint;
	private Paint textPaint;
	
	private void initPaints()
	{
		testPaint = new Paint();
		testPaint.setColor(Color.RED);
		testPaint.setAntiAlias(true);
		
		textPaint = new Paint();
		textPaint.setColor(Color.WHITE);
		textPaint.setAntiAlias(true);
		textPaint.setTextSize(30);
		textPaint.setTypeface(Typeface.create(textPaint.getTypeface(), Typeface.BOLD));
	}
	
	
	
	
	//******************************
	//draw
	
	
	public void drawClockface(Canvas c)
	{
		String currentTime = new SimpleDateFormat("kk:mm:ss").format(new Date());
		
		int width = c.getWidth();
		int height = c.getHeight();
		
		//c.drawColor(Color.rgb(0xDB, 0xD6, 0xDA ));
		
		
		c.drawColor(Color.BLUE);
		c.drawCircle(width/2, height/2, Math.min(width/2, height/2), testPaint);
		c.drawText(currentTime, 0, height/2, textPaint);
	}
	
	
	//*******************************
	// tools
	
	private int getScreenWidth(){
		if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB_MR2)
		{
			WindowManager wm = (WindowManager) ClockfaceApp.App.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
			return wm.getDefaultDisplay().getWidth();
		}
		else
		{
			WindowManager wm = (WindowManager) ClockfaceApp.App.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
			Point p = new Point();
			wm.getDefaultDisplay().getSize(p);
			return p.x;
		}
	}
	
	private int getScreenHeight(){
		if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB_MR2)
		{
			WindowManager wm = (WindowManager) ClockfaceApp.App.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
			return wm.getDefaultDisplay().getHeight();
		}
		else
		{
			WindowManager wm = (WindowManager) ClockfaceApp.App.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
			Point p = new Point();
			wm.getDefaultDisplay().getSize(p);
			return p.y;
		}
	}
}
