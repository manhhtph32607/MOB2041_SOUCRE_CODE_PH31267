package com.example.mob2041_soucre_code_ph31267.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mob2041_soucre_code_ph31267.database.DbHelper;
import com.example.mob2041_soucre_code_ph31267.model.Sach;
import com.example.mob2041_soucre_code_ph31267.model.Top;

import java.util.ArrayList;
import java.util.List;

public class ThongKeDAO {
    private final DbHelper dbHelper;
    private Context context;

    public ThongKeDAO(Context context) {
        dbHelper = new DbHelper(context);
        this.context = context;
    }

    public List<Top> getTop() {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        List<Top> topList = new ArrayList<>();
        SachDAO sachDAO = new SachDAO(context);

        String query = "SELECT maSach, COUNT(maSach) AS soLuong FROM PhieuMuon GROUP BY maSach ORDER BY soLuong DESC LIMIT 10 ";

        Cursor cursor = database.rawQuery(query, null);
        while (cursor.moveToNext()) {
            Top top = new Top();
            int maSach = cursor.getInt(0);
            Sach sach = sachDAO.selectSach(maSach);
            top.setTenSach(sach.getTenSach());
            top.setSoLuong(cursor.getInt(1));
            topList.add(top);
        }
        return topList;
    }

    public int getDoanhThu(String fromDay, String toDay) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        List<Integer> list = new ArrayList<>();

        String query = "SELECT SUM(giaMuon) AS doanhThu FROM PhieuMuon WHERE ngayMuon BETWEEN ? AND ? ";

        Cursor cursor = database.rawQuery(query, new String[]{fromDay, toDay});

        while (cursor.moveToNext()) {
            try {
                list.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("doanhThu"))));
            } catch (Exception ex) {
                list.add(0);
            }
        }
        return list.get(0);
    }
}
