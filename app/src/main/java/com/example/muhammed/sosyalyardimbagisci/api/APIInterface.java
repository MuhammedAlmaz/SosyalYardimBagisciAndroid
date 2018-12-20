package com.example.muhammed.sosyalyardimbagisci.api;



import com.example.muhammed.sosyalyardimbagisci.pojo.GirisSonuc;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIInterface {

    @FormUrlEncoded
    @POST("/SosyalYardim/GirisApi/GirisYap.php")
    Call<GirisSonuc> girisYap(
            @Field("KullaniciEPosta") String kullaniciEPosta,
            @Field("Sifre") String sifre);

/*
    @FormUrlEncoded
    @POST("/SosyalYardim/GirisApi/GirisYap.php")
    Call<GirisSonuc> girisYap(
            @Field("KullaniciEPosta") String kullaniciEPosta,
            @Field("Sifre") String sifre);

    //Listeleme
    @FormUrlEncoded
    @POST("/SosyalYardim/SubeApi/SubeListe.php")
    Call<SubeListeSonuc> subeListe(@Field("AndroidToken") String androidToken);




    //Silme İşlemleri

    @FormUrlEncoded
    @POST("/SosyalYardim/IhtiyacSahibiApi/IhtiyacSahibiSil.php")
    Call<IhtiyacSahibiSilmeSonuc> ihtiyacSahibiSil(@Field("AndroidToken") String androidToken, @Field("IhtiyacSahibiID") int ihtiyacSahibiID);



    // Güncelleme İşlemleri
    @FormUrlEncoded
    @POST("/SosyalYardim/IhtiyacSahibiApi/IhtiyacSahibiGuncelle.php")
    Call<IhtiyacSahibiGuncelleSonuc> ihtiyacSahibiGuncelle(@Field("AndroidToken") String androidToken
            , @Field("IhtiyacSahibiID") int ihtiyacSahibiID
            , @Field("Adi") int adi
            , @Field("Soyadi") String soyadi
            , @Field("TelNo") String telNo
            , @Field("SehirID") String sehirID
            , @Field("Adres") int adres
            , @Field("Aciklama") int aciklama
    );

    //Ekleme İşlemleri

    @FormUrlEncoded
    @POST("/SosyalYardim/SubeApi/SubeEkle.php")
    Call<SubeEkleSonuc> subeEkle(@Field("AndroidToken") String androidToken
            , @Field("IlID") int ilID, @Field("KullaniciID") int kullaniciID);



    //Spinner

    @POST("/SosyalYardim/KullaniciApi/KullaniciSpinnerGetir.php")
    Call<KullaniciSpinnerSonuc> kullaniciSpinnerGetir(@Field("AndroidToken") String androidToken);
*/
}
