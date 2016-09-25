package com.kkucherenkov.teploapp.homescreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.kkucherenkov.teploapp.R;
import com.kkucherenkov.teploapp.TeploApp;
import com.kkucherenkov.teploapp.model.BadgeData;
import com.kkucherenkov.teploapp.model.VisitorDetails;
import com.kkucherenkov.teploapp.newvisitor.NewVisitorContract;
import com.kkucherenkov.teploapp.newvisitor.NewVisitorFragmentDialog;
import com.kkucherenkov.teploapp.scanner.MockScannerActivity;
import com.kkucherenkov.teploapp.scanner.ScannerActivity;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.kkucherenkov.teploapp.homescreen.HomescreenActivity.MOCKED_SCANNER_KEY;

public class HomescreenFragment extends Fragment implements HomescreenContract.View {
    public static final int REQUEST_CODE = 12345;

    @Inject
    protected HomescreenContract.Presenter presenter;
    @Inject
    protected VisitorsAdapter visitorsAdapter;

    @BindView(R.id.visitors_list)
    protected RecyclerView rvVisitorsList;
    @BindView(R.id.progress_bar)
    protected ProgressBar progressBar;

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
        SharedPreferences settings = getActivity().getSharedPreferences("settings", 0);
        boolean isChecked = settings.getBoolean(MOCKED_SCANNER_KEY, false);
        Intent intent = isChecked ? ScannerActivity.newIntent(getContext()) : MockScannerActivity.newIntent(getContext());
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void showNewVisitorScreen(BadgeData badge) {
        NewVisitorFragmentDialog dialog = NewVisitorFragmentDialog.newInstance(badge);
        dialog.setPresenter((NewVisitorContract.Presenter) presenter);
        dialog.show(getFragmentManager().beginTransaction(),
                NewVisitorFragmentDialog.class.getSimpleName());
    }

    @Override
    public void showEndOfVisitScreen(VisitorDetails visitorDetails) {
        visitorDetails.setEndDate(new Date());
        presenter.closeVisitor(visitorDetails);
    }

    @Override
    public void setVisitors(List<VisitorDetails> visitors) {
        visitorsAdapter.setItems(visitors);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setTitle(int titleId) {
        getActivity().setTitle(titleId);
    }
}
