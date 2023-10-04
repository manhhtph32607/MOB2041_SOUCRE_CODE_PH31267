package com.example.mob2041_soucre_code_ph31267.model;

import java.io.Serializable;

public class ThanhVien implements Serializable {
    private int maThanhVien;
    private String hoTenThanhVien;
    private int namSinh;

    public ThanhVien() {
    }

    public ThanhVien(int maThanhVien, String hoTenThanhVien, int namSinh) {
        this.maThanhVien = maThanhVien;
        this.hoTenThanhVien = hoTenThanhVien;
        this.namSinh = namSinh;
    }

    public ThanhVien(String hoTenThanhVien, int namSinh) {
        this.hoTenThanhVien = hoTenThanhVien;
        this.namSinh = namSinh;
    }

    public int getMaThanhVien() {
        return maThanhVien;
    }

    public ThanhVien setMaThanhVien(int maThanhVien) {
        this.maThanhVien = maThanhVien;
        return this;
    }

    public String getHoTenThanhVien() {
        return hoTenThanhVien;
    }

    public ThanhVien setHoTenThanhVien(String hoTenThanhVien) {
        this.hoTenThanhVien = hoTenThanhVien;
        return this;
    }

    public int getNamSinh() {
        return namSinh;
    }

    public ThanhVien setNamSinh(int namSinh) {
        this.namSinh = namSinh;
        return this;
    }
}
