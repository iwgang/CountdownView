package cn.iwgang.countdownviewdemo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import cn.iwgang.countdownview.CountdownView;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CountdownView mCvCountdownViewTest1 = (CountdownView)findViewById(R.id.cv_countdownViewTest1);
        mCvCountdownViewTest1.start(995550000);

        CountdownView mCvCountdownViewTest2 = (CountdownView)findViewById(R.id.cv_countdownViewTest2);
        mCvCountdownViewTest2.start(995550000);

        CountdownView mCvCountdownViewTest3 = (CountdownView)findViewById(R.id.cv_countdownViewTest3);
        mCvCountdownViewTest3.start(995550000);

        CountdownView mCvCountdownViewTest4 = (CountdownView)findViewById(R.id.cv_countdownViewTest4);
        mCvCountdownViewTest4.start(995550000);

        CountdownView mCvCountdownViewTest5 = (CountdownView)findViewById(R.id.cv_countdownViewTest5);
        mCvCountdownViewTest5.start(995550000);

        CountdownView mCvCountdownViewTest6 = (CountdownView)findViewById(R.id.cv_countdownViewTest6);
        mCvCountdownViewTest6.start(995550000);

        CountdownView mCvCountdownViewTest7 = (CountdownView)findViewById(R.id.cv_countdownViewTest7);
        mCvCountdownViewTest7.start(995550000);
    }

}
