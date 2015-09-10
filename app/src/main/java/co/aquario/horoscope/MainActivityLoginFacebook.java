package co.aquario.horoscope;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.util.Arrays;


import co.aquario.horoscope.fragment.FragmentSelectDay;

public class MainActivityLoginFacebook extends AppCompatActivity {
    private static final int LOGIN_REQUEST = 0;

    private TextView titleTextView;
    private TextView emailTextView;
    private TextView nameTextView;
    private Button loginOrLogoutButton,parse_login_skip;
    private ParseUser currentUser;
    ImageView app_logo;

    Class<?> activityClass;
    Class[] paramTypes = {Integer.TYPE, Integer.TYPE};

    Method overrideAnimation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login_facebook);
        titleTextView = (TextView) findViewById(R.id.profile_title);
        emailTextView = (TextView) findViewById(R.id.profile_email);
        nameTextView = (TextView) findViewById(R.id.profile_name);
        loginOrLogoutButton = (Button) findViewById(R.id.login_or_logout_button);
        app_logo = (ImageView) findViewById(R.id.app_logo);
//        parse_login_skip = (Button) findViewById(R.id.skip);
//        parse_login_skip.setVisibility(View.VISIBLE);
        titleTextView.setText(R.string.profile_title_logged_in);



        YoYo.with(Techniques.Tada)
                .duration(1500)
                .playOn(findViewById(R.id.app_logo));


        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo("co.aquario.horoscope", PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }


        try {
            activityClass = Class.forName("android.app.Activity");
            overrideAnimation = activityClass.getDeclaredMethod(
                    "overridePendingTransition", paramTypes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (currentUser != null) {
                    // User clicked to log out.
                    ParseUser.logOut();
                    currentUser = null;
                    showProfileLoggedOut();
                } else {
                    // User clicked to log in.
                    ParseLoginBuilder loginBuilder = new ParseLoginBuilder(MainActivityLoginFacebook.this).setParseLoginEnabled(false).setFacebookLoginPermissions(Arrays.asList("public_profile", "user_friends")).setTwitterLoginEnabled(false);
                    startActivityForResult(loginBuilder.build(), LOGIN_REQUEST);
                }
                if (overrideAnimation != null) {
                    try {
                        overrideAnimation.invoke(getApplicationContext(), android.R.anim.fade_in,
                                android.R.anim.fade_out);
                    } catch (IllegalArgumentException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }, 2500);


    }


    @Override
    protected void onStart() {
        super.onStart();

        currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            showProfileLoggedIn();
        } else {
            showProfileLoggedOut();
        }
    }

    /**
     * Shows the profile of the given user.
     */
    private void showProfileLoggedIn() {
        titleTextView.setText(R.string.profile_title_logged_in);
        emailTextView.setText(currentUser.getEmail());
        String fullName = currentUser.getString("name");
        Log.e("555", currentUser.getUsername());
        Log.e("666", currentUser.getSessionToken());
        if (fullName != null) {
            nameTextView.setText(fullName);
        }
        loginOrLogoutButton.setText(R.string.profile_logout_button_label);
        FragmentSelectDay oneFragment = new FragmentSelectDay();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, oneFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * Show a message asking the user to log in, toggle login/logout button text.
     */
    private void showProfileLoggedOut() {
        titleTextView.setText(R.string.profile_title_logged_out);
        emailTextView.setText("");
        nameTextView.setText("");

        // loginOrLogoutButton.setText(R.string.profile_login_button_label);
    }
}
