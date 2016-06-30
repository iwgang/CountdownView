package cn.iwgang.countdownview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;

/**
 * Base Countdown
 * Created by iWgang on 16/6/18.
 * https://github.com/iwgang/CountdownView
 */
class BaseCountdown {
    private static final String DEFAULT_SUFFIX = ":";
    private static final float DEFAULT_SUFFIX_LR_MARGIN = 3; // dp

    public int mDay, mHour, mMinute, mSecond, mMillisecond;
    public boolean isShowDay, isShowHour, isShowMinute, isShowSecond, isShowMillisecond;
    public boolean mHasSetIsShowDay, mHasSetIsShowHour;

    protected Context mContext;
    protected String mSuffix, mSuffixDay, mSuffixHour, mSuffixMinute, mSuffixSecond, mSuffixMillisecond;
    protected float mSuffixDayTextWidth, mSuffixHourTextWidth, mSuffixMinuteTextWidth, mSuffixSecondTextWidth, mSuffixMillisecondTextWidth;
    protected boolean isDayLargeNinetyNine;
    protected Paint mTimeTextPaint, mSuffixTextPaint;
    protected float mLeftPaddingSize;
    protected float mSuffixDayLeftMargin, mSuffixDayRightMargin;
    protected float mSuffixSecondLeftMargin, mSuffixSecondRightMargin;
    protected float mSuffixHourLeftMargin, mSuffixHourRightMargin;
    protected float mSuffixMinuteLeftMargin, mSuffixMinuteRightMargin;
    protected float mSuffixMillisecondLeftMargin;
    protected float mSuffixDayTextBaseline, mSuffixHourTextBaseline, mSuffixMinuteTextBaseline, mSuffixSecondTextBaseline, mSuffixMillisecondTextBaseline;
    protected float mTimeTextWidth, mTimeTextHeight, mTimeTextBottom;
    protected int mSuffixGravity;

    private boolean hasSetSuffixDay, hasSetSuffixHour, hasSetSuffixMinute, hasSetSuffixSecond, hasSetSuffixMillisecond;
    private boolean hasCustomSomeSuffix;
    private float mSuffixLRMargin;
    private int mTimeTextColor;
    private float mTimeTextSize;
    private boolean isTimeTextBold;
    private int mSuffixTextColor;
    private float mSuffixTextSize;
    private boolean isSuffixTextBold;
    private float mDayTimeTextWidth;
    private float mTimeTextBaseline;

    private float mTempSuffixDayLeftMargin, mTempSuffixDayRightMargin;
    private float mTempSuffixHourLeftMargin, mTempSuffixHourRightMargin;
    private float mTempSuffixMinuteLeftMargin, mTempSuffixMinuteRightMargin;
    private float mTempSuffixSecondLeftMargin, mTempSuffixSecondRightMargin;
    private float mTempSuffixMillisecondLeftMargin;
    private String mTempSuffixMinute, mTempSuffixSecond;

    public void initStyleAttr(Context context, TypedArray ta) {
        mContext = context;
        isTimeTextBold = ta.getBoolean(R.styleable.CountdownView_isTimeTextBold, false);
        mTimeTextSize = ta.getDimension(R.styleable.CountdownView_timeTextSize, Utils.sp2px(mContext, 12));
        mTimeTextColor = ta.getColor(R.styleable.CountdownView_timeTextColor, 0xFF000000);
        isShowDay = ta.getBoolean(R.styleable.CountdownView_isShowDay, false);
        isShowHour = ta.getBoolean(R.styleable.CountdownView_isShowHour, false);
        isShowMinute = ta.getBoolean(R.styleable.CountdownView_isShowMinute, true);
        isShowSecond = ta.getBoolean(R.styleable.CountdownView_isShowSecond, true);
        isShowMillisecond = ta.getBoolean(R.styleable.CountdownView_isShowMillisecond, false);

        isSuffixTextBold = ta.getBoolean(R.styleable.CountdownView_isSuffixTextBold, false);
        mSuffixTextSize = ta.getDimension(R.styleable.CountdownView_suffixTextSize, Utils.sp2px(mContext, 12));
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

        mHasSetIsShowDay = ta.hasValue(R.styleable.CountdownView_isShowDay);
        mHasSetIsShowHour = ta.hasValue(R.styleable.CountdownView_isShowHour);

        initTempSuffixMargin();

        // time validate
        if (!isShowDay && !isShowHour && !isShowMinute) isShowSecond = true;
        if (!isShowSecond) isShowMillisecond = false;
    }

    public void initialize() {
        initSuffixBase();

        // initialize
        initPaint();
        initSuffix();

        // regular time data
        // pick one of two (minute and second)
        if (!isShowMinute && !isShowSecond) isShowSecond = true;
        if (!isShowSecond) isShowMillisecond = false;

        initTimeTextBaseInfo();
    }

    private void initSuffixBase() {
        hasSetSuffixDay = !TextUtils.isEmpty(mSuffixDay);
        hasSetSuffixHour = !TextUtils.isEmpty(mSuffixHour);
        hasSetSuffixMinute = !TextUtils.isEmpty(mSuffixMinute);
        hasSetSuffixSecond = !TextUtils.isEmpty(mSuffixSecond);
        hasSetSuffixMillisecond = !TextUtils.isEmpty(mSuffixMillisecond);

        if ((isShowDay && hasSetSuffixDay)
                || (isShowHour && hasSetSuffixHour)
                || (isShowMinute && hasSetSuffixMinute)
                || (isShowSecond && hasSetSuffixSecond)
                || (isShowMillisecond && hasSetSuffixMillisecond)) {
            hasCustomSomeSuffix = true;
        }

        mTempSuffixMinute = mSuffixMinute;
        mTempSuffixSecond = mSuffixSecond;
    }

    private void initTempSuffixMargin() {
        // temporarily saved suffix left and right margins
        mTempSuffixDayLeftMargin = mSuffixDayLeftMargin;
        mTempSuffixDayRightMargin = mSuffixDayRightMargin;
        mTempSuffixHourLeftMargin = mSuffixHourLeftMargin;
        mTempSuffixHourRightMargin = mSuffixHourRightMargin;
        mTempSuffixMinuteLeftMargin = mSuffixMinuteLeftMargin;
        mTempSuffixMinuteRightMargin = mSuffixMinuteRightMargin;
        mTempSuffixSecondLeftMargin = mSuffixSecondLeftMargin;
        mTempSuffixSecondRightMargin = mSuffixSecondRightMargin;
        mTempSuffixMillisecondLeftMargin = mSuffixMillisecondLeftMargin;
    }

    /**
     * initialize Paint
     */
    protected void initPaint() {
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
    }

    private void initSuffix() {
        boolean isSuffixNull = true;
        float mSuffixTextWidth = 0;
        float mDefSuffixTextWidth = mSuffixTextPaint.measureText(DEFAULT_SUFFIX);

        if (!TextUtils.isEmpty(mSuffix)) {
            isSuffixNull = false;
            mSuffixTextWidth = mSuffixTextPaint.measureText(mSuffix);
        }

        if (isShowDay) {
            if (hasSetSuffixDay) {
                mSuffixDayTextWidth = mSuffixTextPaint.measureText(mSuffixDay);
            } else {
                if (!isSuffixNull) {
                    mSuffixDay = mSuffix;
                    mSuffixDayTextWidth = mSuffixTextWidth;
                } else if (!hasCustomSomeSuffix) {
                    mSuffixDay = DEFAULT_SUFFIX;
                    mSuffixDayTextWidth = mDefSuffixTextWidth;
                }
            }
        } else {
            mSuffixDayTextWidth = 0;
        }

        if (isShowHour) {
            if (hasSetSuffixHour) {
                mSuffixHourTextWidth = mSuffixTextPaint.measureText(mSuffixHour);
            } else {
                if (!isSuffixNull) {
                    mSuffixHour = mSuffix;
                    mSuffixHourTextWidth = mSuffixTextWidth;
                } else if (!hasCustomSomeSuffix) {
                    mSuffixHour = DEFAULT_SUFFIX;
                    mSuffixHourTextWidth = mDefSuffixTextWidth;
                }
            }
        } else {
            mSuffixHourTextWidth = 0;
        }

        if (isShowMinute) {
            if (hasSetSuffixMinute) {
                mSuffixMinuteTextWidth = mSuffixTextPaint.measureText(mSuffixMinute);
            } else if (isShowSecond) {
                if (!isSuffixNull) {
                    mSuffixMinute = mSuffix;
                    mSuffixMinuteTextWidth = mSuffixTextWidth;
                } else if (!hasCustomSomeSuffix) {
                    mSuffixMinute = DEFAULT_SUFFIX;
                    mSuffixMinuteTextWidth = mDefSuffixTextWidth;
                }
            } else {
                mSuffixMinuteTextWidth = 0;
            }
        } else {
            mSuffixMinuteTextWidth = 0;
        }

        if (isShowSecond) {
            if (hasSetSuffixSecond) {
                mSuffixSecondTextWidth = mSuffixTextPaint.measureText(mSuffixSecond);
            } else if (isShowMillisecond) {
                if (!isSuffixNull) {
                    mSuffixSecond = mSuffix;
                    mSuffixSecondTextWidth = mSuffixTextWidth;
                } else if (!hasCustomSomeSuffix) {
                    mSuffixSecond = DEFAULT_SUFFIX;
                    mSuffixSecondTextWidth = mDefSuffixTextWidth;
                }
            } else {
                mSuffixSecondTextWidth = 0;
            }
        } else {
            mSuffixSecondTextWidth = 0;
        }

        if (isShowMillisecond && hasCustomSomeSuffix && hasSetSuffixMillisecond) {
            mSuffixMillisecondTextWidth = mSuffixTextPaint.measureText(mSuffixMillisecond);
        } else {
            mSuffixMillisecondTextWidth = 0;
        }

        initSuffixMargin();
    }

    /**
     * initialize suffix margin
     */
    private void initSuffixMargin() {
        int defSuffixLRMargin = Utils.dp2px(mContext, DEFAULT_SUFFIX_LR_MARGIN);
        boolean isSuffixLRMarginNull = true;

        if (mSuffixLRMargin >= 0) {
            isSuffixLRMarginNull = false;
        }

        if (isShowDay && mSuffixDayTextWidth > 0) {
            if (mSuffixDayLeftMargin < 0) {
                if (!isSuffixLRMarginNull) {
                    mSuffixDayLeftMargin = mSuffixLRMargin;
                } else {
                    mSuffixDayLeftMargin = defSuffixLRMargin;
                }
            }

            if (mSuffixDayRightMargin < 0) {
                if (!isSuffixLRMarginNull) {
                    mSuffixDayRightMargin = mSuffixLRMargin;
                } else {
                    mSuffixDayRightMargin = defSuffixLRMargin;
                }
            }
        } else {
            mSuffixDayLeftMargin = 0;
            mSuffixDayRightMargin = 0;
        }

        if (isShowHour && mSuffixHourTextWidth > 0) {
            if (mSuffixHourLeftMargin < 0) {
                if (!isSuffixLRMarginNull) {
                    mSuffixHourLeftMargin = mSuffixLRMargin;
                } else {
                    mSuffixHourLeftMargin = defSuffixLRMargin;
                }
            }

            if (mSuffixHourRightMargin < 0) {
                if (!isSuffixLRMarginNull) {
                    mSuffixHourRightMargin = mSuffixLRMargin;
                } else {
                    mSuffixHourRightMargin = defSuffixLRMargin;
                }
            }
        } else {
            mSuffixHourLeftMargin = 0;
            mSuffixHourRightMargin = 0;
        }

        if (isShowMinute && mSuffixMinuteTextWidth > 0) {
            if (mSuffixMinuteLeftMargin < 0) {
                if (!isSuffixLRMarginNull) {
                    mSuffixMinuteLeftMargin = mSuffixLRMargin;
                } else {
                    mSuffixMinuteLeftMargin = defSuffixLRMargin;
                }
            }

            if (isShowSecond) {
                if (mSuffixMinuteRightMargin < 0) {
                    if (!isSuffixLRMarginNull) {
                        mSuffixMinuteRightMargin = mSuffixLRMargin;
                    } else {
                        mSuffixMinuteRightMargin = defSuffixLRMargin;
                    }
                }
            } else {
                mSuffixMinuteRightMargin = 0;
            }
        } else {
            mSuffixMinuteLeftMargin = 0;
            mSuffixMinuteRightMargin = 0;
        }

        if (isShowSecond) {
            if (mSuffixSecondTextWidth > 0) {
                if (mSuffixSecondLeftMargin < 0) {
                    if (!isSuffixLRMarginNull) {
                        mSuffixSecondLeftMargin = mSuffixLRMargin;
                    } else {
                        mSuffixSecondLeftMargin = defSuffixLRMargin;
                    }
                }

                if (isShowMillisecond) {
                    if (mSuffixSecondRightMargin < 0) {
                        if (!isSuffixLRMarginNull) {
                            mSuffixSecondRightMargin = mSuffixLRMargin;
                        } else {
                            mSuffixSecondRightMargin = defSuffixLRMargin;
                        }
                    }
                } else {
                    mSuffixSecondRightMargin = 0;
                }
            } else {
                mSuffixSecondLeftMargin = 0;
                mSuffixSecondRightMargin = 0;
            }

            if (isShowMillisecond  && mSuffixMillisecondTextWidth > 0) {
                if (mSuffixMillisecondLeftMargin < 0) {
                    if (!isSuffixLRMarginNull) {
                        mSuffixMillisecondLeftMargin = mSuffixLRMargin;
                    } else {
                        mSuffixMillisecondLeftMargin = defSuffixLRMargin;
                    }
                }
            } else {
                mSuffixMillisecondLeftMargin = 0;
            }
        } else {
            mSuffixSecondLeftMargin = 0;
            mSuffixSecondRightMargin = 0;
            mSuffixMillisecondLeftMargin = 0;
        }
    }

    protected void initTimeTextBaseInfo() {
        // initialize time text width and height
        Rect rect = new Rect();
        mTimeTextPaint.getTextBounds("00", 0, 2, rect);
        mTimeTextWidth = rect.width();
        mTimeTextHeight = rect.height();
        mTimeTextBottom = rect.bottom;
    }

    private void initTimeTextBaseline(int viewHeight, int viewPaddingTop, int viewPaddingBottom) {
        if (viewPaddingTop == viewPaddingBottom) {
            // center
            mTimeTextBaseline = viewHeight / 2 + mTimeTextHeight / 2 - mTimeTextBottom;
        } else {
            // padding top
            mTimeTextBaseline = viewHeight - (viewHeight - viewPaddingTop) + mTimeTextHeight - mTimeTextBottom;
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
                ret = mTimeTextBaseline - mTimeTextHeight - tempRect.top;
                break;
            default:
            case 1:
                // center
                ret = mTimeTextBaseline - mTimeTextHeight / 2 + tempRect.height() / 2;
                break;
            case 2:
                // bottom
                ret = mTimeTextBaseline - tempRect.bottom;
                break;
        }

        return ret;
    }

    protected final float getAllContentWidthBase(float timeWidth) {
        float width = (mSuffixDayTextWidth + mSuffixHourTextWidth + mSuffixMinuteTextWidth + mSuffixSecondTextWidth + mSuffixMillisecondTextWidth);
        width += (mSuffixDayLeftMargin + mSuffixDayRightMargin + mSuffixHourLeftMargin + mSuffixHourRightMargin
                + mSuffixMinuteLeftMargin + mSuffixMinuteRightMargin + mSuffixSecondLeftMargin + mSuffixSecondRightMargin + mSuffixMillisecondLeftMargin);

        if (isShowHour) {
            width += timeWidth;
        }

        if (isShowMinute) {
            width += timeWidth;
        }

        if (isShowSecond) {
            width += timeWidth;
        }

        if (isShowMillisecond) {
            width += timeWidth;
        }
        return width;
    }

    /**
     * get all view width
     * @return all view width
     */
    public int getAllContentWidth() {
        float width = getAllContentWidthBase(mTimeTextWidth);

        if (isShowDay) {
            if (isDayLargeNinetyNine) {
                Rect rect = new Rect();
                String tempDay = String.valueOf(mDay);
                mTimeTextPaint.getTextBounds(tempDay, 0, tempDay.length(), rect);
                mDayTimeTextWidth = rect.width();
                width += mDayTimeTextWidth;
            } else {
                mDayTimeTextWidth = mTimeTextWidth;
                width += mTimeTextWidth;
            }
        }

        return (int)Math.ceil(width);
    }

    public int getAllContentHeight() {
        return (int) mTimeTextHeight;
    }

    public void onMeasure(View v, int viewWidth, int viewHeight, int allContentWidth, int allContentHeight) {
        initTimeTextBaseline(viewHeight, v.getPaddingTop(), v.getPaddingBottom());
        mLeftPaddingSize = v.getPaddingLeft() == v.getPaddingRight() ?  (viewWidth - allContentWidth) / 2 : v.getPaddingLeft();
    }

    public void onDraw(Canvas canvas) {
        // not background
        float mHourLeft;
        float mMinuteLeft;
        float mSecondLeft;

        if (isShowDay) {
            // draw day text
            canvas.drawText(Utils.formatNum(mDay), mLeftPaddingSize + mDayTimeTextWidth / 2, mTimeTextBaseline, mTimeTextPaint);
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
            canvas.drawText(Utils.formatNum(mHour), mHourLeft + mTimeTextWidth / 2, mTimeTextBaseline, mTimeTextPaint);
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
            canvas.drawText(Utils.formatNum(mMinute), mMinuteLeft + mTimeTextWidth / 2 , mTimeTextBaseline, mTimeTextPaint);
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

        if (isShowSecond) {
            // draw second text
            canvas.drawText(Utils.formatNum(mSecond), mSecondLeft + mTimeTextWidth / 2, mTimeTextBaseline, mTimeTextPaint);
            if (mSuffixSecondTextWidth > 0) {
                // draw second suffix
                canvas.drawText(mSuffixSecond, mSecondLeft + mTimeTextWidth + mSuffixSecondLeftMargin, mSuffixSecondTextBaseline, mSuffixTextPaint);
            }

            if (isShowMillisecond) {
                // millisecond left point
                float mMillisecondLeft = mSecondLeft + mTimeTextWidth + mSuffixSecondTextWidth + mSuffixSecondLeftMargin + mSuffixSecondRightMargin;
                // draw millisecond text
                canvas.drawText(Utils.formatMillisecond(mMillisecond), mMillisecondLeft + mTimeTextWidth / 2, mTimeTextBaseline, mTimeTextPaint);
                if (mSuffixMillisecondTextWidth > 0) {
                    // draw millisecond suffix
                    canvas.drawText(mSuffixMillisecond, mMillisecondLeft + mTimeTextWidth + mSuffixMillisecondLeftMargin, mSuffixMillisecondTextBaseline, mSuffixTextPaint);
                }
            }
        }
    }

    public boolean refTimeShow(boolean isShowDay, boolean isShowHour, boolean  isShowMinute, boolean isShowSecond, boolean isShowMillisecond) {
        if (!isShowSecond) isShowMillisecond = false;

        boolean isModCountdownInterval = false;
        if (this.isShowDay != isShowDay) {
            this.isShowDay = isShowDay;
            // reset day margins
            if (isShowDay) {
                mSuffixDayLeftMargin = mTempSuffixDayLeftMargin;
                mSuffixDayRightMargin = mTempSuffixDayRightMargin;
            }
        }

        if (this.isShowHour != isShowHour) {
            this.isShowHour = isShowHour;
            // reset hour margins
            if (isShowHour) {
                mSuffixHourLeftMargin = mTempSuffixHourLeftMargin;
                mSuffixHourRightMargin = mTempSuffixHourRightMargin;
            }
        }

        if (this.isShowMinute != isShowMinute) {
            this.isShowMinute = isShowMinute;
            // reset minute margins
            if (isShowMinute) {
                mSuffixMinuteLeftMargin = mTempSuffixMinuteLeftMargin;
                mSuffixMinuteRightMargin = mTempSuffixMinuteRightMargin;
                mSuffixMinute = mTempSuffixMinute;
            }
        }

        if (this.isShowSecond != isShowSecond) {
            this.isShowSecond = isShowSecond;
            isModCountdownInterval = true;

            // reset second margins
            if (isShowSecond) {
                mSuffixSecondLeftMargin = mTempSuffixSecondLeftMargin;
                mSuffixSecondRightMargin = mTempSuffixSecondRightMargin;
                mSuffixSecond = mTempSuffixSecond;
            } else {
                mSuffixMinute = mTempSuffixMinute;
            }

            mSuffixMinuteLeftMargin = mTempSuffixMinuteLeftMargin;
            mSuffixMinuteRightMargin = mTempSuffixMinuteRightMargin;
        }

        if (this.isShowMillisecond != isShowMillisecond) {
            this.isShowMillisecond = isShowMillisecond;
            isModCountdownInterval = true;

            // reset millisecond margins
            if (isShowMillisecond) {
                mSuffixMillisecondLeftMargin = mTempSuffixMillisecondLeftMargin;
            } else {
                mSuffixSecond = mTempSuffixSecond;
            }

            mSuffixSecondLeftMargin = mTempSuffixSecondLeftMargin;
            mSuffixSecondRightMargin = mTempSuffixSecondRightMargin;
        }

        return isModCountdownInterval;
    }

    public boolean handlerAutoShowTime() {
        boolean isReLayout = false;
        if (!mHasSetIsShowDay) {
            if (!isShowDay && mDay > 0) {
                // auto show day
                // judgement auto show hour
                if (!mHasSetIsShowHour) {
                    refTimeShow(true, true, isShowMinute, isShowSecond, isShowMillisecond);
                } else {
                    refTimeShow(true, isShowHour, isShowMinute, isShowSecond, isShowMillisecond);
                }
                isReLayout = true;
            } else if (isShowDay && mDay == 0) {
                // auto hide day
                refTimeShow(false, isShowHour, isShowMinute, isShowSecond, isShowMillisecond);
                isReLayout = true;
            } else {
                if (!mHasSetIsShowHour) {
                    if (!isShowHour && (mDay > 0 || mHour > 0)) {
                        // auto show hour
                        refTimeShow(isShowDay, true, isShowMinute, isShowSecond, isShowMillisecond);
                        isReLayout = true;
                    } else if (isShowHour && mDay == 0 && mHour == 0) {
                        // auto hide hour
                        refTimeShow(false, false, isShowMinute, isShowSecond, isShowMillisecond);
                        isReLayout = true;
                    }
                }
            }
        } else {
            if (!mHasSetIsShowHour) {
                if (!isShowHour && (mDay > 0 || mHour > 0)) {
                    // auto show hour
                    refTimeShow(isShowDay, true, isShowMinute, isShowSecond, isShowMillisecond);
                    isReLayout = true;
                } else if (isShowHour && mDay == 0 && mHour == 0) {
                    // auto hide hour
                    refTimeShow(isShowDay, false, isShowMinute, isShowSecond, isShowMillisecond);
                    isReLayout = true;
                }
            }
        }

        return isReLayout;
    }

    public boolean handlerDayLargeNinetyNine() {
        boolean isReLayout = false;
        if (isShowDay) {
            // handler large ninety nine
            if (!isDayLargeNinetyNine && mDay > 99) {
                isDayLargeNinetyNine = true;
                isReLayout = true;
            } else if (isDayLargeNinetyNine && mDay <= 99) {
                isDayLargeNinetyNine = false;
                isReLayout = true;
            }
        }
        return isReLayout;
    }

    public void setTimes(int day, int hour, int minute, int second, int millisecond) {
        mDay = day;
        mHour = hour;
        mMinute = minute;
        mSecond = second;
        mMillisecond = millisecond;
    }

    public void reLayout() {
        initSuffix();
        initTimeTextBaseInfo();
    }

    public void setTimeTextSize(float textSize) {
        if (textSize > 0) {
            mTimeTextSize = Utils.sp2px(mContext, textSize);
            mTimeTextPaint.setTextSize(mTimeTextSize);
        }
    }

    public void setTimeTextColor(int textColor) {
        mTimeTextColor = textColor;
        mTimeTextPaint.setColor(mTimeTextColor);
    }

    public void setTimeTextBold(boolean isBold) {
        isTimeTextBold = isBold;
        mTimeTextPaint.setFakeBoldText(isTimeTextBold);
    }

    public void setSuffixTextSize(float textSize) {
        if (textSize > 0) {
            mSuffixTextSize = Utils.sp2px(mContext, textSize);
            mSuffixTextPaint.setTextSize(mSuffixTextSize);
        }
    }

    public void setSuffixTextColor(int textColor) {
        mSuffixTextColor = textColor;
        mSuffixTextPaint.setColor(mSuffixTextColor);
    }

    public void setSuffixTextBold(boolean isBold) {
        isSuffixTextBold = isBold;
        mSuffixTextPaint.setFakeBoldText(isSuffixTextBold);
    }

    public void setSuffix(String suffix) {
        mSuffix = suffix;
        setSuffix(mSuffix, mSuffix, mSuffix, mSuffix, mSuffix);
    }

    public boolean setSuffix(String suffixDay, String suffixHour, String suffixMinute, String suffixSecond, String suffixMillisecond) {
        boolean isRef = false;

        if (null != suffixDay) {
            mSuffixDay = suffixDay;
            isRef = true;
        }

        if (null != suffixHour) {
            mSuffixHour = suffixHour;
            isRef = true;
        }

        if (null != suffixMinute) {
            mSuffixMinute = suffixMinute;
            isRef = true;
        }

        if (null != suffixSecond) {
            mSuffixSecond = suffixSecond;
            isRef = true;
        }

        if (null != suffixMillisecond) {
            mSuffixMillisecond = suffixMillisecond;
            isRef = true;
        }

        if (isRef) initSuffixBase();

        return isRef;
    }

    public void setSuffixLRMargin(float suffixLRMargin) {
        mSuffixLRMargin = Utils.dp2px(mContext, suffixLRMargin);
        setSuffixMargin(mSuffixDayLeftMargin, mSuffixDayLeftMargin,
                        mSuffixDayLeftMargin, mSuffixDayLeftMargin,
                        mSuffixDayLeftMargin, mSuffixDayLeftMargin,
                        mSuffixDayLeftMargin, mSuffixDayLeftMargin,
                        mSuffixDayLeftMargin);
    }

    public boolean setSuffixMargin(Float suffixDayMarginL, Float suffixDayMarginR,
                                Float suffixHourMarginL, Float suffixHourMarginR,
                                Float suffixMinuteMarginL, Float suffixMinuteMarginR,
                                Float suffixSecondMarginL, Float suffixSecondMarginR,
                                Float suffixMillisecondMarginL) {
        boolean isRef = false;

        if (null != suffixDayMarginL) {
            mSuffixDayLeftMargin = Utils.dp2px(mContext, suffixDayMarginL);
            isRef = true;
        }
        if (null != suffixDayMarginR) {
            mSuffixDayRightMargin = Utils.dp2px(mContext, suffixDayMarginR);
            isRef = true;
        }

        if (null != suffixHourMarginL) {
            mSuffixHourLeftMargin = Utils.dp2px(mContext, suffixHourMarginL);
            isRef = true;
        }
        if (null != suffixHourMarginR) {
            mSuffixHourRightMargin = Utils.dp2px(mContext, suffixHourMarginR);
            isRef = true;
        }

        if (null != suffixMinuteMarginL) {
            mSuffixMinuteLeftMargin = Utils.dp2px(mContext, suffixMinuteMarginL);
            isRef = true;
        }
        if (null != suffixMinuteMarginR) {
            mSuffixMinuteRightMargin = Utils.dp2px(mContext, suffixMinuteMarginR);
            isRef = true;
        }

        if (null != suffixSecondMarginL) {
            mSuffixSecondLeftMargin = Utils.dp2px(mContext, suffixSecondMarginL);
            isRef = true;
        }
        if (null != suffixSecondMarginR) {
            mSuffixSecondRightMargin = Utils.dp2px(mContext, suffixSecondMarginR);
            isRef = true;
        }

        if (null != suffixMillisecondMarginL) {
            mSuffixMillisecondLeftMargin = Utils.dp2px(mContext, suffixMillisecondMarginL);
            isRef = true;
        }


        if (isRef) initTempSuffixMargin();

        return isRef;
    }

    public void setSuffixGravity(int suffixGravity) {
        mSuffixGravity = suffixGravity;
    }

}
