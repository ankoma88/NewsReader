package com.test.genesis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ankoma88.newsreader.R;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ankoma88 on 18.06.16.
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private CallbackManager callbackManager;

    @Bind(R.id.name_tv)
    TextView tvName;

    @Bind(R.id.avatar)
    ProfilePictureView avatar;

    @Bind(R.id.login_button)
    LoginButton btnLogin;

    @Bind(R.id.read_button)
    Button btnRead;

    private AccessTokenTracker tokenTracker;
    private ProfileTracker profileTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);

        btnRead.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NewsActivity.class);
            startActivity(intent);
        });

        setupLogin();
    }

    private void setupLogin() {
        callbackManager = CallbackManager.Factory.create();
        btnLogin.registerCallback(callbackManager, facebookCallback);
        tokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {
                updateUi();
            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                updateUi();
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        FacebookSdk.sdkInitialize(getApplicationContext());
        tokenTracker.startTracking();
        profileTracker.startTracking();

        updateUi();
    }

    private void updateUi() {
        Profile currentProfile = Profile.getCurrentProfile();
        boolean isLoggedIn = AccessToken.getCurrentAccessToken() != null && currentProfile != null;

        tvName.setText(isLoggedIn
                ? (String.format(getString(R.string.welcome_username), currentProfile.getName()))
                : getString(R.string.prompt_to_login));
        btnRead.setVisibility(isLoggedIn ? View.VISIBLE : View.GONE);
        avatar.setProfileId(isLoggedIn ? currentProfile.getId() : null);
    }

    @Override
    protected void onStop() {
        super.onStop();
        tokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private FacebookCallback<LoginResult> facebookCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            Log.d(TAG, "Login successful");
            updateUi();
        }

        @Override
        public void onCancel() {
            Log.d(TAG, "Login cancelled");
        }

        @Override
        public void onError(FacebookException error) {
            Log.e(TAG, error.getLocalizedMessage());
        }
    };


}
