package com.example.www.dateme;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class PulseAnimationView extends View {

    private float mRadius;
    private final Paint mPaint = new Paint();
    private static final int colorAdjustor = 5;
    private float mX,mY;
    private static final int duration = 4000;
    private static final int delay = 1000;
    private AnimatorSet mPulseAnimatorSet = new AnimatorSet();

    public PulseAnimationView(Context context) {
        super(context);
    }
    public PulseAnimationView(Context context, AttributeSet attr){
        super(context,attr);
    }
    public void setRadius(float radius){
        mRadius = radius;
        mPaint.setColor(Color.GREEN+(int)radius/colorAdjustor);
        invalidate();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mX,mY,mRadius,mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getActionMasked()==MotionEvent.ACTION_DOWN){
            mX = event.getX();
            mY = event.getY();
        }
        if(mPulseAnimatorSet !=null&& mPulseAnimatorSet.isRunning()){
            mPulseAnimatorSet.cancel();
        }
        mPulseAnimatorSet.start();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        ObjectAnimator growAnimator = ObjectAnimator.ofFloat(this,"radius",0,getWidth());
        growAnimator.setDuration(duration);
        growAnimator.setInterpolator(new LinearInterpolator());

        ObjectAnimator shrinkAnimator = ObjectAnimator.ofFloat(this,"radius",getWidth(),0);
        shrinkAnimator.setDuration(duration);
        shrinkAnimator.setInterpolator(new LinearInterpolator());

        shrinkAnimator.setStartDelay(delay);

        ObjectAnimator repeatAnimator = ObjectAnimator.ofFloat(this,"radius",0,getWidth());
        repeatAnimator.setStartDelay(delay);
        repeatAnimator.setDuration(duration);
        repeatAnimator.setRepeatCount(1);
        repeatAnimator.setRepeatMode(ValueAnimator.REVERSE);

        mPulseAnimatorSet.play(growAnimator).before(shrinkAnimator);
        mPulseAnimatorSet.play(shrinkAnimator).after(growAnimator);
    }

}
