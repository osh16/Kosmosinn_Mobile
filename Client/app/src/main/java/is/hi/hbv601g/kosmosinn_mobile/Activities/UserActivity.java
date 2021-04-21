package is.hi.hbv601g.kosmosinn_mobile.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import is.hi.hbv601g.kosmosinn_mobile.R;

public class UserActivity extends AppCompatActivity {
    private static final String TAG = "UserActivity";

    private int mUserId;
    private String mUsername;

    private TextView mUserText;
    private Button mBackButton;
    private Button mTopicsButton;
    private Button mCommentsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mUserId = getIntent().getIntExtra("id", 2);
        mUsername = getIntent().getStringExtra("username");
        Log.d(TAG, "User with id: " + mUserId + " and username: " + mUsername);

        mUserText = (TextView) findViewById(R.id.user_name);
        mBackButton = (Button) findViewById(R.id.user_back_button);
        mTopicsButton = (Button) findViewById(R.id.user_topics_button);
        mCommentsButton = (Button) findViewById(R.id.user_comments_button);

        mUserText.setText(mUsername);
        mTopicsButton.setText("Topics by " + mUsername);
        mCommentsButton.setText("Comments by " + mUsername);

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
    }
}