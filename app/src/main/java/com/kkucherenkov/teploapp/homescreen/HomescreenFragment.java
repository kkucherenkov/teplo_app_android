package com.kkucherenkov.teploapp.homescreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kkucherenkov.teploapp.R;
import com.kkucherenkov.teploapp.TeploApp;
import com.kkucherenkov.teploapp.model.BadgeData;
import com.kkucherenkov.teploapp.model.VisitorDetails;
import com.kkucherenkov.teploapp.newvisitor.NewVisitorFragmentDialog;
import com.kkucherenkov.teploapp.scanner.ScannerActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class HomescreenFragment extends Fragment implements HomescreenContract.View {
    public static final int REQUEST_CODE = 12345;

    @Inject
    protected HomescreenContract.Presenter presenter;
    @Inject
    protected VisitorsAdapter visitorsAdapter;

    @BindView(R.id.visitors_list)
    protected RecyclerView rvVisitorsList;

    public HomescreenFragment() {
    }

    public static HomescreenFragment newInstance() {
        return new HomescreenFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((TeploApp) getActivity().getApplication()).getComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homescreen, container, false);
        ButterKnife.bind(this, view);
        rvVisitorsList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvVisitorsList.setHasFixedSize(true);
        rvVisitorsList.setAdapter(visitorsAdapter);
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

    @Override
    public void showNewVisitorScreen(BadgeData badge) {
        NewVisitorFragmentDialog.
                newInstance(badge).
                show(getFragmentManager().beginTransaction(),
                        NewVisitorFragmentDialog.class.getSimpleName());
    }

    @Override
    public void showEndOfVisitScreen(VisitorDetails visitorDetails) {

    }
}
