package is.hi.hbv601g.kosmosinn_mobile.Controllers;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.net.URI;
import java.util.List;

import is.hi.hbv601g.kosmosinn_mobile.Adapters.BoardAdapter;
import is.hi.hbv601g.kosmosinn_mobile.Entities.Board;
import is.hi.hbv601g.kosmosinn_mobile.MainActivity;
import is.hi.hbv601g.kosmosinn_mobile.R;

public class NetworkController {

    private static final String BASE_URL = "http://10.0.2.2:8080";
    private static NetworkController mInstance;
    private static RequestQueue mQueue;
    private Context mContext;

    private RecyclerView boardView;                             // birta
    //boardView = (RecyclerView) findViewById(R.id.board_view);   // birta

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

    public void getAllBoards(NetworkCallback<List<Board>> callback) {
       // private BoardAdapter boardAdapter;  //birta

        StringRequest request = new StringRequest(
                Method.GET, BASE_URL + "/api/boards", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<Board>>(){}.getType();
                    List<Board> boards = gson.fromJson(response, listType);
                    callback.onSuccess(boards);
                //boardAdapter = new BoardAdapter(MainActivity.this, boards, descriptions); // birta
                //boardView.setAdapter(boardAdapter);                                               // birta
                //boardView.setLayoutManager(new LinearLayoutManager(MainActivity.this));   // birta
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error.toString());
            }
        }
        );
        mQueue.add(request);
    }
    public void getBoard(int id, final NetworkCallback<Board> callback) {
        String url = Uri.parse(BASE_URL)
                .buildUpon()
                .appendPath("/api/boards")
                .appendPath(String.valueOf(id))
                .build().toString();

        StringRequest request = new StringRequest(
                Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Board board = gson.fromJson(response, Board.class);
                callback.onSuccess(board);
           }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error.toString());
            }
        }
        );
        mQueue.add(request);
    }
}
