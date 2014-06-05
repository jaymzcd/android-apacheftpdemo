package eu.jaymz.ftpdemo;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.TextView;
import android.util.Log;
import android.preference.PreferenceManager;
import java.io.IOException;
import java.net.SocketException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import eu.jaymz.ftpdemo.Preferences;


public class FTPDemo extends Activity {

    private static final String TAG = "FTPClient";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Starting up");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final FTPClient ftp = new FTPClient();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        String username = prefs.getString("server_user", "admin");
        String password = prefs.getString("server_password", "test");
        String host = prefs.getString("server_host", "localhost");

        TextView info = (TextView)findViewById(R.id.ftp_string);
        TextView log = (TextView)findViewById(R.id.ftp_log);
        info.setText(username + "@" + host);

        try {
            ftp.connect(host);

            if (!ftp.login(username, password)) {
                ftp.logout();
                Log.d(TAG, "Couldn't log in");
                log.setText(log.getText() + "\nCouldn't log in");
            } else {
                log.setText("OK - reply code: " + ftp.getReplyCode());
                FTPFile[] files = ftp.listFiles("/");
                log.setText(log.getText() + "\n" + "File count on server: " + files.length);
            }

        } catch(SocketException e) {
            Log.d(TAG, "Error - socket");
            log.setText(log.getText() + "\nSocket error!");

        }catch(IOException e) {
            Log.d(TAG, "Error - IO");
            log.setText(log.getText() + "\nIO error!");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 0, 0, "Settings");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case 0:
            startActivity(new Intent(this, Preferences.class));
            return true;
        }
        return false;
    }

}
