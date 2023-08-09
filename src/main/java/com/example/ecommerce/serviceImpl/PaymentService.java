package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.dto.request.PaymentRequest;
import com.example.ecommerce.dto.response.PaymentResponse;
import com.google.gson.Gson;
import com.squareup.okhttp.*;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j

public class PaymentService {

    private final String SECRET_KEY= System.getenv("PAYSTACK_SECRET_KEY");

    public PaymentResponse pay(PaymentRequest paymentRequest){
        OkHttpClient okHttpClient = new OkHttpClient();
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("name",paymentRequest.getName());
            jsonObject.put("amount",paymentRequest.getAmount());
            jsonObject.put("description",paymentRequest.getDescription());
        }catch (JSONException e){
            log.info(e.getMessage());
        }
        MediaType mediaType = MediaType.parse("application/json");
        //start here
        RequestBody requestBody = RequestBody.create(mediaType,jsonObject.toString());
        Request request = new Request.Builder()
                .url("https://api.paystack.co/page")
                .post(requestBody)
                .addHeader("Authorization", "Bearer "+SECRET_KEY)
                .addHeader("Content-Type", "application/json")
                .build();
        try(ResponseBody response = okHttpClient.newCall(request).execute().body()) {
            Gson gson = new Gson();
            gson.fromJson(response.string(),PaymentResponse.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new PaymentResponse("Payment successful");
    }
}
