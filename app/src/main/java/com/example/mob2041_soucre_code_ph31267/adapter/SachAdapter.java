package com.example.mob2041_soucre_code_ph31267.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.example.mob2041_soucre_code_ph31267.dao.SachDAO;
import com.example.mob2041_soucre_code_ph31267.model.LoaiSach;
import com.example.mob2041_soucre_code_ph31267.model.Sach;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.ViewHolder> {

    private final Context context;
    private List<Sach> sachList;
    private final SachDAO sachDAO;

    private int maLoaiSach;

    public SachAdapter(Context context, List<Sach> sachList, SachDAO sachDAO) {
        this.context = context;
        this.sachList = sachList;
        this.sachDAO = sachDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_sach, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Sach sach = sachList.get(position);

        holder.txtMaSach.setText("Mã sách: " + sach.getMaSach());
        holder.txtTenSach.setText("Tên sách: " + sach.getTenSach());
        holder.txtTenLoaiSach_Sach.setText("Loại sách: " + sachDAO.getTenLoaiSach(sach.getMaLoaiSach()));
        holder.txtGiaMuon.setText("Giá mượn:" + sach.getGiaMuon());

        holder.iBtnOpenSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.iBtnOpenSach.setVisibility(View.GONE);
                holder.rlFunctionSach.setVisibility(View.VISIBLE);
            }
        });

        holder.iBtnCloseSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.iBtnOpenSach.setVisibility(View.VISIBLE);
                holder.rlFunctionSach.setVisibility(View.GONE);
            }
        });

        holder.iBtnUpdateSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.iBtnOpenSach.setVisibility(View.VISIBLE);
                holder.rlFunctionSach.setVisibility(View.GONE);

                openDialogUpdateSach(sach);
            }
        });

        holder.iBtnDeleteSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.iBtnOpenSach.setVisibility(View.VISIBLE);
                holder.rlFunctionSach.setVisibility(View.GONE);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cảnh báo");
                builder.setMessage("Sau khi xóa không thể khôi phục! Tiếp tục?");

                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (sachDAO.deleteSach(sach)) {
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            sachList.clear();
                            sachList.addAll(sachDAO.selectAllSach());
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
        return sachList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtMaSach, txtTenSach, txtTenLoaiSach_Sach, txtGiaMuon;
        ImageButton iBtnOpenSach, iBtnCloseSach, iBtnUpdateSach, iBtnDeleteSach;
        RelativeLayout rlFunctionSach;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMaSach = itemView.findViewById(R.id.txtMaSach);
            txtTenSach = itemView.findViewById(R.id.txtTenSach);
            txtTenLoaiSach_Sach = itemView.findViewById(R.id.txtTenLoaiSach_Sach);
            txtGiaMuon = itemView.findViewById(R.id.txtGiaMuon);
            iBtnOpenSach = itemView.findViewById(R.id.iBtnOpenSach);
            iBtnCloseSach = itemView.findViewById(R.id.iBtnCloseSach);
            iBtnUpdateSach = itemView.findViewById(R.id.iBtnUpdateSach);
            iBtnDeleteSach = itemView.findViewById(R.id.iBtnDeleteSach);
            rlFunctionSach = itemView.findViewById(R.id.rlFunctionSach);
        }
    }

    private void openDialogUpdateSach(Sach sach) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_update_sach, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        TextView txtUpdateMaSach = view.findViewById(R.id.txtUpdateMaSach);
        Spinner spinnerUpdateLoaiSach = view.findViewById(R.id.spinnerUpdateLoaiSach);
        EditText edtUpdateTenSach = view.findViewById(R.id.edtUpdateTenSach);
        EditText edtUpdateGiaMuon = view.findViewById(R.id.edtUpdateGiaMuon);
        TextView txtUpdateSachSub = view.findViewById(R.id.txtUpdateSachSub);
        TextView txtCancelUpdateSach = view.findViewById(R.id.txtCancelUpdateSach);

        txtUpdateMaSach.setText("Mã sách: " + sach.getMaLoaiSach());
        setDataSpinnerLoaiSach(spinnerUpdateLoaiSach, sach);
        edtUpdateTenSach.setText(sach.getTenSach());
        edtUpdateGiaMuon.setText(String.valueOf(sach.getGiaMuon()));

        txtUpdateSachSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtUpdateTenSach.getText().toString().isEmpty() | edtUpdateTenSach.getText().toString().equals("")) {
                    Toast.makeText(context, "Vui lòng nhập tên sách", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtUpdateGiaMuon.getText().toString().isEmpty() | edtUpdateGiaMuon.getText().toString().equals("")) {
                    Toast.makeText(context, "Vui lòng nhập giá mượn sách", Toast.LENGTH_SHORT).show();
                    return;
                }
                sach.setTenSach(edtUpdateTenSach.getText().toString());
                sach.setGiaMuon(Integer.parseInt(edtUpdateGiaMuon.getText().toString().trim()));
                sach.setMaLoaiSach(maLoaiSach);
                if (sachDAO.updateSach(sach)) {
                    Toast.makeText(context, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                    sachList.clear();
                    sachList.addAll(sachDAO.selectAllSach());
                    dialog.dismiss();
                    notifyDataSetChanged();
                }
            }
        });

        txtCancelUpdateSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void setDataSpinnerLoaiSach(Spinner spinnerLoaiSach, Sach sach) {
        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);
        List<LoaiSach> loaiSachList = loaiSachDAO.selectAllLoaiSach();
        List<HashMap<String, Object>> hashMapList = new ArrayList<>();
        for (LoaiSach loaiSach : loaiSachList) {
            if (loaiSach.getMaLoaiSach() == sach.getMaLoaiSach()) {
                loaiSachList.remove(loaiSach);
                loaiSachList.add(0, loaiSach);
                break;
            }
        }
        for (LoaiSach loaiSach : loaiSachList) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("maLoaiSach", loaiSach.getMaLoaiSach());
            hashMap.put("tenLoaiSach", loaiSach.getTenLoaiSach());
            hashMapList.add(hashMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                context,
                hashMapList,
                android.R.layout.simple_list_item_1,
                new String[]{"tenLoaiSach"},
                new int[]{android.R.id.text1}
        );
        spinnerLoaiSach.setAdapter(simpleAdapter);

        spinnerLoaiSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> hashMap = hashMapList.get(position);
                maLoaiSach = (int) hashMap.get("maLoaiSach");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
