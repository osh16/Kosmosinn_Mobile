package is.hi.hbv601g.kosmosinn_mobile.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import is.hi.hbv601g.kosmosinn_mobile.Entities.Board;
import is.hi.hbv601g.kosmosinn_mobile.Activities.MainActivity;
import is.hi.hbv601g.kosmosinn_mobile.R;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.BoardHolder> {
    private static final String TAG = "BoardAdapter";
    private Context mContext;
    private Board mBoards[];

    public BoardAdapter(Context context, Board boards[]) {
        this.mContext = context;
        this.mBoards = boards;
    }
    @NonNull
    @Override
    public BoardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.boardrow, parent, false);
        return new BoardHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BoardHolder holder, int position) {
        holder.title.setText(mBoards[position].getName());
        holder.description.setText(mBoards[position].getDescription());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick -> Board with id: " + mBoards[position].getId());
                ((MainActivity)mContext).selectBoard(mBoards[position].getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBoards.length;
    }

    public class BoardHolder extends RecyclerView.ViewHolder {
        TextView title, description;

        public BoardHolder (@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.board_title);
            description = itemView.findViewById(R.id.board_description);
        }
    }
}
