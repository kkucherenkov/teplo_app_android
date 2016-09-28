package com.kkucherenkov.teploapp.endofvisit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kkucherenkov.teploapp.R;
import com.kkucherenkov.teploapp.TeploApp;
import com.kkucherenkov.teploapp.model.VisitorDetails;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kirillkucherenkov on 28/09/2016.
 */

public class EndOfVisitFragment extends Fragment implements EndOfVisitContract.EndOfVisitView {

    public static final String VISITOR_DETAILS_KEY = "visitor_details";

    @BindView(R.id.visitor_name)
    protected TextView visitorNameTV;
    @BindView(R.id.visitor_id)
    protected TextView visitorIdTV;
    @BindView(R.id.total_cost)
    protected TextView totalCostTV;

    @BindView(R.id.cancel_button)
    protected Button cancelButton;
    @BindView(R.id.ok_button)
    protected Button okButton;

    @Inject
    protected EndOfVisitContract.Presenter presenter;

    public EndOfVisitFragment() {
    }

    public static EndOfVisitFragment newInstance(VisitorDetails visitorDetails) {
        EndOfVisitFragment dialogFragment = new EndOfVisitFragment();
        Bundle args = new Bundle();
        args.putSerializable(VISITOR_DETAILS_KEY, visitorDetails);
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((TeploApp) getActivity().getApplication()).getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.end_of_visit_fragment, container, false);
        ButterKnife.bind(this, view);
        VisitorDetails visitorDetails = (VisitorDetails) getArguments().getSerializable(VISITOR_DETAILS_KEY);

        presenter.viewEndOfVisitCreated(this, visitorDetails);
        return view;
    }

    @Override
    public void setVisitorName(String visitorName) {
        visitorNameTV.setText(visitorName);
    }

    @Override
    public void setVisitorId(String visitorId) {
        visitorIdTV.setText(visitorId);
    }

    @Override
    public void setTotalCost(String totalCost) {
        totalCostTV.setText(totalCost);
    }

    @Override
    public void onDestroyView() {
        presenter.viewEndOfVisitDestroyed();
        super.onDestroyView();
    }

    @Override
    public void closeFragment() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.cancel_button)
    public void cancelBtnClick() {
        closeFragment();
    }

    @OnClick(R.id.ok_button)
    public void okBtnClick() {
        presenter.okButtonClicked();
    }

}
