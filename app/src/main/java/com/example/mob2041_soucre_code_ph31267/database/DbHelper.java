package com.example.mob2041_soucre_code_ph31267.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DbName = "PhuongNamLibrary";

    public DbHelper(@Nullable Context context) {
        super(context, DbName, null, 6);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //  tạo bảng loại sách -----------------------------
        String createLoaiSach = "CREATE TABLE LoaiSach(" +
                "maLoaiSach INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenLoaiSach TEXT NOT NULL );";
        db.execSQL(createLoaiSach);

        // tạo bảng sách -----------------------------------
        String createSach = "CREATE TABLE Sach(" +
                "maSach INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenSach TEXT NOT NULL," +
                "giaMuon INTEGEER NOT NULL," +
                "maLoaiSach INTEGER," +
                "FOREIGN KEY (maLoaiSach) REFERENCES LoaiSach (maLoaiSach) ON DELETE SET NULL);";
        db.execSQL(createSach);

        // tạo bảng thủ thư ------------------------------------
        String createThuThu = "CREATE TABLE ThuThu(" +
                "maThuThu TEXT PRIMARY KEY," +
                "hoThuThu TEXT NOT NULL," +
                "tenThuThu TEXT NOT NULL," +
                "tenDangNhap TEXT UNIQUE NOT NULL," +
                "matKhau TEXT NOT NULL," +
                "trangThaiThuThu INTEGER NOT NULL," +
                "phanQuyen INTEGER NOT NULL);";
        db.execSQL(createThuThu);

        db.execSQL("INSERT INTO ThuThu VALUES(" +
                "'ADMIN','Đinh','Ngọc Anh','ADMINISTRATOR','123456789',1,0);");

        // tạo bảng thành viên -----------------------------------
        String createThanhVien = "CREATE TABLE ThanhVien(" +
                "maThanhVien INTEGER PRIMARY KEY AUTOINCREMENT," +
                "hoTenThanhVien TEXT NOT NULL," +
                "namSinh INTEGER);";
        db.execSQL(createThanhVien);

        // tạo bảng phiếu mượn ------------------------------------
        String createPhieuMuon = "CREATE TABLE PhieuMuon(" +
                "maPhieuMuon INTEGER PRIMARY KEY," +
                "trangThaiPhieuMuon INTEGER NOT NULL," +
                "giaMuon INTEGER," +
                "ngayMuon DATE NOT NULL," +
                "maThanhVien INTEGER," +
                "maSach INTEGER," +
                "maThuThu TEXT," +
                "FOREIGN KEY (maThanhVien) REFERENCES ThanhVien (maThanhVien) ON DELETE SET NULL," +
                "FOREIGN KEY (maSach) REFERENCES Sach (maSach) ON DELETE SET NULL," +
                "FOREIGN KEY (maThuThu) REFERENCES ThuThu (maThuThu));";
        db.execSQL(createPhieuMuon);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            String dropLoaiSach = "DROP TABLE IF EXISTS LoaiSach";
            String dropSach = "DROP TABLE IF EXISTS Sach";
            String dropThuThu = "DROP TABLE IF EXISTS ThuThu";
            String dropThanhVien = "DROP TABLE IF EXISTS ThanhVien";
            String dropPhieuMuon = "DROP TABLE IF EXISTS PhieuMuon";

            db.execSQL(dropLoaiSach);
            db.execSQL(dropSach);
            db.execSQL(dropThuThu);
            db.execSQL(dropThanhVien);
            db.execSQL(dropPhieuMuon);
            //  Gọi lại phương thức tạo bảng
            onCreate(db);
        }
    }
}
