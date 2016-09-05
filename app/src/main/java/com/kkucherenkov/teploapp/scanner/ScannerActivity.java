package com.kkucherenkov.teploapp.scanner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.kkucherenkov.teploapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eu.livotov.labs.android.camview.ScannerLiveView;
import eu.livotov.labs.android.camview.scanner.decoder.zxing.ZXDecoder;

public class ScannerActivity extends AppCompatActivity {

    public static final String QR_CODE_DATA = "qr_code_data";

    @BindView(R.id.camview)
    ScannerLiveView camera;

    private boolean flashStatus;

    public static Intent newIntent(@NonNull Context context) {
        return new Intent(context, ScannerActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        ButterKnife.bind(this);

        camera.setScannerViewEventListener(new ScannerLiveView.ScannerViewEventListener() {
            @Override
            public void onScannerStarted(ScannerLiveView scanner) {
            }

            @Override
            public void onScannerStopped(ScannerLiveView scanner) {
            }

            @Override
            public void onScannerError(Throwable err) {
                Toast.makeText(ScannerActivity.this, "Scanner Error: " + err.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeScanned(String data) {
                Intent intent = new Intent();
                intent.putExtra(QR_CODE_DATA, data);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ZXDecoder decoder = new ZXDecoder();
        decoder.setScanAreaPercent(0.5);
        camera.setDecoder(decoder);
        camera.startScanner();
    }

    @Override
    protected void onPause() {
        camera.stopScanner();
        super.onPause();
    }

    @OnClick(R.id.btnFlash)
    public void toggleFlash() {
        flashStatus = !flashStatus;
        camera.getCamera().getController().switchFlashlight(flashStatus);
    }

}
