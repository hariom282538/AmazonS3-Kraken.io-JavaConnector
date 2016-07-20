package com.devinrsmith.kraken;

import com.devinrsmith.kraken.data.requests.Auth;
import com.devinrsmith.kraken.data.requests.Resize;
import com.devinrsmith.kraken.data.requests.URLRequest;
import com.devinrsmith.kraken.data.responses.Response;
import java.io.IOException;

import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by dsmith on 8/18/14.
 * Modified by Hariom Vashisth
 * Github : hariom282538
 */
public class KrakenIOExampleMain {

    public static final String API_ENDPOINT = "https://api.kraken.io";

    public static void showUsage() {
        System.out.println(KrakenIOExampleMain.class.getSimpleName() + "6ae67663c3b6fa0ba3aad212032fdc24");
    }
String bucket;
    public void Compression(String link, String bucket) throws ExecutionException, InterruptedException {
       try {
        final String apiKey = "PUT_YOUR_API-KEY_HERE";
        final String apiSecret = "PUT_YOUR_API-SECRET_HERE";
        final String url = link;
        
        this.bucket=bucket;

        final KrakenIO service = KrakenIO.Service.get(API_ENDPOINT);

        final URLRequest.Waiting waitingRequest = URLRequest.builder(new Auth(apiKey, apiSecret), url).
                //resize(new Resize(200, 100, "auto", null)).
                lossy(true).
                quality(70).
                build();

        final Response response = service.uploadImage(waitingRequest);
        System.out.println(response.getKraked_url() + " : " + 100 * (response.getOriginal_size() - response.getKraked_size()) / response.getOriginal_size() + "% smaller");
        String fileURL = response.getKraked_url();
        String saveDir = "PUT_YOUR_LOCAL/SERVER-PATH_HERE";
        
            HttpDownloadUtility.downloadFile(fileURL, saveDir,bucket );
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
