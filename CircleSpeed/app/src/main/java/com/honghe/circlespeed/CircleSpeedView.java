package com.honghe.circlespeed;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.Xfermode;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wanghh on 2017/4/19.
 */

public class CircleSpeedView extends View {

    private int speed;
    private int maxSpeed = 220;
    private int tempSpeed;
    private int defaultColor = Color.GRAY;
    private int drawTextColor = Color.WHITE;

    private Paint mPaint;
    private RectF mRectF;
    private int dotStrokeWidth = 30;
    private int speedNumberTextSize = 300;
    private int speedUnitTextSize = 100;
    private int textStrokeWidth = 10;
    private int linePadding = 20;

    private int mViewWidth;
    private int mViewHeight;
    private int padding;
    private float mRadius;

    private Canvas mCanvas;
    private Bitmap mBitmap;
    private float mCenterX;
    private float mCenterY;
    private Xfermode mClearCanvasXfermode;

    public CircleSpeedView(Context context) {
        this(context, null);
    }

    public CircleSpeedView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleSpeedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true); // 消除锯齿
        mRectF = new RectF();
        mClearCanvasXfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CircleSpeedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircleDefault(mCanvas);
        drawText(mCanvas);
        canvas.drawBitmap(mBitmap, 0, 0, null);
        if (tempSpeed < speed) {
            tempSpeed++;
        } else if (tempSpeed > speed) {
            tempSpeed--;
        }
        if (tempSpeed != speed) {
            postInvalidate();
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
        padding = getPaddingLeft();
        // 计算圆半径
        mRadius = (mViewWidth < mViewHeight ? mViewWidth - 2 * padding : mViewHeight - 2 * padding) / 2f;
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    private void drawCircleDefault(Canvas canvas) {
        mPaint.setXfermode(mClearCanvasXfermode);
        mCanvas.drawPaint(mPaint);
        mPaint.setXfermode(null);

        mCenterX = mViewWidth / 2f;
        mCenterY = mViewHeight / 2f;
        mRectF.top = 0;
        mRectF.left = 0;
        mRectF.right = mRadius * 2;
        mRectF.bottom = mRadius * 2;

        mPaint.setColor(defaultColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(dotStrokeWidth);

        mCanvas.save();
        mCanvas.translate(padding, padding);

        Path path = new Path();
        path.arcTo(mRectF, 135, 270);
        PathEffect effect = new DashPathEffect(new float[]{10f, 20f}, 0);
        mPaint.setPathEffect(effect);
        mCanvas.drawPath(path, mPaint);

        Path path2 = new Path();
        path2.arcTo(mRectF, 135, (float) tempSpeed / (float) maxSpeed * 270f);
        PathEffect effect2 = new DashPathEffect(new float[]{10f, 20f}, 0);
        mPaint.setPathEffect(effect2);
        Shader shader = new SweepGradient(mRadius, mRadius, new int[]{Color.RED,
                Color.GREEN, Color.YELLOW, Color.RED}, null);
        mPaint.setShader(shader);
        mCanvas.drawPath(path2, mPaint);
        mCanvas.restore();

    }

    private void drawText(Canvas canvas) {
        adjustNumberTextSize();
        mPaint.setShader(null);
        mPaint.setPathEffect(null);
        mPaint.setStrokeWidth(textStrokeWidth);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(drawTextColor);
        Paint.FontMetrics fm = mPaint.getFontMetrics();

        float textWidth = mPaint.measureText(speed + "");
        float startX = mCenterX - textWidth / 2;
        float textHeight = ((fm.descent - fm.ascent) / 2 - fm.descent) * 2;
        float startY = mCenterY + (fm.descent - fm.ascent) / 2 - fm.descent;
        canvas.drawText(speed + "", startX, startY, mPaint);
        canvas.drawLine(mCenterX - mRadius / 2, startY + linePadding, mCenterX + mRadius / 2, startY + linePadding, mPaint);

        adjustUnitTextSize();
        fm = mPaint.getFontMetrics();
        float textWidth2 = mPaint.measureText("km/h");
        float startX2 = mCenterX - textWidth2 / 2;
        float textHeight2 = ((fm.descent - fm.ascent) / 2 - fm.descent) * 2;
        float startY2 = mCenterY + linePadding + textStrokeWidth + linePadding + textHeight / 2 + textHeight2;

        canvas.drawText("km/h", startX2, startY2, mPaint);

    }

    private void adjustNumberTextSize() {
        mPaint.setTextSize(speedNumberTextSize);
        if (mPaint.measureText("000") > mRadius) {
            speedNumberTextSize = speedNumberTextSize - 10;
            adjustNumberTextSize();
        } else {
            mPaint.setTextSize(speedNumberTextSize > 50 ? speedNumberTextSize : 50);
        }
    }

    private void adjustUnitTextSize() {
        mPaint.setTextSize(speedUnitTextSize);
        if (mPaint.measureText("000000") > mRadius) {
            speedUnitTextSize = speedUnitTextSize - 10;
            adjustUnitTextSize();
        } else {
            mPaint.setTextSize(speedUnitTextSize > 20 ? speedUnitTextSize : 20);
        }
    }

    public synchronized void setSpeed(int speed) {
        this.speed = speed;
        postInvalidate();
    }
}
