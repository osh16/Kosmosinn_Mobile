package is.hi.hbv601g.kosmosinn_mobile.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import is.hi.hbv601g.kosmosinn_mobile.Entities.Comment;
import is.hi.hbv601g.kosmosinn_mobile.R;
import is.hi.hbv601g.kosmosinn_mobile.Activities.TopicActivity;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {
    private static final String TAG = "CommentAdapter";
    private Context mContext;
    private Comment mComments[];

    public CommentAdapter(Context context, Comment comments[]) {
        this.mContext = context;
        this.mComments = comments;
    }
    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.commentrow, parent, false);
        return new CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder holder, int position) {
        holder.user.setText(mComments[position].getUser().getUsername());
        holder.user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = mComments[position].getUser().getId();
                String name = mComments[position].getUser().getUsername();
                ((TopicActivity)mContext).selectUserpageFromTopic(id, name);
            }
        });
        holder.commentText.setText(mComments[position].getCommentText());
    }

    @Override
    public int getItemCount() {
        return mComments.length;
    }

    public class CommentHolder extends RecyclerView.ViewHolder {
        TextView user, commentText;

        public CommentHolder (@NonNull View itemView) {
            super(itemView);
            user = itemView.findViewById(R.id.comment_user);
            commentText = itemView.findViewById(R.id.comment_text);
        }
    }
}
