package com.example.mob2041_soucre_code_ph31267.model;

import java.io.Serializable;

public class Top implements Serializable {
    private String tenSach;
    private int soLuong;

    public Top() {
    }

    public Top(String tenSach, int soLuong) {
        this.tenSach = tenSach;
        this.soLuong = soLuong;
    }

    public String getTenSach() {
        return tenSach;
    }

    public Top setTenSach(String tenSach) {
        this.tenSach = tenSach;
        return this;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public Top setSoLuong(int soLuong) {
        this.soLuong = soLuong;
        return this;
    }
}
