package is.hi.hbv601g.kosmosinn_mobile.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import is.hi.hbv601g.kosmosinn_mobile.Adapters.SearchAdapter;
import is.hi.hbv601g.kosmosinn_mobile.Adapters.TopicAdapter;
import is.hi.hbv601g.kosmosinn_mobile.Controllers.NetworkCallback;
import is.hi.hbv601g.kosmosinn_mobile.Controllers.NetworkController;
import is.hi.hbv601g.kosmosinn_mobile.Entities.Topic;
import is.hi.hbv601g.kosmosinn_mobile.R;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";
    private RecyclerView mSearchView;
    private SearchAdapter mSearchAdapter;

    private Button mBackButton;
    private Button mSearchButton;
    private EditText mSearchString;

    private List<Topic> mTopicList;
    private Topic[] mTopics;
    private String mSearchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        mSearchView = (RecyclerView) findViewById(R.id.search_view);
        mBackButton = (Button) findViewById(R.id.search_back_button);
        mSearchString = (EditText) findViewById(R.id.search_field);
        mSearchButton = (Button) findViewById(R.id.search_topic_submit_button);

        NetworkController networkController = NetworkController.getInstance(this);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchQuery = mSearchString.getText().toString();
                getTopicsBySearch(networkController);
            }
        });
    }

    public void selectTopicFromSearch(int id) {
        Log.d(TAG, "Select Topic");
        Intent intent = new Intent(SearchActivity.this, TopicActivity.class);
        intent.putExtra("topicid", id);
        intent.putExtra("fromsearch", true);
        startActivity(intent);
    }

    public void getTopicsBySearch(NetworkController networkController) {
        networkController.getTopicsBySearch(mSearchQuery, new NetworkCallback<List<Topic>>() {
            @Override
            public void onSuccess(List<Topic> result) {
                Log.d(TAG, "Topics by search :" + mSearchQuery);
                mTopicList = result;
                int size = mTopicList.size();
                mTopics = new Topic[size];

                for (int i = 0; i < size; i++) {
                    mTopics[i] = mTopicList.get(i);
                    Log.d(TAG, "Topic Name: " + mTopics[i].getTopicName());
                }
                mSearchAdapter = new SearchAdapter(SearchActivity.this, mTopics);
                mSearchView.setAdapter(mSearchAdapter);
                mSearchView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get search results: " + errorString);
            }
        });
    }
}