package is.hi.hbv601g.kosmosinn_mobile.Controllers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import is.hi.hbv601g.kosmosinn_mobile.Adapters.BoardAdapter;
import is.hi.hbv601g.kosmosinn_mobile.Entities.Board;
import is.hi.hbv601g.kosmosinn_mobile.MainActivity;

public class NetworkController {

    private static final String BASE_URL = "http://10.0.2.2:8080";
    private static NetworkController mInstance;
    private static RequestQueue mQueue;
    private Context mContext;

    public static synchronized  NetworkController getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new NetworkController(context);
        }
        return mInstance;
    }

    private NetworkController(Context context) {
        mContext = context;
        mQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        if (mQueue == null) {
            mQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mQueue;
    }

    public void getAllBoards() {
        StringRequest request = new StringRequest(
                Method.GET, BASE_URL + "/api/boards", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<Board>>(){}.getType();
                    List<Board> boards = gson.fromJson(response, listType);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );

/*
        JsonArrayRequest request = new JsonArrayRequest(
            Request.Method.GET, BASE_URL, null, new Response.Listener<JSONArray>() {
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
            */
    }
}
