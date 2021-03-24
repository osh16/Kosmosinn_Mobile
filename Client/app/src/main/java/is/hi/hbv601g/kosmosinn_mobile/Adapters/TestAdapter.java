package is.hi.hbv601g.kosmosinn_mobile.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import is.hi.hbv601g.kosmosinn_mobile.R;
import is.hi.hbv601g.kosmosinn_mobile.TestActivity;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestHolder> {
    private String[] topics;
    private Context context;

    public TestAdapter(Context context, String[] topics) {
        this.context = context;
        this.topics = topics;
    }

    @NonNull
    @Override
    public TestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.topicrow, parent, false);
        return new TestHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestHolder holder, int position) {
        holder.title.setText(topics[position]);
    }

    @Override
    public int getItemCount() {
        return topics.length;
    }

    public class TestHolder extends RecyclerView.ViewHolder {

        TextView title, description;

        public TestHolder (@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.topic_name);
        }
    }
}

