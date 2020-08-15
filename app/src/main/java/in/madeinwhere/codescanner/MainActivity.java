package in.madeinwhere.codescanner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import in.madeinwhere.codescanner.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding b;
    private static final String TAG = "MainActivity";
    String ctr = "";
    Drawable flag;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_main);
        b.webview.getSettings().setJavaScriptEnabled(true);
        b.webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
//                return super.shouldOverrideUrlLoading(view, request);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.e(TAG, "onPageStarted: " + url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.e(TAG, "onPageFinished: " + url);
            }
        });
        b.webview.loadUrl(getString(R.string.website_url));
        b.scan.setOnClickListener(v -> {
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
            integrator.setPrompt("Scan a barcode");
            integrator.setOrientationLocked(true);
            integrator.setCameraId(0);  // Use a specific camera of the device
            integrator.setBeepEnabled(false);
            integrator.setBarcodeImageEnabled(true);
            integrator.initiateScan();
        });
    }

    // Get the results:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                String a = result.getContents().replaceAll(" ", "").trim().substring(0, 3);
                int b = Integer.parseInt(a);
//                int c = Integer.parseInt("s32");
                Log.e(TAG, "onActivityResult: " + a);
                Log.e(TAG, "onActivityResult: " + b);
//                Log.e(TAG, "onActivityResult: " + c);
                showFlag(b,result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void showFlag(int code, String fullnumber){
        flag=null;
        String country="Origin: "+getCountry(code);
        String fullcode="The code is: "+fullnumber;
        AlertDialog.Builder alertDialog= new AlertDialog.Builder(this);
        View v= LayoutInflater.from(MainActivity.this).inflate(R.layout.country,null);
        TextView countryTextV=v.findViewById(R.id.country);
        TextView codeTextV=v.findViewById(R.id.code);
        ImageView flagImage=v.findViewById(R.id.flagx);
        alertDialog.setView(v);
        if (flag!=null){
            flagImage.setImageDrawable(flag);
        }
        countryTextV.setText(country);
        codeTextV.setText(fullcode);
        alertDialog.show();
    }
    public String getCountry(int code) {
        flag = getDrawable(R.drawable.us);
        if (code >= 0 && code <= 19) {
            ctr = "UPC-A compatible -  United States and  Canada";
            flag = getDrawable(R.drawable.us);
        } else if (code >= 20 && code <= 29) {
            ctr = "	UPC-A compatible - Used to issue restricted circulation numbers within a geographic region[m]";
            flag = getDrawable(R.drawable.us);
        } else if (code >= 30 && code <= 39) {
            ctr = "UPC-A compatible -  United States drugs (see United States National Drug Code)";
            flag = getDrawable(R.drawable.us);
        } else if (code >= 40 && code <= 49) {
            ctr = "UPC-A compatible - Used to issue restricted circulation numbers within a geographic region[m]";
            flag = getDrawable(R.drawable.us);
        } else if (code >= 50 && code <= 59) {
            ctr = "UPC-A compatible - GS1 US reserved for future use";
            flag = getDrawable(R.drawable.us);
        } else if (code >= 60 && code <= 99) {
            ctr = "UPC-A compatible -  United States and  Canada";
            flag = getDrawable(R.drawable.ca);
        } else if (code >= 100 && code <= 139) {
            ctr = " United States";
            flag = getDrawable(R.drawable.us);
        } else if (code >= 200 && code <= 299) {
            ctr = "Used to issue GS1 restricted circulation number within a geographic region[m]";
            flag = getDrawable(R.drawable.us);
        } else if (code >= 300 && code <= 379) {
            ctr = " France and  Monaco";
            flag = getDrawable(R.drawable.fr);
        } else if (code == 380) {
            ctr = "	 Bulgaria";
            flag = getDrawable(R.drawable.bg);
        } else if (code == 383) {
            ctr = " Slovenia";
            flag = getDrawable(R.drawable.si);
        } else if (code == 385) {
            ctr = " Croatia";
            flag = getDrawable(R.drawable.hr);
        } else if (code == 387) {
            ctr = " Bosnia and Herzegovina";
            flag = getDrawable(R.drawable.ba);
        } else if (code == 389) {
            ctr = " Montenegro";
            flag = getDrawable(R.drawable.me);
        } else if (code == 390) {
            ctr = " Kosovo";
            flag = getDrawable(R.drawable.kp);
        } else if (code >= 400 && code <= 440) {
            ctr = " Germany (440 code inherited from old  East Germany on reunification, 1990)";
            flag = getDrawable(R.drawable.de);
        } else if (code >= 450 && code <= 459) {
            ctr = " Japan (new Japanese Article Number range)";
            flag = getDrawable(R.drawable.jp);
        } else if (code >= 460 && code <= 469) {
            ctr = " Russia (barcodes inherited from the  Soviet Union)";
            flag = getDrawable(R.drawable.ru);
        } else if (code == 470) {
            ctr = "	 Kyrgyzstan";
            flag = getDrawable(R.drawable.kg);
        } else if (code == 471) {
            ctr = " Taiwan";
            flag = getDrawable(R.drawable.tw);
        } else if (code == 474) {
            ctr = " Estonia";
            flag = getDrawable(R.drawable.ee);
        } else if (code == 475) {
            ctr = " Latvia";
            flag = getDrawable(R.drawable.lv);
        } else if (code == 476) {
            ctr = " Azerbaijan";
            flag = getDrawable(R.drawable.az);
        } else if (code == 477) {
            ctr = " Lithuania";
            flag = getDrawable(R.drawable.lt);
        } else if (code == 478) {
            ctr = " Uzbekistan";
            flag = getDrawable(R.drawable.uz);
        } else if (code == 479) {
            ctr = " Sri Lanka";
            flag = getDrawable(R.drawable.lk);
        } else if (code == 480) {
            ctr = " Philippines";
            flag = getDrawable(R.drawable.ph);
        } else if (code == 481) {
            ctr = " Belarus";
            flag = getDrawable(R.drawable.by);
        } else if (code == 482) {
            ctr = " Ukraine";
            flag = getDrawable(R.drawable.ua);
        } else if (code == 483) {
            ctr = " Turkmenistan";
            flag = getDrawable(R.drawable.tm);
        } else if (code == 484) {
            ctr = " Moldova";
            flag = getDrawable(R.drawable.md);
        } else if (code == 485) {
            ctr = " Armenia";
            flag = getDrawable(R.drawable.am);
        } else if (code == 486) {
            ctr = " Georgia";
            flag = getDrawable(R.drawable.ge);
        } else if (code == 487) {
            ctr = " Kazakhstan";
            flag = getDrawable(R.drawable.kz);
        } else if (code == 488) {
            ctr = " Tajikistan";
            flag = getDrawable(R.drawable.tj);
        } else if (code == 489) {
            ctr = " Hong Kong";
            flag = getDrawable(R.drawable.hk);
        } else if (code >= 490 && code <= 499) {
            ctr = " Japan (original Japanese Article Number range)";
            flag = getDrawable(R.drawable.jp);
        } else if (code >= 500 && code <= 509) {
            ctr = " United Kingdom";
            flag = getDrawable(R.drawable.gb);
        } else if (code >= 520 && code <= 521) {
            ctr = " Greece";
            flag = getDrawable(R.drawable.gr);
        } else if (code == 528) {
            ctr = " Lebanon";
            flag = getDrawable(R.drawable.lb);
        } else if (code == 529) {
            ctr = " Cyprus";
            flag = getDrawable(R.drawable.cy);
        } else if (code == 530) {
            ctr = " Albania";
            flag = getDrawable(R.drawable.al);
        } else if (code == 531) {
            ctr = " North Macedonia";
            flag = getDrawable(R.drawable.mk);
        } else if (code == 535) {
            ctr = " Malta";
            flag = getDrawable(R.drawable.mt);
        } else if (code == 539) {
            ctr = " Ireland";
            flag = getDrawable(R.drawable.gb);
        } else if (code >= 540 && code <= 549) {
            ctr = " Belgium and  Luxembourg";
            flag = getDrawable(R.drawable.be);
        } else if (code == 560) {
            ctr = " Portugal";
            flag = getDrawable(R.drawable.pt);
        } else if (code == 569) {
            ctr = " Iceland";
            flag = getDrawable(R.drawable.is);
        } else if (code >= 570 && code <= 579) {
            ctr = " Denmark , Faroe Islands and Greenland";
            flag = getDrawable(R.drawable.dk);
        } else if (code == 590) {
            ctr = " Poland";
            flag = getDrawable(R.drawable.pl);
        } else if (code == 594) {
            ctr = "	 Romania";
            flag = getDrawable(R.drawable.ro);
        } else if (code == 599) {
            ctr = " Hungary";
            flag = getDrawable(R.drawable.hu);
        } else if (code >= 600 && code <= 601) {
            ctr = " South Africa";
            flag = getDrawable(R.drawable.za);
        } else if (code == 603) {
            ctr = " Ghana";
            flag = getDrawable(R.drawable.gh);
        } else if (code == 604) {
            ctr = " Senegal";
            flag = getDrawable(R.drawable.sn);
        } else if (code == 608) {
            ctr = " Bahrain";
            flag = getDrawable(R.drawable.bh);
        } else if (code == 609) {
            ctr = " Mauritius";
            flag = getDrawable(R.drawable.mu);
        } else if (code == 611) {
            ctr = " Morocco";
            flag = getDrawable(R.drawable.ma);
        } else if (code == 613) {
            ctr = " Algeria";
            flag = getDrawable(R.drawable.al);
        } else if (code == 615) {
            ctr = " Nigeria";
            flag = getDrawable(R.drawable.ng);
        } else if (code == 616) {
            ctr = " Kenya";
            flag = getDrawable(R.drawable.ke);
        } else if (code == 617) {
            ctr = " Cameroon";
            flag = getDrawable(R.drawable.cm);
        } else if (code == 618) {
            ctr = " Ivory Coast";
            flag = getDrawable(R.drawable.ci);
        } else if (code == 619) {
            ctr = " Tunisia";
            flag = getDrawable(R.drawable.tn);
        } else if (code == 620) {
            ctr = " Tanzania";
            flag = getDrawable(R.drawable.tz);
        } else if (code == 621) {
            ctr = " Syria";
            flag = getDrawable(R.drawable.sy);
        } else if (code == 622) {
            ctr = " Egypt";
            flag = getDrawable(R.drawable.eg);
        } else if (code == 623) {
            ctr = " Brunei";
            flag = getDrawable(R.drawable.bn);
        } else if (code == 624) {
            ctr = " Libya";
            flag = getDrawable(R.drawable.ly);
        } else if (code == 625) {
            ctr = " Jordan";
            flag = getDrawable(R.drawable.jo);
        } else if (code == 626) {
            ctr = " Iran";
            flag = getDrawable(R.drawable.ir);
        } else if (code == 627) {
            ctr = " Kuwait";
            flag = getDrawable(R.drawable.kw);
        } else if (code == 628) {
            ctr = " Saudi Arabia";
            flag = getDrawable(R.drawable.sa);
        } else if (code == 629) {
            ctr = " United Arab Emirates";
            flag = getDrawable(R.drawable.ae);
        } else if (code == 630) {
            ctr = " Qatar";
            flag = getDrawable(R.drawable.qa);
        } else if (code >= 640 && code <= 649) {
            ctr = " Finland (sometimes used by Romanian manufacturers)";
            flag = getDrawable(R.drawable.fi);
        } else if (code >= 690 && code <= 699) {
            ctr = " People's Republic of China";
            flag = getDrawable(R.drawable.cn);
        } else if (code >= 700 && code <= 709) {
            ctr = " Norway";
            flag = getDrawable(R.drawable.no);
        } else if (code == 729) {
            ctr = " Israel";
            flag = getDrawable(R.drawable.il);
        } else if (code >= 730 && code <= 739) {
            ctr = " Sweden : EAN/GS1 Sweden";
            flag = getDrawable(R.drawable.se);
        } else if (code == 740) {
            ctr = " Guatemala";
            flag = getDrawable(R.drawable.gt);
        } else if (code == 741) {
            ctr = "	 El Salvador";
            flag = getDrawable(R.drawable.sv);
        } else if (code == 742) {
            ctr = " Honduras";
            flag = getDrawable(R.drawable.hn);
        } else if (code == 743) {
            ctr = " Nicaragua";
            flag = getDrawable(R.drawable.ni);
        } else if (code == 744) {
            ctr = " Costa Rica";
            flag = getDrawable(R.drawable.cr);
        } else if (code == 745) {
            ctr = " Panama";
            flag = getDrawable(R.drawable.pa);
        } else if (code == 746) {
            ctr = " Dominican Republic";
            flag = getDrawable(R.drawable.dm);
        } else if (code == 750) {
            ctr = "	 Mexico";
            flag = getDrawable(R.drawable.mx);
        } else if (code >= 754 && code <= 755) {
            ctr = " Canada";
            flag = getDrawable(R.drawable.ca);
        } else if (code == 759) {
            ctr = " Venezuela";
            flag = getDrawable(R.drawable.ve);
        } else if (code >= 760 && code <= 769) {
            ctr = "  Switzerland and  Liechtenstein";
            flag = getDrawable(R.drawable.ch);
        } else if (code >= 770 && code <= 771) {
            ctr = " Colombia";
            flag = getDrawable(R.drawable.co);
        } else if (code == 773) {
            ctr = "	 Uruguay";
            flag = getDrawable(R.drawable.uy);
        } else if (code == 775) {
            ctr = "	 Peru";
            flag = getDrawable(R.drawable.pe);
        } else if (code == 777) {
            ctr = "	 Bolivia";
            flag = getDrawable(R.drawable.bo);
        } else if (code >= 778 && code <= 779) {
            ctr = " Argentina";
            flag = getDrawable(R.drawable.ar);
        } else if (code == 780) {
            ctr = "	 Chile";
            flag = getDrawable(R.drawable.cl);
        } else if (code == 784) {
            ctr = "	 Paraguay";
            flag = getDrawable(R.drawable.py);
        } else if (code == 786) {
            ctr = "	 Ecuador";
            flag = getDrawable(R.drawable.ec);
        } else if (code >= 789 && code <= 790) {
            ctr = " Brazil";
            flag = getDrawable(R.drawable.br);
        } else if (code >= 800 && code <= 839) {
            ctr = " Italy,  San Marino and   Vatican City";
            flag = getDrawable(R.drawable.it);
        } else if (code >= 840 && code <= 849) {
            ctr = " Spain and  Andorra";
            flag = getDrawable(R.drawable.es);
        } else if (code == 850) {
            ctr = " Cuba";
            flag = getDrawable(R.drawable.cu);
        } else if (code == 858) {
            ctr = " Slovakia";
            flag = getDrawable(R.drawable.sk);
        } else if (code == 859) {
            ctr = " Czech Republic";
            flag = getDrawable(R.drawable.cz);
        } else if (code == 860) {
            ctr = "Serbia";
            flag = getDrawable(R.drawable.rs);
        } else if (code == 865) {
            ctr = " Mongolia";
            flag = getDrawable(R.drawable.mn);
        } else if (code == 867) {
            ctr = " North Korea";
            flag = getDrawable(R.drawable.kr);
        } else if (code >= 868 && code <= 869) {
            ctr = " Turkey";
            flag = getDrawable(R.drawable.tr);
        } else if (code >= 870 && code <= 879) {
            ctr = " Netherlands";
            flag = getDrawable(R.drawable.nl);
        } else if (code == 880) {
            ctr = "	 South Korea";
            flag = getDrawable(R.drawable.kp);
        } else if (code == 883) {
            ctr = " Myanmar";
            flag = getDrawable(R.drawable.mm);
        } else if (code == 884) {
            ctr = "	 Cambodia";
            flag = getDrawable(R.drawable.kh);
        } else if (code == 885) {
            ctr = "	 Thailand";
            flag = getDrawable(R.drawable.th);
        } else if (code == 888) {
            ctr = "	 Singapore";
            flag = getDrawable(R.drawable.sg);
        } else if (code == 890) {
            ctr = " India";
            flag = getDrawable(R.drawable.in);
        } else if (code == 893) {
            ctr = "	 Vietnam";
            flag = getDrawable(R.drawable.vn);
        } else if (code == 896) {
            ctr = " Pakistan";
            flag = getDrawable(R.drawable.pk);
        } else if (code == 899) {
            ctr = " Indonesia";
            flag = getDrawable(R.drawable.id);
        } else if (code >= 900 && code <= 919) {
            ctr = " Austria";
            flag = getDrawable(R.drawable.at);
        } else if (code >= 930 && code <= 939) {
            ctr = " Australia";
            flag = getDrawable(R.drawable.au);
        } else if (code >= 940 && code <= 949) {
            ctr = " New Zealand";
            flag = getDrawable(R.drawable.nz);
        } else if (code == 950) {
            ctr = "GS1 Global Office: Special applications";
            flag = getDrawable(R.drawable.us);
        } else if (code == 951) {
            ctr = "Used to issue General Manager Numbers for the EPC General Identifier (GID) scheme as defined by the EPC Tag Data Standard";
            flag = getDrawable(R.drawable.us);
        } else if (code == 952) {
            ctr = "Used for demonstrations and examples of the GS1 system";
            flag = getDrawable(R.drawable.us);
        } else if (code == 955) {
            ctr = "	 Malaysia";
            flag = getDrawable(R.drawable.my);
        } else if (code == 958) {
            ctr = " Macau";
            flag = getDrawable(R.drawable.mc);
        } else if (code >= 960 && code <= 961) {
            ctr = "GS1 UK Office: GTIN-8 allocations";
            flag = getDrawable(R.drawable.us);
        } else if (code >= 962 && code <= 969) {
            ctr = "GS1 Global Office: GTIN-8 allocations";
            flag = getDrawable(R.drawable.us);
        } else if (code == 977) {
            ctr = "Serial publications (ISSN)";
            flag = getDrawable(R.drawable.us);
        } else if (code >= 978 && code <= 979) {
            ctr = "Bookland (ISBN) – 979-0 used for sheet music (Musicland, ISMN-13, replaces deprecated ISMN M- numbers)";
            flag = getDrawable(R.drawable.us);
        } else if (code == 980) {
            ctr = "Refund receipts";
            flag = getDrawable(R.drawable.us);
        } else if (code >= 981 && code <= 984) {
            ctr = "GS1 coupon identification for common currency areas";
            flag = getDrawable(R.drawable.us);
        } else if (code >= 990 && code <= 999) {
            ctr = "	GS1 coupon identification ";
            flag = getDrawable(R.drawable.us);
        } else {
            ctr = "";
            flag = getDrawable(R.drawable.us);
        }
        return ctr.trim();
    }
}