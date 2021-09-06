package com.isport.brandapp.device.bracelet.listview;

import android.content.Context;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.widget.ScrollerCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;

public class SwipeMenuLayout extends LinearLayout {
	private static final int STATE_CLOSE = 0;
	private static final int STATE_OPEN = 1;

	private int mSwipeDirection;

	private View mContentView;
	private View mMenuView;
	private int mDownX;
	private int state = STATE_CLOSE;
	private GestureDetectorCompat mGestureDetector;
	private OnGestureListener mGestureListener;
	private boolean isFling;
	private int MIN_FLING,MAX_VELOCITYX;
	private ScrollerCompat mOpenScroller,mCloseScroller;
	private int mBaseX;

	private boolean mSwipEnable = true;

	public SwipeMenuLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}


	public void setSwipeDirection(int swipeDirection) {
		mSwipeDirection = swipeDirection;
	}

	private void init() {
		MIN_FLING = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 15, getResources().getDisplayMetrics());
		MAX_VELOCITYX = - (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 500, getResources().getDisplayMetrics());
		setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		mGestureListener = new SimpleOnGestureListener() {
			@Override
			public boolean onDown(MotionEvent e) {
				isFling = false;
				return true;
			}

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
				// TODO
				if (Math.abs(e1.getX() - e2.getX()) > MIN_FLING && velocityX < MAX_VELOCITYX) {
					isFling = true;
				}
				return super.onFling(e1, e2, velocityX, velocityY);
			}
		};
		
		mGestureDetector = new GestureDetectorCompat(getContext(), mGestureListener);
		mCloseScroller = ScrollerCompat.create(getContext());
		mOpenScroller = ScrollerCompat.create(getContext());
	}
	

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
	}

	public boolean onSwipe(MotionEvent event) {
		mGestureDetector.onTouchEvent(event);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mDownX = (int) event.getX();
			isFling = false;
			break;
		case MotionEvent.ACTION_MOVE:
			int dis = (int) (mDownX - event.getX());
//			LogUtil.showMsg(TAG+" move mDownX:"+mDownX+",dis:" + dis );
			if (state == STATE_OPEN) {
				dis += mMenuView.getWidth()*mSwipeDirection;;
			}
			swipe(dis);
			break;
		case MotionEvent.ACTION_UP:
			if ((isFling || Math.abs(mDownX - event.getX()) > (mMenuView.getWidth() / 2)) &&
					Math.signum(mDownX - event.getX()) == mSwipeDirection) {
				// open
				smoothOpenMenu();
			} else {
				// close
				smoothCloseMenu();
				return false;
			}
			break;
		}
		return true;
	}

	public boolean isOpen() {
		return state == STATE_OPEN;
	}

	private void swipe(int dis) {
		
		int menuWidth = mMenuView.getMeasuredWidth();
		
		if (Math.signum(dis) != mSwipeDirection) {
			dis = 0;
		} else if (Math.abs(dis) > menuWidth) {
			dis = menuWidth * mSwipeDirection;
		}
		
//		LogUtil.showMsg(TAG+" swipe dis:" + dis +",cW:"+ mContentView.getWidth()+",hW:"+menuWidth);

		mContentView.layout(-dis, mContentView.getTop(),
				mContentView.getWidth() -dis, getMeasuredHeight());

		if (mSwipeDirection == SwipeMenuListView.DIRECTION_LEFT) {
			mMenuView.layout(mContentView.getWidth() - dis, mMenuView.getTop(),
					mContentView.getWidth() +  menuWidth - dis, mMenuView.getBottom());
		} else {
			mMenuView.layout(-menuWidth - dis, mMenuView.getTop(), - dis, mMenuView.getBottom());
		}
	}

	@Override
	public void computeScroll() {
		
//		LogUtil.showMsg(TAG+" computeScroll------------");
		
		if (state == STATE_OPEN) {
			if (mOpenScroller.computeScrollOffset()) {
				swipe(mOpenScroller.getCurrX()*mSwipeDirection);
				postInvalidate();
			}
		} else {
			if (mCloseScroller.computeScrollOffset()) {
				swipe((mBaseX - mCloseScroller.getCurrX())*mSwipeDirection);
				postInvalidate();
			}
		}
	}

	public void smoothCloseMenu() {
		state = STATE_CLOSE;
		if (mSwipeDirection == SwipeMenuListView.DIRECTION_LEFT) {
			mBaseX = -mContentView.getLeft();
			mCloseScroller.startScroll(0, 0, mMenuView.getWidth(), 0, 350);
		} else {
			mBaseX = mMenuView.getRight();
			mCloseScroller.startScroll(0, 0, mMenuView.getWidth(), 0, 350);
		}
		postInvalidate();
	}

	public void smoothOpenMenu() {
		state = STATE_OPEN;
		if (mSwipeDirection == SwipeMenuListView.DIRECTION_LEFT) {
			mOpenScroller.startScroll(-mContentView.getLeft(), 0, mMenuView.getWidth(), 0, 350);
		} else {
			mOpenScroller.startScroll(mContentView.getLeft(), 0, mMenuView.getWidth(), 0, 350);
		}
		postInvalidate();
	}

	public void closeMenu() {
		if (mCloseScroller.computeScrollOffset()) {
			mCloseScroller.abortAnimation();
		}
		if (state == STATE_OPEN) {
			state = STATE_CLOSE;
			swipe(0);
		}
	}

	public void openMenu() {
		if (state == STATE_CLOSE) {
			state = STATE_OPEN;
			swipe(mMenuView.getWidth() * mSwipeDirection);
		}
	}

	public View getContentView() {
		return mContentView;
	}

	public View getMenuView() {
		return mMenuView;
	}


	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		mContentView = getChildAt(0);
		mMenuView = getChildAt(1);
		
		mMenuView.measure(MeasureSpec.makeMeasureSpec(0,
				MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(
				getMeasuredHeight(), MeasureSpec.EXACTLY));
//		LogUtil.showMsg(TAG+" onMeasure mh:" + getMeasuredHeight()+",mw:"+ mMenuView.getWidth()+","+ mMenuView.getMeasuredWidth());
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		
//		LogUtil.showMsg(TAG+" onLayout c:"+ changed+",l:"+l+",t:"+ t+",r:"+ r+",b:"+ b);
		
		mContentView.layout(0, 0, getMeasuredWidth(), mContentView.getMeasuredHeight());
		if (mSwipeDirection == SwipeMenuListView.DIRECTION_LEFT) {
			mMenuView.layout(getMeasuredWidth(), 0,
					getMeasuredWidth() + mMenuView.getMeasuredWidth(), mContentView.getMeasuredHeight());
		} else {
			mMenuView.layout(-mMenuView.getMeasuredWidth(), 0, 0, mContentView.getMeasuredHeight());
		}
	}

	public void setSwipEnable(boolean swipEnable){
		mSwipEnable = swipEnable;
	}

	public boolean getSwipEnable(){
		return mSwipEnable;
	}
}
