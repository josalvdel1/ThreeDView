package josalvdel1.com.threedview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.view.MotionEvent;
import android.view.View;

public class SampleView extends View {
    private final Paint mPaint = new Paint();
    private final float[] mVerts = new float[10];
    private final float[] mTexs = new float[10];
    private final short[] mIndices = {0, 1, 2, 3, 4, 1};
    private final Matrix mMatrix = new Matrix();
    private final Matrix mInverse = new Matrix();

    private static void setXY(float[] array, int index, float x, float y) {
        array[index * 2 + 0] = x;
        array[index * 2 + 1] = y;
    }

    public SampleView(Context context) {
        super(context);
        setFocusable(true);
        Bitmap bm = BitmapFactory.decodeResource(getResources(),
                R.drawable.skull_icon);
        Shader s = new BitmapShader(bm, Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP);
        mPaint.setShader(s);
        float w = bm.getWidth();
        float h = bm.getHeight();
        // construct our mesh
        setXY(mTexs, 0, w / 2, h / 2);
        setXY(mTexs, 1, 0, 0);
        setXY(mTexs, 2, w, 0);
        setXY(mTexs, 3, w, h);
        setXY(mTexs, 4, 0, h);
        setXY(mVerts, 0, w / 2, h / 2);
        setXY(mVerts, 1, 0, 0);
        setXY(mVerts, 2, w, 0);
        setXY(mVerts, 3, w, h);
        setXY(mVerts, 4, 0, h);
        mMatrix.setScale(0.8f, 0.8f);
        mMatrix.preTranslate(20, 20);
        mMatrix.invert(mInverse);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(0xFFCCCCCC);
        canvas.save();
        canvas.concat(mMatrix);
        canvas.drawVertices(Canvas.VertexMode.TRIANGLE_FAN, 10, mVerts, 0,
                mTexs, 0, null, 0, null, 0, 0, mPaint);
        canvas.translate(0, 240);
        canvas.drawVertices(Canvas.VertexMode.TRIANGLE_FAN, 10, mVerts, 0,
                mTexs, 0, null, 0, mIndices, 0, 6, mPaint);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float[] pt = {event.getX(), event.getY()};
        mInverse.mapPoints(pt);
        setXY(mVerts, 0, pt[0], pt[1]);
        invalidate();
        return true;
    }
}
