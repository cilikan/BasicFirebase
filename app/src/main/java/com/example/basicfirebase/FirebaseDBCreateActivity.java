package com.example.basicfirebase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.text.TextUtils.*;


public class FirebaseDBCreateActivity extends AppCompatActivity {
    //variabel firebase database
    private DatabaseReference databaseReference;

    //variabel edittext+button
    private Button btn_submit;
    private EditText edt_nama;
    private EditText edt_merk;
    private EditText edt_harga;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_create);

        //inisialisasi edittext+button
        edt_nama = findViewById(R.id.edt_nama);
        edt_merk = findViewById(R.id.edt_merk);
        edt_harga = findViewById(R.id.edt_harga);
        btn_submit = findViewById(R.id.btn_submit);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        final Barang barang = (Barang)getIntent().getSerializableExtra("data");
        if(barang != null){
            edt_nama.setText(barang.getNama());
            edt_merk.setText(barang.getMerk());
            edt_harga.setText(barang.getHarga());
            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    barang.setNama(edt_nama.getText().toString());
                    barang.setMerk(edt_merk.getText().toString());
                    barang.setHarga(edt_harga.getText().toString());

                    updateBarang(barang);
                }
            });
        }
        else {
            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!isEmpty(edt_nama.getText().toString()) && !isEmpty(edt_merk.getText().toString()) && !isEmpty(edt_harga.getText().toString())){
                        submitBarang(new Barang(edt_nama.getText().toString(), edt_merk.getText().toString(), edt_harga.getText().toString()));
                    }
                    else {
                        Snackbar.make(findViewById(R.id.btn_submit), "Data barang tidak boleh kosong",Snackbar.LENGTH_LONG).show();
                        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(edt_nama.getWindowToken(),0);
                    }
                }
            });
        }
    }

    private boolean isEmpty(String s){
        return TextUtils.isEmpty(s);
    }
    private void updateBarang(Barang barang){
        databaseReference.child("barang")
                .child(barang.getKey())
                .setValue(barang)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Snackbar.make(findViewById(R.id.btn_submit), "Update Berhasil",Snackbar.LENGTH_LONG).setAction("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        }).show();
                    }
                });
    }
    private void submitBarang(Barang barang){
        //kirim data ke firebase, dan menjalankan onSuccessListener ketika data berhasil ditambahkan
        databaseReference.child("barang").push().setValue(barang).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                edt_nama.setText("");
                edt_merk.setText("");
                edt_harga.setText("");
                Snackbar.make(findViewById(R.id.btn_submit), "Data berhasil ditambahkan", Snackbar.LENGTH_LONG).show();

            }
        });
    }

    public static Intent getActIntent(Activity activity){
        //pengambilan Intent
        return new Intent(activity, FirebaseDBCreateActivity.class);
    }

}
