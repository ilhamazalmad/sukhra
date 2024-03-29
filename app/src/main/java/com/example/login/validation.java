package com.example.login;

import android.content.Context;
import android.widget.EditText;

public class validation {
    private Context context;
    public validation(Context context) {
        this.context = context;
    }
    public boolean isInputEditText(EditText editText, String msg) {
        String value = editText.getText().toString().trim();
        if (value.isEmpty()) {
            editText.setError(msg);
            return false;
        } else {
            editText.setError(null);
        }

        return true;
    }
    public boolean ismin(EditText editText,String msg) {
        String value = editText.getText().toString().trim();
        if (value.length()<8) {
            editText.setError(msg);
            return false;
        } else {
            editText.setError(null);
        }

        return true;
    }
    public boolean isInputEmail(EditText editText, String msg) {
        String value = editText.getText().toString().trim();
        if (value.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            editText.setError(msg);
            return false;
        } else {
            editText.setError(null);
        }
        return true;
    }

    public boolean isInputMatches(EditText editText1,EditText editText2, String msg) {
        String value1 = editText1.getText().toString().trim();
        String value2 = editText2.getText().toString().trim();
        if (!value1.contentEquals(value2)) {
            editText2.setError(msg);
            return false;
        } else {
            editText2.setError(null);
        }
        return true;
    }
}

