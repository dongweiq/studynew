package com.honghe.materaildesign1;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    @Bind(R.id.fabtn_test)
    FloatingActionButton fabtnTest;
    @Bind(R.id.et_test1)
    EditText etTest1;
    @Bind(R.id.til_test1)
    TextInputLayout tilTest1;
    @Bind(R.id.button)
    Button button;
    @Bind(R.id.rl_parent)
    RelativeLayout rlParent;
    @Bind(R.id.tl_bottom)
    TabLayout tlBottom;
    @Bind(R.id.btnAdd)
    Button btnAdd;
    @Bind(R.id.btnRemoveFirst)
    Button btnRemoveFirst;
    @Bind(R.id.btnRemoveLast)
    Button btnRemoveLast;
    @Bind(R.id.btnRemoveAll)
    Button btnRemoveAll;

    private int mTabsNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
    }


    private void initData() {
        // 设置提示文本
        tilTest1.setHint("请输入你的邮箱：");
        etTest1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 10) {
                    // 设置错误提示
                    tilTest1.setErrorEnabled(true);
                    tilTest1.setError("邮箱名过长！");
                } else {
                    tilTest1.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    @OnClick(R.id.button)
    public void showWithActionSB() {
        final Snackbar snackbar = Snackbar.make(rlParent,
                "我是带 Action 的 Snackbar", Snackbar.LENGTH_LONG);
        snackbar.setAction("撤销", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "撤销成功", Toast.LENGTH_SHORT).show();
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    @OnClick(R.id.btnAdd)
    public void add() {
        mTabsNum += 1;
        TabLayout.Tab tab = tlBottom.newTab().setText("TAB" + mTabsNum);
        tlBottom.addTab(tab);
    }

    @OnClick(R.id.btnRemoveFirst)
    public void removeFirst() {
        int count = tlBottom.getTabCount();
        if (count <= 0) return;
        tlBottom.removeTabAt(0);
    }

    @OnClick(R.id.btnRemoveLast)
    public void removeLast() {
        int count = tlBottom.getTabCount();
        if (count <= 0) return;
        tlBottom.removeTabAt(count - 1);
    }

    @OnClick(R.id.btnRemoveAll)
    public void removeAll() {
        int count = tlBottom.getTabCount();
        if (count <= 0) return;
        mTabsNum = 0;
        tlBottom.removeAllTabs();
    }
}
