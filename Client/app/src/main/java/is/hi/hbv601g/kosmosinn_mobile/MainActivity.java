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

import is.hi.hbv601g.kosmosinn_mobile.Adapters.BoardAdapter;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView boardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boardView = (RecyclerView) findViewById(R.id.board_view);
        RequestQueue queue = Volley.newRequestQueue(this);

        // herna tharftu ad setja lan ip toluna thina
        // skitalausn sem vid notum i bili, thangad til annad kemur i ljos
        // muna ad keyra serverinn
<<<<<<< HEAD
        String url = "http://192.168.0.103:8080/api/boards";
=======
        String url = "http://192.168.1.10:8080/api/boards";
>>>>>>> f718366d1a8e6b879c94528dcd506448936e8135

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
    }

}