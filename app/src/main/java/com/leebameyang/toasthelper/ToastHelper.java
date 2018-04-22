package com.leebameyang.toasthelper;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.leebameyang.toasthelper.utils.AccessibilityUtil;
import com.leebameyang.toasthelper.utils.AnimationUtil;

public class ToastHelper {

    public static int REQ_CODE_OVERLAY_PERMISSION = 4233;

    private final Context mContext;
    private final View mView;
    private final TextView mTextView;
    private final Attribute attribute;
    protected WindowManager.LayoutParams params;

    /**
     * ToastHelper의 Public 생성자
     * @param context 유효한 컨텍스트
     **/
    public ToastHelper(Context context) {

        final LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mContext = context;
        this.mView = onCreateView(context, layoutInflater);
       // this.mViewGroup = ((Activity) context).findViewById(android.R.id.content);
        this.mTextView = this.mView.findViewById(R.id.basic_toast);
        this.attribute = new Attribute();
        setDefaultAttribute(context);
    }

    /**
     * LayoutInflate를 위해 ToastHelper에 정의한 Protected 메서드
     * @param context 유효한 컨텍스트
     * @param layoutInflater 컨텍스트로 얻어진 인플레이터
     * @return layoutInflate한 View 객체
     **/
    protected View onCreateView(Context context, LayoutInflater layoutInflater) {

        return layoutInflater.inflate(R.layout.toast_helper, null);
    }

    /**
     * 기본적인 ToastHelper를 초기화
     * @param context 액티비티 컨텍스트
     * @return 새로운 ToastHelper를 생성
     **/
    public static ToastHelper init(@NonNull Activity context) {

        return new ToastHelper(context);
    }

    /**
     * TextView의 텍스트를 설정
     * @default gravity center
     * @param text Toast로 출력될 메세지
     * @return 현재 ToastHelper 객체
     **/
    public ToastHelper setText (String text) {
        this.mTextView.setText(text.toString());
        this.mTextView.setGravity(Gravity.CENTER);
        return this;
    }

    /**
     * TextView의 텍스트를 설정
     * @param text Toast로 출력될 메세지
     * @param gravity 출력될 메세지의 위치 지정
     * @return 현재 ToastHelper 객체
     **/
    public ToastHelper setText (String text, @Attribute.GravityStyle int gravity) {
        this.mTextView.setText(text.toString());
        this.mTextView.setGravity(gravity);
        return this;
    }

    /**
     * TextView의 텍스트 색상을 설정
     * @param colorId R.id.color
     * @return 현재 ToastHelper 객체
     **/
    public ToastHelper setTextColor (int colorId) {
        this.mTextView.setTextColor(ContextCompat.getColor(this.mContext, colorId));
        return this;
    }

    /**
     * TextView의 텍스트 크기를 설정
     * @param unitType 단위 SP | DIP
     * @param size 텍스트 크기
     * @return 현재 ToastHelper 객체
     **/
    public ToastHelper setTextSize (@Attribute.TextSize int unitType, float size) {
        this.mTextView.setTextSize(unitType, size);
        return this;
    }

    /**
     * TextView의 frame을 설정
     * @param frameId R.id.drawable
     * @return 현재 ToastHelper 객체
     **/
    public ToastHelper setFrame(@Attribute.Frame int frameId) {
        switch (frameId) {
            case Attribute.SQUARE_FRAME:
                this.mTextView.setBackgroundResource(R.drawable.square_frame);
                break;
            case Attribute.ROUND_FRAME:
                this.mTextView.setBackgroundResource(R.drawable.round_frame);
                break;
            case Attribute.FILL_FRAME :
                this.mTextView.setBackgroundResource(R.drawable.fill_frame);
                setWindowManagerParams(
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT,
                        Gravity.BOTTOM,
                        0,
                        0);
                break;
        }
        return this;
    }

    /**
     * TextView의 배경 이미지를 설정
     * @param drawableId R.id.drawable
     * @return 현재 ToastHelper 객체
     **/
    public ToastHelper setBackground(int drawableId) {
        float paddingTop = getDimen(R.dimen.backgroundPaddingTop);
        float paddingRight = getDimen(R.dimen.backgroundPaddingRight);
        float paddingBottom = getDimen(R.dimen.backgroundPaddingBottom);
        float paddingLeft = getDimen(R.dimen.backgroundPaddingLeft);
        this.mTextView.setBackground(ContextCompat.getDrawable(mContext, drawableId));
        this.mTextView.setPadding(convertDIP(paddingLeft), convertDIP(paddingTop), convertDIP(paddingRight), convertDIP(paddingBottom));
        return this;
    }

    /**
     * Gravity를 설정
     * @param gravity Attribute의 @IntDef
     * @return 현재 ToastHelper 객체
     **/
    public ToastHelper setGravity (@Attribute.GravityToast int gravity) {
        this.params.gravity = gravity;
        return this;
    }

    /**
     * Frame의 가로 길이를 설정
     * @param width 길이 값
     * @return 현재 ToastHelper 객체
     **/
    public ToastHelper setWidth(int width) {
        this.params.width = width;
        return this;
    }

    /**
     * Frame의 세로 길이를 설정
     * @param height 길이 값
     * @return 현재 ToastHelper 객체
     **/
    public ToastHelper setHeight(int height) {
        this.params.height = height;
        return this;
    }

    /**
     * 파라미터의 y 좌표값을 설정하는 것으로 VerticalMargin 효과를 적용
     * @param y y 좌표 값
     * @return ToastHelper 객체
     **/
    public ToastHelper setVerticalMargin(int y) {
        this.params.y = convertDIP(y);
        return this;
    }

    /**
     * 텍스트뷰의 상,하,좌,우 시계방향으로 Padding을 설정
     * @param top 상단 패딩 값
     * @param right 우측 패딩 값
     * @param bottom 하단 패딩 값
     * @param left 좌측 패딩 값
     **/
    public ToastHelper setForTextViewPadding(int top, int right, int bottom, int left) {
        this.mTextView.setPadding(left, top, right, bottom);
        return this;
    }

    /**
     * Toast가 출력될 때 애니메이션 효과를 설정
     * @param animation 애니메이션
     * @return ToastHelper 객체
     **/
    public ToastHelper setAnimation(@Attribute.Animations int animation) {
        this.attribute.animation = animation;
        return this;
    }

    /**
     * 전달받은 설정값들과 로직들을 수행하여 토스트 출력
     **/
    public void show() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this.mContext)) {
            onObtainingPermissionOverlayWindow();
        } else {
            this.onPrepareShow();
            ToastTask.getInstance().add(this);
            AccessibilityUtil.sendAccessibilityEvent(this.mView);
        }
    }

    /**
     * 토스트를 띄우기 전 준비
     * 호출된 순간에 시간을 우선순위로 저장합니다.
     **/
    protected void onPrepareShow() {
        this.getAttribute().priority = System.currentTimeMillis();
    }

    /**
     * 현재 실행중인 모든 토스트메세지를 중지하고 제거
     **/
    public static void cancelAll() {
        ToastTask.getInstance().toastAllCancel();
    }

    /**
     * View가 보여지고 있는지를 확인
     **/
    public boolean isShowing() {
        return mView != null && mView.isShown();
    }

    /**
     * duration 값을 설정
     * @param duration Attribute에 지정된 지연시간 설정
     * @return toastHelper 객체
     **/
    public ToastHelper setDuration(@Attribute.Duration int duration) {
        this.attribute.duration = duration;
        return this;
    }

    /**
     * duration을 반환
     * @return 지연시간
     **/
    public int getDuration() {
        return this.attribute.duration;
    }

    /**
     * Attribute 클래스의 맴버변수 animation을 반환
     * @return 애니메이션
     **/
    public int getAnimation() {
        return this.attribute.animation;
    }

    /**
     * Attribute 클래스의 맴버변수에 접근하기 위한 메서드
     **/
    public Attribute getAttribute() {
        return this.attribute;
    }

    /**
     * TextView의 텍스트를 반환
     **/
    public String getText() {
        return this.mTextView.getText().toString();
    }

    /**
     * mView를 반환
     **/
    public View getView() {
        return this.mView;
    }

    /**
     * Frame의 가로 길이를 반환
     **/
    public int getWidth() {
        return this.params.width;
    }

    /**
     * Frame의 세로 길이를 반환
     **/
    public int getHeight() {
        return this.params.height;
    }

    /**
     * Context를 반환
     **/
    public Context getContext() {
        return this.mContext;
    }

    /**
     * Default 속성 초기화
     **/
    protected void setDefaultAttribute(Context context) {
        setTextColor(R.color.colorWhite)
                .setTextSize(TypedValue.COMPLEX_UNIT_DIP, context.getResources().getDimension(R.dimen.fontSize))
                .setFrame(Attribute.ROUND_FRAME)
                .setDuration(Attribute.DURATION_SHORT)
                .setWindowManagerParams(
                        WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.WRAP_CONTENT,
                        Gravity.BOTTOM | Gravity.CENTER,
                        0,
                        convertDIP(100));
    }

    /**
     * WindowManager 파라미터를 설정
     * @param width 가로 길이
     * @param height 세로 길이
     * @param gravity 위치
     * @param x x 좌표
     * @param y y 좌표
     **/
    private void setWindowManagerParams(int width, int height, int gravity, int x, int y) {
        if (this.params == null) this.params = new WindowManager.LayoutParams();
        this.params.width = width;
        this.params.height = height;
        this.params.gravity = gravity;
        this.params.x = x;
        this.params.y = y;
    }

    /**
     * 토스트 출력 전 필요한 WindowManager 파라미터를 설정하고 반환
     **/
    protected WindowManager.LayoutParams getWindowManagerParams() {
        final int sdkVersion = android.os.Build.VERSION.SDK_INT;

        if (this.params == null) {
            this.params = new WindowManager.LayoutParams();
        }

        // API Level 26 이상 부터 TYPE_TOAST를 지원하지 않습니다.
        if (sdkVersion >= Build.VERSION_CODES.M)
            this.params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        else {
            this.params.type = WindowManager.LayoutParams.TYPE_TOAST;
        }

        // FLAG
        this.params.flags =
                  WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        this.params.format = PixelFormat.TRANSLUCENT;
        this.params.windowAnimations = AnimationUtil.getSystemAnimationsResource(this.attribute.animation);
        return params;
    }

    /**
     * dimens.xml 참조하여 값을 얻습니다.
     * @param dimenId R.dimen.id
     * @return float type 값
     **/
    private float getDimen(int dimenId) {
        return this.mContext.getResources().getDimension(dimenId);
    }

    /**
     * int 타입의 픽셀값을 받아 dp값으로 변환 하는 메소드
     * @param pixel 픽셀 값
     * @return int 타입의 변환된 dp값
     **/
    private int convertDIP(int pixel) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixel, mContext.getResources().getDisplayMetrics()));
    }

    /**
     * float 타입의 픽셀값을 받아 dp값으로 변환 하는 메소드
     * @param pixel 픽셀 값
     * @return int 타입의 변환된 dp값
     **/
    private int convertDIP(float pixel) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixel, mContext.getResources().getDisplayMetrics()));
    }

    /**
     * SDK Level 26 이상 부터 Permission 변경된 해당 권한을 획득하기 위한 메소드
     **/
    @TargetApi(Build.VERSION_CODES.M)
    public void onObtainingPermissionOverlayWindow() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + this.mContext.getPackageName()));
        ((Activity) this.mContext).startActivityForResult(intent, REQ_CODE_OVERLAY_PERMISSION);
    }
}
