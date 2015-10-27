# CountdownView
Android Countdown Widget，Use canvas draw，Supports Multiple show styles

### screenshot
![](https://raw.githubusercontent.com/iwgang/CountdownView/master/screenshot/s1.gif)  

### gradle
    compile 'com.github.iwgang:countdownview:1.0'

### code
```
CountdownView mCvCountdownView = (CountdownView)findViewById(R.id.cv_countdownViewTest1);
mCvCountdownView.start(995550000); // Millisecond

// or
for (int time=0; time<1000; time++) {
    mCvCountdownView.updateShow(time);
}
```

### layout
``` xml
<cn.iwgang.countdownview.CountdownView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:isHideTimeBackground="true"
    app:isShowDay="true"
    app:isShowHour="true"
    app:isShowMinute="true"
    app:isShowSecond="true"
    app:isShowMillisecond="true"
    app:timeTextColor="#000000"
    app:timeTextSize="22sp"
    app:isTimeTextBold="true"
    app:suffixGravity="bottom"
    app:suffixTextColor="#000000"
    app:suffixTextSize="12sp"
    app:suffixHour="时"
    app:suffixMinute="分"
    app:suffixSecond="秒"
    app:suffixMillisecond="毫秒" />
```

### customization
    attr | type | default
--- | --- | ---
isHideTimeBackground | boolean | true
timeBgColor  | color      | #444444
timeBgSize   | dimension  | timeSize + 2dp * 4
timeBgRadius | dimension  | 0
isShowTimeBgDivisionLine | boolean  | true
timeBgDivisionLineColor | color | #30FFFFFF
timeBgDivisionLineSize  | dimension | 0.5dp
timeTextSize   | dimension | 12sp | 
timeTextColor  | color | #000000
isTimeTextBold | boolean | false
isShowDay  | boolean | auto show (has value show if not hide)
isShowHour  | boolean | auto show (has value show if not hide)
isShowMinute  | boolean | true
isShowSecond  | boolean | true
isShowMillisecond  | boolean | false
suffixTextSize | dimension | 12sp
suffixTextColor  | color | #000000
isSuffixTextBold  | boolean | false
suffixGravity | 'top' or 'center' or 'bottom' | 'center'
suffix | string | ':'
suffixDay  | string | null
suffixHour  | string | null
suffixMinute  | string | null
suffixSecond  | string | null
suffixMillisecond  | string | null
suffixLRMargin  | dimension | left 3dp right 3dp
suffixDayLeftMargin | dimension | 0
suffixDayRightMargin  | dimension | 0
suffixHourLeftMargin  | dimension | 0
suffixHourRightMargin  | dimension | 0
suffixMinuteLeftMargin | dimension | 0
suffixMinuteRightMargin  | dimension | 0
suffixSecondLeftMargin  | dimension | 0
suffixSecondRightMargin  | dimension | 0
suffixMillisecondLeftMargin | dimension | 0
--- | --- | ---
