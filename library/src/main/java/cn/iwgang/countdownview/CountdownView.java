package cn.iwgang.countdownview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

/**
 * Countdown View
 * Created by iWgang on 15/9/16.
 * https://github.com/iwgang/CountdownView
 */
public class CountdownView extends View {
    private BaseCountdown mCountdown;
    private CustomCountDownTimer mCustomCountDownTimer;
    private OnCountdownEndListener mOnCountdownEndListener;
    private OnCountdownIntervalListener mOnCountdownIntervalListener;

    private boolean isHideTimeBackground;
    private long mPreviousIntervalCallbackTime;
    private long mInterval;
    private long mRemainTime;

    public CountdownView(Context context) {
        this(context, null);
    }

    public CountdownView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountdownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CountdownView);
        isHideTimeBackground = ta.getBoolean(R.styleable.CountdownView_isHideTimeBackground, true);

        mCountdown = isHideTimeBackground ? new BaseCountdown() : new BackgroundCountdown();
        mCountdown.initStyleAttr(context,ta);
        ta.recycle();

        mCountdown.initialize();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int contentAllWidth = mCountdown.getAllContentWidth();
        int contentAllHeight = mCountdown.getAllContentHeight();
        int viewWidth = measureSize(1, contentAllWidth, widthMeasureSpec);
        int viewHeight = measureSize(2, contentAllHeight, heightMeasureSpec);
        setMeasuredDimension(viewWidth, viewHeight);

        mCountdown.onMeasure(this, viewWidth, viewHeight, contentAllWidth, contentAllHeight);
    }

    /**
     * measure view Size
     * @param specType    1 width 2 height
     * @param contentSize all content view size
     * @param measureSpec spec
     * @return measureSize
     */
    private int measureSize(int specType, int contentSize, int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = Math.max(contentSize, specSize);
        } else {
            result = contentSize;

            if (specType == 1) {
                // width
                result += (getPaddingLeft() + getPaddingRight());
            } else {
                // height
                result += (getPaddingTop() + getPaddingBottom());
            }
        }

        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCountdown.onDraw(canvas);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    private void reLayout() {
        mCountdown.reLayout();
        requestLayout();
    }

    /**
     * start countdown
     * @param millisecond millisecond
     */
    public void start(long millisecond) {
        if (millisecond <= 0) return;

        mPreviousIntervalCallbackTime = 0;

        if (null != mCustomCountDownTimer) {
            mCustomCountDownTimer.stop();
            mCustomCountDownTimer = null;
        }

        long countDownInterval;
        if (mCountdown.isShowMillisecond) {
            countDownInterval = 10;
            updateShow(millisecond);
        } else {
            countDownInterval = 1000;
        }

        mCustomCountDownTimer = new CustomCountDownTimer(millisecond, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateShow(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                // countdown end
                allShowZero();
                // callback
                if (null != mOnCountdownEndListener) {
                    mOnCountdownEndListener.onEnd(CountdownView.this);
                }
            }
        };
        mCustomCountDownTimer.start();
    }

    /**
     * stop countdown
     */
    public void stop() {
        if (null != mCustomCountDownTimer) mCustomCountDownTimer.stop();
    }

    /**
     * pause countdown
     */
    public void pause() {
        if (null != mCustomCountDownTimer) mCustomCountDownTimer.pause();
    }

    /**
     * pause countdown
     */
    public void restart() {
        if (null != mCustomCountDownTimer) mCustomCountDownTimer.restart();
    }

    /**
     * custom time show
     * @param isShowDay isShowDay
     * @param isShowHour isShowHour
     * @param isShowMinute isShowMinute
     * @param isShowSecond isShowSecond
     * @param isShowMillisecond isShowMillisecond
     *
     * use:{@link #dynamicShow(DynamicConfig)}
     */
    @Deprecated
    public void customTimeShow(boolean isShowDay, boolean isShowHour, boolean  isShowMinute, boolean isShowSecond, boolean isShowMillisecond) {
        mCountdown.mHasSetIsShowDay = true;
        mCountdown.mHasSetIsShowHour = true;

        boolean isModCountdownInterval = mCountdown.refTimeShow(isShowDay, isShowHour, isShowMinute, isShowSecond, isShowMillisecond);

        // judgement modify countdown interval
        if (isModCountdownInterval) {
            start(mRemainTime);
        }
    }

    /**
     * set all time zero
     */
    public void allShowZero() {
        mCountdown.setTimes(0, 0, 0, 0, 0);
        invalidate();
    }

    /**
     * set countdown end callback listener
     * @param onCountdownEndListener OnCountdownEndListener
     */
    public void setOnCountdownEndListener(OnCountdownEndListener onCountdownEndListener) {
        mOnCountdownEndListener = onCountdownEndListener;
    }

    /**
     * set interval callback listener
     * @param interval interval time
     * @param onCountdownIntervalListener OnCountdownIntervalListener
     */
    public void setOnCountdownIntervalListener(long interval, OnCountdownIntervalListener onCountdownIntervalListener) {
        mInterval = interval;
        mOnCountdownIntervalListener = onCountdownIntervalListener;
    }

    /**
     * get day
     * @return current day
     */
    public int getDay() {
        return mCountdown.mDay;
    }

    /**
     * get hour
     * @return current hour
     */
    public int getHour() {
        return mCountdown.mHour;
    }

    /**
     * get minute
     * @return current minute
     */
    public int getMinute() {
        return mCountdown.mMinute;
    }

    /**
     * get second
     * @return current second
     */
    public int getSecond() {
        return mCountdown.mSecond;
    }

    /**
     * get remain time
     * @return remain time ( millisecond )
     */
    public long getRemainTime() {
        return mRemainTime;
    }

    public void updateShow(long ms) {
        this.mRemainTime = ms;

        reSetTime(ms);

        // interval callback
        if (mInterval > 0 && null != mOnCountdownIntervalListener) {
            if (mPreviousIntervalCallbackTime == 0) {
                mPreviousIntervalCallbackTime = ms;
            } else if (ms + mInterval <= mPreviousIntervalCallbackTime) {
                mPreviousIntervalCallbackTime = ms;
                mOnCountdownIntervalListener.onInterval(this, mRemainTime);
            }
        }

        if (mCountdown.handlerAutoShowTime() || mCountdown.handlerDayLargeNinetyNine()) {
            reLayout();
        } else {
            invalidate();
        }
    }

    private void reSetTime(long ms) {
        int day = 0;
        int hour;

        if (!mCountdown.isConvertDaysToHours) {
            day = (int) (ms / (1000 * 60 * 60 * 24));
            hour = (int) ((ms % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        } else {
            hour = (int) (ms / (1000 * 60 * 60));
        }

        int minute = (int)((ms % (1000 * 60 * 60)) / (1000 * 60));
        int second = (int)((ms % (1000 * 60)) / 1000);
        int millisecond = (int)(ms % 1000);

        mCountdown.setTimes(day, hour, minute, second, millisecond);
    }

    public interface OnCountdownEndListener {
        void onEnd(CountdownView cv);
    }

    public interface OnCountdownIntervalListener {
        void onInterval(CountdownView cv, long remainTime);
    }

    /**
     * Dynamic show
     * @param dynamicConfig DynamicConfig
     */
    public void dynamicShow(DynamicConfig dynamicConfig) {
        if (null == dynamicConfig) return;

        boolean isReLayout = false;
        boolean isInvalidate = false;

        Float timeTextSize = dynamicConfig.getTimeTextSize();
        if (null != timeTextSize) {
            mCountdown.setTimeTextSize(timeTextSize);
            isReLayout = true;
        }

        Float suffixTextSize = dynamicConfig.getSuffixTextSize();
        if (null != suffixTextSize) {
            mCountdown.setSuffixTextSize(suffixTextSize);
            isReLayout = true;
        }

        Integer timeTextColor = dynamicConfig.getTimeTextColor();
        if (null != timeTextColor) {
            mCountdown.setTimeTextColor(timeTextColor);
            isInvalidate = true;
        }

        Integer suffixTextColor = dynamicConfig.getSuffixTextColor();
        if (null != suffixTextColor) {
            mCountdown.setSuffixTextColor(suffixTextColor);
            isInvalidate = true;
        }

        Boolean isTimeTextBold = dynamicConfig.isTimeTextBold();
        if (null != isTimeTextBold) {
            mCountdown.setTimeTextBold(isTimeTextBold);
            isReLayout = true;
        }

        Boolean isSuffixTimeTextBold = dynamicConfig.isSuffixTimeTextBold();
        if (null != isSuffixTimeTextBold) {
            mCountdown.setSuffixTextBold(isSuffixTimeTextBold);
            isReLayout = true;
        }

        // suffix text (all)
        String suffix = dynamicConfig.getSuffix();
        if (!TextUtils.isEmpty(suffix)) {
            mCountdown.setSuffix(suffix);
            isReLayout = true;
        }

        // suffix text
        String suffixDay = dynamicConfig.getSuffixDay();
        String suffixHour = dynamicConfig.getSuffixHour();
        String suffixMinute = dynamicConfig.getSuffixMinute();
        String suffixSecond = dynamicConfig.getSuffixSecond();
        String suffixMillisecond = dynamicConfig.getSuffixMillisecond();
        if (mCountdown.setSuffix(suffixDay, suffixHour, suffixMinute, suffixSecond, suffixMillisecond)) {
            isReLayout = true;
        }

        // suffix margin (all)
        Float suffixLRMargin = dynamicConfig.getSuffixLRMargin();
        if (null != suffixLRMargin) {
            mCountdown.setSuffixLRMargin(suffixLRMargin);
            isReLayout = true;
        }

        // suffix margin
        Float suffixDayLeftMargin = dynamicConfig.getSuffixDayLeftMargin();
        Float suffixDayRightMargin = dynamicConfig.getSuffixDayRightMargin();
        Float suffixHourLeftMargin = dynamicConfig.getSuffixHourLeftMargin();
        Float suffixHourRightMargin = dynamicConfig.getSuffixHourRightMargin();
        Float suffixMinuteLeftMargin = dynamicConfig.getSuffixMinuteLeftMargin();
        Float suffixMinuteRightMargin = dynamicConfig.getSuffixMinuteRightMargin();
        Float suffixSecondLeftMargin = dynamicConfig.getSuffixSecondLeftMargin();
        Float suffixSecondRightMargin = dynamicConfig.getSuffixSecondRightMargin();
        Float suffixMillisecondRightMargin = dynamicConfig.getSuffixMillisecondLeftMargin();
        if (mCountdown.setSuffixMargin(suffixDayLeftMargin, suffixDayRightMargin, suffixHourLeftMargin, suffixHourRightMargin,
                                       suffixMinuteLeftMargin, suffixMinuteRightMargin, suffixSecondLeftMargin, suffixSecondRightMargin, suffixMillisecondRightMargin)) {
            isReLayout = true;
        }

        Integer suffixGravity = dynamicConfig.getSuffixGravity();
        if (null != suffixGravity) {
            mCountdown.setSuffixGravity(suffixGravity);
            isReLayout = true;
        }

        // judgement time show
        Boolean tempIsShowDay = dynamicConfig.isShowDay();
        Boolean tempIsShowHour = dynamicConfig.isShowHour();
        Boolean tempIsShowMinute = dynamicConfig.isShowMinute();
        Boolean tempIsShowSecond = dynamicConfig.isShowSecond();
        Boolean tempIsShowMillisecond = dynamicConfig.isShowMillisecond();
        if (null != tempIsShowDay || null != tempIsShowHour || null != tempIsShowMinute || null != tempIsShowSecond || null != tempIsShowMillisecond) {
            boolean isShowDay = mCountdown.isShowDay;
            if (null != tempIsShowDay) {
                isShowDay = tempIsShowDay;
                mCountdown.mHasSetIsShowDay = true;
            } else {
                mCountdown.mHasSetIsShowDay = false;
            }
            boolean isShowHour = mCountdown.isShowHour;
            if (null != tempIsShowHour) {
                isShowHour = tempIsShowHour;
                mCountdown.mHasSetIsShowHour = true;
            } else {
                mCountdown.mHasSetIsShowHour = false;
            }
            boolean isShowMinute = null != tempIsShowMinute ? tempIsShowMinute : mCountdown.isShowMinute;
            boolean isShowSecond = null != tempIsShowSecond ? tempIsShowSecond : mCountdown.isShowSecond;
            boolean isShowMillisecond = null != tempIsShowMillisecond ? tempIsShowMillisecond : mCountdown.isShowMillisecond;

            boolean isModCountdownInterval = mCountdown.refTimeShow(isShowDay, isShowHour, isShowMinute, isShowSecond, isShowMillisecond);

            // judgement modify countdown interval
            if (isModCountdownInterval) {
                start(mRemainTime);
            }

            isReLayout = true;
        }

        DynamicConfig.BackgroundInfo backgroundInfo = dynamicConfig.getBackgroundInfo();
        if (!isHideTimeBackground && null != backgroundInfo) {
            BackgroundCountdown backgroundCountdown = (BackgroundCountdown) mCountdown;

            Float size = backgroundInfo.getSize();
            if (null != size) {
                backgroundCountdown.setTimeBgSize(size);
                isReLayout = true;
            }

            Integer color = backgroundInfo.getColor();
            if (null != color) {
                backgroundCountdown.setTimeBgColor(color);
                isInvalidate = true;
            }

            Float radius = backgroundInfo.getRadius();
            if (null != radius) {
                backgroundCountdown.setTimeBgRadius(radius);
                isInvalidate = true;
            }

            Boolean isShowTimeBgDivisionLine = backgroundInfo.isShowTimeBgDivisionLine();
            if (null != isShowTimeBgDivisionLine) {
                backgroundCountdown.setIsShowTimeBgDivisionLine(isShowTimeBgDivisionLine);

                if (isShowTimeBgDivisionLine) {
                    Integer divisionLineColor = backgroundInfo.getDivisionLineColor();
                    if (null != divisionLineColor) {
                        backgroundCountdown.setTimeBgDivisionLineColor(divisionLineColor);
                    }

                    Float divisionLineSize = backgroundInfo.getDivisionLineSize();
                    if (null != divisionLineSize) {
                        backgroundCountdown.setTimeBgDivisionLineSize(divisionLineSize);
                    }
                }
                isInvalidate = true;
            }

            Boolean isShowTimeBgBorder = backgroundInfo.isShowTimeBgBorder();
            if (null != isShowTimeBgBorder) {
                backgroundCountdown.setIsShowTimeBgBorder(isShowTimeBgBorder);

                if (isShowTimeBgBorder) {
                    Integer borderColor = backgroundInfo.getBorderColor();
                    if (null != borderColor) {
                        backgroundCountdown.setTimeBgBorderColor(borderColor);
                    }

                    Float borderSize = backgroundInfo.getBorderSize();
                    if (null != borderSize) {
                        backgroundCountdown.setTimeBgBorderSize(borderSize);
                    }

                    Float borderRadius = backgroundInfo.getBorderRadius();
                    if (null != borderRadius) {
                        backgroundCountdown.setTimeBgBorderRadius(borderRadius);
                    }
                }
                isReLayout = true;
            }
        }

        Boolean tempIsConvertDaysToHours = dynamicConfig.isConvertDaysToHours();
        if (null != tempIsConvertDaysToHours && mCountdown.setConvertDaysToHours(tempIsConvertDaysToHours)) {
            reSetTime(getRemainTime());
            isReLayout = true;
        }

        if (isReLayout) {
            reLayout();
        } else if (isInvalidate) {
            invalidate();
        }
    }

}
