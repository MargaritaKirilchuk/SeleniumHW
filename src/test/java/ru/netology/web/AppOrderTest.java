package ru.netology.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppOrderTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        System.setProperty("webdriver.chrome.driver", "./driver/chromedriver");
    }

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldSubmitRequest() {
        driver.get("http://0.0.0.0:7777/");
        WebElement form = driver.findElement(By.cssSelector("[action]"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Маргарита Кирильчук");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79062762202");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    //bug
    @Test
    void shouldNotSubmitRequestIfOnlyName() {
        driver.get("http://0.0.0.0:7777/");
        WebElement form = driver.findElement(By.cssSelector("[action]"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Маргарита");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79062762202");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String text = driver.findElement(By.cssSelector(".input_type_text .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    //bug
    @Test
    void shouldNotSubmitRequestIfOnlySurname() {
        driver.get("http://0.0.0.0:7777/");
        WebElement form = driver.findElement(By.cssSelector("[action]"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Кирильчук");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79062762202");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String text = driver.findElement(By.cssSelector(".input_type_text .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void shouldSubmitRequestIfSurnameWithHyphen() {
        driver.get("http://0.0.0.0:7777/");
        WebElement form = driver.findElement(By.cssSelector("[action]"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Маргарита Иванова-Петрова");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79062762202");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }
    @Test
    void shouldSubmitRequestIfOnlyCapitalLetters() {
        driver.get("http://0.0.0.0:7777/");
        WebElement form = driver.findElement(By.cssSelector("[action]"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("МАРГАРИТА КИРИЛЬЧУК");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79062762202");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    void shouldSubmitRequestIfOnlySmallLetters() {
        driver.get("http://0.0.0.0:7777/");
        WebElement form = driver.findElement(By.cssSelector("[action]"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys(" маргарита кирильчук");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79062762202");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    void shouldNotSubmitRequestIfEnglishName() {
        driver.get("http://0.0.0.0:7777/");
        WebElement form = driver.findElement(By.cssSelector("[action]"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Margarita Kirilchuk");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79062762202");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String text = driver.findElement(By.cssSelector(".input_type_text .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    void shouldNotSubmitRequestIfInvalidSymbols() {
        driver.get("http://0.0.0.0:7777/");
        WebElement form = driver.findElement(By.cssSelector("[action]"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Маргарита ***");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79062762202");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String text = driver.findElement(By.cssSelector(".input_type_text .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    void shouldNotSubmitRequestIfNumbersInName() {
        driver.get("http://0.0.0.0:7777/");
        WebElement form = driver.findElement(By.cssSelector("[action]"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("12345678");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79062762202");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String text = driver.findElement(By.cssSelector(".input_type_text .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    void shouldNotSubmitRequestIfNoName() {
        driver.get("http://0.0.0.0:7777/");
        WebElement form = driver.findElement(By.cssSelector("[action]"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79062762202");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String text = driver.findElement(By.cssSelector(".input_type_text .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void shouldNotSubmitRequestIfMoreThan11Numbers() {
        driver.get("http://0.0.0.0:7777/");
        WebElement form = driver.findElement(By.cssSelector("[action]"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Маргарита Кирильчук");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+790627622021234");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String text = driver.findElement(By.cssSelector(".input_type_tel .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void shouldNotSubmitRequestIfLessThan11Numbers() {
        driver.get("http://0.0.0.0:7777/");
        WebElement form = driver.findElement(By.cssSelector("[action]"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Маргарита Кирильчук");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+790627622");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String text = driver.findElement(By.cssSelector(".input_type_tel .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void shouldNotSubmitRequestIfTelWithoutPlus() {
        driver.get("http://0.0.0.0:7777/");
        WebElement form = driver.findElement(By.cssSelector("[action]"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Маргарита Кирильчук");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("79062762202");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String text = driver.findElement(By.cssSelector(".input_type_tel .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void shouldNotSubmitRequestIfLettersInPhone() {
        driver.get("http://0.0.0.0:7777/");
        WebElement form = driver.findElement(By.cssSelector("[action]"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Маргарита Кирильчук");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("Маргарита");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String text = driver.findElement(By.cssSelector(".input_type_tel .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void shouldNotSubmitRequestIfInvalidSymbolsInPhone() {
        driver.get("http://0.0.0.0:7777/");
        WebElement form = driver.findElement(By.cssSelector("[action]"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Маргарита Кирильчук");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("***");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String text = driver.findElement(By.cssSelector(".input_type_tel .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void shouldNotSubmitRequestIfNoPhoneNumber() {
        driver.get("http://0.0.0.0:7777/");
        WebElement form = driver.findElement(By.cssSelector("[action]"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Маргарита Кирильчук");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String text = driver.findElement(By.cssSelector(".input_type_tel .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void shouldNotSubmitRequestIfNoAgreement() {
        driver.get("http://0.0.0.0:7777/");
        WebElement form = driver.findElement(By.cssSelector("[action]"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Маргарита Кирильчук");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79062762202");
        form.findElement(By.cssSelector("[role=button]")).click();
        String text = driver.findElement(By.cssSelector("[role=presentation]")).getText();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", text.trim());
    }


}

