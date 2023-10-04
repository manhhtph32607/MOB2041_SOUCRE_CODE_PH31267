package com.example.mob2041_soucre_code_ph31267.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mob2041_soucre_code_ph31267.R;
import com.example.mob2041_soucre_code_ph31267.dao.ThanhVienDAO;
import com.example.mob2041_soucre_code_ph31267.model.ThanhVien;

import java.util.List;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ViewHolder> {

    private final Context context;
    private List<ThanhVien> thanhVienList;
    private final ThanhVienDAO thanhVienDAO;

    public ThanhVienAdapter(Context context, List<ThanhVien> thanhVienList, ThanhVienDAO thanhVienDAO) {
        this.context = context;
        this.thanhVienList = thanhVienList;
        this.thanhVienDAO = thanhVienDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_thanh_vien, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThanhVien thanhVien = thanhVienList.get(position);

        holder.txtMaThanhVien.setText("Mã thành viên: " + thanhVien.getMaThanhVien());
        holder.txtHoTenThanhVien.setText("Họ tên: " + thanhVien.getHoTenThanhVien());
        holder.txtNamSinh.setText("Năm sinh: " + thanhVien.getNamSinh());

        holder.iBtnOpenThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.iBtnOpenThanhVien.setVisibility(View.GONE);
                holder.rlFunctionThanhVien.setVisibility(View.VISIBLE);
            }
        });

        holder.iBtnCloseThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.iBtnOpenThanhVien.setVisibility(View.VISIBLE);
                holder.rlFunctionThanhVien.setVisibility(View.GONE);
            }
        });

        holder.iBtnUpdateThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.iBtnOpenThanhVien.setVisibility(View.VISIBLE);
                holder.rlFunctionThanhVien.setVisibility(View.GONE);

                openDialogUpdateThanhVien(thanhVien);
            }
        });

        holder.iBtnDeleteThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.iBtnOpenThanhVien.setVisibility(View.VISIBLE);
                holder.rlFunctionThanhVien.setVisibility(View.GONE);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cảnh báo");
                builder.setMessage("Sau khi xóa không thể khôi phục! Tiếp tục?");

                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (thanhVienDAO.deleteThanhVien(thanhVien)) {
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            thanhVienList.clear();
                            thanhVienList.addAll(thanhVienDAO.selectAllThanhVien());
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
        return thanhVienList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtMaThanhVien, txtHoTenThanhVien, txtNamSinh;
        ImageButton iBtnOpenThanhVien, iBtnCloseThanhVien, iBtnUpdateThanhVien, iBtnDeleteThanhVien;

        RelativeLayout rlFunctionThanhVien;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaThanhVien = itemView.findViewById(R.id.txtMaThanhVien);
            txtHoTenThanhVien = itemView.findViewById(R.id.txtHoTenThanhVien);
            txtNamSinh = itemView.findViewById(R.id.txtNamSinh);
            iBtnOpenThanhVien = itemView.findViewById(R.id.iBtnOpenThanhVien);
            iBtnCloseThanhVien = itemView.findViewById(R.id.iBtnCloseThanhVien);
            iBtnUpdateThanhVien = itemView.findViewById(R.id.iBtnUpdateThanhVien);
            iBtnDeleteThanhVien = itemView.findViewById(R.id.iBtnDeleteThanhVien);
            rlFunctionThanhVien = itemView.findViewById(R.id.rlFunctionThanhVien);
        }
    }

    private void openDialogUpdateThanhVien(ThanhVien thanhVien) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_update_thanh_vien, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        TextView txtUpdateMaThanhVien = view.findViewById(R.id.txtUpdateMaThanhVien);
        EditText edtUpdateHoTenThanhVien = view.findViewById(R.id.edtUpdateHoTenThanhVien);
        EditText edtUpdateNamSinh = view.findViewById(R.id.edtUpdateNamSinh);
        TextView txtUpdateThanhVienSub = view.findViewById(R.id.txtUpdateThanhVienSub);
        TextView txtCancelUpdateThanhVien = view.findViewById(R.id.txtCancelUpdateThanhVien);

        txtUpdateMaThanhVien.setText("Mã thành viên: " + thanhVien.getMaThanhVien());
        edtUpdateHoTenThanhVien.setText(thanhVien.getHoTenThanhVien());
        edtUpdateNamSinh.setText(String.valueOf(thanhVien.getNamSinh()));

        txtUpdateThanhVienSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtUpdateHoTenThanhVien.getText().toString().isEmpty() | edtUpdateHoTenThanhVien.getText().toString().equals("")) {
                    Toast.makeText(context, "Vui lòng nhập họ tên thành viên", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtUpdateNamSinh.getText().toString().isEmpty() | edtUpdateNamSinh.getText().toString().equals("")) {
                    Toast.makeText(context, "Vui lòng nhập năm sinh", Toast.LENGTH_SHORT).show();
                    return;
                }
                thanhVien.setHoTenThanhVien(edtUpdateHoTenThanhVien.getText().toString());
                thanhVien.setNamSinh(Integer.parseInt(edtUpdateNamSinh.getText().toString()));

                if (thanhVienDAO.updateThanhVien(thanhVien)) {
                    Toast.makeText(context, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                    thanhVienList.clear();
                    thanhVienList.addAll(thanhVienDAO.selectAllThanhVien());
                    dialog.dismiss();
                    notifyDataSetChanged();
                }
            }
        });

        txtCancelUpdateThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
