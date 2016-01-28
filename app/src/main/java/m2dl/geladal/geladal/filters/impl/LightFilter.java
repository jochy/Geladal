package m2dl.geladal.geladal.filters.impl;

import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicConvolve3x3;

import m2dl.geladal.geladal.R;
import m2dl.geladal.geladal.filters.IFilter;
import m2dl.geladal.geladal.filters.IFilterConsumer;

/**
 * Created by root on 28/01/16.
 */
public class LightFilter implements IFilter {
    @Override
    public void filter(IFilterConsumer activity, Bitmap original, float x, float y, float z) {
        x = (float) (x + 0.2);
        float[] coefficients = {-1*x, -1*x, -1*x, -1*x, 8, -1*x, -1*x, -1*x, -1*x};

        Bitmap bitmap = Bitmap.createBitmap(
                original.getWidth(), original.getHeight(),
                Bitmap.Config.ARGB_8888);

        RenderScript rs = RenderScript.create(activity.getContext());

        Allocation allocIn = Allocation.createFromBitmap(rs, original);
        Allocation allocOut = Allocation.createFromBitmap(rs, bitmap);

        ScriptIntrinsicConvolve3x3 convolution
                = ScriptIntrinsicConvolve3x3.create(rs, Element.U8_4(rs));
        convolution.setInput(allocIn);
        convolution.setCoefficients(coefficients);
        convolution.forEach(allocOut);

        allocOut.copyTo(bitmap);
        rs.destroy();
        activity.setImageFiltered(bitmap);
    }

    @Override
    public int getIcon() {
        return R.drawable.filtre3;
    }

    @Override
    public String getName() {
        return "Light";
    }


}
