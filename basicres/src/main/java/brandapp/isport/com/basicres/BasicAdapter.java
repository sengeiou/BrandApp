package brandapp.isport.com.basicres;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ListView的Adapter的基类。
 * @version 创建时间：
 */
public abstract class BasicAdapter<T, V extends BasicAdapter.BaseViewHolder> extends BaseAdapter {
    /**
     * 上下文参数
     */
    protected Context mContext;
    /**
     * 数据源《泛型》
     */
    protected List<T> listSource = new ArrayList<>();

    public BasicAdapter(Context mContext) {
        this.mContext = mContext;
    }

    // 提供一个公共的方法，用于更新ListView的list 数据，并刷新ListView。
    public void setData(List<T> mList) {
        this.listSource = new ArrayList<>();
        this.listSource.addAll(mList);
        notifyDataSetChanged();
    }

    /**
     * 追加数据 不清空原有数据
     *
     * @param listSource
     */
    public void appendData(List<T> listSource) {
        if (null == listSource || listSource.isEmpty()) {
            return;
        }
        if (null == this.listSource) {
            this.listSource = new ArrayList<T>();
        }
        this.listSource.addAll(listSource);
    }

    public List<T> getData() {
        return listSource;
    }

    @Override
    public int getCount() {
        return (null == listSource) ? 0 : listSource.size();
    }

    @Override
    public T getItem(int position) {
        return (null == listSource || position >= listSource.size()) ? null : listSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View contentView, ViewGroup viewGroup) {
        BaseViewHolder baseViewHolder;
        if (contentView == null) {
            int layoutResID = getLayoutId();
            if (layoutResID <= 0) {
                throw new NullPointerException("view id is 0.");
            }
            contentView = LayoutInflater.from(mContext).inflate(layoutResID,
                    viewGroup, false);
            baseViewHolder = bindBaseViewHolder(contentView);
            contentView.setTag(baseViewHolder);
        } else {
            baseViewHolder = (BaseViewHolder) contentView.getTag();
        }
        T item = getItem(position);
        initData((V) baseViewHolder, position, item);
        initEvent((V) baseViewHolder, position, item);
        return contentView;
    }

    /**
     * 获取layout Id
     *
     * @return layout Id
     */
    protected abstract int getLayoutId();

    /**
     * 绑定ViewHolder
     *
     * @param contentView
     * @return
     */
    protected abstract V bindBaseViewHolder(View contentView);

    /**
     * 数据处理
     *
     * @param viewHolder
     * @param position
     * @param item
     */
    protected abstract void initData(final V viewHolder, final int position, final T item);

    /**
     * 设置监听
     *
     * @param viewHolder
     * @param position
     * @param item
     */
    protected abstract void initEvent(final V viewHolder, final int position, final T item);

    public abstract class BaseViewHolder {
        public BaseViewHolder(View itemView) {
        }
    }
}