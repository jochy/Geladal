package m2dl.geladal.geladal.filters.impl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import m2dl.geladal.geladal.R;
import m2dl.geladal.geladal.filters.AbstractFilter;
import m2dl.geladal.geladal.filters.IFilter;
import m2dl.geladal.geladal.filters.IFilterConsumer;

/**
 * Created by Alexandre on 28/01/2016.
 */
public class MozFilter extends AbstractFilter {
    @Override
    public void filterImpl(IFilterConsumer activity, Bitmap original, float x, float y, float z) {
        Bitmap result = original.copy(original.getConfig(), true);
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        canvas.drawBitmap(original, 0, 0, paint);

        x = Math.abs(x);
        y = Math.abs(y);
        z = Math.abs(z);

        int nbCol = (int) (8 * x * z * 100);
        int nbRow = (int) (8 * y * z * 100);

        nbCol = Math.max(Math.min(25, nbCol), 1);
        nbRow = Math.max(Math.min(25, nbRow), 1);

        int sizeC = original.getWidth() / nbCol;
        int sizeR = original.getHeight() / nbRow;

        Bitmap scaled = Bitmap.createScaledBitmap(original, sizeC, sizeR, true);

        for (int i = 0; i < nbCol; i++) {
            for (int j = 0; j < nbRow; j++) {
                int ypos = j * sizeR;
                int xpos = i * sizeC;
                canvas.drawBitmap(scaled, xpos, ypos, paint);
            }
        }

        activity.setImageFiltered(result);
    }

    @Override
    public int getIcon() {
        return R.drawable.filtre2;
    }

    @Override
    public String getName() {
        return "Mosaïque";
    }
}
