package com.andrukhiv.mynavigationdrawer.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.andrukhiv.mynavigationdrawer.R;
import com.google.android.material.textfield.TextInputLayout;


public class LaboratoryActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText
            editVolumeJuice,
            editSugarJuice,
            editAcidityJuice,
            editStrengthWine,
            editAcidityWine;

    TextInputLayout
            inputVolumeJuice,
            inputSugarJuice,
            inputAcidityJuice,
            inputStrengthWine,
            inputAcidityWine,
            inputResultSugar,
            inputResultWater,
            inputResultMust;

    Button buttonResult;

    private EditText textResultSugar;
    private EditText textResultWater;
    private EditText textResultMust;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratory);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        setSupportActionBar(findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_up_arrow_icon);
        }

        editVolumeJuice = findViewById(R.id.volume_juice);
        editSugarJuice = findViewById(R.id.sugar_juice);
        editAcidityJuice = findViewById(R.id.acidity_juice);
        editAcidityWine = findViewById(R.id.acidity_wine);
        editStrengthWine = findViewById(R.id.strength_wine);

        buttonResult = findViewById(R.id.buttonResult);

        textResultSugar = findViewById(R.id.resultSugar);
        textResultWater = findViewById(R.id.resultWater);
        textResultMust = findViewById(R.id.resultMust);

        inputVolumeJuice = findViewById(R.id.input_volume_juice);
        inputSugarJuice = findViewById(R.id.input_sugar_juice);
        inputAcidityJuice = findViewById(R.id.input_acidity_juice);
        inputStrengthWine = findViewById(R.id.input_strength_wine);
        inputAcidityWine = findViewById(R.id.input_acidity_wine);
        inputResultSugar = findViewById(R.id.input_result_sugar);
        inputResultWater = findViewById(R.id.input_result_water);
        inputResultMust = findViewById(R.id.input_result_must);

        buttonResult.setOnClickListener(this);

        editVolumeJuice.addTextChangedListener(new MyTextWatcher(editVolumeJuice));
        editSugarJuice.addTextChangedListener(new MyTextWatcher(editSugarJuice));
        editAcidityJuice.addTextChangedListener(new MyTextWatcher(editAcidityJuice));
        editStrengthWine.addTextChangedListener(new MyTextWatcher(editStrengthWine));
        editAcidityWine.addTextChangedListener(new MyTextWatcher(editAcidityWine));
    }


    // Очищення значень попередніч результатів при внесенні змін у поля
    private void clearViewGroup(ViewGroup group) {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);

            if (view instanceof EditText) {
                ((EditText) view).getText().clear();
            }

            if (view instanceof ViewGroup && (((ViewGroup) view).getChildCount() > 0))
                clearViewGroup((ViewGroup) view);
        }
    }


    private void submitForm() {

        if (!validateVolumeJuice() ||
                !validateSugarJuice() ||
                !validateAcidityJuice() ||
                !validateAcidityWine() ||
                !validateStrengthWine()) {

            clearViewGroup(findViewById(R.id.result_view_group));

            // visibilityViewGroup((ViewGroup) findViewById(R.id.result_view_group));

            Toast.makeText(getApplicationContext(), R.string.error_invalid_toast, Toast.LENGTH_LONG).show();
            return;
        }

        int num1 = Integer.parseInt(editVolumeJuice.getText().toString());// об’єм соку/сусла
        int num2 = Integer.parseInt(editSugarJuice.getText().toString());// кількість цукру у соці
        int num3 = Integer.parseInt(editAcidityJuice.getText().toString());// кислотність соку
        int num4 = Integer.parseInt(editStrengthWine.getText().toString());// спиртуозність вина
        int num5 = Integer.parseInt(editAcidityWine.getText().toString());// кислотність вина

        // https://alcofan.com/raschet-parametrov-domashnego-vina
        double resultSugar = (num4 * 16.7 * (num3 / num5) - num2) / 1000 * num1;
        double resultWater = ((num3 / num5 - 1) * 1000 - 0.6 * resultSugar * 1000 / num1) / 1000 * num1;
        double resultMust = num1 + resultSugar + resultWater;// об’єм води до соку л

        EditText textSugar = textResultSugar;
        @SuppressLint("DefaultLocale") String stringBuilder = String.format("%.2f", resultSugar) + "";
        textSugar.setText(stringBuilder);

        EditText textWater = textResultWater;
        @SuppressLint("DefaultLocale") String stringBuilder2 = String.format("%.2f", resultWater) + "";
        textWater.setText(stringBuilder2);

        EditText textMust = textResultMust;
        @SuppressLint("DefaultLocale") String stringBuilder3 = String.format("%.2f", resultMust) + "";
        textMust.setText(stringBuilder3);

        if (num5 > num3) {

            Toast.makeText(getApplicationContext(), R.string.error_invalid_toast, Toast.LENGTH_LONG).show();

            clearViewGroup(findViewById(R.id.result_view_group));

            inputAcidityWine.setErrorEnabled(Boolean.TRUE);
            inputAcidityWine.setError(getResources().getString(R.string.error_invalid_data));
            requestFocus(editAcidityWine);

        } else {
            inputAcidityWine.setErrorEnabled(Boolean.FALSE);
        }


    }


    private boolean validateVolumeJuice() {

        String obj = editVolumeJuice.getText().toString();

        int value = 0;

        try {
            value = Integer.parseInt(obj);
//            if (value > 1000) {
//                Toast.makeText(this, getResources().getString(R.string.error_volume_juice), Toast.LENGTH_LONG).show();
//                editVolumeJuice.getText().replace(0, editVolumeJuice.getText().length(), "1000");
//            }

        } catch (NumberFormatException exception) {
            exception.printStackTrace();
        }

        if (editVolumeJuice.getText().length() == 0) {
            inputVolumeJuice.setErrorEnabled(Boolean.TRUE);
            inputVolumeJuice.setError(getResources().getString(R.string.error_null));
            requestFocus(editVolumeJuice);
            return false;
        } else if (value > 1000) {
            inputVolumeJuice.setErrorEnabled(Boolean.TRUE);
            inputVolumeJuice.setError(getResources().getString(R.string.error_volume_juice));
            requestFocus(editVolumeJuice);
            return false;
        } else {
            inputVolumeJuice.setErrorEnabled(Boolean.FALSE);
        }

        return true;
    }

    private boolean validateStrengthWine() {

        String obj = editStrengthWine.getText().toString();

        int value = 0;

        try {
            value = Integer.parseInt(obj);
        } catch (NumberFormatException exception) {
            exception.printStackTrace();
        }

        if (editStrengthWine.getText().length() == 0) {
            inputStrengthWine.setErrorEnabled(Boolean.TRUE);
            inputStrengthWine.setError(getResources().getString(R.string.error_null));
            requestFocus(editStrengthWine);
            return false;
        } else if (value < 9 || value > 20) {
            inputStrengthWine.setErrorEnabled(Boolean.TRUE);
            inputStrengthWine.setError(getResources().getString(R.string.error_strength_wine));
            requestFocus(editStrengthWine);
            return false;
        } else {
            inputStrengthWine.setErrorEnabled(Boolean.FALSE);
        }
        return true;
    }

    private boolean validateAcidityWine() {

        String obj = editAcidityWine.getText().toString();

        int value = 0;

        try {
            value = Integer.parseInt(obj);
        } catch (NumberFormatException exception) {
            exception.printStackTrace();
        }

        if (editAcidityWine.getText().length() == 0) {
            inputAcidityWine.setErrorEnabled(Boolean.TRUE);
            inputAcidityWine.setError(getResources().getString(R.string.error_null));
            requestFocus(editAcidityWine);
            return false;
        } else if (value < 4 || value > 6) {
            inputAcidityWine.setErrorEnabled(Boolean.TRUE);
            inputAcidityWine.setError(getResources().getString(R.string.error_acidity_wine));
            requestFocus(editAcidityWine);
            return false;
        } else {
            inputAcidityWine.setErrorEnabled(Boolean.FALSE);
        }
        return true;
    }

    private boolean validateAcidityJuice() {

        String obj = editAcidityJuice.getText().toString();

        int value = 0;

        try {
            value = Integer.parseInt(obj);
        } catch (NumberFormatException exception) {
            exception.printStackTrace();
        }

        if (editAcidityJuice.getText().length() == 0) {
            inputAcidityJuice.setErrorEnabled(Boolean.TRUE);
            inputAcidityJuice.setError(getResources().getString(R.string.error_null));
            requestFocus(editAcidityJuice);
            return false;
        } else if (value < 5 || value > 10) {
            inputAcidityJuice.setErrorEnabled(Boolean.TRUE);
            inputAcidityJuice.setError(getResources().getString(R.string.error_acidity_juice));
            requestFocus(editAcidityJuice);
            return false;
        } else {
            inputAcidityJuice.setErrorEnabled(Boolean.FALSE);
        }
        return true;
    }

    private boolean validateSugarJuice() {

        String obj = editSugarJuice.getText().toString();

        int value = 0;

        try {
            value = Integer.parseInt(obj);
        } catch (NumberFormatException exception) {
            exception.printStackTrace();
        }

        if (editSugarJuice.getText().length() == 0) {
            inputSugarJuice.setErrorEnabled(Boolean.TRUE);
            inputSugarJuice.setError(getResources().getString(R.string.error_null));
            requestFocus(editSugarJuice);
            return false;
        } else if (value < 63 || value > 293) {
            inputSugarJuice.setErrorEnabled(Boolean.TRUE);
            inputSugarJuice.setError(getResources().getString(R.string.error_sugar_juice));
            requestFocus(editSugarJuice);
            return false;
        } else {
            inputSugarJuice.setErrorEnabled(Boolean.FALSE);
        }
        return true;
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

    }


    @Override
    public void onClick(View view) {

        submitForm();

        hideKeyboard(view);
    }


    private class MyTextWatcher implements TextWatcher {
        View view;


        private MyTextWatcher(View view) {
            this.view = view;
        }


        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            clearViewGroup(findViewById(R.id.result_view_group));
        }


        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            // Виведення тосту при введенні значення "0" у поле вводу
            if (editVolumeJuice.getText().toString().matches("^0")) {
                showErrorInvalidSnackBar();
                editVolumeJuice.getText().clear();
            } else if (editSugarJuice.getText().toString().matches("^0")) {
                showErrorInvalidSnackBar();
                editSugarJuice.getText().clear();
            } else if (editAcidityJuice.getText().toString().matches("^0")) {
                showErrorInvalidSnackBar();
                editAcidityJuice.getText().clear();
            } else if (editStrengthWine.getText().toString().matches("^0")) {
                showErrorInvalidSnackBar();
                editStrengthWine.getText().clear();
            } else if (editAcidityWine.getText().toString().matches("^0")) {
                showErrorInvalidSnackBar();
                editAcidityWine.getText().clear();
            }
        }


        @Override
        public void afterTextChanged(Editable editable) {

            switch (view.getId()) {
                case R.id.volume_juice:
                    validateVolumeJuice();
                    break;
                case R.id.sugar_juice:
                    validateSugarJuice();
                    break;
                case R.id.acidity_juice:
                    validateAcidityJuice();
                    break;
                case R.id.acidity_wine:
                    validateAcidityWine();
                    break;
                case R.id.strength_wine:
                    validateStrengthWine();
                    break;
            }
        }
    }

    private void showErrorInvalidSnackBar() {
        Toast.makeText(getApplicationContext(), R.string.error_invalid_value, Toast.LENGTH_LONG).show();
    }


    // При обрахуванні результату скидую клавіатуру донизу
    private void hideKeyboard(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        assert inputMethodManager != null;
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.laboratory, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;

        switch (item.getItemId()) {
            case R.id.information_sugar:
                intent = new Intent(LaboratoryActivity.this, LaboratorySugarActivity.class);
                startActivity(intent);
                break;
            case R.id.information_acidity:
                intent = new Intent(LaboratoryActivity.this, LaboratoryAcidityActivity.class);
                startActivity(intent);
                break;
            case R.id.table_sugar:
                intent = new Intent(LaboratoryActivity.this, LaboratoryTableActivity.class);
                startActivity(intent);
                break;
            case R.id.home:
                finish();
                break;
        }
        return (super.onOptionsItemSelected(item));
    }


    @Override
    public void onResume() {
        super.onResume();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
}