package is.hi.hbv601g.kosmosinn_mobile.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.BreakIterator;

import is.hi.hbv601g.kosmosinn_mobile.Controllers.NetworkCallback;
import is.hi.hbv601g.kosmosinn_mobile.Controllers.NetworkController;
import is.hi.hbv601g.kosmosinn_mobile.R;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private Button mSignupButton;
    private Button mBackButton;
    private EditText mUsernameField;
    private EditText mPasswordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mSignupButton = (Button) findViewById(R.id.signup_button);
        mBackButton = (Button) findViewById(R.id.signup_back_button);

        mUsernameField = (EditText) findViewById(R.id.username_field_signup);
        mPasswordField = (EditText) findViewById(R.id.password_field_signup);

        NetworkController networkController = NetworkController.getInstance(this);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick -> Til baka");
                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick -> Sign up");
                signUp(networkController);
                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void signUp(NetworkController networkController) {
        networkController.signUp(mUsernameField.getText().toString(),
                mPasswordField.getText().toString(),
                new NetworkCallback<JSONObject>() {
                    @Override
                    public void onSuccess(JSONObject result) throws JSONException {
                        String token = result.getString("token");
                        JSONObject user = result.getJSONObject("user");

                        int userId = user.getInt("userId");
                        String username = user.getString("username");
                        String userRole = user.getString("userRole");

                        Intent intent = new Intent(SignupActivity.this, MainActivity.class);

                        SharedPreferences sharedPreferences = getSharedPreferences(
                                "KosmosinnSharedPref",
                                MODE_PRIVATE);

                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                        myEdit.putString("Authorization", token);
                        myEdit.putString("username", username);
                        myEdit.putString("userRole", userRole);
                        myEdit.putInt("userId", userId);
                        myEdit.commit();

                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(String errorString) {
                        Toast.makeText(SignupActivity.this, "Username taken", Toast.LENGTH_SHORT);
                        Log.e(TAG, "Failed to sign up: " + errorString);
                    }
                });

    }

}