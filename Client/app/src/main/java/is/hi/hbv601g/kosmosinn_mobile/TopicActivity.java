package is.hi.hbv601g.kosmosinn_mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import is.hi.hbv601g.kosmosinn_mobile.Adapters.CommentAdapter;
import is.hi.hbv601g.kosmosinn_mobile.Adapters.TopicAdapter;
import is.hi.hbv601g.kosmosinn_mobile.Controllers.NetworkCallback;
import is.hi.hbv601g.kosmosinn_mobile.Controllers.NetworkController;
import is.hi.hbv601g.kosmosinn_mobile.Entities.Comment;
import is.hi.hbv601g.kosmosinn_mobile.Entities.Topic;

public class TopicActivity extends AppCompatActivity {
    private static final String TAG = "TopicActivity";
    private RecyclerView mCommentView;
    private List<Comment> mComments;
    private CommentAdapter mCommentAdapter;

    private String[] mCommentText;
    private int[] mCommentIds;

    private int mTopicId;
    private Button mBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        mCommentView = (RecyclerView) findViewById(R.id.comment_view);
        mBackButton = (Button) findViewById(R.id.topic_back_button);
        mTopicId = getIntent().getIntExtra("id",1);

        Log.d(TAG, "Topic id = " + mTopicId);
        Log.d(TAG, "Button: " + mBackButton);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick -> Til baka");
                Intent intent = new Intent(TopicActivity.this, BoardActivity.class);
                startActivity(intent);
            }
        });

        NetworkController networkController = NetworkController.getInstance(this);
        networkController.getCommentsByTopicId(mTopicId, new NetworkCallback<List<Comment>>() {
            @Override
            public void onSuccess(List<Comment> result) {
                Log.d(TAG, "Comments for TopicId :" + String.valueOf(mTopicId));
                mComments = result;
                Log.d(TAG, "" + result);
                int size = mComments.size();
                mCommentText = new String[size];
                mCommentIds = new int[size];
                for (int i = 0; i < size; i++) {
                    mCommentText[i] = mComments.get(i).getCommentText();
                    mCommentIds[i] = mComments.get(i).getId();
                    Log.d(TAG, "Comment: " + mCommentText[i] + ", id = " + mCommentIds[i]);
                }
                mCommentAdapter = new CommentAdapter(TopicActivity.this, mCommentText, mCommentIds);
                mCommentView.setAdapter(mCommentAdapter);
                mCommentView.setLayoutManager(new LinearLayoutManager(TopicActivity.this));
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get single comment: " + errorString);
            }
        });
    }
}