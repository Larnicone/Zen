package zenproject.meditation.android.sketch.actions.screenshot;

import com.juankysoriano.rainbow.core.drawing.RainbowDrawer;
import com.novoda.notils.logger.toast.Toaster;

import zenproject.meditation.android.ContextRetriever;
import zenproject.meditation.android.R;
import zenproject.meditation.android.SketchRetriever;
import zenproject.meditation.android.analytics.AnalyticsTracker;

public class ScreenshotTaker {
    private static final String ZEN = ContextRetriever.INSTANCE.getResources().getString(R.string.app_name);
    private static final String PICTURE_TITLE = ContextRetriever.INSTANCE.getResources().getString(R.string.picture_title);
    private final RainbowDrawer rainbowDrawer;

    ScreenshotTaker(RainbowDrawer rainbowDrawer) {
        this.rainbowDrawer = rainbowDrawer;
    }

    public static ScreenshotTaker newInstance() {
        return new ScreenshotTaker(SketchRetriever.INSTANCE.getZenSketch().getRainbowDrawer());
    }

    public void takeScreenshot() {
        //rainbowDrawer.save(ZEN, PICTURE_TITLE);
        Toaster.newInstance(ContextRetriever.INSTANCE.getApplicationContext()).popToast(R.string.save_sketch);
        AnalyticsTracker.INSTANCE.trackScreenshot();
    }
}
