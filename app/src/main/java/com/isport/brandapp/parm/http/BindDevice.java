package com.isport.brandapp.parm.http;

import brandapp.isport.com.basicres.entry.bean.BaseParms;

public class BindDevice extends BaseParms {
    // params.put("userId", TokenUtil.getInstance().getPeopleIdStr(context));
    //        params.put("interfaceId", 0);
    //        params.put("deviceType", baseDevice.deviceType);
    //        params.put("mac", baseDevice.getDeviceName());
    //        params.put("deviceName", baseDevice.getDeviceName());


   public String deviceType;
   public String mac;
   public String deviceName;

}
