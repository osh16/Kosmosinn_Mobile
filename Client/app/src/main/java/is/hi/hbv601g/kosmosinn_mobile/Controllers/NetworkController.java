package is.hi.hbv601g.kosmosinn_mobile.Controllers;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import is.hi.hbv601g.kosmosinn_mobile.Adapters.BoardAdapter;
import is.hi.hbv601g.kosmosinn_mobile.Entities.Board;
import is.hi.hbv601g.kosmosinn_mobile.Entities.Comment;
import is.hi.hbv601g.kosmosinn_mobile.Entities.Topic;
import is.hi.hbv601g.kosmosinn_mobile.Entities.User;
import is.hi.hbv601g.kosmosinn_mobile.MainActivity;
import is.hi.hbv601g.kosmosinn_mobile.R;

public class NetworkController {

    private static final String BASE_URL = "http://10.0.2.2:8080";
    private static NetworkController mInstance;
    private static RequestQueue mQueue;
    private Context mContext;
    private static final String TAG = "NetworkController";

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

    public void getAllBoards(final NetworkCallback<List<Board>> callback) {

        StringRequest request = new StringRequest(
                Method.GET, BASE_URL + "/api/boards/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<Board>>(){}.getType();
                    List<Board> boards = gson.fromJson(response, listType);
                    callback.onSuccess(boards);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error.toString());
            }
        }
        );
        request.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mQueue.add(request);
    }
    public void getBoard(int id, final NetworkCallback<Board> callback) {
        String url = Uri.parse(BASE_URL)
                .buildUpon()
                .appendPath("api")
                .appendPath("boards")
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
        request.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mQueue.add(request);
    }
    public void addBoard(Board newBoard, final NetworkCallback<Board> callback) {
        final JSONObject body = new JSONObject();
        try {
            body.put("name", newBoard.getName());
            body.put("description", newBoard.getDescription());
        } catch (JSONException e) {
            Log.d("addBoard", e.getMessage());
        }

        String url = Uri.parse(BASE_URL)
                .buildUpon()
                .appendPath("api")
                .appendPath("boards")
                .appendPath("addBoard")
                .build().toString();

        StringRequest request = new StringRequest(
                Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("addBoard:", response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       Log.d("addBoard:", error.toString());
                    }
                }
        )  {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return body.toString().getBytes();
            }
        };
        mQueue.add(request);
    }
    public void getAllTopics(final NetworkCallback<List<Topic>> callback) {

        StringRequest request = new StringRequest(
                Method.GET, BASE_URL + "/api/topics/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Topic>>(){}.getType();
                List<Topic> topics = gson.fromJson(response, listType);
                callback.onSuccess(topics);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error.toString());
            }
        }
        );
        request.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mQueue.add(request);
    }

    public void getTopicsByBoardId(int id, final NetworkCallback<List<Topic>> callback) {
        StringRequest request = new StringRequest(
                Method.GET, BASE_URL + "/api/boards/" + id + "/topics", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Topic>>(){}.getType();
                List<Topic> topics = gson.fromJson(response, listType);
                callback.onSuccess(topics);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error.toString());
            }
        }
        );
        request.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mQueue.add(request);
    }

    public void getTopic(int id, final NetworkCallback<Topic> callback) {
        String url = Uri.parse(BASE_URL)
                .buildUpon()
                .appendPath("api")
                .appendPath("topics")
                .appendPath(String.valueOf(id))
                .build().toString();

        StringRequest request = new StringRequest(
                Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Topic topic = gson.fromJson(response, Topic.class);
                callback.onSuccess(topic);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error.toString());
            }
        }
        );
        request.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mQueue.add(request);
    }
    public void addTopic(int id, Topic newTopic, final NetworkCallback<Topic> callback) {
        final JSONObject body = new JSONObject();
        try {
            body.put("topicName", newTopic.getTopicName());
            body.put("topicContent", newTopic.getTopicContent());
        } catch (JSONException e) {
            Log.d("addTopic", e.getMessage());
        }

        String url = Uri.parse(BASE_URL)
                .buildUpon()
                .appendPath("api")
                .appendPath("boards")
                .appendPath(String.valueOf(id))
                .appendPath("addTopic")
                .build().toString();

        StringRequest request = new StringRequest(
                Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("addTopic:", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("addTopic:", error.toString());
            }
        }
        )  {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return body.toString().getBytes();
            }
        };
        mQueue.add(request);
    }
    public void getAllComments(final NetworkCallback<List<Comment>> callback) {

        StringRequest request = new StringRequest(
                Method.GET, BASE_URL + "/api/comments/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Topic>>(){}.getType();
                List<Comment> comments = gson.fromJson(response, listType);
                callback.onSuccess(comments);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error.toString());
            }
        }
        );
        request.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mQueue.add(request);
    }

    public void getCommentsByTopicId(int id, final NetworkCallback<List<Comment>> callback) {

        StringRequest request = new StringRequest(
                Method.GET, BASE_URL + "/api/topics/" + id + "/comments", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Topic>>(){}.getType();
                List<Comment> comments = gson.fromJson(response, listType);
                callback.onSuccess(comments);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error.toString());
            }
        }
        );
        request.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mQueue.add(request);
    }

    public void getComment(int id, final NetworkCallback<Comment> callback) {
        String url = Uri.parse(BASE_URL)
                .buildUpon()
                .appendPath("api")
                .appendPath("comments")
                .appendPath(String.valueOf(id))
                .build().toString();

        StringRequest request = new StringRequest(
                Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Comment comment = gson.fromJson(response, Comment.class);
                callback.onSuccess(comment);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error.toString());
            }
        }
        );
        request.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mQueue.add(request);
    }
    public void getAllUsers(final NetworkCallback<List<User>> callback) {

        StringRequest request = new StringRequest(
                Method.GET, BASE_URL + "/api/users/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Topic>>(){}.getType();
                List<User> users = gson.fromJson(response, listType);
                callback.onSuccess(users);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error.toString());
            }
        }
        );
        request.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mQueue.add(request);
    }
    public void getUser(int id, final NetworkCallback<User> callback) {
        String url = Uri.parse(BASE_URL)
                .buildUpon()
                .appendPath("api")
                .appendPath("users")
                .appendPath(String.valueOf(id))
                .build().toString();

        StringRequest request = new StringRequest(
                Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                User user = gson.fromJson(response, User.class);
                callback.onSuccess(user);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error.toString());
            }
        }
        );
        request.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mQueue.add(request);
    }
}
