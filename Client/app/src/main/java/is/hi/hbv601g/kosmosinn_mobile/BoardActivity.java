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

import is.hi.hbv601g.kosmosinn_mobile.Adapters.TopicAdapter;
import is.hi.hbv601g.kosmosinn_mobile.Controllers.NetworkCallback;
import is.hi.hbv601g.kosmosinn_mobile.Controllers.NetworkController;
import is.hi.hbv601g.kosmosinn_mobile.Entities.Topic;

public class BoardActivity extends AppCompatActivity {
    private static final String TAG = "BoardActivity";
    private RecyclerView mTopicView;
    private List<Topic> mTopics;
    private TopicAdapter mTopicAdapter;

    private String[] mTopicNames;
    private int[] mTopicIds;

    private int mBoardId;
    private Button mBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        mTopicView = (RecyclerView) findViewById(R.id.topic_view);
        mBackButton = (Button) findViewById(R.id.board_back_button);
        mBoardId = getIntent().getIntExtra("id",1);

        Log.d(TAG, "Board id = " + mBoardId);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick -> Til baka");
                Intent intent = new Intent(BoardActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        NetworkController networkController = NetworkController.getInstance(this);
        networkController.getTopicsByBoardId(mBoardId, new NetworkCallback<List<Topic>>() {
            @Override
            public void onSuccess(List<Topic> result) {
                Log.d(TAG, "Board text for id :" + String.valueOf(mBoardId));
                mTopics = result;
                Log.d(TAG, "" + result);
                int size = mTopics.size();
                mTopicNames = new String[size];
                mTopicIds = new int[size];
                for (int i = 0; i < size; i++) {
                    mTopicNames[i] = mTopics.get(i).getTopicName();
                    mTopicIds[i] = mTopics.get(i).getId();
                    Log.d(TAG, "Topic Name: " + mTopicNames[i] + ", id = " + mTopicIds[i]);
                }
                mTopicAdapter = new TopicAdapter(BoardActivity.this, mTopicNames, mTopicIds);
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
        intent.putExtra("id", id);
        startActivity(intent);
    }
}