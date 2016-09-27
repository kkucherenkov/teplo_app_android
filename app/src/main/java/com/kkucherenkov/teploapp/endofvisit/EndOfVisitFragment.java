package com.kkucherenkov.teploapp.endofvisit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kkucherenkov.teploapp.R;
import com.kkucherenkov.teploapp.TeploApp;
import com.kkucherenkov.teploapp.model.VisitorDetails;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by kirillkucherenkov on 28/09/2016.
 */

public class EndOfVisitFragment extends Fragment implements EndOfVisitContract.View {

    public static final String VISITOR_DETAILS_KEY = "visitor_details";

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
    public void onDestroyView() {
        presenter.viewEndOfVisitDestroyed();
        super.onDestroyView();
    }

    @Override
    public void closeFragment() {
        getActivity().onBackPressed();
    }

}
