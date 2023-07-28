package me.pickleswine.simplerootblockbrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class DirDir extends Activity {
    public static String removeExtention(String filePath) {
        // These first few lines the same as Justin's
        File f = new File(filePath);

        // if it's a directory, don't remove the extention
        if (f.isDirectory()) return filePath;

        String name = f.getName();

        // Now we know it's a file - don't need to do any special hidden
        // checking or contains() checking because of:
        final int lastPeriodPos = name.lastIndexOf('.');
        if (lastPeriodPos <= 0)
        {
            // No period after first character - return name as it was passed in
            return filePath;
        }
        else
        {
            // Remove the last period and everything after it
            File renamed = new File(f.getParent(), name.substring(0, lastPeriodPos));
            return renamed.getPath();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String dir = "drive/dltube data"+getIntent().getStringExtra("dir");
        String[] f = Bin.instance.getOut("ls \""+dir+"\"").split("\n");
        setContentView(R.layout.activity_dir_dir2);
        for (int i =0; i < f.length ; i++){
            TextView tv = new TextView(this);
            tv.setTextSize(getSharedPreferences("settings", Context.MODE_PRIVATE).getInt("Text size", 17));
            tv.setText(f[i]);

            int finalI = i;
            tv.setOnClickListener((view -> {
                String type = Bin.instance.getOut("file \"" + dir + f[finalI] + "\"");
                if (type.contains(": directory")){
//                    Toast.makeText(this, dir+ f[finalI]+"/", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(DirDir.this, DirDir.class);
                    in.putExtra("dir", getIntent().getStringExtra("dir")+ f[finalI]+"/");
                    startActivity(in);
                }else{
                    String ex =  f[finalI].replace(removeExtention(f[finalI]), "").replace(".", "");

                    String path = getFilesDir().getAbsolutePath()+"/drive/dltube data"+getIntent().getStringExtra("dir")+f[finalI];


                    //                    String ex =  f[finalI].replace(removeExtention(f[finalI]), "");
                    if (!ex.equals("avi")) {
                        Intent it = new Intent(this, VidPlayer.class);
                        it.putExtra("uri", path);
                        startActivity(it);
                    }else{
                        Toast.makeText(this, "AVI DETECTED!!! Moving to downloads", Toast.LENGTH_SHORT).show();
                        new Thread(()->{
                            try {
                                Bin.instance.send(new String[]{"cp \""+path+"\" /storage/emulated/0/Download/run.avi", "echo END"});
                                String e = "";
                                while (!e.contains("END")){
                                    e+=(char)Bin.instance.input.read();
                                }
                                runOnUiThread(()->{
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("/storage/emulated/0/Download/run.avi"));
                                    intent.setDataAndType(Uri.parse("/storage/emulated/0/Download/run.avi"), "video/*");
                                    startActivity(intent);
                                });
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }).start();

                    }


                }

            }));
            ((LinearLayout)findViewById(R.id.sva)).addView(tv);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(tv.getLayoutParams());
            lp.bottomMargin=30;
        }
    }
}