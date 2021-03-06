package br.com.hsacademy.app.Signup;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ProgressBar;
import android.widget.TextView;

import br.com.hsacademy.app.util.FormValidator;

public class PasswordStrengh implements TextWatcher {
    private ProgressBar pbar;
    private TextView textView;
    private FormValidator validator = new FormValidator();

    public PasswordStrengh(ProgressBar pbar, TextView textView) {
        this.pbar = pbar;
        this.textView = textView;
    }

    private String forcaSenha(int i) {
        if (i <= 30) return "Weak password";
        if (i < 55) return "Password neither weak nor strong";
        if (i < 80) return "Strong password";
        if (i < 99) return "Very strong password";
        if (i > 99) return "Wow! Password very, very strong";
        return "Password strenght";
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        int forca = validator.forcaDaSenha(String.valueOf(charSequence));
        textView.setText(forcaSenha(forca));
        pbar.setProgress(forca);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
