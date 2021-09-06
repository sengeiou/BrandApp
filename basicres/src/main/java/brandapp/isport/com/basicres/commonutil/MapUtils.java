package brandapp.isport.com.basicres.commonutil;

import com.isport.blelibrary.deviceEntry.impl.BaseDevice;

import java.util.Map;

/**
 * @创建者 bear
 * @创建时间 2019/3/23 11:01
 * @描述
 */
public class MapUtils {

    /**
     * 获取map中第一个key值
     *
     * @param map 数据源
     * @return
     */
    public static String getKeyOrNull(Map<String, Object> map) {
        String obj = null;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            obj = entry.getKey();
            if (obj != null) {
                break;
            }
        }
        return  obj;
    }


    /**
     * 获取map中第一个数据值
     *
     * @param map 数据源
     * @return
     */
    public static BaseDevice getFirstOrNull(Map<String, BaseDevice> map) {
        BaseDevice obj = null;
        for (Map.Entry<String, BaseDevice> entry : map.entrySet()) {
            obj = entry.getValue();
            if (obj != null) {
                break;
            }
        }
        return  obj;
    }

}
