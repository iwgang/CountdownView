package cn.iwgang.countdownview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

/**
 * Countdown View
 * Created by iWgang on 15/9/16.
 * https://github.com/iwgang/CountdownView
 */
public class CountdownView extends View {
    private Context mContext;
    private int mDay, mHour, mMinute, mSecond, mMillisecond;
    private long mRemainTime;
    private OnCountdownEndListener mOnCountdownEndListener;
    private OnCountdownIntervalListener mOnCountdownIntervalListener;
    private CountDownTimer mCountDownTimer;

    private boolean isShowDay, isShowHour,isShowMinute, isShowMillisecond;
    private boolean mHasSetIsShowDay, mHasSetIsShowHour;
    private boolean isHideTimeBackground;
    private boolean isShowTimeBgDivisionLine;
    private boolean isTimeTextBold;
    private boolean isSuffixTextBold;

    private Paint mTimeTextPaint;
    private Paint mSuffixTextPaint;
    private Paint mTimeTextBgPaint;
    private Paint mTimeTextBgDivisionLinePaint;

    private RectF mDayBgRectF, mHourBgRectF, mMinuteBgRectF, mSecondBgRectF, mMillisecondBgRectF;

    private float mTimeTextWidth;
    private float mTimeTextHeight;
    private float mTimeTextSize;
    private float mTimeBgSize;
    private int mTimeTextColor;
    private int mTimeBgColor;
    private float mTimeBgRadius;
    private int mTimeBgDivisionLineColor;
    private float mTimeTextBaseY;
    private float mTimeBgDivisionLineYPos;
    private int mTimeBgDivisionLineSize;

    private String mSuffix, mSuffixDay, mSuffixHour, mSuffixMinute, mSuffixSecond, mSuffixMillisecond;
    private int mSuffixTextColor;
    private float mSuffixTextSize;
    private float mSuffixDayTextWidth;
    private float mSuffixHourTextWidth;
    private float mSuffixMinuteTextWidth;
    private float mSuffixSecondTextWidth;
    private float mSuffixMillisecondTextWidth;
    private int mSuffixGravity;
    private float mSuffixLRMargin;
    private float mSuffixDayLeftMargin;
    private float mSuffixDayRightMargin;
    private float mSuffixHourLeftMargin;
    private float mSuffixHourRightMargin;
    private float mSuffixMinuteLeftMargin;
    private float mSuffixMinuteRightMargin;
    private float mSuffixSecondLeftMargin;
    private float mSuffixSecondRightMargin;
    private float mSuffixMillisecondLeftMargin;
    private float mSuffixDayTextBaseline, mSuffixHourTextBaseline, mSuffixMinuteTextBaseline, mSuffixSecondTextBaseline, mSuffixMillisecondTextBaseline;

    private float mTimeTextBaseline;
    private float mLeftPaddingSize;
    private float mTopPaddingSize;
    private int mContentAllWidth;
    private int mContentAllHeight;
    private int mViewWidth;
    private int mViewHeight;

    private float mDayTimeTextWidth;
    private float mDayTimeBgWidth;
    private boolean isDayLargeNinetyNine;
    private String mTempSuffixSecond;
    private float mTempSuffixSecondLeftMargin, mTempSuffixSecondRightMargin;

    private long mInterval;
    private long mPreviouIntervalCallbackTime;

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
        mTimeBgColor = ta.getColor(R.styleable.CountdownView_timeBgColor, 0xFF444444);
        mTimeBgRadius = ta.getDimension(R.styleable.CountdownView_timeBgRadius, 0);
        isShowTimeBgDivisionLine = ta.getBoolean(R.styleable.CountdownView_isShowTimeBgDivisionLine, true);
        mTimeBgDivisionLineColor = ta.getColor(R.styleable.CountdownView_timeBgDivisionLineColor, Color.parseColor("#30FFFFFF"));

        isTimeTextBold = ta.getBoolean(R.styleable.CountdownView_isTimeTextBold, false);
        mTimeTextSize = ta.getDimension(R.styleable.CountdownView_timeTextSize, sp2px(12));
        mTimeTextColor = ta.getColor(R.styleable.CountdownView_timeTextColor, 0xFFFFFFFF);
        isHideTimeBackground = ta.getBoolean(R.styleable.CountdownView_isHideTimeBackground, false);
        isShowDay = ta.getBoolean(R.styleable.CountdownView_isShowDay, false);
        isShowHour = ta.getBoolean(R.styleable.CountdownView_isShowHour, false);
        isShowMinute = ta.getBoolean(R.styleable.CountdownView_isShowMinute, true);
        isShowMillisecond = ta.getBoolean(R.styleable.CountdownView_isShowMillisecond, false);

        mHasSetIsShowDay = ta.hasValue(R.styleable.CountdownView_isShowDay);
        mHasSetIsShowHour = ta.hasValue(R.styleable.CountdownView_isShowHour);

        isSuffixTextBold = ta.getBoolean(R.styleable.CountdownView_isSuffixTextBold, false);
        mSuffixTextSize = ta.getDimension(R.styleable.CountdownView_suffixTextSize, sp2px(12));
        mSuffixTextColor = ta.getColor(R.styleable.CountdownView_suffixTextColor, 0xFF000000);
        mSuffix = ta.getString(R.styleable.CountdownView_suffix);
        mSuffixDay = ta.getString(R.styleable.CountdownView_suffixDay);
        mSuffixHour = ta.getString(R.styleable.CountdownView_suffixHour);
        mSuffixMinute = ta.getString(R.styleable.CountdownView_suffixMinute);
        mSuffixSecond = ta.getString(R.styleable.CountdownView_suffixSecond);
        mSuffixMillisecond = ta.getString(R.styleable.CountdownView_suffixMillisecond);
        mSuffixGravity = ta.getInt(R.styleable.CountdownView_suffixGravity, 1);
        mSuffixLRMargin = ta.getDimension(R.styleable.CountdownView_suffixLRMargin, -1);
        mSuffixDayLeftMargin = ta.getDimension(R.styleable.CountdownView_suffixDayLeftMargin, -1);
        mSuffixDayRightMargin = ta.getDimension(R.styleable.CountdownView_suffixDayRightMargin, -1);
        mSuffixHourLeftMargin = ta.getDimension(R.styleable.CountdownView_suffixHourLeftMargin, -1);
        mSuffixHourRightMargin = ta.getDimension(R.styleable.CountdownView_suffixHourRightMargin, -1);
        mSuffixMinuteLeftMargin = ta.getDimension(R.styleable.CountdownView_suffixMinuteLeftMargin, -1);
        mSuffixMinuteRightMargin = ta.getDimension(R.styleable.CountdownView_suffixMinuteRightMargin, -1);
        mSuffixSecondLeftMargin = ta.getDimension(R.styleable.CountdownView_suffixSecondLeftMargin, -1);
        mSuffixSecondRightMargin = ta.getDimension(R.styleable.CountdownView_suffixSecondRightMargin, -1);
        mSuffixMillisecondLeftMargin = ta.getDimension(R.styleable.CountdownView_suffixMillisecondLeftMargin, -1);
        ta.recycle();

        initPaint();
        initSuffix();
        initSuffixMargin();

        // initialize time text width and height
        Rect rect = new Rect();
        mTimeTextPaint.getTextBounds("00", 0, 2, rect);
        mTimeTextWidth = rect.width();
        mTimeTextHeight = rect.height();

        if (!isHideTimeBackground) {
            mTimeBgSize = mTimeTextWidth + (dp2px(2) * 4);
        }
    }

    /**
     * initialize Paint
     */
    private void initPaint() {
        // time text
        mTimeTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTimeTextPaint.setColor(mTimeTextColor);
        mTimeTextPaint.setTextAlign(Paint.Align.CENTER);
        mTimeTextPaint.setTextSize(mTimeTextSize);
        if (isTimeTextBold) {
            mTimeTextPaint.setFakeBoldText(true);
        }

        // suffix text
        mSuffixTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSuffixTextPaint.setColor(mSuffixTextColor);
        mSuffixTextPaint.setTextSize(mSuffixTextSize);
        if (isSuffixTextBold) {
            mSuffixTextPaint.setFakeBoldText(true);
        }

        // time background
        mTimeTextBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTimeTextBgPaint.setStyle(Paint.Style.FILL);
        mTimeTextBgPaint.setColor(mTimeBgColor);

        // time background division line
        mTimeTextBgDivisionLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTimeTextBgDivisionLinePaint.setColor(mTimeBgDivisionLineColor);
        mTimeBgDivisionLineSize = dp2px(0.5f);
        mTimeTextBgDivisionLinePaint.setStrokeWidth(mTimeBgDivisionLineSize);
    }

    /**
     * initialize suffix
     */
    private void initSuffix() {
        boolean isSuffixNull = true;
        float mSuffixTextWidth = 0;

        if (!TextUtils.isEmpty(mSuffix)) {
            isSuffixNull = false;
            mSuffixTextWidth = mSuffixTextPaint.measureText(mSuffix);
        }

        if (isShowDay) {
            if (!TextUtils.isEmpty(mSuffixDay)) {
                mSuffixDayTextWidth = mSuffixTextPaint.measureText(mSuffixDay);
            } else {
                if (!isSuffixNull) {
                    mSuffixDay = mSuffix;
                    mSuffixDayTextWidth = mSuffixTextWidth;
                }
            }
        } else {
            mSuffixDayTextWidth = 0;
        }

        if (isShowHour) {
            if (!TextUtils.isEmpty(mSuffixHour)) {
                mSuffixHourTextWidth = mSuffixTextPaint.measureText(mSuffixHour);
            } else {
                if (!isSuffixNull) {
                    mSuffixHour = mSuffix;
                    mSuffixHourTextWidth = mSuffixTextWidth;
                }
            }
        } else {
            mSuffixHourTextWidth = 0;
        }

        if (isShowMinute) {
            if (!TextUtils.isEmpty(mSuffixMinute)) {
                mSuffixMinuteTextWidth = mSuffixTextPaint.measureText(mSuffixMinute);
            } else if (!isSuffixNull) {
                mSuffixMinute = mSuffix;
                mSuffixMinuteTextWidth = mSuffixTextWidth;
            }
        } else {
            mSuffixMinuteTextWidth = 0;
        }

        if (!TextUtils.isEmpty(mSuffixSecond)) {
            mSuffixSecondTextWidth = mSuffixTextPaint.measureText(mSuffixSecond);
        } else if (isShowMillisecond && !isSuffixNull) {
            mSuffixSecond = mSuffix;
            mSuffixSecondTextWidth = mSuffixTextWidth;
        } else {
            mSuffixSecondTextWidth = 0;
        }

        if (isShowMillisecond && isSuffixNull && !TextUtils.isEmpty(mSuffixMillisecond)) {
            mSuffixMillisecondTextWidth = mSuffixTextPaint.measureText(mSuffixMillisecond);
        } else {
            mSuffixMillisecondTextWidth = 0;
        }
    }

    /**
     * initialize suffix margin
     */
    private void initSuffixMargin() {
        boolean isSuffixLRMarginNull = true;

        if (mSuffixLRMargin > 0) {
            isSuffixLRMarginNull = false;
        }

        if (isShowDay) {
            if (mSuffixDayLeftMargin < 0) {
                if (!isSuffixLRMarginNull) {
                    mSuffixDayLeftMargin = mSuffixLRMargin;
                } else {
                    mSuffixDayLeftMargin = 0;
                }
            }

            if (mSuffixDayRightMargin < 0) {
                if (!isSuffixLRMarginNull) {
                    mSuffixDayRightMargin = mSuffixLRMargin;
                } else {
                    mSuffixDayRightMargin = 0;
                }
            }
        } else {
            mSuffixDayLeftMargin = 0;
            mSuffixDayRightMargin = 0;
        }

        if (isShowHour) {
            if (mSuffixHourLeftMargin < 0) {
                if (!isSuffixLRMarginNull) {
                    mSuffixHourLeftMargin = mSuffixLRMargin;
                } else {
                    mSuffixHourLeftMargin = 0;
                }
            }

            if (mSuffixHourRightMargin < 0) {
                if (!isSuffixLRMarginNull) {
                    mSuffixHourRightMargin = mSuffixLRMargin;
                } else {
                    mSuffixHourRightMargin = 0;
                }
            }
        } else {
            mSuffixHourLeftMargin = 0;
            mSuffixHourRightMargin = 0;
        }

        if (isShowMinute) {
            if (mSuffixMinuteLeftMargin < 0) {
                if (!isSuffixLRMarginNull) {
                    mSuffixMinuteLeftMargin = mSuffixLRMargin;
                } else {
                    mSuffixMinuteLeftMargin = 0;
                }
            }

            if (mSuffixMinuteRightMargin < 0) {
                if (!isSuffixLRMarginNull) {
                    mSuffixMinuteRightMargin = mSuffixLRMargin;
                } else {
                    mSuffixMinuteRightMargin = 0;
                }
            }
        } else {
            mSuffixMinuteLeftMargin = 0;
            mSuffixMinuteRightMargin = 0;
        }

        if (mSuffixSecondLeftMargin < 0) {
            if (!isSuffixLRMarginNull) {
                mSuffixSecondLeftMargin = mSuffixLRMargin;
            } else {
                mSuffixSecondLeftMargin = 0;
            }
        }
        if (isShowMillisecond) {
            if (mSuffixSecondRightMargin < 0) {
                if (!isSuffixLRMarginNull) {
                    mSuffixSecondRightMargin = mSuffixLRMargin;
                } else {
                    mSuffixSecondRightMargin = 0;
                }
            }

            if (mSuffixMillisecondLeftMargin < 0) {
                if (!isSuffixLRMarginNull && mSuffixMillisecondTextWidth > 0) {
                    mSuffixMillisecondLeftMargin = mSuffixLRMargin;
                } else {
                    mSuffixMillisecondLeftMargin = 0;
                }
            }
        } else {
            mSuffixSecondRightMargin = 0;
            mSuffixMillisecondLeftMargin = 0;
        }
    }

    /**
     * initialize time initialize rectF
     */
    private void initTimeBgRect() {
        if (!isHideTimeBackground) {
            float mHourLeft;
            float mMinuteLeft;
            float mSecondLeft;

            if (isShowDay) {
                // initialize day background rectF
                mDayBgRectF = new RectF(mLeftPaddingSize, mTopPaddingSize, mLeftPaddingSize + mDayTimeBgWidth, mTopPaddingSize + mDayTimeBgWidth);
                // hour left point
                mHourLeft = mLeftPaddingSize + mDayTimeBgWidth + mSuffixDayTextWidth + mSuffixDayLeftMargin + mSuffixDayRightMargin;
            } else {
                // hour left point
                mHourLeft = mLeftPaddingSize;
            }

            if (isShowHour) {
                // initialize hour background rectF
                mHourBgRectF = new RectF(mHourLeft, mTopPaddingSize, mHourLeft + mTimeBgSize, mTopPaddingSize + mTimeBgSize);
                // minute left point
                mMinuteLeft = mHourLeft + mTimeBgSize + mSuffixHourTextWidth + mSuffixHourLeftMargin + mSuffixHourRightMargin;
            } else {
                // minute left point
                mMinuteLeft = mHourLeft;
            }

            if (isShowMinute) {
                // initialize minute background rectF
                mMinuteBgRectF = new RectF(mMinuteLeft, mTopPaddingSize, mMinuteLeft + mTimeBgSize, mTopPaddingSize + mTimeBgSize);
                // second left point
                mSecondLeft = mMinuteLeft + mTimeBgSize + mSuffixMinuteTextWidth + mSuffixMinuteLeftMargin + mSuffixMinuteRightMargin;
            } else {
                // second left point
                mSecondLeft = mMinuteLeft;
            }

            // initialize second background rectF
            mSecondBgRectF = new RectF(mSecondLeft, mTopPaddingSize, mSecondLeft + mTimeBgSize, mTopPaddingSize + mTimeBgSize);

            if (isShowMillisecond) {
                // millisecond left point
                float mMillisecondLeft = mSecondLeft + mTimeBgSize + mSuffixSecondTextWidth + mSuffixSecondLeftMargin + mSuffixSecondRightMargin;

                // initialize millisecond background rectF
                mMillisecondBgRectF = new RectF(mMillisecondLeft, mTopPaddingSize, mMillisecondLeft + mTimeBgSize, mTopPaddingSize + mTimeBgSize);
            }

            Paint.FontMetrics timeFontMetrics = mTimeTextPaint.getFontMetrics();
            mTimeTextBaseY = mSecondBgRectF.top + (mSecondBgRectF.bottom - mSecondBgRectF.top - timeFontMetrics.bottom + timeFontMetrics.top) / 2 - timeFontMetrics.top;

            // initialize background division line y point
            mTimeBgDivisionLineYPos = mSecondBgRectF.centerY() + mTimeBgDivisionLineSize;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mContentAllWidth = getAllContentWidth();
        mContentAllHeight = isHideTimeBackground ? (int) mTimeTextHeight : (int) mTimeBgSize;

        mViewWidth = measureSize(1, mContentAllWidth, widthMeasureSpec);
        mViewHeight = measureSize(2, mContentAllHeight, heightMeasureSpec);

        setMeasuredDimension(mViewWidth, mViewHeight);

        initTimeTextBaselineAndTimeBgTopPadding();
        initLeftPaddingSize();
        initTimeBgRect();
    }

    /**
     * initialize time text baseline
     * and
     * time background top padding
     */
    private void initTimeTextBaselineAndTimeBgTopPadding() {
        if (getPaddingTop() == getPaddingBottom()) {
            // center
            mTimeTextBaseline = mViewHeight / 2 + mTimeTextHeight / 2;

            mTopPaddingSize = (mViewHeight - mContentAllHeight) / 2;
        } else {
            // padding top
            mTimeTextBaseline = mViewHeight - (mViewHeight - getPaddingTop()) + mTimeTextHeight;

            mTopPaddingSize = getPaddingTop();
        }

        if (isShowDay && mSuffixDayTextWidth > 0) {
            mSuffixDayTextBaseline = getSuffixTextBaseLine(mSuffixDay);
        }

        if (isShowHour && mSuffixHourTextWidth > 0) {
            mSuffixHourTextBaseline = getSuffixTextBaseLine(mSuffixHour);
        }

        if (isShowMinute && mSuffixMinuteTextWidth > 0) {
            mSuffixMinuteTextBaseline = getSuffixTextBaseLine(mSuffixMinute);
        }

        if (mSuffixSecondTextWidth > 0) {
            mSuffixSecondTextBaseline = getSuffixTextBaseLine(mSuffixSecond);
        }

        if (isShowMillisecond && mSuffixMillisecondTextWidth > 0) {
            mSuffixMillisecondTextBaseline = getSuffixTextBaseLine(mSuffixMillisecond);
        }
    }

    private float getSuffixTextBaseLine(String suffixText) {
        Rect tempRect = new Rect();
        mSuffixTextPaint.getTextBounds(suffixText, 0, suffixText.length(), tempRect);

        float ret;
        switch (mSuffixGravity) {
            case 0:
                // top
                if (isHideTimeBackground) {
                    ret = mTimeTextBaseline - mTimeTextHeight - tempRect.top;
                } else {
                    ret = mTopPaddingSize - tempRect.top;
                }
                break;
            default:
            case 1:
                // center
                if (isHideTimeBackground) {
                    ret = mTimeTextBaseline - mTimeTextHeight / 2 + tempRect.height() / 2;
                } else {
                    ret = mTopPaddingSize + mTimeBgSize - mTimeBgSize / 2  + tempRect.height() / 2;
                }
                break;
            case 2:
                // bottom
                if (isHideTimeBackground) {
                    ret = mTimeTextBaseline - tempRect.bottom;
                } else {
                    ret = mTopPaddingSize + mTimeBgSize - tempRect.bottom;
                }
                break;
        }

        return ret;
    }

    /**
     * initialize view left padding
     */
    private void initLeftPaddingSize() {
        if (getPaddingLeft() == getPaddingRight()) {
            // center
            mLeftPaddingSize = (mViewWidth - mContentAllWidth) / 2;
        } else {
            // padding left
            mLeftPaddingSize = getPaddingLeft();
        }
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

    /**
     * get all view width
     * @return all view width
     */
    private int getAllContentWidth() {
        float timeWidth = isHideTimeBackground ? mTimeTextWidth : mTimeBgSize;
        float width = timeWidth;
        width += (mSuffixDayTextWidth + mSuffixHourTextWidth + mSuffixMinuteTextWidth + mSuffixSecondTextWidth + mSuffixMillisecondTextWidth);
        width += (mSuffixDayLeftMargin + mSuffixDayRightMargin + mSuffixHourLeftMargin + mSuffixHourRightMargin
                + mSuffixMinuteLeftMargin + mSuffixMinuteRightMargin + mSuffixSecondLeftMargin + mSuffixSecondRightMargin + mSuffixMillisecondLeftMargin);

        if (isShowDay) {
            if (isDayLargeNinetyNine) {
                Rect rect = new Rect();
                String tempDay = String.valueOf(mDay);
                mTimeTextPaint.getTextBounds(tempDay, 0, tempDay.length(), rect);
                mDayTimeTextWidth = rect.width();

                if (!isHideTimeBackground) {
                    mDayTimeBgWidth = mDayTimeTextWidth + (dp2px(2) * 4);
                    width += mDayTimeBgWidth;
                } else {
                    width += mDayTimeTextWidth;
                }
            } else {
                mDayTimeTextWidth = mTimeTextWidth;
                mDayTimeBgWidth = mTimeBgSize;
                width += timeWidth;
            }
        }

        if (isShowHour) {
            width += timeWidth;
        }

        if (isShowMinute) {
            width += timeWidth;
        }

        if (isShowMillisecond) {
            width += timeWidth;
        }

        return (int)Math.ceil(width);
    }

    private void refTimeShow(boolean isShowDay, boolean isShowHour, boolean  isShowMinute, boolean isShowMillisecond) {
        boolean isRef = false;

        if (this.isShowDay != isShowDay) {
            // reset
            if (isShowDay) {
                mSuffixDayLeftMargin = -1;
                mSuffixDayRightMargin = -1;
            }
            this.isShowDay = isShowDay;
            isRef = true;
        }

        if (this.isShowHour != isShowHour) {
            // reset
            if (isShowHour) {
                mSuffixHourLeftMargin = -1;
                mSuffixHourRightMargin = -1;
            }
            this.isShowHour = isShowHour;
            isRef = true;
        }

        if (this.isShowMinute != isShowMinute) {
            // reset
            if (isShowMinute) {
                mSuffixMinuteLeftMargin = -1;
                mSuffixMinuteRightMargin = -1;
            }
            this.isShowMinute = isShowMinute;
            isRef = true;
        }

        if (this.isShowMillisecond != isShowMillisecond) {
            // reset
            if (isShowMillisecond) {
                mSuffixMillisecondLeftMargin = -1;

                if (!TextUtils.isEmpty(mSuffix)) {
                    // reset temp value
                    mSuffixSecond = mTempSuffixSecond;
                    mSuffixSecondLeftMargin = mTempSuffixSecondLeftMargin;
                    mSuffixSecondRightMargin = mTempSuffixSecondRightMargin;
                }
            } else {
                if (!TextUtils.isEmpty(mSuffix)) {
                    // temp save
                    mTempSuffixSecond = mSuffixSecond;
                    mTempSuffixSecondLeftMargin = mSuffixSecondLeftMargin;
                    mTempSuffixSecondRightMargin = mSuffixSecondRightMargin;

                    mSuffixSecond = null;
                    mSuffixSecondLeftMargin = 0;
                    mSuffixSecondRightMargin = 0;
                }
            }
            this.isShowMillisecond = isShowMillisecond;
            isRef = true;
        }

        if (isRef) {
            initSuffix();
            initSuffixMargin();
            requestLayout();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float mHourLeft;
        float mMinuteLeft;
        float mSecondLeft;

        if (isHideTimeBackground) {
            // no background

            if (isShowDay) {
                // draw day text
                canvas.drawText(formatNum(mDay), mLeftPaddingSize + mDayTimeTextWidth / 2, mTimeTextBaseline, mTimeTextPaint);
                if (mSuffixDayTextWidth > 0) {
                    // draw day suffix
                    canvas.drawText(mSuffixDay, mLeftPaddingSize + mDayTimeTextWidth + mSuffixDayLeftMargin, mSuffixDayTextBaseline, mSuffixTextPaint);
                }

                // hour left point
                mHourLeft = mLeftPaddingSize + mDayTimeTextWidth + mSuffixDayTextWidth + mSuffixDayLeftMargin + mSuffixDayRightMargin;
            } else {
                // hour left point
                mHourLeft = mLeftPaddingSize;
            }

            if (isShowHour) {
                // draw hour text
                canvas.drawText(formatNum(mHour), mHourLeft + mTimeTextWidth / 2, mTimeTextBaseline, mTimeTextPaint);
                if (mSuffixHourTextWidth > 0) {
                    // draw hour suffix
                    canvas.drawText(mSuffixHour, mHourLeft + mTimeTextWidth + mSuffixHourLeftMargin, mSuffixHourTextBaseline, mSuffixTextPaint);
                }

                // minute left point
                mMinuteLeft = mHourLeft + mTimeTextWidth + mSuffixHourTextWidth + mSuffixHourLeftMargin + mSuffixHourRightMargin;
            } else {
                // minute left point
                mMinuteLeft = mHourLeft;
            }

            if (isShowMinute) {
                // draw minute text
                canvas.drawText(formatNum(mMinute), mMinuteLeft + mTimeTextWidth / 2 , mTimeTextBaseline, mTimeTextPaint);
                if (mSuffixMinuteTextWidth > 0) {
                    // draw minute suffix
                    canvas.drawText(mSuffixMinute, mMinuteLeft + mTimeTextWidth + mSuffixMinuteLeftMargin, mSuffixMinuteTextBaseline, mSuffixTextPaint);
                }

                // second left point
                mSecondLeft = mMinuteLeft + mTimeTextWidth + mSuffixMinuteTextWidth + mSuffixMinuteLeftMargin + mSuffixMinuteRightMargin;
            } else {
                // second left point
                mSecondLeft = mMinuteLeft;
            }

            // draw second text
            canvas.drawText(formatNum(mSecond), mSecondLeft + mTimeTextWidth / 2, mTimeTextBaseline, mTimeTextPaint);
            if (mSuffixSecondTextWidth > 0) {
                // draw second suffix
                canvas.drawText(mSuffixSecond, mSecondLeft + mTimeTextWidth + mSuffixSecondLeftMargin, mSuffixSecondTextBaseline, mSuffixTextPaint);
            }

            if (isShowMillisecond) {
                // millisecond left point
                float mMillisecondLeft = mSecondLeft + mTimeTextWidth + mSuffixSecondTextWidth + mSuffixSecondLeftMargin + mSuffixSecondRightMargin;
                // draw millisecond text
                canvas.drawText(formatMillisecond(), mMillisecondLeft + mTimeTextWidth / 2, mTimeTextBaseline, mTimeTextPaint);
                if (mSuffixMillisecondTextWidth > 0) {
                    // draw millisecond suffix
                    canvas.drawText(mSuffixMillisecond, mMillisecondLeft + mTimeTextWidth + mSuffixMillisecondLeftMargin, mSuffixMillisecondTextBaseline, mSuffixTextPaint);
                }
            }
        } else {
            // show background

            if (isShowDay) {
                // draw day background
                canvas.drawRoundRect(mDayBgRectF, mTimeBgRadius, mTimeBgRadius, mTimeTextBgPaint);
                if (isShowTimeBgDivisionLine) {
                    // draw day background division line
                    canvas.drawLine(mLeftPaddingSize, mTimeBgDivisionLineYPos, mLeftPaddingSize + mDayTimeBgWidth, mTimeBgDivisionLineYPos, mTimeTextBgDivisionLinePaint);
                }
                // draw day text
                canvas.drawText(formatNum(mDay), mDayBgRectF.centerX(), mTimeTextBaseY, mTimeTextPaint);
                if (mSuffixDayTextWidth > 0) {
                    // draw day suffix
                    canvas.drawText(mSuffixDay, mLeftPaddingSize + mDayTimeBgWidth + mSuffixDayLeftMargin, mSuffixDayTextBaseline, mSuffixTextPaint);
                }

                // hour left point
                mHourLeft = mLeftPaddingSize + mDayTimeBgWidth + mSuffixDayTextWidth + mSuffixDayLeftMargin + mSuffixDayRightMargin;
            } else {
                // hour left point
                mHourLeft = mLeftPaddingSize;
            }

            if (isShowHour) {
                // draw hour background
                canvas.drawRoundRect(mHourBgRectF, mTimeBgRadius, mTimeBgRadius, mTimeTextBgPaint);
                if (isShowTimeBgDivisionLine) {
                    // draw hour background division line
                    canvas.drawLine(mHourLeft, mTimeBgDivisionLineYPos, mTimeBgSize + mHourLeft, mTimeBgDivisionLineYPos, mTimeTextBgDivisionLinePaint);
                }
                // draw hour text
                canvas.drawText(formatNum(mHour), mHourBgRectF.centerX(), mTimeTextBaseY, mTimeTextPaint);
                if (mSuffixHourTextWidth > 0) {
                    // draw hour suffix
                    canvas.drawText(mSuffixHour, mHourLeft + mTimeBgSize + mSuffixHourLeftMargin, mSuffixHourTextBaseline, mSuffixTextPaint);
                }

                // minute left point
                mMinuteLeft = mHourLeft + mTimeBgSize + mSuffixHourTextWidth + mSuffixHourLeftMargin + mSuffixHourRightMargin;
            } else {
                // minute left point
                mMinuteLeft = mHourLeft;
            }

            if (isShowMinute) {
                // draw minute background
                canvas.drawRoundRect(mMinuteBgRectF, mTimeBgRadius, mTimeBgRadius, mTimeTextBgPaint);
                if (isShowTimeBgDivisionLine) {
                    // draw minute background division line
                    canvas.drawLine(mMinuteLeft, mTimeBgDivisionLineYPos, mTimeBgSize + mMinuteLeft, mTimeBgDivisionLineYPos, mTimeTextBgDivisionLinePaint);
                }
                // draw minute text
                canvas.drawText(formatNum(mMinute), mMinuteBgRectF.centerX(), mTimeTextBaseY, mTimeTextPaint);
                if (mSuffixMinuteTextWidth > 0) {
                    // draw minute suffix
                    canvas.drawText(mSuffixMinute, mMinuteLeft + mTimeBgSize + mSuffixMinuteLeftMargin, mSuffixMinuteTextBaseline, mSuffixTextPaint);
                }

                // second left point
                mSecondLeft = mMinuteLeft + mTimeBgSize + mSuffixMinuteTextWidth + mSuffixMinuteLeftMargin + mSuffixMinuteRightMargin;
            } else {
                // second left point
                mSecondLeft = mMinuteLeft;
            }

            // draw second background
            canvas.drawRoundRect(mSecondBgRectF, mTimeBgRadius, mTimeBgRadius, mTimeTextBgPaint);
            if (isShowTimeBgDivisionLine) {
                // draw second background division line
                canvas.drawLine(mSecondLeft, mTimeBgDivisionLineYPos, mTimeBgSize + mSecondLeft, mTimeBgDivisionLineYPos, mTimeTextBgDivisionLinePaint);
            }
            // draw second text
            canvas.drawText(formatNum(mSecond), mSecondBgRectF.centerX(), mTimeTextBaseY, mTimeTextPaint);
            if (mSuffixSecondTextWidth > 0) {
                // draw second suffix
                canvas.drawText(mSuffixSecond, mSecondLeft + mTimeBgSize + mSuffixSecondLeftMargin, mSuffixSecondTextBaseline, mSuffixTextPaint);
            }

            if (isShowMillisecond) {
                // millisecond left point
                float mMillisecondLeft = mSecondLeft + mTimeBgSize + mSuffixSecondTextWidth + mSuffixSecondLeftMargin + mSuffixSecondRightMargin;
                // draw millisecond background
                canvas.drawRoundRect(mMillisecondBgRectF, mTimeBgRadius, mTimeBgRadius, mTimeTextBgPaint);
                if (isShowTimeBgDivisionLine) {
                    // draw millisecond background division line
                    canvas.drawLine(mMillisecondLeft, mTimeBgDivisionLineYPos, mTimeBgSize + mMillisecondLeft, mTimeBgDivisionLineYPos, mTimeTextBgDivisionLinePaint);
                }
                // draw millisecond text
                canvas.drawText(formatMillisecond(), mMillisecondBgRectF.centerX(), mTimeTextBaseY, mTimeTextPaint);
                if (mSuffixMillisecondTextWidth > 0) {
                    // draw millisecond suffix
                    canvas.drawText(mSuffixMillisecond, mMillisecondLeft + mTimeBgSize + mSuffixMillisecondLeftMargin, mSuffixMillisecondTextBaseline, mSuffixTextPaint);
                }
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    /**
     * start countdown
     * @param millisecond millisecond
     */
    public void start(long millisecond) {
        if (millisecond <= 0) {
            return ;
        }

        if (null != mCountDownTimer) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }

        long countDownInterval;
        if (isShowMillisecond) {
            countDownInterval = 10;
            updateShow(millisecond);
        } else {
            countDownInterval = 1000;
        }

        mCountDownTimer = new CountDownTimer(millisecond, countDownInterval) {
            @Override
            public void onFinish() {
                // countdown end
                allShowZero();
                // callback
                if (null != mOnCountdownEndListener) {
                    mOnCountdownEndListener.onEnd(CountdownView.this);
                }
            }

            @Override
            public void onTick(long millisUntilFinished) {
                updateShow(millisUntilFinished);
            }
        };
        mCountDownTimer.start();
    }

    /**
     * stop countdown
     */
    public void stop() {
        if (null != mCountDownTimer) mCountDownTimer.cancel();
    }

    /**
     * custom time show
     * @param isShowDay isShowDay
     * @param isShowHour isShowHour
     * @param isShowMinute isShowMinute
     * @param isShowMillisecond isShowMillisecd
     */
    public void customTimeShow(boolean isShowDay, boolean isShowHour, boolean  isShowMinute, boolean isShowMillisecond) {
        mHasSetIsShowDay = true;
        mHasSetIsShowHour = true;
        refTimeShow(isShowDay, isShowHour, isShowMinute, isShowMillisecond);
    }

    /**
     * set all time zero
     */
    public void allShowZero() {
        mHour = 0;
        mMinute = 0;
        mSecond = 0;
        mMillisecond = 0;
        invalidate();
    }

    /**
     * set countdown end callback listener
     * @param onCountdownEndListener OnCountdownEndListener
     */
    public void setOnCountdownEndListener(OnCountdownEndListener onCountdownEndListener) {
        this.mOnCountdownEndListener = onCountdownEndListener;
    }

    /**
     * set interval callback listener
     * @param interval
     * @param onCountdownIntervalListener
     */
    public void setOnCountdownIntervalListener(long interval, OnCountdownIntervalListener onCountdownIntervalListener) {
        this.mInterval = interval;
        this.mOnCountdownIntervalListener = onCountdownIntervalListener;
    }

    /**
     * get day
     * @return current day
     */
    public int getDay() {
        return mDay;
    }

    /**
     * get hour
     * @return current hour
     */
    public int getHour() {
        return mHour;
    }

    /**
     * get minute
     * @return current minute
     */
    public int getMinute() {
        return mMinute;
    }

    /**
     * get second
     * @return current second
     */
    public int getSecond() {
        return mSecond;
    }

    /**
     * get remain time
     * @return remain time ( millisecond )
     */
    public long getRemainTime() {
        return mRemainTime;
    }

    private void updateShow(long ms) {
        this.mRemainTime = ms;

        mDay = (int)(ms / (1000 * 60 * 60 * 24));
        mHour = (int)((ms % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        mMinute = (int)((ms % (1000 * 60 * 60)) / (1000 * 60));
        mSecond = (int)((ms % (1000 * 60)) / 1000);
        mMillisecond = (int)(ms % 1000);

        // interval callback
        if (mInterval > 0 && null != mOnCountdownIntervalListener) {
            if (mPreviouIntervalCallbackTime == 0) {
                mPreviouIntervalCallbackTime = ms;
            } else if (ms + mInterval <= mPreviouIntervalCallbackTime) {
                mPreviouIntervalCallbackTime = ms;
                mOnCountdownIntervalListener.onInterval(this, mRemainTime);
            }
        }

        handlerAutoShowTimeAndDayLargeNinetyNine();

        invalidate();
    }

    private void handlerAutoShowTimeAndDayLargeNinetyNine() {
        if (!mHasSetIsShowDay) {
            if (!isShowDay && mDay > 0) {
                // auto show day
                // judge auto show hour
                if (!mHasSetIsShowHour) {
                    refTimeShow(true, true, isShowMinute, isShowMillisecond);
                } else {
                    refTimeShow(true, isShowHour, isShowMinute, isShowMillisecond);
                }
            } else if (isShowDay && mDay == 0) {
                // auto hide day
                refTimeShow(false, isShowHour, isShowMinute, isShowMillisecond);
            } else {
                if (!mHasSetIsShowHour) {
                    if (!isShowHour && (mDay > 0 || mHour > 0)) {
                        // auto show hour
                        refTimeShow(isShowDay, true, isShowMinute, isShowMillisecond);
                    } else if (isShowHour && mDay == 0 && mHour == 0) {
                        // auto hide hour
                        refTimeShow(false, false, isShowMinute, isShowMillisecond);
                    }
                }
            }
        } else {
            if (!mHasSetIsShowHour) {
                if (!isShowHour && (mDay > 0 || mHour > 0)) {
                    // auto show hour
                    refTimeShow(isShowDay, true, isShowMinute, isShowMillisecond);
                } else if (isShowHour && mDay == 0 && mHour == 0) {
                    // auto hide hour
                    refTimeShow(isShowDay, false, isShowMinute, isShowMillisecond);
                }
            }
        }

        if (isShowDay) {
            // handler large ninety nine
            if (!isDayLargeNinetyNine && mDay > 99) {
                isDayLargeNinetyNine = true;
                requestLayout();
            } else if (isDayLargeNinetyNine && mDay <= 99) {
                isDayLargeNinetyNine = false;
                requestLayout();
            }
        }
    }

    private String formatNum(int time) {
        return time < 10 ? "0"+time : String.valueOf(time);
    }

    private String formatMillisecond() {
        String retMillisecondStr;

        if (mMillisecond > 99) {
            retMillisecondStr = String.valueOf(mMillisecond / 10);
        } else if (mMillisecond <= 9) {
            retMillisecondStr = "0" + mMillisecond;
        } else {
            retMillisecondStr = String.valueOf(mMillisecond);
        }

        return retMillisecondStr;
    }

    public interface OnCountdownEndListener {
        void onEnd(CountdownView cv);
    }

    public interface OnCountdownIntervalListener {
        void onInterval(CountdownView cv, long remainTime);
    }

    private int dp2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private float sp2px(float spValue) {
        final float scale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return spValue * scale;
    }

}
