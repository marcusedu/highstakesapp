package br.com.hsacademy.app;

import org.junit.Before;
import org.junit.Test;

import br.com.hsacademy.app.Login.LoginFormValidator;

import static org.junit.Assert.assertEquals;

public class LoginFormValidatorTest {
    LoginFormValidator formValidator;

    @Before
    public void initLoginFormValidator() {
        formValidator = new LoginFormValidator();
    }

    @Test
    public void deveRetornarEmailValido() {
        assertEquals(true, formValidator.isValidEmail("marcusedu@hotmail.com"));
    }

    @Test
    public void deveRetornarEmailInvalido() {
        assertEquals(false, formValidator.isValidEmail("marcus edu@hotmail.com"));
        assertEquals(false, formValidator.isValidEmail("marcusedu@hotmailcom"));
    }

    @Test
    public void deveRetornarSenhaValida(){
        assertEquals(true, formValidator.isValidPassword("M@rc6278544"));
    }

    @Test
    public void deveRetornarSenhaInvalida(){
        assertEquals(false, formValidator.isValidPassword("1324"));
    }

    @Test
    public void deveRetornarSenhaFraca(){
        assertEquals(20, formValidator.forcaDaSenha("4914"));
    }
}
