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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mob2041_soucre_code_ph31267.R;
import com.example.mob2041_soucre_code_ph31267.adapter.ThuThuAdapter;
import com.example.mob2041_soucre_code_ph31267.dao.ThuThuDAO;
import com.example.mob2041_soucre_code_ph31267.model.Sach;
import com.example.mob2041_soucre_code_ph31267.model.ThuThu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class ThuThuFragment extends Fragment {
    private ThuThuDAO thuThuDAO;
    private List<ThuThu> thuThuList;
    private ThuThuAdapter thuThuAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thu_thu, container, false);
        thuThuDAO = new ThuThuDAO(getContext());
        thuThuList = new ArrayList<>();
        List<ThuThu> list = thuThuDAO.selectAllThuThu();
        for (ThuThu thuThu : list) {
            if (thuThu.getPhanQuyen() == 1) {
                thuThuList.add(thuThu);
            }
        }
        thuThuAdapter = new ThuThuAdapter(getContext(), thuThuList, thuThuDAO);
        RecyclerView recyclerThuThu = view.findViewById(R.id.recyclerThuThu);
        FloatingActionButton floatBtnAddThuThu = view.findViewById(R.id.floatBtnAddThuThu);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerThuThu.setLayoutManager(manager);
        recyclerThuThu.setAdapter(thuThuAdapter);

        floatBtnAddThuThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogAddThuThu();
            }
        });

        return view;
    }

    private void openDialogAddThuThu() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.item_add_thu_thu, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText edtAddMaThuThu = view.findViewById(R.id.edtAddMaThuThu);
        EditText edtAddHoThuThu = view.findViewById(R.id.edtAddHoThuThu);
        EditText edtAddTenThuThu = view.findViewById(R.id.edtAddTenThuThu);
        EditText edtAddTenDangNhap = view.findViewById(R.id.edtAddTenDangNhap);
        EditText edtAddMatKhau = view.findViewById(R.id.edtAddMatKhau);
        TextView txtAddThuThuSub = view.findViewById(R.id.txtAddThuThuSub);
        TextView txtCancelAddThuThu = view.findViewById(R.id.txtCancelAddThuThu);


        txtAddThuThuSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtAddMaThuThu.getText().toString().isEmpty() | edtAddMaThuThu.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Vui lòng nhập mã thủ thư", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtAddHoThuThu.getText().toString().isEmpty() | edtAddHoThuThu.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Vui lòng nhập họ thủ thư", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtAddTenThuThu.getText().toString().isEmpty() | edtAddTenThuThu.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Vui lòng nhập tên thủ thư", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtAddTenDangNhap.getText().toString().isEmpty() | edtAddTenDangNhap.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Vui lòng nhập tên đăng nhập", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtAddMatKhau.getText().toString().trim().isEmpty() | edtAddMatKhau.getText().toString().trim().equals("")) {
                    Toast.makeText(getContext(), "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtAddMatKhau.getText().toString().trim().length() < 8) {
                    Toast.makeText(getContext(), "Mật khẩu quá ngắn", Toast.LENGTH_SHORT).show();
                    return;
                }

                String maThuThu = edtAddMaThuThu.getText().toString();
                String hoThuThu = edtAddHoThuThu.getText().toString();
                String tenThuThu = edtAddTenThuThu.getText().toString();
                String tenDangNhap = edtAddTenDangNhap.getText().toString();
                String matKhau = edtAddMatKhau.getText().toString().trim();

                ThuThu thuThu = new ThuThu(maThuThu, hoThuThu, tenThuThu, tenDangNhap, matKhau, 1, 1);

                if (thuThuDAO.addThuThu(thuThu)) {
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    thuThuList.clear();
                    thuThuList.addAll(thuThuDAO.selectAllThuThu());
                    thuThuAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });

        txtCancelAddThuThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}