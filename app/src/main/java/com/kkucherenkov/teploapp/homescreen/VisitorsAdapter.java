package com.kkucherenkov.teploapp.homescreen;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kkucherenkov.teploapp.R;
import com.kkucherenkov.teploapp.model.BadgeData;

import java.text.DateFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kirill Kucherenkov on 05/09/16.
 */

public class VisitorsAdapter extends RecyclerView.Adapter<VisitorsAdapter.ViewHolder> {

    private DateFormat dateFormat;
    private ArrayList<BadgeData> items;

    public VisitorsAdapter(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
        items = new ArrayList<>();
    }

    public void addItem(BadgeData data) {
        int previousItem = -1;
        for (int i = 0; i < items.size(); i++) {
            BadgeData item = items.get(i);
            if (item.getId().equals(data.getId())) {
                previousItem = i;
                break;
            }
        }
        if (previousItem < 0) {
            items.add(data);
            notifyItemInserted(items.size() - 1);
        } else {
            items.remove(previousItem);
            notifyItemRemoved(previousItem);
        }
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

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_fullname)
        TextView tvFullname;
        @BindView(R.id.tv_start_date)
        TextView tvStartDate;
        private DateFormat dateFormat;

        ViewHolder(View itemView, DateFormat dateFormat) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.dateFormat = dateFormat;
        }

        void bind(@NonNull BadgeData data) {
            tvFullname.setText(data.getFullname());
            String date = dateFormat.format(data.getStartDate());
            tvStartDate.setText(date);
        }
    }

}
