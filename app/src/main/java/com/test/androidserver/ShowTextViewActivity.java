package com.test.androidserver;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.chinalwb.are.render.AreTextView;

/**
 * @author dlink
 * @email linxy59@mail2.sysu.edu.cn
 * @date 2018/5/21
 * @discription null
 * @usage null
 */
public class ShowTextViewActivity extends AppCompatActivity {
    public static final String HTML_TEXT = "html_text";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_are_tv);
        AreTextView areTextView = findViewById(R.id.areTextView);
        String s = getIntent().getStringExtra(HTML_TEXT);
        areTextView.fromHtml(s);
    }
}
