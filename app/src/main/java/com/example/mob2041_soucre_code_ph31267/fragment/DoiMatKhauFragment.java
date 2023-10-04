package com.example.mob2041_soucre_code_ph31267.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mob2041_soucre_code_ph31267.R;
import com.example.mob2041_soucre_code_ph31267.dao.ThuThuDAO;
import com.example.mob2041_soucre_code_ph31267.model.ThuThu;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class DoiMatKhauFragment extends Fragment {

    private String tenDangNhap, maThuThu;

    public DoiMatKhauFragment(String tenDangNhap, String maThuThu) {
        this.tenDangNhap = tenDangNhap;
        this.maThuThu = maThuThu;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doi_mat_khau, container, false);
        ThuThuDAO thuThuDAO = new ThuThuDAO(getContext());

        TextInputLayout txtILayoutMatKhauCu = view.findViewById(R.id.txtILayoutMatKhauCu);
        TextInputEditText txtIEdtMatKhauCu = view.findViewById(R.id.txtIEdtMatKhauCu);
        ImageButton iBtnAnMatKhauCu = view.findViewById(R.id.iBtnAnMatKhauCu);
        ImageButton iBtnHienMatKhauCu = view.findViewById(R.id.iBtnHienMatKhauCu);
        TextInputLayout txtILayoutMatKhauMoi = view.findViewById(R.id.txtILayoutMatKhauMoi);
        TextInputEditText txtIEdtMatKhauMoi = view.findViewById(R.id.txtIEdtMatKhauMoi);
        ImageButton iBtnAnMatKhauMoi = view.findViewById(R.id.iBtnAnMatKhauMoi);
        ImageButton iBtnHienMatKhauMoi = view.findViewById(R.id.iBtnHienMatKhauMoi);
        TextInputLayout txtILayoutMatKhauMoiCon = view.findViewById(R.id.txtILayoutMatKhauMoiCon);
        TextInputEditText txtIEdtMatKhauMoiCon = view.findViewById(R.id.txtIEdtMatKhauMoiCon);
        ImageButton iBtnAnMatKhauMoiCon = view.findViewById(R.id.iBtnAnMatKhauMoiCon);
        ImageButton iBtnHienMatKhauMoiCon = view.findViewById(R.id.iBtnHienMatKhauMoiCon);
        TextView txtUpdatePasswordSub = view.findViewById(R.id.txtUpdatePasswordSub);

        txtIEdtMatKhauCu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    txtILayoutMatKhauCu.setHelperText(null);
                    txtIEdtMatKhauMoi.setEnabled(true);
                }
                return false;
            }
        });

        txtIEdtMatKhauMoi.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    txtILayoutMatKhauMoi.setHelperText(null);
                    txtIEdtMatKhauMoiCon.setEnabled(true);

                    if (txtIEdtMatKhauCu.getText().toString().trim().isEmpty()) {

                        txtIEdtMatKhauMoi.setEnabled(false);
                        txtILayoutMatKhauCu.setHelperText("Vui lòng nhập mật khẩu cũ");
                    }
                }
                return false;
            }
        });

        txtIEdtMatKhauMoiCon.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    txtILayoutMatKhauMoiCon.setHelperText(null);

                    if (txtIEdtMatKhauMoi.getText().toString().trim().isEmpty()) {

                        txtIEdtMatKhauMoiCon.setEnabled(false);
                        txtILayoutMatKhauMoi.setHelperText("Vui lòng nhập mật khẩu mới");
                    }
                    
                }
                return false;
            }
        });

        iBtnAnMatKhauCu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iBtnHienMatKhauCu.setVisibility(View.VISIBLE);
                iBtnAnMatKhauCu.setVisibility(View.GONE);
                txtIEdtMatKhauCu.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        iBtnHienMatKhauCu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iBtnAnMatKhauCu.setVisibility(View.VISIBLE);
                iBtnHienMatKhauCu.setVisibility(View.GONE);
                txtIEdtMatKhauCu.setTransformationMethod(null);
            }
        });

        iBtnAnMatKhauMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iBtnHienMatKhauMoi.setVisibility(View.VISIBLE);
                iBtnAnMatKhauMoi.setVisibility(View.GONE);
                txtIEdtMatKhauMoi.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        iBtnHienMatKhauMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iBtnAnMatKhauMoi.setVisibility(View.VISIBLE);
                iBtnHienMatKhauMoi.setVisibility(View.GONE);
                txtIEdtMatKhauMoi.setTransformationMethod(null);
            }
        });

        iBtnAnMatKhauMoiCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iBtnHienMatKhauMoiCon.setVisibility(View.VISIBLE);
                iBtnAnMatKhauMoiCon.setVisibility(View.GONE);
                txtIEdtMatKhauMoiCon.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        iBtnHienMatKhauMoiCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iBtnAnMatKhauMoiCon.setVisibility(View.VISIBLE);
                iBtnHienMatKhauMoiCon.setVisibility(View.GONE);
                txtIEdtMatKhauMoiCon.setTransformationMethod(null);
            }
        });

        txtUpdatePasswordSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean checkMatKhauCu = true;
                Boolean checkMatKhauMoi = true;
                Boolean checkMatKhauMoiCon = true;

                if (txtIEdtMatKhauCu.getText().toString().trim().isEmpty()) {
                    txtIEdtMatKhauCu.setEnabled(true);
                    txtILayoutMatKhauCu.setHelperText("Vui lòng nhập mật khẩu cũ");
                    checkMatKhauCu = false;
                }

                if (txtIEdtMatKhauMoi.getText().toString().trim().isEmpty()) {
                    txtIEdtMatKhauMoi.setEnabled(true);
                    txtILayoutMatKhauMoi.setHelperText("Vui lòng nhập mật khẩu mới");
                    checkMatKhauMoi = false;
                }

                if (txtIEdtMatKhauMoi.getText().toString().trim().length() < 8) {
                    Toast.makeText(getContext(), "Mật khẩu quá ngắn", Toast.LENGTH_SHORT).show();
                    txtILayoutMatKhauMoi.setHelperText("Mật khẩu quá ngắn");
                    txtIEdtMatKhauMoi.setEnabled(true);
                    checkMatKhauMoi = false;
                }

                if (txtIEdtMatKhauMoiCon.getText().toString().trim().isEmpty()) {
                    txtIEdtMatKhauMoiCon.setEnabled(true);
                    txtILayoutMatKhauMoiCon.setHelperText("Vui lòng nhập mật khẩu xác nhận");
                    checkMatKhauMoiCon = false;
                }

                if (checkMatKhauCu == false || checkMatKhauMoi == false || checkMatKhauMoiCon == false) {
                    Toast.makeText(getContext(), "Vui lòng nhập liệu đầy đủ", Toast.LENGTH_SHORT).show();
                    return;
                }

                String matKhauCu = txtIEdtMatKhauCu.getText().toString().trim();
                String matKhauMoi = txtIEdtMatKhauMoi.getText().toString().trim();
                String matKhauMoiCon = txtIEdtMatKhauMoiCon.getText().toString().trim();

                if (thuThuDAO.checkLogin(tenDangNhap, matKhauCu) != null) {
                    if (matKhauMoi.equals(matKhauMoiCon)) {
                        ThuThu thuThu = thuThuDAO.selectThuThu(maThuThu);
                        thuThu.setMatKhau(matKhauMoi);
                        if (thuThuDAO.updatePassword(thuThu)) {
                            Toast.makeText(getContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            txtIEdtMatKhauCu.setText(null);
                            txtIEdtMatKhauMoi.setText(null);
                            txtIEdtMatKhauMoiCon.setText(null);
                        } else {
                            Toast.makeText(getContext(), "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
                        txtILayoutMatKhauMoiCon.setHelperText("Mật khẩu xác nhận không khớp");
                    }
                } else {
                    Toast.makeText(getContext(), "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                    txtILayoutMatKhauCu.setHelperText("Mật khẩu cũ không đúng");
                }
            }
        });


        return view;

    }
}