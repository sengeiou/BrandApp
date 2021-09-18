package com.isport.brandapp.home.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.isport.brandapp.R;
import com.isport.brandapp.banner.recycleView.holder.CustomHolder;

import java.util.List;

import brandapp.isport.com.basicres.commonutil.ViewMultiClickUtil;

public class MineFooterHolder extends CustomHolder<String> {
    private View tvAddDevice;

    ImageView ivCoal, ivCourse, ivFreeCourse;

    public MineFooterHolder(View itemView) {
        super(itemView);
    }

    public MineFooterHolder(List<String> datas, View itemView) {
        super(datas, itemView);
    }

    public MineFooterHolder(Context context, final List<String> lists, int itemID) {
        super(context, lists, itemID);
        tvAddDevice = itemView.findViewById(R.id.tv_adddevice);

        tvAddDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lister != null) {
                    if (ViewMultiClickUtil.onMultiClick(view)) {
                        return;
                    }
                    lister.onAddDeviceOnclick();
                }
            }
        });
        /*ivCoal = (ImageView) itemView.findViewById(R.id.iv_coal);
        ivCourse = (ImageView) itemView.findViewById(R.id.iv_team_course);
        ivFreeCourse = (ImageView) itemView.findViewById(R.id.iv_free_course);
        ivCoal.setImageResource(R.drawable.bg_course_coach);
        ivCourse.setImageResource(R.drawable.bg_course_high_quality);
        ivFreeCourse.setImageResource(R.drawable.bg_course_free_group);

        ivFreeCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lister != null) {
                    if (ViewMultiClickUtil.onMultiClick(view)) {
                        return;
                    }
                    lister.OnCourseItemOnclick(JkConfiguration.CourseType.FREECOURESE);
                }
            }
        });

        ivCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ViewMultiClickUtil.onMultiClick(view)) {
                    return;
                }
                if (lister != null) {
                    lister.OnCourseItemOnclick(JkConfiguration.CourseType.GOODCOURSE);
                }
            }
        });

        ivCoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ViewMultiClickUtil.onMultiClick(view)) {
                    return;
                }
                if (lister != null) {
                    lister.OnCourseItemOnclick(JkConfiguration.CourseType.GYMCOACH);
                }
            }
        });*/

    }

    OnFooterOnclickLister lister;

    public void setOnCourseOnclickLister(OnFooterOnclickLister lister) {
        this.lister = lister;
    }


    public interface OnFooterOnclickLister {
        public void onAddDeviceOnclick();
    }
}
