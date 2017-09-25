package com.example.obo.pointer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by obo on 2017/9/25.
 */

public class PointerView extends View {

    private static final String TAG = "PointerView";

    private static final int FONT_SIZE = 30;

    private Paint mWordPaint = new Paint();
    private Paint mPointPaint = new Paint();
    private Paint mArchPaint = new Paint();
    private RectF mArchRect;

    private int mViewWidth;
    private int mViewHeight;

    private float mDegreen = 0;
    private int mValue = 15;
    private Path path ;
    private PointF wordPosition = new PointF();
    private PointF mPointCenter = new PointF();

    public PointerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initPath();
        initWordPosition();
    }

    private void initPaint() {
        mPointPaint.setAntiAlias(true);
        mWordPaint.setAntiAlias(true);
        mWordPaint.setTextSize(FONT_SIZE);
        mArchPaint.setAntiAlias(true);
    }

    private void initPath() {
        double degreen = - mDegreen * 2 * Math.PI + Math.PI ;
        float sinValue = (float) Math.sin(degreen);
        float cosValue = (float) Math.cos(degreen);

        int pointLength = (mViewWidth >> 1) - 60;
        float pointX =  mPointCenter.x + pointLength * sinValue;
        float pointY =  mPointCenter.y + pointLength * cosValue;

        path = new Path();
        path.moveTo(mPointCenter.x + 2 * cosValue, mPointCenter.y  - 2 * sinValue);
        path.lineTo(mPointCenter.x - 2 * cosValue, mPointCenter.y  + 2 * sinValue);
        path.lineTo(pointX , pointY);
    }

    private void initWordPosition() {
        double degreen = - mDegreen * 2 * Math.PI + Math.PI;
        float sinValue = (float) Math.sin(degreen);
        float cosValue = (float) Math.cos(degreen);

        int pointLength = (mViewWidth >> 1) - 20;
        float pointX =  mPointCenter.x + pointLength * sinValue;
        float pointY =  mPointCenter.y + pointLength * cosValue;

        String text = mValue + "\"";
        Rect rect = new Rect();
        mWordPaint.getTextBounds(text, 0, text.length(), rect);
        int width = rect.width();//文本的宽度
        int height = rect.height();//文本的高度

        wordPosition.x = pointX - width / 2;
        wordPosition.y = pointY + height / 2;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mViewWidth = getMeasuredWidth();
        mViewHeight = getMeasuredHeight();
        mPointCenter = new PointF(mViewWidth / 2, mViewHeight / 2);
        initPath();
        initWordPosition();
        mArchRect = new RectF(30, 30, mViewWidth - 60, mViewHeight - 60);
    }

    public void setDegreen(int value) {
        this.mValue = value;
        this.mDegreen = value / 120f;
        initPath();
        initWordPosition();
        postInvalidate();

    }

    int []mMinColors = {Color.TRANSPARENT, 0x2263A17E, Color.TRANSPARENT};
    float []degreens = {0,  0.3f, 0.3f};

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, mPointPaint);
        canvas.drawText("" + mValue + "\"" , wordPosition.x, wordPosition.y , mWordPaint);
//        canvas.drawText("" + mValue, 0, 10, mWordPaint);
        degreens[2] = degreens[1] = mDegreen;
        SweepGradient sweepGradient = new SweepGradient(canvas.getWidth() / 2,
                canvas.getHeight() / 2, //以圆弧中心作为扫描渲染的中心以便实现需要的效果
                mMinColors, //这是我定义好的颜色数组，包含2个颜色：#35C3D7、#2894DD
                degreens);
        Matrix matrix = new Matrix();
        matrix.setRotate(- 90, mViewWidth / 2, mViewHeight / 2);
        sweepGradient.setLocalMatrix(matrix);
        mArchPaint.setShader(sweepGradient);
        canvas.drawArc(mArchRect, 0, 360, true, mArchPaint);
    }

}
