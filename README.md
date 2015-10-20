# CountdownView
Android 倒计时控件，全部使用canvas绘制，支持多种显示风格

![](https://raw.githubusercontent.com/iwgang/CountdownView/master/screenshot/s1.gif)  

# gradle
    compile 'com.github.iwgang:countdownview:1.0'

# code
    CountdownView mCvCountdownViewTest1 = (CountdownView)findViewById(R.id.cv_countdownViewTest1);
    mCvCountdownViewTest1.start(995550000); // Millisecond

# xml
    注：时间后缀增加空格可以控制两边间距
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
        
# Customization
    app:isHideTimeBackground [boolean default:false] "is hide background"
    ↓↓ Need isHideTimeBackground = false 
    app:timeBgColor [color default:#444444] "time background color"
    app:timeBgRadius [dimension default:0dp] "time background radius"
    app:isShowTimeBgDivisionLine [boolean default:true] "time background middle dividing line"
    app:timeBgDivisionLineColor [color default:#30FFFFFF] "time background middle dividing line color"
    
    app:timeTextSize [dimension default:12sp] "time text size"
    app:timeTextColor [color default:#FFFFFF] "time text color"
    app:isShowDay [boolean default:false] "is show day"
    app:isShowHour [boolean default:true] "is show hour"
    app:isShowMinute [boolean default:true] "is show minute"
    app:isShowMillisecond [boolean default:false] "is show millisecond"
    
    app:suffixTextSize [dimension default:12sp] "suffix text size"
    app:suffixTextColor [color default:#FFFFFF] "suffix text color"
    app:suffix [string default:null] "all suffix"
    app:suffixDay [string default:null] "day suffix"
    app:suffixHour [string default:null] "hour suffix"
    app:suffixMinute [string default:null] "minute suffix"
    app:suffixSecond [string default:null] "second suffix"
    app:suffixMillisecond [string default:null] "millisecond suffix"
    app:suffixGravity ['top' or 'center' or 'bottom' default:'center'] "suffix gravity"
