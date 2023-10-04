package com.example.mob2041_soucre_code_ph31267.model;

import java.io.Serializable;

public class LoaiSach implements Serializable {
    private int maLoaiSach;
    private String tenLoaiSach;

    public LoaiSach() {
    }

    public LoaiSach(int maLoaiSach, String tenLoaiSach) {
        this.maLoaiSach = maLoaiSach;
        this.tenLoaiSach = tenLoaiSach;
    }

    public LoaiSach(String tenLoaiSach) {
        this.tenLoaiSach = tenLoaiSach;
    }

    public int getMaLoaiSach() {
        return maLoaiSach;
    }

    public LoaiSach setMaLoaiSach(int maLoaiSach) {
        this.maLoaiSach = maLoaiSach;
        return this;
    }

    public String getTenLoaiSach() {
        return tenLoaiSach;
    }

    public LoaiSach setTenLoaiSach(String tenLoaiSach) {
        this.tenLoaiSach = tenLoaiSach;
        return this;
    }
}
