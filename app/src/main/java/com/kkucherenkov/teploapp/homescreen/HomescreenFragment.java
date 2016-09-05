package com.kkucherenkov.teploapp.homescreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kkucherenkov.teploapp.R;
import com.kkucherenkov.teploapp.TeploApp;
import com.kkucherenkov.teploapp.scanner.ScannerActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class HomescreenFragment extends Fragment implements HomescreenContract.View {
    public static final int REQUEST_CODE = 12345;
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @Inject
    protected HomescreenContract.Presenter presenter;

    public HomescreenFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static HomescreenFragment newInstance(String param1, String param2) {
        HomescreenFragment fragment = new HomescreenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((TeploApp) getActivity().getApplication()).getComponent().inject(this);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homescreen, container, false);
        ButterKnife.bind(this, view);
        presenter.viewCreated(this);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @OnClick(R.id.fab_scan)
    public void scanNewQR() {
        presenter.scanButtonClicked();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            String dataString = data.getStringExtra(ScannerActivity.QR_CODE_DATA);
            presenter.scanCompleted(dataString);
        }
    }

    @Override
    public void openScanScreen() {
        startActivityForResult(ScannerActivity.newIntent(getContext()), REQUEST_CODE);
    }
}
