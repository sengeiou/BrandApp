package com.isport.brandapp.device.scale

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.view.View
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import brandapp.isport.com.basicres.BaseApp
import brandapp.isport.com.basicres.BaseTitleActivity
import brandapp.isport.com.basicres.commonalertdialog.AlertDialogStateCallBack
import brandapp.isport.com.basicres.commonalertdialog.PublicAlertDialog
import brandapp.isport.com.basicres.commonutil.AppUtil
import brandapp.isport.com.basicres.commonutil.Logger
import brandapp.isport.com.basicres.commonutil.TokenUtil
import brandapp.isport.com.basicres.commonview.TitleBarView
import com.isport.blelibrary.ISportAgent
import com.isport.blelibrary.utils.TimeUtils
import com.isport.brandapp.AppConfiguration
import com.isport.brandapp.R
import com.isport.brandapp.device.scale.adpter.ScaleHistoryFragmentAdapter
import com.isport.brandapp.device.scale.bean.ScaleHistoryBean
import com.isport.brandapp.device.scale.bean.ScaleHistroyNList
import com.isport.brandapp.device.scale.presenter.ScaleHistoryPresenter
import com.isport.brandapp.device.scale.view.ScaleHistoryView
import com.isport.brandapp.util.ActivitySwitcher
import com.isport.brandapp.util.AppSP
import kotlinx.android.synthetic.main.activity_scale_main.*
import kotlinx.android.synthetic.main.app_fragment_data_device_no_data_item.*
import phone.gym.jkcq.com.commonres.common.JkConfiguration
import java.util.*

internal class ScaleMainActivity() : BaseTitleActivity(), ScaleHistoryView {

    val scaleHistoryPresenter: ScaleHistoryPresenter by lazy { ScaleHistoryPresenter(this) }
    lateinit var mMessageAdapter: ScaleHistoryFragmentAdapter
    override fun getLayoutId(): Int = R.layout.activity_scale_main
    var pageSize = 0
    var mNextTimeTamp: Long = 1

    var isFresh = true


    override fun initEvent() {


        btn_measure.setOnClickListener(View.OnClickListener {
            onScaleStateListenter()
        })

        viewpage2.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
//                Logger.e(TAG, "position=" + position + "  positionOffset=" + positionOffset + "  positionOffsetPixels=" + positionOffsetPixels);
            }

            override fun onPageSelected(position: Int) {

                try {
                    tv_time.text = TimeUtils.getTimeByyyyyMM(ScaleCommon.list.get(position))
                } catch (e: Exception) {

                }

            }

            override fun onPageScrollStateChanged(state: Int) {
                Logger.e(TAG, "state=$state")
            }
        })

    }

    override fun initView(view: View?) {

        ScaleCommon.list.clear()
        ScaleCommon.listHashMap.clear()
        titleBarView.setRightIcon(R.drawable.icon_sport_history)
        ScaleCommon.list.add(1)
        mNextTimeTamp = ScaleCommon.list.get(0)

        /*  scaleHistoryPresenter.getScaleHomeFirstHistoryModel(mNextTimeTamp, RequestCode.Request_getScaleHistoryData, Calendar
                  .getInstance().timeInMillis, mDeviceId, currentMonth, "1", "", "", pageSize.toString() + "", "")*/

    }


    fun setTimeValue(value: String) {
        tv_time.text = value
    }

    override fun initHeader() {
        titleBarView.setTitle(getString(R.string.body_fat_scale))
        titleBarView.setOnTitleBarClickListener(object : TitleBarView.OnTitleBarClickListener() {
            override fun onRightClicked(view: View?) {
                val historyIntent = Intent(this@ScaleMainActivity, ActivityScaleHistory::class.java)
                historyIntent.putExtra("mDeviceId", AppConfiguration.deviceMainBeanList.get(JkConfiguration.DeviceType.BODYFAT)?.deviceID)
                startActivity(historyIntent)

            }

            override fun onLeftClicked(view: View?) {
                finish()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        if (isFresh) {
            scaleHistoryPresenter.getMothListData(TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()))
        }
    }

    override fun initData() {

    }

    override fun successRefresh(historyBean: ArrayList<ScaleHistoryBean>?, isLastData: Boolean, lastMonthStr: String?, nextTimeTamp: Long) {
        //TODO("Not yet implemented")
    }

    override fun successMainScale(historyBean: ScaleHistroyNList?) {

        /*if (historyBean?.time != 0L) {
            ScaleCommon.list.add(historyBean?.time)
        }
        if (historyBean?.list != null && historyBean?.list.size > 0) {
            ScaleCommon.listHashMap.put(mNextTimeTamp, historyBean)

        } else {
        }*/

        //TODO("Not yet implemented")
    }

    override fun successLoadMone(historyBean: ArrayList<ScaleHistoryBean>?, isLastData: Boolean, lastMonthStr: String?, nextTimeTamp: Long) {
        //TODO("Not yet implemented")
    }

    override fun successMothList(list: MutableList<Long>?) {
        ScaleCommon.list.clear();
        list?.forEach {
            ScaleCommon.list.add(it)
        }
        if (ScaleCommon.list.size > 0) {
            titleBarView.setRightIconEnable(true)
            layout_no_data.visibility = View.GONE
            initViewPage()
        } else {
            layout_no_data.visibility = View.VISIBLE
            titleBarView.setRightIconEnable(false)
            if (AppUtil.isZh(BaseApp.getApp())) {
                //LoadImageUtil.getInstance().load(context,R.drawable.bg_scale_emty_zh,iv_empty,0);
                iv_empty.setImageResource(R.drawable.bg_scale_emty_zh)
            } else {
                //LoadImageUtil.getInstance().load(context,R.drawable.bg_scale_emty_en,iv_empty,0);
                iv_empty.setImageResource(R.drawable.bg_scale_emty_en)
            }
        }
        // TODO("Not yet implemented")
    }

    override fun onRespondError(message: String?) {
        //TODO("Not yet implemented")
    }

    fun initViewPage() {
        mMessageAdapter = ScaleHistoryFragmentAdapter(this)
        viewpage2.adapter = mMessageAdapter
        viewpage2.setCurrentItem(ScaleCommon.list.size, false)

        viewpage2.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                if (position == 0) {
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                Logger.e(TAG, "state=$state")
            }
        })

    }

    fun onScaleStateListenter() {

        //连接状态，并且连的是体脂秤就直接跳转，否则就进行断开，再扫描体脂称连接
        if (AppConfiguration.isConnected && ISportAgent.getInstance().currnetDevice != null && ISportAgent.getInstance().currnetDevice.deviceType == JkConfiguration.DeviceType.BODYFAT) {
            //是连接状态,点击的不是当前连接设备，那么就去切换连接,点击的是当前，那么进页面检测
            val currentDeviceType = AppSP.getInt(context, AppSP.DEVICE_CURRENTDEVICETYPE, JkConfiguration.DeviceType.WATCH_W516)
            //去心率检测页面
            ActivitySwitcher.goScaleRealTimeAct(context, false)
        } else {
            //是未连接状态
            ISportAgent.getInstance().disConDevice(false)
            connectScale()
        }
    }

    /**
     * 直连体脂称
     */
    private fun connectScale() {
        //需要把list更为Map,key为deviceType
        if (!AppUtil.isOpenBle()) {
            openBlueDialog()
            return
        }
        //如果当前的设备连接的是体脂秤，就不需要去断开连接，否则都去调用一次断开连接
        if (AppConfiguration.isConnected) {
            if (ISportAgent.getInstance().currnetDevice != null && ISportAgent.getInstance().currnetDevice.deviceType == JkConfiguration.DeviceType.BODYFAT) {
            } else {
                ISportAgent.getInstance().disConDevice(false)
            }
        } else {
            //可能手表在重连中
            ISportAgent.getInstance().disConDevice(false)
        }
        AppSP.putInt(context, AppSP.DEVICE_CURRENTDEVICETYPE, JkConfiguration.DeviceType.BODYFAT)
        ActivitySwitcher.goScaleRealTimeAct(context, false)
    }

    fun openBlueDialog() {
        PublicAlertDialog.getInstance().showDialog("", context.resources.getString(R.string.bonlala_open_blue), context, resources.getString(R.string.app_bluetoothadapter_turnoff), resources.getString(R.string.app_bluetoothadapter_turnon), object : AlertDialogStateCallBack {
            override fun determine() {
//                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                    startActivity(intent);
                val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                bluetoothAdapter?.enable()
            }

            override fun cancel() {}
        }, false)
    }

}
