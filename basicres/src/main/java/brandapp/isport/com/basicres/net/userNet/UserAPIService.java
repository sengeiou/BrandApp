package brandapp.isport.com.basicres.net.userNet;


import brandapp.isport.com.basicres.commonbean.BaseResponse;
import brandapp.isport.com.basicres.commonbean.CommonFriendRelation;
import brandapp.isport.com.basicres.commonbean.UserInfoBean;
import brandapp.isport.com.basicres.entry.bean.OssBean;
import brandapp.isport.com.basicres.entry.bean.UpdatePhotoBean;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author azheng
 * @date 2018/4/24.
 * GitHub：https://github.com/RookieExaminer
 * Email：wei.azheng@foxmail.com
 * Description：
 */
public interface UserAPIService {


    /**
     * 获取阿里云OSS key、secret
     * http://192.168.10.203:8767/oss/bonlala-oss/oss/app
     */
    @GET("oss/bonlala-oss/oss/app")
    Observable<BaseResponse<OssBean>> getAliToken();

    /**
     * 查询个人资料
     * http://192.168.10.203:8767/isport/concumer-basic/basic/customer/getBasicInfo
     */

    @POST("isport/concumer-basic/basic/customer/getBasicInfo")
    Observable<BaseResponse<UserInfoBean>> getUserInfo(@Body RequestBody body);

    //http://192.168.10.203:8767/isport/concumer-basic/basic/customer/friendRelation
    @POST("isport/concumer-basic/basic/customer/friendRelation")
    Observable<BaseResponse<CommonFriendRelation>> getFriendRelation(@Body RequestBody body);

    //上传用户图像
    //http://192.168.10.203:8767/isport/concumer-basic/basic/customer/editImmage?userId=1233
    @Multipart
    @POST("isport/concumer-basic/basic/customer/editImmage")
    Observable<BaseResponse<UpdatePhotoBean>> uploadFile(
            @Query("userId") String userid,
            @Part("description") RequestBody description,
            @Part MultipartBody.Part file);
    /**
     * 编辑用户资料
     * http://192.168.1.247:8767/isport/concumer-basic/basic/customer/editBasicInfo
     */
    @POST("isport/concumer-basic/basic/customer/editBasicInfo")
    Observable<BaseResponse<Integer>> updateUserInfo(@Body RequestBody body);

}
