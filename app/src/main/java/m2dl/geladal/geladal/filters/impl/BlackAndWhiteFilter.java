package m2dl.geladal.geladal.filters.impl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.widget.Toast;

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

        float coef = Math.abs(x) + Math.abs(y) + Math.abs(z) * 2;

        cm.setSaturation(coef);

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
