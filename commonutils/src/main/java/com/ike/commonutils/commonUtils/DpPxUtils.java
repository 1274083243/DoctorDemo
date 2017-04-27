package com.ike.commonutils.commonUtils;

import android.content.Context;
import android.content.res.Resources;

public class DpPxUtils {

	/**
	 * dip转换px
	 * 
	 * @return
	 */
	public static int dip2px(int dip,Context context) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dip * scale + 0.5f);
	}

	/**
	 * px转换dip
	 */
	public static int px2dip(int px,Context context) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}
	/**
     * sp转px.
     *
     * @param value   the value
     * @param context the context
     * @return the int
     */
    public static int sp2px(float value, Context context) {
        Resources r;
        if (context == null) {
            r = Resources.getSystem();
        } else {
            r = context.getResources();
        }
        float spvalue = value * r.getDisplayMetrics().scaledDensity;
        return (int) (spvalue + 0.5f);
    }
    /**
     * px转sp.
     *
     * @param value   the value
     * @param context the context
     * @return the int
     */
    public static int px2sp(float value, Context context) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (value / scale + 0.5f);
    }
}
