package com.example.projectkelompok1e_library;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

public class PdfViewerActivity extends AppCompatActivity {
    private static final String TAG = "PdfViewerActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        PDFView pdfView = findViewById(R.id.pdfView);

        String pdfFileName = getIntent().getStringExtra("pdf_file_name");
        Log.d(TAG, "PDF File Name: " + pdfFileName);

        if (pdfFileName != null) {
            int resId = getResources().getIdentifier(pdfFileName, "raw", getPackageName());
            if (resId != 0) {
                pdfView.fromStream(getResources().openRawResource(resId)).load();
            } else {
                Log.e(TAG, "Resource not found for the given PDF file name.");
            }
        } else {
            Log.e(TAG, "No PDF file name provided");
        }
    }
}
