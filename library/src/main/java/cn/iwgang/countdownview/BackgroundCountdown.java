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
    private static final float DEFAULT_TIME_BG_BORDER_SIZE = 1f; // dp

    private boolean isDrawBg;
    private boolean isShowTimeBgDivisionLine;
    private int mTimeBgDivisionLineColor;
    private float mTimeBgDivisionLineSize;
    private float mTimeBgRadius;
    private float mTimeBgSize;
    private int mTimeBgColor;
    private Paint mTimeBgPaint, mTimeBgBorderPaint, mTimeBgDivisionLinePaint;
    private float mDefSetTimeBgSize;
    private float mDayTimeBgWidth;
    private RectF mDayBgRectF, mHourBgRectF, mMinuteBgRectF, mSecondBgRectF, mMillisecondBgRectF;
    private RectF mDayBgBorderRectF, mHourBgBorderRectF, mMinuteBgBorderRectF, mSecondBgBorderRectF, mMillisecondBgBorderRectF;
    private float mTimeBgDivisionLineYPos;
    private float mTimeTextBaseY;
    private boolean isShowTimeBgBorder;
    private float mTimeBgBorderSize;
    private float mTimeBgBorderRadius;
    private int mTimeBgBorderColor;

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
        mTimeBgBorderSize = ta.getDimension(R.styleable.CountdownView_timeBgBorderSize, Utils.dp2px(context, DEFAULT_TIME_BG_BORDER_SIZE));
        mTimeBgBorderRadius = ta.getDimension(R.styleable.CountdownView_timeBgBorderRadius, 0);
        mTimeBgBorderColor = ta.getColor(R.styleable.CountdownView_timeBgBorderColor, 0xFF000000);
        isShowTimeBgBorder = ta.getBoolean(R.styleable.CountdownView_isShowTimeBgBorder, false);

        isDrawBg = ta.hasValue(R.styleable.CountdownView_timeBgColor) || !isShowTimeBgBorder;
    }

    @Override
    protected void initPaint() {
        super.initPaint();
        // time background
        mTimeBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTimeBgPaint.setStyle(Paint.Style.FILL);
        mTimeBgPaint.setColor(mTimeBgColor);

        // time background border
        if (isShowTimeBgBorder) {
            initTimeBgBorderPaint();
        }

        // time background division line
        if (isShowTimeBgDivisionLine) {
            initTimeTextBgDivisionLinePaint();
        }
    }

    private void initTimeBgBorderPaint() {
        if (null != mTimeBgBorderPaint) return;

        mTimeBgBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTimeBgBorderPaint.setColor(mTimeBgBorderColor);
        if (!isDrawBg) {
            mTimeBgBorderPaint.setStrokeWidth(mTimeBgBorderSize);
            mTimeBgBorderPaint.setStyle(Paint.Style.STROKE);
        }
    }

    private void initTimeTextBgDivisionLinePaint() {
        if (null != mTimeBgDivisionLinePaint) return;

        mTimeBgDivisionLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTimeBgDivisionLinePaint.setColor(mTimeBgDivisionLineColor);
        mTimeBgDivisionLinePaint.setStrokeWidth(mTimeBgDivisionLineSize);
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
            // initialize day background and border rectF
            if (isShowTimeBgBorder) {
                mDayBgBorderRectF = new RectF(mLeftPaddingSize, topPaddingSize, mLeftPaddingSize + mDayTimeBgWidth + (mTimeBgBorderSize * 2), topPaddingSize + mTimeBgSize + (mTimeBgBorderSize * 2));
                mDayBgRectF = new RectF(mLeftPaddingSize + mTimeBgBorderSize, topPaddingSize + mTimeBgBorderSize, mLeftPaddingSize + mDayTimeBgWidth + mTimeBgBorderSize, topPaddingSize + mTimeBgSize + mTimeBgBorderSize);
            } else {
                mDayBgRectF = new RectF(mLeftPaddingSize, topPaddingSize, mLeftPaddingSize + mDayTimeBgWidth, topPaddingSize + mTimeBgSize);
            }
            // hour left point
            mHourLeft = mLeftPaddingSize + mDayTimeBgWidth + mSuffixDayTextWidth + mSuffixDayLeftMargin + mSuffixDayRightMargin + (mTimeBgBorderSize * 2);

            if (!isShowHour && !isShowMinute && !isShowSecond) {
                isInitHasBackgroundTextBaseY = true;
                initHasBackgroundTextBaseY(mDayBgRectF);
            }
        } else {
            // hour left point
            mHourLeft = mLeftPaddingSize;
        }

        if (isShowHour) {
            // initialize hour background border rectF
            if (isShowTimeBgBorder) {
                mHourBgBorderRectF = new RectF(mHourLeft, topPaddingSize, mHourLeft + mTimeBgSize + (mTimeBgBorderSize * 2), topPaddingSize + mTimeBgSize + (mTimeBgBorderSize * 2));
                mHourBgRectF = new RectF(mHourLeft + mTimeBgBorderSize, topPaddingSize + mTimeBgBorderSize, mHourLeft + mTimeBgSize + mTimeBgBorderSize, topPaddingSize + mTimeBgSize + mTimeBgBorderSize);
            } else {
                mHourBgRectF = new RectF(mHourLeft, topPaddingSize, mHourLeft + mTimeBgSize, topPaddingSize + mTimeBgSize);
            }
            // minute left point
            mMinuteLeft = mHourLeft + mTimeBgSize + mSuffixHourTextWidth + mSuffixHourLeftMargin + mSuffixHourRightMargin + (mTimeBgBorderSize * 2);

            if (!isInitHasBackgroundTextBaseY) {
                isInitHasBackgroundTextBaseY = true;
                initHasBackgroundTextBaseY(mHourBgRectF);
            }
        } else {
            // minute left point
            mMinuteLeft = mHourLeft;
        }

        if (isShowMinute) {
            // initialize minute background border rectF
            if (isShowTimeBgBorder) {
                mMinuteBgBorderRectF = new RectF(mMinuteLeft, topPaddingSize, mMinuteLeft + mTimeBgSize + (mTimeBgBorderSize * 2), topPaddingSize + mTimeBgSize + (mTimeBgBorderSize * 2));
                mMinuteBgRectF = new RectF(mMinuteLeft + mTimeBgBorderSize, topPaddingSize + mTimeBgBorderSize, mMinuteLeft + mTimeBgSize + mTimeBgBorderSize, topPaddingSize + mTimeBgSize + mTimeBgBorderSize);
            } else {
                mMinuteBgRectF = new RectF(mMinuteLeft, topPaddingSize, mMinuteLeft + mTimeBgSize, topPaddingSize + mTimeBgSize);
            }
            // second left point
            mSecondLeft = mMinuteLeft + mTimeBgSize + mSuffixMinuteTextWidth + mSuffixMinuteLeftMargin + mSuffixMinuteRightMargin + (mTimeBgBorderSize * 2);

            if (!isInitHasBackgroundTextBaseY) {
                isInitHasBackgroundTextBaseY = true;
                initHasBackgroundTextBaseY(mMinuteBgRectF);
            }
        } else {
            // second left point
            mSecondLeft = mMinuteLeft;
        }

        if (isShowSecond) {
            // initialize second background border rectF
            if (isShowTimeBgBorder) {
                mSecondBgBorderRectF = new RectF(mSecondLeft, topPaddingSize, mSecondLeft + mTimeBgSize + (mTimeBgBorderSize * 2), topPaddingSize + mTimeBgSize + (mTimeBgBorderSize * 2));
                mSecondBgRectF = new RectF(mSecondLeft + mTimeBgBorderSize, topPaddingSize + mTimeBgBorderSize, mSecondLeft + mTimeBgSize + mTimeBgBorderSize, topPaddingSize + mTimeBgSize + mTimeBgBorderSize);
            } else {
                mSecondBgRectF = new RectF(mSecondLeft, topPaddingSize, mSecondLeft + mTimeBgSize, topPaddingSize + mTimeBgSize);
            }

            if (isShowMillisecond) {
                // millisecond left point
                float mMillisecondLeft = mSecondLeft + mTimeBgSize + mSuffixSecondTextWidth + mSuffixSecondLeftMargin + mSuffixSecondRightMargin + (mTimeBgBorderSize * 2);

                // initialize millisecond background border rectF
                if (isShowTimeBgBorder) {
                    mMillisecondBgBorderRectF = new RectF(mMillisecondLeft, topPaddingSize, mMillisecondLeft + mTimeBgSize + (mTimeBgBorderSize * 2), topPaddingSize + mTimeBgSize + (mTimeBgBorderSize * 2));
                    mMillisecondBgRectF = new RectF(mMillisecondLeft + mTimeBgBorderSize, topPaddingSize + mTimeBgBorderSize, mMillisecondLeft + mTimeBgSize + mTimeBgBorderSize, topPaddingSize + mTimeBgSize + mTimeBgBorderSize);
                } else {
                    mMillisecondBgRectF = new RectF(mMillisecondLeft, topPaddingSize, mMillisecondLeft + mTimeBgSize, topPaddingSize + mTimeBgSize);
                }
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
                ret = topPaddingSize + mTimeBgSize - mTimeBgSize / 2  + tempRect.height() / 2 + mTimeBgBorderSize;
                break;
            case 2:
                // bottom
                ret = topPaddingSize + mTimeBgSize - tempRect.bottom + (mTimeBgBorderSize * 2);
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
        float width = getAllContentWidthBase(mTimeBgSize + (mTimeBgBorderSize * 2));

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

            width += (mTimeBgBorderSize * 2);
        }

        return (int)Math.ceil(width);
    }

    @Override
    public int getAllContentHeight() {
        return (int) (mTimeBgSize + (mTimeBgBorderSize * 2));
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
            // draw day background border
            if (isShowTimeBgBorder) {
                canvas.drawRoundRect(mDayBgBorderRectF, mTimeBgBorderRadius, mTimeBgBorderRadius, mTimeBgBorderPaint);
            }
            if (isDrawBg) {
                // draw day background
                canvas.drawRoundRect(mDayBgRectF, mTimeBgRadius, mTimeBgRadius, mTimeBgPaint);
                if (isShowTimeBgDivisionLine) {
                    // draw day background division line
                    canvas.drawLine(mLeftPaddingSize + mTimeBgBorderSize, mTimeBgDivisionLineYPos, mLeftPaddingSize + mDayTimeBgWidth + mTimeBgBorderSize, mTimeBgDivisionLineYPos, mTimeBgDivisionLinePaint);
                }
            }
            // draw day text
            canvas.drawText(Utils.formatNum(mDay), mDayBgRectF.centerX(), mTimeTextBaseY, mTimeTextPaint);
            if (mSuffixDayTextWidth > 0) {
                // draw day suffix
                canvas.drawText(mSuffixDay, mLeftPaddingSize + mDayTimeBgWidth + mSuffixDayLeftMargin + (mTimeBgBorderSize * 2), mSuffixDayTextBaseline, mSuffixTextPaint);
            }

            // hour left point
            mHourLeft = mLeftPaddingSize + mDayTimeBgWidth + mSuffixDayTextWidth + mSuffixDayLeftMargin + mSuffixDayRightMargin + (mTimeBgBorderSize * 2);
        } else {
            // hour left point
            mHourLeft = mLeftPaddingSize;
        }

        if (isShowHour) {
            // draw hour background border
            if (isShowTimeBgBorder) {
                canvas.drawRoundRect(mHourBgBorderRectF, mTimeBgBorderRadius, mTimeBgBorderRadius, mTimeBgBorderPaint);
            }
            if (isDrawBg) {
                // draw hour background
                canvas.drawRoundRect(mHourBgRectF, mTimeBgRadius, mTimeBgRadius, mTimeBgPaint);
                if (isShowTimeBgDivisionLine) {
                    // draw hour background division line
                    canvas.drawLine(mHourLeft + mTimeBgBorderSize, mTimeBgDivisionLineYPos, mTimeBgSize + mHourLeft + mTimeBgBorderSize, mTimeBgDivisionLineYPos, mTimeBgDivisionLinePaint);
                }
            }
            // draw hour text
            canvas.drawText(Utils.formatNum(mHour), mHourBgRectF.centerX(), mTimeTextBaseY, mTimeTextPaint);
            if (mSuffixHourTextWidth > 0) {
                // draw hour suffix
                canvas.drawText(mSuffixHour, mHourLeft + mTimeBgSize + mSuffixHourLeftMargin + (mTimeBgBorderSize * 2), mSuffixHourTextBaseline, mSuffixTextPaint);
            }

            // minute left point
            mMinuteLeft = mHourLeft + mTimeBgSize + mSuffixHourTextWidth + mSuffixHourLeftMargin + mSuffixHourRightMargin + (mTimeBgBorderSize * 2);
        } else {
            // minute left point
            mMinuteLeft = mHourLeft;
        }

        if (isShowMinute) {
            // draw minute background border
            if (isShowTimeBgBorder) {
                canvas.drawRoundRect(mMinuteBgBorderRectF, mTimeBgBorderRadius, mTimeBgBorderRadius, mTimeBgBorderPaint);
            }
            if (isDrawBg) {
                // draw minute background
                canvas.drawRoundRect(mMinuteBgRectF, mTimeBgRadius, mTimeBgRadius, mTimeBgPaint);
                if (isShowTimeBgDivisionLine) {
                    // draw minute background division line
                    canvas.drawLine(mMinuteLeft + mTimeBgBorderSize, mTimeBgDivisionLineYPos, mTimeBgSize + mMinuteLeft + mTimeBgBorderSize, mTimeBgDivisionLineYPos, mTimeBgDivisionLinePaint);
                }
            }
            // draw minute text
            canvas.drawText(Utils.formatNum(mMinute), mMinuteBgRectF.centerX(), mTimeTextBaseY, mTimeTextPaint);
            if (mSuffixMinuteTextWidth > 0) {
                // draw minute suffix
                canvas.drawText(mSuffixMinute, mMinuteLeft + mTimeBgSize + mSuffixMinuteLeftMargin + (mTimeBgBorderSize * 2), mSuffixMinuteTextBaseline, mSuffixTextPaint);
            }

            // second left point
            mSecondLeft = mMinuteLeft + mTimeBgSize + mSuffixMinuteTextWidth + mSuffixMinuteLeftMargin + mSuffixMinuteRightMargin + (mTimeBgBorderSize * 2);
        } else {
            // second left point
            mSecondLeft = mMinuteLeft;
        }

        if (isShowSecond) {
            // draw second background border
            if (isShowTimeBgBorder) {
                canvas.drawRoundRect(mSecondBgBorderRectF, mTimeBgBorderRadius, mTimeBgBorderRadius, mTimeBgBorderPaint);
            }
            if (isDrawBg) {
                // draw second background
                canvas.drawRoundRect(mSecondBgRectF, mTimeBgRadius, mTimeBgRadius, mTimeBgPaint);
                if (isShowTimeBgDivisionLine) {
                    // draw second background division line
                    canvas.drawLine(mSecondLeft + mTimeBgBorderSize, mTimeBgDivisionLineYPos, mTimeBgSize + mSecondLeft + mTimeBgBorderSize, mTimeBgDivisionLineYPos, mTimeBgDivisionLinePaint);
                }
            }
            // draw second text
            canvas.drawText(Utils.formatNum(mSecond), mSecondBgRectF.centerX(), mTimeTextBaseY, mTimeTextPaint);
            if (mSuffixSecondTextWidth > 0) {
                // draw second suffix
                canvas.drawText(mSuffixSecond, mSecondLeft + mTimeBgSize + mSuffixSecondLeftMargin + (mTimeBgBorderSize * 2), mSuffixSecondTextBaseline, mSuffixTextPaint);
            }

            if (isShowMillisecond) {
                // draw millisecond background border
                if (isShowTimeBgBorder) {
                    canvas.drawRoundRect(mMillisecondBgBorderRectF, mTimeBgBorderRadius, mTimeBgBorderRadius, mTimeBgBorderPaint);
                }
                // millisecond left point
                float mMillisecondLeft = mSecondLeft + mTimeBgSize + mSuffixSecondTextWidth + mSuffixSecondLeftMargin + mSuffixSecondRightMargin + (mTimeBgBorderSize * 2);
                if (isDrawBg) {
                    // draw millisecond background
                    canvas.drawRoundRect(mMillisecondBgRectF, mTimeBgRadius, mTimeBgRadius, mTimeBgPaint);
                    if (isShowTimeBgDivisionLine) {
                        // draw millisecond background division line
                        canvas.drawLine(mMillisecondLeft + mTimeBgBorderSize, mTimeBgDivisionLineYPos, mTimeBgSize + mMillisecondLeft + mTimeBgBorderSize, mTimeBgDivisionLineYPos, mTimeBgDivisionLinePaint);
                    }
                }
                // draw millisecond text
                canvas.drawText(Utils.formatMillisecond(mMillisecond), mMillisecondBgRectF.centerX(), mTimeTextBaseY, mTimeTextPaint);
                if (mSuffixMillisecondTextWidth > 0) {
                    // draw millisecond suffix
                    canvas.drawText(mSuffixMillisecond, mMillisecondLeft + mTimeBgSize + mSuffixMillisecondLeftMargin + (mTimeBgBorderSize * 2), mSuffixMillisecondTextBaseline, mSuffixTextPaint);
                }
            }
        }
    }

    public void setTimeBgSize(float size) {
        mTimeBgSize = Utils.dp2px(mContext, size);
    }


    public void setTimeBgColor(int color) {
        mTimeBgColor = color;
        mTimeBgPaint.setColor(mTimeBgColor);
        if (color == Color.TRANSPARENT && isShowTimeBgBorder) {
            isDrawBg = false;
            mTimeBgBorderPaint.setStrokeWidth(mTimeBgBorderSize);
            mTimeBgBorderPaint.setStyle(Paint.Style.STROKE);
        } else {
            isDrawBg = true;
            if (isShowTimeBgBorder) {
                mTimeBgBorderPaint.setStrokeWidth(0);
                mTimeBgBorderPaint.setStyle(Paint.Style.FILL);
            }
        }
    }

    public void setTimeBgRadius(float radius) {
        mTimeBgRadius = Utils.dp2px(mContext, radius);
    }

    public void setIsShowTimeBgDivisionLine(boolean isShow) {
        isShowTimeBgDivisionLine = isShow;
        if (isShowTimeBgDivisionLine) {
            initTimeTextBgDivisionLinePaint();
        } else {
            mTimeBgDivisionLinePaint = null;
        }
    }

    public void setTimeBgDivisionLineColor(int color) {
        mTimeBgDivisionLineColor = color;
        if (null != mTimeBgDivisionLinePaint) {
            mTimeBgDivisionLinePaint.setColor(mTimeBgDivisionLineColor);
        }
    }

    public void setTimeBgDivisionLineSize(float size) {
        mTimeBgDivisionLineSize = Utils.dp2px(mContext, size);
        if (null != mTimeBgDivisionLinePaint) {
            mTimeBgDivisionLinePaint.setStrokeWidth(mTimeBgDivisionLineSize);
        }
    }

    public void setIsShowTimeBgBorder(boolean isShow) {
        isShowTimeBgBorder = isShow;
        if (isShowTimeBgBorder) {
            initTimeBgBorderPaint();
        } else {
            mTimeBgBorderPaint = null;
            mTimeBgBorderSize = 0;
        }
    }

    public void setTimeBgBorderColor(int color) {
        mTimeBgBorderColor = color;
        if (null != mTimeBgBorderPaint) {
            mTimeBgBorderPaint.setColor(mTimeBgBorderColor);
        }
    }

    public void setTimeBgBorderSize(float size) {
        mTimeBgBorderSize = Utils.dp2px(mContext, size);
        if (null != mTimeBgBorderPaint && !isDrawBg) {
            mTimeBgBorderPaint.setStrokeWidth(mTimeBgBorderSize);
            mTimeBgBorderPaint.setStyle(Paint.Style.STROKE);
        }
    }

    public void setTimeBgBorderRadius(float size) {
        mTimeBgBorderRadius = Utils.dp2px(mContext, size);
    }

}
