package is.hi.hbv601g.kosmosinn_mobile.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import is.hi.hbv601g.kosmosinn_mobile.Adapters.CommentAdapter;
import is.hi.hbv601g.kosmosinn_mobile.Controllers.NetworkCallback;
import is.hi.hbv601g.kosmosinn_mobile.Controllers.NetworkController;
import is.hi.hbv601g.kosmosinn_mobile.Entities.Comment;
import is.hi.hbv601g.kosmosinn_mobile.Entities.User;
import is.hi.hbv601g.kosmosinn_mobile.R;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private Button mLoginButton;
    private Button mBackButton;
    private EditText mUsernameField;
    private EditText mPasswordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginButton = (Button) findViewById(R.id.login_button);
        mBackButton = (Button) findViewById(R.id.login_back_button);
        mUsernameField = (EditText) findViewById(R.id.username_field_login);
        mPasswordField = (EditText) findViewById(R.id.username_field_login);

        NetworkController networkController = NetworkController.getInstance(this);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick -> Til baka");
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClock -> Login");
                login(networkController);
            }
        });
    }

    public void login(NetworkController networkController) {
        networkController.login(mUsernameField.getText().toString(), mPasswordField.getText().toString(), new NetworkCallback<JSONObject>() {

            @Override
            public void onSuccess(JSONObject result) {
                Log.d(TAG, "Token: " + String.valueOf(result));
                String token = "noToken";
                try {
                    token = result.get("Bearer").toString();
                } catch (JSONException err) {
                    Log.d("Error ", err.toString());
                }
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                SharedPreferences sharedPreferences = getSharedPreferences(
                        "KosmosinnSharedPref",
                        MODE_PRIVATE);

                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putString("Authorization", "Bearer " + token);
                myEdit.commit();

                startActivity(intent);
            }

            @Override
            public void onFailure(String errorString) {

                Log.e(TAG, "Failed to login: " + errorString);
            }
        });

    }
}