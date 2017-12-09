package tech.alvarez.quicklyanswers.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tech.alvarez.quicklyanswers.R;
import tech.alvarez.quicklyanswers.model.Question;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {

    private List<Question> dataset;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onQuestionClick(Question q);
    }

    public QuestionsAdapter(OnItemClickListener onItemClickListener) {
        this.dataset = new ArrayList<>();
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Question q = dataset.get(position);
        holder.valueTextView.setText(q.getValue());
        holder.codeTextView.setText(q.getUserCreated());

        holder.setOnItemClickListener(q, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView valueTextView;
        TextView codeTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            valueTextView = (TextView) itemView.findViewById(R.id.valueTextView);
            codeTextView = (TextView) itemView.findViewById(R.id.codeTextView);
        }

        public void setOnItemClickListener(final Question q, final OnItemClickListener onItemClickListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onQuestionClick(q);
                }
            });
        }
    }


    public void add(Question q) {
        dataset.add(q);
        notifyDataSetChanged();
    }

    public void clear() {
        dataset.clear();
        notifyDataSetChanged();
    }
}
