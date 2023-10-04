package com.example.mob2041_soucre_code_ph31267.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mob2041_soucre_code_ph31267.R;
import com.example.mob2041_soucre_code_ph31267.adapter.SachAdapter;
import com.example.mob2041_soucre_code_ph31267.dao.LoaiSachDAO;
import com.example.mob2041_soucre_code_ph31267.dao.SachDAO;
import com.example.mob2041_soucre_code_ph31267.model.LoaiSach;
import com.example.mob2041_soucre_code_ph31267.model.Sach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SachFragment extends Fragment {

    private SachDAO sachDAO;
    private List<Sach> sachList;
    private SachAdapter sachAdapter;

    private int maLoaiSach;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sach, container, false);
        sachDAO = new SachDAO(getContext());
        sachList = sachDAO.selectAllSach();
        sachAdapter = new SachAdapter(getContext(), sachList, sachDAO);
        RecyclerView recyclerSach = view.findViewById(R.id.recyclerSach);
        FloatingActionButton floatBtnAddSach = view.findViewById(R.id.floatBtnAddSach);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerSach.setLayoutManager(manager);
        recyclerSach.setAdapter(sachAdapter);

        floatBtnAddSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogAddSach();
            }
        });

        return view;
    }

    private void openDialogAddSach() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.item_add_sach, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        Spinner spinnerAddLoaiSach = view.findViewById(R.id.spinnerAddLoaiSach);
        EditText edtAddTenSach = view.findViewById(R.id.edtAddTenSach);
        EditText edtAddGiaMuon = view.findViewById(R.id.edtAddGiaMuon);
        TextView txtAddSachSub = view.findViewById(R.id.txtAddSachSub);
        TextView txtCancelAddSach = view.findViewById(R.id.txtCancelAddSach);

        setDataSpinnerLoaiSach(spinnerAddLoaiSach);

        txtAddSachSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtAddTenSach.getText().toString().isEmpty() | edtAddTenSach.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Vui lòng nhập tên sách", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtAddGiaMuon.getText().toString().isEmpty() | edtAddGiaMuon.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Vui lòng nhập giá mượn sách", Toast.LENGTH_SHORT).show();
                    return;
                }

                String tenSach = edtAddTenSach.getText().toString();
                int giaMuon = Integer.parseInt(edtAddGiaMuon.getText().toString().trim());

                Sach sach = new Sach(tenSach, giaMuon, maLoaiSach);

                if (sachDAO.addSach(sach)) {
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    sachList.clear();
                    sachList.addAll(sachDAO.selectAllSach());
                    sachAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });

        txtCancelAddSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void setDataSpinnerLoaiSach(Spinner spinnerLoaiSach) {
        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(getContext());
        List<LoaiSach> loaiSachList = loaiSachDAO.selectAllLoaiSach();
        List<HashMap<String, Object>> hashMapList = new ArrayList<>();
        for (LoaiSach loaiSach : loaiSachList) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("maLoaiSach", loaiSach.getMaLoaiSach());
            hashMap.put("tenLoaiSach", loaiSach.getTenLoaiSach());
            hashMapList.add(hashMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                hashMapList,
                android.R.layout.simple_list_item_1,
                new String[]{"tenLoaiSach"},
                new int[]{android.R.id.text1}
        );
        spinnerLoaiSach.setAdapter(simpleAdapter);

        spinnerLoaiSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> hashMap = hashMapList.get(position);
                maLoaiSach = (int) hashMap.get("maLoaiSach");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}