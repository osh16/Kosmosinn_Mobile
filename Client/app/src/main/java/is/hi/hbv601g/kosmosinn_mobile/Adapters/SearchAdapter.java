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
import is.hi.hbv601g.kosmosinn_mobile.Activities.SearchActivity;
import is.hi.hbv601g.kosmosinn_mobile.Entities.Topic;
import is.hi.hbv601g.kosmosinn_mobile.R;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchHolder> {
    private static final String TAG = "SearchAdapter";
    private Context mContext;
    private Topic mTopics[];

    public SearchAdapter(Context context, Topic topics[]) {
        this.mContext = context;
        this.mTopics = topics;
    }
    @NonNull
    @Override
    public SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.searchrow, parent, false);
        return new SearchHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHolder holder, int position) {
        holder.user.setText(mTopics[position].getUser().getUsername());
        holder.topicName.setText(mTopics[position].getTopicName());
        holder.topicName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick -> Topic with id: " + mTopics[position].getId());
                ((SearchActivity)mContext).selectTopicFromSearch(mTopics[position].getId());
            }
        });
        String s = String.valueOf(mTopics[position].getTopicPoints());
        holder.topicPoints.setText(s);
    }

    @Override
    public int getItemCount() {
        return mTopics.length;
    }

    public class SearchHolder extends RecyclerView.ViewHolder {
        TextView user, topicName, topicPoints;

        public SearchHolder (@NonNull View itemView) {
            super(itemView);
            user = itemView.findViewById(R.id.search_topic_user);
            topicName = itemView.findViewById(R.id.search_topic_name);
            topicPoints = itemView.findViewById(R.id.search_topic_points);
        }
    }
}
