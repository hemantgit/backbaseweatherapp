package bartburg.nl.backbaseweather.util;

import android.view.View;
import android.view.animation.AlphaAnimation;

/**
 * Created by Bart on 6/6/2017.
 */

public class ViewTransparencyAnimator {
    public static void animateTransparency(boolean animate, float transparencyFrom, float transparencyTo, View... viewsToTransform) {
        AlphaAnimation animation1 = new AlphaAnimation(transparencyFrom, transparencyTo);
        animation1.setDuration(animate ? 150 : 0);
        animation1.setStartOffset(0);
        animation1.setFillAfter(true);
        for (View view : viewsToTransform) {
            view.startAnimation(animation1);
        }
    }


    public static void animateTransparency(float transparencyFrom, float transparencyTo, View... viewsToTransform) {
        animateTransparency(true, transparencyFrom, transparencyTo, viewsToTransform);
    }
}
