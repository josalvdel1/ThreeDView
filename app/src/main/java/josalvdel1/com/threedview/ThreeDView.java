package josalvdel1.com.threedview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

public class ThreeDView extends TextView implements SensorEventListener {

    private float timestamp;
    private SensorManager mSensorManager;
    private long lastUpdate;
    private double last_y;
    private double last_z;
    private double last_x;
    private float xAxis;
    private float yAxis;
    private float zAxis;
    private Paint btnPaint;
    private Paint edgePaint;
    private int mWidth;
    private int mHeight;
    private Point center;
    private float yCenter;
    private float xCenter;

    public ThreeDView(Context context) {
        super(context);
        init(context);
    }


    public ThreeDView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ThreeDView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
        btnPaint = new Paint();
        edgePaint = new Paint();
        btnPaint.setColor(Color.parseColor("#f52646f9"));
        edgePaint.setColor(Color.parseColor("#F5267EF9"));
    }

    public void onSensorChanged(SensorEvent event) {
        xAxis = event.values[0];
        yAxis = event.values[1];
        zAxis = event.values[2];
        //setText(Math.absMath.round(x) + " " + Math.round(y) + " " + Math.round(z) + " " );
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        xCenter = (float) mWidth / 2;
        yCenter = (float) mHeight / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(30, 30, mWidth - 30, mHeight - 30, btnPaint);
        double x = xAxis / 10;
        Log.d("t", x + "x");
        double y = yAxis / 10;
        Log.d("t", y + "y");
        double z = zAxis / 10;
        Log.d("t", z + "z");
        if (x > 0) {
            canvas.drawRect(xCenter, 0, (float) (mWidth * x), mHeight, edgePaint);
        } else {
            canvas.drawRect((float) (mWidth + mWidth * x), 0, xCenter, mHeight, edgePaint);
        }
        if (y > 0) {
            canvas.drawRect(0, (float) (mHeight + mHeight * -y), mWidth, yCenter, edgePaint);
        } else {
            canvas.drawRect(0, yCenter, mWidth, (float) (mHeight * -y), edgePaint);
        }
        super.onDraw(canvas);
        invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
