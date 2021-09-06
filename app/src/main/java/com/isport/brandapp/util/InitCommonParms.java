package com.isport.brandapp.util;

import brandapp.isport.com.basicres.commonnet.net.PostBody;

public class InitCommonParms<T, T1, T2> {
    PostBody postBody;

    public InitCommonParms<T, T1, T2> setPostBody(boolean isStandAlone) {
        PostBody<T, T1, T2> parmsPostBody = new PostBody<>();
        postBody = parmsPostBody;
        postBody.isStandAlone=isStandAlone;
        return this;
    }

    public InitCommonParms<T, T1, T2> setParms(T t) {
        postBody.data = t;
        return this;
    }

    public InitCommonParms<T, T1, T2> setBaseUrl(T1 t) {
        postBody.requseturl = t;
        return this;
    }

    public InitCommonParms<T, T1, T2> setBaseDbParms(T2 t) {
        postBody.dbParm = t;
        return this;
    }

    public PostBody<T, T1, T2> getPostBody() {
        return postBody;
    }

    public InitCommonParms<T, T1, T2> setType(int type) {
        postBody.type = type;
        return this;
    }

}
