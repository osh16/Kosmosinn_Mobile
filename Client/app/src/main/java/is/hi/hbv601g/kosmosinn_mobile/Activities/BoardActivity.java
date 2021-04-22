package is.hi.hbv601g.kosmosinn_mobile.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import is.hi.hbv601g.kosmosinn_mobile.Adapters.TopicAdapter;
import is.hi.hbv601g.kosmosinn_mobile.Controllers.NetworkCallback;
import is.hi.hbv601g.kosmosinn_mobile.Controllers.NetworkController;
import is.hi.hbv601g.kosmosinn_mobile.Entities.Board;
import is.hi.hbv601g.kosmosinn_mobile.Entities.Topic;
import is.hi.hbv601g.kosmosinn_mobile.R;

public class BoardActivity extends AppCompatActivity {
    private static final String TAG = "BoardActivity";
    private RecyclerView mTopicView;
    private List<Topic> mTopicList;
    private TopicAdapter mTopicAdapter;

    private Topic[] mTopics;
    private int mBoardId;
    private int mUserId;
    private String mUsername;
    private Board mBoard;
    private Button mBackButton;
    private Button mAddTopicButton;
    private TextView mBoardHeader;

    private String mToken;
    private int mCurrentUserId;
    private String mCurrentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        mTopicView = (RecyclerView) findViewById(R.id.topic_view);
        mBoardHeader = (TextView) findViewById(R.id.board_header);
        mBackButton = (Button) findViewById(R.id.board_back_button);
        mAddTopicButton = (Button) findViewById(R.id.add_topic_button);
        mBoardId = getIntent().getIntExtra("boardid",0);
        mUserId = getIntent().getIntExtra("userid", 0);
        mUsername = getIntent().getStringExtra("username");

        SharedPreferences sharedPreferences = getSharedPreferences(
                "KosmosinnSharedPref",
                MODE_PRIVATE);

        mToken = sharedPreferences.getString("Authorization", "");
        mCurrentUserId = sharedPreferences.getInt("userId", 0);
        mCurrentUsername = sharedPreferences.getString("username", "");

        NetworkController networkController = NetworkController.getInstance(this);

        networkController.getBoard(mBoardId, new NetworkCallback<Board>() {
            @Override
            public void onSuccess(Board result) {
                mBoard = result;
            }

            @Override
            public void onFailure(String errorString) {
                Log.d(TAG, errorString);
            }
        });

        Log.d(TAG, "Board id = " + mBoardId);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick -> Til baka");
                Intent intent = new Intent(BoardActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        if (mBoardId != 0) {
            getTopicsByBoard(networkController);
            new android.os.Handler(Looper.getMainLooper()).postDelayed(
                    new Runnable() {
                        public void run() {
                            mBoardHeader.setText(mBoard.getName());
                        }
                    },
                    400);
        }
        else {
            getTopicsByUser(networkController);
            mAddTopicButton.setVisibility(View.GONE);
            mBackButton.setVisibility(View.GONE);
            mBoardHeader.setText(mUsername);
        }

        mAddTopicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick -> Add Topic");
                Intent intent = new Intent(BoardActivity.this, AddTopicActivity.class);
                intent.putExtra("boardid", mBoardId);
                startActivity(intent);
            }
        });
    }

    public void getTopicsByBoard(NetworkController networkController) {
        networkController.getTopicsByBoardId(mBoardId, new NetworkCallback<List<Topic>>() {
            @Override
            public void onSuccess(List<Topic> result) {
                Log.d(TAG, "Board text for id :" + String.valueOf(mBoardId));
                mTopicList = result;
                int size = mTopicList.size();
                mTopics = new Topic[size];

                for (int i = 0; i < size; i++) {
                    mTopics[i] = mTopicList.get(i);
                    Log.d(TAG, "Topic Name: " + mTopics[i].getTopicName());
                }
                mTopicAdapter = new TopicAdapter(BoardActivity.this, mTopics);
                mTopicView.setAdapter(mTopicAdapter);
                mTopicView.setLayoutManager(new LinearLayoutManager(BoardActivity.this));
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get single board: " + errorString);
            }
        });
    }

    public void getTopicsByUser(NetworkController networkController) {
        networkController.getTopicsByUserId(mUserId, new NetworkCallback<List<Topic>>() {
            @Override
            public void onSuccess(List<Topic> result) {
                Log.d(TAG, "Board text for userid :" + String.valueOf(mUserId));
                mTopicList = result;
                int size = mTopicList.size();
                mTopics = new Topic[size];

                for (int i = 0; i < size; i++) {
                    mTopics[i] = mTopicList.get(i);
                    Log.d(TAG, "Topic Name: " + mTopics[i].getTopicName());
                }
                mTopicAdapter = new TopicAdapter(BoardActivity.this, mTopics);
                mTopicView.setAdapter(mTopicAdapter);
                mTopicView.setLayoutManager(new LinearLayoutManager(BoardActivity.this));
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get single board: " + errorString);
            }
        });
    }

    public void selectTopic(int id) {
        Log.d(TAG, "Select Topic");
        Intent intent = new Intent(BoardActivity.this, TopicActivity.class);
        intent.putExtra("topicid", id);
        intent.putExtra("boardid", mBoardId);
        startActivity(intent);
    }

    public void selectUserFromBoard(int id, String name) {
        Log.d(TAG, "Select User");
        Intent intent = new Intent(BoardActivity.this, UserActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("username", name);
        startActivity(intent);
    }
}