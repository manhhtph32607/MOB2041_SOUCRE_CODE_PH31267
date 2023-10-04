package com.example.mob2041_soucre_code_ph31267.model;

import java.io.Serializable;

public class Sach implements Serializable {
    private int maSach;
    private String tenSach;
    private int giaMuon, maLoaiSach;

    public Sach() {
    }

    public Sach(int maSach, String tenSach, int giaMuon, int maLoaiSach) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.giaMuon = giaMuon;
        this.maLoaiSach = maLoaiSach;
    }

    public Sach(String tenSach, int giaMuon, int maLoaiSach) {
        this.tenSach = tenSach;
        this.giaMuon = giaMuon;
        this.maLoaiSach = maLoaiSach;
    }

    public int getMaSach() {
        return maSach;
    }

    public Sach setMaSach(int maSach) {
        this.maSach = maSach;
        return this;
    }

    public String getTenSach() {
        return tenSach;
    }

    public Sach setTenSach(String tenSach) {
        this.tenSach = tenSach;
        return this;
    }

    public int getGiaMuon() {
        return giaMuon;
    }

    public Sach setGiaMuon(int giaMuon) {
        this.giaMuon = giaMuon;
        return this;
    }

    public int getMaLoaiSach() {
        return maLoaiSach;
    }

    public Sach setMaLoaiSach(int maLoaiSach) {
        this.maLoaiSach = maLoaiSach;
        return this;
    }
}
