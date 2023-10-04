package com.example.mob2041_soucre_code_ph31267.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mob2041_soucre_code_ph31267.R;
import com.example.mob2041_soucre_code_ph31267.fragment.Top10SachFragment;
import com.example.mob2041_soucre_code_ph31267.model.Top;

import java.util.List;

public class TopAdapter extends ArrayAdapter<Top> {

    private Context context;
    private Top10SachFragment top10SachFragment;
    private List<Top> topList;

    public TopAdapter(@NonNull Context context, Top10SachFragment top10SachFragment, List<Top> topList) {
        super(context, 0, topList);
        this.context = context;
        this.top10SachFragment = top10SachFragment;
        this.topList = topList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_top_adapter, null);
        }
        final Top item = topList.get(position);
        if (item != null) {
            TextView txtTopTenSach = view.findViewById(R.id.txtTopTenSach);
            TextView txtTopSoLuong = view.findViewById(R.id.txtTopSoLuong);
            txtTopTenSach.setText("Sách: " + item.getTenSach());
            txtTopSoLuong.setText("Số lượng: "+ item.getSoLuong());
        }
        return view;
    }
}
