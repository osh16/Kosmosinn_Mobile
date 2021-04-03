package is.hi.hbv601g.kosmosinn_mobile.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import is.hi.hbv601g.kosmosinn_mobile.R;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {
    private static final String TAG = "CommentAdapter";
    private Context mContext;
    private String mComments[];
    private int mIds[];

    public CommentAdapter(Context context, String comments[], int ids[]) {
        this.mContext = context;
        this.mComments = comments;
        this.mIds = ids;
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
        holder.commentText.setText(mComments[position]);
    }

    @Override
    public int getItemCount() {
        return mComments.length;
    }

    public class CommentHolder extends RecyclerView.ViewHolder {
        TextView commentText;

        public CommentHolder (@NonNull View itemView) {
            super(itemView);
            commentText = itemView.findViewById(R.id.comment_text);
        }
    }
}
