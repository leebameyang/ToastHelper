package com.leebameyang.library;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;

import com.leebameyang.library.utils.AnimationUtil;

import java.util.Comparator;
import java.util.PriorityQueue;

class ToastTask extends Handler {

    private static class Messages {
        private static final int DISPLAY_TOAST = 0x32551;
        private static final int SHOW_NEXT = 0x42551;
        private static final int REMOVE_TOAST = 0x52551;
    }

    /**
     * 기본 생성자를 통해 우선순위큐를 초기화 합니다.
     **/
    private ToastTask() {
        toastTaskQueue = new PriorityQueue<>(10, new ToastComparator());
    }

    private class ToastComparator implements Comparator<ToastHelper> {

        /**
         * ToastTaskQueue에 추가된 객체의 우선순위를 비교하는 메서드
         * @param first 먼저 들어온 인스턴스
         * @param next 나중에 들어온 인스턴스
         * @return 우선순위
         **/
        @Override
        public int compare(ToastHelper next, ToastHelper first) {
            //우선 순위 -1 < 0 < 1
            if (next.getAttribute().priority < first.getAttribute().priority) return -1;
            else if (next.getAttribute().priority > first.getAttribute().priority) return  1;
            return next.getAttribute().priority <= first.getAttribute().priority ? -1 : 1;
        }
    }

    /**
     * 싱글톤 패턴
     * @return ToastTask 객체를 반환합니다.
     **/
    public static synchronized ToastTask getInstance() {
        if (mToast == null) {
            mToast = new ToastTask();
        }
        return mToast;
    }

    private static ToastTask mToast;
    private final PriorityQueue<ToastHelper> toastTaskQueue;

    /**
     * PriorityQueue에 ToastHelper 인스턴스를 추가하고
     * showNextToast() 메서드 호출
     * @param toastHelper 큐에 추가할 toastHelper 인스턴스
     **/
    public void add(ToastHelper toastHelper) {
        toastAllCancel();
        toastTaskQueue.add(toastHelper);
        this.showNextToast();
    }

    /**
     * 우선순위는 System.currentTimeMillis()로 가장 먼저 추가된 순서대로 반환
     * PriorityQueue에 우선순위에 따라 ToastHelper 인스턴스를 큐에서 제거하지않은 상태로 반환
     **/
    private void showNextToast() {
        // 큐가 비어있다면 메서드를 실행하지 않습니다.
        if (toastTaskQueue.isEmpty()) return;

        final ToastHelper toastHelper = toastTaskQueue.peek();
        // View가 보여지지 않았을 때만 다음 토스트를 띄우기위해 메세지 핸들러에 전달합니다.
        if (!toastHelper.isShowing()) {
            final Message message = obtainMessage(Messages.DISPLAY_TOAST);
            message.obj = toastHelper;
            sendMessage(message);
        }
    }

    /**
     * 토스트 출력
     * 레이아웃 파라미터에 지정된 애니메이션 효과 적용
     * @param toastHelper toastHelper 객체
     **/
    private void displayToast(ToastHelper toastHelper) {
        if (toastHelper.isShowing()) return;
        // 현재 보여지고 있는 토스트가 존재한다면 메서드 종료

        // 토스트 출력을 위해 WindowManager를 이용해 View를 추가
        final WindowManager windowManager = (WindowManager) toastHelper.getContext()
                .getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            windowManager.addView(toastHelper.getView(), toastHelper.getWindowManagerParams());
        }

        // 지정된 Duration 만큼 딜레이 후 토스트를 지우기 위해 핸들러메세지 전달
        sendMessageDelay(toastHelper, Messages.REMOVE_TOAST,
                toastHelper.getDuration() + AnimationUtil.SHOW_DURATION);
    }

    /**
     * 토스트 제거
     * 레이아웃 파라미터에 지정된 애니메이션 효과 적용
     * @param toastHelper 큐에서 제거할 toastHelper 인스턴스
     **/
    private void removeToast(final ToastHelper toastHelper) {

        final WindowManager windowManager = (WindowManager) toastHelper.getContext()
                .getSystemService(Context.WINDOW_SERVICE);

        if (windowManager == null) throw new IllegalStateException("WindowManager Instance NULL");

        // 토스트를 제거하기 위해 지정한 View를 제거
        try {
            windowManager.removeView(toastHelper.getView());
        } catch (IllegalArgumentException illegalArgumentException) {
            Log.e(getClass().getName(), illegalArgumentException.toString());
        }

        // 큐에 남아있는 다음 토스트를 실행하기 위한 핸들러 메세지 전달
        this.sendMessageDelay(toastHelper, Messages.SHOW_NEXT, AnimationUtil.HIDE_DURATION);

        toastTaskQueue.poll();
    }

    /**
     * 주어진 시간만큼 핸들러 메세지를 지연시키고 전달합니다.
     * @param toastHelper 메세지와 함께 전달할 toastHelper 인스턴스
     * @param messageId 메세지의 고유 아이디
     * @param duration 지연시간
     **/
    private void sendMessageDelay(ToastHelper toastHelper, int messageId, long duration) {
        Message message = obtainMessage(messageId);
        message.obj = toastHelper;
        sendMessageDelayed(message, duration);
    }

    /**
     * 도착한 핸들러 메세지를 처리합니다.
     * @param msg 전달받은 메세지 인스턴스
     **/
    @Override
    public void handleMessage(Message msg) {
        final ToastHelper toastHelper = (ToastHelper) msg.obj;
        switch (msg.what) {
            // 큐에 대기중인 다음 토스트를 실행
            case Messages.SHOW_NEXT :
                showNextToast();
                break;
            // 토스트 출력
            case Messages.DISPLAY_TOAST :
                displayToast(toastHelper);
                break;
            // Duration이 종료된 토스트 제거
            case Messages.REMOVE_TOAST :
                removeToast(toastHelper);
                break;
            // default
            default:
                super.handleMessage(msg);
                break;
        }
    }

    /**
     * 큐에 쌓인 모든 토스트메세지를 제거 합니다.
     **/
    public void toastAllCancel() {
        removeMessages(Messages.DISPLAY_TOAST);
        removeMessages(Messages.SHOW_NEXT);
        removeMessages(Messages.REMOVE_TOAST);

        for (ToastHelper toastHelper : toastTaskQueue) {
            final WindowManager windowManager =
                    (WindowManager) toastHelper.getContext().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
            if (toastHelper.isShowing()) {
                try {
                    windowManager.removeView(toastHelper.getView());
                } catch (NullPointerException | IllegalArgumentException e) {
                    Log.e(getClass().getName(), e.toString());
                }
            }
        }

        toastTaskQueue.clear();
    }
}
