package com.kkucherenkov.teploapp.homescreen;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kkucherenkov.teploapp.R;
import com.kkucherenkov.teploapp.TeploApp;

import javax.inject.Inject;

import butterknife.OnClick;

public class HomescreenFragment extends Fragment implements HomescreenContract.View {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @Inject
    private HomescreenContract.Presenter presenter;

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
    public void openScanScreen() {

    }
}
