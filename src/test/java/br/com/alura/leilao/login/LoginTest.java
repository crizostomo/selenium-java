package br.com.alura.leilao.login;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginTest {

    private WebDriver browser;

    private String URL_LOGIN() {
        return "http://localhost:8080/login";
    }

    @BeforeAll
    public static void beforeAll(){
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
    }

    @BeforeEach
    public void beforeEach(){
        this.browser = new ChromeDriver();
        this.browser.navigate().to(URL_LOGIN());
    }

    @AfterEach
    public void afterEach(){
        this.browser.quit();
    }

    @Test
    public void shouldLoginWithValidInfo() {
        browser.findElement(By.id("username")).sendKeys("fulano");
        browser.findElement(By.id("password")).sendKeys("pass");
        browser.findElement(By.id("login-form")).submit();

        Assert.assertFalse(browser.getCurrentUrl().equals(URL_LOGIN()));
        Assert.assertEquals("fulano", browser.findElement(By.id("usuario-logado")).getText());
    }

    @Test
    public void itShouldNotLoginWithInvalidData(){
        browser.findElement(By.id("username")).sendKeys("inválido");
        browser.findElement(By.id("password")).sendKeys("123123");
        browser.findElement(By.id("login-form")).submit();

        Assert.assertTrue(browser.getCurrentUrl().contains(URL_LOGIN()));
        Assert.assertTrue(browser.getPageSource().contains("Usuário e senha inválidos."));
        Assert.assertThrows(NoSuchElementException.class, () -> browser.findElement(By.id("usuario-logado")));
    }

    @Test
    public void itShouldAccessRestrictPageWithoutLogin(){
        this.browser.navigate().to("http://localhost:8080/leiloes/2");

        Assert.assertTrue(browser.getCurrentUrl().contains(URL_LOGIN()));
        Assert.assertFalse(browser.getPageSource().contains("Dados do Leilão"));

    }
}
