package net.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.adapter.MyAdapter;
import net.basicmodel.R;
import net.entity.DataEntity;
import net.utils.Constant;
import net.utils.CopyUtils;
import net.utils.MMKVUtils;
import net.utils.OnclickListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Copyright (C) 2021,2021/8/26, a Tencent company. All rights reserved.
 * <p>
 * User : v_xhangxie
 * <p>
 * Desc :
 */
public class CommonFragment extends Fragment implements OnclickListener {

    String kind;
    RecyclerView recyclerCommon;
    ArrayList<DataEntity> data;
    MyAdapter adapter;

    public CommonFragment(String kind) {
        this.kind = kind;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragment_common, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView(view);
        initRecyclerView();
    }

    private void initData() {
        if (TextUtils.equals(Constant.TAG_COLLECTION, kind)) {
            data = MMKVUtils.getAllDatas(Constant.KEY_COLLECTION);
        }
        if (TextUtils.equals(Constant.TAG_HISTORY, kind)) {
            data = MMKVUtils.getAllDatas(Constant.KEY_HISTORY);
        }
    }

    private void initView(View view) {
        recyclerCommon = view.findViewById(R.id.recyclerCommon);
    }

    private void initRecyclerView() {
        adapter = new MyAdapter(data, kind, this);
        adapter.setMap();
        recyclerCommon.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerCommon.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position, @NotNull String type) {
        String text = data.get(position).getTrans_result().get(0).getSrc();
        new CopyUtils().copy(getActivity(), text);
    }
}
