package brandapp.isport.com.basicres.commonutil;

import android.graphics.Bitmap;
import android.view.View;

public class BitmapUtil {

    /**
     * 截图view的任意图片
     */
    public static Bitmap getAnyViewShot(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
//        bitmap.setConfig(Bitmap.Config.ARGB_4444);
//        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache(), left, top, right, bottom);
        view.setDrawingCacheEnabled(false);
        view.destroyDrawingCache();
        return bitmap;
    }



}
