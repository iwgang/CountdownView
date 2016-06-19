package cn.iwgang.countdownview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

/**
 * Background Countdown
 * Created by iWgang on 16/6/19.
 * https://github.com/iwgang/CountdownView
 */
class BackgroundCountdown extends BaseCountdown {
    private static final float DEFAULT_TIME_BG_DIVISION_LINE_SIZE = 0.5f; // dp

    private boolean isShowTimeBgDivisionLine;
    private int mTimeBgDivisionLineColor;
    private float mTimeBgDivisionLineSize;
    private float mTimeBgRadius;
    private float mTimeBgSize;
    private int mTimeBgColor;
    private Paint mTimeTextBgPaint;
    private Paint mTimeTextBgDivisionLinePaint;
    private float mDefSetTimeBgSize;
    private float mDayTimeBgWidth;
    private RectF mDayBgRectF, mHourBgRectF, mMinuteBgRectF, mSecondBgRectF, mMillisecondBgRectF;
    private float mTimeBgDivisionLineYPos;
    private float mTimeTextBaseY;

    @Override
    public void initStyleAttr(Context context, TypedArray ta) {
        super.initStyleAttr(context, ta);

        mTimeBgColor = ta.getColor(R.styleable.CountdownView_timeBgColor, 0xFF444444);
        mTimeBgRadius = ta.getDimension(R.styleable.CountdownView_timeBgRadius, 0);
        isShowTimeBgDivisionLine = ta.getBoolean(R.styleable.CountdownView_isShowTimeBgDivisionLine, true);
        mTimeBgDivisionLineColor = ta.getColor(R.styleable.CountdownView_timeBgDivisionLineColor, Color.parseColor("#30FFFFFF"));
        mTimeBgDivisionLineSize = ta.getDimension(R.styleable.CountdownView_timeBgDivisionLineSize, Utils.dp2px(context, DEFAULT_TIME_BG_DIVISION_LINE_SIZE));
        mTimeBgSize = ta.getDimension(R.styleable.CountdownView_timeBgSize, 0);
        mDefSetTimeBgSize = mTimeBgSize;
    }

    @Override
    protected void initPaint() {
        super.initPaint();
        // time background
        mTimeTextBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTimeTextBgPaint.setStyle(Paint.Style.FILL);
        mTimeTextBgPaint.setColor(mTimeBgColor);

        // time background division line
        if (isShowTimeBgDivisionLine) {
            initTimeTextBgDivisionLinePaint();
        }
    }

    private void initTimeTextBgDivisionLinePaint() {
        mTimeTextBgDivisionLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTimeTextBgDivisionLinePaint.setColor(mTimeBgDivisionLineColor);
        mTimeTextBgDivisionLinePaint.setStrokeWidth(mTimeBgDivisionLineSize);
    }

    @Override
    protected void initTimeTextBaseInfo() {
        super.initTimeTextBaseInfo();

        if (mDefSetTimeBgSize == 0 || mTimeBgSize < mTimeTextWidth) {
            mTimeBgSize = mTimeTextWidth + (Utils.dp2px(mContext, 2) * 4);
        }
    }

    /**
     * initialize time initialize rectF
     */
    private void initTimeBgRect(float topPaddingSize) {
        float mHourLeft;
        float mMinuteLeft;
        float mSecondLeft;
        boolean isInitHasBackgroundTextBaseY = false;

        if (isShowDay) {
            // initialize day background rectF
            mDayBgRectF = new RectF(mLeftPaddingSize, topPaddingSize, mLeftPaddingSize + mDayTimeBgWidth, topPaddingSize + mDayTimeBgWidth);
            // hour left point
            mHourLeft = mLeftPaddingSize + mDayTimeBgWidth + mSuffixDayTextWidth + mSuffixDayLeftMargin + mSuffixDayRightMargin;

            if (!isShowHour && !isShowMinute && !isShowSecond) {
                isInitHasBackgroundTextBaseY = true;
                initHasBackgroundTextBaseY(mDayBgRectF);
            }
        } else {
            // hour left point
            mHourLeft = mLeftPaddingSize;
        }

        if (isShowHour) {
            // initialize hour background rectF
            mHourBgRectF = new RectF(mHourLeft, topPaddingSize, mHourLeft + mTimeBgSize, topPaddingSize + mTimeBgSize);
            // minute left point
            mMinuteLeft = mHourLeft + mTimeBgSize + mSuffixHourTextWidth + mSuffixHourLeftMargin + mSuffixHourRightMargin;

            if (!isInitHasBackgroundTextBaseY) {
                isInitHasBackgroundTextBaseY = true;
                initHasBackgroundTextBaseY(mHourBgRectF);
            }
        } else {
            // minute left point
            mMinuteLeft = mHourLeft;
        }

        if (isShowMinute) {
            // initialize minute background rectF
            mMinuteBgRectF = new RectF(mMinuteLeft, topPaddingSize, mMinuteLeft + mTimeBgSize, topPaddingSize + mTimeBgSize);
            // second left point
            mSecondLeft = mMinuteLeft + mTimeBgSize + mSuffixMinuteTextWidth + mSuffixMinuteLeftMargin + mSuffixMinuteRightMargin;

            if (!isInitHasBackgroundTextBaseY) {
                isInitHasBackgroundTextBaseY = true;
                initHasBackgroundTextBaseY(mMinuteBgRectF);
            }
        } else {
            // second left point
            mSecondLeft = mMinuteLeft;
        }

        if (isShowSecond) {
            // initialize second background rectF
            mSecondBgRectF = new RectF(mSecondLeft, topPaddingSize, mSecondLeft + mTimeBgSize, topPaddingSize + mTimeBgSize);

            if (isShowMillisecond) {
                // millisecond left point
                float mMillisecondLeft = mSecondLeft + mTimeBgSize + mSuffixSecondTextWidth + mSuffixSecondLeftMargin + mSuffixSecondRightMargin;

                // initialize millisecond background rectF
                mMillisecondBgRectF = new RectF(mMillisecondLeft, topPaddingSize, mMillisecondLeft + mTimeBgSize, topPaddingSize + mTimeBgSize);
            }

            if (!isInitHasBackgroundTextBaseY) {
                initHasBackgroundTextBaseY(mSecondBgRectF);
            }
        }
    }

    private float getSuffixTextBaseLine(String suffixText, float topPaddingSize) {
        Rect tempRect = new Rect();
        mSuffixTextPaint.getTextBounds(suffixText, 0, suffixText.length(), tempRect);

        float ret;
        switch (mSuffixGravity) {
            case 0:
                // top
                ret = topPaddingSize - tempRect.top;
                break;
            default:
            case 1:
                // center
                ret = topPaddingSize + mTimeBgSize - mTimeBgSize / 2  + tempRect.height() / 2;
                break;
            case 2:
                // bottom
                ret = topPaddingSize + mTimeBgSize - tempRect.bottom;
                break;
        }

        return ret;
    }

    private void initHasBackgroundTextBaseY(RectF rectF) {
        // time text baseline
        Paint.FontMetrics timeFontMetrics = mTimeTextPaint.getFontMetrics();
        mTimeTextBaseY = rectF.top + (rectF.bottom - rectF.top - timeFontMetrics.bottom + timeFontMetrics.top) / 2 - timeFontMetrics.top - mTimeTextBottom;
        // initialize background division line y point
        mTimeBgDivisionLineYPos = rectF.centerY() + (mTimeBgDivisionLineSize == Utils.dp2px(mContext, DEFAULT_TIME_BG_DIVISION_LINE_SIZE) ? mTimeBgDivisionLineSize : mTimeBgDivisionLineSize / 2);
    }

    /**
     * initialize time text baseline
     * and
     * time background top padding
     */
    private float initTimeTextBaselineAndTimeBgTopPadding(int viewHeight, int viewPaddingTop, int viewPaddingBottom, int contentAllHeight) {
        float topPaddingSize;
        if (viewPaddingTop == viewPaddingBottom) {
            // center
            topPaddingSize = (viewHeight - contentAllHeight) / 2;
        } else {
            // padding top
            topPaddingSize = viewPaddingTop;
        }

        if (isShowDay && mSuffixDayTextWidth > 0) {
            mSuffixDayTextBaseline = getSuffixTextBaseLine(mSuffixDay, topPaddingSize);
        }

        if (isShowHour && mSuffixHourTextWidth > 0) {
            mSuffixHourTextBaseline = getSuffixTextBaseLine(mSuffixHour, topPaddingSize);
        }

        if (isShowMinute && mSuffixMinuteTextWidth > 0) {
            mSuffixMinuteTextBaseline = getSuffixTextBaseLine(mSuffixMinute, topPaddingSize);
        }

        if (mSuffixSecondTextWidth > 0) {
            mSuffixSecondTextBaseline = getSuffixTextBaseLine(mSuffixSecond, topPaddingSize);
        }

        if (isShowMillisecond && mSuffixMillisecondTextWidth > 0) {
            mSuffixMillisecondTextBaseline = getSuffixTextBaseLine(mSuffixMillisecond, topPaddingSize);
        }

        return topPaddingSize;
    }

    @Override
    public int getAllContentWidth() {
        float width = getAllContentWidthBase(mTimeBgSize);

        if (isShowDay) {
            if (isDayLargeNinetyNine) {
                Rect rect = new Rect();
                String tempDay = String.valueOf(mDay);
                mTimeTextPaint.getTextBounds(tempDay, 0, tempDay.length(), rect);
                mDayTimeBgWidth = rect.width() + (Utils.dp2px(mContext, 2) * 4);
                width += mDayTimeBgWidth;
            } else {
                mDayTimeBgWidth = mTimeBgSize;
                width += mTimeBgSize;
            }
        }

        return (int)Math.ceil(width);
    }

    @Override
    public int getAllContentHeight() {
        return (int) mTimeBgSize;
    }

    @Override
    public void onMeasure(View v, int viewWidth, int viewHeight, int allContentWidth, int allContentHeight) {
        float retTopPaddingSize = initTimeTextBaselineAndTimeBgTopPadding(viewHeight, v.getPaddingTop(), v.getPaddingBottom(), allContentHeight);
        mLeftPaddingSize = v.getPaddingLeft() == v.getPaddingRight() ?  (viewWidth - allContentWidth) / 2 : v.getPaddingLeft();
        initTimeBgRect(retTopPaddingSize);
    }

    @Override
    public void onDraw(Canvas canvas) {
        // show background
        float mHourLeft;
        float mMinuteLeft;
        float mSecondLeft;

        if (isShowDay) {
            // onDraw day background
            canvas.drawRoundRect(mDayBgRectF, mTimeBgRadius, mTimeBgRadius, mTimeTextBgPaint);
            if (isShowTimeBgDivisionLine) {
                // onDraw day background division line
                canvas.drawLine(mLeftPaddingSize, mTimeBgDivisionLineYPos, mLeftPaddingSize + mDayTimeBgWidth, mTimeBgDivisionLineYPos, mTimeTextBgDivisionLinePaint);
            }
            // onDraw day text
            canvas.drawText(Utils.formatNum(mDay), mDayBgRectF.centerX(), mTimeTextBaseY, mTimeTextPaint);
            if (mSuffixDayTextWidth > 0) {
                // onDraw day suffix
                canvas.drawText(mSuffixDay, mLeftPaddingSize + mDayTimeBgWidth + mSuffixDayLeftMargin, mSuffixDayTextBaseline, mSuffixTextPaint);
            }

            // hour left point
            mHourLeft = mLeftPaddingSize + mDayTimeBgWidth + mSuffixDayTextWidth + mSuffixDayLeftMargin + mSuffixDayRightMargin;
        } else {
            // hour left point
            mHourLeft = mLeftPaddingSize;
        }

        if (isShowHour) {
            // onDraw hour background
            canvas.drawRoundRect(mHourBgRectF, mTimeBgRadius, mTimeBgRadius, mTimeTextBgPaint);
            if (isShowTimeBgDivisionLine) {
                // onDraw hour background division line
                canvas.drawLine(mHourLeft, mTimeBgDivisionLineYPos, mTimeBgSize + mHourLeft, mTimeBgDivisionLineYPos, mTimeTextBgDivisionLinePaint);
            }
            // onDraw hour text
            canvas.drawText(Utils.formatNum(mHour), mHourBgRectF.centerX(), mTimeTextBaseY, mTimeTextPaint);
            if (mSuffixHourTextWidth > 0) {
                // onDraw hour suffix
                canvas.drawText(mSuffixHour, mHourLeft + mTimeBgSize + mSuffixHourLeftMargin, mSuffixHourTextBaseline, mSuffixTextPaint);
            }

            // minute left point
            mMinuteLeft = mHourLeft + mTimeBgSize + mSuffixHourTextWidth + mSuffixHourLeftMargin + mSuffixHourRightMargin;
        } else {
            // minute left point
            mMinuteLeft = mHourLeft;
        }

        if (isShowMinute) {
            // onDraw minute background
            canvas.drawRoundRect(mMinuteBgRectF, mTimeBgRadius, mTimeBgRadius, mTimeTextBgPaint);
            if (isShowTimeBgDivisionLine) {
                // onDraw minute background division line
                canvas.drawLine(mMinuteLeft, mTimeBgDivisionLineYPos, mTimeBgSize + mMinuteLeft, mTimeBgDivisionLineYPos, mTimeTextBgDivisionLinePaint);
            }
            // onDraw minute text
            canvas.drawText(Utils.formatNum(mMinute), mMinuteBgRectF.centerX(), mTimeTextBaseY, mTimeTextPaint);
            if (mSuffixMinuteTextWidth > 0) {
                // onDraw minute suffix
                canvas.drawText(mSuffixMinute, mMinuteLeft + mTimeBgSize + mSuffixMinuteLeftMargin, mSuffixMinuteTextBaseline, mSuffixTextPaint);
            }

            // second left point
            mSecondLeft = mMinuteLeft + mTimeBgSize + mSuffixMinuteTextWidth + mSuffixMinuteLeftMargin + mSuffixMinuteRightMargin;
        } else {
            // second left point
            mSecondLeft = mMinuteLeft;
        }

        if (isShowSecond) {
            // onDraw second background
            canvas.drawRoundRect(mSecondBgRectF, mTimeBgRadius, mTimeBgRadius, mTimeTextBgPaint);
            if (isShowTimeBgDivisionLine) {
                // onDraw second background division line
                canvas.drawLine(mSecondLeft, mTimeBgDivisionLineYPos, mTimeBgSize + mSecondLeft, mTimeBgDivisionLineYPos, mTimeTextBgDivisionLinePaint);
            }
            // onDraw second text
            canvas.drawText(Utils.formatNum(mSecond), mSecondBgRectF.centerX(), mTimeTextBaseY, mTimeTextPaint);
            if (mSuffixSecondTextWidth > 0) {
                // onDraw second suffix
                canvas.drawText(mSuffixSecond, mSecondLeft + mTimeBgSize + mSuffixSecondLeftMargin, mSuffixSecondTextBaseline, mSuffixTextPaint);
            }

            if (isShowMillisecond) {
                // millisecond left point
                float mMillisecondLeft = mSecondLeft + mTimeBgSize + mSuffixSecondTextWidth + mSuffixSecondLeftMargin + mSuffixSecondRightMargin;
                // onDraw millisecond background
                canvas.drawRoundRect(mMillisecondBgRectF, mTimeBgRadius, mTimeBgRadius, mTimeTextBgPaint);
                if (isShowTimeBgDivisionLine) {
                    // onDraw millisecond background division line
                    canvas.drawLine(mMillisecondLeft, mTimeBgDivisionLineYPos, mTimeBgSize + mMillisecondLeft, mTimeBgDivisionLineYPos, mTimeTextBgDivisionLinePaint);
                }
                // onDraw millisecond text
                canvas.drawText(Utils.formatMillisecond(mMillisecond), mMillisecondBgRectF.centerX(), mTimeTextBaseY, mTimeTextPaint);
                if (mSuffixMillisecondTextWidth > 0) {
                    // onDraw millisecond suffix
                    canvas.drawText(mSuffixMillisecond, mMillisecondLeft + mTimeBgSize + mSuffixMillisecondLeftMargin, mSuffixMillisecondTextBaseline, mSuffixTextPaint);
                }
            }
        }
    }

    public void setTimeBgSize(float size) {
        mTimeBgSize = Utils.dp2px(mContext, size);
    }


    public void setTimeBgColor(int textColor) {
        mTimeBgColor = textColor;
        mTimeTextBgPaint.setColor(mTimeBgColor);
    }

    public void setTimeBgRadius(float radius) {
        mTimeBgRadius = Utils.dp2px(mContext, radius);
    }

    public void setIsShowTimeBgDivisionLine(boolean isShow) {
        isShowTimeBgDivisionLine = isShow;
        if (isShowTimeBgDivisionLine) {
            initTimeTextBgDivisionLinePaint();
        } else {
            mTimeTextBgDivisionLinePaint = null;
        }
    }

    public void setTimeBgDivisionLineColor(int textColor) {
        if (null != mTimeTextBgDivisionLinePaint) {
            mTimeBgDivisionLineColor = textColor;
            mTimeTextBgDivisionLinePaint.setColor(mTimeBgDivisionLineColor);
        }
    }

    public void setTimeBgDivisionLineSize(float size) {
        if (null != mTimeTextBgDivisionLinePaint) {
            mTimeBgDivisionLineSize = size;
            mTimeTextBgDivisionLinePaint.setStrokeWidth(mTimeBgDivisionLineSize);
        }
    }

}
