package S3Controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hariom
 */

import java.io.IOException;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import java.util.ArrayList;
import com.devinrsmith.kraken.*;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import EmailHandler.*;

public class DownloadingImages {
    
   static String GetNotificationEmail = "vashisth.hariom7@gmail.com";

    public static void main(String[] args) throws IOException {
        AWSCredentials credentials = null;
        String aws_access_key_id = "PUT_YOUR_aws_access_key_id_HERE";
        String aws_secret_access_key = "PUT_YOUR_aws_secret_access_key_HERE";
        try {
            credentials = new BasicAWSCredentials(aws_access_key_id, aws_secret_access_key);//.getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. "
                    + "Please make sure that your credentials file is at the correct "
                    + "location (~/.aws/credentials), and is in valid format.",
                    e);
        }

        AmazonS3 s3 = new AmazonS3Client(credentials);
        Region AP_SOUTHEAST_1 = Region.getRegion(Regions.AP_SOUTHEAST_1);
        s3.setRegion(AP_SOUTHEAST_1);

        String bucketName = "PUT_YOUR_S3-BUCKET-NAME_HERE";
        String key = "PUT_YOUR_S3-BUCKET-KEY_HERE";

        try {

            ArrayList arr = new ArrayList();
            ArrayList EmailArray = new ArrayList();
            Bucket bucket = new Bucket(bucketName);
            ObjectListing objects = s3.listObjects(bucket.getName());
            do {
                for (S3ObjectSummary objectSummary : objects.getObjectSummaries()) {
//                System.out.println(objectSummary.getKey() + "\t" +
//                        objectSummary.getSize() + "\t" +
//                        StringUtils.fromDate(objectSummary.getLastModified()));
                    arr.add(objectSummary.getKey());
                }
                objects = s3.listNextBatchOfObjects(objects);
            } while (objects.isTruncated());

            KrakenIOExampleMain kraken = new KrakenIOExampleMain();
            for (int i = 0; i < arr.size(); i++) {
                System.out.println("Compressing: " + arr.get(i));
                String s = (String) arr.get(i);
                GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucket.getName(), s);
                System.out.println(s3.generatePresignedUrl(request));
                URL Glink = s3.generatePresignedUrl(request);
                String Dlink = Glink.toString();
                System.out.println("Download Link:" + Dlink);
                kraken.Compression(Dlink,bucketName);
                System.out.println("Compression completed: " + arr.get(i));
                EmailArray.add("Processed Image:"+arr.get(i));
            }
            System.out.println("Start Emailing list");
            EmailSender esender = new EmailSender();
            esender.EmailVerification(GetNotificationEmail, EmailArray);
            System.out.println("Kraken compression completed");
        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon S3, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        } catch (ExecutionException ex) {
            Logger.getLogger(DownloadingImages.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(DownloadingImages.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
