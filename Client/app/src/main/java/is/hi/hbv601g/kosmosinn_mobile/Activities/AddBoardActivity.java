package is.hi.hbv601g.kosmosinn_mobile.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import is.hi.hbv601g.kosmosinn_mobile.Controllers.NetworkCallback;
import is.hi.hbv601g.kosmosinn_mobile.Controllers.NetworkController;
import is.hi.hbv601g.kosmosinn_mobile.Entities.Board;
import is.hi.hbv601g.kosmosinn_mobile.Entities.User;
import is.hi.hbv601g.kosmosinn_mobile.R;

public class AddBoardActivity extends AppCompatActivity {

    private static final String TAG = "AddBoardActivity";

    private Button mBackButton;
    private Button mSubmitButton;
    private EditText mBoardName;
    private EditText mBoardDescription;

    private String mToken;
    private String mUsername;
    private String mUserId;

    private User mUser;
    private Board mBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_board);

        mBackButton = (Button) findViewById(R.id.add_board_back_button);
        mSubmitButton = (Button) findViewById(R.id.add_board_submit_button);

        NetworkController networkController = NetworkController.getInstance(this);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick -> Til baka");
                Intent intent = new Intent(AddBoardActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBoardName = (EditText) findViewById(R.id.board_name_field);
                mBoardDescription = (EditText) findViewById(R.id.board_description_field);

                String name = mBoardName.getText().toString();
                String description = mBoardDescription.getText().toString();
                mBoard = new Board(name, description);

                SharedPreferences sharedPreferences = getSharedPreferences(
                        "KosmosinnSharedPref",
                        MODE_PRIVATE);


                String token = sharedPreferences.getString("Authorization", "");

                networkController.addBoard(mBoard, token, new NetworkCallback<Board>() {
                    @Override
                    public void onSuccess(Board result) {
                        Log.d(TAG, "Board " + mBoard.getName() + " added");
                    }

                    @Override
                    public void onFailure(String errorString) {
                        Log.d(TAG, "Error adding board");
                    }
                });

                Intent intent = new Intent(AddBoardActivity.this, MainActivity.class);

                new android.os.Handler(Looper.getMainLooper()).postDelayed(
                        new Runnable() {
                            public void run() {
                                startActivity(intent);
                            }
                        },
                        300);
            }
        });
    }
}