package com.infinite.task.services;

import com.infinite.task.R;
import com.infinite.task.app.AppController;

import java.io.IOException;
import java.io.InputStream;

public class JsonParsingFromRaw {

    private static JsonParsingFromRaw instance;

    public static JsonParsingFromRaw getInstance() {
        if (instance == null) {
            instance = new JsonParsingFromRaw();
        }
        return instance;
    }


    public String readJsonRawFile() {
        try {
            String json = null;
            try {
                InputStream is = AppController.getInstance().getResources().openRawResource(R.raw.infinite);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
            return json;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
