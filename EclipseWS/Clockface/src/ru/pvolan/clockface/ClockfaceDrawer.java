package ru.pvolan.clockface;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ru.pvolan.trace.Trace;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class ClockfaceDrawer {
	
	
	
	public ClockfaceDrawer() 
	{
		initDimensions();
		initPaints();		
	}
	
	
	//****************************
	//TODO move outside
	private Bitmap clockfaceBitmap = null;
	
	public Bitmap getClockfaceBitmap()
	{
		if(clockfaceBitmap == null)
		{
			clockfaceBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		}
		
		return clockfaceBitmap;
	}
	
	
	//******************************
	//dimensions
	
	
	private int width;
	private int height;
	private int centerX;
	private int centerY;
	private float face_padding1;
	private float face_padding2;
	private float face_roundings1;
	private float face_roundings2;
	private RectF face1_rect;
	private RectF face2_rect;
	private float face_stroke_width;
	
	private float hourArrow_length;
	private float hourArrow_width;
	
	private float minArrow_length;
	private float minArrow_width;
	
	private float secArrow_length;
	private float secArrow_width;
	
	private float centerPointRadius;
	
	private void initDimensions()
	{
		int maxScreenSizeNormalized = Math.max( getScreenWidth()/3, getScreenHeight()/4 );
		width = maxScreenSizeNormalized * 3;
		height = maxScreenSizeNormalized * 4;
		
		centerX = width/2;
		centerY = height/2;
		
		face_padding1 = width * 0.05f;
		face_padding2 = width * 0.10f;

		face_roundings1 = width/3f;
		face_roundings2 = face_roundings1 - ((face_padding2 - face_padding1));
		
		face1_rect = new RectF(face_padding1, face_padding1, width-face_padding1, height-face_padding1);
		face2_rect = new RectF(face_padding2, face_padding2, width-face_padding2, height-face_padding2);
		
		face_stroke_width = width * 0.03f;
		
		hourArrow_length = width * 0.25f;
		hourArrow_width = width * 0.05f;
		
		minArrow_length = width * 0.30f;
		minArrow_width = width * 0.03f;
		
		secArrow_length = width * 0.32f;
		secArrow_width = width * 0.01f;
		
		centerPointRadius = width * 0.035f;
	}
	
	
	
	//**********************************
	//paints
	
	private Paint testPaint;
	private Paint textPaint;
	
	private Paint faceFillPaint;
	private Paint faceStrokePaint;
	
	private Paint hourStrokePaint;
	private Paint minStrokePaint;
	private Paint secStrokePaint;
	
	private Paint centerPointPaint;
	
	
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
		
		faceFillPaint = new Paint();
		faceFillPaint.setColor(Color.rgb(0xe5, 0xda, 0xcd));
		faceFillPaint.setAntiAlias(true);
		
		faceStrokePaint = new Paint();
		faceStrokePaint.setColor(Color.rgb(0x00, 0x00, 0x60));
		faceStrokePaint.setAntiAlias(true);
		faceStrokePaint.setStyle(Style.STROKE);
		faceStrokePaint.setStrokeWidth(face_stroke_width);
		
		hourStrokePaint = new Paint();
		hourStrokePaint.setColor(Color.rgb(0x00, 0x00, 0x60));
		hourStrokePaint.setAntiAlias(true);
		hourStrokePaint.setStyle(Style.STROKE);
		hourStrokePaint.setStrokeWidth(hourArrow_width);
		hourStrokePaint.setStrokeCap(Cap.ROUND);

		minStrokePaint = new Paint();
		minStrokePaint.setColor(Color.rgb(0x00, 0x00, 0x60));
		minStrokePaint.setAntiAlias(true);
		minStrokePaint.setStyle(Style.STROKE);
		minStrokePaint.setStrokeWidth(minArrow_width);
		minStrokePaint.setStrokeCap(Cap.ROUND);

		secStrokePaint = new Paint();
		secStrokePaint.setColor(Color.rgb(0x00, 0x00, 0x60));
		secStrokePaint.setAntiAlias(true);
		secStrokePaint.setStyle(Style.STROKE);
		secStrokePaint.setStrokeWidth(secArrow_width);
		secStrokePaint.setStrokeCap(Cap.ROUND);
		
		centerPointPaint = new Paint();
		centerPointPaint.setColor(Color.rgb(0x00, 0x00, 0x60));
		centerPointPaint.setAntiAlias(true);
	}
	
	
	
	
	
	
	//******************************
	//draw
	
	
	public void drawClockface(Canvas c)
	{
		//Draw bg
		c.drawRoundRect(face1_rect, face_roundings1, face_roundings1, faceFillPaint);
		c.drawRoundRect(face1_rect, face_roundings1, face_roundings1, faceStrokePaint);
		c.drawRoundRect(face2_rect, face_roundings2, face_roundings2, faceStrokePaint);
		
		Calendar now = Calendar.getInstance();
				
		//Draw hour
		double hourAngle = now.get(Calendar.HOUR) * ((2 * Math.PI) / 12); //clean hour
		hourAngle += now.get(Calendar.MINUTE) * ((2 * Math.PI) / (12 * 60)); //hour + minute
		
		float hourX = (float)(Math.sin(hourAngle) * hourArrow_length + centerX);
		float hourY = (float)(centerY - Math.cos(hourAngle) * hourArrow_length);
		
		c.drawLine(centerX, centerY, hourX, hourY, hourStrokePaint);
		
		//draw min
		double minAngle = now.get(Calendar.MINUTE) * ((2 * Math.PI) / 60); 
		
		float minX = (float)(Math.sin(minAngle) * minArrow_length + centerX);
		float minY = (float)(centerY - Math.cos(minAngle) * minArrow_length);
		
		c.drawLine(centerX, centerY, minX, minY, minStrokePaint);
		
		//draw sec
		double secAngle = now.get(Calendar.SECOND) * ((2 * Math.PI) / 60); 
		
		float secX = (float)(Math.sin(secAngle) * secArrow_length + centerX);
		float secY = (float)(centerY - Math.cos(secAngle) * secArrow_length);
		
		c.drawLine(centerX, centerY, secX, secY, secStrokePaint);
		
		//draw center point
		c.drawCircle(centerX, centerY, centerPointRadius, centerPointPaint);
	}
	
	
	//*******************************
	//TODO move outside tools
	
	private int getScreenWidth(){
		WindowManager wm = (WindowManager) ClockfaceApp.App.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
		if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB_MR2)
		{			
			return wm.getDefaultDisplay().getWidth();
		}
		else
		{
			Point p = new Point();
			wm.getDefaultDisplay().getSize(p);
			return p.x;
		}
	}
	
	private int getScreenHeight()
	{
		WindowManager wm = (WindowManager) ClockfaceApp.App.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
		if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB_MR2)
		{
			return wm.getDefaultDisplay().getHeight();
		}
		else
		{
			Point p = new Point();
			wm.getDefaultDisplay().getSize(p);
			return p.y;
		}
	}
	
	
	private float densityFactor = 0f;
	private float dipToPx(float dip)
	{
		if(densityFactor == 0)
		{
			WindowManager wm = (WindowManager) ClockfaceApp.App.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
			DisplayMetrics dm = new DisplayMetrics();
			
			wm.getDefaultDisplay().getMetrics(dm);
			densityFactor = dm.density;
		}
		
		return dip * densityFactor;
	}
}
