package com.example.mob2041_soucre_code_ph31267.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mob2041_soucre_code_ph31267.R;
import com.example.mob2041_soucre_code_ph31267.dao.LoaiSachDAO;
import com.example.mob2041_soucre_code_ph31267.dao.PhieuMuonDAO;
import com.example.mob2041_soucre_code_ph31267.dao.SachDAO;
import com.example.mob2041_soucre_code_ph31267.dao.ThanhVienDAO;
import com.example.mob2041_soucre_code_ph31267.model.LoaiSach;
import com.example.mob2041_soucre_code_ph31267.model.PhieuMuon;
import com.example.mob2041_soucre_code_ph31267.model.Sach;
import com.example.mob2041_soucre_code_ph31267.model.ThanhVien;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.ViewHolder> {

    private final Context context;
    private List<PhieuMuon> phieuMuonList;
    private final PhieuMuonDAO phieuMuonDAO;

    private int maSach;
    private int maThanhVien;

    public PhieuMuonAdapter(Context context, List<PhieuMuon> phieuMuonList, PhieuMuonDAO phieuMuonDAO) {
        this.context = context;
        this.phieuMuonList = phieuMuonList;
        this.phieuMuonDAO = phieuMuonDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_phieu_muon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PhieuMuon phieuMuon = phieuMuonList.get(position);

        holder.txtMaPhieuMuon.setText("Mã phiếu mượn: " + phieuMuon.getMaPhieuMuon());
        holder.txtTenThanhVien_PhieuMuon.setText("Người mượn: "
                + phieuMuonDAO.getHoTenThanhVien(phieuMuon.getMaThanhVien()));
        holder.txtTenSach_PhieuMuon.setText("Tên sách: "
                + phieuMuonDAO.getTenSach(phieuMuon.getMaSach()));
        holder.txtNgayMuon.setText("Ngày mượn: " + phieuMuon.getNgayMuon());
        holder.txtGiaMuon_PhieuMuon.setText("Giá mượn: " + phieuMuon.getGiaMuon());
        holder.txtTenThuThu_PhieuMuon.setText("Thủ thư tạo phiếu: "
                + phieuMuonDAO.getHoThuThu(phieuMuon.getMaThuThu()) + " "
                + phieuMuonDAO.getTenThuThu(phieuMuon.getMaThuThu()));
        String trangThaiPhieuMuon =
                phieuMuon.getTrangThaiPhieuMuon() == 0 ? "(Chưa trả sách)" : "(Đã trả sách)";
        holder.txtTrangThaiPhieuMuon.setText(trangThaiPhieuMuon);

        holder.iBtnOpenPhieuMuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.iBtnOpenPhieuMuon.setVisibility(View.GONE);
                holder.rlFunctionPhieuMuon.setVisibility(View.VISIBLE);
            }
        });

        holder.iBtnClosePhieuMuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.iBtnOpenPhieuMuon.setVisibility(View.VISIBLE);
                holder.rlFunctionPhieuMuon.setVisibility(View.GONE);
            }
        });

        SachDAO sachDAO = new SachDAO(context);

        holder.iBtnUpdatePhieuMuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.rlFunctionPhieuMuon.setVisibility(View.GONE);
                holder.iBtnOpenPhieuMuon.setVisibility(View.VISIBLE);
                openDialogUpdatePhieuMuon(phieuMuon, sachDAO);
            }
        });

        holder.iBtnDeletePhieuMuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.rlFunctionPhieuMuon.setVisibility(View.GONE);
                holder.iBtnOpenPhieuMuon.setVisibility(View.VISIBLE);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cảnh báo");
                builder.setMessage("Sau khi xóa không thể khôi phục! Tiếp tục?");

                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (phieuMuonDAO.deletePhieuMuon(phieuMuon)) {
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            phieuMuonList.clear();
                            phieuMuonList.addAll(phieuMuonDAO.selectAllPhieuMuon());
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "Lỗi xóa", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return phieuMuonList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtMaPhieuMuon, txtTenThanhVien_PhieuMuon, txtTenSach_PhieuMuon, txtNgayMuon,
                txtGiaMuon_PhieuMuon, txtTenThuThu_PhieuMuon, txtTrangThaiPhieuMuon;

        ImageButton iBtnOpenPhieuMuon, iBtnClosePhieuMuon, iBtnUpdatePhieuMuon, iBtnDeletePhieuMuon;
        RelativeLayout rlFunctionPhieuMuon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaPhieuMuon = itemView.findViewById(R.id.txtMaPhieuMuon);
            txtTenThanhVien_PhieuMuon = itemView.findViewById(R.id.txtTenThanhVien_PhieuMuon);
            txtTenSach_PhieuMuon = itemView.findViewById(R.id.txtTenSach_PhieuMuon);
            txtNgayMuon = itemView.findViewById(R.id.txtNgayMuon);
            txtGiaMuon_PhieuMuon = itemView.findViewById(R.id.txtGiaMuon_PhieuMuon);
            txtTenThuThu_PhieuMuon = itemView.findViewById(R.id.txtTenThuThu_PhieuMuon);
            txtTrangThaiPhieuMuon = itemView.findViewById(R.id.txtTrangThaiPhieuMuon);
            iBtnOpenPhieuMuon = itemView.findViewById(R.id.iBtnOpenPhieuMuon);
            iBtnClosePhieuMuon = itemView.findViewById(R.id.iBtnClosePhieuMuon);
            iBtnUpdatePhieuMuon = itemView.findViewById(R.id.iBtnUpdatePhieuMuon);
            iBtnDeletePhieuMuon = itemView.findViewById(R.id.iBtnDeletePhieuMuon);
            rlFunctionPhieuMuon = itemView.findViewById(R.id.rlFunctionPhieuMuon);
        }
    }

    private void openDialogUpdatePhieuMuon(PhieuMuon phieuMuon, SachDAO sachDAO) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_update_phieu_muon, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        TextView txtUpdateMaPhieuMuon = view.findViewById(R.id.txtUpdateMaPhieuMuon);
        TextView txtUpdateHoTenThuThu = view.findViewById(R.id.txtUpdateHoTenThuThu);
        EditText edtUpdateNgayMuon = view.findViewById(R.id.edtUpdateNgayMuon);
        Spinner spinnerUpdateThanhVien = view.findViewById(R.id.spinnerUpdateThanhVien);
        Spinner spinnerUpdateSach = view.findViewById(R.id.spinnerUpdateSach);
        TextView txtChuaTraSach = view.findViewById(R.id.txtChuaTraSach);
        TextView txtDaTraSach = view.findViewById(R.id.txtDaTraSach);
        TextView txtUpdatePhieuMuonSub = view.findViewById(R.id.txtUpdatePhieuMuonSub);
        TextView txtCancelUpdatePhieuMuon = view.findViewById(R.id.txtCancelUpdatePhieuMuon);

        txtUpdateMaPhieuMuon.setText("Mã phiếu: " + phieuMuon.getMaPhieuMuon());
        txtUpdateHoTenThuThu.setText("Thủ thư tạo phiếu: "
                + phieuMuonDAO.getHoThuThu(phieuMuon.getMaThuThu()) + " "
                + phieuMuonDAO.getTenThuThu(phieuMuon.getMaThuThu()));
        edtUpdateNgayMuon.setText(phieuMuon.getNgayMuon());
        setDataSpinnerThanhVien(spinnerUpdateThanhVien, phieuMuon);
        setDataSpinnerSach(spinnerUpdateSach, phieuMuon);

        if (phieuMuon.getTrangThaiPhieuMuon() == 0) {
            txtDaTraSach.setVisibility(View.GONE);
        } else if (phieuMuon.getTrangThaiPhieuMuon() == 1) {
            txtChuaTraSach.setVisibility(View.GONE);
        }

        edtUpdateNgayMuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar date = Calendar.getInstance();
                int year = date.get(Calendar.YEAR);
                int month = date.get(Calendar.MONTH) + 1;
                int day = date.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edtUpdateNgayMuon.setText(String.format("%d-%d-%d", dayOfMonth, month, year));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        txtChuaTraSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtChuaTraSach.setVisibility(View.GONE);
                txtDaTraSach.setVisibility(View.VISIBLE);
                phieuMuon.setTrangThaiPhieuMuon(1);
            }
        });

        txtDaTraSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtDaTraSach.setVisibility(View.GONE);
                txtChuaTraSach.setVisibility(View.VISIBLE);
                phieuMuon.setTrangThaiPhieuMuon(0);
            }
        });

        txtUpdatePhieuMuonSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phieuMuon.setNgayMuon(edtUpdateNgayMuon.getText().toString());
                phieuMuon.setMaThanhVien(maThanhVien);
                phieuMuon.setMaSach(maSach);
                phieuMuon.setGiaMuon(sachDAO.getGiaMuon(maSach));

                if (phieuMuonDAO.updatePhieuMuon(phieuMuon)) {
                    Toast.makeText(context, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                    phieuMuonList.clear();
                    phieuMuonList.addAll(phieuMuonDAO.selectAllPhieuMuon());
                    dialog.dismiss();
                    notifyDataSetChanged();
                }
            }
        });

        txtCancelUpdatePhieuMuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void setDataSpinnerSach(Spinner spinnerSach, PhieuMuon phieuMuon) {
        SachDAO sachDAO = new SachDAO(context);
        List<Sach> sachList = sachDAO.selectAllSach();
        List<HashMap<String, Object>> hashMapList = new ArrayList<>();
        for (Sach sach : sachList) {
            if (sach.getMaSach() == phieuMuon.getMaSach()) {
                sachList.remove(sach);
                sachList.add(0, sach);
                break;
            }
        }
        for (Sach sach : sachList) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("maSach", sach.getMaSach());
            hashMap.put("tenSach", sach.getTenSach());
            hashMapList.add(hashMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                context,
                hashMapList,
                android.R.layout.simple_list_item_1,
                new String[]{"tenSach"},
                new int[]{android.R.id.text1}
        );
        spinnerSach.setAdapter(simpleAdapter);

        spinnerSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> hashMap = hashMapList.get(position);
                maSach = (int) hashMap.get("maSach");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setDataSpinnerThanhVien(Spinner spinnerThanhVien, PhieuMuon phieuMuon) {
        ThanhVienDAO thanhVienDAO = new ThanhVienDAO(context);
        List<ThanhVien> thanhVienList = thanhVienDAO.selectAllThanhVien();
        List<HashMap<String, Object>> hashMapList = new ArrayList<>();
        for (ThanhVien thanhVien : thanhVienList) {
            if (thanhVien.getMaThanhVien() == phieuMuon.getMaThanhVien()) {
                thanhVienList.remove(thanhVien);
                thanhVienList.add(0, thanhVien);
                break;
            }
        }
        for (ThanhVien thanhVien : thanhVienList) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("maThanhVien", thanhVien.getMaThanhVien());
            hashMap.put("hoTenThanhVien", thanhVien.getHoTenThanhVien());
            hashMapList.add(hashMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                context,
                hashMapList,
                android.R.layout.simple_list_item_1,
                new String[]{"hoTenThanhVien"},
                new int[]{android.R.id.text1}
        );
        spinnerThanhVien.setAdapter(simpleAdapter);

        spinnerThanhVien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> hashMap = hashMapList.get(position);
                maThanhVien = (int) hashMap.get("maThanhVien");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
