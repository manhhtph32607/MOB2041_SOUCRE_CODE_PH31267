package com.example.mob2041_soucre_code_ph31267.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mob2041_soucre_code_ph31267.R;
import com.example.mob2041_soucre_code_ph31267.dao.ThuThuDAO;
import com.example.mob2041_soucre_code_ph31267.model.ThuThu;

import java.util.List;

public class ThuThuAdapter extends RecyclerView.Adapter<ThuThuAdapter.ViewHolder> {

    private final Context context;
    private List<ThuThu> thuThuList;
    private final ThuThuDAO thuThuDAO;

    public ThuThuAdapter(Context context, List<ThuThu> thuThuList, ThuThuDAO thuThuDAO) {
        this.context = context;
        this.thuThuList = thuThuList;
        this.thuThuDAO = thuThuDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_thu_thu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThuThu thuThu = thuThuList.get(position);

        holder.txtMaThuThu.setText("Mã thủ thư: " + thuThu.getMaThuThu());
        holder.txtHoTenThuThu.setText("Họ và tên: " + thuThu.getHoThuThu() + " " + thuThu.getTenThuThu());
        String trangThai = thuThu.getTrangThaiThuThu() == 1 ? "Kích hoạt" : "Vô hiệu hóa";
        holder.txtTrangThaiThuThu.setText(trangThai);

        holder.iBtnUpdateStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận");
                builder.setMessage("Thay đổi trạng thái thủ thư! Tiếp tục?");

                builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int status = thuThu.getTrangThaiThuThu() == 0 ? 1 : 0;
                        thuThu.setTrangThaiThuThu(status);
                        if (thuThuDAO.updateStatusThuThu(thuThu)) {
                            Toast.makeText(context, "Đã thay đổi", Toast.LENGTH_SHORT).show();
                            thuThuList.clear();
                            thuThuList.addAll(thuThuDAO.selectAllThuThu());
                            dialog.dismiss();
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "Lỗi", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
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
        return thuThuList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtMaThuThu, txtHoTenThuThu, txtTrangThaiThuThu;
        ImageButton iBtnUpdateStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaThuThu = itemView.findViewById(R.id.txtMaThuThu);
            txtHoTenThuThu = itemView.findViewById(R.id.txtHoTenThuThu);
            txtTrangThaiThuThu = itemView.findViewById(R.id.txtTrangThaiThuThu);
            iBtnUpdateStatus = itemView.findViewById(R.id.iBtnUpdateStatus);
        }
    }
}
