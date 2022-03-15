package com.example.dathan_stone_c196_task.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dathan_stone_c196_task.R;
import com.example.dathan_stone_c196_task.entities.Term;
import com.example.dathan_stone_c196_task.entities.TermWithCourses;

import java.util.ArrayList;
import java.util.List;

public class TermsAdapter extends RecyclerView.Adapter<TermsAdapter.TermHolder> {

    private List<Term> termList = new ArrayList<>();
    private List<TermWithCourses> termDetailsList = new ArrayList<>();
    private TermsAdapter.OnItemClickListener listener;

    public TermsAdapter (OnItemClickListener listener) {
        this.listener = listener;
    }



    @NonNull
    @Override
    public TermHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.term_item, parent, false);
        return new TermHolder(itemView);
    }

    public void setTermDetailsList(List<TermWithCourses> termDetailsList) {
        this.termDetailsList = termDetailsList;
        notifyDataSetChanged();
    }

    //binds the data
    @Override
    public void onBindViewHolder(@NonNull TermHolder holder, int position) {
        TermWithCourses details = termDetailsList.get(position);
        holder.textViewTitle.setText(details.term.getTitle());
    }

    //Determines how many items you want to return.
    @Override
    public int getItemCount() {
        return termDetailsList.size();
    }

    class TermHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private ImageButton detailView;
        private ImageButton deleteView;

        public TermHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.term_title_hehe);
            detailView = itemView.findViewById(R.id.view_term_details);
            deleteView = itemView.findViewById(R.id.term_delete);

            deleteView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                listener.deleteTerm(termDetailsList.get(position));
            });

            detailView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                listener.detailsForTerm(termDetailsList.get(position));
            });
        }
    }

    public interface OnItemClickListener {
        void deleteTerm(TermWithCourses delete);
        void detailsForTerm(TermWithCourses details);
    }
}
