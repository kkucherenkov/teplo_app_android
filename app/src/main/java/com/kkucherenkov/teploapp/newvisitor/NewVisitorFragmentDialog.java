package com.kkucherenkov.teploapp.newvisitor;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kkucherenkov.teploapp.R;
import com.kkucherenkov.teploapp.TeploApp;
import com.kkucherenkov.teploapp.dagger.ApplicationModule;
import com.kkucherenkov.teploapp.model.BadgeData;

import java.text.DateFormat;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kirill Kucherenkov on 12/09/16.
 */

public class NewVisitorFragmentDialog extends DialogFragment implements NewVisitorContract.View {

    @BindView(R.id.visitor_name)
    protected TextView visitorName;
    @BindView(R.id.visitor_id)
    protected TextView visitorId;
    @BindView(R.id.start_time)
    protected TextView startTime;
    @BindView(R.id.stop_check_enabled)
    protected CheckBox stopCheckCB;
    @BindView(R.id.stop_time_enabled)
    protected CheckBox stopTimeCB;

    @BindView(R.id.dialog_top_title_text)
    protected TextView titleTV;


    @Inject
    protected NewVisitorContract.Presenter presenter;

    @Inject
    @Named(ApplicationModule.APP_DATE_FORMAT)
    protected DateFormat dateFormat;

    public NewVisitorFragmentDialog() {
    }

    public static DialogFragment newInstance(BadgeData data) {
        DialogFragment dialogFragment = new NewVisitorFragmentDialog();
        Bundle args = new Bundle();
        args.putSerializable("badge", data);
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((TeploApp) getActivity().getApplication()).getComponent().inject(this);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
        final Dialog dialog = new Dialog(getActivity(), R.style.AppTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        dialog.getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_visitor_dialog, container, false);
        ButterKnife.bind(this, view);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
        getDialog().getWindow().setDimAmount(0);
        BadgeData data = (BadgeData) getArguments().getSerializable("badge");
        titleTV.setText(R.string.new_visitor_title);
        presenter.viewCreated(this, data);
        return view;
    }

    @Override
    public void onDestroyView() {
        presenter.viewDestroyed();
        super.onDestroyView();
    }

    @Override
    public void setName(String fullname) {
        visitorName.setText(fullname);
    }

    @Override
    public void setId(String id) {
        visitorId.setText(id);
    }

    @Override
    public void setStartDate(Date startDate) {
        startTime.setText(getString(R.string.start_time_is_0, dateFormat.format(startDate)));
    }
}
