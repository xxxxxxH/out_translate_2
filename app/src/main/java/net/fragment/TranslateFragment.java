package net.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.tencent.mmkv.MMKV;

import net.basicmodel.LanguageActivity;
import net.basicmodel.R;
import net.entity.DataEntity;
import net.event.MessageEvent;
import net.http.RequestService;
import net.http.RetrofitUtils;
import net.utils.Constant;
import net.utils.CopyUtils;
import net.utils.LanguageManager;
import net.utils.MMKVUtils;
import net.widget.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Copyright (C) 2021,2021/8/26, a Tencent company. All rights reserved.
 * <p>
 * User : v_xhangxie
 * <p>
 * Desc :
 */
public class TranslateFragment extends Fragment implements View.OnClickListener {

    private TextView language_from;
    private TextView language_to;
    private ImageView change_language;
    private EditText input_language;
    private TextView btn_translate;
    private TextView language_tv;
    private ImageView copy;
    private ImageView collection;
    private TextView translate_after;

    private HashMap<String, String> languageMap;
    private LoadingDialog loadingDialog;
    private DataEntity entity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragment_translate, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        initData();
        initView(view);
    }

    private void initData() {
        languageMap = new LanguageManager().getLanguage();
    }

    private void initView(View view) {
        language_from = view.findViewById(R.id.language_from);
        language_to = view.findViewById(R.id.language_to);
        change_language = view.findViewById(R.id.change_language);
        input_language = view.findViewById(R.id.input_language);
        btn_translate = view.findViewById(R.id.btn_translate);
        language_tv = view.findViewById(R.id.language_tv);
        copy = view.findViewById(R.id.copy);
        collection = view.findViewById(R.id.collection);
        translate_after = view.findViewById(R.id.translate_after);
        language_from.setOnClickListener(this);
        language_to.setOnClickListener(this);
        change_language.setOnClickListener(this);
        btn_translate.setOnClickListener(this);
        copy.setOnClickListener(this);
        collection.setOnClickListener(this);
    }

    private void showDlg() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(getActivity());
        }
        loadingDialog.show();
    }

    private void closeDlg() {
        if (loadingDialog != null) {
            if (loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
        }
    }

    private void selectLanguage(String type) {
        Intent intent = new Intent(getActivity(), LanguageActivity.class);
        intent.putExtra(Constant.TYPE, type);
        getActivity().startActivity(intent);
    }

    private void changeLanguage() {
        String from = language_from.getText().toString();
        String to = language_to.getText().toString();
        language_from.setText(to);
        language_to.setText(from);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(MessageEvent event) {
        String[] msg = event.getMessage();
        String type = msg[0];
        String content = msg[1];
        if (TextUtils.equals(type, Constant.TYPE_FROM)) {
            language_from.setText(content);
        }
        if (TextUtils.equals(type, Constant.TYPE_TO)) {
            language_to.setText(content);
        }
        String codeF = new LanguageManager().getCode(languageMap, language_from.getText().toString());
        String toF = new LanguageManager().getCode(languageMap, language_to.getText().toString());
        language_tv.setText(codeF + " - " + toF);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void getTranslateResult() {
        showDlg();
        String codeF = new LanguageManager().getCode(languageMap, language_from.getText().toString());
        String toF = new LanguageManager().getCode(languageMap, language_to.getText().toString());
        String content = input_language.getText().toString();
        Retrofit retrofit = new RetrofitUtils().retrofit();
        retrofit.create(RequestService.class).getData(codeF, toF, content).enqueue(new Callback<DataEntity>() {
            @Override
            public void onResponse(Call<DataEntity> call, Response<DataEntity> response) {
                entity = response.body();
                String result = entity.getTrans_result().get(0).getDst();
                translate_after.setText(result);
                String entityKey = String.valueOf(System.currentTimeMillis());
                MMKVUtils.saveKeys(Constant.KEY_HISTORY, entityKey);
                MMKV.defaultMMKV().encode(entityKey, entity);
                closeDlg();
            }

            @Override
            public void onFailure(Call<DataEntity> call, Throwable t) {
                Toast.makeText(getActivity(), "no data", Toast.LENGTH_SHORT).show();
                closeDlg();
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.language_from:
                selectLanguage(Constant.TYPE_FROM);
                break;
            case R.id.language_to:
                selectLanguage(Constant.TYPE_TO);
                break;
            case R.id.change_language:
                changeLanguage();
                break;
            case R.id.btn_translate:
                getTranslateResult();
                break;
            case R.id.copy:
                new CopyUtils().copy(getActivity(), translate_after.getText().toString());
                break;
            case R.id.collection:
                String entityKey = String.valueOf(System.currentTimeMillis());
                MMKVUtils.saveKeys(Constant.KEY_COLLECTION, entityKey);
                MMKV.defaultMMKV().encode(entityKey, entity);
                Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
