package cn.iwgang.countdownviewdemo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import cn.iwgang.countdownview.CountdownView;


public class MainActivity extends ActionBarActivity implements CountdownView.OnCountdownEndListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CountdownView mCvCountdownViewTest1 = (CountdownView)findViewById(R.id.cv_countdownViewTest1);
        mCvCountdownViewTest1.setTag("test1");
        long time1 = (long)2 * 60 * 60 * 1000;
        mCvCountdownViewTest1.start(time1);

        CountdownView mCvCountdownViewTest2 = (CountdownView)findViewById(R.id.cv_countdownViewTest2);
        mCvCountdownViewTest1.setTag("test2");
        long time2 = (long)30 * 60 * 1000;
        mCvCountdownViewTest2.start(time2);

        CountdownView mCvCountdownViewTest3 = (CountdownView)findViewById(R.id.cv_countdownViewTest3);
        long time3 = (long)9 * 60 * 60 * 1000;
        mCvCountdownViewTest3.start(time3);

        CountdownView mCvCountdownViewTest4 = (CountdownView)findViewById(R.id.cv_countdownViewTest4);
        long time4 = (long)150 * 24 * 60 * 60 * 1000;
        mCvCountdownViewTest4.start(time4);

        CountdownView mCvCountdownViewTest5 = (CountdownView)findViewById(R.id.cv_countdownViewTest5);
        long time5 = (long)2 * 60 * 60 * 1000;
        mCvCountdownViewTest5.start(time5);

    }

    @Override
    public void onEnd(CountdownView cv) {
        Object tag = cv.getTag();
        if (null != tag) {
            Log.i("wg", "tag = " + tag.toString());
        }
    }
}


