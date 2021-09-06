package phone.gym.jkcq.com.socialmodule.manager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.devilsen.czxing.Scanner;
import me.devilsen.czxing.code.BarcodeFormat;
import me.devilsen.czxing.code.BarcodeWriter;
import me.devilsen.czxing.util.BarCodeUtil;
import me.devilsen.czxing.view.ScanActivityDelegate;
import phone.gym.jkcq.com.socialmodule.FriendConstant;
import phone.gym.jkcq.com.socialmodule.R;

public class QRCodeManager {
    private BarcodeWriter mWriter;
    private Context mContext;
    private OnScanResultListener mOnScanResultListener;

    public QRCodeManager(Context context) {
        this.mContext = context;
        mWriter = new BarcodeWriter();
    }

    public void setOnScanResultListener(OnScanResultListener scanResultListener) {
        this.mOnScanResultListener = scanResultListener;
    }

    /**
     * 二维码扫描
     *
     * @param activity
     */
    public void scanQRCode(Activity activity) {
        Scanner.with(activity)
                .setTitle(activity.getString(R.string.friend_scan))
                .setScanNoticeText(activity.getString(R.string.friend_input_qrcode_to_frame))
                .showAlbum(false)
                .setFlashLightOnText(activity.getString(R.string.friend_open_light))
                .setFlashLightOffText(activity.getString(R.string.friend_close_light))
//                .setFlashLightInvisible()
                .setFlashLightOnDrawable(R.drawable.ic_highlight_blue_open_24dp)
                .setFlashLightOffDrawable(R.drawable.ic_highlight_white_close_24dp)
                .setOnScanResultDelegate(new ScanActivityDelegate.OnScanDelegate() {
                    @Override
                    public void onScanResult(final Activity activity, final String result, BarcodeFormat format) {
                        // 如果有回调，则必然有值,因为要避免AndroidX和support包的差异，所以没有默认的注解
//                        Intent intent = new Intent(MainActivity.this, DelegateActivity.class);
//                        intent.putExtra("result", result);
//                        startActivity(intent);
                        final String showContent = "format: " + format.name() + "  code: " + result;
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                int size = result.length();
                                if (result.contains(FriendConstant.QR_HEAD)) {
                                    if (mOnScanResultListener != null) {

                                        mOnScanResultListener.onScanResult(result.substring(8));
                                    }

                                } else if (result.contains("http")) {

                                } else {
                                    Toast.makeText(activity, "QRCode=" + showContent, Toast.LENGTH_LONG).show();

                                }

                            }
                        });
                    }
                })
                .start();
    }

    /**
     * 生成二维码
     *
     * @param qrString
     * @param iv_qrcode
     */
    public void writeQRCode(String qrString, ImageView iv_qrcode) {

        Observable.create(new ObservableOnSubscribe<Bitmap>() {
            @Override
            public void subscribe(ObservableEmitter<Bitmap> emitter) {
                Bitmap bitmap = mWriter.write(qrString,
                        BarCodeUtil.dp2px(mContext, 196),
                        Color.BLACK);
                if (bitmap != null) {
                    emitter.onNext(bitmap);
                }
                emitter.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bitmap>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        iv_qrcode.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Write", "生成失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    /**
     * 生成二维码
     *
     * @param content
     * @param width
     * @param height
     * @param character_set
     * @param error_correction_level
     * @param margin
     * @param color_black
     * @param color_white
     * @return
     */
    public static Bitmap createQRCodeBitmap(String content, int width, int height,
                                            String character_set, String error_correction_level,
                                            String margin, int color_black, int color_white) {
        // 字符串内容判空
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        // 宽和高>=0
        if (width < 0 || height < 0) {
            return null;
        }
        try {
            /** 1.设置二维码相关配置 */
            Hashtable<EncodeHintType, String> hints = new Hashtable<>();
            // 字符转码格式设置
            if (!TextUtils.isEmpty(character_set)) {
                hints.put(EncodeHintType.CHARACTER_SET, character_set);
            }
            // 容错率设置
            if (!TextUtils.isEmpty(error_correction_level)) {
                hints.put(EncodeHintType.ERROR_CORRECTION, error_correction_level);
            }
            // 空白边距设置
            if (!TextUtils.isEmpty(margin)) {
                hints.put(EncodeHintType.MARGIN, margin);
            }
            /** 2.将配置参数传入到QRCodeWriter的encode方法生成BitMatrix(位矩阵)对象 */
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, com.google.zxing.BarcodeFormat.QR_CODE, width, height, hints);

            /** 3.创建像素数组,并根据BitMatrix(位矩阵)对象为数组元素赋颜色值 */
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    //bitMatrix.get(x,y)方法返回true是黑色色块，false是白色色块
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = color_black;//黑色色块像素设置
                    } else {
                        pixels[y * width + x] = color_white;// 白色色块像素设置
                    }
                }
            }
            /** 4.创建Bitmap对象,根据像素数组设置Bitmap每个像素点的颜色值,并返回Bitmap对象 */
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 生成二维码
     *
     * @param content
     * @param width
     * @param height
     * @return
     */
    public static Bitmap createQRCodeBitmap(String content, int width, int height) {
        String character_set = "UTF-8";
        String error_correction_level = null;
        String margin = "0";
        int color_black = Color.BLACK;
        int color_white = Color.WHITE;
        // 字符串内容判空
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        // 宽和高>=0
        if (width < 0 || height < 0) {
            return null;
        }
        try {
            /** 1.设置二维码相关配置 */
            Hashtable<EncodeHintType, String> hints = new Hashtable<>();
            // 字符转码格式设置
            if (!TextUtils.isEmpty(character_set)) {
                hints.put(EncodeHintType.CHARACTER_SET, character_set);
            }
            // 容错率设置
            if (!TextUtils.isEmpty(error_correction_level)) {
                hints.put(EncodeHintType.ERROR_CORRECTION, error_correction_level);
            }
            // 空白边距设置
            if (!TextUtils.isEmpty(margin)) {
                hints.put(EncodeHintType.MARGIN, margin);
            }
            /** 2.将配置参数传入到QRCodeWriter的encode方法生成BitMatrix(位矩阵)对象 */
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, com.google.zxing.BarcodeFormat.QR_CODE, width, height, hints);

            /** 3.创建像素数组,并根据BitMatrix(位矩阵)对象为数组元素赋颜色值 */
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    //bitMatrix.get(x,y)方法返回true是黑色色块，false是白色色块
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = color_black;//黑色色块像素设置
                    } else {
                        pixels[y * width + x] = color_white;// 白色色块像素设置
                    }
                }
            }
            /** 4.创建Bitmap对象,根据像素数组设置Bitmap每个像素点的颜色值,并返回Bitmap对象 */
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    public interface OnScanResultListener {
        void onScanResult(String result);
    }
}
