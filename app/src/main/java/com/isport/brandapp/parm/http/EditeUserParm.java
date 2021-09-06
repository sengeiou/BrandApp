package com.isport.brandapp.parm.http;

import brandapp.isport.com.basicres.entry.bean.BaseParms;

public class EditeUserParm extends BaseParms {
    /**
     * String url = AllocationApi.postEditCustomerBasicInfo();
     * HashMap<String, Object> parmes = new HashMap<>();
     * parmes.put("userId", TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()));
     * parmes.put("interfaceId", String.valueOf(0));
     * parmes.put("nickName", name);
     * parmes.put("gender", sex);
     * parmes.put("height", StringUtil.getNumberStr(height));
     * parmes.put("weight", StringUtil.getNumberStr(weight));
     * parmes.put("birthday", defaultDay);
     */

    public String nickName;
    public String gender;
    public String height;
    public String weight;
    public String birthday;
    public String headurl;

}
