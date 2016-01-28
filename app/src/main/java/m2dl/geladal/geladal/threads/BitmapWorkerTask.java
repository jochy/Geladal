package m2dl.geladal.geladal.threads;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

import m2dl.geladal.geladal.R;

/**
 * Created by Nabil on 28/01/16.
 */
class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {

    private final WeakReference<ImageView> imageViewReference;
    private int data = 0;
    Bitmap bitmap;
    View rootView;

    public BitmapWorkerTask(Bitmap bitmap, View rootView) {
        // Use a WeakReference to ensure the ImageView can be garbage collected

        this.rootView = rootView;
        ImageView imageView = (ImageView) rootView.findViewById(R.id.basicImage);
        imageViewReference = new WeakReference<ImageView>(imageView);

        this.bitmap = bitmap;
    }

    // Decode image in background.
    @Override
    protected Bitmap doInBackground(Integer... params) {
        data = params[0];
        int value = 50;
        // image size
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // create output bitmap
        Bitmap bmOut = bitmap.copy(bitmap.getConfig(), true);
        // color information
        int A, R, G, B;
        int pixel;
        // get contrast value
        double contrast = Math.pow((100 + value) / 100, 2);
        // scan through all pixels
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                // get pixel color
                pixel = bitmap.getPixel(i, j);
                A = Color.alpha(pixel);
                // apply filter contrast for every channel R, G, B
                R = Color.red(pixel);
                R = (int) (((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if (R < 0) {
                    R = 0;
                } else if (R > 255) {
                    R = 255;
                }

                G = Color.red(pixel);
                G = (int) (((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if (G < 0) {
                    G = 0;
                } else if (G > 255) {
                    G = 255;
                }

                B = Color.red(pixel);
                B = (int) (((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if (B < 0) {
                    B = 0;
                } else if (B > 255) {
                    B = 255;
                }

                // set new pixel color to output bitmap
                bmOut.setPixel(i, j, Color.argb(A, R, G, B));

                //return decodeSampledBitmapFromResource(getResources(), data, 100, 100));
            }
        }
        return bmOut;
    }

            // Once complete, see if ImageView is still around and set bitmap.
            @Override
            protected void onPostExecute (Bitmap bitmap){
                if (imageViewReference != null && bitmap != null) {
                    final ImageView imageView = imageViewReference.get();
                    if (imageView != null) {
                        imageView.setImageBitmap(bitmap);
                    }
                }
            }

}