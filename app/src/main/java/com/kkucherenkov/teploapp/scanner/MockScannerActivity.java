package com.kkucherenkov.teploapp.scanner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.kkucherenkov.teploapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kirillkucherenkov on 15/09/16.
 */

public class MockScannerActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.visitor_one)
    Button visitorOneBtn;
    @BindView(R.id.visitor_two)
    Button visitorTwoBtn;
    @BindView(R.id.visitor_three)
    Button visitorThreeBtn;
    @BindView(R.id.visitor_four)
    Button visitorFourBtn;
    @BindView(R.id.visitor_five)
    Button visitorFiveBtn;

    private static final String VISITOR_1 = "{ \"type\": \"teplo_badge\",\"id\": \"av_1\",\"fullname\" :\"John Smith\",\"sec_id\" : \"d4cde1d73c2228a8de8a83aabf0037d7\"}";
    private static final String VISITOR_2 = "{ \"type\": \"teplo_badge\",\"id\": \"av_2\",\"fullname\" :\"John Golt\",\"sec_id\" : \"d4cde1d73c2228a8de8a83aabf0037d7\"}";
    private static final String VISITOR_3 = "{ \"type\": \"teplo_badge\",\"id\": \"av_3\",\"fullname\" :\"David Bowie\",\"sec_id\" : \"d4cde1d73c2228a8de8a83aabf0037d7\"}";
    private static final String VISITOR_4 = "{ \"type\": \"teplo_badge\",\"id\": \"av_4\",\"fullname\" :\"Andy Worhole\",\"sec_id\" : \"d4cde1d73c2228a8de8a83aabf0037d7\"}";
    private static final String VISITOR_5 = "{ \"type\": \"teplo_badge\",\"id\": \"av_5\",\"fullname\" :\"Neo\",\"sec_id\" : \"d4cde1d73c2228a8de8a83aabf0037d7\"}";

    public static Intent newIntent(@NonNull Context context) {
        return new Intent(context, MockScannerActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mock_activity_scanner);
        ButterKnife.bind(this);
        visitorOneBtn.setOnClickListener(this);
        visitorTwoBtn.setOnClickListener(this);
        visitorThreeBtn.setOnClickListener(this);
        visitorFourBtn.setOnClickListener(this);
        visitorFiveBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        StringBuilder data = new StringBuilder();
        switch (id) {
            case R.id.visitor_one:
                data.append(VISITOR_1);
                break;
            case R.id.visitor_two:
                data.append(VISITOR_2);
                break;
            case R.id.visitor_three:
                data.append(VISITOR_3);
                break;
            case R.id.visitor_four:
                data.append(VISITOR_4);
                break;
            case R.id.visitor_five:
                data.append(VISITOR_5);
                break;
            default:
                throw new IllegalStateException("wrong id");
        }

        Intent intent = new Intent();
        intent.putExtra(ScannerActivity.QR_CODE_DATA, data.toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}
