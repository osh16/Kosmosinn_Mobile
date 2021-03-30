package is.hi.hbv601g.kosmosinn_mobile.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import is.hi.hbv601g.kosmosinn_mobile.BoardActivity;
import is.hi.hbv601g.kosmosinn_mobile.MainActivity;
import is.hi.hbv601g.kosmosinn_mobile.R;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicHolder> {
    private static final String TAG = "BoardAdapter";
    private Context mContext;
    private String mTopics[];
    private int mIds[];

    public TopicAdapter(Context context, String topics[], int ids[]) {
        this.mContext = context;
        this.mTopics = topics;
        this.mIds = ids;
    }
    @NonNull
    @Override
    public TopicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.topicrow, parent, false);
        return new TopicHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicHolder holder, int position) {
        String name = mTopics[position];
        Log.d(TAG,name);

        holder.topicName.setText(mTopics[position]);
        holder.topicName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick -> Topic with id: " + mIds[position]);
                ((BoardActivity)mContext).selectTopic(mIds[position]);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTopics.length;
    }

    public class TopicHolder extends RecyclerView.ViewHolder {
        TextView topicName;

        public TopicHolder (@NonNull View itemView) {
            super(itemView);
            topicName = itemView.findViewById(R.id.topic_name);
        }
    }
}
