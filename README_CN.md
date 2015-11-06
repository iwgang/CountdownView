[![Android Gems](http://www.android-gems.com/badge/iwgang/CountdownView.svg?branch=master)](http://www.android-gems.com/lib/iwgang/CountdownView)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-CountdownView-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/2641) 
[![@iwgang](https://img.shields.io/badge/微博-%40iwgang-blue.svg)](http://weibo.com/iwgang)

#### [English](https://github.com/iwgang/CountdownView/blob/master/README.md)


# CountdownView
Android 倒计时控件，使用Canvas绘制，支持多种样式

### screenshot
![](https://raw.githubusercontent.com/iwgang/CountdownView/master/screenshot/screenshot.gif)  

### gradle
    compile 'com.github.iwgang:countdownview:1.2'

### code
```
CountdownView mCvCountdownView = (CountdownView)findViewById(R.id.cv_countdownViewTest1);
mCvCountdownView.start(995550000); // 毫秒

// 或者自己编写倒计时逻辑，然后调用updateShow来更新UI
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
    参数 | 类型 | 默认值
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
isShowDay  | boolean | 自动显示 (天 > 1 显示, = 0 隐藏)
isShowHour  | boolean | 自动显示 (小时 > 1 显示， = 0 隐藏)
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

### 技巧
1. 多个CountdownView时，给每个指定值
```
    // 第1步，设置tag
    mCvCountdownView.setTag(R.id.name, uid);
    // 第2步，从回调中的CountdownView取回tag
    @Override
    public void onEnd(CountdownView cv) {
        Object nameTag = cv.getTag(R.id.uid);
        if (null != nameTag) {
            Log.i(TAG, "name = " + nameTag.toString());
        }
    }
```
2. 动态显示/隐藏某些时间 (如：开始显示时、分、秒，后面到指定时间改成分、秒、毫秒)
```
    customTimeShow(boolean isShowDay, boolean isShowHour, boolean  isShowMinute, boolean isShowSecond, boolean isShowMillisecond)
```
3. 倒计时结束后回调
```
    setOnCountdownEndListener(OnCountdownEndListener onCountdownEndListener);
```
4. 指定间隔时间回调
```
    setOnCountdownIntervalListener(long interval, OnCountdownIntervalListener onCountdownIntervalListener);
```
