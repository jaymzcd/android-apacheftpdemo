package eu.jaymz.ftpdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import java.io.IOException;
import java.net.SocketException;
import org.apache.commons.net.ftp.FTPClient;


public class FTPDemo extends Activity {

    private static final String TAG = "FTPClient";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Starting up");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final FTPClient ftp = new FTPClient();
        String username = "jaymz";
        String password = "test";

        try {
            ftp.connect("toad.jaymz.eu");

            if (!ftp.login(username, password))
            {
                ftp.logout();
                Log.d(TAG, "Couldn't log in");
            }
        } catch(SocketException e) {
            Log.d(TAG, "Error - socket");
        }catch(IOException e) {
            Log.d(TAG, "Error - IO");
        }
    }
}
