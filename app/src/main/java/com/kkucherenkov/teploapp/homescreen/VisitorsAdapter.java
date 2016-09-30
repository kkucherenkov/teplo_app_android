package com.kkucherenkov.teploapp.homescreen;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kkucherenkov.teploapp.R;
import com.kkucherenkov.teploapp.model.VisitorDetails;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kirill Kucherenkov on 05/09/16.
 */

public class VisitorsAdapter extends RecyclerView.Adapter<VisitorsAdapter.ViewHolder> {

    private DateFormat dateFormat;
    private ArrayList<VisitorDetails> items;

    public VisitorsAdapter(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
        items = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.card_visitor_record, parent, false),
                dateFormat);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    void setItems(List<VisitorDetails> visitors) {
        items.clear();
        items.addAll(visitors);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_fullname)
        TextView tvFullname;
        @BindView(R.id.tv_start_date)
        TextView tvStartDate;
        private DateFormat dateFormat;

        private VisitorDetails details;

        ViewHolder(View itemView, DateFormat dateFormat) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.dateFormat = dateFormat;
        }

        void bind(@NonNull VisitorDetails data) {
            details = data;
            tvFullname.setText(data.getFullName());
            String date = dateFormat.format(data.getStartDate());
            tvStartDate.setText(date);
        }
    }

}
