package is.hi.hbv601g.kosmosinn_mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import is.hi.hbv601g.kosmosinn_mobile.Adapters.BoardAdapter;
import is.hi.hbv601g.kosmosinn_mobile.Adapters.TestAdapter;
import is.hi.hbv601g.kosmosinn_mobile.Controllers.NetworkController;

public class TestActivity extends AppCompatActivity {

    private static final String TAG = "TestActivity";
    private String URL = "http://192.168.0.103:8080/api/boards/";
    private int boardId;
    private RecyclerView boardView;
    private TextView mTextView;
    NetworkController networkController = new NetworkController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        boardView = (RecyclerView) findViewById(R.id.topic_view);
        boardId = getIntent().getIntExtra("id_of_board",1);
        Log.d(TAG, "onCreate");
        Log.d(TAG, URL);
        Log.d(TAG, "" + boardId);
        URL = URL + 2 + "/topics";
        Log.d(TAG, URL);

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            private String[] topics;
            private TestAdapter testAdapter;

            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG,"response length: " + response.length());
                try {
                    Log.d(TAG,"erum inní try");
                    topics = new String[response.length()];
                    JSONObject testInfo;
                    for (int i = 0; i < response.length(); i++) {
                        testInfo = response.getJSONObject(i);
                        topics[i] = testInfo.getString("topicName");
                        Log.d(TAG,topics[i]);
                    }
                } catch (JSONException e) {
                    Log.d(TAG, response.toString());
                    e.printStackTrace();
                }
                testAdapter = new TestAdapter(TestActivity.this, topics);
                boardView.setAdapter(testAdapter);
                boardView.setLayoutManager(new LinearLayoutManager(TestActivity.this));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.toString());
                Toast.makeText(TestActivity.this, "villa ad hlada inn gognum", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }
}