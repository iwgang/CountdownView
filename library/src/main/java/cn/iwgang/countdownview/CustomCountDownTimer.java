package cn.iwgang.countdownview;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by iWgang on 15/10/18.
 * https://github.com/iwgang/CountdownView
 */
public class CustomCountDownTimer {
    private static final int HANDLER_WHAT_TICK = 10001;
    private static final int HANDLER_WHAT_END = 10002;
    private static final int HANDLER_WHAT_CANCEL = 10003;

    private long mMillisInFuture;
    private long mCountDownInterval;
    private boolean isCancel;
    private boolean isPause = true;
    private Timer mTimer;
    private CustomCountDownTimerListener mCustomCountDownTimerListener;
    private MyHandler mHandler;

    public CustomCountDownTimer(long millisInFuture, long countDownInterval) {
        this.mMillisInFuture = millisInFuture;
        this.mCountDownInterval = countDownInterval;
        mHandler = new MyHandler(this);
    }

    public void stop() {
        if (isCancel) return ;

        isCancel = true;
        mHandler.sendEmptyMessage(HANDLER_WHAT_CANCEL);
        if (null != mTimer) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    public void start() {
        if (isPause) {
            isPause = false;
            mTimer = new Timer();
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (isCancel) return ;

                    mMillisInFuture -= mCountDownInterval;
                    mHandler.sendEmptyMessage(HANDLER_WHAT_TICK);

                    if (mMillisInFuture <= 0) {
                        mMillisInFuture = 0;
                        isCancel = true;
                        mHandler.sendEmptyMessage(HANDLER_WHAT_END);
                        mTimer.cancel();
                    }
                }
            }, 0, mCountDownInterval);
        }
    }

    public void pause() {
        isPause = true;
        if (null != mTimer) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    public void restart() {
        start();
    }

    public void setCustomCountDownTimerListener(CustomCountDownTimerListener customCountDownTimerListener) {
        this.mCustomCountDownTimerListener = customCountDownTimerListener;
    }

    public interface CustomCountDownTimerListener {
        void onTick(long remainMillis);
        void onFinish();
        void onCancel();
    }

    private static class MyHandler extends Handler {
        WeakReference<CustomCountDownTimer> wrf = null;

        public MyHandler(CustomCountDownTimer customCountDownTimer) {
            wrf = new WeakReference<>(customCountDownTimer);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (null == wrf) {
                return ;
            }

            CustomCountDownTimer curCustomCountDownTimer = wrf.get();

            switch (msg.what) {
                case HANDLER_WHAT_TICK:
                    if (null != curCustomCountDownTimer.mCustomCountDownTimerListener) {
                        curCustomCountDownTimer.mCustomCountDownTimerListener.onTick(curCustomCountDownTimer.mMillisInFuture);
                    }
                    break;
                case HANDLER_WHAT_END:
                    if (null != curCustomCountDownTimer.mCustomCountDownTimerListener) {
                        curCustomCountDownTimer.mCustomCountDownTimerListener.onFinish();
                    }
                    break;
                case HANDLER_WHAT_CANCEL:
                    if (null != curCustomCountDownTimer.mCustomCountDownTimerListener) {
                        curCustomCountDownTimer.mCustomCountDownTimerListener.onCancel();
                    }
                    break;
            }
        }

    }

}
