package is.hi.hbv601g.kosmosinn_mobile.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import is.hi.hbv601g.kosmosinn_mobile.Adapters.BoardAdapter;
import is.hi.hbv601g.kosmosinn_mobile.Controllers.NetworkCallback;
import is.hi.hbv601g.kosmosinn_mobile.Controllers.NetworkController;
import is.hi.hbv601g.kosmosinn_mobile.Entities.Board;
import is.hi.hbv601g.kosmosinn_mobile.R;
import is.hi.hbv601g.kosmosinn_mobile.Entities.Topic;
import is.hi.hbv601g.kosmosinn_mobile.Entities.User;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView mBoardView;
    private List<Board> mBoardList;
    private BoardAdapter mBoardAdapter;

    private Board[] mBoards;

    private User mUser;

    private String mToken;
    private String mUserRole;

    private Button mLoginButton;
    private Button mSignupButton;
    private Button mAddBoardButton;
    private Button mSearchButton;
    private Button mLogoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences(
                "KosmosinnSharedPref",
                MODE_PRIVATE);

        mToken = sharedPreferences.getString("Authorization", "");
        mUserRole = sharedPreferences.getString("userRole", "");
        
        mBoardView = (RecyclerView) findViewById(R.id.boards_view);
        mLoginButton = (Button) findViewById(R.id.login_activity_button);
        mLogoutButton = (Button) findViewById(R.id.logout_button);
        mSignupButton = (Button) findViewById(R.id.signup_activity_button);
        mSearchButton = (Button) findViewById(R.id.search_page_button);
        mAddBoardButton = (Button) findViewById(R.id.add_board_button);

        if (!mToken.equals("")) {
            mLoginButton.setVisibility(View.GONE);
            mSignupButton.setVisibility(View.GONE);
            mLogoutButton.setVisibility(View.VISIBLE);
            if (mUserRole.equals("ADMIN")) {
                mAddBoardButton.setVisibility(View.VISIBLE);
            } else {
                mAddBoardButton.setVisibility(View.GONE);
            }
        } else {
            mLoginButton.setVisibility(View.VISIBLE);
            mSignupButton.setVisibility(View.VISIBLE);
            mAddBoardButton.setVisibility(View.GONE);
            mLogoutButton.setVisibility(View.GONE);
        }

        NetworkController networkController = NetworkController.getInstance(this);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick -> Login");
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.remove("userId");
                myEdit.remove("username");
                myEdit.remove("userRole");
                myEdit.remove("Authorization");
                myEdit.commit();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick -> Signup");
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);

                startActivity(intent);
            }
        });

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick -> Search");
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        mAddBoardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick -> Add Board");
                Intent intent = new Intent(MainActivity.this, AddBoardActivity.class);
                startActivity(intent);
            }
        });

        networkController.getAllBoards(new NetworkCallback<List<Board>>() {
            @Override
            public void onSuccess(List<Board> result) {
                mBoardList = result;
                int size = mBoardList.size();
                mBoards = new Board[size];
                for (int i = 0; i < size; i++) {
                    mBoards[i] = mBoardList.get(i);
                    Log.d(TAG, "Board id = " + mBoards[i].getId());
                }
                mBoardAdapter = new BoardAdapter(MainActivity.this, mBoards);
                mBoardView.setAdapter(mBoardAdapter);
                mBoardView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get board: " + errorString);
            }
        });
    }

    public void validateToken(String token, NetworkController networkController) {

    }

    public void selectBoard(int id) {
        Log.d(TAG, "Select Board");
        Intent intent = new Intent(MainActivity.this, BoardActivity.class);
        intent.putExtra("boardid", id);
        startActivity(intent);
    }
}