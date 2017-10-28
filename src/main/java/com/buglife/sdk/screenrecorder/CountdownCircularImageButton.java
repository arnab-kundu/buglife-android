package com.buglife.sdk.screenrecorder;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;

import com.buglife.sdk.ViewUtils;

public class CountdownCircularImageButton extends AppCompatImageButton {
    private final Paint mRingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final RectF mRingBounds = new RectF();
    private ValueAnimator mRingAnimator;
    float mCurrentRingAngle = 360;

    public CountdownCircularImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mRingPaint.setColor(Color.BLUE);
        mRingPaint.setStyle(Paint.Style.STROKE);
        mRingPaint.setStrokeWidth(ViewUtils.dpToPx(3, getResources()));
        mRingAnimator = ValueAnimator.ofFloat(360, 0);
        mRingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentRingAngle = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
    }

    public void setCountdownDuration(long duration) {
        mRingAnimator.setDuration(duration);
    }

    @Override protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        // Set the bounds of the ring to sit nicely inside of the button
        float inset = mRingPaint.getStrokeWidth() / 2;
        mRingBounds.set(
                inset,
                inset,
                getMeasuredWidth() - inset,
                getMeasuredHeight() - inset
        );
    }

    @Override protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mRingAnimator.start();
    }

    @Override protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mRingAnimator.end();
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(mRingBounds, -90, mCurrentRingAngle, false, mRingPaint);
    }
}
