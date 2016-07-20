package S3Controller;

// Include the Dropbox SDK.

import com.dropbox.core.*;
import java.io.*;
import java.util.Locale;

public class Dropbox {
    public static void main(String[] args) throws IOException, DbxException {
        // Get your app key and secret from the Dropbox developers website.
        final String APP_KEY = "915bjt74omfcdj1";
        final String APP_SECRET = "ywnf99m54ipe7vy";

        DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);

        DbxRequestConfig config = new DbxRequestConfig("JavaTutorial/1.0",
            Locale.getDefault().toString());
        DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);

        // Have the user sign in and authorize your app.
        String authorizeUrl = webAuth.start();
        System.out.println("1. Go to: " + authorizeUrl);
        System.out.println("2. Click \"Allow\" (you might have to log in first)");
        System.out.println("3. Copy the authorization code.");
      //String code = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
//String code="2HKAJxvoHAQAAAAAAAAGAqPrIK9Nlxz2JPtL0oBTmsY";
        // This will fail if the user enters an invalid authorization code.
        //DbxAuthFinish authFinish = webAuth.finish(code);
        
       String accessToken = "2HKAJxvoHAQAAAAAAAAGB2gZyMXWWTfFxa6wFrmajFwUtsl9cfjcgUfkeBfajNlO";//authFinish.accessToken;
        System.out.println(accessToken);
        DbxClient client = new DbxClient(config, accessToken);

        System.out.println("Linked account: " + client.getAccountInfo().displayName);

        

        
        FileOutputStream outputStream = new FileOutputStream("index.html");
        try {
            
            DbxEntry.File downloadedFile = client.getFile("/index.html", null,
                outputStream);
            System.out.println("Metadata: " + downloadedFile.toString());
        } finally {
            outputStream.close();
        }
    }
}