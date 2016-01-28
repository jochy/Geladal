package m2dl.geladal.geladal.filters.impl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;

import java.util.Random;

import m2dl.geladal.geladal.R;
import m2dl.geladal.geladal.filters.AbstractFilter;
import m2dl.geladal.geladal.filters.IFilter;
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
        Random r = new Random();
        int low = 0;
        int high = 6;
        int randomNumber = Math.round(Math.abs(x)+Math.abs(y)+Math.abs(z));
        //int randomNumber = r.nextInt(high-low) + low;

        int[] colorArray = {Color.RED,Color.BLUE,Color.GREEN,Color.YELLOW,Color.MAGENTA,Color.CYAN,Color.WHITE};

        // Color
        paint.setColorFilter(new PorterDuffColorFilter(colorArray[randomNumber], PorterDuff.Mode.MULTIPLY));
        canvas.drawBitmap(original, 0, 0, paint);
        paint.setColorFilter(null);
       /* // Shadows
        paint.setXfermode(new PorterDuffXfermode(Mode.MULTIPLY));
        canvas.drawBitmap(buttonShadows, 0, 0, paint);
        // HighLights
        paint.setXfermode(new PorterDuffXfermode(Mode.SCREEN));
        canvas.drawBitmap(buttonHighLights, 0, 0, paint); */
        paint.setXfermode(null);
        activity.setImageFiltered(result);



    }

    @Override
    public int getIcon() {
        return R.drawable.filtre4;
    }

    @Override
    public String getName() {
        return "Color Filter";
    }
}
