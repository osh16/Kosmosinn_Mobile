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
import is.hi.hbv601g.kosmosinn_mobile.Entities.Comment;
import is.hi.hbv601g.kosmosinn_mobile.Entities.Topic;
import is.hi.hbv601g.kosmosinn_mobile.Entities.User;

public class MainActivity extends AppCompatActivity {
    private RecyclerView boardView;
    private List<Board> mBoards;
    private List<Topic> mTopics;
    private List<Topic> mBoardTopics;
    private List<Comment> mComments;
    private List<User> mUsers;
    private static final String TAG = "MainActivity";

    private BoardAdapter boardAdapter;
    private String[] mBoardNames;
    private String[] mBoardDescriptions;

    private String[] mTopicNames;
    private String[] mBoardTopicNames;
    private String[] mTopicContent;
    private String[] mBoardTopicContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boardView = (RecyclerView) findViewById(R.id.board_view);

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
                //Log.d(TAG, "First board name: " + mBoards.get(0).getName());
                boardAdapter = new BoardAdapter(MainActivity.this, mBoardNames, mBoardDescriptions);
                boardView.setAdapter(boardAdapter);
                boardView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get all boards: " + errorString);
            }
        });
        networkController.getAllTopics(new NetworkCallback<List<Topic>>() {
            @Override
            public void onSuccess(List<Topic> result) {
                mTopics = result;
                int size = mTopics.size();
                mTopicNames = new String[size];
                mTopicContent = new String[size];
                for (int i = 0; i < size; i++) {
                    mTopicNames[i] = mTopics.get(i).getTopicName();
                    mTopicContent[i] = mTopics.get(i).getTopicContent();
                }
                Log.d(TAG, "First topic name: " + mTopics.get(0).getTopicName());
                boardAdapter = new BoardAdapter(MainActivity.this, mTopicNames, mTopicContent);
                boardView.setAdapter(boardAdapter);
                boardView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }
            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get all topics: " + errorString);
            }
        });
/*
        networkController.getBoardTopics(1, new NetworkCallback<List<Topic>>() {
            @Override
            public void onSuccess(List<Topic> result) {
                mBoardTopics = result;
                int size = mBoardTopics.size();
                mBoardTopicNames = new String[size];
                mBoardTopicContent = new String[size];
                for (int i = 0; i < size; i++) {
                    mBoardTopicNames[i] = mBoardTopics.get(i).getTopicName();
                    mBoardTopicContent[i] = mBoardTopics.get(i).getTopicContent();
                }
                Log.d(TAG, "First board topic name: " + mBoardTopics.get(0).getTopicName());
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get all board topics: " + errorString);
            }
        });
 */
        networkController.getBoard(1, new NetworkCallback<Board>() {
            @Override
            public void onSuccess(Board result) {
                Log.d(TAG, "Board text for id " + String.valueOf(1) + ": " + result.getName());
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get single board: " + errorString);
            }
        });
        /*
        networkController.getTopic(0, new NetworkCallback<Topic>() {
            @Override
            public void onSuccess(Topic result) {
                Log.d(TAG, "Board text for id :" + String.valueOf(0) + " is " + result.getTopicName());
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get single topic: " + errorString);
            }
        });
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
                Log.d(TAG, "First board name: " + mBoards.get(0).getName());
                boardAdapter = new BoardAdapter(MainActivity.this, mBoardNames, mBoardDescriptions);
                boardView.setAdapter(boardAdapter);
                boardView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get all boards: " + errorString);
            }
        });
        networkController.getAllTopics(new NetworkCallback<List<Topic>>() {
            @Override
            public void onSuccess(List<Topic> result) {
                mTopics = result;
                int size = mTopics.size();
                mTopicNames = new String[size];
                mTopicDescriptions = new String[size];
                for (int i = 0; i < size; i++) {
                    mTopicNames[i] = mTopics.get(i).getTopicName();
                    mTopicDescriptions[i] = mTopics.get(i).getTopicContent();
                }
                Log.d(TAG, "First topic name: " + mTopics.get(0).getTopicName());
                boardAdapter = new BoardAdapter(MainActivity.this, mTopicNames, mTopicDescriptions);
                boardView.setAdapter(boardAdapter);
                boardView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }
            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get all topics: " + errorString);
            }
        });

        networkController.getAllTopics(new NetworkCallback<List<Topic>>() {
            @Override
            public void onSuccess(List<Topic> result) {
                mTopics = result;
                Log.d(TAG, "First topic name: " + mTopics.get(0).getTopicName());
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get all topics: " + errorString);
            }
        });
            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get all topics: " + errorString);
            }
        });
        networkController.getAllTopics(new NetworkCallback<List<Topic>>() {
            @Override
            public void onSuccess(List<Topic> result) {
                mTopics = result;
                Log.d(TAG, "First topic name: " + mTopics.get(0).getTopicName());
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get all topics: " + errorString);
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
        networkController.getTopic(0, new NetworkCallback<Topic>() {
            @Override
            public void onSuccess(Topic result) {
                Log.d(TAG, "Board text for id :" + String.valueOf(0) + " is " + result.getTopicName());
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get single topic: " + errorString);
            }
        });
        networkController.getAllComments(new NetworkCallback<List<Comment>>() {
            @Override
            public void onSuccess(List<Comment> result) {
                mComments = result;
                Log.d(TAG, "First comment: " + mComments.get(0).getCommentText());
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get all comments: " + errorString);
            }
        networkController.getAllUsers(new NetworkCallback<List<User>>() {
            @Override
            public void onSuccess(List<User> result) {
                mUsers = result;
                int size = mUsers.size();
                for (int i = 0; i < size; i++) {
                    Log.d(TAG, "user " + i + " " + mUsers.get(i).getUsername());
                }
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get all users: " + errorString);
            }
        });
        });
 */
    }
}