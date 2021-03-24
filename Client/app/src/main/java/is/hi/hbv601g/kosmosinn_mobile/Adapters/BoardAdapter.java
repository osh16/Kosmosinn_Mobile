package is.hi.hbv601g.kosmosinn_mobile.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import is.hi.hbv601g.kosmosinn_mobile.R;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.BoardHolder> {
    private Context context;
    private String boards[];
    private String descriptions[];
    private int boardIds[];
    private Button buttons[];

    public BoardAdapter(Context context, String boards[], String descriptions[], int boardIds[], Button buttons[]) {
        this.context = context;
        this.boards = boards;
        this.descriptions = descriptions;
        this.boardIds = boardIds;
        this.buttons = buttons;
    }

    @NonNull
    @Override
    public BoardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.boardrow, parent, false);
        return new BoardHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BoardHolder holder, int position) {
        holder.title.setText(boards[position]);
        holder.description.setText(descriptions[position]);
    }

    @Override
    public int getItemCount() {
        return boards.length;
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
