package cn.iwgang.calendardemo.countdownview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;

/**
 * 倒计时View
 * Created by iWgang on 15/9/16.
 */
public class CountdownView extends View {
    private Context mContext;
    private int mDay, mHour, mMinute, mSecond, mMillisecond;
    private OnCountdownEndListener mOnCountdownEndListener;
    private CountDownTimer mCountDownTimer;

    private boolean isShowHour;
    private boolean isShowMillisecond;
    private boolean isHideTimeBackground;

    private Paint mTimeTextPaint;
    private Paint mDecollatorPaint;
    private Paint mTimeTextBgPaint;
    private Paint mTimeTextBgDivisionLinePaint;

    private RectF mHourBgRectF;
    private RectF mMinuteBgRectF;
    private RectF mSecondBgRectF;
    private RectF mMillisecondBgRectF;

    private float mTimeTextWidth;
    private float mTimeTextHeight;
    private float mDecollatorTextWidth;
    private float mDecollatorWidth;
    private float mDivisionTextSize;
    private float mTimeTextSize;
    private float mTimeBgSize;
    private int mTimeTextColor;
    private int mDivisionTextColor;

    private int mTimeBgCircularSize;

    public CountdownView(Context context) {
        this(context, null);
    }

    public CountdownView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountdownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.mContext = context;

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CountdownView);
        mTimeTextSize = ta.getDimension(R.styleable.CountdownView_timeTextSize, sp2px(12));
        mDivisionTextSize = ta.getDimension(R.styleable.CountdownView_divisionTextSize, sp2px(12));
        mTimeTextColor = ta.getColor(R.styleable.CountdownView_timeTextColor, Color.WHITE);
        mDivisionTextColor = ta.getColor(R.styleable.CountdownView_divisionTextColor, Color.BLACK);
        isHideTimeBackground = ta.getBoolean(R.styleable.CountdownView_isHideTimeBackground, false);
        isShowHour = ta.getBoolean(R.styleable.CountdownView_isShowHour, true);
        isShowMillisecond = ta.getBoolean(R.styleable.CountdownView_isShowMillisecond, false);
        ta.recycle();

        mTimeBgCircularSize = dp2px(2);

        // 初始化画笔
        initPaint(context);

        // 测量时间文字高度
        Rect rect = new Rect();
        mTimeTextPaint.getTextBounds("00", 0, 2, rect);
        mTimeTextWidth = rect.width();
        mTimeTextHeight = rect.height();

        mDecollatorTextWidth = mDecollatorPaint.measureText(":");
        mDecollatorWidth = mDecollatorTextWidth + (dp2px(2) * 2);
        mTimeBgSize = mTimeTextWidth + (dp2px(2) * 4);

        // 初始化时间背景的RectF对象
        initTimeBgRect();
    }

    /**
     * 初始化画笔
     * @param context context
     */
    private void initPaint(Context context) {
        // 初始化时间文字画笔
        mTimeTextPaint = new Paint();
        mTimeTextPaint.setAntiAlias(true);
        mTimeTextPaint.setColor(mTimeTextColor);
        mTimeTextPaint.setTextSize(mTimeTextSize);

        // 初始化分割文字画笔
        mDecollatorPaint = new Paint();
        mDecollatorPaint.setAntiAlias(true);
        mDecollatorPaint.setColor(mDivisionTextColor);
//        mDecollatorPaint.setTextAlign(Paint.Align.CENTER);
        mDecollatorPaint.setTextSize(mDivisionTextSize);

        // 初始化时间背景画笔
        mTimeTextBgPaint = new Paint();
        mTimeTextBgPaint.setStyle(Paint.Style.FILL);
        mTimeTextBgPaint.setAntiAlias(true);
        mTimeTextBgPaint.setColor(0xFF444444);

        // 初始化时间背景中间的分割线画笔
        mTimeTextBgDivisionLinePaint = new Paint();
        mTimeTextBgDivisionLinePaint.setAntiAlias(true);
        mTimeTextBgDivisionLinePaint.setColor(Color.parseColor("#30FFFFFF"));
        mTimeTextBgDivisionLinePaint.setStrokeWidth(dp2px(0.5f));
    }

    /**
     * 初始化时间背景的RectF对象
     */
    private void initTimeBgRect() {
        if (!isHideTimeBackground) {
            float mLeftSize3;

            if (isShowHour) {
                // 显示小时

                // 计算分钟x轴
                float mLeftSize2 = mTimeBgSize + mDecollatorWidth;
                // 计算秒钟x轴
                mLeftSize3 = mLeftSize2 * 2;

                // 初始化小时背景RectF
                mHourBgRectF = new RectF(0, 0, mTimeBgSize, mTimeBgSize);
                // 初始化分钟背景RectF
                mMinuteBgRectF = new RectF(mLeftSize2, 0, mTimeBgSize + mLeftSize2, mTimeBgSize);
                // 初始化秒钟背景RectF
                mSecondBgRectF = new RectF(mLeftSize3, 0, mTimeBgSize + mLeftSize3, mTimeBgSize);
            } else {
                // 不显示小时

                // 计算秒钟x轴
                mLeftSize3 = mTimeBgSize + mDecollatorWidth;

                // 初始化分钟背景RectF
                mMinuteBgRectF = new RectF(0, 0, mTimeBgSize, mTimeBgSize);
                // 初始化秒钟背景RectF
                mSecondBgRectF = new RectF(mLeftSize3, 0, mTimeBgSize + mLeftSize3, mTimeBgSize);
            }

            if (isShowMillisecond) {
                // 计算毫秒x轴
                float mLeftSize4 = mTimeBgSize + mDecollatorWidth + mLeftSize3;

                // 初始化毫秒背景RectF
                mMillisecondBgRectF = new RectF(mLeftSize4, 0, mTimeBgSize + mLeftSize4, mTimeBgSize);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (isHideTimeBackground) {
            float width = mTimeTextWidth * 2;
            width += mDecollatorWidth;

            if (isShowMillisecond) {
                width += mTimeTextWidth;
                width += mDecollatorWidth;
            }

            if (isShowHour) {
                width += mTimeTextWidth;
                width += mDecollatorWidth;
            }

            setMeasuredDimension((int)width + 5, (int)mTimeTextHeight);
        } else {
            float width = mTimeBgSize * 2;
            width += mDecollatorWidth;

            if (isShowMillisecond) {
                width += mTimeBgSize;
                width += mDecollatorWidth;
            }

            if (isShowHour) {
                width += mTimeBgSize;
                width += mDecollatorWidth;
            }

            setMeasuredDimension((int)width, (int)mTimeBgSize);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float mLeftSize3;
        float mTextYPos;

        if (isHideTimeBackground) {
            // 无背景
            mTextYPos = mTimeTextHeight;

            if (isShowHour) {
                // 显示小时
                // 画小时文字
                canvas.drawText(formatNum(mHour), 0, mTextYPos, mTimeTextPaint);
                // 画小时后面的分割线
                canvas.drawText(":", mTimeTextWidth + (mDecollatorTextWidth / 2), mTextYPos, mDecollatorPaint);

                // 计算分钟x轴
                float mLeftSize2 = mTimeTextWidth + mDecollatorWidth;
                // 画分钟文字
                canvas.drawText(formatNum(mMinute), mLeftSize2, mTextYPos, mTimeTextPaint);
                // 画分钟后面的分割线
                canvas.drawText(":", mLeftSize2 + mTimeTextWidth + (mDecollatorTextWidth / 2), mTextYPos, mDecollatorPaint);

                // 计算秒钟x轴
                mLeftSize3 = mTimeTextWidth + mDecollatorWidth + mLeftSize2;
                // 画秒钟文字
                canvas.drawText(formatNum(mSecond), mLeftSize3 + (mTimeTextWidth / 2) - (mTimeTextWidth / 2), mTextYPos, mTimeTextPaint);
            } else {
                // 不显示小时
                // 画分钟文字
                canvas.drawText(formatNum(mMinute), 0, mTextYPos, mTimeTextPaint);
                // 画分钟后面的分割线
                canvas.drawText(":", mTimeTextWidth + (mDecollatorTextWidth / 2), mTextYPos, mDecollatorPaint);

                // 计算秒钟x轴
                mLeftSize3 = mTimeTextWidth + mDecollatorWidth;
                // 画秒钟文字
                canvas.drawText(formatNum(mSecond), mLeftSize3, mTextYPos, mTimeTextPaint);
            }
        } else {
            // 有背景
            mTextYPos = mTimeBgSize / 2 + mTimeTextHeight / 2;

            if (isShowHour) {
                // 显示小时
                // 画小时背景
                canvas.drawRoundRect(mHourBgRectF, mTimeBgCircularSize, mTimeBgCircularSize, mTimeTextBgPaint);
                // 画小时背景中间的横线
                canvas.drawLine(0, mTimeBgSize / 2, mTimeBgSize, mTimeBgSize / 2, mTimeTextBgDivisionLinePaint);
                // 画小时文字
                canvas.drawText(formatNum(mHour), mTimeBgSize / 2 - mTimeTextWidth / 2, mTextYPos, mTimeTextPaint);
                // 画小时后面的分割线
                canvas.drawText(":", mTimeBgSize + (mDecollatorTextWidth / 2), mTextYPos, mDecollatorPaint);

                // 计算分钟x轴
                float mLeftSize2 = mTimeBgSize + mDecollatorWidth;
                // 画分钟背景
                canvas.drawRoundRect(mMinuteBgRectF, mTimeBgCircularSize, mTimeBgCircularSize, mTimeTextBgPaint);
                // 画分钟背景中间的横线
                canvas.drawLine(mLeftSize2, mTimeBgSize / 2, mTimeBgSize + mLeftSize2, mTimeBgSize / 2, mTimeTextBgDivisionLinePaint);
                // 画分钟文字
                canvas.drawText(formatNum(mMinute), mLeftSize2 + (mTimeBgSize / 2) - (mTimeTextWidth / 2), mTextYPos, mTimeTextPaint);
                // 画分钟后面的分割线
                canvas.drawText(":", mLeftSize2 + mTimeBgSize + (mDecollatorTextWidth / 2), mTextYPos, mDecollatorPaint);

                // 计算秒钟x轴
                mLeftSize3 = mLeftSize2 * 2;
                // 画秒钟背景
                canvas.drawRoundRect(mSecondBgRectF, mTimeBgCircularSize, mTimeBgCircularSize, mTimeTextBgPaint);
                // 画秒钟背景中间的横线
                canvas.drawLine(mLeftSize3, mTimeBgSize / 2, mTimeBgSize + mLeftSize3, mTimeBgSize / 2, mTimeTextBgDivisionLinePaint);
                // 画秒钟文字
                canvas.drawText(formatNum(mSecond), mLeftSize3 + (mTimeBgSize / 2) - (mTimeTextWidth / 2), mTextYPos, mTimeTextPaint);
            } else {
                // 不显示小时
                // 画分钟背景
                canvas.drawRoundRect(mMinuteBgRectF, mTimeBgCircularSize, mTimeBgCircularSize, mTimeTextBgPaint);
                // 画分钟背景中间的横线
                canvas.drawLine(0, mTimeBgSize / 2, mTimeBgSize, mTimeBgSize / 2, mTimeTextBgDivisionLinePaint);
                // 画分钟文字
                canvas.drawText(formatNum(mMinute), mTimeBgSize / 2 - mTimeTextWidth / 2, mTextYPos, mTimeTextPaint);
                // 画分钟后面的分割线
                canvas.drawText(":", mTimeBgSize + (mDecollatorTextWidth / 2), mTextYPos, mDecollatorPaint);

                // 计算秒钟x轴
                mLeftSize3 = mTimeBgSize + mDecollatorWidth;
                // 画秒钟背景
                canvas.drawRoundRect(mSecondBgRectF, mTimeBgCircularSize, mTimeBgCircularSize, mTimeTextBgPaint);
                // 画秒钟背景中间的横线
                canvas.drawLine(mLeftSize3, mTimeBgSize / 2, mTimeBgSize + mLeftSize3, mTimeBgSize / 2, mTimeTextBgDivisionLinePaint);
                // 画秒钟文字
                canvas.drawText(formatNum(mSecond), mLeftSize3 + (mTimeBgSize / 2) - (mTimeTextWidth / 2), mTextYPos, mTimeTextPaint);
            }
        }

        // 判断显示毫秒
        if (isShowMillisecond) {
            // 计算毫秒x轴
            if (isHideTimeBackground) {
                // 无背景
                // 画秒钟后面的分割线
                canvas.drawText(":", mLeftSize3 + mTimeTextWidth + (mDecollatorTextWidth / 2), mTextYPos, mDecollatorPaint);

                float mLeftSize4 = mTimeTextWidth + mDecollatorWidth + mLeftSize3;
                // 画毫秒文字
                canvas.drawText(formatMillisecond(), mLeftSize4, mTextYPos, mTimeTextPaint);
            } else {
                // 有背景
                // 画秒钟后面的分割线
                canvas.drawText(":", mLeftSize3 + mTimeBgSize + (mDecollatorTextWidth / 2), mTextYPos, mDecollatorPaint);

                float mLeftSize4 = mTimeBgSize + mDecollatorWidth + mLeftSize3;
                // 画毫秒背景
                canvas.drawRoundRect(mMillisecondBgRectF, mTimeBgCircularSize, mTimeBgCircularSize, mTimeTextBgPaint);
                // 画毫秒背景中间的横线
                canvas.drawLine(mLeftSize4, mTimeBgSize / 2, mTimeBgSize + mLeftSize4, mTimeBgSize / 2, mTimeTextBgDivisionLinePaint);
                // 画毫秒文字
                canvas.drawText(formatMillisecond(), mLeftSize4 + (mTimeBgSize / 2) - (mTimeTextWidth / 2), mTextYPos, mTimeTextPaint);
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    /**
     * 启动倒计时
     * @param millisecond 毫秒数
     */
    public void start(long millisecond) {
        if (millisecond <= 0) {
            return ;
        }

        updateShow(millisecond);

        if (null != mCountDownTimer) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }

        mCountDownTimer = new CountDownTimer(millisecond, 10) {
            @Override
            public void onFinish() {
                // 倒计时结束
                // 回调
                if (null != mOnCountdownEndListener) {
                    mOnCountdownEndListener.onEnd();
                }
            }

            @Override
            public void onTick(long millisUntilFinished) {
                updateShow(millisUntilFinished);
            }
        };
        mCountDownTimer.start();
    }

    public void stop() {
        if (null != mCountDownTimer) mCountDownTimer.cancel();
    }

    public void setShowHourView(boolean isShowHour) {
        this.isShowHour = isShowHour;
        invalidate();
    }

    public void setShowMillisecondView(boolean isShowMillisecond) {
        this.isShowMillisecond = isShowMillisecond;
        invalidate();
    }

    public void allShowZero() {
        mHour = 0;
        mMinute = 0;
        mSecond = 0;
        mMillisecond = 0;

        invalidate();
    }

    public void setOnCountdownEndListener(OnCountdownEndListener onCountdownEndListener) {
        this.mOnCountdownEndListener = onCountdownEndListener;
    }

    private void updateShow(long ms) {
        mDay = (int)(ms / (1000 * 60 * 60 * 24));
        mHour = (int)((ms % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        mMinute = (int)((ms % (1000 * 60 * 60)) / (1000 * 60));
        mSecond = (int)((ms % (1000 * 60)) / 1000);
        mMillisecond = (int)(ms % 1000);

        invalidate();
    }

    private String formatNum(int time) {
        return time < 10 ? "0"+time : String.valueOf(time);
    }

    private String formatMillisecond() {
        String retMillisecondStr;

        if (mMillisecond > 99) {
            retMillisecondStr = String.valueOf(mMillisecond).substring(0, 2);
        } else if (mMillisecond <= 9) {
            retMillisecondStr = "0" + mMillisecond;
        } else {
            retMillisecondStr = String.valueOf(mMillisecond);
        }

        return retMillisecondStr;
    }

    public interface OnCountdownEndListener {
        void onEnd();
    }

    public int dp2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public float sp2px(float spValue) {
        final float scale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return spValue * scale;
    }

}
