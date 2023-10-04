package com.example.mob2041_soucre_code_ph31267;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mob2041_soucre_code_ph31267.dao.ThuThuDAO;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextInputLayout txtILayoutTenDangNhap = findViewById(R.id.txtILayoutTenDangNhap);
        TextInputEditText txtIEdtTenDangNhap = findViewById(R.id.txtIEdtTenDangNhap);
        TextInputLayout txtILayoutMatKhau = findViewById(R.id.txtILayoutMatKhau);
        TextInputEditText txtIEdtMatKhau = findViewById(R.id.txtIEdtMatKhau);
        ImageButton iBtnAnMatKhau = findViewById(R.id.iBtnAnMatKhau);
        ImageButton iBtnHienMatKhau = findViewById(R.id.iBtnHienMatKhau);
        CheckBox checkBoxLuuTaiKhoan = findViewById(R.id.checkBoxLuuTaiKhoan);
        ProgressBar progressBarDangNhap = findViewById(R.id.progressBarDangNhap);
        Button btnDangNhapSub = findViewById(R.id.btnDangNhapSub);

        Context context = LoginActivity.this;
        ThuThuDAO thuThuDAO = new ThuThuDAO(context);

        txtIEdtTenDangNhap.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    txtILayoutTenDangNhap.setHelperText(null);
                    txtIEdtMatKhau.setEnabled(true);
                }
                return false;
            }
        });

        txtIEdtMatKhau.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    txtILayoutMatKhau.setHelperText(null);

                    if (txtIEdtTenDangNhap.getText().toString().trim().isEmpty()) {

                        txtIEdtMatKhau.setEnabled(false);
                        txtILayoutTenDangNhap.setHelperText("Vui lòng nhập tên đăng nhập");
                    }
                }
                return false;
            }
        });

        iBtnAnMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iBtnHienMatKhau.setVisibility(View.VISIBLE);
                iBtnAnMatKhau.setVisibility(View.GONE);
                txtIEdtMatKhau.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        iBtnHienMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iBtnAnMatKhau.setVisibility(View.VISIBLE);
                iBtnHienMatKhau.setVisibility(View.GONE);
                txtIEdtMatKhau.setTransformationMethod(null);
            }
        });

        checkSave(checkBoxLuuTaiKhoan, txtIEdtTenDangNhap, txtIEdtMatKhau);

        btnDangNhapSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean checkUser = true;
                Boolean checkPass = true;

                if (txtIEdtTenDangNhap.getText().toString().isEmpty()) {
                    txtILayoutTenDangNhap.setHelperText("Vui lòng nhập tên đăng nhập");

                    checkUser = false;
                }

                if (txtIEdtMatKhau.getText().toString().trim().isEmpty()) {
                    txtIEdtMatKhau.setEnabled(true);
                    txtILayoutMatKhau.setHelperText("Vui lòng nhập mật khẩu");
                    checkPass = false;
                }

                if (checkUser == false || checkPass == false) {
                    Toast.makeText(context, "Vui lòng nhập liệu đầy đủ", Toast.LENGTH_SHORT).show();
                    return;
                }

                String tenDangNhap = txtIEdtTenDangNhap.getText().toString();
                String matKhau = txtIEdtMatKhau.getText().toString().trim();
                Boolean checkSave = checkBoxLuuTaiKhoan.isChecked();

                progressBarDangNhap.setVisibility(View.VISIBLE);
                btnDangNhapSub.setVisibility(View.INVISIBLE);

                String maThuThu = thuThuDAO.checkLogin(tenDangNhap, matKhau);

                if (maThuThu == null) {
                    progressBarDangNhap.setVisibility(View.INVISIBLE);
                    btnDangNhapSub.setVisibility(View.VISIBLE);
                    Toast.makeText(context, "Tài khoản mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                } else {
                    if (thuThuDAO.checkStatus(maThuThu) == 0) {
                        progressBarDangNhap.setVisibility(View.INVISIBLE);
                        btnDangNhapSub.setVisibility(View.VISIBLE);
                        Toast.makeText(context, "Tài khoản đã bị vô hiệu hóa", Toast.LENGTH_SHORT).show();
                    } else {
                        luuTaiKhoan(tenDangNhap, matKhau, checkSave);
                        if (checkSave == false) {
                            txtIEdtMatKhau.setText("");
                        }

                        int phanQuyen = thuThuDAO.getRank(maThuThu);
                        Intent intent = new Intent(context, MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("maThuThu", maThuThu);
                        bundle.putString("tenDangNhap", tenDangNhap);
                        bundle.putInt("phanQuyen", phanQuyen);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

    }

    public void luuTaiKhoan(String user, String pass, boolean check) {
        SharedPreferences sharedPreferences = getSharedPreferences("luuTaiKhoan", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user", user);
        editor.putString("pass", pass);
        editor.putBoolean("check", check);
        editor.apply();
    }

    public void checkSave(CheckBox checkBox, TextInputEditText edtUser, TextInputEditText edtPass) {
        SharedPreferences sharedPreferences = getSharedPreferences("luuTaiKhoan", MODE_PRIVATE);
        String user = sharedPreferences.getString("user", "");
        String pass = sharedPreferences.getString("pass", "");
        boolean checkSave = sharedPreferences.getBoolean("check", false);
        checkBox.setChecked(checkSave);
        if (checkBox.isChecked()) {
            edtUser.setText(user);
            edtPass.setText(pass);
        }
    }

    @Override
    public void onBackPressed() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage("Bạn có chắc muốn thoát ứng dụng?")
                .setPositiveButton("Có", (dialog, which) -> finish())
                .setNegativeButton("Không", (dialog, which) -> dialog.dismiss())
                .show();
    }

}