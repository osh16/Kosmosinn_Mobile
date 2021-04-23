package is.hi.hbv601g.kosmosinn_mobile.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Network;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import is.hi.hbv601g.kosmosinn_mobile.Adapters.CommentAdapter;
import is.hi.hbv601g.kosmosinn_mobile.Controllers.NetworkCallback;
import is.hi.hbv601g.kosmosinn_mobile.Controllers.NetworkController;
import is.hi.hbv601g.kosmosinn_mobile.Entities.Comment;
import is.hi.hbv601g.kosmosinn_mobile.Entities.Topic;
import is.hi.hbv601g.kosmosinn_mobile.R;

public class TopicActivity extends AppCompatActivity {
    private static final String TAG = "TopicActivity";
    private RecyclerView mCommentView;
    private List<Comment> mCommentList;
    private CommentAdapter mCommentAdapter;

    private Comment[] mComments;
    private Topic mTopic;
    private int mBoardId;
    private int mTopicId;
    private int mUserId;
    private String mUsername;
    private boolean mFromSearch;

    private Button mHomeButton;
    private Button mBackButton;
    private Button mEditTopicButton;
    private Button mDeleteTopicButton;
    private Button mAddCommentButton;
    private TextView mTopicHeader;

    private String mToken;
    private String mCurrentUsername;
    private int mCurrentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        mCommentView = (RecyclerView) findViewById(R.id.comment_view);
        mTopicHeader = (TextView) findViewById(R.id.topic_header);
        mHomeButton = (Button) findViewById(R.id.topic_home_button);
        mBackButton = (Button) findViewById(R.id.topic_back_button);
        mEditTopicButton = (Button) findViewById(R.id.topic_edit_button);
        mDeleteTopicButton = (Button) findViewById(R.id.topic_delete_button);
        mAddCommentButton = (Button) findViewById(R.id.add_comment_button);

        mTopicId = getIntent().getIntExtra("topicid",0);
        mBoardId = getIntent().getIntExtra("boardid",0);
        mUserId = getIntent().getIntExtra("userid", 0);

        mUsername = getIntent().getStringExtra("username");
        mFromSearch = getIntent().getBooleanExtra("fromsearch", false);

        SharedPreferences sharedPreferences = getSharedPreferences(
                "KosmosinnSharedPref",
                MODE_PRIVATE);

        mToken = sharedPreferences.getString("Authorization", "");
        mCurrentUserId = sharedPreferences.getInt("userId", 0);
        mCurrentUsername = sharedPreferences.getString("username", "");

        NetworkController networkController = NetworkController.getInstance(this);

        networkController.getTopic(mTopicId, new NetworkCallback<Topic>() {
            @Override
            public void onSuccess(Topic result) {
                mTopic = result;
                if (mTopic.getUser().getId() != mCurrentUserId) {
                    mDeleteTopicButton.setVisibility(View.GONE);
                    mEditTopicButton.setVisibility((View.GONE));
                }
            }

            @Override
            public void onFailure(String errorString) {
                Log.d(TAG, errorString);
            }
        });

        Log.d(TAG, "Topic id = " + mTopicId);
        Log.d(TAG, "Button: " + mBackButton);

        mHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TopicActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFromSearch) {
                    Intent intent = new Intent(TopicActivity.this, SearchActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(TopicActivity.this, BoardActivity.class);
                    intent.putExtra("boardid", mBoardId);

                    startActivity(intent);
                }
            }
        });

        mEditTopicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                networkController.editTopic(mTopicId, mToken, mTopic, new NetworkCallback<Topic>() {
                    @Override
                    public void onSuccess(Topic result) {
                        Log.d(TAG, "Topic edited");
                    }

                    @Override
                    public void onFailure(String errorString) {
                        Log.d(TAG, errorString);
                    }
                });
                Intent intent = new Intent(TopicActivity.this, AddTopicActivity.class);
                intent.putExtra("edittopic", true);
                intent.putExtra("boardid", mBoardId);
                intent.putExtra("topicid", mTopicId);
                startActivity(intent);
            }
        });

        mDeleteTopicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                networkController.deleteTopic(mTopicId, mToken, new NetworkCallback<Topic>() {
                    @Override
                    public void onSuccess(Topic result) {
                        Log.d(TAG, "Topic deleted");
                    }

                    @Override
                    public void onFailure(String errorString) {
                        Log.d(TAG, errorString);
                    }
                });
                Intent intent = new Intent(TopicActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        if (mTopicId != 0) {
            getCommentsByTopic(networkController);
            new android.os.Handler(Looper.getMainLooper()).postDelayed(
                    new Runnable() {
                        public void run() {
                            mTopicHeader.setText(mTopic.getTopicName());
                        }
                    },
                    300);
        }
        else {
            getCommentsByUser(networkController);
            mAddCommentButton.setVisibility(View.GONE);
            mBackButton.setVisibility(View.GONE);
            mTopicHeader.setText(mUsername);
        }

        mAddCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick -> Add Comment");
                Intent intent = new Intent(TopicActivity.this, AddCommentActivity.class);
                intent.putExtra("boardId", mBoardId);
                intent.putExtra("topicid", mTopicId);
                startActivity(intent);
            }
        });
    }

    public void getCommentsByTopic(NetworkController networkController) {
        networkController.getCommentsByTopicId(mTopicId, mToken, new NetworkCallback<List<Comment>>() {
            @Override
            public void onSuccess(List<Comment> result) {
                Log.d(TAG, "Comments for TopicId :" + String.valueOf(mTopicId));
                mCommentList = result;
                int size = mCommentList.size();
                mComments = new Comment[size];

                for (int i = 0; i < size; i++) {
                    mComments[i] = mCommentList.get(i);
                    Log.d(TAG, "Comment: " + mComments[i].getCommentText());
                }
                mCommentAdapter = new CommentAdapter(TopicActivity.this, mComments);
                mCommentView.setAdapter(mCommentAdapter);
                mCommentView.setLayoutManager(new LinearLayoutManager(TopicActivity.this));
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get single comment: " + errorString);
            }
        });
    }

    public void getCommentsByUser(NetworkController networkController) {
        Log.d(TAG, String.valueOf(mUserId));
        networkController.getCommentsByUserId(mUserId, mToken, new NetworkCallback<List<Comment>>() {
            @Override
            public void onSuccess(List<Comment> result) {
                Log.d(TAG, "Comments for UserId :" + String.valueOf(mUserId));
                mCommentList = result;
                int size = mCommentList.size();
                mComments = new Comment[size];

                for (int i = 0; i < size; i++) {
                    mComments[i] = mCommentList.get(i);
                    Log.d(TAG, "Comment: " + mComments[i].getCommentText());
                }
                mCommentAdapter = new CommentAdapter(TopicActivity.this, mComments);
                mCommentView.setAdapter(mCommentAdapter);
                mCommentView.setLayoutManager(new LinearLayoutManager(TopicActivity.this));
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get single comment: " + errorString);
            }
        });
    }

    public void selectUserpageFromTopic(int id, String name) {
        Log.d(TAG, "Select User");
        Intent intent = new Intent(TopicActivity.this, UserActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("username", name);
        startActivity(intent);
    }
}