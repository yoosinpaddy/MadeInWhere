package in.madeinwhere.codescanner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import in.madeinwhere.codescanner.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding b;
    private static final String TAG = "MainActivity";

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
                int c = Integer.parseInt("s32");
                Log.e(TAG, "onActivityResult: " + a);
                Log.e(TAG, "onActivityResult: " + b);
                Log.e(TAG, "onActivityResult: " + c);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public String showCountry(int code) {
        String ctr = "";
        if (code >= 0 && code <= 19) {
            ctr = " 	UPC-A compatible -  United States and  Canada";
        } else if (code >= 20 && code <= 29) {
            ctr = "	UPC-A compatible - Used to issue restricted circulation numbers within a geographic region[m]";
        } else if (code >= 30 && code <= 39) {
            ctr = " 	UPC-A compatible -  United States drugs (see United States National Drug Code)";
        } else if (code >= 40 && code <= 49) {
            ctr = " 	UPC-A compatible - Used to issue restricted circulation numbers within a geographic region[m]";
        } else if (code >= 50 && code <= 59) {
            ctr = " 	UPC-A compatible - GS1 US reserved for future use";
        } else if (code >= 60 && code <= 99) {
            ctr = " 	UPC-A compatible -  United States and  Canada";
        } else if (code >= 100 && code <= 139) {
            ctr = " 	 United States";
        } else if (code >= 200 && code <= 299) {
            ctr = " 	Used to issue GS1 restricted circulation number within a geographic region[m]";
        } else if (code >= 300 && code <= 379) {
            ctr = " 	 France and  Monaco";
        } else if (code == 380) {
            ctr = "	 Bulgaria";
        } else if (code == 383) {
            ctr = " 	 Slovenia";
        } else if (code == 385) {
            ctr = " 	 Croatia";
        } else if (code == 387) {
            ctr = " 	 Bosnia and Herzegovina";
        } else if (code == 389) {
            ctr = " 	 Montenegro";
        } else if (code == 390) {
            ctr = " 	 Kosovo";
        } else if (code >= 400 && code <= 440) {
            ctr = " 	 Germany (440 code inherited from old  East Germany on reunification, 1990)";
        } else if (code >= 450 && code <= 459) {
            ctr = " 	 Japan (new Japanese Article Number range)";
        } else if (code >= 460 && code <= 469) {
            ctr = " 	 Russia (barcodes inherited from the  Soviet Union)";
        } else if (code == 470) {
            ctr = "	 Kyrgyzstan";
        } else if (code == 471) {
            ctr = " 	 Taiwan";
        } else if (code == 474) {
            ctr = " 	 Estonia";
        } else if (code == 475) {
            ctr = " 	 Latvia";
        } else if (code == 476) {
            ctr = " 	 Azerbaijan";
        } else if (code == 477) {
            ctr = " 	 Lithuania";
        } else if (code == 478) {
            ctr = " 	 Uzbekistan";
        } else if (code == 479) {
            ctr = " 	 Sri Lanka";
        } else if (code == 480) {
            ctr = " 	 Philippines";
        } else if (code == 481) {
            ctr = " 	 Belarus";
        } else if (code == 482) {
            ctr = " 	 Ukraine";
        } else if (code == 483) {
            ctr = " 	 Turkmenistan";
        } else if (code == 484) {
            ctr = " 	 Moldova";
        } else if (code == 485) {
            ctr = " 	 Armenia";
        } else if (code == 486) {
            ctr = " 	 Georgia";
        } else if (code == 487) {
            ctr = " 	 Kazakhstan";
        } else if (code == 488) {
            ctr = " 	 Tajikistan";
        } else if (code == 489) {
            ctr = " 	 Hong Kong";
        } else if (code >= 490 && code <= 499) {
            ctr = " 	 Japan (original Japanese Article Number range)";
        } else if (code >= 500 && code <= 509) {
            ctr = " 	 United Kingdom";
        } else if (code >= 520 && code <= 521) {
            ctr = " 	 Greece";
        } else if (code == 528) {
            ctr = " 	 Lebanon";
        } else if (code == 529) {
            ctr = " 	 Cyprus";
        } else if (code == 530) {
            ctr = " 	 Albania";
        } else if (code == 531) {
            ctr = " 	 North Macedonia";
        } else if (code == 535) {
            ctr = " 	 Malta";
        } else if (code == 539) {
            ctr = " 	 Ireland";
        } else if (code >= 540 && code <= 549) {
            ctr = " 	 Belgium and  Luxembourg";
        } else if (code == 560) {
            ctr = " 	 Portugal";
        } else if (code == 569) {
            ctr = " 	 Iceland";
        } else if (code >= 570 && code <= 579) {
            ctr = " 	 Denmark , Faroe Islands and Greenland";
        } else if (code == 590) {
            ctr = " 	 Poland";
        } else if (code == 594) {
            ctr = "	 Romania";
        } else if (code == 599) {
            ctr = " 	 Hungary";
        } else if (code >= 600 && code <= 601) {
            ctr = " 	 South Africa";
        } else if (code == 603) {
            ctr = " 	 Ghana";
        } else if (code == 604) {
            ctr = " 	 Senegal";
        } else if (code == 608) {
            ctr = " 	 Bahrain";
        } else if (code == 609) {
            ctr = " 	 Mauritius";
        } else if (code == 611) {
            ctr = " 	 Morocco";
        } else if (code == 613) {
            ctr = " 	 Algeria";
        } else if (code == 615) {
            ctr = " 	 Nigeria";
        } else if (code == 616) {
            ctr = " 	 Kenya";
        } else if (code == 617) {
            ctr = " 	 Cameroon";
        } else if (code == 618) {
            ctr = " 	 Ivory Coast";
        } else if (code == 619) {
            ctr = " 	 Tunisia";
        } else if (code == 620) {
            ctr = " 	 Tanzania";
        } else if (code == 621) {
            ctr = " 	 Syria";
        } else if (code == 622) {
            ctr = " 	 Egypt";
        } else if (code == 623) {
            ctr = " 	 Brunei";
        } else if (code == 624) {
            ctr = " 	 Libya";
        } else if (code == 625) {
            ctr = " 	 Jordan";
        } else if (code == 626) {
            ctr = " 	 Iran";
        } else if (code == 627) {
            ctr = " 	 Kuwait";
        } else if (code == 628) {
            ctr = " 	 Saudi Arabia";
        } else if (code == 629) {
            ctr = " 	 United Arab Emirates";
        } else if (code == 630) {
            ctr = " 	 Qatar";
        } else if (code >= 640 && code <= 649) {
            ctr = " 	 Finland (sometimes used by Romanian manufacturers)";
        } else if (code >= 690 && code <= 699) {
            ctr = " 	 People's Republic of China";
        } else if (code >= 700 && code <= 709) {
            ctr = " 	 Norway";
        } else if (code == 729) {
            ctr = " 	 Israel";
        } else if (code >= 730 && code <= 739) {
            ctr = " 	 Sweden : EAN/GS1 Sweden";
        } else if (code == 740) {
            ctr = " 	 Guatemala";
        } else if (code == 741) {
            ctr = "	 El Salvador";
        } else if (code == 742) {
            ctr = " 	 Honduras";
        } else if (code == 743) {
            ctr = " 	 Nicaragua";
        } else if (code == 744) {
            ctr = " 	 Costa Rica";
        } else if (code == 745) {
            ctr = " 	 Panama";
        } else if (code == 746) {
            ctr = " 	 Dominican Republic";
        } else if (code == 750) {
            ctr = "	 Mexico";
        } else if (code >= 754 && code <= 755) {
            ctr = " 	 Canada";
        } else if (code == 759) {
            ctr = " 	 Venezuela";
        } else if (code >= 760 && code <= 769) {
            ctr = " 	  Switzerland and  Liechtenstein";
        } else if (code >= 770 && code <= 771) {
            ctr = " 	 Colombia";
        } else if (code == 773) {
            ctr = "	 Uruguay";
        } else if (code == 775) {
            ctr = "	 Peru";
        } else if (code == 777) {
            ctr = "	 Bolivia";
        } else if (code >= 778 && code <= 779) {
            ctr = " 	 Argentina";
        } else if (code == 780) {
            ctr = "	 Chile";
        } else if (code == 784) {
            ctr = "	 Paraguay";
        } else if (code == 786) {
            ctr = "	 Ecuador";
        } else if (code >= 789 && code <= 790) {
            ctr = " 	 Brazil";
        } else if (code >= 800 && code <= 839) {
            ctr = " 	 Italy,  San Marino and   Vatican City";
        } else if (code >= 840 && code <= 849) {
            ctr = " 	 Spain and  Andorra";
        } else if (code == 850) {
            ctr = " 	 Cuba";
        } else if (code == 858) {
            ctr = " 	 Slovakia";
        } else if (code == 859) {
            ctr = " 	 Czech Republic";
        } else if (code == 860) {
            ctr = "Serbia";

        } else if (code == 865) {
            ctr = " 	 Mongolia";
        } else if (code == 867) {
            ctr = " 	 North Korea";
        } else if (code >= 868 && code <= 869) {
            ctr = " 	 Turkey";
        } else if (code >= 870 && code <= 879) {
            ctr = " 	 Netherlands";
        } else if (code == 880) {
            ctr = "	 South Korea";
        } else if (code == 883) {
            ctr = " 	 Myanmar";
        } else if (code == 884) {
            ctr = "	 Cambodia";
        } else if (code == 885) {
            ctr = "	 Thailand";
        } else if (code == 888) {
            ctr = "	 Singapore";
        } else if (code == 890) {
            ctr = " 	 India";
        } else if (code == 893) {
            ctr = "	 Vietnam";
        } else if (code == 896) {
            ctr = " 	 Pakistan";
        } else if (code == 899) {
            ctr = " 	 Indonesia";
        } else if (code >= 900 && code <= 919) {
            ctr = " 	 Austria";
        } else if (code >= 930 && code <= 939) {
            ctr = " 	 Australia";
        } else if (code >= 940 && code <= 949) {
            ctr = " 	 New Zealand";
        } else if (code == 950) {
            ctr = " 	GS1 Global Office: Special applications";
        } else if (code == 951) {
            ctr = " 	Used to issue General Manager Numbers for the EPC General Identifier (GID) scheme as defined by the EPC Tag Data Standard";
        } else if (code == 952) {
            ctr = " 	Used for demonstrations and examples of the GS1 system";
        } else if (code == 955) {
            ctr = "	 Malaysia";
        } else if (code == 958) {
            ctr = " 	 Macau";
        } else if (code >= 960 && code <= 961) {
            ctr = " 	GS1 UK Office: GTIN-8 allocations";
        } else if (code >= 962 && code <= 969) {
            ctr = " 	GS1 Global Office: GTIN-8 allocations";
        } else if (code == 977) {
            ctr = " 	Serial publications (ISSN)";
        } else if (code >= 978 && code <= 979) {
            ctr = " 	Bookland (ISBN) – 979-0 used for sheet music (Musicland, ISMN-13, replaces deprecated ISMN M- numbers)";
        } else if (code == 980) {
            ctr = " 	Refund receipts";
        } else if (code >= 981 && code <= 984) {
            ctr = " 	GS1 coupon identification for common currency areas";
        } else if (code >= 990 && code <= 999) {
            ctr = "	GS1 coupon identification ";
        } else {
            ctr = "";
        }
        return ctr;
    }
}