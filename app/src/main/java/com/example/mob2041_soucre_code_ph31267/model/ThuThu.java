package com.example.mob2041_soucre_code_ph31267.model;

import java.io.Serializable;

public class ThuThu implements Serializable {
    private String maThuThu, hoThuThu, tenThuThu, tenDangNhap, matKhau;
    private int trangThaiThuThu, phanQuyen;

    public ThuThu() {
    }

    public ThuThu(String maThuThu, String hoThuThu, String tenThuThu, String tenDangNhap, String matKhau, int trangThaiThuThu, int phanQuyen) {
        this.maThuThu = maThuThu;
        this.hoThuThu = hoThuThu;
        this.tenThuThu = tenThuThu;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.trangThaiThuThu = trangThaiThuThu;
        this.phanQuyen = phanQuyen;
    }

    public ThuThu(String maThuThu, String hoThuThu, String tenThuThu, int trangThaiThuThu) {
        this.maThuThu = maThuThu;
        this.hoThuThu = hoThuThu;
        this.tenThuThu = tenThuThu;
        this.trangThaiThuThu = trangThaiThuThu;
    }

    public String getMaThuThu() {
        return maThuThu;
    }

    public ThuThu setMaThuThu(String maThuThu) {
        this.maThuThu = maThuThu;
        return this;
    }

    public String getHoThuThu() {
        return hoThuThu;
    }

    public ThuThu setHoThuThu(String hoThuThu) {
        this.hoThuThu = hoThuThu;
        return this;
    }

    public String getTenThuThu() {
        return tenThuThu;
    }

    public ThuThu setTenThuThu(String tenThuThu) {
        this.tenThuThu = tenThuThu;
        return this;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public ThuThu setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
        return this;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public ThuThu setMatKhau(String matKhau) {
        this.matKhau = matKhau;
        return this;
    }

    public int getTrangThaiThuThu() {
        return trangThaiThuThu;
    }

    public ThuThu setTrangThaiThuThu(int trangThaiThuThu) {
        this.trangThaiThuThu = trangThaiThuThu;
        return this;
    }

    public int getPhanQuyen() {
        return phanQuyen;
    }

    public ThuThu setPhanQuyen(int phanQuyen) {
        this.phanQuyen = phanQuyen;
        return this;
    }
}
