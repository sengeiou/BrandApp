package com.isport.brandapp.ropeskipping.history;

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import brandapp.isport.com.basicres.BaseApp
import brandapp.isport.com.basicres.commonbean.UserInfoBean
import brandapp.isport.com.basicres.commonutil.MessageEvent
import brandapp.isport.com.basicres.commonutil.ToastUtils
import brandapp.isport.com.basicres.commonutil.TokenUtil
import brandapp.isport.com.basicres.commonutil.UIUtils
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.isport.blelibrary.db.table.s002.DailyBrief
import com.isport.blelibrary.db.table.s002.Summary
import com.isport.blelibrary.utils.AppLanguageUtil
import com.isport.blelibrary.utils.DateUtil
import com.isport.blelibrary.utils.Logger
import com.isport.blelibrary.utils.TimeUtils
import com.isport.brandapp.AppConfiguration
import com.isport.brandapp.R
import com.isport.brandapp.device.dialog.BaseDialog
import com.isport.brandapp.device.share.NewShareActivity
import com.isport.brandapp.device.share.ShareBean
import com.isport.brandapp.device.sleep.TimeUtil
import com.isport.brandapp.device.sleep.calendar.WatchPopCalendarView
import com.isport.brandapp.login.ropeshare.ActivityRopeDetailWebView
import com.isport.brandapp.ropeskipping.RopeSkippingPresenter
import com.isport.brandapp.ropeskipping.RopeSkippingView
import com.isport.brandapp.ropeskipping.history.adpter.CenterLayoutManager
import com.isport.brandapp.ropeskipping.history.bean.BarInfo
import com.isport.brandapp.ropeskipping.history.bean.HistoryDateBean
import com.isport.brandapp.ropeskipping.response.ResponseDailySummaries
import com.jkcq.homebike.history.adpter.BarAdapter
import com.jkcq.homebike.history.adpter.HistoryDayDetailAdapter
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.AutoDisposeConverter
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import kotlinx.android.synthetic.main.app_fragment_rope_day.*
import kotlinx.android.synthetic.main.item_main_deivce_list.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import phone.gym.jkcq.com.commonres.common.JkConfiguration
import java.text.SimpleDateFormat
import java.util.*

class RopeDayFragment() : Fragment(), WatchPopCalendarView.MonthDataListen, RopeSkippingView {
    //柱状图的变量初始化
    var mDataList = mutableListOf<BarInfo>()
    lateinit var mBarAdapter: BarAdapter
    lateinit var mCenterLayoutManager: CenterLayoutManager


    val mRopeSkippingPresenter: RopeSkippingPresenter by lazy { RopeSkippingPresenter(this@RopeDayFragment) }


    //运动详情的初始化
    var mDetailBean = mutableListOf<DailyBrief>()
    lateinit var bean: DailyBrief
    lateinit var barInfo: BarInfo
    lateinit var mHistoryDayDetailAdapter: HistoryDayDetailAdapter


    var mCurrentMessage: BarInfo? = null
    var mCurrentPosition = 0
    var currentPageNumber = 1

    private var calendarview: WatchPopCalendarView? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.app_fragment_rope_day, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBarRec()
        initSportDetailRec()
        //initSportDetailRec()
        initEvent()
        initData()

    }

    fun initEvent() {

        if (strDate.equals(dateFormat.format(Date()))) {
            iv_data_right.visibility = View.INVISIBLE
        } else {
            iv_data_right.visibility = View.VISIBLE
        }
        iv_data_left.setOnClickListener {
            EventBus.getDefault().post(MessageEvent(MessageEvent.DayAdd))
        }
        iv_data_right.setOnClickListener {
            EventBus.getDefault().post(MessageEvent(MessageEvent.DaySub))

        }

    }

    fun initData() {
        tv_update_time.setText(strDate)
        mRopeSkippingPresenter.getSummary(TokenUtil.getInstance().getPeopleIdInt(BaseApp.instance), DateUtil.getdateFormatyyMMdd(date * 1000L), "DAY")
        mRopeSkippingPresenter.getDailyBrief(strDate, TokenUtil.getInstance().getPeopleIdInt(BaseApp.instance));
    }

    fun initBarRec() {
        mCenterLayoutManager = CenterLayoutManager(activity, RecyclerView.HORIZONTAL, true)
        recyclerview_sport.layoutManager = mCenterLayoutManager
        mBarAdapter = BarAdapter(mDataList, 5)
        recyclerview_sport.adapter = mBarAdapter

        mBarAdapter.setOnItemClickListener(OnItemClickListener { adapter, view, position ->

            if (TextUtils.isEmpty(mDataList.get(position).date)) {
                return@OnItemClickListener;
            }

            /*  mCenterLayoutManager.smoothScrollToPosition(
                      recyclerview_sport,
                      RecyclerView.State(),
                      position
              )*/
            for (user in mDataList) {
                user.isSelect = false;
            }

            mDataList.get(position).isSelect = true;
            adapter.notifyDataSetChanged()
        })
    }

    fun initSportDetailRec() {
        recycle_sport_detail.layoutManager = LinearLayoutManager(activity)
        mHistoryDayDetailAdapter = HistoryDayDetailAdapter(mDetailBean)
        recycle_sport_detail.adapter = mHistoryDayDetailAdapter

        mHistoryDayDetailAdapter.setOnItemClickListener(OnItemClickListener { adapter, view, position ->
            var intent = Intent(activity, ActivityRopeDetailWebView::class.java)
            intent.putExtra("title", UIUtils.getString(R.string.rope_dtail))
            intent.putExtra("urldark", AppConfiguration.ropedetailDarkurl + "?userId=" + TokenUtil.getInstance().getPeopleIdInt(BaseApp.instance) + "&ropeId=" + mDetailBean.get(position).ropeSportDetailId + "&language=" + AppLanguageUtil.getCurrentLanguageStr())
            intent.putExtra("urlLigh", AppConfiguration.ropedetailLighturl + "?userId=" + TokenUtil.getInstance().getPeopleIdInt(BaseApp.instance) + "&ropeId=" + mDetailBean.get(position).ropeSportDetailId + "&language=" + AppLanguageUtil.getCurrentLanguageStr())
            startActivity(intent)
        })
    }


    private val dateFormat = SimpleDateFormat("yyyy-MM-dd")

    fun setCurrentDate(value: String) {
        // tv_date_current_value.text = value
    }

    var date: Int = 0
    lateinit var deviceId: String
    lateinit var userId: String
    lateinit var strDate: String
    lateinit var userInfoBean: UserInfoBean
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        date = arguments!!.getInt("date")
        deviceId = arguments!!.getString("deviceId").toString()
        userId = TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp())
        strDate = dateFormat.format(Date(date * 1000L))
        /*date = arguments!!.getInt("date")
        deviceId = arguments!!.getString("deviceId")
        userId = TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp())
        strDate = .format(Date(date * 1000L))
        deviceBean = arguments!!.getSerializable(JkConfiguration.DEVICE) as DeviceBean?
        Logger.myLog("DayReportFragment$deviceBean")
        if (deviceBean != null) {
            deviceId = deviceBean.deviceName
            currentType = deviceBean.currentType
        } else {
            deviceId = ""
            currentType = 0
        }
        userInfoBean = CommonUserAcacheUtil.getUserInfo(TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()))*/
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }


    private var ivPreDate: ImageView? = null
    private var ivNextDate: ImageView? = null
    private var tvDatePopTitle: TextView? = null
    private var tvBackToay: TextView? = null

    fun setPopupWindow(context: Context, view: View?) {
        try {

            var mMenuViewBirth = BaseDialog.Builder(context)
                    .setContentView(R.layout.app_activity_watch_dem)
                    .fullWidth()
                    .setCanceledOnTouchOutside(false)
                    .fromBottom(true)
                    .show()
            val view_hide = mMenuViewBirth!!.findViewById<View>(R.id.view_hide)
            calendarview = mMenuViewBirth!!.findViewById<View>(R.id.calendar) as WatchPopCalendarView
            ivPreDate = mMenuViewBirth!!.findViewById<View>(R.id.iv_pre) as ImageView
            ivNextDate = mMenuViewBirth!!.findViewById<View>(R.id.iv_next) as ImageView
            tvDatePopTitle = mMenuViewBirth!!.findViewById<View>(R.id.tv_date) as TextView
            tvBackToay = mMenuViewBirth!!.findViewById<View>(R.id.tv_back_today) as TextView
            view_hide!!.setOnClickListener { mMenuViewBirth!!.dismiss() }
            calendarview!!.setOnCellTouchListener(WatchPopCalendarView.OnCellTouchListener { cell -> /*if (cell.getStartTime() <= 0) {
                    Toast.makeText(BaseApp.getApp(),"没有数据", Toast.LENGTH_SHORT).show();
                    return;
                }
               */
                val stime = cell.startTime
                val dateStr = cell.dateStr
                if (TextUtils.isEmpty(dateStr)) {
                    return@OnCellTouchListener
                }
                val date = TimeUtils.stringToDate(dateStr)
                val mCurrentDate = TimeUtils.getCurrentDate()
                //未来的日期提示用户不可选
                if (!date.before(mCurrentDate)) {
                    ToastUtils.showToast(UIUtils.getContext(), UIUtils.getString(R.string.select_date_error))
                    return@OnCellTouchListener
                }

                mMenuViewBirth!!.dismiss()
                tv_update_time.setText(dateStr)

                val messageEvent = MessageEvent(MessageEvent.viewPageChage)
                messageEvent.obj = dateStr
                EventBus.getDefault().post(messageEvent)
                //mRopeSkippingPresenter.getDailyBrief(dateStr, TokenUtil.getInstance().getPeopleIdInt(BaseApp.instance))

            })
            tvBackToay!!.setOnClickListener(View.OnClickListener {
                mMenuViewBirth!!.dismiss()
                val dateStr = DateUtil.dataToString(Date(), "yyyy-MM-dd")
                tv_update_time.setText(dateStr)

                val messageEvent = MessageEvent(MessageEvent.viewPageChage)
                messageEvent.obj = dateStr
                EventBus.getDefault().post(messageEvent)


                // mRopeSkippingPresenter.getDailyBrief(dateStr, TokenUtil.getInstance().getPeopleIdInt(BaseApp.instance))
            })
            ivPreDate!!.setOnClickListener(View.OnClickListener { // TODO Auto-generated method stub
                calendarview!!.previousMonth()
                calendarview!!.clearSummary()
                getLastMonthData()
                initDatePopMonthTitle()
            })
            ivNextDate!!.setOnClickListener(View.OnClickListener { // TODO Auto-generated method stub
                calendarview!!.nextMonth()
                calendarview!!.clearSummary()
                getLastMonthData()
                initDatePopMonthTitle()
            })


            //获取当月月初0点时间戳,毫秒值
            val instance = Calendar.getInstance()
            instance[Calendar.DAY_OF_MONTH] = 1
            instance[Calendar.HOUR_OF_DAY] = 0
            instance[Calendar.MINUTE] = 0
            instance[Calendar.SECOND] = 0
            //向前移动一个月
//        getMonthData(instance);//获取当月的数据,主页已经获取
            instance.add(Calendar.MONTH, -1)
            //首次进入获取上一个月的数据,
            getMonthData(instance)
        } catch (e: Exception) {

        }

    }

    private fun getLastMonthData() {
        val calendar = calendarview!!.date
        calendar[Calendar.DAY_OF_MONTH] = 1
        calendar[Calendar.HOUR_OF_DAY] = 0
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        //向前移动一个月
        calendar.add(Calendar.MONTH, -1)
        getMonthData(calendar) //获取上月的数据
        calendar.add(Calendar.MONTH, 1)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun Event(messageEvent: MessageEvent) {

        Logger.myLog("messageEvent.msg" + messageEvent.msg)
        when (messageEvent.msg) {
            MessageEvent.share -> if (userVisibleHint) {
                createShareBean()
            }
            MessageEvent.calender -> if (userVisibleHint) {
                setPopupWindow(activity!!, tv_update_time)
                val time = (System.currentTimeMillis() / 1000).toInt()
                initDatePopMonthTitle()
                calendarview!!.setActiveC(TimeUtil.second2Millis(time))
                calendarview!!.setMonthDataListen(this@RopeDayFragment)
                calendarview!!.setTimeInMillis(TimeUtil.second2Millis(time))
            }
        }
    }

    fun createShareBean() {


        val intent = Intent(activity, NewShareActivity::class.java)
        //        Intent intent = new Intent(context, ShareActivity.class);
        intent.putExtra(JkConfiguration.FROM_TYPE, JkConfiguration.DeviceType.ROPE_SKIPPING)
        val shareBean = ShareBean()
        //目标比例 设置的目标值与当前值的比列
        shareBean.centerValue = tv_rope_count.text.toString()
        shareBean.one = tv_hour.text.toString()
        //里程
        shareBean.two = tv_exeCount.text.toString()
        //消耗
        shareBean.three = tv_cal.text.toString()
        shareBean.time = tv_update_time.text.toString()

        intent.putExtra(JkConfiguration.FROM_BEAN, shareBean)
        startActivity(intent)
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


    private fun getMonthData(instance: Calendar) {

        //mRopeSkippingPresenter.getsportDaysInMonth(TokenUtil.getInstance().getPeopleIdInt(BaseApp.instance), DateUtil.dateFormatyyMM.format(instance.time))
    }

    private val dateFormatMMDD = SimpleDateFormat("yyyy-MM")

    private fun initDatePopMonthTitle() {
        val calendar = calendarview!!.date
        tvDatePopTitle!!.setText(dateFormatMMDD.format(calendar.time))
    }

    override fun getMontData(strYearAndMonth: String?) {
        mRopeSkippingPresenter.getsportDaysInMonth(TokenUtil.getInstance().getPeopleIdInt(BaseApp.instance), strYearAndMonth)
        // TODO("Not yet implemented")
    }

    var maxValue = 0;
    override fun sucessDailyBrief(summary: MutableList<DailyBrief>?) {
        mDataList.clear()
        maxValue = 0;
        summary!!.forEach {
            barInfo = BarInfo()
            barInfo.currentValue = it.skippingNum.toInt()
            if (maxValue < barInfo.currentValue) {
                maxValue = barInfo.currentValue
            }
            barInfo.date = it.startTime
            barInfo.mdDate = it.hhandMin
            mDataList.add(barInfo)

        }
        mDataList.forEach {
            it.maxVlaue = maxValue;
        }

        mDetailBean.clear()
        mDetailBean.addAll(summary)

        mHistoryDayDetailAdapter.notifyDataSetChanged()
        if (mDetailBean.size == 0) {
            mBarAdapter.setEmptyView(R.layout.include_rope_empty_view)
        } else {
            mDataList.get(0).isSelect = true
        }
        mBarAdapter.notifyDataSetChanged()

        /* mDetailBean.add(RopeHistoryBean());
         mDetailBean.add(RopeHistoryBean());
         mDetailBean.add(RopeHistoryBean());
         mDetailBean.add(RopeHistoryBean());
         mDetailBean.add(RopeHistoryBean());*/

        // TODO("Not yet implemented")
    }

    override fun <T : Any?> bindAutoDispose(): AutoDisposeConverter<T> {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider
                .from(this, Lifecycle.Event.ON_DESTROY))
    }

    override fun successgetSummaryData(summary: Summary?) {
        //  TODO("Not yet implemented")
        tv_rope_count.text = summary!!.totalSkippingNum
        tv_exeCount.text = summary!!.totalTimes
        tv_cal.text = summary!!.totalCalories
        tv_hour.text = summary!!.strTime
    }

    override fun sucessDaysInMonth(summary: MutableList<String>?) {
        calendarview!!.setSummary(summary!!)
        //   TODO("Not yet implemented")
    }

    override fun sucessSummaries(summary: MutableList<ResponseDailySummaries>?) {
        //  TODO("Not yet implemented")
    }

    override fun sucessMonthStr(summary: MutableList<HistoryDateBean>?) {
        //TODO("Not yet implemented")
    }

    override fun sucessWeekStr(summary: MutableList<HistoryDateBean>?) {
        // TODO("Not yet implemented")
    }

    override fun onRespondError(message: String?) {
        // TODO("Not yet implemented")
    }


}