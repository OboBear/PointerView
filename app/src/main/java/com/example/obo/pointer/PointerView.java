package com.example.obo.pointer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by obo on 2017/9/25.
 */

public class PointerView extends View {

    Paint mPaint;
    Paint mPointPaint;

    int mDegreen = 15;
    Path path = new Path();

    public PointerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    private void initPaint() {
        mPointPaint = new Paint();
        path.moveTo();
    }
    public void setDegreen(int degreen) {
        this.mDegreen = degreen;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath();
        canvas.drawRect(0,0, 100, 100, mPointPaint);
    }

}
