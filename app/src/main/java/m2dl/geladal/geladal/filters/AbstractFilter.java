package m2dl.geladal.geladal.filters;

import android.graphics.Bitmap;

/**
 * Created by Alexandre on 28/01/2016.
 */
public abstract class AbstractFilter implements IFilter {

    private Thread thread;

    @Override
    public void filter(final IFilterConsumer activity, final Bitmap original, final float x, final float y, final float z) {
        if (thread == null || !thread.isAlive()) {
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    filterImpl(activity, original, x, y, z);
                }
            });
            thread.start();
        }
    }

    public abstract void filterImpl(IFilterConsumer activity, Bitmap original, float x, float y, float z);

    @Override
    public abstract int getIcon();

    @Override
    public abstract String getName();
}
