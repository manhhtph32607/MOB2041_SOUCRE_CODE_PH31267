package com.example.mob2041_soucre_code_ph31267;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.mob2041_soucre_code_ph31267.dao.ThuThuDAO;
import com.example.mob2041_soucre_code_ph31267.fragment.DoanhThuFragment;
import com.example.mob2041_soucre_code_ph31267.fragment.DoiMatKhauFragment;
import com.example.mob2041_soucre_code_ph31267.fragment.LoaiSachFragment;
import com.example.mob2041_soucre_code_ph31267.fragment.PhieuMuonFragment;
import com.example.mob2041_soucre_code_ph31267.fragment.SachFragment;
import com.example.mob2041_soucre_code_ph31267.fragment.ThanhVienFragment;
import com.example.mob2041_soucre_code_ph31267.fragment.ThuThuFragment;
import com.example.mob2041_soucre_code_ph31267.fragment.Top10SachFragment;
import com.example.mob2041_soucre_code_ph31267.model.ThuThu;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer_navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get bunlde
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String maThuThu = bundle.getString("maThuThu");
        String tenDangNhap = bundle.getString("tenDangNhap");
        int phanQuyen = bundle.getInt("phanQuyen");

        // Ánh xạ Layout xml
        drawer_navigation = findViewById(R.id.drawer_navigation);
        Toolbar toolbar_drawer_navigation = findViewById(R.id.toolbar_drawer_navigation);
        NavigationView navigation_view = findViewById(R.id.navigation_view);
        navigation_view.setItemIconTintList(null);

        // Setup toolbar
        setSupportActionBar(toolbar_drawer_navigation);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Setup DrawerTogger
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                MainActivity.this,
                drawer_navigation,
                toolbar_drawer_navigation,
                R.string.open,
                R.string.close);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        drawer_navigation.addDrawerListener(toggle);

        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager manager = getSupportFragmentManager();
                if (item.getItemId() == R.id.ql_phieu_muon) {
                    Fragment fragment = new PhieuMuonFragment(maThuThu);
                    manager.beginTransaction().replace(R.id.frame_layout_drawer_navigation, fragment).commit();
                } else if (item.getItemId() == R.id.ql_loai_sach) {
                    Fragment fragment = new LoaiSachFragment();
                    manager.beginTransaction().replace(R.id.frame_layout_drawer_navigation, fragment).commit();
                } else if (item.getItemId() == R.id.ql_sach) {
                    Fragment fragment = new SachFragment();
                    manager.beginTransaction().replace(R.id.frame_layout_drawer_navigation, fragment).commit();
                } else if (item.getItemId() == R.id.ql_thu_thu) {
                    Fragment fragment = new ThuThuFragment();
                    manager.beginTransaction().replace(R.id.frame_layout_drawer_navigation, fragment).commit();
                } else if (item.getItemId() == R.id.ql_thanh_vien) {
                    Fragment fragment = new ThanhVienFragment();
                    manager.beginTransaction().replace(R.id.frame_layout_drawer_navigation, fragment).commit();
                } else if (item.getItemId() == R.id.doi_mat_khau) {
                    Fragment fragment = new DoiMatKhauFragment(tenDangNhap, maThuThu);
                    manager.beginTransaction().replace(R.id.frame_layout_drawer_navigation, fragment).commit();
                } else if (item.getItemId() == R.id.top10) {
                    Fragment fragment = new Top10SachFragment();
                    manager.beginTransaction().replace(R.id.frame_layout_drawer_navigation, fragment).commit();
                } else if (item.getItemId() == R.id.doanh_thu) {
                    Fragment fragment = new DoanhThuFragment();
                    manager.beginTransaction().replace(R.id.frame_layout_drawer_navigation, fragment).commit();
                } else if (item.getItemId() == R.id.dang_xuat) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
                drawer_navigation.closeDrawer(GravityCompat.START);
                setTitle(item.getTitle());
                return false;
            }
        });
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = new PhieuMuonFragment(maThuThu);
        manager.beginTransaction().replace(R.id.frame_layout_drawer_navigation, fragment).commit();
        setTitle("Quản lý phiếu mượn");

        checkVisibleMenu(phanQuyen, navigation_view);

        ThuThuDAO thuThuDAO = new ThuThuDAO(MainActivity.this);
        ThuThu thuThu = thuThuDAO.selectThuThu(maThuThu);
        setIntroHeader(thuThu, navigation_view);

    }

    private void checkVisibleMenu(int phanQuyen, NavigationView navigationView) {
        Menu menu = navigationView.getMenu();
        MenuItem item = menu.findItem(R.id.ql_thu_thu);
        if (phanQuyen == 0) {
            item.setVisible(true);
        }
    }

    private void setIntroHeader(ThuThu thuThu, NavigationView navigationView) {
        View headerView = navigationView.getHeaderView(0);
        TextView txt_ho_ten_header = headerView.findViewById(R.id.txt_ho_ten_header);
        TextView txt_phan_quyen_header = headerView.findViewById(R.id.txt_phan_quyen_header);

        txt_ho_ten_header.setText(thuThu.getHoThuThu() + " " + thuThu.getTenThuThu());
        String phanQuyen = thuThu.getPhanQuyen() == 0 ? "Admin" : "Thủ thư";
        txt_phan_quyen_header.setText(phanQuyen);
    }

    @Override
    public void onBackPressed() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage("Bạn có chắc muốn thoát ứng dụng?")
                .setPositiveButton("Có", (dialog, which) -> finish())
                .setNegativeButton("Không", (dialog, which) -> dialog.dismiss())
                .show();
    }
}