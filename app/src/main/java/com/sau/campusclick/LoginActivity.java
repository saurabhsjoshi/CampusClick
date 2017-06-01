package com.sau.campusclick;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sau.campusclick.model.User;
import com.sau.campusclick.rest.ApiClient;
import com.sau.campusclick.rest.ApiInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.txt_username) TextView txtUsername;

    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public void loginUser(View v) {
        Call<User> call = apiInterface.loginUser(txtUsername.getText().toString());
        doCall(call);
    }

    public void signUpUser(View v) {
        Call<User> call = apiInterface.signUpUser(txtUsername.getText().toString());
        doCall(call);
    }

    public void doCall(Call<User> call) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setIndeterminate(true);
        dialog.setTitle("Please wait...");
        dialog.show();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                SharedPreferences sharedPref = LoginActivity.this.getSharedPreferences(
                        getString(R.string.user_pref_file), Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("id", Integer.parseInt(response.body().getId()));
                editor.putString("username", response.body().getUsername());
                editor.putBoolean("isSignedIn", true);
                editor.apply();
                dialog.dismiss();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error signing in", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }
}
