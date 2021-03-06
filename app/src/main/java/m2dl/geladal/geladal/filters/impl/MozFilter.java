package m2dl.geladal.geladal.filters.impl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import m2dl.geladal.geladal.R;
import m2dl.geladal.geladal.filters.AbstractFilter;
import m2dl.geladal.geladal.filters.IFilterConsumer;

/**
 * Created by Alexandre on 28/01/2016.
 */
public class MozFilter extends AbstractFilter {
    @Override
    public void filterImpl(IFilterConsumer activity, Bitmap original, float x, float y, float z) {
        Bitmap result =  Bitmap.createBitmap(original.getWidth(), original.getHeight(), original.getConfig()); //original.copy(original.getConfig(), true);
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        canvas.drawBitmap(original, 0, 0, paint);

        x = Math.abs(x);
        y = Math.abs(y);
        z = Math.abs(z);

        int nbCol = (int) (x * z * 90);
        int nbRow = (int) (y * z * 90);

        nbCol = Math.max(Math.min(20, nbCol), 1);
        nbRow = Math.max(Math.min(20, nbRow), 1);

        int sizeC = original.getWidth() / nbCol;
        int sizeR = original.getHeight() / nbRow;

        Bitmap scaled = Bitmap.createScaledBitmap(original, sizeC, sizeR, true);

        for (int i = 0; i < nbCol + 1; i++) {
            for (int j = 0; j < nbRow + 1; j++) {
                int ypos = j * sizeR;
                int xpos = i * sizeC;
                canvas.drawBitmap(scaled, xpos, ypos, paint);
            }
        }

        activity.setImageFiltered(result);
    }

    @Override
    public int getIcon() {
        return R.drawable.mosaic;
    }

    @Override
    public String getName() {
        return "Mosaic Filter";
    }
}
