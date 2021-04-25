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
import android.widget.Toast;

import com.google.gson.JsonObject;

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
        mPasswordField = (EditText) findViewById(R.id.password_field_login);

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
        networkController.login(mUsernameField.getText().toString(),
                mPasswordField.getText().toString(),
                new NetworkCallback<JSONObject>() {

            @Override
            public void onSuccess(JSONObject result) throws JSONException {

                /*JSONObject user = result.getJSONObject("user");
                String token = result.getString("token");

                int userId = user.getInt("userId");
                String username = user.getString("username");
                String role = user.getString("userRole");

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                SharedPreferences sharedPreferences = getSharedPreferences(
                        "KosmosinnSharedPref",
                        MODE_PRIVATE);

                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putString("Authorization", token);
                myEdit.putString("username", username);
                myEdit.putString("userRole", role);
                myEdit.putInt("userId", userId);
                myEdit.commit();

                String s = "Velkominn " + username;
                Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();

                startActivity(intent);*/

                try {
                    JSONObject user = result.getJSONObject("user");
                    String token = result.getString("token");

                    int userId = user.getInt("userId");
                    String username = user.getString("username");
                    String role = user.getString("userRole");

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                    SharedPreferences sharedPreferences = getSharedPreferences(
                            "KosmosinnSharedPref",
                            MODE_PRIVATE);

                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putString("Authorization", token);
                    myEdit.putString("username", username);
                    myEdit.putString("userRole", role);
                    myEdit.putInt("userId", userId);
                    myEdit.commit();

                    String s = "Velkominn " + username;
                    Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();

                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, "Username or password incorrect", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to login: " + errorString);
                Toast.makeText(LoginActivity.this, "Username or password incorrect", Toast.LENGTH_SHORT).show();
            }
        });

    }

}