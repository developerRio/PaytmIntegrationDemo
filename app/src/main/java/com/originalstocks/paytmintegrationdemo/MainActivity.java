package com.originalstocks.paytmintegrationdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.originalstocks.paytmintegrationdemo.Model.PaytmModel;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private EditText amountEditText;
    private Button payButton;
    private PaytmPGService pgService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*
        * for paytm credentials use
        * mobile = 7777777777
        * OTP = 489871  */

        //initialising staging service by paytm
        pgService = PaytmPGService.getStagingService();
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
        }

        amountEditText = findViewById(R.id.balance_text);
        payButton = findViewById(R.id.proceed_to_pay_button);

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = amountEditText.getText().toString().trim();
                if (amount.isEmpty()) {
                    Log.i(TAG, "onClick: paytm process is not checked");
                    Toast.makeText(MainActivity.this, "Please enter some amount", Toast.LENGTH_SHORT).show();
                }else {
                    paytmProcess(amount);
                    Log.i(TAG, "onClick: paytm process is checked");
                }
            }
        });

    }// onCreate closes

    private void paytmProcess(String amount) {

        String orderID = "order" + generateRandomUUID();
        String customerID = "customer" + generateRandomUUID();
        String callbackURL = "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=" + orderID;

        Log.i(TAG, "paytmProcess_orderID = " + orderID);
        Log.i(TAG, "paytmProcess_customerID = " + customerID);
        Log.i(TAG, "paytmProcess_callbackURL = " + callbackURL);

        PaytmModel paytmModel = new PaytmModel(
                "MVDqBk37195830679502",
                orderID,
                customerID,
                "WAP",
                amount,
                "WEBSTAGING",
                callbackURL,
                "Retail",
                "enter_the_checksum_key_generated_by_your_server");


        HashMap<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("MID", paytmModel.getmId());
        // Key in your staging and production MID available in your dashboard
        paramMap.put("ORDER_ID", paytmModel.getOrderId());
        paramMap.put("CUST_ID", paytmModel.getCustId());
        paramMap.put("MOBILE_NO", "7777777777");
        paramMap.put("EMAIL", "username@emailprovider.com");
        paramMap.put("CHANNEL_ID", "WAP");
        paramMap.put("TXN_AMOUNT", amount);
        paramMap.put("WEBSITE", "WEBSTAGING");
        // This is the staging value. Production value is available in your dashboard
        paramMap.put("INDUSTRY_TYPE_ID", "Retail");
        // This is the staging value. Production value is available in your dashboard
        paramMap.put("CALLBACK_URL", paytmModel.getCallbackURL());
        paramMap.put("CHECKSUMHASH", paytmModel.getCheckSumHash());


        PaytmOrder paytmOrder = new PaytmOrder(paramMap);

        pgService.initialize(paytmOrder, null);

        pgService.startPaymentTransaction(this, true, true, new PaytmPaymentTransactionCallback() {
            @Override
            public void onTransactionResponse(Bundle inResponse) {
                Toast.makeText(getApplicationContext(), "Payment Transaction response " + inResponse.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void networkNotAvailable() {
                Log.i(TAG, "networkNotAvailable: ");
            }

            @Override
            public void clientAuthenticationFailed(String inErrorMessage) {

            }

            @Override
            public void someUIErrorOccurred(String inErrorMessage) {

            }

            @Override
            public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {

            }

            @Override
            public void onBackPressedCancelTransaction() {

            }

            @Override
            public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {

            }
        });

    }

    private String generateRandomUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("[-+.^:,|@_]", "");
    }
}
