package com.example.mob2041_soucre_code_ph31267.dao;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.mob2041_soucre_code_ph31267.database.DbHelper;
import com.example.mob2041_soucre_code_ph31267.model.PhieuMuon;

import java.util.ArrayList;
import java.util.List;

public class PhieuMuonDAO {
    private final DbHelper dbHelper;
    private Context context;

    public PhieuMuonDAO(Context context) {
        dbHelper = new DbHelper(context);
        this.context = context;
    }

    public List<PhieuMuon> selectAllPhieuMuon() {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        List<PhieuMuon> phieuMuonList = new ArrayList<>();

        try {
            Cursor cursor = database.rawQuery("SELECT * FROM PhieuMuon", null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    phieuMuonList.add(new PhieuMuon(cursor.getInt(0),
                            cursor.getInt(1),
                            cursor.getInt(2),
                            cursor.getString(3),
                            cursor.getInt(4),
                            cursor.getInt(5),
                            cursor.getString(6)));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e(TAG, "" + ex);
        }
        return phieuMuonList;
    }

    public boolean addPhieuMuon(PhieuMuon phieuMuon) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("trangThaiPhieuMuon", phieuMuon.getTrangThaiPhieuMuon());
        values.put("giaMuon", phieuMuon.getGiaMuon());
        values.put("ngayMuon", phieuMuon.getNgayMuon());
        values.put("maThanhVien", phieuMuon.getMaThanhVien());
        values.put("maSach", phieuMuon.getMaSach());
        values.put("maThuThu", phieuMuon.getMaThuThu());

        long row = database.insert("PhieuMuon", null, values);
        return row != -1;
    }

    public boolean updatePhieuMuon(PhieuMuon phieuMuon) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("trangThaiPhieuMuon", phieuMuon.getTrangThaiPhieuMuon());
        values.put("giaMuon", phieuMuon.getGiaMuon());
        values.put("ngayMuon", phieuMuon.getNgayMuon());
        values.put("maThanhVien", phieuMuon.getMaThanhVien());
        values.put("maSach", phieuMuon.getMaSach());
        values.put("maThuThu", phieuMuon.getMaThuThu());

        long row = database.update("PhieuMuon", values, "maPhieuMuon=?",
                new String[]{String.valueOf(phieuMuon.getMaPhieuMuon())});
        return row != -1;
    }

    public boolean deletePhieuMuon(PhieuMuon phieuMuon) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        long row = database.delete("PhieuMuon", "maPhieuMuon=?",
                new String[]{String.valueOf(phieuMuon.getMaPhieuMuon())});
        return row != -1;
    }

    public String getHoTenThanhVien(int maThanhVien) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String hoTenThanhVien = null;

        String query = "SELECT ThanhVien.hoTenThanhVien FROM ThanhVien INNER JOIN PhieuMuon ON " +
                "ThanhVien.maThanhVien = PhieuMuon.maThanhVien WHERE PhieuMuon.maThanhVien = ?";
        String args[] = {String.valueOf(maThanhVien)};

        Cursor cursor = database.rawQuery(query, args);
        if (cursor.moveToFirst()) {
            hoTenThanhVien = cursor.getString(0);
        }
        return hoTenThanhVien;
    }

    public String getTenSach(int maSach) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String tenSach = null;

        String query = "SELECT Sach.tenSach FROM Sach INNER JOIN PhieuMuon ON " +
                "Sach.maSach = PhieuMuon.maSach WHERE PhieuMuon.maSach = ?";
        String args[] = {String.valueOf(maSach)};

        Cursor cursor = database.rawQuery(query, args);
        if (cursor.moveToFirst()) {
            tenSach = cursor.getString(0);
        }
        return tenSach;
    }

    public String getHoThuThu(String maThuThu) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String hoThuThu = null;

        String query = "SELECT ThuThu.hoThuThu FROM ThuThu INNER JOIN PhieuMuon ON " +
                "ThuThu.maThuThu = PhieuMuon.maThuThu WHERE PhieuMuon.maThuThu = ?";
        String args[] = {String.valueOf(maThuThu)};

        Cursor cursor = database.rawQuery(query, args);
        if (cursor.moveToFirst()) {
            hoThuThu = cursor.getString(0);
        }
        return hoThuThu;
    }

    public String getTenThuThu(String maThuThu) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String tenThuThu = null;

        String query = "SELECT ThuThu.tenThuThu FROM ThuThu INNER JOIN PhieuMuon ON " +
                "ThuThu.maThuThu = PhieuMuon.maThuThu WHERE PhieuMuon.maThuThu = ?";
        String args[] = {String.valueOf(maThuThu)};

        Cursor cursor = database.rawQuery(query, args);
        if (cursor.moveToFirst()) {
            tenThuThu = cursor.getString(0);
        }
        return tenThuThu;
    }
}
