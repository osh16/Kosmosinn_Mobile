package is.hi.hbv601g.kosmosinn_mobile.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import is.hi.hbv601g.kosmosinn_mobile.Activities.BoardActivity;
import is.hi.hbv601g.kosmosinn_mobile.Entities.Topic;
import is.hi.hbv601g.kosmosinn_mobile.R;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicHolder> {
    private static final String TAG = "BoardAdapter";
    private Context mContext;
    private Topic mTopics[];

    public TopicAdapter(Context context, Topic topics[]) {
        this.mContext = context;
        this.mTopics = topics;
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
        holder.user.setText(mTopics[position].getUser().getUsername());
        holder.user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = mTopics[position].getUser().getId();
                String name = mTopics[position].getUser().getUsername();
                ((BoardActivity)mContext).selectUserFromBoard(id, name);
            }
        });
        holder.topicName.setText(mTopics[position].getTopicName());
        holder.topicName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick -> Topic with id: " + mTopics[position].getId());
                ((BoardActivity)mContext).selectTopic(mTopics[position].getId());
            }
        });
        String s = String.valueOf(mTopics[position].getTopicPoints());
        holder.topicPoints.setText(s);
    }

    @Override
    public int getItemCount() {
        return mTopics.length;
    }

    public class TopicHolder extends RecyclerView.ViewHolder {
        TextView user, topicName, topicPoints;

        public TopicHolder (@NonNull View itemView) {
            super(itemView);
            user = itemView.findViewById(R.id.topic_user);
            topicName = itemView.findViewById(R.id.topic_name);
            topicPoints = itemView.findViewById(R.id.topic_points);
        }
    }
}
