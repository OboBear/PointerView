package com.example.obo.pointer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by obo on 2017/9/25.
 */

public class PointerView extends View {

    Paint mWordPaint = new Paint();
    Paint mPointPaint = new Paint();

    int mViewWidth;
    int mViewHeight;

    int mDegreen = 15;
    Path path ;
    PointF wordPosition= new PointF();

    public PointerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initPath();
        initWordPosition();
    }

    private void initPaint() {

        mPointPaint.setAntiAlias(true);

        mWordPaint.setAntiAlias(true);
        mWordPaint.setTextSize(15);

    }

    private void initPath() {
        double degreen = mDegreen / 120f * 2 * Math.PI;
        float sinValue = (float) Math.sin(degreen);
        float cosValue = (float) Math.cos(degreen);

        int pointLength = (mViewWidth - 90) >> 1;
        float pointX =  pointLength * (1 + sinValue);
        float pointY =  pointLength * (1 + cosValue);

        path = new Path();
        path.moveTo(pointLength + 2 * cosValue, pointLength - 2 * sinValue);
        path.lineTo(pointLength - 2 * cosValue, pointLength + 2 * sinValue);
        path.lineTo(pointX , pointY);
    }

    private void initWordPosition() {

        double degreen = mDegreen / 120f * 2 * Math.PI;
        float sinValue = (float) Math.sin(degreen);
        float cosValue = (float) Math.cos(degreen);

        int pointLength = (mViewWidth >> 1) - 40;
        float pointX =  pointLength * (1 + sinValue);
        float pointY =  pointLength * (1 + cosValue);

        wordPosition.x = pointX;
        wordPosition.y = pointY;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mViewWidth = getMeasuredWidth();
        mViewHeight = getMeasuredHeight();
        initPath();
        initWordPosition();
    }

    public void setDegreen(int degreen) {
        this.mDegreen = degreen;
        initPath();
        initWordPosition();
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, mPointPaint);
        canvas.drawText("" + mDegreen , wordPosition.x, wordPosition.y , mWordPaint);
//        canvas.drawRect(0,0, 100, 100, mPointPaint);
    }

}
