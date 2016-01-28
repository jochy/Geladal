package m2dl.geladal.geladal.filters.impl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.Toast;

import m2dl.geladal.geladal.R;
import m2dl.geladal.geladal.filters.AbstractFilter;
import m2dl.geladal.geladal.filters.IFilter;
import m2dl.geladal.geladal.filters.IFilterConsumer;

/**
 * Created by Alexandre on 28/01/2016.
 */
public class GridFilter extends AbstractFilter {

    @Override
    public void filterImpl(IFilterConsumer activity, Bitmap original, float x, float y, float z) {
        Bitmap result = original.copy(original.getConfig(), true);
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        canvas.drawBitmap(original, 0, 0, paint);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);

        x = Math.abs(x);
        y = Math.abs(y);
        z = Math.abs(z);

        int nbCol = (int) (8 * x * z * 100);
        int nbRow = (int) (8 * y * z * 100);

        nbCol = Math.max(Math.min(20, nbCol), 1);
        nbRow = Math.max(Math.min(20, nbRow), 1);

        int sizeC = original.getWidth() / nbCol;
        int sizeR = original.getHeight() / nbRow;

        for (int i = 0; i < nbCol; i += 2) {
            int xpos = i * sizeC;
            int ypos = original.getHeight();
            canvas.drawRect(xpos, 0, xpos + 3, ypos, paint);
        }

        for (int i = 0; i < nbRow; i += 2) {
            int ypos = i * sizeR;
            int xpos = original.getWidth();
            canvas.drawRect(0, ypos, xpos, ypos + 3, paint);
        }

        activity.setImageFiltered(result);
    }

    @Override
    public int getIcon() {
        return R.drawable.filtre4;
    }

    @Override
    public String getName() {
        return "Grille";
    }
}
