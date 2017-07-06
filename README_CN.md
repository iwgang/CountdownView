[![Android Gems](http://www.android-gems.com/badge/iwgang/CountdownView.svg?branch=master)](http://www.android-gems.com/lib/iwgang/CountdownView)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-CountdownView-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/2641) 
[![@iwgang](https://img.shields.io/badge/weibo-%40iwgang-blue.svg)](http://weibo.com/iwgang)

#### [English](https://github.com/iwgang/CountdownView/blob/master/README.md)


# CountdownView
Android倒计时控件，使用Canvas绘制，支持多种样式

[下载DemoAPK](https://raw.githubusercontent.com/iwgang/CountdownView/master/demoapk/Demo_2.1.apk)

### Screenshot
<img src="https://raw.githubusercontent.com/iwgang/CountdownView/master/screenshot/s_main.png" width="400px" height="650px"/>

![](https://raw.githubusercontent.com/iwgang/CountdownView/master/screenshot/g_config.gif) 
![](https://raw.githubusercontent.com/iwgang/CountdownView/master/screenshot/g_config2.gif)  

<img src="https://raw.githubusercontent.com/iwgang/CountdownView/master/screenshot/s_list.jpg" width="400px" height="650px"/>

### Gradle
    compile 'com.github.iwgang:countdownview:2.1.6'

### Code
```
CountdownView mCvCountdownView = (CountdownView)findViewById(R.id.cv_countdownViewTest1);
mCvCountdownView.start(995550000); // 毫秒

// 或者自己编写倒计时逻辑，然后调用updateShow来更新UI
for (int time=0; time<1000; time++) {
    mCvCountdownView.updateShow(time);
}
```

### Layout
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

### 定制
|    参数    |   类型   |  默认值 |
| --------   | :-----:  | :----:  |
|isHideTimeBackground | boolean | true|
|timeBgColor  | color      | #444444|
|timeBgSize   | dimension  | timeSize + 2dp * 4|
|timeBgRadius | dimension  | 0|
|isShowTimeBgDivisionLine | boolean  | true|
|timeBgDivisionLineColor | color | #30FFFFFF|
|timeBgDivisionLineSize  | dimension | 0.5dp|
|timeTextSize   | dimension | 12sp | 
|timeTextColor  | color | #000000|
|isTimeTextBold | boolean | false|
|isShowDay  | boolean | 自动显示 (天 > 1 显示, = 0 隐藏)|
|isShowHour  | boolean | 自动显示 (小时 > 1 显示， = 0 隐藏)|
|isShowMinute  | boolean | true|
|isShowSecond  | boolean | true|
|isShowMillisecond  | boolean | false|
|isConvertDaysToHours | boolean | false|
|suffixTextSize | dimension | 12sp|
|suffixTextColor  | color | #000000|
|isSuffixTextBold  | boolean | false|
|suffixGravity | 'top' or 'center' or 'bottom' | 'center'|
|suffix | string | ':'|
|suffixDay  | string | null|
|suffixHour  | string | null|
|suffixMinute  | string | null|
|suffixSecond  | string | null|
|suffixMillisecond  | string | null|
|suffixLRMargin  | dimension | left 3dp right 3dp|
|suffixDayLeftMargin | dimension | 0|
|suffixDayRightMargin  | dimension | 0|
|suffixHourLeftMargin  | dimension | 0|
|suffixHourRightMargin  | dimension | 0|
|suffixMinuteLeftMargin | dimension | 0|
|suffixMinuteRightMargin  | dimension | 0|
|suffixSecondLeftMargin  | dimension | 0|
|suffixSecondRightMargin  | dimension | 0|
|suffixMillisecondLeftMargin | dimension | 0|
|isShowTimeBgBorder | boolean | false|
|timeBgBorderColor  | color | #000000|
|timeBgBorderSize  | dimension | 1dp|
|timeBgBorderRadius  | dimension | 0|

### 其它
* **多个CountdownView时，给每个指定值**
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
* **动态显示设置, 支持所有xml中的配置项来使用java代码设置**
```
    dynamicShow(DynamicConfig dynamicConfig)
```
* **倒计时结束后回调**
```
    setOnCountdownEndListener(OnCountdownEndListener onCountdownEndListener);
```
* **指定间隔时间回调**
```
    setOnCountdownIntervalListener(long interval, OnCountdownIntervalListener onCountdownIntervalListener);
```