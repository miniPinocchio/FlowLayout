package com.leanote.berial.flowlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 流式布局
 * @author berial
 */
public class FlowLayout extends ViewGroup {

	private static final int DEFAULT_HORIZONTAL_SPACING = 5;
	private static final int DEFAULT_VERTICAL_SPACING = 5;

	//两个子View的垂直间距
	private int mVerticalSpacing;

	//两个子View的水平间距
	private int mHorizontalSpacing;

	public FlowLayout(Context context) {
		this(context, null);
	}

	public FlowLayout(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
		try {
			mHorizontalSpacing = a.getDimensionPixelSize(
					R.styleable.FlowLayout_horizontal_spacing, DEFAULT_HORIZONTAL_SPACING);
			mVerticalSpacing = a.getDimensionPixelSize(
					R.styleable.FlowLayout_vertical_spacing, DEFAULT_VERTICAL_SPACING);
		} finally {
			a.recycle();
		}
	}

	/**
	 * 设置水平间距
	 * @param pixelSize 水平间距px值
	 */
	public void setHorizontalSpacing(int pixelSize) {
		mHorizontalSpacing = pixelSize;
	}

	/**
	 * 设置垂直间距
	 * @param pixelSize 垂直间距px值
	 */
	public void setVerticalSpacing(int pixelSize) {
		mVerticalSpacing = pixelSize;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int myWidth = resolveSize(0, widthMeasureSpec);

		int paddingLeft = getPaddingLeft();
		int paddingTop = getPaddingTop();
		int paddingRight = getPaddingRight();
		int paddingBottom = getPaddingBottom();

		int childLeft = paddingLeft;
		int childTop = paddingTop;

		int lineHeight = 0;

		// Measure each child and put the child to the right of previous child
		// if there's enough room for it, otherwise, wrap the line and put the child to next line.
		//测量每一个子控件，如果当前行有足够的控件，会把当前子控件放到上一个子控件的右方，否则会放到下一行。
		for(int i = 0, childCount = getChildCount(); i < childCount; ++i) {
			View child = getChildAt(i);
			measureChild(child, widthMeasureSpec, heightMeasureSpec);

			int childWidth = child.getMeasuredWidth();
			int childHeight = child.getMeasuredHeight();

			lineHeight = Math.max(childHeight, lineHeight);//行高为当前行所有控件中的最大值

			if(childLeft + childWidth + paddingRight > myWidth) {
				childLeft = paddingLeft;
				childTop += mVerticalSpacing + lineHeight;
				lineHeight = childHeight;
			} else {
				childLeft += childWidth + mHorizontalSpacing;
			}
		}

		int wantedHeight = childTop + lineHeight + paddingBottom;

		setMeasuredDimension(myWidth, resolveSize(wantedHeight, heightMeasureSpec));
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int myWidth = r - l;

		int paddingLeft = getPaddingLeft();
		int paddingTop = getPaddingTop();
		int paddingRight = getPaddingRight();

		int childLeft = paddingLeft;
		int childTop = paddingTop;

		int lineHeight = 0;

		for(int i = 0, childCount = getChildCount(); i < childCount; ++i) {
			View childView = getChildAt(i);

			if(childView.getVisibility() == View.GONE) {
				continue;
			}

			int childWidth = childView.getMeasuredWidth();
			int childHeight = childView.getMeasuredHeight();

			lineHeight = Math.max(childHeight, lineHeight);

			if(childLeft + childWidth + paddingRight > myWidth) {
				childLeft = paddingLeft;
				childTop += mVerticalSpacing + lineHeight;
				lineHeight = childHeight;
			}

			childView.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
			childLeft += childWidth + mHorizontalSpacing;
		}
	}
}
