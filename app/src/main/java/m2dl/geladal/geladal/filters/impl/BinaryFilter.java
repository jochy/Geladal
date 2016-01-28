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
public class BinaryFilter extends AbstractFilter {

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
        return R.drawable.binary;
    }

    @Override
    public String getName() {
        return "Binary Filter";
    }

    private ColorMatrix getColorMatrix(float x, float y, float z) {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);

        float m = (float) (255f *     ((x+y)/2+0.5));
        float t = (float) (-255*128f * ((y+z)/2+0.5));
        ColorMatrix threshold = new ColorMatrix(new float[] {
                m, 0, 0, 1, t,
                0, m, 0, 1, t,
                0, 0, m, 1, t,
                0, 0, 0, 1, 0
        });

        // Convert to grayscale, then scale and clamp
        colorMatrix.postConcat(threshold);

        return colorMatrix;
    }

}
