package com.leebameyang.toasthelper.utils;

import com.leebameyang.toasthelper.Attribute;
import com.leebameyang.toasthelper.R;

public class AnimationUtil {

    public static final long SHOW_DURATION = 250;
    public static final long HIDE_DURATION = 250;

    /**
     * 애니메이션 유형에 따라 애니메이션을 반환 합니다.
     * @param animations Attribute 애니메이션 패턴
     * @return int type 애니메이션
     **/
    public static int getSystemAnimationsResource(@Attribute.Animations int animations) {
        switch (animations) {
            case Attribute.ANIMATION_FADE_IN: return android.R.style.Animation_Toast;
            case Attribute.ANIMATION_FLY_FROM_LEFT: return R.style.AnimationFlyFromLeft;
            case Attribute.ANIMATION_FLY_FROM_RIGHT: return R.style.AnimationFlyFromRight;
            case Attribute.ANIMATION_SCALE_UP: return android.R.style.Animation_Dialog;
            case Attribute.ANIMATION_POP: return android.R.style.Animation_InputMethod;
            default: return android.R.style.Animation_Toast;
        }
    }
}
