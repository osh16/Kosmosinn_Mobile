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
import android.widget.TextView;

import is.hi.hbv601g.kosmosinn_mobile.Controllers.NetworkCallback;
import is.hi.hbv601g.kosmosinn_mobile.Controllers.NetworkController;
import is.hi.hbv601g.kosmosinn_mobile.Entities.Board;
import is.hi.hbv601g.kosmosinn_mobile.Entities.Topic;
import is.hi.hbv601g.kosmosinn_mobile.Entities.User;
import is.hi.hbv601g.kosmosinn_mobile.R;

public class AddTopicActivity extends AppCompatActivity {

    private static final String TAG = "AddTopicActivity";

    private Button mBackButton;
    private Button mSubmitButton;
    private EditText mTopicName;
    private EditText mTopicContent;

    private String mToken;
    private int mUserId;
    private String mUsername;

    private User mUser;
    private Topic mTopic;
    private int mBoardId;
    private int mTopicId;
    private boolean mEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_topic);

        mBackButton = (Button) findViewById(R.id.add_topic_back_button);
        mSubmitButton = (Button) findViewById(R.id.add_topic_submit_button);
        mTopicName = (EditText) findViewById(R.id.topic_name_field);
        mTopicContent = (EditText) findViewById(R.id.topic_content_field);

        mBoardId = getIntent().getIntExtra("boardid", 0);
        mTopicId = getIntent().getIntExtra("topicid", 0);
        mEdit = getIntent().getBooleanExtra("edittopic", false);

        SharedPreferences sharedPreferences = getSharedPreferences(
                "KosmosinnSharedPref",
                MODE_PRIVATE);

        mToken = sharedPreferences.getString("Authorization", "");
        mUserId = sharedPreferences.getInt("userId", 0);
        mUsername = sharedPreferences.getString("username", "");

        Log.d(TAG, "Board id: " + mBoardId);
        Log.d(TAG, "Topic id for edit: " + mTopicId);
        Log.d(TAG, "Edit Topic? " + mEdit);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick -> Til baka");
                Intent intent = new Intent(AddTopicActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        NetworkController networkController = NetworkController.getInstance(this);

        networkController.getTopic(mTopicId, new NetworkCallback<Topic>() {
            @Override
            public void onSuccess(Topic result) {
                mTopic = result;
            }

            @Override
            public void onFailure(String errorString) {
                Log.d(TAG, errorString);
            }
        });

        if (mEdit) {
            editThisTopic(networkController);
        } else {
            addThisTopic(networkController);
        }
    }

    private void addThisTopic(NetworkController networkController) {

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                networkController.getUser(mUserId, mToken, new NetworkCallback<User>() {
                    @Override
                    public void onSuccess(User result) {
                        mUser = result;
                        String name = mTopicName.getText().toString();
                        String content = mTopicContent.getText().toString();
                        mTopic = new Topic(mBoardId, mUser, name, content, 0, 0, "mars", "april");
                        networkController.addTopic(mBoardId, mToken, mTopic, new NetworkCallback<Topic>() {
                            @Override
                            public void onSuccess(Topic result) {
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

                Intent intent = new Intent(AddTopicActivity.this, BoardActivity.class);
                intent.putExtra("boardid", mBoardId);
                new android.os.Handler(Looper.getMainLooper()).postDelayed(
                        new Runnable() {
                            public void run() {
                                startActivity(intent);
                            }
                        },
                        100);
            }
        });
    }

    private void editThisTopic(NetworkController networkController) {
        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                new Runnable() {
                    public void run() {
                        mTopicName.setHint(mTopic.getTopicName());
                        mTopicContent.setHint(mTopic.getTopicContent());
                        Log.d(TAG, "Topic>>>>>>> " + mTopic);
                    }
                },
                100);

        mSubmitButton.setText("Edit Topic");
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                networkController.getUser(mUserId, mToken, new NetworkCallback<User>() {
                    @Override
                    public void onSuccess(User result) {
                        mUser = result;
                        String name = mTopicName.getText().toString();
                        String content = mTopicContent.getText().toString();
                        mTopic.setTopicName(name);
                        mTopic.setTopicContent(content);
                        networkController.editTopic(mTopicId, mToken, mTopic, new NetworkCallback<Topic>() {
                            @Override
                            public void onSuccess(Topic result) {
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
                Intent intent = new Intent(AddTopicActivity.this, BoardActivity.class);
                intent.putExtra("boardid", mBoardId);
                //startActivity(intent);
                new android.os.Handler(Looper.getMainLooper()).postDelayed(
                        new Runnable() {
                            public void run() {
                                startActivity(intent);
                            }
                        },
                        100);
            }
        });
    }
}