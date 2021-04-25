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
    private int mCurrentUserId;

    private User mUser;
    private Board mBoard;
    private int mBoardId;
    private boolean mEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_board);

        mBackButton = (Button) findViewById(R.id.add_board_back_button);
        mSubmitButton = (Button) findViewById(R.id.add_board_submit_button);
        mBoardName = (EditText) findViewById(R.id.board_name_field);
        mBoardDescription = (EditText) findViewById(R.id.board_description_field);

        mBoardId = getIntent().getIntExtra("boardid", 0);
        mEdit = getIntent().getBooleanExtra("editboard", false);

        SharedPreferences sharedPreferences = getSharedPreferences(
                "KosmosinnSharedPref",
                MODE_PRIVATE);

        mToken = sharedPreferences.getString("Authorization", "");
        mCurrentUserId = sharedPreferences.getInt("userId", 0);

        NetworkController networkController = NetworkController.getInstance(this);

        if (mEdit) {
            getBoard(networkController);
            editThisBoard(networkController);
        } else {
            addThisBoard(networkController);
        }

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick -> Til baka");
                Intent intent = new Intent(AddBoardActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        if (mEdit) {
            getBoard(networkController);
            editThisBoard(networkController);
        } else {
            addThisBoard(networkController);
        }

    }

    public void getBoard(NetworkController networkController) {
        networkController.getBoard(mBoardId, new NetworkCallback<Board>() {
            @Override
            public void onSuccess(Board result) {
                mBoard = result;
            }

            @Override
            public void onFailure(String errorString) {
                Log.d(TAG, errorString);
            }
        });
    }

    private void addThisBoard(NetworkController networkController) {

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    private void editThisBoard(NetworkController networkController) {
        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                new Runnable() {
                    public void run() {
                        mBoardName.setHint(mBoard.getName());
                        mBoardDescription.setHint(mBoard.getDescription());
                    }
                },
                300);

        mSubmitButton.setText("Edit Board");
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                networkController.getUser(mCurrentUserId, mToken, new NetworkCallback<User>() {
                    @Override
                    public void onSuccess(User result) {
                        mUser = result;
                        String name = mBoardName.getText().toString();
                        String description = mBoardDescription.getText().toString();
                        mBoard.setName(name);
                        mBoard.setDescription(description);

                        networkController.editBoard(mBoardId, mToken, mBoard, new NetworkCallback<Board>() {
                            @Override
                            public void onSuccess(Board result) {
                                Log.d(TAG, "Board edited");
                            }

                            @Override
                            public void onFailure(String errorString) {
                                Log.d(TAG, errorString);
                            }
                        });
                    }

                    @Override
                    public void onFailure(String errorString) {
                        Log.d(TAG, errorString);
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