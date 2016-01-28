package m2dl.geladal.geladal.filters.impl;

import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import m2dl.geladal.geladal.R;
import m2dl.geladal.geladal.filters.IFilter;
import m2dl.geladal.geladal.filters.IFilterConsumer;

/**
 * Created by Nabil on 28/01/16.
 */
public class BlurFilter implements IFilter {

    @Override
    public void filter(IFilterConsumer activity, Bitmap original, float x, float y, float z) {
        Bitmap bitmap = Bitmap.createBitmap(
                original.getWidth(), original.getHeight(),
                Bitmap.Config.ARGB_8888);
        RenderScript rs = RenderScript.create(activity.getContext());
        Allocation allocIn = Allocation.createFromBitmap(rs, original);
        Allocation allocOut = Allocation.createFromBitmap(rs, bitmap);
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(
                rs, Element.U8_4(rs));
        blur.setInput(allocIn);
        float res = Math.abs(x) + Math.abs(y) + Math.abs(z);
        blur.setRadius(Math.max(res * 2 - 1, 15));
        blur.forEach(allocOut);
        allocOut.copyTo(bitmap);
        rs.destroy();
        activity.setImageFiltered(bitmap);
    }

    @Override
    public int getIcon() {
        return R.drawable.filtre2;
    }

    @Override
    public String getName() {
        return "Blur";
    }
}
