package com.example.projectkelompok1e_library;

import android.os.AsyncTask;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RetrievePdfStream extends AsyncTask<String, Void, InputStream> {
    PDFView pdfView;

    public RetrievePdfStream(PDFView pdfView) {
        this.pdfView = pdfView;
    }

    @Override
    protected InputStream doInBackground(String... strings) {
        InputStream inputStream = null;
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    @Override
    protected void onPostExecute(InputStream inputStream) {
        pdfView.fromStream(inputStream).load();
    }
}

