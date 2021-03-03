package is.hi.hbv601g.kosmosinn_mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import is.hi.hbv601g.kosmosinn_mobile.Adapters.BoardAdapter;

public class MainActivity extends AppCompatActivity {
    private RecyclerView boardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boardView = (RecyclerView) findViewById(R.id.board_view);

        String boards[] = {"board 1","board2","hehe","hvad er malid"};
        String descriptions[] = {"board 1","board2","hehe","hvad er malid"};

        BoardAdapter cringeAdapter =  new BoardAdapter(this,boards,descriptions);
        boardView.setAdapter(cringeAdapter);
        boardView.setLayoutManager(new LinearLayoutManager(this));

    }
}