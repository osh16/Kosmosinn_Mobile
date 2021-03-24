package is.hi.hbv601g.kosmosinn_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ComponentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import is.hi.hbv601g.kosmosinn_mobile.Adapters.BoardAdapter;
import is.hi.hbv601g.kosmosinn_mobile.Controllers.NetworkCallback;
import is.hi.hbv601g.kosmosinn_mobile.Controllers.NetworkController;
import is.hi.hbv601g.kosmosinn_mobile.Entities.Board;

public class MainActivity extends AppCompatActivity {
    private RecyclerView boardView;
    private List<Board> mBoards;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // herna tharftu ad setja lan ip toluna thina
        // skitalausn sem vid notum i bili, thangad til annad kemur i ljos
        // muna ad keyra serverinn
        //String url = "http://10.5.0.2:8080/api/boards";
        //String url = "http://192.168.1.205:8080/api/boards";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boardView = (RecyclerView) findViewById(R.id.board_view);
        RequestQueue queue = Volley.newRequestQueue(this);

        NetworkController networkController = NetworkController.getInstance(this);
        networkController.getAllBoards(new NetworkCallback<List<Board>>() {
            @Override
            public void onSuccess(List<Board> result) {
                mBoards = result;
                Log.d(TAG, "First board: " + mBoards.get(1).getDescription());
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get board: " + errorString);
            }
        });

        networkController.getBoard(2, new NetworkCallback<Board>() {
            @Override
            public void onSuccess(Board result) {
                Log.d(TAG, "Board text for id :" + String.valueOf(2) + " is " + result.getName());
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get board: " + errorString);
            }
        });


    /*
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            private String[] boards;
            private String[] descriptions;
            private BoardAdapter boardAdapter;

            @Override
            public void onResponse(JSONArray response) {
                Log.d("OSKAR", "erum inni onresponse");
                try {
                    boards = new String[response.length()];
                    descriptions = new String[response.length()];
                    JSONObject boardInfo;
                    for (int i = 0; i < response.length(); i++) {
                        boardInfo = response.getJSONObject(i);
                        boards[i] = boardInfo.getString("name");
                        descriptions[i] = boardInfo.getString("description");
                    }
                } catch (JSONException e) {
                    Log.d("OSKAR", response.toString());
                    e.printStackTrace();
                }
                boardAdapter = new BoardAdapter(MainActivity.this, boards, descriptions);
                boardView.setAdapter(boardAdapter);
                boardView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("OSKAR", error.toString());
                Toast.makeText(MainActivity.this, "villa ad hlada inn gognum", Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(request);
     */
    }

}