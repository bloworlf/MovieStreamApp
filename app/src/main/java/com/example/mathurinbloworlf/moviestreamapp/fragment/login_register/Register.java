package com.example.mathurinbloworlf.moviestreamapp.fragment.login_register;

import android.app.ProgressDialog;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mathurinbloworlf.moviestreamapp.R;
import com.example.mathurinbloworlf.moviestreamapp.manager.RequestHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;


public class Register extends Fragment{

    private static final int IMAGE_PICKER_SELECT = 1;
    private TextInputLayout input_layout_last_name, input_layout_first_name, input_layout_username, input_layout_email,
            input_layout_password, input_layout_confirm_password;
    private EditText input_last_name, input_first_name, input_username, input_email, input_password, input_confirm_password;
    private Button register;
    private TextView already_registered;
    private ImageView profile;

    private String lastname, firstname, username, email, password, telephone, adresse;

    private TextInputLayout input_layout_phone;
    private EditText input_phone;
    private TextInputLayout input_layout_adress;
    private EditText input_adress;
    private String filename;
    private Bitmap bitmap;

    public Register() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.register, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profile = (ImageView) view.findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPicture();
            }
        });

        input_layout_last_name = (TextInputLayout) view.findViewById(R.id.input_layout_last_name);
        input_layout_first_name = (TextInputLayout) view.findViewById(R.id.input_layout_first_name);
        input_layout_username = (TextInputLayout) view.findViewById(R.id.input_layout_username);
        input_layout_email = (TextInputLayout) view.findViewById(R.id.input_layout_email);
        input_layout_phone = (TextInputLayout) view.findViewById(R.id.input_layout_phone);
        input_layout_password = (TextInputLayout) view.findViewById(R.id.input_layout_password);
        input_layout_confirm_password = (TextInputLayout) view.findViewById(R.id.input_layout_confirm_password);
        input_layout_adress = (TextInputLayout) view.findViewById(R.id.input_layout_adress);

        input_last_name = (EditText) view.findViewById(R.id.input_last_name);
        input_first_name = (EditText) view.findViewById(R.id.input_first_name);
        input_username = (EditText) view.findViewById(R.id.input_username);
        input_email = (EditText) view.findViewById(R.id.input_email);
        input_phone = (EditText) view.findViewById(R.id.input_phone);
        input_password = (EditText) view.findViewById(R.id.input_password);
        input_confirm_password = (EditText) view.findViewById(R.id.input_confirm_password);
        input_adress = (EditText) view.findViewById(R.id.input_adress);

        input_last_name.addTextChangedListener(new MyTextWatcher(input_last_name));
        input_first_name.addTextChangedListener(new MyTextWatcher(input_first_name));
        input_username.addTextChangedListener(new MyTextWatcher(input_username));
        input_email.addTextChangedListener(new MyTextWatcher(input_email));
        input_password.addTextChangedListener(new MyTextWatcher(input_password));
        input_confirm_password.addTextChangedListener(new MyTextWatcher(input_confirm_password));

        register = (Button) view.findViewById(R.id.button_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastname = input_last_name.getText().toString().trim();
                firstname = input_first_name.getText().toString().trim();
                username = input_username.getText().toString().trim();
                email = input_email.getText().toString().trim();
                password = input_password.getText().toString().trim();
                telephone = input_phone.getText().toString().trim();
                adresse = input_adress.getText().toString().trim();
                registerUser();
                /*
                if(isValidRequest())
                    registerUser();
                else
                    Toast.makeText(getContext(), "Invalid registration request. \n Form not properly filled.", Toast.LENGTH_LONG).show();
                */

            }
        });

    }

    private boolean isValidRequest() {
        if(!input_layout_last_name.isErrorEnabled() ||
                !input_layout_first_name.isErrorEnabled() ||
                !input_layout_username.isErrorEnabled() ||
                !input_layout_email.isErrorEnabled() ||
                !input_layout_password.isErrorEnabled() ||
                !input_layout_confirm_password.isErrorEnabled() ||
                !input_layout_phone.isErrorEnabled() ||
                !input_layout_adress.isErrorEnabled())
            return true;
        return false;
    }

    private void selectPicture(){
        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        startActivityForResult(pickIntent, IMAGE_PICKER_SELECT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICKER_SELECT && data != null) {
            Uri selectedMedia = data.getData();

            /*
            Glide.with(this)
                    .load(selectedMedia)
                    .bitmapTransform(new CircleTransform(getContext()))
                    .thumbnail(Glide.with(this).load(R.drawable.vainsilentamericantoad))
                    .fitCenter()
                    .into(profile);
            filename = selectedMedia.getPath().substring(selectedMedia.getPath().lastIndexOf("/")+1);
            */

            try {
                bitmap = MediaStore.Images.Media.getBitmap(new ContextWrapper(getContext()).getContentResolver(), selectedMedia);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private void registerUser(){
        class RegisterUser extends AsyncTask<Bitmap,Void,String> {

            private ProgressDialog loading;
            private RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getContext(), "Registering...", null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = null;
                if(bitmap != null){
                    uploadImage = getStringImage(bitmap);
                }


                HashMap<String,String> data = new HashMap<>();

                data.put("image", uploadImage);
                data.put("filename", filename);
                data.put("lastname", lastname);
                data.put("firstname", firstname);
                data.put("username", username);
                data.put("email", email);
                data.put("password", password);
                data.put("telephone", telephone);
                data.put("adresse", adresse);

                return rh.sendPostRequest("http://10.0.2.2/Android/W7App/RegisterUser.php",data);
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(bitmap);
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            switch (view.getId()){
                case R.id.input_password:
                    showConfirmPassword();
                    break;
            }
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_first_name:
                    validateFirstName();
                    break;
                case R.id.input_last_name:
                    validateLastName();
                    break;
                case R.id.input_username:
                    validateUsername();
                    break;
                case R.id.input_email:
                    validateEmail();
                    break;
                case R.id.input_password:
                    validatePassword();
                    break;
                case R.id.input_confirm_password:
                    confirmPassword();
                    break;
            }
        }
    }

    private void showConfirmPassword()
    {
        Animation in = AnimationUtils.loadAnimation(getContext(), R.anim.edittext_appear);
        Animation out = AnimationUtils.loadAnimation(getContext(), R.anim.edittext_disappear);

        if(input_password.getText().toString().trim().equals("")){
            if(input_confirm_password.getVisibility() == View.VISIBLE){
                input_layout_confirm_password.startAnimation(out);
                input_confirm_password.startAnimation(out);

                input_confirm_password.setVisibility(View.GONE);
                input_layout_confirm_password.setVisibility(View.GONE);
            }
        }
        else{
            if(input_confirm_password.getVisibility() == View.GONE){
                input_confirm_password.setVisibility(View.VISIBLE);
                input_layout_confirm_password.setVisibility(View.VISIBLE);

                input_layout_confirm_password.startAnimation(in);
                input_confirm_password.startAnimation(in);
            }
        }
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private static boolean isValidName(String string){
        return string.length() > 3;
    }

    private static boolean isValidUsername(String username){
        return username.length() > 6;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private static boolean isUnacceptableSymbol(String c){
        return c.equals("!") || c.equals("@") || c.equals("#") || c.equals("$") || c.equals("%") || c.equals("^") || c.equals("&") ||
                c.equals("*") || c.equals("(") || c.equals(")") || c.equals("-") || c.equals("=") || c.equals("_") || c.equals("+") || c.equals(" ");
    }

    private int isValidPassword(String password){
        if(password.length() < 8){
            return 1;
        }
        else{
            for(int i=0;i<password.length();i++){
                String x = String.valueOf(password.toCharArray()[i]);
                if(isUnacceptableSymbol(x)){
                    return 2;
                }
            }
        }

        return 0;
    }

    private boolean validateFirstName() {
        String firstname = input_first_name.getText().toString().trim();
        if (firstname.isEmpty() || !isValidName(firstname)) {
            input_layout_first_name.setError(getString(R.string.err_msg_first_name));
            requestFocus(input_first_name);
            return false;
        } else {
            input_layout_first_name.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateLastName() {
        String lastname = input_last_name.getText().toString().trim();
        if (lastname.isEmpty() || !isValidName(lastname)) {
            input_layout_last_name.setError(getString(R.string.err_msg_last_name));
            requestFocus(input_last_name);
            return false;
        } else {
            input_layout_last_name.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail() {
        String email = input_email.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            input_layout_email.setError(getString(R.string.err_msg_email));
            requestFocus(input_email);
            return false;
        } else {
            input_layout_email.setErrorEnabled(false);
        }

        return true;
    }

    /*************************************************************************************
     *
     * A modifier
     */
    private boolean validateUsername() {
        String username = input_username.getText().toString().trim();
        //if username is empty or incorrect
        if (username.isEmpty() || !isValidUsername(username)) {
            input_layout_username.setError(getString(R.string.err_msg_incorect_username));
            requestFocus(input_username);
            return false;
        } else {
            //if username is already taken
            if (isTaken(username)) {
                input_layout_username.setError(getString(R.string.err_msg_taken_username));
                requestFocus(input_username);
                return false;
            }
            else{
                input_layout_username.setErrorEnabled(false);
            }
        }

        return true;
    }

    private boolean isTaken(String username) {
        return false;
    }

    /*************************************************************************************
     *
     * A modifier
     */

    private boolean validatePassword() {
        String password = input_password.getText().toString().trim();
        if(password.isEmpty() || isValidPassword(password) == 1){
            input_layout_password.setError(getString(R.string.err_msg_short_password));
            requestFocus(input_password);
            return false;
        }
        else if(isValidPassword(password) == 2){
            input_layout_password.setError(getString(R.string.err_msg_wrong_password));
            requestFocus(input_password);
            return false;
        } else {
            input_layout_password.setErrorEnabled(false);
        }

        return true;
    }

    private boolean confirmPassword() {
        String password = input_password.getText().toString().trim();
        String confirmpassword = input_confirm_password.getText().toString().trim();
        if (!confirmpassword.equals(password)) {
            input_layout_confirm_password.setError(getString(R.string.err_msg_confirm_password));
            requestFocus(input_confirm_password);
            return false;
        } else {
            input_layout_confirm_password.setErrorEnabled(false);
        }

        return true;
    }
}
