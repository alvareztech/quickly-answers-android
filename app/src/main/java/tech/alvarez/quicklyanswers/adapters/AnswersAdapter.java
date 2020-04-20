package tech.alvarez.quicklyanswers.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tech.alvarez.quicklyanswers.R;
import tech.alvarez.quicklyanswers.model.Answer;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.ViewHolder> {

    private List<Answer> dataset;

    public AnswersAdapter() {
        this.dataset = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Answer a = dataset.get(position);
        holder.valueTextView.setText(a.getValue());
        holder.codeTextView.setText(a.getUser());
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
    }


    public void add(Answer q) {
        dataset.add(q);
        notifyDataSetChanged();
    }

    public void clear() {
        dataset.clear();
        notifyDataSetChanged();
    }
}
