package cn.iwgang.countdownviewdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import cn.iwgang.countdownview.CountdownView;
import cn.iwgang.countdownview.DynamicConfig;
import cn.qqtheme.framework.picker.ColorPicker;


@SuppressWarnings("ALL")
public class DynamicShowActivity extends AppCompatActivity {
    private final long TIME = (long)8 * 24 * 60 * 60 * 1000;


    private CountdownView mCvCountdownViewTest, mCvCountdownViewTestHasBg;
    private View mLlBackgroundConfigContainer;

    private boolean hasBackgroundCountdownView = false;
    private float timeTextSize = 22;
    private float suffixTextSize = 12;
    private boolean isShowDay = true, isShowHour = true, isShowMinute = true, isShowSecond = true, isShowMillisecond = true;
    private float timeBgSize = 40;
    private float bgRadius = 3;
    private float bgBorderSize;
    private float bgBorderRadius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic);

        mCvCountdownViewTest = (CountdownView) findViewById(R.id.cv_countdownViewTest);
        mCvCountdownViewTestHasBg = (CountdownView) findViewById(R.id.cv_countdownViewTestHasBg);
        mLlBackgroundConfigContainer = findViewById(R.id.ll_backgroundConfigContainer);

        mCvCountdownViewTest.start(TIME);

        findViewById(R.id.btn_theme1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DynamicConfig.Builder dynamicConfigBuilder = new DynamicConfig.Builder();
                if (hasBackgroundCountdownView) {
                    DynamicConfig.BackgroundInfo backgroundInfo = new DynamicConfig.BackgroundInfo();
                    backgroundInfo.setColor(0xFFFF54BC)
                                  .setSize(30f)
                                  .setRadius(0f)
                                  .setShowTimeBgDivisionLine(false);
                    dynamicConfigBuilder.setTimeTextSize(15)
                                        .setTimeTextColor(0xFFFFFFFF)
                                        .setTimeTextBold(true)
                                        .setSuffixTextColor(0xFF000000)
                                        .setSuffixTextSize(15)
                                        .setBackgroundInfo(backgroundInfo)
                                        .setShowDay(false).setShowHour(true).setShowMinute(true).setShowSecond(true).setShowMillisecond(true);
                } else {
                    dynamicConfigBuilder.setTimeTextSize(35)
                                        .setTimeTextColor(0xFFFF5000)
                                        .setTimeTextBold(true)
                                        .setSuffixTextColor(0xFFFF5000)
                                        .setSuffixTextSize(30)
                                        .setSuffixTextBold(false)
                                        .setSuffix(":")
                                        .setSuffixMillisecond("") // Remove millisecond suffix
                                        .setSuffixGravity(DynamicConfig.SuffixGravity.CENTER)
                                        .setShowDay(false).setShowHour(false).setShowMinute(true).setShowSecond(true).setShowMillisecond(true);
                }
                if (hasBackgroundCountdownView) {
                    mCvCountdownViewTestHasBg.dynamicShow(dynamicConfigBuilder.build());
                } else {
                    mCvCountdownViewTest.dynamicShow(dynamicConfigBuilder.build());
                }
            }
        });

        findViewById(R.id.btn_theme2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DynamicConfig.Builder dynamicConfigBuilder = new DynamicConfig.Builder();
                if (hasBackgroundCountdownView) {
                    DynamicConfig.BackgroundInfo backgroundInfo = new DynamicConfig.BackgroundInfo();
                    backgroundInfo.setColor(0xFFFF5000)
                                  .setSize(60f)
                                  .setRadius(30f)
                                  .setShowTimeBgDivisionLine(false);
                    dynamicConfigBuilder.setTimeTextSize(42)
                                        .setTimeTextColor(0xFFFFFFFF)
                                        .setTimeTextBold(true)
                                        .setSuffixTextColor(0xFF000000)
                                        .setSuffixTextSize(42)
                                        .setSuffixTextBold(true)
                                        .setBackgroundInfo(backgroundInfo)
                                        .setShowDay(false).setShowHour(true).setShowMinute(true).setShowSecond(true).setShowMillisecond(false);
                } else {
                    dynamicConfigBuilder.setTimeTextSize(60)
                                        .setTimeTextColor(0xFF444444)
                                        .setTimeTextBold(false)
                                        .setSuffixTextColor(0xFF444444)
                                        .setSuffixTextSize(20)
                                        .setSuffixTextBold(false)
                                        .setSuffixMinute("m")
                                        .setSuffixMinuteLeftMargin(5)
                                        .setSuffixMinuteRightMargin(10)
                                        .setSuffixSecond("s")
                                        .setSuffixSecondLeftMargin(5)
                                        .setSuffixGravity(DynamicConfig.SuffixGravity.BOTTOM)
                                        .setShowDay(false).setShowHour(false).setShowMinute(true).setShowSecond(true).setShowMillisecond(false);
                }
                if (hasBackgroundCountdownView) {
                    mCvCountdownViewTestHasBg.dynamicShow(dynamicConfigBuilder.build());
                } else {
                    mCvCountdownViewTest.dynamicShow(dynamicConfigBuilder.build());
                }
            }
        });

        findViewById(R.id.btn_theme3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DynamicConfig.Builder dynamicConfigBuilder = new DynamicConfig.Builder();
                if (hasBackgroundCountdownView) {
                    DynamicConfig.BackgroundInfo backgroundInfo = new DynamicConfig.BackgroundInfo();
                    backgroundInfo.setColor(0xFF444444)
                                  .setSize(40f)
                                  .setRadius(3f)
                                  .setShowTimeBgDivisionLine(true)
                                  .setDivisionLineColor(Color.parseColor("#30FFFFFF"))
                                  .setDivisionLineSize(1f);
                    dynamicConfigBuilder.setTimeTextSize(22)
                                        .setTimeTextColor(0xFFFFFFFF)
                                        .setTimeTextBold(true)
                                        .setSuffixTextColor(0xFF000000)
                                        .setSuffixTextSize(18)
                                        .setSuffixTextBold(true)
                                        .setBackgroundInfo(backgroundInfo)
                                        .setShowDay(true).setShowHour(true).setShowMinute(true).setShowSecond(true).setShowMillisecond(true);
                } else {
                    dynamicConfigBuilder.setTimeTextSize(22)
                                        .setTimeTextColor(0xFF000000)
                                        .setTimeTextBold(false)
                                        .setSuffixTextColor(0xFF000000)
                                        .setSuffixTextSize(12)
                                        .setSuffixTextBold(false)
                                        .setSuffixDay("天").setSuffixHour("小时").setSuffixMinute("分钟").setSuffixSecond("秒").setSuffixMillisecond("毫秒")
                                        .setSuffixGravity(DynamicConfig.SuffixGravity.TOP)
                                        .setShowDay(true).setShowHour(true).setShowMinute(true).setShowSecond(true).setShowMillisecond(true);
                }
                if (hasBackgroundCountdownView) {
                    mCvCountdownViewTestHasBg.dynamicShow(dynamicConfigBuilder.build());
                } else {
                    mCvCountdownViewTest.dynamicShow(dynamicConfigBuilder.build());
                }
            }
        });

        findViewById(R.id.btn_timeTextSizePlus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DynamicConfig.Builder dynamicConfigBuilder = new DynamicConfig.Builder();
                dynamicConfigBuilder.setTimeTextSize(++timeTextSize);
                if (hasBackgroundCountdownView) {
                    mCvCountdownViewTestHasBg.dynamicShow(dynamicConfigBuilder.build());
                } else {
                    mCvCountdownViewTest.dynamicShow(dynamicConfigBuilder.build());
                }
            }
        });

        findViewById(R.id.btn_timeTextSizeSubtract).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timeTextSize == 0) return;
                DynamicConfig.Builder dynamicConfigBuilder = new DynamicConfig.Builder();
                dynamicConfigBuilder.setTimeTextSize(--timeTextSize);
                if (hasBackgroundCountdownView) {
                    mCvCountdownViewTestHasBg.dynamicShow(dynamicConfigBuilder.build());
                } else {
                    mCvCountdownViewTest.dynamicShow(dynamicConfigBuilder.build());
                }
            }
        });

        findViewById(R.id.btn_modTimeTextColor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPicker picker = new ColorPicker(DynamicShowActivity.this);
                picker.setOnColorPickListener(new ColorPicker.OnColorPickListener() {
                    @Override
                    public void onColorPicked(int pickedColor) {
                        DynamicConfig dynamicConfig = new DynamicConfig.Builder().setTimeTextColor(pickedColor).build();
                        if (hasBackgroundCountdownView) {
                            mCvCountdownViewTestHasBg.dynamicShow(dynamicConfig);
                        } else {
                            mCvCountdownViewTest.dynamicShow(dynamicConfig);
                        }
                    }
                });
                picker.show();
            }
        });

        findViewById(R.id.btn_suffixTextSizePlus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DynamicConfig.Builder dynamicConfigBuilder = new DynamicConfig.Builder();
                dynamicConfigBuilder.setSuffixTextSize(++suffixTextSize);
                if (hasBackgroundCountdownView) {
                    mCvCountdownViewTestHasBg.dynamicShow(dynamicConfigBuilder.build());
                } else {
                    mCvCountdownViewTest.dynamicShow(dynamicConfigBuilder.build());
                }
            }
        });

        findViewById(R.id.btn_suffixTextSizeSubtract).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timeTextSize == 0) return;
                DynamicConfig.Builder dynamicConfigBuilder = new DynamicConfig.Builder();
                dynamicConfigBuilder.setSuffixTextSize(--suffixTextSize);
                if (hasBackgroundCountdownView) {
                    mCvCountdownViewTestHasBg.dynamicShow(dynamicConfigBuilder.build());
                } else {
                    mCvCountdownViewTest.dynamicShow(dynamicConfigBuilder.build());
                }
            }
        });

        findViewById(R.id.btn_modSuffixTextColor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPicker picker = new ColorPicker(DynamicShowActivity.this);
                picker.setOnColorPickListener(new ColorPicker.OnColorPickListener() {
                    @Override
                    public void onColorPicked(int pickedColor) {
                        DynamicConfig dynamicConfig = new DynamicConfig.Builder().setSuffixTextColor(pickedColor).build();
                        if (hasBackgroundCountdownView) {
                            mCvCountdownViewTestHasBg.dynamicShow(dynamicConfig);
                        } else {
                            mCvCountdownViewTest.dynamicShow(dynamicConfig);
                        }
                    }
                });
                picker.show();
            }
        });

        findViewById(R.id.btn_suffixGravityTop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DynamicConfig dynamicConfig = new DynamicConfig.Builder().setSuffixGravity(DynamicConfig.SuffixGravity.TOP).build();
                if (hasBackgroundCountdownView) {
                    mCvCountdownViewTestHasBg.dynamicShow(dynamicConfig);
                } else {
                    mCvCountdownViewTest.dynamicShow(dynamicConfig);
                }
            }
        });

        findViewById(R.id.btn_suffixGravityCenter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DynamicConfig dynamicConfig = new DynamicConfig.Builder().setSuffixGravity(DynamicConfig.SuffixGravity.CENTER).build();
                if (hasBackgroundCountdownView) {
                    mCvCountdownViewTestHasBg.dynamicShow(dynamicConfig);
                } else {
                    mCvCountdownViewTest.dynamicShow(dynamicConfig);
                }
            }
        });

        findViewById(R.id.btn_suffixGravityBottom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DynamicConfig dynamicConfig = new DynamicConfig.Builder().setSuffixGravity(DynamicConfig.SuffixGravity.BOTTOM).build();
                if (hasBackgroundCountdownView) {
                    mCvCountdownViewTestHasBg.dynamicShow(dynamicConfig);
                } else {
                    mCvCountdownViewTest.dynamicShow(dynamicConfig);
                }
            }
        });

        findViewById(R.id.btn_refTimeShow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isShowDay && !isShowHour && !isShowMinute && !isShowSecond && !isShowMillisecond) {
                    Toast.makeText(DynamicShowActivity.this, "Select at least one item", Toast.LENGTH_LONG).show();
                    return;
                }
                DynamicConfig.Builder dynamicConfigBuilder = new DynamicConfig.Builder();
                dynamicConfigBuilder.setShowDay(isShowDay)
                                    .setShowHour(isShowHour)
                                    .setShowMinute(isShowMinute)
                                    .setShowSecond(isShowSecond)
                                    .setShowMillisecond(isShowMillisecond);

                if (hasBackgroundCountdownView) {
                    mCvCountdownViewTestHasBg.dynamicShow(dynamicConfigBuilder.build());
                } else {
                    mCvCountdownViewTest.dynamicShow(dynamicConfigBuilder.build());
                }
            }
        });

        findViewById(R.id.btn_bgSizePlus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DynamicConfig.Builder dynamicConfigBuilder = new DynamicConfig.Builder();
                dynamicConfigBuilder.setBackgroundInfo(new DynamicConfig.BackgroundInfo().setSize(++timeBgSize));
                mCvCountdownViewTestHasBg.dynamicShow(dynamicConfigBuilder.build());
            }
        });
        findViewById(R.id.btn_bgSizeSubtract).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timeBgSize == 0) return;
                DynamicConfig.Builder dynamicConfigBuilder = new DynamicConfig.Builder();
                dynamicConfigBuilder.setBackgroundInfo(new DynamicConfig.BackgroundInfo().setSize(--timeBgSize));
                mCvCountdownViewTestHasBg.dynamicShow(dynamicConfigBuilder.build());
            }
        });

        findViewById(R.id.btn_modBgColor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPicker picker = new ColorPicker(DynamicShowActivity.this);
                picker.setOnColorPickListener(new ColorPicker.OnColorPickListener() {
                    @Override
                    public void onColorPicked(int pickedColor) {
                        DynamicConfig dynamicConfig = new DynamicConfig.Builder().setBackgroundInfo(new DynamicConfig.BackgroundInfo().setColor(pickedColor)).build();
                        mCvCountdownViewTestHasBg.dynamicShow(dynamicConfig);
                    }
                });
                picker.show();
            }
        });


        findViewById(R.id.btn_bgRadiusPlus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DynamicConfig.Builder dynamicConfigBuilder = new DynamicConfig.Builder();
                dynamicConfigBuilder.setBackgroundInfo(new DynamicConfig.BackgroundInfo().setRadius(++bgRadius));
                mCvCountdownViewTestHasBg.dynamicShow(dynamicConfigBuilder.build());
            }
        });
        findViewById(R.id.btn_bgRadiusSubtract).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bgRadius == 0) return;
                DynamicConfig.Builder dynamicConfigBuilder = new DynamicConfig.Builder();
                dynamicConfigBuilder.setBackgroundInfo(new DynamicConfig.BackgroundInfo().setRadius(--bgRadius));
                mCvCountdownViewTestHasBg.dynamicShow(dynamicConfigBuilder.build());
            }
        });

        final Button btnModBgDivisionLineColor = (Button) findViewById(R.id.btn_modBgDivisionLineColor);
        btnModBgDivisionLineColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPicker picker = new ColorPicker(DynamicShowActivity.this);
                picker.setOnColorPickListener(new ColorPicker.OnColorPickListener() {
                    @Override
                    public void onColorPicked(int pickedColor) {
                        DynamicConfig.Builder dynamicConfigBuilder = new DynamicConfig.Builder();
                        dynamicConfigBuilder.setBackgroundInfo(new DynamicConfig.BackgroundInfo().setShowTimeBgDivisionLine(true).setDivisionLineColor(pickedColor));
                        mCvCountdownViewTestHasBg.dynamicShow(dynamicConfigBuilder.build());
                    }
                });
                picker.show();
            }
        });

        ((CheckBox)findViewById(R.id.cb_bgDivisionLine)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                btnModBgDivisionLineColor.setEnabled(isChecked);

                DynamicConfig.Builder dynamicConfigBuilder = new DynamicConfig.Builder();
                dynamicConfigBuilder.setBackgroundInfo(new DynamicConfig.BackgroundInfo().setShowTimeBgDivisionLine(isChecked));
                mCvCountdownViewTestHasBg.dynamicShow(dynamicConfigBuilder.build());
            }
        });

        final Button btnBgBorderSizePlus = (Button) findViewById(R.id.btn_bgBorderSizePlus);
        final Button btnBgBorderSizeSubtract = (Button) findViewById(R.id.btn_bgBorderSizeSubtract);
        final Button btnBgBorderRadiusPlus = (Button) findViewById(R.id.btn_bgBorderRadiusPlus);
        final Button btnBgBorderRadiusSubtract = (Button) findViewById(R.id.btn_bgBorderRadiusSubtract);
        btnBgBorderSizePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DynamicConfig.Builder dynamicConfigBuilder = new DynamicConfig.Builder();
                dynamicConfigBuilder.setBackgroundInfo(new DynamicConfig.BackgroundInfo().setShowTimeBgBorder(true).setBorderSize(++bgBorderSize));
                mCvCountdownViewTestHasBg.dynamicShow(dynamicConfigBuilder.build());
            }
        });
        btnBgBorderSizeSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bgBorderSize == 0) return;
                DynamicConfig.Builder dynamicConfigBuilder = new DynamicConfig.Builder();
                dynamicConfigBuilder.setBackgroundInfo(new DynamicConfig.BackgroundInfo().setShowTimeBgBorder(true).setBorderSize(--bgBorderSize));
                mCvCountdownViewTestHasBg.dynamicShow(dynamicConfigBuilder.build());
            }
        });
        btnBgBorderRadiusPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DynamicConfig.Builder dynamicConfigBuilder = new DynamicConfig.Builder();
                dynamicConfigBuilder.setBackgroundInfo(new DynamicConfig.BackgroundInfo().setShowTimeBgBorder(true).setBorderRadius(++bgBorderRadius));
                mCvCountdownViewTestHasBg.dynamicShow(dynamicConfigBuilder.build());
            }
        });
        btnBgBorderRadiusSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bgBorderRadius == 0) return;
                DynamicConfig.Builder dynamicConfigBuilder = new DynamicConfig.Builder();
                dynamicConfigBuilder.setBackgroundInfo(new DynamicConfig.BackgroundInfo().setShowTimeBgBorder(true).setBorderRadius(--bgBorderRadius));
                mCvCountdownViewTestHasBg.dynamicShow(dynamicConfigBuilder.build());
            }
        });

        final Button btnModBgBorderColor = (Button) findViewById(R.id.btn_modBgBorderColor);
        btnModBgBorderColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPicker picker = new ColorPicker(DynamicShowActivity.this);
                picker.setOnColorPickListener(new ColorPicker.OnColorPickListener() {
                    @Override
                    public void onColorPicked(int pickedColor) {
                        DynamicConfig.Builder dynamicConfigBuilder = new DynamicConfig.Builder();
                        dynamicConfigBuilder.setBackgroundInfo(new DynamicConfig.BackgroundInfo().setShowTimeBgBorder(true).setBorderColor(pickedColor));
                        mCvCountdownViewTestHasBg.dynamicShow(dynamicConfigBuilder.build());
                    }
                });
                picker.show();
            }
        });

        ((CheckBox)findViewById(R.id.cb_bgBorder)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                btnModBgBorderColor.setEnabled(isChecked);
                btnBgBorderSizePlus.setEnabled(isChecked);
                btnBgBorderSizeSubtract.setEnabled(isChecked);
                btnBgBorderRadiusPlus.setEnabled(isChecked);
                btnBgBorderRadiusSubtract.setEnabled(isChecked);

                DynamicConfig.Builder dynamicConfigBuilder = new DynamicConfig.Builder();
                dynamicConfigBuilder.setBackgroundInfo(new DynamicConfig.BackgroundInfo().setShowTimeBgBorder(isChecked));
                mCvCountdownViewTestHasBg.dynamicShow(dynamicConfigBuilder.build());
            }
        });
        ((CheckBox)findViewById(R.id.cb_bgBorder)).setChecked(false);

        final EditText etSuffixDay = (EditText) findViewById(R.id.et_suffixDay);
        final EditText etSuffixHour = (EditText) findViewById(R.id.et_suffixHour);
        final EditText etSuffixMinute = (EditText) findViewById(R.id.et_suffixMinute);
        final EditText etSuffixSecond = (EditText) findViewById(R.id.et_suffixSecond);
        final EditText etSuffixMillisecond = (EditText) findViewById(R.id.et_suffixMillisecond);
        findViewById(R.id.btm_refSuffix).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DynamicConfig.Builder dynamicConfigBuilder = new DynamicConfig.Builder();
                dynamicConfigBuilder.setSuffixDay(etSuffixDay.getText().toString())
                                    .setSuffixHour(etSuffixHour.getText().toString())
                                    .setSuffixMinute(etSuffixMinute.getText().toString())
                                    .setSuffixSecond(etSuffixSecond.getText().toString())
                                    .setSuffixMillisecond(etSuffixMillisecond.getText().toString());
                mCvCountdownViewTest.dynamicShow(dynamicConfigBuilder.build());
            }
        });

        ((CheckBox) findViewById(R.id.cb_hasBackground)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                hasBackgroundCountdownView = isChecked;
                mLlBackgroundConfigContainer.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                mCvCountdownViewTest.setVisibility(!isChecked ? View.VISIBLE : View.GONE);
                mCvCountdownViewTestHasBg.setVisibility(isChecked ? View.VISIBLE : View.GONE);

                if (isChecked) {
                    mCvCountdownViewTest.stop();
                    mCvCountdownViewTestHasBg.start(TIME);
                } else {
                    mCvCountdownViewTestHasBg.stop();
                    mCvCountdownViewTest.start(TIME);
                }
            }
        });

        handlerCheckBoxSel();
    }

    private void handlerCheckBoxSel() {
        final CheckBox cbDay = (CheckBox) findViewById(R.id.cb_day);
        final CheckBox cbHour = (CheckBox) findViewById(R.id.cb_hour);
        final CheckBox cbMinute = (CheckBox) findViewById(R.id.cb_minute);
        final CheckBox cbSecond = (CheckBox) findViewById(R.id.cb_second);
        final CheckBox cbMillisecond = (CheckBox) findViewById(R.id.cb_millisecond);

        cbDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DynamicShowActivity.this.isShowDay = isChecked;
            }
        });
        cbHour.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DynamicShowActivity.this.isShowHour = isChecked;
            }
        });
        cbMinute.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DynamicShowActivity.this.isShowMinute = isChecked;
            }
        });
        cbSecond.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DynamicShowActivity.this.isShowSecond = isChecked;
                if (!isChecked && DynamicShowActivity.this.isShowMillisecond) {
                    cbMillisecond.setChecked(false);
                }
            }
        });
        cbMillisecond.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DynamicShowActivity.this.isShowMillisecond = isChecked;
                if (isChecked && !DynamicShowActivity.this.isShowSecond) {
                    cbSecond.setChecked(true);
                }
            }
        });
    }

}


