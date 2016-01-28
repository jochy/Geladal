package m2dl.geladal.geladal.filters.impl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import m2dl.geladal.geladal.R;
import m2dl.geladal.geladal.filters.AbstractFilter;
import m2dl.geladal.geladal.filters.IFilter;
import m2dl.geladal.geladal.filters.IFilterConsumer;

/**
 * Created by Alexandre on 28/01/2016.
 */
public class BlackAndWhiteFilter extends AbstractFilter {
    @Override
    public void filterImpl(IFilterConsumer activity, Bitmap original, float x, float y, float z) {
        Bitmap result =  Bitmap.createBitmap(original.getWidth(), original.getHeight(), original.getConfig()); //original.copy(original.getConfig(), true);
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint(Paint.DITHER_FLAG);
        ColorMatrix cm = new ColorMatrix();

        float coef = Math.abs(x) + Math.abs(y) + Math.abs(z) * 5 - 1;
        cm.setSaturation(coef);

        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        canvas.drawBitmap(original, 0, 0, paint);

        //Toast.makeText(activity.getContext(), x + " " + y + " " + z + " " + coef, Toast.LENGTH_SHORT).show();

        activity.setImageFiltered(result);
    }

    @Override
    public int getIcon() {
        return R.drawable.filtre1;
    }

    @Override
    public String getName() {
        return "Back and white";
    }
}
