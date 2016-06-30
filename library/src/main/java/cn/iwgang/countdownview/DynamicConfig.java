package cn.iwgang.countdownview;

/**
 * Dynamic configuration
 * Created by iWgang on 16/6/18.
 * https://github.com/iwgang/CountdownView
 */
public class DynamicConfig {
    private Builder mBuilder;

    private DynamicConfig(final Builder builder) {
        mBuilder = builder;
    }

    public Float getTimeTextSize() {
        return mBuilder.timeTextSize;
    }

    public Integer getTimeTextColor() {
        return mBuilder.timeTextColor;
    }

    public Boolean isTimeTextBold() {
        return mBuilder.isTimeTextBold;
    }

    public Float getSuffixTextSize() {
        return mBuilder.suffixTextSize;
    }

    public Integer getSuffixTextColor() {
        return mBuilder.suffixTextColor;
    }

    public Boolean isSuffixTimeTextBold() {
        return mBuilder.isSuffixTextBold;
    }

    public String getSuffix() {
        return mBuilder.suffix;
    }

    public String getSuffixDay() {
        return mBuilder.suffixDay;
    }

    public String getSuffixHour() {
        return mBuilder.suffixHour;
    }

    public String getSuffixMinute() {
        return mBuilder.suffixMinute;
    }

    public String getSuffixSecond() {
        return mBuilder.suffixSecond;
    }

    public String getSuffixMillisecond() {
        return mBuilder.suffixMillisecond;
    }

    public Integer getSuffixGravity() {
        return mBuilder.suffixGravity;
    }

    public Float getSuffixLRMargin() {
        return mBuilder.suffixLRMargin;
    }

    public Float getSuffixDayLeftMargin() {
        return mBuilder.suffixDayLeftMargin;
    }

    public Float getSuffixDayRightMargin() {
        return mBuilder.suffixDayRightMargin;
    }

    public Float getSuffixHourLeftMargin() {
        return mBuilder.suffixHourLeftMargin;
    }

    public Float getSuffixHourRightMargin() {
        return mBuilder.suffixHourRightMargin;
    }

    public Float getSuffixMinuteLeftMargin() {
        return mBuilder.suffixMinuteLeftMargin;
    }

    public Float getSuffixMinuteRightMargin() {
        return mBuilder.suffixMinuteRightMargin;
    }

    public Float getSuffixSecondLeftMargin() {
        return mBuilder.suffixSecondLeftMargin;
    }

    public Float getSuffixSecondRightMargin() {
        return mBuilder.suffixSecondRightMargin;
    }

    public Float getSuffixMillisecondLeftMargin() {
        return mBuilder.suffixMillisecondLeftMargin;
    }

    public Boolean isShowDay() {
        return mBuilder.isShowDay;
    }

    public Boolean isShowHour() {
        return mBuilder.isShowHour;
    }

    public Boolean isShowMinute() {
        return mBuilder.isShowMinute;
    }

    public Boolean isShowSecond() {
        return mBuilder.isShowSecond;
    }

    public Boolean isShowMillisecond() {
        return mBuilder.isShowMillisecond;
    }

    public BackgroundInfo getBackgroundInfo() {
        return mBuilder.backgroundInfo;
    }


    public static class Builder {
        private Float timeTextSize;
        private Integer timeTextColor;
        private Boolean isTimeTextBold;
        private Float suffixTextSize;
        private Integer suffixTextColor;
        private Integer suffixGravity;
        private Boolean isSuffixTextBold;
        private Boolean isShowDay;
        private Boolean isShowHour;
        private Boolean isShowMinute;
        private Boolean isShowSecond;
        private Boolean isShowMillisecond;
        private BackgroundInfo backgroundInfo;
        private String suffix, suffixDay, suffixHour, suffixMinute, suffixSecond, suffixMillisecond;
        private Float suffixLRMargin;
        private Float suffixDayLeftMargin, suffixDayRightMargin;
        private Float suffixSecondLeftMargin, suffixSecondRightMargin;
        private Float suffixHourLeftMargin, suffixHourRightMargin;
        private Float suffixMinuteLeftMargin, suffixMinuteRightMargin;
        private Float suffixMillisecondLeftMargin;

        /**
         * Set time text size
         * @param size text size（Unit sp）
         */
        public Builder setTimeTextSize(float size) {
            this.timeTextSize = size;
            return this;
        }

        /**
         * Set time text color
         * @param color text color
         */
        public Builder setTimeTextColor(int color) {
            this.timeTextColor = color;
            return this;
        }

        /**
         * Set time text bold
         * @param isBold true bold, false cancel bold
         */
        public Builder setTimeTextBold(boolean isBold) {
            this.isTimeTextBold = isBold;
            return this;
        }

        /**
         * Set suffix text size
         * @param size text size（Unit sp）
         */
        public Builder setSuffixTextSize(float size) {
            this.suffixTextSize = size;
            return this;
        }

        /**
         * Set suffix text color
         * @param color text color
         */
        public Builder setSuffixTextColor(int color) {
            this.suffixTextColor = color;
            return this;
        }

        /**
         * Set suffix text bold
         * @param isBold true bold, false cancel bold
         */
        public Builder setSuffixTextBold(boolean isBold) {
            this.isSuffixTextBold = isBold;
            return this;
        }

        /**
         * Set suffix (All)
         * @param suffix suffix text
         */
        public Builder setSuffix(String suffix) {
            this.suffix = suffix;
            return this;
        }

        /**
         * Set day suffix
         * @param suffix suffix text
         */
        public Builder setSuffixDay(String suffix) {
            this.suffixDay = suffix;
            return this;
        }

        /**
         * Set hour suffix
         * @param suffix suffix text
         */
        public Builder setSuffixHour(String suffix) {
            this.suffixHour = suffix;
            return this;
        }

        /**
         * Set minute suffix
         * @param suffix suffix text
         */
        public Builder setSuffixMinute(String suffix) {
            this.suffixMinute = suffix;
            return this;
        }

        /**
         * Set second suffix
         * @param suffix suffix text
         */
        public Builder setSuffixSecond(String suffix) {
            this.suffixSecond = suffix;
            return this;
        }

        /**
         * Set millisecond suffix
         * @param suffix suffix text
         */
        public Builder setSuffixMillisecond(String suffix) {
            this.suffixMillisecond = suffix;
            return this;
        }

        /**
         * Set suffix margin (All)
         * @param margin margin（Unit dp）
         */
        public Builder setSuffixLRMargin(float margin) {
            this.suffixLRMargin = margin;
            return this;
        }

        public Builder setSuffixDayLeftMargin(float margin) {
            this.suffixDayLeftMargin = margin;
            return this;
        }

        public Builder setSuffixDayRightMargin(float margin) {
            this.suffixDayRightMargin = margin;
            return this;
        }

        public Builder setSuffixHourLeftMargin(float margin) {
            this.suffixHourLeftMargin = margin;
            return this;
        }

        public Builder setSuffixHourRightMargin(float margin) {
            this.suffixHourRightMargin = margin;
            return this;
        }

        public Builder setSuffixMinuteLeftMargin(float margin) {
            this.suffixMinuteLeftMargin = margin;
            return this;
        }

        public Builder setSuffixMinuteRightMargin(float margin) {
            this.suffixMinuteRightMargin = margin;
            return this;
        }

        public Builder setSuffixSecondLeftMargin(float margin) {
            this.suffixSecondLeftMargin = margin;
            return this;
        }

        public Builder setSuffixSecondRightMargin(float margin) {
            this.suffixSecondRightMargin = margin;
            return this;
        }

        public Builder setSuffixMillisecondLeftMargin(float margin) {
            this.suffixMillisecondLeftMargin = margin;
            return this;
        }

        /**
         * Set suffix gravity
         * @param suffixGravity See {@link SuffixGravity}
         */
        public Builder setSuffixGravity(int suffixGravity) {
            this.suffixGravity = suffixGravity;
            return this;
        }

        /**
         * Set day show or hide
         * @param isShowDay true show, false hide
         */
        public Builder setShowDay(Boolean isShowDay) {
            this.isShowDay = isShowDay;
            return this;
        }

        /**
         * Set hour show or hide
         * @param isShowHour true show, false hide
         */
        public Builder setShowHour(Boolean isShowHour) {
            this.isShowHour = isShowHour;
            return this;
        }

        /**
         * Set minute show or hide
         * @param isShowMinute true show, false hide
         */
        public Builder setShowMinute(Boolean isShowMinute) {
            this.isShowMinute = isShowMinute;
            return this;
        }

        /**
         * Set second show or hide
         * @param isShowSecond true show, false hide
         */
        public Builder setShowSecond(Boolean isShowSecond) {
            this.isShowSecond = isShowSecond;
            return this;
        }

        /**
         * Set millisecond show or hide
         * @param isShowMillisecond true show, false hide
         */
        public Builder setShowMillisecond(Boolean isShowMillisecond) {
            this.isShowMillisecond = isShowMillisecond;
            return this;
        }

        /**
         * Set background info
         * @param backgroundInfo BackgroundInfo
         */
        public Builder setBackgroundInfo(BackgroundInfo backgroundInfo) {
            this.backgroundInfo = backgroundInfo;
            return this;
        }

        private void checkData() {
            if (null != timeTextSize && timeTextSize <= 0) timeTextSize = null;

            if (null != suffixTextSize && suffixTextSize <= 0) suffixTextSize = null;

            if (null != backgroundInfo && !backgroundInfo.hasData) {
                backgroundInfo = null;
            }
            if (null != backgroundInfo) {
                Boolean isShowTimeBgDivisionLine = backgroundInfo.isShowTimeBgDivisionLine();
                if (null == isShowTimeBgDivisionLine || !isShowTimeBgDivisionLine) {
                    backgroundInfo.setDivisionLineColor(null);
                    backgroundInfo.setDivisionLineSize(null);
                }

                Boolean isShowTimeBgBorder = backgroundInfo.isShowTimeBgBorder();
                if (null == isShowTimeBgBorder || !isShowTimeBgBorder) {
                    backgroundInfo.setBorderColor(null);
                    backgroundInfo.setBorderRadius(null);
                    backgroundInfo.setBorderSize(null);
                }

                if (null != backgroundInfo.getSize() && backgroundInfo.getSize() <= 0) {
                    backgroundInfo.setSize(null);
                }
            }

            if (null != suffixGravity && (suffixGravity < 0 || suffixGravity > 2)) suffixGravity = null;
        }

        public DynamicConfig build() {
            checkData();
            return new DynamicConfig(this);
        }

    }


    public static class BackgroundInfo {
        private boolean hasData = false;
        private Integer color;
        private Float size;
        private Float radius;
        private Boolean isShowDivisionLine;
        private Integer divisionLineColor;
        private Float divisionLineSize;
        private Boolean isShowBorder;
        private Integer borderColor;
        private Float borderRadius;
        private Float borderSize;

        public BackgroundInfo setColor(Integer color) {
            hasData = true;
            this.color = color;
            return this;
        }

        public BackgroundInfo setSize(Float size) {
            hasData = true;
            this.size = size;
            return this;
        }

        public BackgroundInfo setRadius(Float radius) {
            hasData = true;
            this.radius = radius;
            return this;
        }

        public BackgroundInfo setShowTimeBgDivisionLine(Boolean showTimeBgDivisionLine) {
            hasData = true;
            isShowDivisionLine = showTimeBgDivisionLine;
            return this;
        }

        public BackgroundInfo setDivisionLineSize(Float divisionLineSize) {
            hasData = true;
            this.divisionLineSize = divisionLineSize;
            return this;
        }

        public BackgroundInfo setDivisionLineColor(Integer divisionLineColor) {
            hasData = true;
            this.divisionLineColor = divisionLineColor;
            return this;
        }

        public BackgroundInfo setShowTimeBgBorder(Boolean showTimeBgBorder) {
            hasData = true;
            isShowBorder = showTimeBgBorder;
            return this;
        }

        public BackgroundInfo setBorderSize(Float borderSize) {
            hasData = true;
            this.borderSize = borderSize;
            return this;
        }

        public BackgroundInfo setBorderColor(Integer borderColor) {
            hasData = true;
            this.borderColor = borderColor;
            return this;
        }

        public BackgroundInfo setBorderRadius(Float borderRadius) {
            hasData = true;
            this.borderRadius = borderRadius;
            return this;
        }

        public Integer getColor() {
            return color;
        }

        public Integer getDivisionLineColor() {
            return divisionLineColor;
        }

        public Float getDivisionLineSize() {
            return divisionLineSize;
        }

        public Boolean isShowTimeBgDivisionLine() {
            return isShowDivisionLine;
        }

        public Float getRadius() {
            return radius;
        }

        public Float getSize() {
            return size;
        }

        public Boolean isShowTimeBgBorder() {
            return isShowBorder;
        }

        public Integer getBorderColor() {
            return borderColor;
        }

        public Float getBorderSize() {
            return borderSize;
        }

        public Float getBorderRadius() {
            return borderRadius;
        }
    }

    public static class SuffixGravity {
        public static final int TOP = 0;
        public static final int CENTER = 1;
        public static final int BOTTOM = 2;

    }

}
