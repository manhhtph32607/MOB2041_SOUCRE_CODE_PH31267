package com.example.mob2041_soucre_code_ph31267.model;

import java.io.Serializable;

public class PhieuMuon implements Serializable {
    private int maPhieuMuon, trangThaiPhieuMuon, giaMuon;
    private String ngayMuon;
    private int maThanhVien, maSach;
    private String maThuThu;

    public PhieuMuon() {
    }

    public PhieuMuon(int maPhieuMuon, int trangThaiPhieuMuon, int giaMuon, String ngayMuon, int maThanhVien, int maSach, String maThuThu) {
        this.maPhieuMuon = maPhieuMuon;
        this.trangThaiPhieuMuon = trangThaiPhieuMuon;
        this.giaMuon = giaMuon;
        this.ngayMuon = ngayMuon;
        this.maThanhVien = maThanhVien;
        this.maSach = maSach;
        this.maThuThu = maThuThu;
    }

    public PhieuMuon(int trangThaiPhieuMuon, int giaMuon, String ngayMuon, int maThanhVien, int maSach, String maThuThu) {
        this.trangThaiPhieuMuon = trangThaiPhieuMuon;
        this.giaMuon = giaMuon;
        this.ngayMuon = ngayMuon;
        this.maThanhVien = maThanhVien;
        this.maSach = maSach;
        this.maThuThu = maThuThu;
    }

    public int getMaPhieuMuon() {
        return maPhieuMuon;
    }

    public PhieuMuon setMaPhieuMuon(int maPhieuMuon) {
        this.maPhieuMuon = maPhieuMuon;
        return this;
    }

    public int getTrangThaiPhieuMuon() {
        return trangThaiPhieuMuon;
    }

    public PhieuMuon setTrangThaiPhieuMuon(int trangThaiPhieuMuon) {
        this.trangThaiPhieuMuon = trangThaiPhieuMuon;
        return this;
    }

    public String getNgayMuon() {
        return ngayMuon;
    }

    public PhieuMuon setNgayMuon(String ngayMuon) {
        this.ngayMuon = ngayMuon;
        return this;
    }

    public int getMaThanhVien() {
        return maThanhVien;
    }

    public PhieuMuon setMaThanhVien(int maThanhVien) {
        this.maThanhVien = maThanhVien;
        return this;
    }

    public int getMaSach() {
        return maSach;
    }

    public PhieuMuon setMaSach(int maSach) {
        this.maSach = maSach;
        return this;
    }

    public int getGiaMuon() {
        return giaMuon;
    }

    public PhieuMuon setGiaMuon(int giaMuon) {
        this.giaMuon = giaMuon;
        return this;
    }

    public String getMaThuThu() {
        return maThuThu;
    }

    public PhieuMuon setMaThuThu(String maThuThu) {
        this.maThuThu = maThuThu;
        return this;
    }
}
