package is.hi.hbv601g.kosmosinn_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ComponentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

import static is.hi.hbv601g.kosmosinn_mobile.R.id.constraintLayout2;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String BOARD_ID = "id_of_board";
    private RecyclerView boardView;
    private RecyclerView boardV;
    private Button mTest;
    private Button mRestTest;
    private TextView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boardView = (RecyclerView) findViewById(R.id.board_view);
        RequestQueue queue = Volley.newRequestQueue(this);
        mTest = (Button) findViewById(R.id.testButton);
        mList = (TextView) findViewById(R.id.board_title);
        mRestTest = (Button) findViewById(R.id.board_button);

        // herna tharftu ad setja lan ip toluna thina
        // skitalausn sem vid notum i bili, thangad til annad kemur i ljos
        // muna ad keyra serverinn
        String url = "http://192.168.0.103:8080/api/boards";

        mTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick");
                Intent intent = new Intent(MainActivity.this, TestActivity.class);
                intent.putExtra(BOARD_ID, 1);
                startActivity(intent);
            }
        });

        Log.d(TAG, "hinn button >>> " + mTest);
        Log.d(TAG, "hinn button ID >> " + R.id.testButton);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            private String[] boards;
            private String[] descriptions;
            private int[] boardIds;
            private Button[] buttons;
            private BoardAdapter boardAdapter;

            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, "erum inni onresponse");
                try {
                    boards = new String[response.length()];
                    descriptions = new String[response.length()];
                    boardIds = new int[response.length()];
                    buttons = new Button[response.length()];
                    JSONObject boardInfo;
                    for (int i = 0; i < response.length(); i++) {
                        boardInfo = response.getJSONObject(i);
                        boards[i] = boardInfo.getString("name");
                        descriptions[i] = boardInfo.getString("description");
                        Log.d(TAG,boardInfo.getString("id"));
                        boardIds[i] = Integer.parseInt(boardInfo.getString("id"));
                        Button button = (Button) findViewById(R.id.board_button);
                        Log.d(TAG, "ID FYRIR BUTTON> " + R.id.board_button);
                        buttons[i] = button;
                        Log.d(TAG, "button >>>" + buttons[i]);
                        /*buttons[i].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Log.d(TAG, "ID AF ÞESSU BOARDI ER >>> " + boardIds[1]);
                            }
                        });*/
                        //boardIds[i] = boardInfo.getString("id");
                    }
                } catch (JSONException e) {
                    Log.d("OSKAR", response.toString());
                    e.printStackTrace();
                }
                boardAdapter = new BoardAdapter(MainActivity.this, boards, descriptions, boardIds, buttons);
                boardView.setAdapter(boardAdapter);
                boardView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                for (int i = 0; i < 4; i++) {
                    Log.d(TAG, ">>>" + buttons[i]);
                    Log.d(TAG, "<<<" + boards[i]);
                }

                /*boardView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "KLIKK KLIKK", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "er eh ves");
                    }
                });*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("OSKAR", error.toString());
                Toast.makeText(MainActivity.this, "villa ad hlada inn gognum", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);

        Button inbutton = (Button) findViewById(R.id.board_button);
        Log.d(TAG, "buttonn inní oncreate >>> " + inbutton);

        /*boardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "KLIKK KLIKK", Toast.LENGTH_SHORT).show();
            }
        });*/
    }
}