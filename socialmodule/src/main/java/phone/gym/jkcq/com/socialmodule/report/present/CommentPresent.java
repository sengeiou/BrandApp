package phone.gym.jkcq.com.socialmodule.report.present;

import android.text.TextUtils;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.mvp.BasePresenter;
import phone.gym.jkcq.com.socialmodule.bean.ListDataCommend;
import phone.gym.jkcq.com.socialmodule.bean.response.CommendDelBean;
import phone.gym.jkcq.com.socialmodule.bean.result.ResultCommentBean;
import phone.gym.jkcq.com.socialmodule.bean.result.ResultLikeBean;
import phone.gym.jkcq.com.socialmodule.report.repository.CommentRepository;
import phone.gym.jkcq.com.socialmodule.report.view.CommentView;

public class CommentPresent extends BasePresenter<CommentView> {

    private CommentView commentView;

    public CommentPresent(CommentView commentView) {
        this.commentView = commentView;
    }


    public void delCommend(String dynamicCommentId) {
        CommentRepository.delCommond(dynamicCommentId).as(commentView.bindAutoDispose()).subscribe(new BaseObserver<CommendDelBean>(BaseApp.getApp()) {
            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                if (commentView != null) {
                    commentView.failDelCommend();
                }
            }

            @Override
            public void onNext(CommendDelBean bean) {

                if (commentView != null) {
                    if (bean != null) {
                        commentView.successDelCommend(bean.getReplyCommentNums(),bean.getDynamicCommentNums());
                    } else {
                        commentView.failDelCommend();
                    }
                }
            }
        });
    }


    public void addCommend(String dynamicId, String dynamicCommentId, String fromUserId, String toUserId, String content) {
        CommentRepository.addComment(dynamicId, dynamicCommentId, fromUserId, toUserId, content).as(commentView.bindAutoDispose()).subscribe(new BaseObserver<String>(BaseApp.getApp()) {
            @Override
            public void onNext(String s) {
                if (commentView != null) {
                    if (TextUtils.isEmpty(s)) {
                        commentView.failAddCommend();
                    } else {
                        if (s.equals("true")) {
                            commentView.successAddCommend();
                        } else {
                            commentView.failAddCommend();
                        }
                    }
                }
            }

            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                if (commentView != null) {
                    commentView.failAddCommend();
                }
            }

        });
    }


    public void getCommend(String dynamicCommentId, String dynamicId, String positionDynamicCommentId,String meUserId, int page, int size) {
        CommentRepository.getCommond(dynamicCommentId, dynamicId,positionDynamicCommentId, meUserId, page, size).as(commentView.bindAutoDispose()).subscribe(new BaseObserver<ResultCommentBean<ListDataCommend>>(BaseApp.getApp(),true) {
            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

                if (commentView != null) {
                    commentView.failGetCommend();
                }
            }

            @Override
            public void onNext(ResultCommentBean<ListDataCommend> listDataResultCommentBean) {
                if (commentView != null) {
                    commentView.successGetCommend(listDataResultCommentBean,positionDynamicCommentId);
                }
            }
        });

    }

    public void likeCommend(String dynamicCommentId, String meUserId, String toUserId,boolean isTop) {
        CommentRepository.dynamicPraise(dynamicCommentId, meUserId,toUserId).as(commentView.bindAutoDispose()).subscribe(new BaseObserver<ResultLikeBean>(BaseApp.getApp()) {
            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

                if (commentView != null) {
                    commentView.failGetCommend();
                }
            }

            @Override
            public void onNext(ResultLikeBean resultLikeBean) {
                if (commentView != null) {
                    commentView.successLike(resultLikeBean,isTop,dynamicCommentId);
                }
            }
        });

    }

}
