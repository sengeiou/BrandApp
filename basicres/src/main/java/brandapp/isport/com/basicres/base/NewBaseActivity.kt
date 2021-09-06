package brandapp.isport.com.basicres.base

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import brandapp.isport.com.basicres.BaseApp
import brandapp.isport.com.basicres.commonutil.TokenUtil
import java.util.*

open class NewBaseActivity : AppCompatActivity() {

    private  val mBaseHandler=Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        //android 10之后不允许后台访问剪切板，延迟一点时间，界面出来了就可以访问了
        mBaseHandler.postDelayed({ setClipBoard()},100)
    }

    /**
     * 设置剪贴板
     */
    open fun setClipBoard() {
      /*  val ClipBoardText = ClipBoardUtil.getClipBoardText(this)
        Log.e("basebase","ClipBoardText="+ClipBoardText)
        if (!TextUtils.isEmpty(ClipBoardText)) {
            ClipBoardUtil.clearClipboard()
            getGroupBuyCommand(ClipBoardText)
        }*/
    }

    /**
     * 获取拼团口令
     */
    private fun getGroupBuyCommand(command: String) {

        val params = HashMap<String, String>()
        params["userId"] = TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp())
        params["command"] = command
//        params["lat"]="0"
//        params["lng"]="0"
       /* RetrofitHelper.getService().postGroupBuyContentByCommand(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { groupBuyCommandInfoBaseResponse ->
                    if (groupBuyCommandInfoBaseResponse.obj != null) {
                        DialogFactory.getInstance().showBuyTogetherDialog(this@NewBaseActivity, groupBuyCommandInfoBaseResponse.obj)
                    } else {
//                        com.blankj.utilcode.util.ToastUtils.showShort(groupBuyCommandInfoBaseResponse.message)
                    }
                }

*/
    }
}
