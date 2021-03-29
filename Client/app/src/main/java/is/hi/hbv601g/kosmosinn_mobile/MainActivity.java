package is.hi.hbv601g.kosmosinn_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ComponentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import is.hi.hbv601g.kosmosinn_mobile.Adapters.BoardAdapter;
import is.hi.hbv601g.kosmosinn_mobile.Controllers.NetworkCallback;
import is.hi.hbv601g.kosmosinn_mobile.Controllers.NetworkController;
import is.hi.hbv601g.kosmosinn_mobile.Entities.Board;
import is.hi.hbv601g.kosmosinn_mobile.Entities.Topic;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView boardView;
    private List<Board> mBoards;
    private List<Topic> mTopics;

    private BoardAdapter boardAdapter;
    private String[] mBoardNames;
    private String[] mBoardDescriptions;

    private Button mLoginButton;
    private Button mSignupButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boardView = (RecyclerView) findViewById(R.id.board_view);
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
                mBoards = result;
                mBoardNames = new String[mBoards.size()];
                mBoardDescriptions = new String[mBoards.size()];
                for (int i = 0; i < mBoards.size(); i++) {
                    mBoardNames[i] = mBoards.get(i).getName();
                    mBoardDescriptions[i] = mBoards.get(i).getDescription();
                }
                boardAdapter = new BoardAdapter(MainActivity.this, mBoardNames, mBoardDescriptions);
                boardView.setAdapter(boardAdapter);
                boardView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get board: " + errorString);
            }
        });
        networkController.getBoard(2, new NetworkCallback<Board>() {
            @Override
            public void onSuccess(Board result) {
                Log.d(TAG, "Board text for id :" + String.valueOf(2) + " is " + result.getName());
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get single board: " + errorString);
            }
        });

/*
        networkController.getAllTopics(new NetworkCallback<List<Topic>>() {
            @Override
            public void onSuccess(List<Topic> result) {
                mTopics = result;
                Log.d(TAG, "First topic: " + mTopics.get(0).getTopicContent());
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get board: " + errorString);
            }
        });

        */

    }

}