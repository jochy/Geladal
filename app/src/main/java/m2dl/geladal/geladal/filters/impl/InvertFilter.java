package m2dl.geladal.geladal.filters.impl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

import m2dl.geladal.geladal.R;
import m2dl.geladal.geladal.filters.AbstractFilter;
import m2dl.geladal.geladal.filters.IFilterConsumer;

/**
 * Created by root on 28/01/16.
 */
public class InvertFilter extends AbstractFilter {
    @Override
    public void filterImpl(IFilterConsumer activity, Bitmap original, float x, float y, float z) { // get image size
        Bitmap bitmap = Bitmap.createBitmap(original.getWidth(),
                original.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(
                getColorMatrix(x, y, z)));
        canvas.drawBitmap(original, 0, 0, paint);
        activity.setImageFiltered(bitmap);
    }

    @Override
    public int getIcon() {
        return R.drawable.inverted;
    }

    @Override
    public String getName() {
        return "Inverted Filter";
    }

    private ColorMatrix getColorMatrix(float x, float y, float z) {


        return new ColorMatrix(new float[] {
                -1,  0,  0,  0, (float) ((-y+0.7)*255),
                0, -1,  0,  0, (float) ((-y+0.7)*255),
                0,  0, -1,  0, (float) ((-y+0.7)*255),
                0,  0,  0,  1,   0
        });
    }

}
