package is.hi.hbv601g.kosmosinn_mobile.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

    private User mUser;
    private int mBoardId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_topic);

        mBackButton = (Button) findViewById(R.id.add_topic_back_button);
        mSubmitButton = (Button) findViewById(R.id.add_topic_submit_button);
        mTopicName = (EditText) findViewById(R.id.topic_name_field);
        mTopicContent = (EditText) findViewById(R.id.topic_content_field);

        mBoardId = getIntent().getIntExtra("boardid",0);
        Log.d(TAG, "Board id: " + mBoardId);

        NetworkController networkController = NetworkController.getInstance(this);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick -> Til baka");
                Intent intent = new Intent(AddTopicActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                networkController.getUser(1, new NetworkCallback<User>() {
                    @Override
                    public void onSuccess(User result) {
                        mUser = result;
                        String name = mTopicName.getText().toString();
                        String content = mTopicContent.getText().toString();
                        Topic topic = new Topic(mBoardId, mUser, name, content, 0, 0, "mars", "april");
                        networkController.addTopic(mBoardId, topic, new NetworkCallback<Topic>() {
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
                startActivity(intent);
            }
        });
    }
}