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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mob2041_soucre_code_ph31267.R;
import com.example.mob2041_soucre_code_ph31267.adapter.LoaiSachAdapter;
import com.example.mob2041_soucre_code_ph31267.dao.LoaiSachDAO;
import com.example.mob2041_soucre_code_ph31267.model.LoaiSach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class LoaiSachFragment extends Fragment {

    private LoaiSachDAO loaiSachDAO;
    private List<LoaiSach> loaiSachList;
    private LoaiSachAdapter loaiSachAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loai_sach, container, false);
        loaiSachDAO = new LoaiSachDAO(getContext());
        loaiSachList = loaiSachDAO.selectAllLoaiSach();
        loaiSachAdapter = new LoaiSachAdapter(getContext(), loaiSachList, loaiSachDAO);
        RecyclerView recyclerLoaiSach = view.findViewById(R.id.recyclerLoaiSach);
        FloatingActionButton floatBtnAddLoaiSach = view.findViewById(R.id.floatBtnAddLoaiSach);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerLoaiSach.setLayoutManager(manager);
        recyclerLoaiSach.setAdapter(loaiSachAdapter);

        floatBtnAddLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogAddLoaiSach();
            }
        });

        return view;
    }

    private void openDialogAddLoaiSach() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.item_add_loai_sach, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText edtAddTenLoaiSach = view.findViewById(R.id.edtAddTenLoaiSach);
        TextView txtAddLoaiSachSub = view.findViewById(R.id.txtAddLoaiSachSub);
        TextView txtCancelAddLoaiSach = view.findViewById(R.id.txtCancelAddLoaiSach);

        txtAddLoaiSachSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtAddTenLoaiSach.getText().toString().isEmpty() | edtAddTenLoaiSach.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Vui lòng nhập tên loại sách", Toast.LENGTH_SHORT).show();
                    return;
                }
                String tenLoaiSach = edtAddTenLoaiSach.getText().toString();
                LoaiSach loaiSach = new LoaiSach(tenLoaiSach);

                if (loaiSachDAO.addLoaiSach(loaiSach)) {
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    loaiSachList.clear();
                    loaiSachList.addAll(loaiSachDAO.selectAllLoaiSach());
                    dialog.dismiss();
                    loaiSachAdapter.notifyDataSetChanged();
                }
            }
        });

        txtCancelAddLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}