# CountdownView
Android 倒计时控件

![](https://raw.githubusercontent.com/iwgang/CountdownView/master/screenshot/s1.gif)  

# 代码使用
    CountdownView mCvCountdownViewTest1 = (CountdownView)findViewById(R.id.cv_countdownViewTest1);
    mCvCountdownViewTest1.start(995550000); // 毫秒

# xml (时间后缀增加空格可以控制两边间距)
    <cn.iwgang.calendardemo.countdownview.CountdownView
        android:id="@+id/cv_countdownViewTest4"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal"
        android:layout_marginTop="20dp"
        app:isHideTimeBackground="true"
        app:isShowHour="true"
        app:isShowMillisecond="true"
        app:timeTextColor="#000000"
        app:timeTextSize="25sp"
        app:suffixTextColor="#000000"
        app:suffixTextSize="15sp"
        app:suffixHour=" 时 "
        app:suffixMinute=" 分 "
        app:suffixSecond=" 秒 "
        app:suffixMillisecond=" 毫秒" />
