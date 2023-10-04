package com.example.mob2041_soucre_code_ph31267.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.mob2041_soucre_code_ph31267.R;
import com.example.mob2041_soucre_code_ph31267.adapter.TopAdapter;
import com.example.mob2041_soucre_code_ph31267.dao.ThongKeDAO;
import com.example.mob2041_soucre_code_ph31267.model.Top;

import java.util.List;

public class Top10SachFragment extends Fragment {

    private ListView listViewTop;
    private List<Top> topList;
    private TopAdapter topAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top10_sach, container, false);
        listViewTop = view.findViewById(R.id.listViewTop);
        ThongKeDAO thongKeDAO = new ThongKeDAO(getContext());
        topList = thongKeDAO.getTop();
        topAdapter = new TopAdapter(getContext(), this, topList);
        listViewTop.setAdapter(topAdapter);
        return view;
    }
}