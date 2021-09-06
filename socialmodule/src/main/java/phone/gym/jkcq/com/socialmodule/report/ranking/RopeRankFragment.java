package phone.gym.jkcq.com.socialmodule.report.ranking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.ViewMultiClickUtil;
import brandapp.isport.com.basicres.mvp.BaseMVPFragment;
import phone.gym.jkcq.com.commonres.commonutil.DisplayUtils;
import phone.gym.jkcq.com.socialmodule.FriendConstant;
import phone.gym.jkcq.com.socialmodule.R;
import phone.gym.jkcq.com.socialmodule.activity.PersonalHomepageActivity;
import phone.gym.jkcq.com.socialmodule.adapter.RankListDecoration;
import phone.gym.jkcq.com.socialmodule.adapter.RopeRankAdapter;
import phone.gym.jkcq.com.socialmodule.bean.PraiseInfo;
import phone.gym.jkcq.com.socialmodule.bean.RankInfo;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RopeRankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RopeRankFragment extends BaseMVPFragment<RopeRankView, RopeRankPresenter> implements RopeRankView {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mMyUserId;
    private RecyclerView recycler_sport_rank;
    private TextView tv_data_origin;
    private RopeRankAdapter mSportRankAdapter;
    private List<RankInfo> mDataList = new ArrayList<>();
    private RankInfo mCurrentInfo;
    private int mCurrentPostion;
    private int mMotionType;
    private String mParam2;

    public RopeRankFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param motionType Parameter 1.
     * @param param2     Parameter 2.
     * @return A new instance of fragment SportRankFragment.
     */
    public static RopeRankFragment newInstance(int motionType, String param2) {
        RopeRankFragment fragment = new RopeRankFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, motionType);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMotionType = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sport_rank;
    }

    @Override
    protected void initView(View view) {
        tv_data_origin = mView.findViewById(R.id.tv_data_origin);
        tv_data_origin.setText(getResources().getString(R.string.rope_ranking_data_from));
        recycler_sport_rank = mView.findViewById(R.id.recycler_sport_rank);
        recycler_sport_rank.setLayoutManager(new LinearLayoutManager(getActivity()));
//        for (int i = 0; i < 10; i++) {
//            mDataList.add(new RankInfo());
//        }
        mSportRankAdapter = new RopeRankAdapter(mDataList);
        recycler_sport_rank.setAdapter(mSportRankAdapter);
        recycler_sport_rank.addItemDecoration(new RankListDecoration(DisplayUtils.dip2px(getActivity(), 0.5f), DisplayUtils.dip2px(getActivity(), 15)));
        mSportRankAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if (!ViewMultiClickUtil.onMultiClick(view)) {
                    if (view.getId() == R.id.iv_like) {
                        mCurrentInfo = mDataList.get(position);
                        mCurrentPostion = position;
                        mFragPresenter.PraiseRank(mMyUserId, mCurrentInfo.getUserId(), mCurrentInfo.getMotionType(), 7);
                    }

                }
            }
        });
        mSportRankAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if (!ViewMultiClickUtil.onMultiClick(view)) {
                    Intent intent = new Intent(getActivity(), PersonalHomepageActivity.class);
                    intent.putExtra(FriendConstant.USER_ID, mDataList.get(position).getUserId());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void initData() {
        mMyUserId = TokenUtil.getInstance().getPeopleIdStr(getActivity());
        //mFragPresenter.getRankInfo(TokenUtil.getInstance().getPeopleIdInt(getActivity()), mMotionType, 7);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void onResume() {
        super.onResume();
        mFragPresenter.getRankInfo(dateFormat.format(new Date()), mMotionType + "", TokenUtil.getInstance().getPeopleIdInt(BaseApp.instance));

    }

    @Override
    protected RopeRankPresenter createPersenter() {
        return new RopeRankPresenter(this);
    }

    @Override
    public void initImmersionBar() {

    }

    @Override
    public void onSuccessPraise(PraiseInfo info) {
//        mCurrentInfo.setWhetherPraise(info.isWhetherPraise());
//        mCurrentInfo.setPraiseNums(info.getPraiseNums());
//        mSportRankAdapter.notifyItemChanged(mCurrentPostion);

        mFragPresenter.getRankInfo(dateFormat.format(new Date()), mMotionType + "", TokenUtil.getInstance().getPeopleIdInt(BaseApp.instance));

    }

    @Override
    public void getRankInfoSuccess(List<RankInfo> listData) {
        if (listData != null) {
            mDataList = listData;
            mSportRankAdapter.setList(mDataList);
        }

    }
}
