package m2dl.geladal.geladal.filters.impl;

import android.graphics.Bitmap;
import android.graphics.Color;

import m2dl.geladal.geladal.R;
import m2dl.geladal.geladal.filters.AbstractFilter;
import m2dl.geladal.geladal.filters.IFilterConsumer;

/**
 * Created by root on 28/01/16.
 */
public class NewFilter extends AbstractFilter {
    @Override
    public void filterImpl(IFilterConsumer activity, Bitmap original, float x, float y, float z) { // get image size
        Bitmap bmOut = Bitmap.createBitmap(original.getWidth(), original.getHeight(), original.getConfig());
        // color info
        int A, R, G, B;
        int pixelColor;
        // image size
        int height = original.getHeight();
        int width = original.getWidth();

        // scan through every pixel
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                // get one pixel
                pixelColor = original.getPixel(i, j);
                // saving alpha channel
                A = Color.alpha(pixelColor);
                // inverting byte for each R/G/B channel
                R = 255 - Color.red(pixelColor);
                G = 255 - Color.green(pixelColor);
                B = 255 - Color.blue(pixelColor);
                // set newly-inverted pixel to output image
                //bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }
        activity.setImageFiltered(bmOut);
    }

    @Override
    public int getIcon() {
        return R.drawable.filtre1;
    }

    @Override
    public String getName() {
        return "New";
    }


}
