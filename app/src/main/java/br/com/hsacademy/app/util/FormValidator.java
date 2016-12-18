package br.com.hsacademy.app.util;

import java.util.regex.Pattern;

import static android.support.v4.util.PatternsCompat.EMAIL_ADDRESS;

public class FormValidator {
    private Pattern lowCase, highCase, numeric, especial;

    public FormValidator() {
        lowCase = Pattern.compile("[a-z]");
        highCase = Pattern.compile("[A-Z]");
        numeric = Pattern.compile("\\d");
        especial = Pattern.compile("\\W");
    }

    public int forcaDaSenha(String senha) {
        int forca = 0;
        if (senha.length() < 5) return forca;
        if (senha.length() < 8) {
            forca += 10;
        } else if (senha.length() >= 8) {
            forca += 25;
        }
        if (lowCase.matcher(senha).find()) {
            forca += 10;
        }
        if (highCase.matcher(senha).find()) {
            forca += 20;
        }
        if (numeric.matcher(senha).find()) {
            forca += 20;
        }
        if (especial.matcher(senha).find()) {
            forca += 25;
        }
        return forca;
    }


    public boolean isValidEmail(String email) {
        return EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean isValidPassword(String pass) {
        return forcaDaSenha(pass) >= 30;

    }
}
