package com.example.muhammed.sosyalyardimbagisci;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.muhammed.sosyalyardimbagisci.api.APIClient;
import com.example.muhammed.sosyalyardimbagisci.api.APIInterface;
import com.example.muhammed.sosyalyardimbagisci.ekstralar.HesapBilgileri;
import com.example.muhammed.sosyalyardimbagisci.fonksiyonlar.DialogMesajlari;
import com.example.muhammed.sosyalyardimbagisci.pojo.GirisSonuc;
import com.example.muhammed.sosyalyardimbagisci.pojo.KullaniciBilgileri;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GirisEkraniActivity extends AppCompatActivity {
    int hataKodu=-1;
    public GirisEkraniActivity(){}
    public GirisEkraniActivity(int hataKodu){
        this.hataKodu=hataKodu;
    }
    APIInterface apiInterface;
    DialogMesajlari dialogMesajlari;
    BootstrapButton btnGirisYap;
    EditText txtKullaniciAdi,txtSifre;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String TAG = GirisEkraniActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris_ekrani);
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String kayitliKullaniciBilgileri=preferences.getString("KullaniciBilgileri",null);
        if(kayitliKullaniciBilgileri!=null)
        {
            Gson gson=new Gson();
            KullaniciBilgileri kullaniciBilgileri=gson.fromJson(kayitliKullaniciBilgileri,KullaniciBilgileri.class);
            HesapBilgileri.androidToken=kullaniciBilgileri.androidToken;
            HesapBilgileri.yetkiKullaniciListesi=kullaniciBilgileri.yetkiKullaniciListesi;
            HesapBilgileri.yetkiKullaniciEkle=kullaniciBilgileri.yetkiKullaniciEkle;
            HesapBilgileri.yetkiKullaniciSil=kullaniciBilgileri.yetkiKullaniciSil;
            HesapBilgileri.yetkiKullaniciDuzenle=kullaniciBilgileri.yetkiKullaniciDuzenle;
            HesapBilgileri.yetkiSubeListesi=kullaniciBilgileri.yetkiSubeListesi;
            HesapBilgileri.yetkiSubeEkle=kullaniciBilgileri.yetkiSubeEkle;
            HesapBilgileri.yetkiSubeSil=kullaniciBilgileri.yetkiSubeSil;
            HesapBilgileri.yetkiSubeDuzenle=kullaniciBilgileri.yetkiSubeDuzenle;
            HesapBilgileri.yetkiSubeDetay=kullaniciBilgileri.yetkiSubeDetay;
            HesapBilgileri.kullaniciAdiSoyadi=kullaniciBilgileri.kullaniciAdi+" "+kullaniciBilgileri.kullaniciSoyadi;
            startActivity(new Intent(GirisEkraniActivity.this,AnaEkranActivity.class));
            return;
        }
        setContentView(R.layout.activity_giris_ekrani);
        viewOlustur();
        txtKullaniciAdi.setText("sa@aaa.com");
        txtSifre.setText("1234567");
        dialogMesajlari =new DialogMesajlari(this);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        btnGirisYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kullaniciAdi=txtKullaniciAdi.getText().toString();
                String sifre=txtSifre.getText().toString();
                if(kullaniciAdi.length()<6||!kullaniciAdi.matches(emailPattern))
                {
                    dialogMesajlari.hataMesajiGoster("Lütfen geçerli bir mail adresi giriniz...");
                }else if(sifre.length()<6){
                    dialogMesajlari.hataMesajiGoster("Şifreniz en az 6 karakter olmalıdır.");
                }else{

                    Call<GirisSonuc> call=apiInterface.girisYap(kullaniciAdi,sifre);
                    call.enqueue(new Callback<GirisSonuc>() {
                        @Override
                        public void onResponse(Call<GirisSonuc> call, Response<GirisSonuc> response) {
                            GirisSonuc sonuc=response.body();
                            if(!dialogMesajlari.hataMesajiGoster(sonuc.hataKodu))
                            {
                                Gson gson=new Gson();
                                String kullaniciBilgileriJSON=gson.toJson(sonuc.kullaniciBilgileri);
                                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                                editor.putString("KullaniciBilgileri", kullaniciBilgileriJSON);
                                editor.apply();
                                KullaniciBilgileri kullaniciBilgileri=gson.fromJson(kullaniciBilgileriJSON,KullaniciBilgileri.class);
                                HesapBilgileri.androidToken=kullaniciBilgileri.androidToken;
                                HesapBilgileri.kullaniciAdiSoyadi=kullaniciBilgileri.kullaniciAdi+" "+kullaniciBilgileri.kullaniciSoyadi;
                                startActivity(new Intent(GirisEkraniActivity.this,AnaEkranActivity.class));
                            }
                        }

                        @Override
                        public void onFailure(Call<GirisSonuc> call, Throwable t) {
                            call.cancel();
                            dialogMesajlari.hataMesajiGoster();
                        }
                    });
                }
            }
        });
    }


    public void viewOlustur(){
        btnGirisYap=findViewById(R.id.BtnGirisYap);
        txtKullaniciAdi=findViewById(R.id.TxtKullaniciAdi);
        txtSifre=findViewById(R.id.TxtSifre);
    }


}
