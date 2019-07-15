package com.example.peakapp;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout firstNameTextInputLayout;
    private EditText firstNameEditText;
    private TextInputLayout lastNameTextInputLayout;
    private EditText lastNameEditText;
    private EditText nicknameEditText;
    private EditText ageEditText;
    private EditText genderEditText;
    private EditText countryEditText;
    private EditText addressEditText;
    private Button registerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        addListenerOnButtons();
        firstNameTextInputLayout = findViewById(R.id.firstNameTextInputLayout);
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameTextInputLayout = findViewById(R.id.lastNameTextInputLayout);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        nicknameEditText = findViewById(R.id.nicknameEditText);
        ageEditText = findViewById(R.id.ageEditText);
        genderEditText = findViewById(R.id.genderEditText);
        countryEditText = findViewById(R.id.countryEditText);
        addressEditText = findViewById(R.id.addressEditText);
        registerButton = findViewById(R.id.registerButton);

        firstNameEditText.addTextChangedListener(loginTextWatcher);
        lastNameEditText.addTextChangedListener(loginTextWatcher);
        nicknameEditText.addTextChangedListener(loginTextWatcher);
        ageEditText.addTextChangedListener(loginTextWatcher);
        genderEditText.addTextChangedListener(loginTextWatcher);
        countryEditText.addTextChangedListener(loginTextWatcher);
        addressEditText.addTextChangedListener(loginTextWatcher);

        registerButton.setEnabled(false);
        registerButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                userRegistration();
            }
        });
    }

    private void userRegistration() {

        String firstNameInput = firstNameEditText.getText().toString().trim();
        String lastNameInput = lastNameEditText.getText().toString().trim();
        String nicknameInput = nicknameEditText.getText().toString().trim();
        String ageInput = ageEditText.getText().toString().trim();
        int ageInputInteger = Integer.parseInt(ageInput);
        String genderInput = genderEditText.getText().toString().trim();
        String countryInput = countryEditText.getText().toString().trim();
        final String addressInput = addressEditText.getText().toString().trim();

        User user = new User(firstNameInput,
                lastNameInput,
                nicknameInput,
                ageInputInteger,
                genderInput,
                countryInput,
                addressInput);
        Call<User> call = RetrofitClient
                .getInstance()
                .getApi()
                .createUser(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                String ageLimitMessage = "Have not reached age limit for registration!";
                if (response.code() == 201) {
                    openDialog("Success", "Successfully created a user!");
                } else {
                    try {
                        if (response.errorBody() != null && response.errorBody().string().contains(ageLimitMessage)) {
                            openDialog("Failure", ageLimitMessage);
                        } else {
                            openDialog("Failure", response.raw().toString());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                openDialog("Failure", t.getMessage());
            }
        });
    }

    private void openDialog(String title, String message) {
        Dialog dialog = new Dialog(title, message);
        dialog.show(getSupportFragmentManager(), "dialog");
    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String firstNameInput = firstNameEditText.getText().toString().trim();
            String lastNameInput = lastNameEditText.getText().toString().trim();
            String ageInput = ageEditText.getText().toString().trim();
            String genderInput = genderEditText.getText().toString().trim();
            String countryInput = countryEditText.getText().toString().trim();
            String addressInput = addressEditText.getText().toString().trim();

            boolean isValid = !firstNameInput.isEmpty() && firstNameInput.length() > 6
                    && !lastNameInput.isEmpty() && lastNameInput.length() > 6
                    && !ageInput.isEmpty()
                    && !genderInput.isEmpty()
                    && !countryInput.isEmpty()
                    && !addressInput.isEmpty();

            registerButton.setEnabled(isValid);
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (firstNameEditText.length() < 7 && firstNameEditText.length() != 0)
                firstNameTextInputLayout.setError("Field should be at least 6 characters long");
            else
                firstNameTextInputLayout.setErrorEnabled(false);
            if (lastNameEditText.length() < 7 && lastNameEditText.length() != 0)
                lastNameTextInputLayout.setError("Field should be at least 6 characters long");
            else
                lastNameTextInputLayout.setErrorEnabled(false);
        }
    };

}
