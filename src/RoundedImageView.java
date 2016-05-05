package com.pathofacoder.roundedimageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by adh on 5/5/2016.
 */
public class RoundedImageView extends ImageView {
    public RoundedImageView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        float radius = 150.0f; // angle of round corners
        Path clipPath = new Path();
        RectF rect = new RectF(0, 0, getWidth(), getHeight());
        clipPath.addRoundRect(rect, radius, radius, Path.Direction.CW);
        canvas.clipPath(clipPath);

        super.onDraw(canvas);
    }

    public static Bitmap getCroppedBitmap(Bitmap bmp, int radius)
    {
        Bitmap sbmp;
        if (bmp.getWidth() != radius || bmp.getHeight() != radius)
            sbmp = Bitmap.createScaledBitmap(bmp, radius, radius, false);
        else
            sbmp = bmp;

        Bitmap output = Bitmap.createBitmap(sbmp.getWidth(), sbmp.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor("#BAB399"));

        canvas.drawCircle(sbmp.getWidth() / 2 + 0.0f, sbmp.getHeight() / 2 + 0.0f, sbmp.getWidth() / 2 + 0.0f, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        try
        {
            canvas.drawBitmap(sbmp, rect, rect, paint);
        }
        catch (Exception ex)
        {
            Log.d("Round Image Crash", ex.toString());
        }

        return output;
    }
}
