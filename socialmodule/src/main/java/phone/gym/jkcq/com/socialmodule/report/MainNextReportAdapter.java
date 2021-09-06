package phone.gym.jkcq.com.socialmodule.report;

import android.graphics.Paint;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonutil.LoadImageUtil;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import phone.gym.jkcq.com.commonres.commonutil.DisplayUtils;
import phone.gym.jkcq.com.socialmodule.R;
import phone.gym.jkcq.com.socialmodule.report.bean.ReportBean;
import phone.gym.jkcq.com.socialmodule.util.TimeUtil;

public class MainNextReportAdapter extends BaseQuickAdapter<ReportBean, BaseViewHolder> {

    private OnChildClickLisenter mOnChildClickLisenter;
    private OnChildLongClickLisenter mOnChildLongClickLisenter;
    private OnItemClickLisenter mOnItemClickLisenter;

    public MainNextReportAdapter(List<ReportBean> data) {
        super(R.layout.friend_item_next_report, data);
    }

    public void addOnChildClickListener(OnChildClickLisenter onChildClickLisenter) {
        this.mOnChildClickLisenter = onChildClickLisenter;
    }

    public void addOnItemClickListener(OnItemClickLisenter onItemClickLisenter) {
        this.mOnItemClickLisenter = onItemClickLisenter;
    }

    public void addOnChildLongClikeListener(OnChildLongClickLisenter onChildLongClickLisenter) {
        this.mOnChildLongClickLisenter = onChildLongClickLisenter;
    }

    @Override
    protected void convert(BaseViewHolder holder, ReportBean info) {


        holder.getView(R.id.tv_nickname).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnChildClickLisenter != null) {
                    mOnChildClickLisenter.onClick(v, info, holder.getLayoutPosition());
                }
            }
        });
        holder.getView(R.id.iv_like).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnChildClickLisenter != null) {
                    mOnChildClickLisenter.onClick(v, info, holder.getLayoutPosition());
                }
            }
        });
        holder.getView(R.id.tv_content).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mOnChildLongClickLisenter.onLongClick(v, info, holder.getLayoutPosition());
                return true;
            }
        });
        holder.getView(R.id.tv_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnChildClickLisenter != null) {
                    mOnChildClickLisenter.onClick(v, info, holder.getLayoutPosition());
                }
            }
        });
        /*holder.getView(R.id.tv_replay_nikename).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnChildClickLisenter != null) {
                    mOnChildClickLisenter.onClick(v, info, holder.getLayoutPosition());
                }
            }
        });*/
        holder.getView(R.id.iv_head_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnChildClickLisenter != null) {
                    mOnChildClickLisenter.onClick(v, info, holder.getLayoutPosition());
                }
            }
        });
        holder.getView(R.id.tv_del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnChildClickLisenter != null) {
                    mOnChildClickLisenter.onClick(v, info, holder.getLayoutPosition());
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickLisenter != null) {
                    mOnItemClickLisenter.onClick(v, info, holder.getLayoutPosition());
                }
            }
        });
        holder.setText(R.id.tv_nickname, info.getFromNickName());
        holder.setText(R.id.tv_like_number, info.getPraiseNums() + "");

        holder.setText(R.id.tv_send_time, TimeUtil.getDynmicTime(info.getCreateTime(), ""));
        LoadImageUtil.getInstance().loadCirc(getContext(), info.getFromHeadUrlTiny(), holder.getView(R.id.iv_head_photo));

        if (info.isWhetherPraise()) {
            holder.setImageResource(R.id.iv_like, R.drawable.icon_report_like_press);
        } else {
            holder.setImageResource(R.id.iv_like, R.drawable.icon_report_like_nor);
        }

        if (info.getFromUserId().equals(TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()))) {
            holder.setGone(R.id.tv_del, false);
        } else {
            holder.setGone(R.id.tv_del, true);
        }


        if (info.getAuthorType() == 2 || info.getAuthorType() == 7 || info.getAuthorType() == 8) {

            String connect = UIUtils.getString(R.string.comment_reply) + info.getToNickName() + " ：" + info.getContent();
            SpannableString str = getClickableSpan(info.getToNickName(), connect);
            holder.setText(R.id.tv_content, str);
            holder.setVisible(R.id.tv_replay_nikename, true);
            TextView tvReplayNikename = holder.getView(R.id.tv_replay_nikename);
            ViewGroup.LayoutParams params = tvReplayNikename.getLayoutParams();
            Paint paint = tvReplayNikename.getPaint();
            params.width = (int) paint.measureText(info.getToNickName()) + DisplayUtils.dip2px(getContext(), 10);

            //  Logger.e("params.width", params.width + ",DisplayUtils.getScreenWidth(getContext())=" + DisplayUtils.getScreenWidth(getContext())+",DisplayUtils.dip2px(getContext(), 105)="+DisplayUtils.dip2px(getContext(), 105));

            if (DisplayUtils.getScreenWidth(getContext()) - params.width < (DisplayUtils.dip2px(getContext(), 105))) {
                params.width = DisplayUtils.getScreenWidth(getContext()) - DisplayUtils.dip2px(getContext(), 180);
            }
            // Logger.e("params.width", params.width +"");

            tvReplayNikename.setLayoutParams(params);

        } else {
           /* String connect = UIUtils.getString(R.string.comment_reply) + info.getToNickName() + " ：" + info.getContent();
            SpannableString str = getClickableSpan(info.getToNickName(), connect);
            holder.setText(R.id.tv_content, str);*/
            holder.setText(R.id.tv_content, info.getContent());
            holder.setVisible(R.id.tv_replay_nikename, false);
        }

        switch (info.getAuthorType()) {
            //(authorType:作者类型:5作者，6楼主，2回复，0无文字)
            case 5:
            case 11:
            case 13:
            case 7:
                holder.setGone(R.id.tv_acti, false);
                holder.setText(R.id.tv_acti, UIUtils.getString(R.string.comment_author));
                break;
            case 6:
            case 8:
                holder.setGone(R.id.tv_acti, false);
                holder.setText(R.id.tv_acti, UIUtils.getString(R.string.comment_host));
                break;
            case 2:
                holder.setGone(R.id.tv_acti, true);
                break;
            case 0:
                holder.setGone(R.id.tv_acti, true);
                break;
        }

    }

    private SpannableString getClickableSpan(String linkConnent, String connent) {
        int index = connent.indexOf(linkConnent);
        SpannableString spannableString = new SpannableString(connent);
        if (index >= 0 && index < connent.length()) {


/*//设置下划线文字


//设置文字的单击事件

        */

//设置文字的前景色
           /* spannableString.setSpan(new UnderlineSpan(), index, index + linkConnent.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            spannableString.setSpan(new ClickableSpan() {

                @Override

                public void onClick(View widget) {

                    ToastUtils.showToast(BaseApp.getApp(), "点击了名字");

                }

            }, index, index + linkConnent.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);*/

            spannableString.setSpan(new ForegroundColorSpan(UIUtils.getColor(R.color.color_warm_up)), index, index + linkConnent.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        }

        return spannableString;

    }


    public interface OnChildLongClickLisenter {
        void onLongClick(View view, ReportBean info, int position);
    }

    public interface OnChildClickLisenter {
        void onClick(View view, ReportBean info, int position);
    }

    public interface OnItemClickLisenter {
        void onClick(View view, ReportBean info, int position);
    }


}
