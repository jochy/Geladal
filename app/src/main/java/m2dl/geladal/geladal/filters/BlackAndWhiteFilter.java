package m2dl.geladal.geladal.filters;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by Nabil on 28/01/16.
 */
public class BlackAndWhiteFilter implements  IFilter {


    @Override
    public Bitmap filter(Bitmap original, float x, float y, float z) {
            int value = 50 ;
// image size
            int width = original.getWidth();
            int height = original.getHeight();
// create output bitmap
            Bitmap bmOut = Bitmap.createBitmap(width, height, original.getConfig());
// color information
            int A, R, G, B;
            int pixel;
// get contrast value
            double contrast = Math.pow((100 + value) / 100, 2);
// scan through all pixels
            for(int i = 0; i < width; ++i) {
                for(int j = 0; j < height; ++j) {
                    // get pixel color
                    pixel = original.getPixel(i, j);
                    A = Color.alpha(pixel);
                    // apply filter contrast for every channel R, G, B
                    R = Color.red(pixel);
                    R = (int)(((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                    if(R < 0) { R = 0; }
                    else if(R > 255) { R = 255; }

                    G = Color.red(pixel);
                    G = (int)(((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                    if(G < 0) { G = 0; }
                    else if(G > 255) { G = 255; }

                    B = Color.red(pixel);
                    B = (int)(((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                    if(B < 0) { B = 0; }
                    else if(B > 255) { B = 255; }

                    // set new pixel color to output bitmap
                    bmOut.setPixel(i, j, Color.argb(A, R, G, B));
                }


            return bmOut;
        }




        return null;
    }

    @Override
    public Bitmap getIcon() {
        return null;
    }

    @Override
    public String getName() {
        return "Black and White";
    }
}
