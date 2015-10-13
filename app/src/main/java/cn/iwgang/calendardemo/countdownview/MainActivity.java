package cn.iwgang.calendardemo.countdownview;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CountdownView mCvCountdownViewTest1 = (CountdownView)findViewById(R.id.cv_countdownViewTest1);
        mCvCountdownViewTest1.start(955550000);

        CountdownView mCvCountdownViewTest2 = (CountdownView)findViewById(R.id.cv_countdownViewTest2);
        mCvCountdownViewTest2.start(955550000);

        CountdownView mCvCountdownViewTest3 = (CountdownView)findViewById(R.id.cv_countdownViewTest3);
        mCvCountdownViewTest3.start(955550000);
    }

}
