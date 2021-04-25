package is.hi.hbv601g.kosmosinn_mobile.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import is.hi.hbv601g.kosmosinn_mobile.Controllers.NetworkCallback;
import is.hi.hbv601g.kosmosinn_mobile.Controllers.NetworkController;
import is.hi.hbv601g.kosmosinn_mobile.Entities.Board;
import is.hi.hbv601g.kosmosinn_mobile.Entities.Comment;
import is.hi.hbv601g.kosmosinn_mobile.Entities.Topic;
import is.hi.hbv601g.kosmosinn_mobile.Entities.User;
import is.hi.hbv601g.kosmosinn_mobile.R;

public class AddCommentActivity extends AppCompatActivity {

    private static final String TAG = "AddCommentActivity";

    private Button mSubmitButton;
    private EditText mCommentText;

    private String mToken;
    private int mUserId;
    private String mUsername;

    private User mUser;
    private int mTopicId;
    private int mBoardId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);

        mSubmitButton = (Button) findViewById(R.id.add_comment_submit_button);
        mCommentText = (EditText) findViewById(R.id.comment_text_field);

        mBoardId = getIntent().getIntExtra("boardId", 0);
        mTopicId = getIntent().getIntExtra("topicid",0);

        Log.d(TAG, "Topic id: " + mTopicId);

        NetworkController networkController = NetworkController.getInstance(this);

        SharedPreferences sharedPreferences = getSharedPreferences(
                "KosmosinnSharedPref",
                MODE_PRIVATE);

        mToken = sharedPreferences.getString("Authorization", "");
        mUserId = sharedPreferences.getInt("userId", 0);
        mUsername = sharedPreferences.getString("username", "");

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mCommentText.getText().toString();
                if (text.trim().equals("")) {
                    return;
                }
                networkController.getUser(mUserId, mToken, new NetworkCallback<User>() {
                    @Override
                    public void onSuccess(User result) {
                        mUser = result;
                        Comment comment = new Comment(mTopicId, mUser, text, "mars", "april");
                        networkController.addComment(mTopicId, mToken, comment, new NetworkCallback<Comment>() {
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

                Intent intent = new Intent(AddCommentActivity.this, TopicActivity.class);
                intent.putExtra("boardid", mBoardId);
                intent.putExtra("topicid", mTopicId);

                new android.os.Handler(Looper.getMainLooper()).postDelayed(
                        new Runnable() {
                            public void run() {
                                startActivity(intent);
                            }
                        },
                        300);
            }
        });
    }
}