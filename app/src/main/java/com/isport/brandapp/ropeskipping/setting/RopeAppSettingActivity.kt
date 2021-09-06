package com.isport.brandapp.ropeskipping.setting

import android.os.Handler
import android.view.View
import bike.gymproject.viewlibray.ItemDeviceSettingView
import brandapp.isport.com.basicres.BaseApp
import brandapp.isport.com.basicres.BaseTitleActivity
import brandapp.isport.com.basicres.commonutil.TokenUtil
import brandapp.isport.com.basicres.commonutil.ViewMultiClickUtil
import brandapp.isport.com.basicres.commonview.TitleBarView
import brandapp.isport.com.basicres.net.userNet.CommonUserAcacheUtil
import com.isport.blelibrary.ISportAgent
import com.isport.blelibrary.utils.Logger
import com.isport.brandapp.AppConfiguration
import com.isport.brandapp.R
import com.isport.brandapp.ropeskipping.realsport.dialog.SelectPopupWindow
import com.isport.brandapp.ropeskipping.util.Preference
import com.isport.brandapp.sport.bean.SportSettingBean
import com.isport.brandapp.sport.service.Seekbars
import com.isport.brandapp.wu.util.HeartRateConvertUtils
import kotlinx.android.synthetic.main.app_activity_rope_app_setting.*
import phone.gym.jkcq.com.commonres.commonutil.UserUtils

internal class RopeAppSettingActivity() : BaseTitleActivity(), ItemDeviceSettingView.OnItemViewCheckedChangeListener, Seekbars.OnListenCurrentValue {

    var ropeCountOpen: Boolean by Preference(Preference.ROPE_COUNT_OPEN, false)
    var ropeCount: Int by Preference(Preference.ROPE_COUNT, 50)
    var ropetimeOpen: Boolean by Preference(Preference.ROPE_TIME_OPEN, true)
    var ropetime: Int by Preference(Preference.ROPE_TIME, 20)
    var ropeHrvalue: Int by Preference(Preference.ROPE_Hr_Count, 0)
    var ropeHrOpen: Boolean by Preference(Preference.ROPE_Hr_OPEN, true)
    var musicSwitch: Boolean by Preference(Preference.MUSIC_SWITCH, true)

    override fun initData() {


        item_music_switch.setCheckBox(musicSwitch)
        item_count.setCheckBox(ropeCountOpen)
        item_times.setCheckBox(ropetimeOpen)
        item_hr_switch.setCheckBox(ropeHrOpen)
        if (ropeHrOpen) {
            view_enable.visibility = View.GONE
        } else {
            view_enable.visibility = View.VISIBLE
        }
        setCountValue()
        setTimeValue()


        var sportDetail = SportSettingBean()


        seekbars_hr.setSettingBean(sportDetail, 1)

        var userInfoBean = CommonUserAcacheUtil.getUserInfo(TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()))

        age = UserUtils.getAge(userInfoBean.birthday)
        sex = userInfoBean.gender


        var maxHr = HeartRateConvertUtils.getMaxHeartRate(age, sex)

        if (ropeHrvalue == 0) {
            ropeHrvalue = (0.75 * maxHr).toInt()
        }
        sportDetail.currentHrValue = ropeHrvalue
        sportDetail.hrMaxValue = 250
        sportDetail.hrMinValue = (maxHr * 0.70f).toInt()
        if (sportDetail.currentHrValue < sportDetail.hrMinValue) {
            ropeHrvalue = (0.75 * maxHr).toInt()
            sportDetail.currentHrValue = ropeHrvalue
        }

        //sportDetail.hrMinValue = 30
        seekbars_hr.setCurrentValue(this@RopeAppSettingActivity)
        seekbars_hr.setTips(String.format(this.getString(R.string.remind_hr_rope), (maxHr * 0.7f).toInt(), (maxHr * 0.8f).toInt()))


       // tv_hr_remind_zone.setText(String.format(this.getString(R.string.remind_hr_rope), (maxHr * 0.7f).toInt(), (maxHr * 0.8f).toInt()))

        var hrprgess = 0


        Logger.myLog("hrprgess sportDetail.currentHrValue" + sportDetail.currentHrValue + "sportDetail.hrMaxValue =" + sportDetail.hrMaxValue + "sportDetail.hrMinValue=" + sportDetail.hrMinValue)

        handler.postDelayed({
            hrprgess = ((sportDetail.currentHrValue - sportDetail.hrMinValue) / ((sportDetail.hrMaxValue - sportDetail.hrMinValue) / 100.0f)).toInt()
            if (hrprgess == 0) {
                hrprgess = 1
            }
            seekbars_hr.setProgess(hrprgess)
            Logger.myLog("hrprgess" + hrprgess)
        }, 100)




        mSelectPopupWindow = SelectPopupWindow(object : SelectPopupWindow.OnSelectPopupListener {
            override fun onSelect(type: String, data: String) {
                when (type) {
                    SelectPopupWindow.ROPE_COUNT_RE -> {


                        ropeCount = data.toInt()
                        Logger.myLog("ROPE_COUNT_RE=" + data)
                        setTimeValue()
                        //ropeCount = data.toInt()

                        // itemview_birthday.setRightText(data)
                    }
                    SelectPopupWindow.ROPE_TIME_RE -> {
                        // targetBean.targetCount=

                        ropetime = data.toInt()

                        Logger.myLog("ROPE_TIME_RE=" + data)
                        setCountValue()

                        //ropetime = data.toInt()

                        // mHeight = data
                        // itemview_height.setRightText(data + "cm")
                    }
                }
            }

        })
    }

    fun setTimeValue() {
        item_count_value.setTitleText(String.format(this@RopeAppSettingActivity.getString(R.string.rope_setting_count_unit), ropeCount))

    }

    fun setCountValue() {
        item_time_value.setTitleText(String.format(this@RopeAppSettingActivity.getString(R.string.rope_setting_time_unit), ropetime))

    }

/* var mDataList = mutableListOf<MessageInfo>()
 lateinit var mMessageAdapter: MessageAdapter
 var mCurrentMessage: MessageInfo? = null
 var mCurrentPosition = 0
 var currentPageNumber = 1*/


    override fun getLayoutId(): Int = R.layout.app_activity_rope_app_setting

    var mSelectPopupWindow: SelectPopupWindow? = null
    override fun initEvent() {
        item_music_value.setTitleText("music")
        item_music_value.setOnContentClickListener(ItemDeviceSettingView.OnContentClickListener {
        })
        item_time_value.setOnContentClickListener(ItemDeviceSettingView.OnContentClickListener {
            if (ropetimeOpen) {
                if (!ViewMultiClickUtil.onMultiClick()) {
                    mSelectPopupWindow?.popWindowSelect(
                            this@RopeAppSettingActivity,
                            item_time_value,
                            SelectPopupWindow.ROPE_TIME_RE,
                            ropetime.toString(), false
                    )
                }
            }
        })

        item_count_value.setOnContentClickListener(ItemDeviceSettingView.OnContentClickListener {

            if (ropeCountOpen) {
                if (!ViewMultiClickUtil.onMultiClick()) {
                    mSelectPopupWindow?.popWindowSelect(
                            this@RopeAppSettingActivity,
                            item_count_value,
                            SelectPopupWindow.ROPE_COUNT_RE,
                            ropeCount.toString(), false
                    )
                }
            }
        })


        item_music_switch.setOnCheckedChangeListener(this)
        item_count.setOnCheckedChangeListener(this)
        item_hr_switch.setOnCheckedChangeListener(this)
        item_times.setOnCheckedChangeListener(this)

    }

    private var age = 0
    private var sex: String? = null

    override fun initView(view: View?) {
        view_enable.setOnClickListener(View.OnClickListener {

        })

    }


    var handler = Handler()


    override fun initHeader() {
        titleBarView.setTitle(getString(R.string.set))
        titleBarView.setOnTitleBarClickListener(object : TitleBarView.OnTitleBarClickListener() {
            override fun onRightClicked(view: View?) {
            }

            override fun onLeftClicked(view: View?) {

                sendHrValue()
                finish()

            }
        })
    }

    fun sendHrValue() {
        if (AppConfiguration.isConnected) {
            if (ropeHrOpen) {
                ISportAgent.getInstance().requestBle(ropeHrvalue)
            } else {
                ISportAgent.getInstance().requestBle(251)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        sendHrValue()
    }

    override fun onCheckedChanged(id: Int, isChecked: Boolean) {
        when (id) {
            R.id.item_music_switch -> {
                musicSwitch = isChecked
            }
            R.id.item_times -> {
                if (isChecked) {
                    item_count.setCheckBox(false)
                    ropeCountOpen = false
                }
                ropetimeOpen = isChecked

            }
            R.id.item_count -> {

                if (isChecked) {
                    item_times.setCheckBox(false)
                    ropetimeOpen = false
                }
                ropeCountOpen = isChecked

            }
            R.id.item_hr_switch -> {
                if (isChecked) {
                    view_enable.visibility = View.GONE
                } else {
                    view_enable.visibility = View.VISIBLE
                }
                ropeHrOpen = isChecked
            }
        }
    }

    override fun backeCurrentValue(value: Int?, currenttype: Int) {
        //TODO("Not yet implemented")
        if (value != null) {
            ropeHrvalue = value
        }
    }


}
