package is.hi.hbv601g.kosmosinn_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ComponentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
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
    private RecyclerView boardView;
    private List<Board> mBoards;
    private List<Topic> mTopics;
    private static final String TAG = "MainActivity";

    private BoardAdapter boardAdapter;
    private String[] mBoardNames;
    private String[] mBoardDescriptions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // herna tharftu ad setja lan ip toluna thina
        // skitalausn sem vid notum i bili, thangad til annad kemur i ljos
        // muna ad keyra serverinn
        //String url = "http://10.5.0.2:8080/api/boards";
        //String url = "http://192.168.1.205:8080/api/boards";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boardView = (RecyclerView) findViewById(R.id.board_view);

        NetworkController networkController = NetworkController.getInstance(this);
        networkController.getAllBoards(new NetworkCallback<List<Board>>() {
            @Override
            public void onSuccess(List<Board> result) {
                mBoards = result;
                mBoardNames = new String[3];
                mBoardDescriptions = new String[3];
                for (int i = 0; i < 3; i++) {
                    mBoardNames[i] = mBoards.get(i).getName();
                    mBoardDescriptions[i] = mBoards.get(i).getDescription();
                }
                /*
                Log.d(TAG, "First board name: " + mBoards.get(0).getName());
                Log.d(TAG, "First board description: " + mBoards.get(0).getDescription());
                Log.d(TAG, "First board id: " + mBoards.get(0).getId());
                */
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