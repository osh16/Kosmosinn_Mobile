package is.hi.hbv601g.kosmosinn_mobile;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import is.hi.hbv601g.kosmosinn_mobile.R;

public class BoardActivity extends AppCompatActivity {
    private static final String TAG = "BoardActivity";
    private TextView textView;
    private TextView textV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        //textView = (TextView) findViewById(R.id.textView);
    }

    RequestQueue queue = Volley.newRequestQueue(this);
    String url = "http://192.168.0.103:8080/api/topics";

}