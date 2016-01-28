package m2dl.geladal.geladal.filters.impl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

import m2dl.geladal.geladal.filters.IFilter;
import m2dl.geladal.geladal.filters.IFilterConsumer;

/**
 * Created by Alexandre on 28/01/2016.
 */
public class BlackAndWhiteFilter implements IFilter {
    @Override
    public void filter(IFilterConsumer activity, Bitmap original, float x, float y, float z) {
        Bitmap result = original.copy(original.getConfig(), true);
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint(Paint.DITHER_FLAG);
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(Math.min(0, Math.max(Math.abs(x + y + z) / 3f, 1)));

        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        canvas.drawBitmap(original, 0, 0, paint);

        activity.setImageFiltered(result);
    }

    @Override
    public Bitmap getIcon() {
        return null;
    }

    @Override
    public String getName() {
        return "Back and white";
    }
}
