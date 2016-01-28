package m2dl.geladal.geladal.filters.impl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;

import m2dl.geladal.geladal.R;
import m2dl.geladal.geladal.filters.AbstractFilter;
import m2dl.geladal.geladal.filters.IFilterConsumer;

/**
 * Created by Nabil on 28/01/16.
 */
public class ColorFilter extends AbstractFilter {


    @Override
    public void filterImpl(IFilterConsumer activity, Bitmap original, float x, float y, float z) {
        Canvas canvas = new Canvas();
        Bitmap result = Bitmap.createBitmap(original.getWidth(), original.getHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(result);
        Paint paint = new Paint();
        paint.setFilterBitmap(false);

        int color = Color.rgb((int) (Math.abs(x) * 255), (int) (Math.abs(y) * 255), (int) (Math.abs(z) * 255));

        // Color
        paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY));
        canvas.drawBitmap(original, 0, 0, paint);
        paint.setColorFilter(null);
        paint.setXfermode(null);
        activity.setImageFiltered(result);
    }

    @Override
    public int getIcon() {
        return R.drawable.color;
    }

    @Override
    public String getName() {
        return "Color Filter";
    }
}
