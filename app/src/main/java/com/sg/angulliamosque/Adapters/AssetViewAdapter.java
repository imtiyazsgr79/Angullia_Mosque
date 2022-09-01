package com.sg.angulliamosque.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sg.angulliamosque.Activities.UpdateAssetActivity;
import com.sg.angulliamosque.R;
import com.sg.angulliamosque.models.ListObject;

import java.util.List;


public class AssetViewAdapter extends RecyclerView.Adapter<AssetViewAdapter.ViewHolder> {
    List<ListObject> name;
    Context context;

    public AssetViewAdapter(List<ListObject> names,Context context) {
        this.name = names;
        this.context=context;
    }
//
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_recycler_item, parent, false);
        ViewHolder ViewHolder = new ViewHolder(v);
        return ViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.name.setText(name.get(position).getName());
        holder.equipmentCode.setText(name.get(position).getEquipmentCode());
        holder.equipIdtv.setText(String.valueOf(name.get(position).getId()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView equipIdTextView = holder.itemView.findViewById(R.id.tv3);
                int equipId = Integer.parseInt(equipIdTextView.getText().toString());
                Intent intent = new Intent(context, UpdateAssetActivity.class);
                intent.putExtra("equipId", equipId);
                intent.putExtra("from","list");
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView equipmentCode, equipIdtv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv1);
            equipmentCode = itemView.findViewById(R.id.tv2);
            equipIdtv = itemView.findViewById(R.id.tv3);
        }
    }

}
