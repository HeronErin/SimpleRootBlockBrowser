package me.pickleswine.simplerootblockbrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private void makeDrive(){
        String fdir = getFilesDir().getAbsolutePath();

        try {
            Bin.instance.send(new String[]{
                    "umount drive",
                    "cd "+fdir,
                    "mkdir drive"
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        String p = Bin.instance.getOut("ls /dev/block/sd*");
        if (p.length() > 5) {
            String[] ln = p.split("\n");
            try {
                Bin.instance.send(new String[]{"mount "+ln[ln.length-1] + " drive"});
                Thread.sleep(5000);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Bin.instance.mountedsda=true;
            Intent i = new Intent(this, DirDir.class);
            i.putExtra("dir", "/");
            startActivity(i);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        (new Thread(()-> {
            try {
                Bin.instance.su = Runtime.getRuntime().exec("su");

                Thread.sleep(50);
                if (Bin.instance.su.isAlive()){
                    Bin.instance.input = new BufferedReader(new InputStreamReader(Bin.instance.su.getInputStream()));
                    Bin.instance.authed=true;

                }else{
                    MainActivity.this.runOnUiThread(()->{
                        Toast.makeText(MainActivity.this, "SU FAILED", Toast.LENGTH_SHORT).show();
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        })).start();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener((v)->{
            makeDrive();
        });
        findViewById(R.id.button2).setOnClickListener((v)->{
            Intent i = new Intent(this, settings.class);
            startActivity(i);
        });
    }
}