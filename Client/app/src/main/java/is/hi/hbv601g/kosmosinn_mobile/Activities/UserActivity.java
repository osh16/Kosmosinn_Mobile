package is.hi.hbv601g.kosmosinn_mobile.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import is.hi.hbv601g.kosmosinn_mobile.Adapters.ProfileAdapter;
import is.hi.hbv601g.kosmosinn_mobile.Adapters.SearchAdapter;
import is.hi.hbv601g.kosmosinn_mobile.Controllers.NetworkCallback;
import is.hi.hbv601g.kosmosinn_mobile.Controllers.NetworkController;
import is.hi.hbv601g.kosmosinn_mobile.Entities.Comment;
import is.hi.hbv601g.kosmosinn_mobile.Entities.Topic;
import is.hi.hbv601g.kosmosinn_mobile.Entities.User;
import is.hi.hbv601g.kosmosinn_mobile.R;

public class UserActivity extends AppCompatActivity {
    private static final String TAG = "UserActivity";
    private RecyclerView mSignatureView;
    private ProfileAdapter mProfileAdapter;

    private int mUserId;
    private String mUsername;
    private List<Comment> mCommentList;
    private Comment[] mComments;
    private String mComment;

    private EditText mCommentForm;
    private TextView mUserText;
    private Button mBackButton;
    private Button mTopicsButton;
    private Button mCommentsButton;
    private Button mAddCommentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        mUserId = getIntent().getIntExtra("id", 2);
        mUsername = getIntent().getStringExtra("username");
        Log.d(TAG, "User with id: " + mUserId + " and username: " + mUsername);

        mSignatureView = (RecyclerView) findViewById(R.id.signature_view);
        mCommentForm = (EditText) findViewById(R.id.comment_userprofile_field);
        mUserText = (TextView) findViewById(R.id.user_name);
        mBackButton = (Button) findViewById(R.id.user_back_button);
        mTopicsButton = (Button) findViewById(R.id.user_topics_button);
        mCommentsButton = (Button) findViewById(R.id.user_comments_button);
        mAddCommentButton = (Button) findViewById(R.id.user_add_comment_button);

        mUserText.setText(mUsername);
        mTopicsButton.setText("Topics by " + mUsername);
        mCommentsButton.setText("Comments by " + mUsername);

        NetworkController networkController = NetworkController.getInstance(this);

        listComments(networkController);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick -> from UserActivity");
                Intent intent = new Intent(UserActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mTopicsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, BoardActivity.class);
                intent.putExtra("userid", mUserId);
                intent.putExtra("username", mUsername);
                startActivity(intent);
            }
        });

        mCommentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, TopicActivity.class);
                intent.putExtra("userid", mUserId);
                intent.putExtra("username", mUsername);
                startActivity(intent);
            }
        });

        mAddCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComment(networkController);
                new android.os.Handler(Looper.getMainLooper()).postDelayed(
                        new Runnable() {
                            public void run() {
                                startActivity(getIntent());
                            }
                        },
                        100);
            }
        });
    }

    private void addComment(NetworkController networkController) {
        networkController.getUser(1, new NetworkCallback<User>() {
            @Override
            public void onSuccess(User result) {
                mComment = mCommentForm.getText().toString();
                Comment comment = new Comment(mUserId, result, result,mComment,"mars","april");
                networkController.addCommentToProfile(mUserId, comment, new NetworkCallback<Comment>() {
                    @Override
                    public void onSuccess(Comment result) {
                    }

                    @Override
                    public void onFailure(String errorString) {
                    }
                });
            }

            @Override
            public void onFailure(String errorString) {
                Log.d(TAG, errorString.toString());
            }
        });
    }

    private void listComments(NetworkController networkController) {
        networkController.getCommentsByUserprofileId(mUserId, new NetworkCallback<List<Comment>>() {
            @Override
            public void onSuccess(List<Comment> result) {
                Log.d(TAG, "Comments for userprofile :" + mUserId);
                mCommentList = result;
                int size = mCommentList.size();
                mComments = new Comment[size];

                for (int i = 0; i < size; i++) {
                    mComments[i] = mCommentList.get(i);
                    Log.d(TAG, "Comment: " + mComments[i].getCommentText());
                }
                mProfileAdapter = new ProfileAdapter(UserActivity.this, mComments);
                mSignatureView.setAdapter(mProfileAdapter);
                mSignatureView.setLayoutManager(new LinearLayoutManager(UserActivity.this));
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get search results: " + errorString);
            }
        });
    }
}