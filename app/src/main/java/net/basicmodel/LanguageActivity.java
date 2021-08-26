package net.basicmodel;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.adapter.LanguageAdapter;
import net.event.MessageEvent;
import net.utils.Constant;
import net.utils.LanguageManager;
import net.utils.OnclickListener;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class LanguageActivity extends AppCompatActivity implements View.OnClickListener, OnclickListener {

    private ImageView back;
    private EditText editLan;
    private ImageView clear;
    private RecyclerView select_recycler;
    private LanguageAdapter adapter;

    private HashMap<String, String> languageMap;
    private ArrayList<String> countrys;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_select_language);
        initData();
        initView();
        initRecyclerView();
        initEditTextView();
    }

    private void initData() {
        languageMap = new LanguageManager().getLanguage();
        countrys = new LanguageManager().getAllCountry(languageMap);
        Intent intent = getIntent();
        type = intent.getStringExtra(Constant.TYPE);
    }

    private void initView() {
        back = findViewById(R.id.back);
        editLan = findViewById(R.id.editLan);
        clear = findViewById(R.id.clear);
        select_recycler = findViewById(R.id.select_recycler);
        clear.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    private void initRecyclerView() {
        adapter = new LanguageAdapter(countrys, this);
        select_recycler.setLayoutManager(new LinearLayoutManager(this));
        select_recycler.setAdapter(adapter);
    }

    private void initEditTextView() {
        editLan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() < 3)
                    return;
                int index = findItem(charSequence.toString());
                if (index != -1) {
                    select_recycler.smoothScrollToPosition(index);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private int findItem(String key) {
        int index = -1;
        for (int i = 0; i < countrys.size(); i++) {
            if (countrys.get(i).toLowerCase().contains(key.toLowerCase())) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.back:
                finish();
                break;
            case R.id.clear:
                editLan.setText("");
                break;
        }
    }

    @Override
    public void onItemClick(int position, @NotNull String type) {
        String content = countrys.get(position);
        EventBus.getDefault().post(new MessageEvent(this.type, content));
        finish();
    }
}
