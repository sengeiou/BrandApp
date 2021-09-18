package com.isport.brandapp.device.scale.fragment

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.isport.blelibrary.utils.CommonDateUtil
import com.isport.blelibrary.utils.TimeUtils
import com.isport.brandapp.home.customview.BezierView
import com.isport.brandapp.R
import com.isport.brandapp.arithmetic.WeightStandardImpl
import com.isport.brandapp.device.scale.ActivityScaleReport
import com.isport.brandapp.device.scale.ScaleCommon
import com.isport.brandapp.device.scale.bean.ScaleCharBean
import com.isport.brandapp.device.scale.bean.ScaleHistoryBean
import com.isport.brandapp.device.scale.bean.ScaleHistoryNBean
import com.isport.brandapp.device.scale.bean.ScaleHistroyNList
import com.isport.brandapp.device.scale.presenter.ScaleHistoryPresenter
import com.isport.brandapp.device.scale.view.ScaleHistoryView
import com.isport.brandapp.util.BmiUtil
import com.isport.brandapp.util.RequestCode
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.AutoDisposeConverter
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import kotlinx.android.synthetic.main.app_fragment_data_device_scale_item.*
import java.util.*

internal class ScaleMonthHistoryFragment() : Fragment(), ScaleHistoryView {

    var currentMonth = "";
    var POSITION = "positon"
    var param1 = ""
    var currentPositon = -1

    var mDataList = mutableListOf<ScaleHistoryNBean>()

    var pointList = mutableListOf<ScaleCharBean>()
    val scaleHistoryPresenter: ScaleHistoryPresenter by lazy { ScaleHistoryPresenter(this) }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.app_fragment_data_device_scale_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initdata()
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyFragment.
         */
        @JvmStatic
        fun newInstance(param1: Long) =
                ScaleMonthHistoryFragment().apply {
                    arguments = Bundle().apply {
                        putLong(POSITION, param1)
                    }
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mNextTimeTamp = it.getLong(POSITION)
        }
    }

    var pageSize = 0
    var mNextTimeTamp: Long = 1
    var mDeviceId = "";
    fun initdata() {
        scaleHistoryPresenter.getScaleHomeFirstHistoryModel(mNextTimeTamp, RequestCode.Request_getScaleHistoryData, Calendar
                .getInstance().timeInMillis, mDeviceId, currentMonth, "1", "", "", pageSize.toString() + "", "")





        bezier.setOnItemBarClickListener(BezierView.OnItemBarClickListener { position ->
            if (mDataList != null && position >= 0 && mDataList.size > position) {

                currentPositon = position
                val scacleBean: ScaleHistoryNBean = mDataList.get(position)
                tv_bfp.text = "" + scacleBean.bfpBodyFatPercent
                item_screen_time.setContentText(TimeUtils.getTimeByyyyyMMddhhmmss(scacleBean.timestamp.toLong()))
                // tv_sport_time.text = DateUtil.dataToString(Date(scacleBean.timestamp), "yyyy-MM-dd HH:mm:ss")
                //tv_sport_time.text = scacleBean.dateStr
                tv_weight_value.text = CommonDateUtil.formatOnePoint(scacleBean.bodyWeight.toDouble())
                tv_bmi_value.text = CommonDateUtil.formatTwoPoint(scacleBean.bmi)
                // tv_bmi_standard.setText(Scale);
                tv_bmi_standard.visibility = View.VISIBLE
                val weightStandard = WeightStandardImpl()
                val result: ArrayList<String> = weightStandard.bmiStandardWithValue(scacleBean.bmi)
                var strStander = result.get(0)
                // tv_bmi_standard.setBackgroundColor(result.get(1))
                if (TextUtils.isEmpty(strStander)) {
                    strStander = ""
                }
                tv_bmi_standard.text = ":" + strStander

                calBmiStandard(scacleBean.bmi)
            }
        })

        item_screen_time.setOnClickListener {

            if (currentPositon >= 0 && currentPositon < mDataList.size) {

                val intent = Intent(activity, ActivityScaleReport::class.java)
                //                    App.isHttp() ? Long.parseLong(beanList.scaleDayBean.fatSteelyardId) :
                val mMFatsteelyard: Long = mDataList[currentPositon].timestamp.toLong()
                intent.putExtra("fatSteelyardId", mMFatsteelyard)
                startActivity(intent)


            }
        }
    }

    fun calBmiStandard(bmi: Double) {
        val color = BmiUtil.getBmiCorrespondingColor(bmi.toFloat())
        if (tv_bmi_standard != null) {
            tv_bmi_standard.setTextColor(color)
            tv_bmi_value.setTextColor(color)
            tv_weight_value.setTextColor(color)
            tv_bmi_unitl.setTextColor(color)
        }
    }

    override fun <T : Any?> bindAutoDispose(): AutoDisposeConverter<T> {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider
                .from(this, Lifecycle.Event.ON_DESTROY))
    }

    override fun successRefresh(historyBean: ArrayList<ScaleHistoryBean>?, isLastData: Boolean, lastMonthStr: String?, nextTimeTamp: Long) {
        // TODO("Not yet implemented")
    }

    override fun successMainScale(historyBean: ScaleHistroyNList?) {
        // TODO("Not yet implemented")
        ScaleCommon.listHashMap.put(mNextTimeTamp, historyBean)
        var bean: ScaleCharBean
        if (historyBean?.list != null && historyBean?.list.size > 0) {
            historyBean.list.forEach {
                bean = ScaleCharBean()
                bean.starDate = TimeUtils.getTimeByyyyyMMddhhmmss(it.timestamp.toLong())
                bean.weight = it.bodyWeight.toFloat()
                pointList.add(bean)
            }
            mDataList.addAll(historyBean.list)
            bezier.setPointListFloat(pointList)
        } else {
        }

    }

    override fun successLoadMone(historyBean: ArrayList<ScaleHistoryBean>?, isLastData: Boolean, lastMonthStr: String?, nextTimeTamp: Long) {


        //TODO("Not yet implemented")
    }

    override fun successMothList(list: MutableList<Long>?) {
        // TODO("Not yet implemented")
    }

    override fun onRespondError(message: String?) {
        // TODO("Not yet implemented")
    }


}
