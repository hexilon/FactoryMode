package com.mediatek.factorymode.touchscreen;

import com.mediatek.factorymode.Utils;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import com.mediatek.factorymode.R;
import android.view.KeyEvent;


public class LineTest extends Activity{

	static int CameraTestStatus = 0;
	 int CameraTestStatus1 = 0;
	 int CameraTestStatus2 = 0;
	 int CameraTestStatus3 = 0;
	 int CameraTestStatus4 = 0;
	 int CameraTestStatus5 = 0;
	int center;
	Canvas mcanvas;
	int innerCircle;
	int ringWidth;
	
	int point_x;
	int point_y;
	int radius = 0;
	 int alpha = 255;  
	boolean upFlag = false; 
    SharedPreferences localSharedPreferences;
 @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		//full screen
     localSharedPreferences = getSharedPreferences("FactoryMode", 0);
		
		
//		if(FeatureOption.FACTORYMODE_TPTEST_MODE1){
//		
//		
//		setContentView(new RingView(this));
//		
//		}else if(FeatureOption.FACTORYMODE_TPTEST_MODE2 || FeatureOption.FACTORYMODE_TPTEST_MODE3){
			
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,	
				                  WindowManager.LayoutParams.FLAG_FULLSCREEN);
			setContentView(new RingView(this));
			
//		}else{
//				
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,	
//				                  WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		if(FeatureOption.FACTORYMODE_480x800){
//		  setContentView(new SimpleView51(this));
//		}else{
//			setContentView(new SimpleView5(this));
//		}
//		}
			
	}
  public void fullScreenChange() {
	  SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
	  boolean fullScreen = mPreferences.getBoolean("fullScreen", false);
	  WindowManager.LayoutParams attrs = getWindow().getAttributes(); 
	  
	  if (fullScreen) {
	  attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN); 
	  getWindow().setAttributes(attrs); 
	  
	  getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
	  mPreferences.edit().putBoolean("fullScreen", false).commit() ;
	  } else {
	  attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN; 
	  getWindow().setAttributes(attrs); 
	  getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS); 
	  mPreferences.edit().putBoolean("fullScreen", true).commit();
	  }
	  }
  
 protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
//		setContentView(R.layout.test_result_tp);
		
		setContentView(R.layout.test_result);
//		Button success = (Button)findViewById(R.id.Button_tpSuccess);
//		Button fail = (Button)findViewById(R.id.Button_tpFail);
		

		Button success = (Button)findViewById(R.id.Button_Success);
		Button fail = (Button)findViewById(R.id.Button_Fail);
		
		success.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
        Utils.SetPreferences(LineTest.this, localSharedPreferences, R.string.touchscreen_name, "success");
				Intent intent = new Intent(); 
		        intent.setAction("android.intent.action.FACTORY_TEST_MAIN");
		        setResult(RESULT_OK,intent);
		        CameraTestStatus  = 1;
		        Intent mIntent = getIntent();
				boolean fl = mIntent.getBooleanExtra("textall", false);
				if(fl == true){
					Intent miIntent = new Intent();
					miIntent.setAction("android.intent.action.SIMCARD_TEST");
					miIntent.putExtra("textall", true);
					startActivityForResult(miIntent, RESULT_OK);
				}
		        finish();
			}
		});
		fail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
        Utils.SetPreferences(LineTest.this, localSharedPreferences, R.string.touchscreen_name, "failed");
				Intent intent = new Intent(); 
		        intent.setAction("android.intent.action.FACTORY_TEST_MAIN");
		        setResult(RESULT_OK,intent);
		        CameraTestStatus  = -1;
		        Intent mIntent = getIntent();
				boolean fl = mIntent.getBooleanExtra("textall", false);
				if(fl == true){
					Intent miIntent = new Intent();
					miIntent.setAction("android.intent.action.SIMCARD_TEST");
					miIntent.putExtra("textall", true);
					startActivityForResult(miIntent, RESULT_OK);
				}
				finish();
			}
		});
		
	}
// public void onAttachedToWindow() {
//		// TODO Auto-generated method stub
//		this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
//		super.onAttachedToWindow();
//	}
 
 public void onBackPressed() {
		// TODO Auto-generated method stub
//		super.onBackPressed();
		Intent intent = new Intent(); 
     intent.setAction("android.intent.action.FACTORY_TEST_MAIN");
     setResult(RESULT_OK,intent);
     CameraTestStatus  = -1;
//     Intent mIntent = getIntent();
//		boolean fl = mIntent.getBooleanExtra("textall", false);
//		if(fl == true){
//			Intent miIntent = new Intent();
//			miIntent.setAction("android.intent.action.SIMCARD_TEST");
//			miIntent.putExtra("textall", true);
//			startActivityForResult(miIntent, RESULT_OK);
//		}
     onPause();
		
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

class RingView extends View {

	private final  Paint paint1;
	private final  Paint paint2;
	private final  Paint paint3;
	private final  Paint paint4;
	private final  Paint paint5;
	
	private final Context context;
	
	public RingView(Context context) {
		
		// TODO Auto-generated constructor stub
		this(context, null);
	}

	public RingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.paint1 = new Paint();
		this.paint1.setARGB(155, 167, 190, 206);
		this.paint1.setAntiAlias(true); 
		this.paint1.setStyle(Paint.Style.STROKE); 
		
		this.paint2 = new Paint();
		this.paint2.setARGB(155, 167, 190, 206);
		this.paint2.setAntiAlias(true); 
		this.paint2.setStyle(Paint.Style.STROKE); 
		
		this.paint3 = new Paint();
		this.paint3.setARGB(155, 167, 190, 206);
		this.paint3.setAntiAlias(true); 
		this.paint3.setStyle(Paint.Style.STROKE); 
		
		this.paint4 = new Paint();
		this.paint4.setARGB(155, 167, 190, 206);
		this.paint4.setAntiAlias(true); 
		this.paint4.setStyle(Paint.Style.STROKE);  
		
		this.paint5 = new Paint();
		this.paint5.setARGB(155, 167, 190, 206);
		this.paint5.setAntiAlias(true); 
		this.paint5.setStyle(Paint.Style.STROKE); 
		
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		center = getWidth()/2;
		 innerCircle = dip2px(context, 33); 
		 ringWidth = dip2px(context, 5); 
		mcanvas = canvas;
		
		
		
		alpha -= 3;
		canvas.drawColor(Color.GRAY);
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);	
		paint.setAlpha(alpha);		
		paint.setStyle(Paint.Style.FILL);
		paint.setAntiAlias(true);
		if (upFlag) {
			++radius;
			if (radius > 26) {
				upFlag = false;
				radius = 0;
				alpha = 255;
			}
			if (radius == 18) {
				alpha = 100;
			}
			canvas.drawCircle(point_x, point_y, radius, paint);
			invalidate();
		}
		
		
		
		
		
		this.paint1.setStrokeWidth(2);
		canvas.drawCircle(center,center/2, innerCircle, this.paint1);
		

		////////////////////////////////////////
		
				
				this.paint2.setStrokeWidth(2);
				canvas.drawCircle(center/3,3*center/2, innerCircle, this.paint2);
				
		////////////
				
			
				
				this.paint3.setStrokeWidth(2);
				canvas.drawCircle(center,3*center/2, innerCircle, this.paint3);
				
		////////////
				
				
				
			
				
				this.paint4.setStrokeWidth(2);
				canvas.drawCircle(5*center/3,3*center/2, innerCircle, this.paint4);
				
		////////////
				
				
				
			
				
				this.paint5.setStrokeWidth(2);
				canvas.drawCircle(center,5*center/2, innerCircle, this.paint5);
				
		
		super.onDraw(canvas);
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		
		point_x = (int) event.getX();
		point_y = (int) event.getY();
		//wangshengyuan add 
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			upFlag = false;
			return false;
		}
		
		if (event.getAction() == MotionEvent.ACTION_UP) {
			upFlag = true;
		}
		
		
		
		
		
		float X = event.getX();
		float Y = event.getY();
		Log.e("lyaotao", "X ="+X);
		Log.e("lyaotao", "Y ="+Y);
		
		if(((int)center-33)< X && X <((int)center+33) &&
				((int)center/2-43)< Y && Y <((int)center/2+43) ){
			CameraTestStatus1 = 1;
			this.paint1.setColor(Color.GREEN);
			Log.e("lyaotao", "CameraTestStatus1");
		}
		
		if(((int)center/3-33)< X && X <((int)center/3+33) &&
				((int)3*center/2-43)< Y && Y <((int)3*center/2+43) ){
			CameraTestStatus2 = 1;
			this.paint2.setColor(Color.GREEN);
			Log.e("lyaotao", "CameraTestStatus2");
		}
		
		if(((int)center-33)< X && X <((int)center+33) &&
				((int)3*center/2-43)< Y && Y <((int)3*center/2+43) ){
			CameraTestStatus3 = 1;
			this.paint3.setColor(Color.GREEN);
			Log.e("lyaotao", "CameraTestStatus3");
		}
		
		if(((int)5*center/3-33)< X && X <((int)5*center/3+33) &&
				((int)3*center/2-43)< Y && Y <((int)3*center/2+43) ){
			CameraTestStatus4 = 1;
			this.paint4.setColor(Color.GREEN);
			Log.e("lyaotao", "CameraTestStatus4");
		}
		
		if(((int)center - 33)< X && X <((int)center+33) &&
				((int)5*center/2-43)< Y && Y <((int)5*center/2+43) ){
			CameraTestStatus5 = 1;
			this.paint5.setColor(Color.GREEN);
			Log.e("lyaotao", "CameraTestStatus5");
		}
		
		if(CameraTestStatus1 == 1 && CameraTestStatus2 == 1 &&
				CameraTestStatus3 == 1 && CameraTestStatus4 == 1 && CameraTestStatus5 == 1){

			//liuxinwen add 20170315 for [bug #14] start
			mHandler.removeMessages(MSG_SET_CTPVIEW);
			mHandler.sendEmptyMessage(MSG_SET_CTPVIEW);
			//liuxinwen add 20170315 for [bug #14] end

			/*	if(FeatureOption.LCM_480_854){
					setContentView(new CtpTest(TPTest.this,854,480));
				}else if(FeatureOption.LCM_480_800){
					setContentView(new CtpTest(TPTest.this,800,480));
				}else if(FeatureOption.LCM_540_960){
					setContentView(new CtpTest(TPTest.this,960,540));
				}else if(FeatureOption.LCM_720_1280){
					setContentView(new CtpTest(TPTest.this,1280,720));
				}else{
					setContentView(new CtpTest(TPTest.this,800,480));
				}*/
			
//			}
		}
		invalidate();
//		return super.onTouchEvent(event);
		return true;
	}

	
	public  int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
}
class CtpTest extends View
{

	private int mov_x;
	private int mov_y;
	private float curr_mov_x;
	private float curr_mov_y;
	private Paint paint;
	private Paint[] mPaint;
	private boolean[] flag;
	private Canvas canvas;
	private Bitmap bitmap;
	private int mHeight;
	private int mWidth;
	private int rNum;
	private int cNum;
	private int smallBoxW;
	private int smallBoxH;
	private int gapW;
	private int gapH;
	private int topPadding;
	private int leftPadding;
	private int offset = 0;

	public CtpTest(Context context, int height, int width)
	{
		super(context);
		mHeight = height;
		mWidth = width;

		bitmap = Bitmap.createBitmap(mWidth,mHeight,Bitmap.Config.ARGB_8888); 
		paint = new Paint(Paint.DITHER_FLAG);
		canvas = new Canvas();
		canvas.setBitmap(bitmap);

		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(5);
		paint.setColor(Color.RED);
		paint.setAntiAlias(true);
		//??????????????,???????????????,????????????
		if( mWidth == 480 && mHeight == 800){
			smallBoxH = 48;
			smallBoxW = 48;
			gapW = 5;
			gapH = 5;
		}else if (mWidth == 480 && mHeight ==854){
			smallBoxH = 45;
			smallBoxW = 48;
			gapW = 5;
			gapH = 5;
		}else if (mWidth == 640 && mHeight ==1184){
			smallBoxH = 46;
			smallBoxW = 44;
			gapW = 5;
			gapH = 5;
		}else if (mWidth == 720 && mHeight == 1280){
			smallBoxW = 50;
			smallBoxH = 46;
			gapW = 5;
			gapH = 5;
		}else if (mWidth == 1080 && mHeight == 1920){
			smallBoxW = 67;
			smallBoxH = 66;
			gapW = 5;
			gapH = 5;
		}else{
			smallBoxH = 48;
			smallBoxW = 48;
			gapW = 5;
			gapH = 5;
		}

		cNum = mHeight / (smallBoxH + gapH);  //??????
		rNum = mWidth / (smallBoxW + gapW);	//??????
		offset = ((smallBoxW + gapW) * ((rNum - 3)/2 - 1)) / ((cNum - 3)/2 - 1); //?????????

		topPadding = (Math.max(0,mHeight - cNum * (smallBoxH + gapH)) + gapH) / 2;
		leftPadding = (Math.max(0,mWidth - rNum * (smallBoxW + gapW)) + gapW) / 2;

		int tatol = (cNum + rNum) * 3 - 9 + (cNum - 3) * 2;

		mPaint = new Paint[tatol];
		flag = new boolean[tatol];

		for(int i = 0; i < tatol; i++)
		{
			mPaint[i] = new Paint();
			mPaint[i].setStyle(Style.FILL);
			mPaint[i].setStrokeWidth(1);
			mPaint[i].setColor(Color.GRAY);
			mPaint[i].setAntiAlias(true);
			flag[i] = false;
		}
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		// super.onDraw(canvas);

		canvas.drawBitmap(bitmap,0,0,null);
		int k = 0;
		for(int i = 0; i < cNum; i++)
		{
			for(int j = 0; j < rNum; j++)
			{
				int mid = (int)(cNum / 2) + cNum % 2 - 1;
				if(i == 0 || i == (cNum - 1) || i == mid)
				{
					int top = leftPadding + j * (smallBoxW + gapW);
					int left = topPadding + i * (smallBoxH + gapH);
					int bottom = top + smallBoxW;
					int right = left + smallBoxH;
					if(pointList!=null){
						for(int m=0;m<pointList.size();m++){
							if(pointList.get(m).y > left && pointList.get(m).y < right
									&& pointList.get(m).x > top && pointList.get(m).x < bottom
									&& !flag[k])
							{
								flag[k] = true;
								mPaint[k].setColor(Color.GREEN);
							}
						}
					}else if(currPoint.y > left && currPoint.y < right
							&& currPoint.x > top && currPoint.x < bottom
							&& !flag[k])
					{
						flag[k] = true;
						mPaint[k].setColor(Color.GREEN);
					}
					canvas.drawRect(top,left,top + smallBoxW,left + smallBoxH,
							mPaint[k]);
					k++;
				} else
				{
					int midR = (int)(rNum / 2) + rNum % 2 - 1;
					if(j == 0 || j == (rNum - 1) || j == midR)
					{
						int top = leftPadding + j * (smallBoxW + gapW);
						int left = topPadding + i * (smallBoxH + gapH);
						int bottom = top + smallBoxW;
						int right = left + smallBoxH;
						if(pointList!=null){
							for(int m=0;m<pointList.size();m++){
								if(pointList.get(m).y > left && pointList.get(m).y < right
										&& pointList.get(m).x > top && pointList.get(m).x < bottom
										&& !flag[k])
								{
									flag[k] = true;
									mPaint[k].setColor(Color.GREEN);
								}
							}
						}else if(currPoint.y > left && currPoint.y < right
								&& currPoint.x > top && currPoint.x < bottom
								&& !flag[k])
						{
							flag[k] = true;
							mPaint[k].setColor(Color.GREEN);
						}
						canvas.drawRect(top,left,top + smallBoxW,left
								+ smallBoxH,mPaint[k]);
						k++;
					}else if(j == 1){
						int top;
						if(i > mid){
							top = leftPadding + j * (smallBoxW + gapW) + (cNum - i - 2) * offset;
						}else{
							top = leftPadding + j * (smallBoxW + gapW) + (i - 1) * offset;
						}
						int left = topPadding + i * (smallBoxH + gapH);
						int bottom = top + smallBoxW;
						int right = left + smallBoxH;
						if(pointList!=null){
							for(int m=0;m<pointList.size();m++){
								if(pointList.get(m).y > left && pointList.get(m).y < right
										&& pointList.get(m).x > top && pointList.get(m).x < bottom
										&& !flag[k])
								{
									flag[k] = true;
									mPaint[k].setColor(Color.GREEN);
								}
							}
						}else if(currPoint.y > left && currPoint.y < right
								&& currPoint.x > top && currPoint.x < bottom
								&& !flag[k])
						{
							flag[k] = true;
							mPaint[k].setColor(Color.GREEN);
						}
						canvas.drawRect(top,left,top + smallBoxW,left
								+ smallBoxH,mPaint[k]);
						k++;
					}else if(j == (rNum -2)){
						int top;
						if(i > mid){
							top = leftPadding + j * (smallBoxW + gapW) - (cNum - i - 2) * offset;
						}else{
							top = leftPadding + j * (smallBoxW + gapW) - (i - 1) * offset;
						}
						int left = topPadding + i * (smallBoxH + gapH);
						int bottom = top + smallBoxW;
						int right = left + smallBoxH;
						if(pointList!=null){
						for(int m=0;m<pointList.size();m++){
							if(pointList.get(m).y > left && pointList.get(m).y < right
									&& pointList.get(m).x > top && pointList.get(m).x < bottom
									&& !flag[k])
							{
								flag[k] = true;
								mPaint[k].setColor(Color.GREEN);
							}
						}
					}else if(currPoint.y > left && currPoint.y < right
							&& currPoint.x > top && currPoint.x < bottom
							&& !flag[k])
					{
							flag[k] = true;
							mPaint[k].setColor(Color.GREEN);
						}
						canvas.drawRect(top,left,top + smallBoxW,left
								+ smallBoxH,mPaint[k]);
						k++;
					}
				}

			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if(event.getAction() == MotionEvent.ACTION_MOVE)
		{
			canvas.drawLine(mov_x,mov_y,event.getX(),event.getY(),paint);
			currPoint.x = (int)event.getX();
			currPoint.y = (int)event.getY();
			pointList = getPoints(prevPoint,currPoint,smallBoxW);
			prevPoint.x = (int)event.getX();
			prevPoint.y = (int)event.getY();
			invalidate();
		}
		if(event.getAction() == MotionEvent.ACTION_DOWN)
		{
			mov_x = (int)event.getX();
			mov_y = (int)event.getY();
			prevPoint.x = (int)event.getX();
			prevPoint.y = (int)event.getY();
		}

		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			currPoint.x = 0;
			currPoint.y = 0;
			prevPoint.x = 0;
			prevPoint.y = 0;
			int i = 0;
			for(i = 0; i < flag.length; i++)
			{
				if(!flag[i])
				{
					break;
				}
			}
			if(i == flag.length)
			{
         onPause();
			}
		}

		mov_x = (int)event.getX();
		mov_y = (int)event.getY();
		return true;
	}

}
@Override
protected void onDestroy() {
	// TODO Auto-generated method stub
	super.onDestroy();
	
	
	Intent intent = new Intent(); 
    intent.setAction("android.intent.action.FACTORY_TEST_MAIN");
    setResult(RESULT_OK,intent);
    
	
}

@Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	CameraTestStatus  = -1;
}

	//liuxinwen add 20170315 for [bug #14] start 
	private static final int MSG_SET_CTPVIEW = 0;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what) {
			case MSG_SET_CTPVIEW:
				setCtpView();
				break;
				
			}
		}
		
	};
	
	private void setCtpView() {
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int mScreenWidth = metrics.widthPixels;
		int mScreenHeight = metrics.heightPixels;
		setContentView(new CtpTest(LineTest.this,mScreenHeight,mScreenWidth));
	}
	//liuxinwen add 20170315 for [bug #14] end

	//????TP???,???????????????????,???????????????????????????
	public Point prevPoint = new Point(0, 0);
	public Point currPoint = new Point(0, 0);;
	private ArrayList<Point> pointList;
	private ArrayList<Point> getPoints(Point prevPoint,Point currPoint,int smallBoxW){
		ArrayList<Point> arr = new ArrayList<Point>();
		int xlen = Math.abs(currPoint.x - prevPoint.x);
		int ylen = Math.abs(currPoint.y - prevPoint.y);
		int points = Math.max(xlen/smallBoxW, ylen/smallBoxW);
		if(points >= 1){
			arr.add(prevPoint);
			for(int i = 1; i <= points; i ++){
				arr.add(new Point(prevPoint.x + xlen*i/(points + 1),prevPoint.y + ylen*i/(points + 1)));
			}
			arr.add(currPoint);
			return arr;
		}else{
			return null;
		}
	}
}

