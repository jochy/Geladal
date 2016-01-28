package m2dl.geladal.geladal.filters;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by Alexandre on 28/01/2016.
 */
public interface IFilterConsumer {
    public void setImageFiltered(Bitmap bitmap);

    public Context getContext();
}
