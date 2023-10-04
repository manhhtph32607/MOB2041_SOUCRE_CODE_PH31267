package com.example.mob2041_soucre_code_ph31267.fragment;

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
import com.example.mob2041_soucre_code_ph31267.adapter.ThanhVienAdapter;
import com.example.mob2041_soucre_code_ph31267.dao.ThanhVienDAO;
import com.example.mob2041_soucre_code_ph31267.model.ThanhVien;
import com.example.mob2041_soucre_code_ph31267.model.ThuThu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ThanhVienFragment extends Fragment {

    private ThanhVienDAO thanhVienDAO;
    private List<ThanhVien> thanhVienList;
    private ThanhVienAdapter thanhVienAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thanh_vien, container, false);
        thanhVienDAO = new ThanhVienDAO(getContext());
        thanhVienList = thanhVienDAO.selectAllThanhVien();
        thanhVienAdapter = new ThanhVienAdapter(getContext(), thanhVienList, thanhVienDAO);
        RecyclerView recyclerThanhVien = view.findViewById(R.id.recyclerThanhVien);
        FloatingActionButton floatBtnAddThanhVien = view.findViewById(R.id.floatBtnAddThanhVien);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerThanhVien.setLayoutManager(manager);
        recyclerThanhVien.setAdapter(thanhVienAdapter);

        floatBtnAddThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogAddThanhVien();
            }
        });

        return view;
    }

    private void openDialogAddThanhVien() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.item_add_thanh_vien, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText edtAddHoTenThanhVien = view.findViewById(R.id.edtAddHoTenThanhVien);
        EditText edtAddNamSinh = view.findViewById(R.id.edtAddNamSinh);
        TextView txtAddThanhVienSub = view.findViewById(R.id.txtAddThanhVienSub);
        TextView txtCancelAddThanhVien = view.findViewById(R.id.txtCancelAddThanhVien);

        txtAddThanhVienSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtAddHoTenThanhVien.getText().toString().isEmpty() | edtAddHoTenThanhVien.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Vui lòng nhập họ và tên thành viên", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtAddNamSinh.getText().toString().trim().isEmpty() | edtAddNamSinh.getText().toString().trim().equals("")) {
                    Toast.makeText(getContext(), "Vui lòng nhập năm sinh", Toast.LENGTH_SHORT).show();
                    return;
                }

                String hoTenThanhVien = edtAddHoTenThanhVien.getText().toString();
                int namSinh = Integer.parseInt(edtAddNamSinh.getText().toString());

                Calendar date = Calendar.getInstance();
                int year = date.get(Calendar.YEAR);
                if ((year - namSinh) < 13) {
                    Toast.makeText(getContext(), "Thành viên phải từ 13 tuổi trở lên", Toast.LENGTH_SHORT).show();
                    return;
                }

                ThanhVien thanhVien = new ThanhVien(hoTenThanhVien, namSinh);

                if (thanhVienDAO.addThanhVien(thanhVien)) {
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    thanhVienList.clear();
                    thanhVienList.addAll(thanhVienDAO.selectAllThanhVien());
                    thanhVienAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });

        txtCancelAddThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}