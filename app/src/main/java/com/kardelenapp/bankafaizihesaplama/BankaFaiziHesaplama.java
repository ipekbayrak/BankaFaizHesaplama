package com.kardelenapp.bankafaizihesaplama;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BankaFaiziHesaplama extends AppCompatActivity {

    private EditText editText_anapara;
    private EditText editText_faizorani;
    private EditText editText_vade;
    private EditText editText_donem;
    private EditText editText_faizTutari;
    private EditText editText_toplam;
    private Button button_hesapla;
    private AdView mAdView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banka_faizi_hesaplama);

        //mAdView = (AdView) findViewById(R.id.adView);
        editText_anapara = (EditText) findViewById(R.id.editText_AnaPara);
        editText_faizorani = (EditText) findViewById(R.id.editText_FaizOrani);
        editText_vade = (EditText) findViewById(R.id.editText_Vade);
        editText_donem = (EditText) findViewById(R.id.editText_Donem);
        editText_faizTutari = (EditText) findViewById(R.id.editText_FaizTutari);
        editText_toplam = (EditText) findViewById(R.id.editText_Toplam);

        List<String> spinnerArray2 =  new ArrayList<String>();
        spinnerArray2.add("Günlük");
        spinnerArray2.add("Haftalık");
        spinnerArray2.add("Aylık");
        spinnerArray2.add("Yıllık");

        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("Gün");
        spinnerArray.add("Hafta");
        spinnerArray.add("Ay");
        spinnerArray.add("Yıl");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner sItems_Donem = (Spinner) findViewById(R.id.spinner_Donem);
        sItems_Donem.setAdapter(adapter);
        final Spinner sItems_Vade = (Spinner) findViewById(R.id.spinner_Vade);
        sItems_Vade.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray2);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner sItems_FaizOrani = (Spinner) findViewById(R.id.spinner_FaizOrani);
        sItems_FaizOrani.setAdapter(adapter2);

        sItems_Donem.setSelection(2);
        sItems_Vade.setSelection(3);
        sItems_FaizOrani.setSelection(3);


        LinearLayout layout = (LinearLayout) findViewById(R.id.adsContainer);
        AdsController adsController = new AdsController(this);
        adsController.loadBanner(layout);

        Button button= (Button) findViewById(R.id.button_Hesapla);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double donem_gunu = 0;
                double vade_gunu = 0;
                double faiz_gunu = 0;
                double anapara = Double.parseDouble(editText_anapara.getText().toString()) ;

                double faizOrani =  Double.parseDouble(editText_faizorani.getText().toString());
                double vade =  Double.parseDouble(editText_vade.getText().toString());
                double donem =  Double.parseDouble(editText_donem.getText().toString());

                String selected_Donem = sItems_Donem.getSelectedItem().toString();
                if (selected_Donem.equals("Gün")) {
                    donem_gunu= 1;
                }
                else if (selected_Donem.equals("Hafta")) {
                    donem_gunu= 7;
                }
                else if (selected_Donem.equals("Ay")) {
                    donem_gunu= 30;
                }
                else if (selected_Donem.equals("Yıl")) {
                    donem_gunu= 365;
                }

                String selected_Vade = sItems_Vade.getSelectedItem().toString();
                if (selected_Vade.equals("Gün")) {
                    vade_gunu = 1;
                }
                else if (selected_Vade.equals("Hafta")) {
                    vade_gunu = 7;
                }
                else if (selected_Vade.equals("Ay")) {
                    vade_gunu = 30;
                }
                else if (selected_Vade.equals("Yıl")) {
                    vade_gunu = 365;
                }

                String selected_FaizOrani = sItems_FaizOrani.getSelectedItem().toString();
                if (selected_FaizOrani.equals("Günlük")) {
                    faiz_gunu = 1;
                }
                else if (selected_FaizOrani.equals("Haftalık")) {
                    faiz_gunu = 7;
                }
                else if (selected_FaizOrani.equals("Aylık")) {
                    faiz_gunu = 30;
                }
                else if (selected_FaizOrani.equals("Yıllık")) {
                    faiz_gunu = 365;
                }


                double sonuc = 0;

                double hesaplananAnapara = anapara;
                for (double i = donem * donem_gunu; i<=vade_gunu * vade; i+= donem * donem_gunu){
                    hesaplananAnapara += (hesaplananAnapara / 100 * faizOrani) / (faiz_gunu / donem_gunu);
                }

                //Faiz Tutarı = AnaPara x (1 + Dönemlik Faiz Oranı)Dönem Sayısı - AnaPara
                //sonuc = (anapara * Math.pow(1 + faizOrani,donem))     ;

                editText_faizTutari.setText(String.format("%.2f", hesaplananAnapara - anapara)  );
                editText_toplam.setText(String.format("%.2f", hesaplananAnapara) );

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {

            case R.id.about:
                Intent myIntent = new Intent(this, Hakkinda.class);
                this.startActivity(myIntent);

                break;
        }
        return true;
    }


}
