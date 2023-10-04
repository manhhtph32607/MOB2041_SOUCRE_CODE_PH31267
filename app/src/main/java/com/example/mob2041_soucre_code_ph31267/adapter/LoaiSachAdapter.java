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
import com.example.mob2041_soucre_code_ph31267.dao.LoaiSachDAO;
import com.example.mob2041_soucre_code_ph31267.model.LoaiSach;

import java.sql.SQLException;
import java.util.List;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.ViewHolder> {

    private final Context context;
    private List<LoaiSach> loaiSachList;
    private final LoaiSachDAO loaiSachDAO;

    public LoaiSachAdapter(Context context, List<LoaiSach> loaiSachList, LoaiSachDAO loaiSachDAO) {
        this.context = context;
        this.loaiSachList = loaiSachList;
        this.loaiSachDAO = loaiSachDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_loai_sach, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LoaiSach loaiSach = loaiSachList.get(position);

        holder.txtMaLoaiSach.setText("Mã loại sách: " + loaiSach.getMaLoaiSach());
        holder.txtTenLoaiSach.setText("Tên loại sách: " + loaiSach.getTenLoaiSach());

        holder.iBtnOpenLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.iBtnOpenLoaiSach.setVisibility(View.GONE);
                holder.rlFunctionLoaiSach.setVisibility(View.VISIBLE);
            }
        });

        holder.iBtnCloseLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.iBtnOpenLoaiSach.setVisibility(View.VISIBLE);
                holder.rlFunctionLoaiSach.setVisibility(View.GONE);
            }
        });

        holder.iBtnUpdateLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.rlFunctionLoaiSach.setVisibility(View.GONE);
                holder.iBtnOpenLoaiSach.setVisibility(View.VISIBLE);
                openDialogUpdateLoaiSach(loaiSach);
            }
        });

        holder.iBtnDeleteLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.rlFunctionLoaiSach.setVisibility(View.GONE);
                holder.iBtnOpenLoaiSach.setVisibility(View.VISIBLE);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cảnh báo");
                builder.setMessage("Sau khi xóa không thể khôi phục! Tiếp tục?");

                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (loaiSachDAO.deleteLoaiSach(loaiSach)) {
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            loaiSachList.clear();
                            loaiSachList.addAll(loaiSachDAO.selectAllLoaiSach());
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
        return loaiSachList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtMaLoaiSach, txtTenLoaiSach;
        ImageButton iBtnOpenLoaiSach, iBtnCloseLoaiSach, iBtnUpdateLoaiSach, iBtnDeleteLoaiSach;
        RelativeLayout rlFunctionLoaiSach;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaLoaiSach = itemView.findViewById(R.id.txtMaLoaiSach);
            txtTenLoaiSach = itemView.findViewById(R.id.txtTenLoaiSach);
            iBtnOpenLoaiSach = itemView.findViewById(R.id.iBtnOpenLoaiSach);
            iBtnCloseLoaiSach = itemView.findViewById(R.id.iBtnCloseLoaiSach);
            iBtnUpdateLoaiSach = itemView.findViewById(R.id.iBtnUpdateLoaiSach);
            iBtnDeleteLoaiSach = itemView.findViewById(R.id.iBtnDeleteLoaiSach);
            rlFunctionLoaiSach = itemView.findViewById(R.id.rlFunctionLoaiSach);
        }
    }

    private void openDialogUpdateLoaiSach(LoaiSach loaiSach) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_update_loai_sach, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        TextView txtUpdateMaLoaiSach = view.findViewById(R.id.txtUpdateMaLoaiSach);
        EditText edtUpdateTenLoaiSach = view.findViewById(R.id.edtUpdateTenLoaiSach);
        TextView txtUpdateLoaiSachSub = view.findViewById(R.id.txtUpdateLoaiSachSub);
        TextView txtCancelUpdateLoaiSach = view.findViewById(R.id.txtCancelUpdateLoaiSach);

        txtUpdateMaLoaiSach.setText("Mã loại sách: " + loaiSach.getMaLoaiSach());
        edtUpdateTenLoaiSach.setText(loaiSach.getTenLoaiSach());

        txtUpdateLoaiSachSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtUpdateTenLoaiSach.getText().toString().isEmpty() | edtUpdateTenLoaiSach.getText().toString().equals("")) {
                    Toast.makeText(context, "Vui lòng nhập tên loại sách", Toast.LENGTH_SHORT).show();
                    return;
                }
                loaiSach.setTenLoaiSach(edtUpdateTenLoaiSach.getText().toString());
                if (loaiSachDAO.updateLoaiSach(loaiSach)) {
                    Toast.makeText(context, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                    loaiSachList.clear();
                    loaiSachList.addAll(loaiSachDAO.selectAllLoaiSach());
                    dialog.dismiss();
                    notifyDataSetChanged();
                }
            }
        });

        txtCancelUpdateLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
