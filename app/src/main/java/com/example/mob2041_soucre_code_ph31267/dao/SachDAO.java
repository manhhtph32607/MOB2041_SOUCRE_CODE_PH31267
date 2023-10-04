package com.example.mob2041_soucre_code_ph31267.dao;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.mob2041_soucre_code_ph31267.database.DbHelper;
import com.example.mob2041_soucre_code_ph31267.model.Sach;
import com.example.mob2041_soucre_code_ph31267.model.ThuThu;

import java.util.ArrayList;
import java.util.List;

public class SachDAO {
    private final DbHelper dbHelper;
    private Context context;

    public SachDAO(Context context) {
        dbHelper = new DbHelper(context);
        this.context = context;
    }

    public List<Sach> selectAllSach() {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        List<Sach> sachList = new ArrayList<>();

        try {
            Cursor cursor = database.rawQuery("SELECT * FROM Sach", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    sachList.add(new Sach(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getInt(2),
                            cursor.getInt(3)));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e(TAG, "" + ex);
        }
        return sachList;
    }

    public Sach selectSach(int maSach) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Sach sach = null;

        String query = "SELECT * FROM Sach WHERE maSach = ?";
        String args[] = {String.valueOf(maSach)};

        Cursor cursor = database.rawQuery(query, args);

        if (cursor.moveToFirst()) {
            sach = new Sach(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getInt(3));
        }
        return sach;
    }

    public boolean addSach(Sach sach) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("tenSach", sach.getTenSach());
        values.put("giaMuon", sach.getGiaMuon());
        values.put("maLoaiSach", sach.getMaLoaiSach());

        long row = database.insert("Sach", null, values);
        return row != -1;
    }

    public boolean updateSach(Sach sach) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("tenSach", sach.getTenSach());
        values.put("giaMuon", sach.getGiaMuon());
        values.put("maLoaiSach", sach.getMaLoaiSach());

        long row = database.update("Sach", values, "maSach = ?",
                new String[]{String.valueOf(sach.getMaSach())});
        return row != -1;
    }

    public boolean deleteSach(Sach sach) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        long row = database.delete("Sach", "maSach = ?",
                new String[]{String.valueOf(sach.getMaSach())});
        return row != -1;
    }

    public String getTenLoaiSach(int maLoaiSach) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String tenLoaiSach = null;

        String query = "SELECT LoaiSach.tenLoaiSach FROM LoaiSach INNER JOIN Sach ON " +
                "LoaiSach.maLoaiSach = Sach.maLoaiSach WHERE Sach.maLoaiSach = ?";
        String args[] = {String.valueOf(maLoaiSach)};

        Cursor cursor = database.rawQuery(query, args);
        if (cursor.moveToFirst()) {
            tenLoaiSach = cursor.getString(0);
        }
        return tenLoaiSach;
    }

    public int getGiaMuon(int maSach) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        int giaMuon = 0;

        String query = "SELECT Sach.giaMuon FROM Sach WHERE Sach.maSach = ?";
        String args[] = {String.valueOf(maSach)};

        Cursor cursor = database.rawQuery(query, args);
        if (cursor.moveToFirst()) {
            giaMuon = cursor.getInt(0);
        }
        return giaMuon;
    }
}
