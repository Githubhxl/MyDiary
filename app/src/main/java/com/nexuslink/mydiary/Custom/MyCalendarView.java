package com.nexuslink.mydiary.Custom;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Region;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.nexuslink.mydiary.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyCalendarView extends View {
	final String month[]={"January","February","March","April","May","June","July","August","September","October","November","December"};
	final String week[]={"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
	int currentPage=0;
	//文字和折叠区域颜色
	final  int COLOR=getResources().getColor(R.color.colorPrimary);
	SimpleDateFormat monthsdf = new SimpleDateFormat("MM");
	SimpleDateFormat daySDF=new SimpleDateFormat("dd");
	Date mDate;
	Calendar mCalendar;

	private static final int LAST_BITMAP=0;
	private static final int CURRENT_BITMAP=1;
	private static final int NEXT_BITMAP=2;


	boolean isAnimation=false;
	boolean isTopRight=false;
	boolean isBottomLeft=false;
    private int mWidth = 480;
    private int mHeight = 800;
    private int mCornerX =0;
    private int mCornerY = 0;
    private Path mPath0;
    private Path mPath1;
    Bitmap mCurPageBitmap = null;
    Bitmap mCurPageBackBitmap = null;
    Bitmap mNextPageBitmap = null;

    PointF mTouch = new PointF();
    PointF mBezierStart1 = new PointF();
    PointF mBezierControl1 = new PointF();
    PointF mBeziervertex1 = new PointF();
    PointF mBezierEnd1 = new PointF();
    PointF mBezierStart2 = new PointF();
    PointF mBezierControl2 = new PointF();
    PointF mBeziervertex2 = new PointF();
    PointF mBezierEnd2 = new PointF();

    float mMiddleX;
    float mMiddleY;
    float mDegrees;
    float mTouchToCornerDis;

    boolean mIsRTandLB;
    float mMaxLength = (float) Math.hypot(mWidth, mHeight);
    int[] mBackShadowColors;
    int[] mFrontShadowColors;
    GradientDrawable mBackShadowDrawableLR;
    GradientDrawable mBackShadowDrawableRL;
    GradientDrawable mFolderShadowDrawableLR;
    GradientDrawable mFolderShadowDrawableRL;
   
    GradientDrawable mFrontShadowDrawableHBT;
    GradientDrawable mFrontShadowDrawableHTB;
    GradientDrawable mFrontShadowDrawableVLR;
    GradientDrawable mFrontShadowDrawableVRL;

    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mBitmapPaint;
    Paint paint;

    public MyCalendarView(Context context) {
		super(context);

    }

	public MyCalendarView(Context context, AttributeSet attributeSet){
		super(context,attributeSet);

	}
    void init(){
		mDate=new Date();
		mCalendar= Calendar.getInstance();
		mCalendar.setTime(mDate);


		mPath0 = new Path();
		mPath1 = new Path();

		createDrawable();

		mBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
		mCanvas = new Canvas(mBitmap);
		mBitmapPaint = new Paint(Paint.DITHER_FLAG);

		mCurPageBitmap = makeBitmap(CURRENT_BITMAP);
		mNextPageBitmap =mCurPageBitmap;

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mWidth=w;
		mHeight=h;
		mMaxLength = (float) Math.hypot(mWidth, mHeight);
		init();
	}

	private void calcCornerXY(float x, float y) {
	if (x <= mWidth / 2)
	    mCornerX = 0;
	else
	    mCornerX = mWidth;
	if (y <= mHeight / 2)
	    mCornerY = 0;
	else
	    mCornerY = mHeight;
	if ((mCornerX == 0 && mCornerY == mHeight)
		|| (mCornerX == mWidth && mCornerY == 0))
	    mIsRTandLB = true;
	else
	    mIsRTandLB = false;
    }
	boolean isMyNeedEvent(MotionEvent event){
		float x=event.getX();
		float y=event.getY();
		if((x>mWidth/2&&y<mHeight/2)){
			isTopRight=true;
		}
		if(x<mWidth/2&&y>mHeight/2){
			isBottomLeft=true;
		}
		return isTopRight||isBottomLeft;
	}
	void resetModel(){
		isBottomLeft=false;
		isTopRight=false;
	}
    @Override
    public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if(!isMyNeedEvent(event)||isAnimation){
				return false;
			}
			if(isTopRight){
				mNextPageBitmap=makeBitmap(NEXT_BITMAP);
			}else {
				mNextPageBitmap=makeBitmap(LAST_BITMAP);
			}
			mCanvas.drawColor(COLOR);
			mTouch.x = event.getX();
			mTouch.y = event.getY();
			calcCornerXY(mTouch.x, mTouch.y);
			this.postInvalidate();
		}
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			mCanvas.drawColor(COLOR);
			mTouch.x = event.getX();
			mTouch.y = event.getY();
			this.postInvalidate();
		}

		if (event.getAction() == MotionEvent.ACTION_UP) {
			mCanvas.drawColor(0xFFAAAAAA);
			mTouch.x = event.getX();
			mTouch.y = event.getY();
			//this.postInvalidate();
			if(isTopRight){
				if(mTouch.x<mWidth/2||mTouch.y>mWidth/2){
					nextAnimation();

				}else {
					currentPage--;
					TRCloseAnimation();

				}
			}else if(isBottomLeft){
				if(mTouch.x>mWidth/2||mTouch.y<(mHeight-mWidth/2)){
					lastAnimation();
				}else {
					BLCloseAnimation();
					currentPage++;
				}
			}
			resetModel();
		}
	return true;
    }

	private void nextAnimation() {
		isAnimation=true;
		final float originX=mTouch.x;
		final float originY=mTouch.y;
		final float endY=mHeight*2;
		final float dtsY=(endY-originY);
		final float dtsX=(mWidth-originX);
		ValueAnimator animator= ValueAnimator.ofFloat(originY,mHeight*2);
		animator.setDuration(400);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator valueAnimator) {
				mTouch.y= (float) valueAnimator.getAnimatedValue();
				float offset=1-(endY-mTouch.y)/dtsY;
				mTouch.x=originX+dtsX*offset;
				mCanvas.drawColor(COLOR);
				invalidate();
				if(offset==1){
					isAnimation=false;
					mCurPageBitmap=mNextPageBitmap;
					mTouch.x=mCornerX;
					mTouch.y=mCornerY;
					postInvalidate();
				}
			}
		});

		animator.start();
	}


	private void lastAnimation() {
		isAnimation=true;
		final float originX=mTouch.x;
		final float originY=mTouch.y;
		final float endY=-mHeight;
		final float dtsY=(originY-endY);
		final float dtsX=originX;
		ValueAnimator animator= ValueAnimator.ofFloat(originY,endY);
		animator.setDuration(400);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator valueAnimator) {
				mTouch.y= (float) valueAnimator.getAnimatedValue();
				float offset=(mTouch.y-endY)/dtsY;
				mTouch.x=dtsX*offset;
				mCanvas.drawColor(COLOR);
				invalidate();
				if(offset==0){
					isAnimation=false;
					mCurPageBitmap=mNextPageBitmap;
					mTouch.x=mCornerX;
					mTouch.y=mCornerY;
					postInvalidate();
				}
			}
		});

		animator.start();
	}


	private void TRCloseAnimation() {

		isAnimation=true;
		final float originX=mTouch.x;
		final float originY=mTouch.y;
		ValueAnimator animator= ValueAnimator.ofFloat(originX,mWidth);
		animator.setDuration(300);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator valueAnimator) {
				mTouch.x= (float) valueAnimator.getAnimatedValue();
				mTouch.y=(mWidth-mTouch.x)*originY/(mWidth-originX);
				mCanvas.drawColor(COLOR);
				invalidate();
				if(mTouch.x==mWidth){
					isAnimation=false;
					mTouch.x=mCornerX;
					mTouch.y=mCornerY;
					postInvalidate();
				}
			}
		});

		animator.start();
	}
	private void BLCloseAnimation() {

		isAnimation=true;
		final float originX=mTouch.x;
		final float originY=mTouch.y;
		final float dstY=mHeight-originY;
		ValueAnimator animator= ValueAnimator.ofFloat(originX,0);
		animator.setDuration(300);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator valueAnimator) {
				mTouch.x= (float) valueAnimator.getAnimatedValue();
				float offset=1-mTouch.x/originX;
				mTouch.y=originY+dstY*offset;
				mCanvas.drawColor(COLOR);
				invalidate();
				if(mTouch.x==0){
					isAnimation=false;
					mTouch.x=mCornerX;
					mTouch.y=mCornerY;
					postInvalidate();
				}
			}
		});

		animator.start();
	}
  

    public PointF getCross(PointF P1, PointF P2, PointF P3, PointF P4) {
	PointF CrossP = new PointF();
	// ��Ԫ����ͨʽ�� y=ax+b
	float a1 = (P2.y - P1.y) / (P2.x - P1.x);
	float b1 = ((P1.x * P2.y) - (P2.x * P1.y)) / (P1.x - P2.x);

	float a2 = (P4.y - P3.y) / (P4.x - P3.x);
	float b2 = ((P3.x * P4.y) - (P4.x * P3.y)) / (P3.x - P4.x);
	CrossP.x = (b2 - b1) / (a1 - a2);
	CrossP.y = a1 * CrossP.x + b1;
	return CrossP;
    }

    private void calcPoints() {

	mMiddleX = (mTouch.x + mCornerX) / 2;
	mMiddleY = (mTouch.y + mCornerY) / 2;

	mBezierControl1.x = mMiddleX - (mCornerY - mMiddleY)
		* (mCornerY - mMiddleY) / (mCornerX - mMiddleX);
	mBezierControl1.y = mCornerY;
	mBezierControl2.x = mCornerX;
	mBezierControl2.y = mMiddleY - (mCornerX - mMiddleX)
		* (mCornerX - mMiddleX) / (mCornerY - mMiddleY);


	mBezierStart1.x = mBezierControl1.x - (mCornerX - mBezierControl1.x)
		/ 2;
	mBezierStart1.y = mCornerY;

	mBezierStart2.x = mCornerX;
	mBezierStart2.y = mBezierControl2.y - (mCornerY - mBezierControl2.y)
		/ 2;



	mTouchToCornerDis = (float) Math.hypot((mTouch.x - mCornerX),
		(mTouch.y - mCornerY));

	mBezierEnd1 = getCross(mTouch, mBezierControl1, mBezierStart1,
		mBezierStart2);
	mBezierEnd2 = getCross(mTouch, mBezierControl2, mBezierStart1,
		mBezierStart2);



	mBeziervertex1.x = (mBezierStart1.x + 2 * mBezierControl1.x + mBezierEnd1.x) / 4;
	mBeziervertex1.y = (2 * mBezierControl1.y + mBezierStart1.y + mBezierEnd1.y) / 4;
	mBeziervertex2.x = (mBezierStart2.x + 2 * mBezierControl2.x + mBezierEnd2.x) / 4;
	mBeziervertex2.y = (2 * mBezierControl2.y + mBezierStart2.y + mBezierEnd2.y) / 4;
    }

    private void drawCurrentPageArea(Canvas canvas, Bitmap bitmap, Path path) {
	mPath0.reset();
	mPath0.moveTo(mBezierStart1.x, mBezierStart1.y);
	mPath0.quadTo(mBezierControl1.x, mBezierControl1.y, mBezierEnd1.x,
		mBezierEnd1.y);
	mPath0.lineTo(mTouch.x, mTouch.y);
	mPath0.lineTo(mBezierEnd2.x, mBezierEnd2.y);
	mPath0.quadTo(mBezierControl2.x, mBezierControl2.y, mBezierStart2.x,
		mBezierStart2.y);
	mPath0.lineTo(mCornerX, mCornerY);
	mPath0.close();

	canvas.save();
	canvas.clipPath(path, Region.Op.XOR);
	canvas.drawBitmap(bitmap, 0, 0, null);
	canvas.restore();
    }

    private void drawNextPageAreaAndShadow(Canvas canvas, Bitmap bitmap) {
	mPath1.reset();
	mPath1.moveTo(mBezierStart1.x, mBezierStart1.y);
	mPath1.lineTo(mBeziervertex1.x, mBeziervertex1.y);
	mPath1.lineTo(mBeziervertex2.x, mBeziervertex2.y);
	mPath1.lineTo(mBezierStart2.x, mBezierStart2.y);
	mPath1.lineTo(mCornerX, mCornerY);
	mPath1.close();
	mDegrees = (float) Math.toDegrees(Math.atan2(mBezierControl1.x
		- mCornerX, mBezierControl2.y - mCornerY));
	float f5 = mTouchToCornerDis /4;
	int leftx;
	int rightx;
	GradientDrawable mBackShadowDrawable;
	if(mIsRTandLB)
	{	   
	    leftx=(int)(mBezierStart1.x - 1);
	    rightx= (int)(mBezierStart1.x +mTouchToCornerDis /4+ 1);
	    mBackShadowDrawable=mBackShadowDrawableLR;
	}else
	{	    
	    leftx=(int)(mBezierStart1.x - mTouchToCornerDis/4 - 1);
            rightx= (int) mBezierStart1.x + 1;
	    mBackShadowDrawable=mBackShadowDrawableRL;
	}


	canvas.save();
	canvas.clipPath(mPath0);
	canvas.clipPath(mPath1, Region.Op.INTERSECT);
	canvas.drawBitmap(bitmap, 0, 0, null);
	canvas.rotate(mDegrees, mBezierStart1.x, mBezierStart1.y);
	mBackShadowDrawable.setBounds(leftx,
		(int)mBezierStart1.y, rightx,
		(int) (mMaxLength + mBezierStart1.y));
	mBackShadowDrawable.draw(canvas);	
	canvas.restore();
    }

    public void setBitmaps(Bitmap bm1, Bitmap bm2, Bitmap bm3) {
	mCurPageBitmap = bm1;
	mCurPageBackBitmap = bm2;
	mNextPageBitmap = bm3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
	canvas.drawColor(COLOR);
	calcPoints();
	drawCurrentPageArea(mCanvas, mCurPageBitmap, mPath0);
	drawNextPageAreaAndShadow(mCanvas, mNextPageBitmap);	
	drawCurrentPageShadow(mCanvas);
	canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
    }

    

    private void createDrawable() {
	int[] color = { 0x333333, 0xB0333333 };
	mFolderShadowDrawableRL = new GradientDrawable(
		GradientDrawable.Orientation.RIGHT_LEFT, color);
	mFolderShadowDrawableRL
		.setGradientType(GradientDrawable.LINEAR_GRADIENT);

	mFolderShadowDrawableLR = new GradientDrawable(
		GradientDrawable.Orientation.LEFT_RIGHT, color);
	mFolderShadowDrawableLR.setGradientType(GradientDrawable.LINEAR_GRADIENT);

	mBackShadowColors = new int[] { 0xFF111111, 0x111111 };
	mBackShadowDrawableRL = new GradientDrawable(
		GradientDrawable.Orientation.RIGHT_LEFT, mBackShadowColors);
	mBackShadowDrawableRL.setGradientType(GradientDrawable.LINEAR_GRADIENT);

	mBackShadowDrawableLR = new GradientDrawable(
		GradientDrawable.Orientation.LEFT_RIGHT, mBackShadowColors);
	mBackShadowDrawableLR.setGradientType(GradientDrawable.LINEAR_GRADIENT);

	mFrontShadowColors = new int[] { 0x80888888, 0x888888 };
	mFrontShadowDrawableVLR = new GradientDrawable(
		GradientDrawable.Orientation.LEFT_RIGHT, mFrontShadowColors);
	mFrontShadowDrawableVLR.setGradientType(GradientDrawable.LINEAR_GRADIENT);
	mFrontShadowDrawableVRL = new GradientDrawable(
		GradientDrawable.Orientation.RIGHT_LEFT, mFrontShadowColors);
	mFrontShadowDrawableVRL.setGradientType(GradientDrawable.LINEAR_GRADIENT);

	mFrontShadowDrawableHTB = new GradientDrawable(
		GradientDrawable.Orientation.TOP_BOTTOM, mFrontShadowColors);
	mFrontShadowDrawableHTB.setGradientType(GradientDrawable.LINEAR_GRADIENT);

	mFrontShadowDrawableHBT = new GradientDrawable(
		GradientDrawable.Orientation.BOTTOM_TOP, mFrontShadowColors);
	mFrontShadowDrawableHBT.setGradientType(GradientDrawable.LINEAR_GRADIENT);
    }
   
    
    public void drawCurrentPageShadow(Canvas canvas)
    {
	      double d1 = Math.atan2(mBezierControl1.y - mTouch.y, mTouch.x-mBezierControl1.x);
	      double d3 =(float) 25/ Math.cos(d1) ;
	      float x = (float)(mTouch.x-d3);
	      float y =(float)( mTouch.y -d3);
	      mPath1.reset();
	      mPath1.moveTo(x, y);
	      mPath1.lineTo(mTouch.x, mTouch.y);
	      mPath1.lineTo(mBezierControl1.x, mBezierControl1.y);
	      mPath1.lineTo(mBezierStart1.x, mBezierStart1.y);
	      mPath1.close();
	      float rotateDegrees;
	       canvas.save();
	      
	     canvas.clipPath(mPath0, Region.Op.XOR);
	     canvas.clipPath(mPath1, Region.Op.INTERSECT);
	         int leftx;
		int rightx; 
	       GradientDrawable mCurrentPageShadow;
		if(mIsRTandLB)
		{	   
		    leftx=(int)(mBezierControl1.x);
		    rightx= (int)mBezierControl1.x+25;
		    mCurrentPageShadow=mFrontShadowDrawableVLR;
		}else
		{	    
		    leftx=(int)(mBezierControl1.x-25);
	            rightx= (int) mBezierControl1.x;
		    mCurrentPageShadow=mFrontShadowDrawableVRL;
		}
	      rotateDegrees= (float) Math.toDegrees(Math.atan2(mTouch.x-mBezierControl1.x,mBezierControl1.y-mTouch.y));
	      canvas.rotate(rotateDegrees, mBezierControl1.x, mBezierControl1.y);
    	          
	      mCurrentPageShadow.setBounds(leftx, (int)(mBezierControl1.y-500), rightx, (int)(mBezierControl1.y));	     
	      mCurrentPageShadow.draw(canvas);
	      canvas.restore();
	      
	      mPath1.reset();
	      mPath1.moveTo(x, y);
	      mPath1.lineTo(mTouch.x, mTouch.y);
	      mPath1.lineTo(mBezierControl2.x, mBezierControl2.y);
	      mPath1.lineTo(mBezierStart2.x, mBezierStart2.y);
	      mPath1.close();
	      canvas.save();
	      canvas.clipPath(mPath0, Region.Op.XOR);
	      canvas.clipPath(mPath1, Region.Op.INTERSECT);
	      
	      if(mIsRTandLB)
		{	   
		  leftx=(int)(mBezierControl2.y);
	            rightx=(int)(mBezierControl2.y+25);
		    mCurrentPageShadow=mFrontShadowDrawableHTB;
		}else
		{	    
		    leftx=(int)(mBezierControl2.y-25);
	            rightx=(int)(mBezierControl2.y);
		    mCurrentPageShadow=mFrontShadowDrawableHBT;
		}
	      rotateDegrees= (float) Math.toDegrees(Math.atan2(mBezierControl2.y-mTouch.y,mBezierControl2.x-mTouch.x));
	      canvas.rotate( rotateDegrees, mBezierControl2.x, mBezierControl2.y);
	      mCurrentPageShadow.setBounds((int)(mBezierControl2.x-500),leftx , (int)(mBezierControl2.x), rightx );	     
	      mCurrentPageShadow.draw(canvas);
	      canvas.restore();
    }
	public Bitmap makeBitmap(int flag){

		mCalendar.setTime(mDate);
		Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight,
				Bitmap.Config.ARGB_8888);
		//利用bitmap生成画布
		Canvas canvas = new Canvas(bitmap);
		canvas.drawColor(Color.WHITE);
		Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
		paint.setStyle(Paint.Style.FILL);

		paint.setTextSize(dip2px(25));
		paint.setColor(COLOR);
		paint.setTextAlign(Paint.Align.CENTER);


		switch (flag){
			case LAST_BITMAP:
				currentPage--;
				break;
			case CURRENT_BITMAP:
				break;
			case NEXT_BITMAP:
				currentPage++;
				break;
		}

		mCalendar.add(Calendar.DATE, currentPage);
		Date date = mCalendar.getTime();

		String monthIndex = monthsdf.format(date);
		String currentDay=daySDF.format(date);
		int weekIndex=mCalendar.get(Calendar.DAY_OF_WEEK)-1;
		Paint.FontMetrics fm = paint.getFontMetrics();
		int textHeight= (int) (Math.ceil(fm.descent - fm.top) + 2);
		canvas.drawText(month[Integer.parseInt(monthIndex)],mWidth/2,mHeight/4-textHeight/2,paint);
		canvas.drawText(week[weekIndex],mWidth/2,mHeight*3/4+textHeight/2,paint);

		paint.setTextSize(dip2px(180));

		 fm = paint.getFontMetrics();
		 textHeight= (int) (Math.ceil(fm.descent - fm.top) + 2);

		canvas.drawText(Integer.parseInt(currentDay)+"",mWidth/2,mHeight/2+textHeight/4,paint);





		return bitmap;
	}
	//dp转px
	public int dip2px(float dip) {
		float density = getContext().getResources().getDisplayMetrics().density;
		return (int) (dip * density + 0.5f);
	}
   
}
