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
/*
*/


import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.File;

public class UploadImages 
{
    public void UploadToS3(String bucketname, String filename, String filepath)  {
  //  public static void main(String[] args) {
        
    
   AWSCredentials credentials = null;
        String aws_access_key_id = "AKIAJKZ47GTKMNO6QZOA";
        String aws_secret_access_key = "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";
        String bucketName=bucketname;  //"zillionbucket";
        String fileName= filename;  //"javaee_duke_image.jpg";
        String localpath=filepath;   //"C:\\Users\\Hariom\\Documents\\NetBeansProjects\\JavaApplication9\\src\\ProcessedImages\\javaee_duke_image.jpg";
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
      
        try {
            
           s3.putObject(new PutObjectRequest(bucketName, fileName,new File(localpath)).withCannedAcl(CannedAccessControlList.PublicRead));
		
            System.out.println("File Uploaded to S3: "+fileName);
            
        }
        
        catch (AmazonServiceException ase) {
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
        } 
        
}
    
    
}