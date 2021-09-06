package bike.gymproject.viewlibray;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

/**
 * ClassName:CcsbcTextView <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date: 2017年5月2日 下午6:12:34 <br/>
 *
 * @author Administrator
 */
public class AkrobatNumberTextView extends androidx.appcompat.widget.AppCompatTextView {

    public AkrobatNumberTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public AkrobatNumberTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AkrobatNumberTextView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        AssetManager assets = context.getAssets();
        Typeface font = Typeface.createFromAsset(assets, "fonts/Akrobat-ExtraBold.otf");
        setTypeface(font);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable[] drawables = getCompoundDrawables();
        if (drawables != null) {
            Drawable drawableLeft = drawables[0];
            if (drawableLeft != null) {
                float textWidth = getPaint().measureText(getText().toString());
                int drawablePadding = getCompoundDrawablePadding();
                int drawableWidth = 0;
                drawableWidth = drawableLeft.getIntrinsicWidth();
                float bodyWidth = textWidth + drawableWidth + drawablePadding;
                canvas.translate((getWidth() - bodyWidth) / 11 * 5, 0);
            }
        }
        super.onDraw(canvas);
    }
}