package com.example.basicfirebase;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Barang implements Serializable {
    private String nama;
    private String merk;
    private String harga;
    private String key;

    public Barang(){

    }
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @NonNull
    @Override
    public String toString() {
        return " " +nama+ "\n" + " " +merk+ " " +harga;
    }
    public Barang(String nm, String mrk, String hrg){
        nama = nm;
        merk = mrk;
        harga = hrg;
    }
}
