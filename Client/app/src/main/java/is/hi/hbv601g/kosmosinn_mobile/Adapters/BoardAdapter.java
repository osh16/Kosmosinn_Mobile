package is.hi.hbv601g.kosmosinn_mobile.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;

import is.hi.hbv601g.kosmosinn_mobile.MainActivity;
import is.hi.hbv601g.kosmosinn_mobile.R;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.BoardHolder> {
    private static final String TAG = "BoardAdapter";
    private Context mContext;
    private String mBoards[];
    private String mDescriptions[];
    private int mIds[];

    public BoardAdapter(Context context, String boards[], String descriptions[], int ids[]) {
        this.mContext = context;
        this.mBoards = boards;
        this.mDescriptions = descriptions;
        this.mIds = ids;
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
        String name = mBoards[position];
        Log.d(TAG,name);

        holder.title.setText(mBoards[position]);
        holder.description.setText(mDescriptions[position]);
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick -> Board with id: " + mIds[position]);
                ((MainActivity)mContext).selectBoard(mIds[position]);
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
