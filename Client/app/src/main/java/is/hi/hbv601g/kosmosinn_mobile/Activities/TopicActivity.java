package is.hi.hbv601g.kosmosinn_mobile.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Network;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import is.hi.hbv601g.kosmosinn_mobile.Adapters.CommentAdapter;
import is.hi.hbv601g.kosmosinn_mobile.Controllers.NetworkCallback;
import is.hi.hbv601g.kosmosinn_mobile.Controllers.NetworkController;
import is.hi.hbv601g.kosmosinn_mobile.Entities.Comment;
import is.hi.hbv601g.kosmosinn_mobile.R;

public class TopicActivity extends AppCompatActivity {
    private static final String TAG = "TopicActivity";
    private RecyclerView mCommentView;
    private List<Comment> mCommentList;
    private CommentAdapter mCommentAdapter;

    private Comment[] mComments;
    private int mTopicId;
    private int mUserId;
    private Button mBackButton;
    private Button mAddCommentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        mCommentView = (RecyclerView) findViewById(R.id.comment_view);
        mBackButton = (Button) findViewById(R.id.topic_back_button);
        mAddCommentButton = (Button) findViewById(R.id.add_comment_button);
        mTopicId = getIntent().getIntExtra("topicid",0);
        mUserId = getIntent().getIntExtra("userid", 0);

        Log.d(TAG, "Topic id = " + mTopicId);
        Log.d(TAG, "Button: " + mBackButton);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick -> Til baka");
                Intent intent = new Intent(TopicActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        NetworkController networkController = NetworkController.getInstance(this);
        if (mTopicId != 0) {
            getCommentsByTopic(networkController);
        }
        else {
            getCommentsByUser(networkController);
        }

        mAddCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick -> Add Comment");
                Intent intent = new Intent(TopicActivity.this, AddCommentActivity.class);
                intent.putExtra("topicid", mTopicId);
                startActivity(intent);
            }
        });
    }

    public void getCommentsByTopic(NetworkController networkController) {
        networkController.getCommentsByTopicId(mTopicId, new NetworkCallback<List<Comment>>() {
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
        networkController.getCommentsByUserId(mUserId, new NetworkCallback<List<Comment>>() {
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