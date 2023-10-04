package com.example.mob2041_soucre_code_ph31267.dao;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.mob2041_soucre_code_ph31267.database.DbHelper;
import com.example.mob2041_soucre_code_ph31267.model.ThuThu;

import java.util.ArrayList;
import java.util.List;

public class ThuThuDAO {
    private final DbHelper dbHelper;
    private Context context;

    public ThuThuDAO(Context context) {
        dbHelper = new DbHelper(context);
        this.context = context;
    }

    public List<ThuThu> selectAllThuThu() {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        List<ThuThu> thuThuList = new ArrayList<>();

        try {
            Cursor cursor = database.rawQuery(
                    "SELECT ThuThu.maThuThu, ThuThu.hoThuThu, ThuThu.tenThuThu," +
                            " ThuThu.trangThaiThuThu  FROM ThuThu", null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    thuThuList.add(new ThuThu(cursor.getString(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getInt(3)));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e(TAG, "" + ex);
        }
        return thuThuList;
    }

    public ThuThu selectThuThu(String maThuThu) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        ThuThu thuThu = null;

        String query = "SELECT * FROM ThuThu WHERE maThuThu = ?";
        String args[] = {maThuThu};

        Cursor cursor = database.rawQuery(query, args);

        if (cursor.moveToFirst()) {
            thuThu = new ThuThu(cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5),
                    cursor.getInt(6));
        }
        return thuThu;
    }

    public boolean addThuThu(ThuThu thuThu) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("maThuThu", thuThu.getMaThuThu());
        values.put("hoThuThu", thuThu.getHoThuThu());
        values.put("tenThuThu", thuThu.getTenThuThu());
        values.put("tenDangNhap", thuThu.getTenDangNhap());
        values.put("matKhau", thuThu.getMatKhau());
        values.put("trangThaiThuThu", thuThu.getTrangThaiThuThu());
        values.put("phanQuyen", thuThu.getPhanQuyen());

        long row = database.insert("ThuThu", null, values);
        return row != -1;
    }

    public boolean updateStatusThuThu(ThuThu thuThu) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("trangThaiThuThu", thuThu.getTrangThaiThuThu());

        long row = database.update("ThuThu", values, "maThuThu = ?",
                new String[]{thuThu.getMaThuThu()});
        return row != -1;
    }

    public boolean updatePassword(ThuThu thuThu) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("matKhau", thuThu.getMatKhau());

        long row = database.update("ThuThu", values, "maThuThu = ?",
                new String[]{thuThu.getMaThuThu()});
        return row != -1;
    }

    public String checkLogin(String username, String password) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String maThuThu = null;

        String query = "SELECT ThuThu.maThuThu FROM ThuThu WHERE tenDangNhap =? AND matKhau = ?";
        String args[] = {username, password};

        Cursor cursor = database.rawQuery(query, args);

        if (cursor.moveToFirst()) {
            maThuThu = cursor.getString(0);
        }
        return maThuThu;
    }

    public int checkStatus(String maThuThu) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        int trangThaiThuThu = -1;

        String query = "SELECT ThuThu.trangThaiThuThu FROM ThuThu WHERE maThuThu = ?";
        String args[] = {maThuThu};

        Cursor cursor = database.rawQuery(query, args);

        if (cursor.moveToFirst()) {
            trangThaiThuThu = cursor.getInt(0);
        }
        return trangThaiThuThu;
    }

    public int getRank(String maThuThu) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        int phanQuyen = -1;

        String query = "SELECT ThuThu.phanQuyen FROM ThuThu WHERE maThuThu = ?";
        String args[] = {maThuThu};

        Cursor cursor = database.rawQuery(query, args);

        if (cursor.moveToFirst()) {
            phanQuyen = cursor.getInt(0);
        }
        return phanQuyen;
    }
}
