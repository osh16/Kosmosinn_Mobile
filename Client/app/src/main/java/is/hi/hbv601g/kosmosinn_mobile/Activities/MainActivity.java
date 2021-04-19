package is.hi.hbv601g.kosmosinn_mobile.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import is.hi.hbv601g.kosmosinn_mobile.Adapters.BoardAdapter;
import is.hi.hbv601g.kosmosinn_mobile.Controllers.NetworkCallback;
import is.hi.hbv601g.kosmosinn_mobile.Controllers.NetworkController;
import is.hi.hbv601g.kosmosinn_mobile.Entities.Board;
import is.hi.hbv601g.kosmosinn_mobile.R;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView mBoardView;
    private List<Board> mBoardList;
    private BoardAdapter mBoardAdapter;

    private Board[] mBoards;

    private Button mLoginButton;
    private Button mSignupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String AUTHORIZATION = "";

        if (getIntent().hasExtra("Authorization")) {
            AUTHORIZATION = getIntent().getStringExtra("Authorization");
        }

        Log.d(TAG, "Authies" + AUTHORIZATION);

        mBoardView = (RecyclerView) findViewById(R.id.boards_view);
        mLoginButton = (Button) findViewById(R.id.login_activity_button);
        mSignupButton = (Button) findViewById(R.id.signup_activity_button);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick -> Login");
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick -> Signup");
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);

                startActivity(intent);
            }
        });

        NetworkController networkController = NetworkController.getInstance(this);
        networkController.getAllBoards(new NetworkCallback<List<Board>>() {
            @Override
            public void onSuccess(List<Board> result) {
                mBoardList = result;
                int size = mBoardList.size();
                mBoards = new Board[size];
                for (int i = 0; i < size; i++) {
                    mBoards[i] = mBoardList.get(i);
                    Log.d(TAG, "Board id = " + mBoards[i].getId());
                }
                mBoardAdapter = new BoardAdapter(MainActivity.this, mBoards);
                mBoardView.setAdapter(mBoardAdapter);
                mBoardView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get board: " + errorString);
            }
        });
    }

    public void selectBoard(int id) {
        Log.d(TAG, "Select Board");

        String authorization = "null";

        if (getIntent().hasExtra("Authorization")) {
            authorization = getIntent().getStringExtra("Authorization");
        }

        Intent intent = new Intent(MainActivity.this, BoardActivity.class)
                .putExtra("Authorization", authorization);

        Log.d(TAG, authorization);

        intent.putExtra("boardid", id);

        startActivity(intent);
    }
}