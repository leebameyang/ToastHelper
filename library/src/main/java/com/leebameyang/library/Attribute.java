package com.leebameyang.library;

import android.support.annotation.IntDef;
import android.util.TypedValue;
import android.view.Gravity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Attribute {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TypedValue.COMPLEX_UNIT_SP, TypedValue.COMPLEX_UNIT_DIP})
    public @interface TextSize {}

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TOP_CENTER, CENTRAL, BOTTOM_CENTER, Gravity.LEFT})
    public @interface GravityToast {}

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({SQUARE_FRAME, FILL_FRAME, ROUND_FRAME})
    public @interface Frame {}

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ANIMATION_FADE_IN, ANIMATION_FLY_FROM_LEFT, ANIMATION_POP, ANIMATION_SCALE_UP, ANIMATION_FLY_FROM_RIGHT})
    public @interface Animations {}

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({DURATION_VERY_SHORT, DURATION_SHORT, DURATION_MEDIUM, DURATION_LONG, DURATION_VERY_LONG})
    public @interface Duration {}

    public static final int SQUARE_FRAME = -1;
    public static final int ROUND_FRAME = -2;
    public static final int FILL_FRAME = -3;

    public static final int TOP_CENTER = Gravity.TOP | Gravity.CENTER;
    public static final int CENTRAL = Gravity.CENTER | Gravity.CENTER;
    public static final int BOTTOM_CENTER = Gravity.BOTTOM | Gravity.CENTER;

    public static final int ANIMATION_FADE_IN = 1;
    public static final int ANIMATION_FLY_FROM_LEFT = 2;
    public static final int ANIMATION_FLY_FROM_RIGHT = 3;
    public static final int ANIMATION_SCALE_UP = 4;
    public static final int ANIMATION_POP = 5;

    public static final int DURATION_VERY_SHORT = 1500;
    public static final int DURATION_SHORT = 2000;
    public static final int DURATION_MEDIUM = 2750;
    public static final int DURATION_LONG = 3500;
    public static final int DURATION_VERY_LONG = 4500;

    public int animation;
    public int duration;
    protected long priority;
}
