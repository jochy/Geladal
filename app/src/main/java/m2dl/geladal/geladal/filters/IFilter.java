package m2dl.geladal.geladal.filters;

import android.graphics.Bitmap;

/**
 * Created by Alexandre on 28/01/2016.
 */
public interface IFilter {
    Bitmap filter(Bitmap original, float x, float y, float z);
}
