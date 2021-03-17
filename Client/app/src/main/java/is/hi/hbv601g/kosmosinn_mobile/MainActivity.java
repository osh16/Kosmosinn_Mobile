package is.hi.hbv601g.kosmosinn_mobile;

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

import is.hi.hbv601g.kosmosinn_mobile.Adapters.BoardAdapter;

public class MainActivity extends AppCompatActivity {
    private RecyclerView boardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boardView = (RecyclerView) findViewById(R.id.board_view);

        /* Basic virkni, recycler view fyrir static strengjafylki */
        //String boards[] = {"board 1","board2","hehe","hvad er malid"};
        //String descriptions[] = {"board 1","board2","hehe","hvad er malid"};

        //BoardAdapter boardAdapter =  new BoardAdapter(MainActivity.this,boards,descriptions);
        //boardView.setAdapter(boardAdapter);
        //boardView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        // Instantiate the RequestQueue.
        //RequestQueue queue = Volley.newRequestQueue(this);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url ="http://localhost:8080/api/boards";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            private String[] boards;
            private String[] descriptions;
            private int boardCount = 0;
            BoardAdapter boardAdapter;
            @Override
            public void onResponse(JSONArray response) {
                Log.d("OSKAR", "erum inni onresponse");
                try {
                    JSONObject boardInfo = response.getJSONObject(0);
                    boards[boardCount] = boardInfo.getString("name");
                    descriptions[boardCount] = boardInfo.getString("description");
                    boardCount++;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                boardAdapter = new BoardAdapter(MainActivity.this, boards, descriptions);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("OSKAR", "cringe error");
                Toast.makeText(MainActivity.this, "villa ad hlada inn gognum", Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(request);

    }
}