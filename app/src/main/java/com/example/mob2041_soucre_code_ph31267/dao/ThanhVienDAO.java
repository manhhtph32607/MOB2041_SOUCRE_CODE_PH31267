package com.example.mob2041_soucre_code_ph31267.dao;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.mob2041_soucre_code_ph31267.database.DbHelper;
import com.example.mob2041_soucre_code_ph31267.model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class ThanhVienDAO {
    private final DbHelper dbHelper;
    private Context context;

    public ThanhVienDAO(Context context) {
        dbHelper = new DbHelper(context);
        this.context = context;
    }

    public List<ThanhVien> selectAllThanhVien() {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        List<ThanhVien> thanhVienList = new ArrayList<>();

        try {
            Cursor cursor = database.rawQuery("SELECT * FROM ThanhVien", null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    thanhVienList.add(new ThanhVien(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getInt(2)));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e(TAG, "" + ex);
        }
        return thanhVienList;
    }

    public boolean addThanhVien(ThanhVien thanhVien) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("hoTenThanhVien", thanhVien.getHoTenThanhVien());
        values.put("namSinh", thanhVien.getNamSinh());

        long row = database.insert("ThanhVien", null, values);
        return row != -1;
    }

    public boolean updateThanhVien(ThanhVien thanhVien) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("hoTenThanhVien", thanhVien.getHoTenThanhVien());
        values.put("namSinh", thanhVien.getNamSinh());

        long row = database.update("ThanhVien", values, "maThanhVien =?",
                new String[]{String.valueOf(thanhVien.getMaThanhVien())});
        return row != -1;
    }

    public boolean deleteThanhVien(ThanhVien thanhVien) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        long row = database.delete("ThanhVien", "maThanhVien =?",
                new String[]{String.valueOf(thanhVien.getMaThanhVien())});
        return row != -1;
    }
}
