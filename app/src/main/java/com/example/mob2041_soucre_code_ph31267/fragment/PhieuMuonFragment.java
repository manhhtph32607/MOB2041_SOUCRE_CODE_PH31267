package com.example.mob2041_soucre_code_ph31267.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mob2041_soucre_code_ph31267.R;
import com.example.mob2041_soucre_code_ph31267.adapter.PhieuMuonAdapter;
import com.example.mob2041_soucre_code_ph31267.dao.PhieuMuonDAO;
import com.example.mob2041_soucre_code_ph31267.dao.SachDAO;
import com.example.mob2041_soucre_code_ph31267.dao.ThanhVienDAO;
import com.example.mob2041_soucre_code_ph31267.model.PhieuMuon;
import com.example.mob2041_soucre_code_ph31267.model.Sach;
import com.example.mob2041_soucre_code_ph31267.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


public class PhieuMuonFragment extends Fragment {

    private PhieuMuonDAO phieuMuonDAO;
    private List<PhieuMuon> phieuMuonList;
    private PhieuMuonAdapter phieuMuonAdapter;
    private int maSach;
    private int maThanhVien;
    private String maThuThu;

    public PhieuMuonFragment(String maThuThu) {
        this.maThuThu = maThuThu;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_phieu_muon, container, false);
        phieuMuonDAO = new PhieuMuonDAO(getContext());
        phieuMuonList = phieuMuonDAO.selectAllPhieuMuon();
        phieuMuonAdapter = new PhieuMuonAdapter(getContext(), phieuMuonList, phieuMuonDAO);
        RecyclerView recyclerPhieuMuon = view.findViewById(R.id.recyclerPhieuMuon);
        FloatingActionButton floatBtnAddPhieuMuon = view.findViewById(R.id.floatBtnAddPhieuMuon);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerPhieuMuon.setLayoutManager(manager);
        recyclerPhieuMuon.setAdapter(phieuMuonAdapter);

        SachDAO sachDAO = new SachDAO(getContext());
        floatBtnAddPhieuMuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogUpdatePhieuMuon(sachDAO);
            }
        });

        return view;
    }

    private void openDialogUpdatePhieuMuon(SachDAO sachDAO) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.item_add_phieu_muon, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText edtAddNgayMuon = view.findViewById(R.id.edtAddNgayMuon);
        Spinner spinnerAddThanhVien = view.findViewById(R.id.spinnerAddThanhVien);
        Spinner spinnerAddSach = view.findViewById(R.id.spinnerAddSach);
        TextView txtAddPhieuMuonSub = view.findViewById(R.id.txtAddPhieuMuonSub);
        TextView txtCancelAddPhieuMuon = view.findViewById(R.id.txtCancelAddPhieuMuon);

        Calendar date = Calendar.getInstance();
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        edtAddNgayMuon.setText(String.format("%d-%d-%d", day, month, year));

        setDataSpinnerThanhVien(spinnerAddThanhVien);
        setDataSpinnerSach(spinnerAddSach);

        txtAddPhieuMuonSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ngayMuon = edtAddNgayMuon.getText().toString();
                int giaMuon = sachDAO.getGiaMuon(maSach);

                PhieuMuon phieuMuon = new PhieuMuon(0, giaMuon, ngayMuon, maThanhVien, maSach, maThuThu);
                if (phieuMuonDAO.addPhieuMuon(phieuMuon)) {
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    phieuMuonList.clear();
                    phieuMuonList.addAll(phieuMuonDAO.selectAllPhieuMuon());
                    dialog.dismiss();
                    phieuMuonAdapter.notifyDataSetChanged();
                }
            }
        });

        txtCancelAddPhieuMuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void setDataSpinnerSach(Spinner spinnerSach) {
        SachDAO sachDAO = new SachDAO(getContext());
        List<Sach> sachList = sachDAO.selectAllSach();
        List<HashMap<String, Object>> hashMapList = new ArrayList<>();
        for (Sach sach : sachList) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("maSach", sach.getMaSach());
            hashMap.put("tenSach", sach.getTenSach());
            hashMapList.add(hashMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                hashMapList,
                android.R.layout.simple_list_item_1,
                new String[]{"tenSach"},
                new int[]{android.R.id.text1}
        );
        spinnerSach.setAdapter(simpleAdapter);

        spinnerSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> hashMap = hashMapList.get(position);
                maSach = (int) hashMap.get("maSach");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setDataSpinnerThanhVien(Spinner spinnerThanhVien) {
        ThanhVienDAO thanhVienDAO = new ThanhVienDAO(getContext());
        List<ThanhVien> thanhVienList = thanhVienDAO.selectAllThanhVien();
        List<HashMap<String, Object>> hashMapList = new ArrayList<>();
        for (ThanhVien thanhVien : thanhVienList) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("maThanhVien", thanhVien.getMaThanhVien());
            hashMap.put("hoTenThanhVien", thanhVien.getHoTenThanhVien());
            hashMapList.add(hashMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                hashMapList,
                android.R.layout.simple_list_item_1,
                new String[]{"hoTenThanhVien"},
                new int[]{android.R.id.text1}
        );
        spinnerThanhVien.setAdapter(simpleAdapter);

        spinnerThanhVien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> hashMap = hashMapList.get(position);
                maThanhVien = (int) hashMap.get("maThanhVien");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}