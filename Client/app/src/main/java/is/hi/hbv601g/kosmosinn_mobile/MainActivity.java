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
import android.widget.TextView;
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
import is.hi.hbv601g.kosmosinn_mobile.Entities.User;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView mBoardView;
    private List<Board> mBoards;
    private BoardAdapter mBoardAdapter;

    private String[] mBoardNames;
    private String[] mBoardDescriptions;
    private int[] mBoardIds;

    private User user;

    private Button mLoginButton;
    private Button mSignupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBoardView = (RecyclerView) findViewById(R.id.boards_view);
        mLoginButton = (Button) findViewById(R.id.login_activity_button);
        mSignupButton = (Button) findViewById(R.id.signup_activity_button);

        NetworkController networkController = NetworkController.getInstance(this);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d(TAG, "onClick -> Login");
                //Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                //startActivity(intent);
                networkController.getUser(1, new NetworkCallback<User>() {
                    @Override
                    public void onSuccess(User result) {
                        user = result;
                        Log.d("OSKAR", ""+result.getRole());
                        Log.d("OSKAR", ""+result.getUsername());
                    }

                    @Override
                    public void onFailure(String errorString) {
                        Log.d("OSKAR", errorString.toString());
                    }
                });

                Topic topic = new Topic(1, user, "topic", "ass", 1, 1, "mars", "april");
                networkController.addTopic(1, topic, new NetworkCallback<Topic>() {
                    @Override
                    public void onSuccess(Topic result) {
                    }

                    @Override
                    public void onFailure(String errorString) {
                    }
                });
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

        networkController.getAllBoards(new NetworkCallback<List<Board>>() {
            @Override
            public void onSuccess(List<Board> result) {
                mBoards = result;
                mBoardNames = new String[mBoards.size()];
                mBoardDescriptions = new String[mBoards.size()];
                mBoardIds = new int[mBoards.size()];
                for (int i = 0; i < mBoards.size(); i++) {
                    mBoardNames[i] = mBoards.get(i).getName();
                    mBoardDescriptions[i] = mBoards.get(i).getDescription();
                    mBoardIds[i] = mBoards.get(i).getId();
                    Log.d(TAG, "Board id = " + mBoardIds[i]);
                }
                mBoardAdapter = new BoardAdapter(MainActivity.this, mBoardNames, mBoardDescriptions, mBoardIds);
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
        Intent intent = new Intent(MainActivity.this, BoardActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}