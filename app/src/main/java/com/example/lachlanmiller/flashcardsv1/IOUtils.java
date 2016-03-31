package com.example.lachlanmiller.flashcardsv1;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by lachlanmiller on 14/03/2016.
 */
public class IOUtils {

    Context mContext;

    public IOUtils(Context context) {
        this.mContext = context;
    }


    public void firstTimeBoot() {

        File init = new File(mContext.getFilesDir(), "decks");

        if (init.exists() && init.isDirectory()) {
            // no need to do anything
        } else {
            Toast.makeText(mContext, "First Boot", Toast.LENGTH_SHORT).show();
            init.mkdir();


            // Make demo list and populate
            String deckName = "どんなに高かろうが";
            File file = new File(mContext.getFilesDir() + File.separator + "decks" + File.separator + deckName);
            file.mkdirs();

            ArrayList<String> data = new ArrayList<String>();
            ArrayList<String> cardNames = new ArrayList<String>();

            cardNames.add("card0");
            cardNames.add("card1");

            data.add("〜であれ、〜であれ`先生であれ学生であれ、この規則には従わなければならない`" +
                    "彼が誰であれ、特別扱いするのはおかしい`blank`Regardless of`You must follow the rules " +
                    "regardless of whether you are a student or teacher`It is wrong to give him special treatment even" +
                    " if he is someone important`blank`");
            data.add("〜ろうが、〜ろうが`雨が降ろうと雪が降ろうと明日の集まりには必ず行くよ`" +
                    "私は肉だろうが魚だろうが、なんでも食べます`" +
                    "新品であろうと、中古であろうと、そんな型の古いパソコンを買うべきでばないと思う`" +
                    "Either way`Come rain or snow, I'll definitely be at the gathering`" +
                    "I'll eat meat or fish, or anything`I don't think you should buy" +
                    "such an old model, be it new or secondhand`");

            for (int i = 0; i < 2; i++) {
                writeToFileWithPath(data.get(i), cardNames.get(i), "decks" + File.separator + deckName, false);
            }

        }
    }

    public void writeToFileWithPath(String data, String fileName, String folderPath, boolean append) {

        FileOutputStream fos = null;
        try {
            if (append == true) {
                fos = new FileOutputStream(mContext.getFilesDir() + File.separator + folderPath + File.separator + fileName,
                        true);
            } else if (append == false) {
                fos = new FileOutputStream(mContext.getFilesDir() + File.separator + folderPath + File.separator + fileName,
                        false);
            }
            fos.write(data.getBytes());


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public String getLastBitFromFilePath(String filePath){
        return filePath.replaceFirst(".*/([^/?]+).*", "$1");
    }

    public String removeExtension(String s) {

        String separator = System.getProperty("file.separator");
        String filename;

        // Remove the path up to the filename.
        int lastSeparatorIndex = s.lastIndexOf(separator);
        if (lastSeparatorIndex == -1) {
            filename = s;
        } else {
            filename = s.substring(lastSeparatorIndex + 1);
        }

        // Remove the extension.
        int extensionIndex = filename.lastIndexOf(".");
        if (extensionIndex == -1)
            return filename;

        return filename.substring(0, extensionIndex);
    }

    public String readFromFile (String path) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
            return convertStreamToString(fis);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return "Error";
    }

    private String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();
        return sb.toString();
    }
}
