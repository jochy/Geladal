package m2dl.geladal.geladal.filters;

import android.graphics.Bitmap;

/**
 * Created by Alexandre on 28/01/2016.
 */
public interface IFilter {
    void filter(IFilterConsumer activity, Bitmap original, float x, float y, float z);

    void filterOnTouch(IFilterConsumer activity, Bitmap original, float x, float y);

    int getIcon();

    String getName();
}
