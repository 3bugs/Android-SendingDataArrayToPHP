package com.example.sendingdataarraytophp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private OkHttpClient mClient = new OkHttpClient();
    private ArrayList<Product> mProductList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProductList.add(
                new Product("Apple", 3)
        );
        mProductList.add(
                new Product("Orange", 4)
        );

        FormBody.Builder builder = new FormBody.Builder();
        for (Product p : mProductList) {
            builder.add("product[]", p.productName);
            builder.add("quantity[]", String.valueOf(p.quantity));
        }

        RequestBody requestBody = builder.build();

        final Request request = new Request.Builder()
                .url("http://10.0.2.2/test.php")
                .post(requestBody)
                .build();

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.i(TAG, result);
            }
        });
    }

    private static class Product {
        public final String productName;
        public final int quantity;

        public Product(String productName, int quantity) {
            this.productName = productName;
            this.quantity = quantity;
        }
    }
}
