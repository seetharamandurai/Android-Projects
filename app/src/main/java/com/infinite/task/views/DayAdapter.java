package com.infinite.task.views;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.infinite.task.R;
import com.infinite.task.model.GraphData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayViewHolder> {

    private final Context context;
    private List<GraphData> items;
    private int currentPsotion;

    public DayAdapter(Context context) {
        this.context = context;
        items = new ArrayList<>();
    }

    public void setItems(List<GraphData> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public DayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_details, parent, false);
        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DayViewHolder holder, int position) {
        holder.bind(position);
        if (currentPsotion == position) {
            holder.cardview.setCardBackgroundColor(ContextCompat.getColor(context, R.color.card_select));
        } else {
            holder.cardview.setCardBackgroundColor(ContextCompat.getColor(context, R.color.card_unselect));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private GraphData getItem(int position) {
        return items.get(position);
    }

    public void setCurrentPosition(int firstVisibleItem) {
        this.currentPsotion = firstVisibleItem;
        notifyDataSetChanged();
    }

    public class DayViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cardview)
        CardView cardview;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.desc)
        TextView desc;

        DayViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(int position) {
            GraphData movie = getItem(position);

            setClickListener(movie);
            setTitle(movie.getTitle());
            setDescription(movie.getDescription());
        }

        private void setTitle(String title) {
            this.title.setText(title);
        }

        private void setDescription(String description) {
            desc.setText(description);
        }

        private void setClickListener(GraphData graphData) {
            itemView.setTag(graphData);
        }

    }
}