package com.isport.brandapp.device.bracelet

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import brandapp.isport.com.basicres.BaseApp
import brandapp.isport.com.basicres.commonalertdialog.AlertDialogStateCallBack
import brandapp.isport.com.basicres.commonalertdialog.PublicAlertDialog
import brandapp.isport.com.basicres.commonutil.UIUtils
import brandapp.isport.com.basicres.commonview.TitleBarView
import brandapp.isport.com.basicres.mvp.BaseMVPTitleActivity
import brandapp.isport.com.basicres.service.observe.NetProgressObservable
import com.isport.blelibrary.ISportAgent
import com.isport.blelibrary.db.table.watch_w516.Watch_W516_SedentaryModel
import com.isport.blelibrary.deviceEntry.impl.BaseDevice
import com.isport.blelibrary.entry.AutoSleep
import com.isport.blelibrary.interfaces.BleReciveListener
import com.isport.blelibrary.observe.BleSettingObservable
import com.isport.blelibrary.observe.bean.ResultBean
import com.isport.blelibrary.result.IResult
import com.isport.blelibrary.result.impl.watch.DeviceGetSuccessResult
import com.isport.blelibrary.utils.BleRequest
import com.isport.blelibrary.utils.Logger
import com.isport.brandapp.AppConfiguration
import com.isport.brandapp.home.bean.http.WatchSleepDayData
import com.isport.brandapp.R
import com.isport.brandapp.banner.recycleView.utils.ToastUtil
import com.isport.brandapp.bean.DeviceBean
import com.isport.brandapp.device.watch.presenter.WatchPresenter
import com.isport.brandapp.device.watch.view.WatchView
import kotlinx.android.synthetic.main.activity_307j_settings_sleep.*
import phone.gym.jkcq.com.commonres.common.JkConfiguration
import java.util.*

public class W307JSleepSetting() : BaseMVPTitleActivity<WatchView, WatchPresenter>(), WatchView {


    private var autoSleep: AutoSleep? = null
    private var isAutoSleep = false
    private var isSleepRemind = false
    private var isSleep = false
    private var isNap = false
    private var isNapRemind = false
    private var sleepStartHour = 0
    private var sleepStartMin = 0
    private var sleepEndHour = 0
    private var sleepEndMin = 0
    private var napStartHour = 0
    private var napStartMin = 0
    private var napEndHour = 0
    private var napEndMin = 0
    private var sleepTargetHour = 0
    private var sleepTargetMin = 0
    private var napRemind = 0
    private var sleepRemins = 0

    private var changeisAutoSleep = false
    private var changeisSleepRemind = false
    private var changeisSleep = false
    private var changeisNap = false
    private var changeisNapRemind = false
    private var changesleepStartHour = 0
    private var changesleepStartMin = 0
    private var changesleepEndHour = 0
    private var changesleepEndMin = 0
    private var changenapStartHour = 0
    private var changenapStartMin = 0
    private var changenapEndHour = 0
    private var changenapEndMin = 0
    private var changesleepTargetHour = 0
    private var changesleepTargetMin = 0
    private var changenapRemind = 0
    private var changesleepRemins = 0

    var press = "press"
    var nor = "nor"

    override fun getLayoutId(): Int = R.layout.activity_307j_settings_sleep

    override fun initEvent() {


        text_sleep_time_begin.setOnClickListener(View.OnClickListener {
            mActPresenter.setPopupWindow(context, text_sleep_time_begin, "3", text_sleep_time_begin.text.toString().toString())
        })
        text_sleep_time_end.setOnClickListener(View.OnClickListener {
            mActPresenter.setPopupWindow(context, text_sleep_time_end, "3", text_sleep_time_end.text.toString().toString())
        })
        text_reminder.setOnClickListener(View.OnClickListener {
            mActPresenter.popWindowSelect(context, text_reminder, JkConfiguration.GymUserInfo.TIME_MIN, sleepRemins.toString(), false)

        })

        switch_sleep.setOnClickListener() {
            var tag = switch_sleep.getTag().toString()
            var isChecked = false;
            if (tag.equals(press)) {
                isChecked = false
                switch_sleep.setTag(nor)
            } else {
                isChecked = true
                switch_sleep.setTag(press)
            }

            if (isChecked) {
                Logger.myLog("switch_sleep" + isChecked)
                scrollView1.visibility = View.VISIBLE
                isAutoSleep = true
            } else {
                Logger.myLog("switch_sleep" + isChecked)
                scrollView1.visibility = View.INVISIBLE
                isAutoSleep = false
            }
            setCheackState(switch_sleep, isChecked)
        }
        switch_sleep_time.setOnClickListener {
            var tag = switch_sleep_time.getTag().toString()
            var isChecked = false;
            if (tag.equals(press)) {
                isChecked = false
                switch_sleep_time.setTag(nor)
            } else {
                isChecked = true
                switch_sleep_time.setTag(press)
            }
            switchSleepTime(switch_sleep_time, isChecked)
        }
        switch_reminder.setOnClickListener {
            var tag = switch_reminder.getTag().toString()
            var isChecked = false;
            if (tag.equals(press)) {
                isChecked = false
                switch_reminder.setTag(nor)
            } else {
                isChecked = true
                switch_reminder.setTag(press)
            }
            switchRemind(switch_reminder, isChecked)
        }



        text_lunch_begin.setOnClickListener(View.OnClickListener {
            mActPresenter.setPopupWindowStartHour(context, text_lunch_begin, "3", text_lunch_begin.text.toString().toString(), 11, 15)
        })
        text_lunch_end.setOnClickListener(View.OnClickListener {
            mActPresenter.setPopupWindowStartHour(context, text_lunch_end, "3", text_lunch_end.text.toString().toString(), 11, 15)

        })
        tv_luncher_reminder.setOnClickListener(View.OnClickListener {
            mActPresenter.popWindowSelect(context, tv_luncher_reminder, JkConfiguration.GymUserInfo.TIME_MIN, napRemind.toString(), false)
        })


        switch_lunch.setOnClickListener {
            var tag = switch_reminder.getTag().toString()
            var isChecked = false;
            if (tag.equals(press)) {
                isChecked = false
                switch_reminder.setTag(nor)
            } else {
                isChecked = true
                switch_reminder.setTag(press)
            }
            switchLunchTime(switch_lunch, isChecked)
        }
        switch_lunch_reminder.setOnClickListener {
            var tag = switch_lunch_reminder.getTag().toString()
            var isChecked = false;
            if (tag.equals(press)) {
                isChecked = false
                switch_lunch_reminder.setTag(nor)
            } else {
                isChecked = true
                switch_lunch_reminder.setTag(press)
            }
            switchLunchRemind(switch_lunch_reminder, isChecked)
        }

        initController()

    }

    fun switchLunchRemind(iv: ImageView, isChecked: Boolean) {
        if (isChecked) {
            tv_luncher_reminder.isEnabled = true
            isNapRemind = true
        } else {
            tv_luncher_reminder.isEnabled = false
            isNapRemind = false
        }
        setCheackState(iv, isChecked)
    }

    fun switchLunchTime(iv: ImageView, isChecked: Boolean) {
        if (isChecked) {
            text_lunch_begin.isEnabled = true
            text_lunch_end.isEnabled = true
            switch_lunch_reminder.visibility = View.VISIBLE
            if (switch_lunch_reminder.getTag().equals(press)) {
                tv_luncher_reminder.isEnabled = true
            }
            isNap = true
        } else {
            text_lunch_begin.isEnabled = false
            text_lunch_end.isEnabled = false
            tv_luncher_reminder.isEnabled = false
            switch_lunch_reminder.visibility = View.GONE
            isNap = false
        }
        setCheackState(iv, isChecked)
    }

    fun switchRemind(iv: ImageView, isChecked: Boolean) {
        if (isChecked) {
            text_reminder.isEnabled = true;
            isSleepRemind = true
        } else {
            text_reminder.isEnabled = false;
            isSleepRemind = false
        }
        setCheackState(iv, isChecked)
    }


    private val mBleReciveListener: BleReciveListener = object : BleReciveListener {
        override fun onConnResult(isConn: Boolean, isConnectByUser: Boolean, baseDevice: BaseDevice) {
            if (isConn) {
            } else {
                NetProgressObservable.getInstance().hide()
            }
        }

        override fun setDataSuccess(s: String) {}
        override fun receiveData(mResult: IResult) {
            if (mResult != null) when (mResult.type) {
                IResult.DEVICE_GET_SETTING -> {

                    val deviceResult = mResult as DeviceGetSuccessResult
                    if (deviceResult.dataType == 1) {
                        if (deviceResult.isSuccess == 0) {
                            initController()
                        }
                    }
                }

                else -> {
                }
            }
        }

        override fun onConnecting(baseDevice: BaseDevice) {}
        override fun onBattreyOrVersion(baseDevice: BaseDevice) {}
    }


    fun switchSleepTime(iv: ImageView, isChecked: Boolean) {
        if (isChecked) {
            switch_reminder.visibility = View.VISIBLE
            text_sleep_time_begin.isEnabled = true;
            text_sleep_time_end.isEnabled = true;
            if (switch_reminder.getTag().equals(press)) {
                text_reminder.isEnabled = true;
            }
            Logger.myLog("switch_sleep_time" + isChecked)

            isSleep = true
        } else {
            switch_reminder.visibility = View.INVISIBLE
            text_sleep_time_begin.isEnabled = false;
            text_sleep_time_end.isEnabled = false;
            text_reminder.isEnabled = false;
            Logger.myLog("switch_sleep_time" + isChecked)
            isSleep = false
        }
        setCheackState(iv, isChecked)
    }

    override fun initView(view: View?) {
        text_sleep_time_begin.text
    }


    fun setRemidValue(textvalue: TextView, value: Int, textUnit: String) {
        textvalue.text = String.format(textUnit, value)
    }


    fun initController() {
        autoSleep = AutoSleep.getInstance(this)
        isAutoSleep = autoSleep!!.isAutoSleep()
        isSleep = autoSleep!!.isSleep()
        isSleepRemind = autoSleep!!.isSleepRemind()
        isNap = autoSleep!!.isNap()
        isNapRemind = autoSleep!!.isNapRemind()
        sleepEndHour = autoSleep!!.getSleepEndHour()
        sleepEndMin = autoSleep!!.getSleepEndMin()
        sleepStartHour = autoSleep!!.getSleepStartHour()
        sleepStartMin = autoSleep!!.getSleepStartMin()
        sleepRemins = autoSleep!!.getSleepRemindTime()
        napEndHour = autoSleep!!.getNapEndHour()
        napEndMin = autoSleep!!.getNapEndMin()
        napStartHour = autoSleep!!.getNapStartHour()
        napStartMin = autoSleep!!.getNapStartMin()
        napRemind = autoSleep!!.getNapRemindTime()
        sleepTargetHour = autoSleep!!.getSleepTargetHour()
        sleepTargetMin = autoSleep!!.getSleepTargetMin()

        changeisAutoSleep = autoSleep!!.isAutoSleep()
        changeisSleep = autoSleep!!.isSleep()
        changeisSleepRemind = autoSleep!!.isSleepRemind()
        changeisNap = autoSleep!!.isNap()
        changeisNapRemind = autoSleep!!.isNapRemind()
        changesleepEndHour = autoSleep!!.getSleepEndHour()
        changesleepEndMin = autoSleep!!.getSleepEndMin()
        changesleepStartHour = autoSleep!!.getSleepStartHour()
        changesleepStartMin = autoSleep!!.getSleepStartMin()
        changesleepRemins = autoSleep!!.getSleepRemindTime()
        changenapEndHour = autoSleep!!.getNapEndHour()
        changenapEndMin = autoSleep!!.getNapEndMin()
        changenapStartHour = autoSleep!!.getNapStartHour()
        changenapStartMin = autoSleep!!.getNapStartMin()
        changenapRemind = autoSleep!!.getNapRemindTime()
        changesleepTargetHour = autoSleep!!.getSleepTargetHour()
        changesleepTargetMin = autoSleep!!.getSleepTargetMin()



        Logger.myLog("initController-------isAutoSleep=" + isAutoSleep + "isSleep=" + isSleep+"sleepStartHour="+sleepStartHour+"sleepEndHour="+sleepEndHour);

        if (isAutoSleep) {
            scrollView1.visibility = View.VISIBLE
        } else {
            scrollView1.visibility = View.INVISIBLE
        }

        setCheackState(switch_sleep, isAutoSleep)
        /*setCheackState(switch_sleep_time, isSleep)
        setCheackState(switch_reminder, isSleepRemind)
        setCheackState(switch_lunch, isNap)
        setCheackState(switch_lunch_reminder, isNapRemind)*/

        switchSleepTime(switch_sleep_time, isSleep)
        switchRemind(switch_reminder, isSleepRemind)
        switchLunchTime(switch_lunch, isNap)
        switchLunchRemind(switch_lunch_reminder, isNapRemind)

        setTimeValue(text_sleep_time_begin, sleepStartHour, sleepStartMin)
        setTimeValue(text_sleep_time_end, sleepEndHour, sleepEndMin)
        setTimeValue(text_lunch_begin, napStartHour, napStartMin)
        setTimeValue(text_lunch_end, napEndHour, napEndMin)
        setRemidValue(text_reminder, sleepRemins, UIUtils.getString(R.string.app_sleep_setting_remind))
        setRemidValue(tv_luncher_reminder, napRemind, UIUtils.getString(R.string.app_sleep_setting_remind))

    }


    fun setCheackState(iv: ImageView, isChecked: Boolean) {
        if (isChecked) {
            iv.setTag(press)
            iv.setImageResource(R.drawable.icon_open)
        } else {
            iv.setTag(nor)
            iv.setImageResource(R.drawable.icon_close)
        }
    }

    fun setTimeValue(textvalue: TextView, hour: Int, min: Int) {

        textvalue.text = String.format("%02d", hour) + ":" + String.format("%02d", min)
    }


    override fun initHeader() {
        titleBarView.setTitle(getString(R.string.watch_sleep_setting_title))
        titleBarView.setRightText(UIUtils.getString(R.string.save))
        titleBarView.setOnTitleBarClickListener(object : TitleBarView.OnTitleBarClickListener() {
            override fun onRightClicked(view: View?) {
                save()
            }

            override fun onLeftClicked(view: View?) {
                isChange()
            }
        })
    }

    override fun initData() {
        ISportAgent.getInstance().registerListener(mBleReciveListener)
        BleSettingObservable.getInstance().addObserver(this)
        if (AppConfiguration.isConnected) {
            ISportAgent.getInstance().requestBle(BleRequest.W307J_SLEEP_GET)
        }
    }


    fun save() {
        if (AppConfiguration.isConnected) {
            autoSleep!!.setAutoSleep(isAutoSleep)
            autoSleep!!.setNaoStartMin(napStartMin)
            autoSleep!!.setNapStartHour(napStartHour)
            autoSleep!!.setNapEndHour(napEndHour)
            autoSleep!!.setNapEndMin(napEndMin)
            autoSleep!!.isNapRemind = isNapRemind
            autoSleep!!.napRemindTime = napRemind
            autoSleep!!.sleepStartHour = sleepStartHour
            autoSleep!!.sleepStartMin = sleepStartMin
            autoSleep!!.sleepEndHour = sleepEndHour
            autoSleep!!.sleepEndMin = sleepEndMin
            autoSleep!!.isNap = isNap
            autoSleep!!.isSleep = isSleep
            autoSleep!!.isSleepRemind = isSleepRemind
            autoSleep!!.isNapRemind = isNapRemind
            autoSleep!!.sleepRemindTime = sleepRemins


            // mainService.setAutoSleep(autoSleep)
            ISportAgent.getInstance().requestBle(BleRequest.W307J_SLEEP_SET, autoSleep);
        } else {
            // UtilTools.showToast(this, R.string.please_bind)
        }
    }

    override fun createPresenter(): WatchPresenter {
        return WatchPresenter(this)
    }

    override fun updateFail() {
    }

    override fun seccessGetDeviceSedentaryReminder(watch_w516_sedentaryModel: Watch_W516_SedentaryModel?) {
    }

    override fun updateWatchHistoryDataSuccess(deviceBean: DeviceBean?) {
    }

    var temphour = 0
    var tempMin = 0


    override fun dataSetSuccess(view: View?, select: String?, data: String?) {
        if (view is TextView) {

            if (AppConfiguration.isConnected) {
                when (view.getId()) {
                    R.id.text_sleep_time_begin -> {
                        var tempTime = data!!.split(":");
                        temphour = Integer.parseInt(tempTime[0]);
                        tempMin = Integer.parseInt(tempTime[1]);

                        var tempValue = (sleepEndHour * 60 + sleepEndMin) - (temphour * 60 + tempMin)
                        if (tempValue < 0) {
                            tempValue = (sleepEndHour * 60 + sleepEndMin + 24 * 60) - (temphour * 60 + tempMin)
                        }

                        if (tempValue >= 10) {
                            sleepStartHour = temphour
                            sleepStartMin = tempMin
                            text_sleep_time_begin.text = data

                        } else {
                            ToastUtil.showTextToast(BaseApp.getApp(), UIUtils.getString(R.string.sleep_setting_tips))
                        }

                        Logger.myLog("(sleepEndHour * 60 + sleepEndMin)=" + (sleepEndHour * 60 + sleepEndMin) + "(temphour * 60 + tempMin)=" + (temphour * 60 + tempMin)+"sleepStartHour="+sleepStartHour+"sleepStartMin="+sleepStartMin)


                    }
                    R.id.text_sleep_time_end -> {
                        var tempTime = data!!.split(":");
                        temphour = Integer.parseInt(tempTime[0]);
                        tempMin = Integer.parseInt(tempTime[1]);


                        var tempValue = ((temphour * 60) + tempMin) - ((sleepStartHour * 60) + sleepStartMin)
                        if (tempValue < 0) {
                            tempValue = ((temphour * 60) + tempMin + (24 * 60))-((sleepStartHour * 60 )+ sleepStartMin)
                        }
                        if (tempValue >= 10) {
                            sleepEndHour = temphour
                            sleepEndMin = tempMin
                            text_sleep_time_end.text = data
                        } else {
                            ToastUtil.showTextToast(BaseApp.getApp(), UIUtils.getString(R.string.sleep_setting_tips))
                        }
                    }
                    R.id.text_reminder -> {
                        val strs = data!!.split(" ".toRegex()).toTypedArray()
                        var min = 15;
                        try {
                            min = strs.get(0).toInt()
                        } catch (e: Exception) {
                        } finally {
                            sleepRemins = min
                            setRemidValue(text_reminder, sleepRemins, UIUtils.getString(R.string.app_sleep_setting_remind))
                        }


                    }
                    R.id.text_lunch_begin -> {
                        var tempTime = data!!.split(":");
                        temphour = Integer.parseInt(tempTime[0]);
                        tempMin = Integer.parseInt(tempTime[1]);
                        var startemHour = (napEndHour * 60 + napEndMin) - (temphour * 60 + tempMin);
                        if (startemHour < 0) {
                            ToastUtil.showTextToast(BaseApp.getApp(), UIUtils.getString(R.string.stableRemind_tips))
                            return
                        }
                        if (startemHour > 10) {
                            napStartHour = temphour
                            napStartMin = tempMin
                            text_lunch_begin.text = data
                        } else {
                            ToastUtil.showTextToast(BaseApp.getApp(), UIUtils.getString(R.string.nap_sleep_setting_tips))
                        }

                    }
                    R.id.text_lunch_end -> {
                        var tempTime = data!!.split(":");
                        temphour = Integer.parseInt(tempTime[0]);
                        tempMin = Integer.parseInt(tempTime[1]);

                        var starttemHour = (temphour * 60 + tempMin) - (napStartHour * 60 + napStartMin);
                        if (starttemHour < 0) {
                            ToastUtil.showTextToast(BaseApp.getApp(), UIUtils.getString(R.string.stableRemind_tips))
                            return
                        }
                        if (starttemHour > 10) {
                            napEndHour = temphour
                            napEndMin = tempMin
                            text_lunch_end.text = data
                        } else {
                            ToastUtil.showTextToast(BaseApp.getApp(), UIUtils.getString(R.string.nap_sleep_setting_tips))
                        }
                    }
                    R.id.tv_luncher_reminder -> {
                        val strs = data!!.split(" ".toRegex()).toTypedArray()
                        var min = 15;
                        try {
                            min = strs.get(0).toInt()
                        } catch (e: Exception) {
                        } finally {
                            napRemind = min
                            setRemidValue(tv_luncher_reminder, napRemind, UIUtils.getString(R.string.app_sleep_setting_remind))
                        }


                    }
                }
            }
        }
    }

    override fun onUnBindSuccess() {
    }

    override fun successDayDate(watchSleepDayData: WatchSleepDayData?) {
    }


    override fun update(o: Observable?, arg: Any?) {
        super.update(o, arg)
        if (o is BleSettingObservable) {
            val result = arg as ResultBean
            if (result.dataType == 1 && result.isSuccess == 0) {
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        BleSettingObservable.getInstance().deleteObserver(this)
        ISportAgent.getInstance().unregisterListener(mBleReciveListener)
    }


    fun isChange() {
        if (
                (isSleep != changeisSleep)
                || (isNapRemind != changeisNapRemind)
                || (isSleepRemind != changeisSleepRemind)
                || (isNap != changeisNap)
                || (isAutoSleep != changeisAutoSleep)
                || (sleepStartHour != changesleepStartHour)
                || (sleepStartMin != changesleepStartMin)
                || (sleepEndHour != changesleepEndHour)
                || (sleepEndMin != changesleepEndMin)
                || (napStartHour != changenapStartHour)
                || (napStartMin != changenapStartMin)
                || (napEndHour != changenapEndHour)
                || (napEndMin != changenapEndMin)
        ) {
            PublicAlertDialog.getInstance().showDialog("", context.resources.getString(R.string.not_save_alert), context, resources.getString(R.string.common_dialog_cancel), resources.getString(R.string.common_dialog_ok), object : AlertDialogStateCallBack {
                override fun determine() {
                    save()
                }

                override fun cancel() {
                    finish()
                }
            }, false)

        } else {
            finish()
        }
    }

    override fun onBackPressed() {
        isChange()
    }

}
