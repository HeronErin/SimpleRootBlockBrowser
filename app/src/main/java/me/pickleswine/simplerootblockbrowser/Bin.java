package me.pickleswine.simplerootblockbrowser;

import com.google.android.exoplayer2.ExoPlayer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Bin {
    public static Bin instance = new Bin();
    public boolean authed=false;
    public Process su;
    public boolean mountedsda = false;
    public ExoPlayer player;
    BufferedReader input;
    public void send(String[] in) throws IOException {
        DataOutputStream os = new DataOutputStream(su.getOutputStream());
        for (int i = 0; i < in.length; i++) {
            os.writeBytes(in[i] + "\n");
        }
        os.flush();
    }
    public String getOut(String[] in){

        try {


            send(in);
            Thread.sleep(250);
            String o = "";
            while (input.ready()){
                o+=(char)input.read();
            }
//                StringBuffer sb = new StringBuffer();
//                String s;
//                while ((s = input.readLine()) != null && input.ready())
//                    sb.append(s + "| |");
//                return sb.toString().split("\\| \\|");


            return o;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    return "";
    }
    public String getOut(String in){
        return getOut(new String[]{in});
    }
}
