package com.example.basicfirebase;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterBarangRecyclerView extends RecyclerView.Adapter<AdapterBarangRecyclerView.ViewHolder>{
    private ArrayList<Barang> daftarbarang;
    private Context context;
    FirebaseDataListener listener;

    public AdapterBarangRecyclerView(ArrayList<Barang> barangs, Context context1){

        daftarbarang = barangs;
        context = context1;
        listener = (FirebaseDBReadActivity)context1;
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        CardView cvMain;
        ViewHolder(View v){
            super(v);
            tvTitle = v.findViewById(R.id.txt_nama);
            cvMain = v.findViewById(R.id.cv_main);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){
        final String name = daftarbarang.get(position).getNama();
        System.out.println("data barang"+position+daftarbarang.size());
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(FirebaseDBReadSingleActivity.getActIntent((Activity)context).putExtra("data",daftarbarang.get(position)));

            }
        });
        holder.tvTitle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_view);
                dialog.setTitle("Pilih");
                dialog.show();

                Button btn_edit = (Button)dialog.findViewById(R.id.btn_edit);
                Button btn_delete = (Button)dialog.findViewById(R.id.btn_delete);

                btn_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        context.startActivity(FirebaseDBCreateActivity.getActIntent((Activity)context).putExtra("data", daftarbarang.get(position)));

                    }
                });

                btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        listener.onDeleteData(daftarbarang.get(position),position);
                    }
                });
                return true;
            }
        });
        holder.tvTitle.setText(name);
    }
    @Override
    public int getItemCount(){
        return daftarbarang.size();
    }
    public interface FirebaseDataListener{
        void onDeleteData(Barang barang, int position);
    }


}
