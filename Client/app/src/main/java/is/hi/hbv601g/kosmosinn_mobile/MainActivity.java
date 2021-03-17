package is.hi.hbv601g.kosmosinn_mobile;

import androidx.appcompat.app.AppCompatActivity;
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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import is.hi.hbv601g.kosmosinn_mobile.Adapters.BoardAdapter;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView boardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boardView = (RecyclerView) findViewById(R.id.board_view);

        String boards[] = {"board 1","board2","hehe","hvad er malid"};
        String descriptions[] = {"board 1","board2","hehe","hvad er malid"};

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://localhost:8080/api/boards/";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
            }
        }), new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        };


        BoardAdapter cringeAdapter =  new BoardAdapter(this,boards,descriptions);
        boardView.setAdapter(cringeAdapter);
        boardView.setLayoutManager(new LinearLayoutManager(this));

        // fetchData FetchData = new fetchData();

        // FetchData.doInBackground();

        Log.d(TAG,"hallo");

    }

}