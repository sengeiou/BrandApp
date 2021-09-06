package phone.gym.jkcq.com.socialmodule.fragment;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import okio.BufferedSource;
import okio.Okio;
import okio.Sink;

/**
 * @anthor:njb
 * @date: 2020-04-21 04:36
 * @desc: 文件保存工具类
 **/
public class ImageVideoFileUtils {
    /**
     * 保存图片
     * @param context
     * @param file
     */
    public static void saveImage(Context context, File file) {
        ContentResolver localContentResolver = context.getContentResolver();
        ContentValues localContentValues = getImageContentValues(context, file, System.currentTimeMillis());
        localContentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, localContentValues);

        Intent localIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        final Uri localUri = Uri.fromFile(file);
        localIntent.setData(localUri);
        context.sendBroadcast(localIntent);
    }

    public static ContentValues getImageContentValues(Context paramContext, File paramFile, long paramLong) {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("title", paramFile.getName());
        localContentValues.put("_display_name", paramFile.getName());
        localContentValues.put("mime_type", "image/jpeg");
        localContentValues.put("datetaken", Long.valueOf(paramLong));
        localContentValues.put("date_modified", Long.valueOf(paramLong));
        localContentValues.put("date_added", Long.valueOf(paramLong));
        localContentValues.put("orientation", Integer.valueOf(0));
        localContentValues.put("_data", paramFile.getAbsolutePath());
        localContentValues.put("_size", Long.valueOf(paramFile.length()));
        return localContentValues;
    }


    /**
     * 保存视频
     * @param context
     * @param file
     */
    public static void saveVideo(Context context, File file) {
        //是否添加到相册
        ContentResolver localContentResolver = context.getContentResolver();
        ContentValues localContentValues = getVideoContentValues(context, file, System.currentTimeMillis());
        Uri localUri = localContentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, localContentValues);
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri));
    }

    public static ContentValues getVideoContentValues(Context paramContext, File paramFile, long paramLong) {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("title", paramFile.getName());
        localContentValues.put("_display_name", paramFile.getName());
        localContentValues.put("mime_type", "video/mp4");
        localContentValues.put("datetaken", Long.valueOf(paramLong));
        localContentValues.put("date_modified", Long.valueOf(paramLong));
        localContentValues.put("date_added", Long.valueOf(paramLong));
        localContentValues.put("_data", paramFile.getAbsolutePath());
        localContentValues.put("_size", Long.valueOf(paramFile.length()));
        return localContentValues;
    }





    private static final String VIDEO_BASE_URI = "content://media/external/video/media";
    /***
     *
     * @param videoPath
     * @param context
     */
    public static void insertVideo(String videoPath, Context context) {
        try {
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(videoPath);
            int nVideoWidth = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
            int nVideoHeight = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
            int duration = Integer
                    .parseInt(retriever
                            .extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
            long dateTaken = System.currentTimeMillis();
            File file = new File(videoPath);
            String title = file.getName();
            String filename = file.getName();
            String mime = "video/mp4";
            ContentValues mCurrentVideoValues = new ContentValues(9);
            mCurrentVideoValues.put(MediaStore.Video.Media.TITLE, title);
            mCurrentVideoValues.put(MediaStore.Video.Media.DISPLAY_NAME, filename);
            mCurrentVideoValues.put(MediaStore.Video.Media.DATE_TAKEN, dateTaken);
            mCurrentVideoValues.put(MediaStore.MediaColumns.DATE_MODIFIED, dateTaken / 1000);
            mCurrentVideoValues.put(MediaStore.Video.Media.MIME_TYPE, mime);
            mCurrentVideoValues.put(MediaStore.Video.Media.DATA, videoPath);
            mCurrentVideoValues.put(MediaStore.Video.Media.WIDTH, nVideoWidth);
            mCurrentVideoValues.put(MediaStore.Video.Media.HEIGHT, nVideoHeight);
            mCurrentVideoValues.put(MediaStore.Video.Media.RESOLUTION, Integer.toString(nVideoWidth) + "x" + Integer.toString(nVideoHeight));
            mCurrentVideoValues.put(MediaStore.Video.Media.SIZE, new File(videoPath).length());
            mCurrentVideoValues.put(MediaStore.Video.Media.DURATION, duration);
            ContentResolver contentResolver = context.getContentResolver();
            Uri videoTable = Uri.parse(VIDEO_BASE_URI);
            Uri uri = contentResolver.insert(videoTable, mCurrentVideoValues);
            writeFile(context,videoPath, mCurrentVideoValues, contentResolver, uri);
        }catch (Exception e){

        }

    }

    /**需要依赖Okio*/
    /***
     *
     * @param imagePath
     * @param context
     */
    public static void insertImage(String imagePath, Context context) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "IMG1024.JPG");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        ContentResolver contentResolver = context.getContentResolver();
        Uri collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        Uri item = contentResolver.insert(collection, values);
        writeFile(context,imagePath, values, contentResolver, item);
        contentResolver.update(item, values, null, null);
    }

    private static void writeFile(Context context, String imagePath, ContentValues values, ContentResolver contentResolver, Uri item) {
        try (OutputStream rw = contentResolver.openOutputStream(item, "rw")) {
            // Write data into the pending image.
            Sink sink = Okio.sink(rw);
            BufferedSource buffer = Okio.buffer(Okio.source(new File(imagePath)));
            buffer.readAll(sink);
            values.put(MediaStore.Video.Media.IS_PENDING, 0);
            contentResolver.update(item, values, null, null);
            //new File(imagePath).delete();
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                Cursor query = context.getContentResolver().query(item, null, null, null);
                if (query != null) {
                    int count = query.getCount();
                    Log.e("writeFile","writeFile result :" + count);
                    query.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
