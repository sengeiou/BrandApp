package phone.gym.jkcq.com.socialmodule.adapter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by peng on 2018/4/20.
 */

public class RankListDecoration extends RecyclerView.ItemDecoration {
    int mDivider;
    private Paint mPaint;
    private int lef;

    public RankListDecoration(int space) {
        this.mDivider = space;
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#4DDA64"));
    }

    public RankListDecoration(int space, int lef) {
        this.mDivider = space;
        mPaint = new Paint();
        this.lef = lef;
        mPaint.setColor(Color.parseColor("#4DDA64"));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = mDivider;
        if (parent.getChildLayoutPosition(view) != 0) {
            outRect.bottom = 0;
        }
        super.getItemOffsets(outRect, view, parent, state);
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        View child = parent.getChildAt(0);
        if (child != null) {
            // 获取布局参数
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            // 根据子视图的位置 & 间隔区域，设置矩形（分割线）的2个顶点坐标(左上 & 右下)

            // 矩形左上顶点 = (ItemView的左边界,ItemView的下边界)
            // ItemView的左边界 = RecyclerView 的左边界 + paddingLeft距离 后的位置
            final int left = parent.getPaddingLeft() + lef;
            // ItemView的下边界：ItemView 的 bottom坐标 + 距离RecyclerView底部距离 +translationY
            final int top = child.getBottom() + params.bottomMargin +
                    Math.round(ViewCompat.getTranslationY(child));

            // 矩形右下顶点 = (ItemView的右边界,矩形的下边界)
            // ItemView的右边界 = RecyclerView 的右边界减去 paddingRight 后的坐标位置
            final int right = parent.getWidth() - parent.getPaddingRight() - left;
            // 绘制分割线的下边界 = ItemView的下边界+分割线的高度
            final int bottom = top + mDivider;


            // 通过Canvas绘制矩形（分割线）
            c.drawRect(left, top, right, bottom, mPaint);
        }
    }

}
