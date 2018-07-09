package com.example.mathurinbloworlf.moviestreamapp.fragment.login_register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/*
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
*/
import com.example.mathurinbloworlf.moviestreamapp.R;
import com.example.mathurinbloworlf.moviestreamapp.constants.MovieDB;
import com.example.mathurinbloworlf.moviestreamapp.manager.SharedPrefManager;
import com.example.mathurinbloworlf.moviestreamapp.manager.StringRequestHandler;

import cz.msebera.android.httpclient.Header;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Login extends Fragment{

    private TextInputLayout textInputLayout_username, textInputLayout_password;
    private EditText input_username, input_password;

    private Button login, guest;
    private TextView no_account_yet;

    public Login() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textInputLayout_username = (TextInputLayout) view.findViewById(R.id.inputlayout_username);
        textInputLayout_password = (TextInputLayout) view.findViewById(R.id.inputlayout_password);
        input_username = (EditText) view.findViewById(R.id.input_username);
        input_password = (EditText) view.findViewById(R.id.input_password);

        guest = view.findViewById(R.id.button_guest);
        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpClient client = new OkHttpClient();

                MediaType mediaType = MediaType.parse("application/octet-stream");
                RequestBody body = RequestBody.create(mediaType, "{}");
                Request request = new Request.Builder()
                        .url("https://api.themoviedb.org/3/authentication/guest_session/new?api_key="+ MovieDB.key)
                        .get()
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    //Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        login = (Button) view.findViewById(R.id.button_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
                getActivity().finish();
                //startActivity(new Intent(getContext(), Profile.class));
            }
        });
        no_account_yet = (TextView) view.findViewById(R.id.no_account_yet);
        no_account_yet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Créez donc un compte", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void userLogin()
    {
        /*

        final String username = input_username.getText().toString().trim();
        final String password = input_password.getText().toString().trim();

        //progressDialog.show();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                "http://10.0.2.2/Android/W7App/UserLogin.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(!jsonObject.getBoolean("error"))
                            {
                                SharedPrefManager.getInstance(getContext()).userLogin(
                                        jsonObject.getInt("id"),
                                        jsonObject.getString("lastname"),
                                        jsonObject.getString("firstname"),
                                        jsonObject.getString("username"),
                                        jsonObject.getString("email"),
                                        jsonObject.getString("profile_link")
                                );
                                Toast.makeText(getContext(), "Bonjour "+jsonObject.getString("lastname")+" "+jsonObject.getString("firstname"), Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
                                textInputLayout_username.startAnimation(shake);
                                textInputLayout_password.startAnimation(shake);
                                login.startAnimation(shake);
                                //register_link.startAnimation(shake);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //progressDialog.dismiss();
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

        StringRequestHandler.getInstance(getContext()).addToRequestQueue(stringRequest);
        */
    }
}
