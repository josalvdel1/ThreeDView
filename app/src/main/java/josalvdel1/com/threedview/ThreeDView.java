package josalvdel1.com.threedview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
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
    private int padding = 35;
    private double factor = 1.1;

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
        double x = xAxis / 10;
        double y = yAxis / 10;
        double z = zAxis / 10;
        if (x > 0 && y > 0) {
            canvas.drawRect(padding, (float) (padding - padding * y * factor), (float) (mWidth - padding + padding * x* factor), mHeight - padding, edgePaint);
        } else if (x > 0 && y < 0) {
            canvas.drawRect(padding, padding, (float) (mWidth - padding + padding * x* factor), (float) (mHeight - padding + padding * -y* factor), edgePaint);
        } else if (x < 0 && y > 0) {
            canvas.drawRect((float) (padding + padding * x* factor), (float) (padding - padding * y* factor), mWidth - padding, mHeight - padding, edgePaint);
        } else if (x < 0 && y < 0) {
            canvas.drawRect((float) (padding + padding * x* factor), padding, mWidth - padding, (float) (mHeight - padding + padding * -y* factor), edgePaint);
        }
        canvas.drawRect(padding, padding, mWidth - padding, mHeight - padding, btnPaint);
        super.onDraw(canvas);
        invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
