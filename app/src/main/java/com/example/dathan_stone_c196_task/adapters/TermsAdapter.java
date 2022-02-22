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

import java.util.ArrayList;
import java.util.List;

public class TermsAdapter extends RecyclerView.Adapter<TermsAdapter.TermHolder> {

    private List<Term> termList = new ArrayList<>();
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

    //binds the data
    @Override
    public void onBindViewHolder(@NonNull TermHolder holder, int position) {
        Term currentTerm = termList.get(position);
        holder.textViewTitle.setText(currentTerm.getTitle());
    }

    //Determines how many items you want to return.
    @Override
    public int getItemCount() {
        return termList.size();
    }

    public void setTermList(List<Term> terms) {
        this.termList = terms;
        notifyDataSetChanged();
    }

    //Grabs a specific term at a certain index position
    public Term getTermAt(int position) {
        return termList.get(position);
    }

    class TermHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private ImageButton detailView;
        private ImageButton deleteView;

        public TermHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.term_title);
            detailView = itemView.findViewById(R.id.term_details);
            deleteView = itemView.findViewById(R.id.term_delete);

            deleteView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                listener.deleteTerm(termList.get(position));
            });

            detailView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                listener.detailsForTerm(termList.get(position));
            });
        }
    }

    public interface OnItemClickListener {
        void deleteTerm(Term term);
        void detailsForTerm(Term term);
    }
}
