package brandapp.isport.com.basicres.commonrecyclerview;

import android.graphics.Rect;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.View;

/*
 * 瀑布流
 * classes : com.snscity.globalexchange.view.recyclerview
 * @author 苗恒聚
 * V 1.0.0
 * Create at 2015-12-9 14:17
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    /**
     * 所有Item总数
     */
    private int itemCount;

    /**
     * 每行显示的数量
     */
    private int spanCount;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public void setSpanCount(int spanCount) {
        this.spanCount = spanCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (null == parent) {
            return;
        }

        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (null == layoutManager) {
            return;
        }

        if (layoutManager instanceof LinearLayoutManager) {
            outRect.bottom = space;
            outRect.top = space;
            outRect.left = space;
            outRect.right = space;
            return;
        } else if (layoutManager instanceof GridLayoutManager) {
            outRect.bottom = space;
            outRect.top = space;
            outRect.left = space;
            outRect.right = space;
            return;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            if (parent.getChildAdapterPosition(view) == 0) {
                return;
            }
            if (parent.getChildAdapterPosition(view) >= itemCount) {
                outRect.bottom = space;
                outRect.top = space;
            }
            outRect.left = space;
            outRect.right = space;
        }
    }
}
