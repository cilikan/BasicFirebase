package com.example.basicfirebase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FirebaseDBReadSingleActivity extends AppCompatActivity {
    private Button btn_submit;
    private EditText edt_nama;
    private EditText edt_merk;
    private EditText edt_harga;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_create);
        edt_nama = findViewById(R.id.edt_nama);
        edt_merk = findViewById(R.id.edt_merk);
        edt_harga = findViewById(R.id.edt_harga);
        btn_submit = findViewById(R.id.btn_submit);

        edt_nama.setEnabled(false);
        edt_merk.setEnabled(false);
        edt_harga.setEnabled(false);
        btn_submit.setVisibility(View.GONE);

        Barang barang = (Barang)getIntent().getSerializableExtra("data");
        if(barang != null){
            edt_nama.setText(barang.getNama());
            edt_merk.setText(barang.getMerk());
            edt_harga.setText(barang.getHarga());
        }
    }
    public static Intent getActIntent(Activity activity){
        return new Intent(activity, FirebaseDBReadSingleActivity.class);
    }
}
