package brandapp.isport.com.basicres.commonrecyclerview.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.SoftReference;
import java.lang.reflect.Constructor;

public class ViewHolder {
    private final SparseArray<SoftReference<View>> mViews;
    private final SparseArray<SoftReference<View>> childViews;
    private int mPosition;
    private View mConvertView;
    private ViewGroup parent;
    private Context mContext;

    private ViewHolder(Context context, ViewGroup parent, int layoutId,
                       int position) {
        this.mPosition = position;
        this.mContext = context;
        this.parent = parent;
        this.mViews = new SparseArray<SoftReference<View>>();
        this.childViews = new SparseArray<SoftReference<View>>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        // setTag
        mConvertView.setTag(this);
    }

    private ViewHolder(Context context, ViewGroup parent, View view,
                       int position) {
        this.mPosition = position;
        this.parent = parent;
        this.mViews = new SparseArray<SoftReference<View>>();
        this.childViews = new SparseArray<SoftReference<View>>();
        mConvertView = view;
        // setTag
        mConvertView.setTag(this);
    }

    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }

    /**
     * 拿到�?��ViewHolder对象
     *
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId
     * @param position
     * @return
     */
    public static ViewHolder get(Context context, View convertView,
                                 ViewGroup parent, int layoutId, int position) {
        ViewHolder holder;
        holder = new ViewHolder(context, parent, layoutId, position);
        /*if (convertView == null) {
            holder = new ViewHolder(context, parent, layoutId, position);
        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.mPosition = position;
        }*/
        return holder;
    }

    /**
     * 拿到ViewHolder对象
     *
     * @param context
     * @param convertView
     * @param parent
     * @param
     * @param position
     * @return
     */
    public static ViewHolder get(Context context, View convertView,
                                 ViewGroup parent, Class<? extends View> view, int position) throws Exception {
        ViewHolder holder = null;
        if (convertView == null) {
            Constructor<?> constructor = view.getConstructor(Context.class);
            View contentView = (View) constructor.newInstance(context);
            holder = new ViewHolder(context, parent, contentView, position);
        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.mPosition = position;
        }
        return holder;
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId) != null ? mViews.get(viewId).get() : null;
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, new SoftReference<View>(view));
        }
        return (T) view;
    }

    /**
     * 为TextView设置字符
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, CharSequence text) {
        TextView view = getView(viewId);
        if (view != null) {
            view.setText(text);
        }
        return this;
    }

    /**
     * 为TextView设置字符
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, CharSequence text, float textSize, boolean isShow) {
        TextView view = getView(viewId);
        if (view != null) {
            view.setText(text);
            if (isShow) {
                // view.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
                view.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            }
        }
        return this;
    }

    public ViewHolder setText(int viewId, CharSequence text, boolean isShow) {
        TextView view = getView(viewId);
        if (view != null) {
            view.setText(text);
        }
        view.setVisibility(isShow ? View.VISIBLE : View.GONE);
        return this;
    }

    public ViewHolder setText(int viewId, int res, boolean isShow) {
        TextView view = getView(viewId);
        view.setText(mContext.getString(res));
        view.setVisibility(isShow ? View.VISIBLE : View.GONE);
        return this;
    }


    /**
     *
     */
    public ViewHolder setText(int viewId, boolean isShow) {
        TextView view = getView(viewId);
        if (isShow) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
        return this;
    }

    public ViewHolder setText(int viewId, Drawable drawable) {
        TextView view = getView(viewId);
        drawable.setBounds(0, 1, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        view.setCompoundDrawables(drawable, null, null, null);
        return this;
    }

    /**
     * 设置TextView的颜色和字符
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, String text, int color) {
        TextView view = getView(viewId);
        view.setTextColor(color);
        view.setText(text);
        return this;
    }

    public ViewHolder setText(int viewId, int color) {
        TextView view = getView(viewId);
        view.setTextColor(color);
        return this;
    }

    /**
     * 设置图片
     * @param viewId
     * @param
     * @param
     * @return
     */


    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public ViewHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);

        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param
     * @return
     */
    public ViewHolder setImageBitmap(int viewId, Bitmap bm) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }

    /**
     * 临时创建view，但不显
     *
     * @param viewId
     * @param
     */
    public void setChildView(int viewId, View childView) {
        if (getView(viewId) == null) {
            return;
        }
        childViews.put(viewId, new SoftReference<View>(childView));
    }

    /**
     * 获取子view
     *
     * @param viewId
     */
    public <T extends View> T getChildView(int viewId) {
        View view = childViews.get(viewId) != null ? childViews.get(viewId).get() : null;
        return (T) view;
    }

    public ViewGroup getParent() {
        return parent;
    }

    public int getPosition() {
        return mPosition;
    }

}
