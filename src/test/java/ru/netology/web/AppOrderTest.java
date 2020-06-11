package ru.netology.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppOrderTest {
    private WebDriver driver;
    private static ChromeOptions options;
    WebElement form;

    @BeforeAll
    static void setUpAll() {
        System.out.println(System.getProperty("os.name"));
        if (System.getProperty("os.name").equals("Linux")){
            System.setProperty("webdriver.chrome.driver", "./driver/linux/chromedriver");
        } else if (System.getProperty("os.name").contains("Windows")) {
            System.setProperty("webdriver.chrome.driver", ".\\driver\\windows\\chromedriver.exe");
        }
        options = new ChromeOptions();
        options.addArguments("--headless");
    }

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver(options);
        driver.get("http://0.0.0.0:7777/");
        form = driver.findElement(By.cssSelector("[action]"));
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldSubmitRequest() {
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Маргарита Кирильчук");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79062762202");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actual.trim());
    }

    //bug
    @Test
    void shouldNotSubmitRequestIfOnlyName() {
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Маргарита");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79062762202");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String actual= driver.findElement(By.cssSelector(".input_type_text .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", actual.trim());
    }

    //bug
    @Test
    void shouldNotSubmitRequestIfOnlySurname() {
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Кирильчук");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79062762202");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String actual= driver.findElement(By.cssSelector(".input_type_text .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", actual.trim());
    }

    @Test
    void shouldSubmitRequestIfSurnameWithHyphen() {
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Маргарита Иванова-Петрова");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79062762202");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String actual= driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actual.trim());
    }
    @Test
    void shouldSubmitRequestIfOnlyCapitalLetters() {
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("МАРГАРИТА КИРИЛЬЧУК");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79062762202");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actual.trim());
    }

    @Test
    void shouldSubmitRequestIfOnlySmallLetters() {
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys(" маргарита кирильчук");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79062762202");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String actual= driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actual.trim());
    }

    @Test
    void shouldNotSubmitRequestIfEnglishName() {
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Margarita Kirilchuk");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79062762202");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String actual= driver.findElement(By.cssSelector(".input_type_text .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actual.trim());
    }

    @Test
    void shouldNotSubmitRequestIfInvalidSymbols() {
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Маргарита ***");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79062762202");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String actual= driver.findElement(By.cssSelector(".input_type_text .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actual.trim());
    }

    @Test
    void shouldNotSubmitRequestIfNumbersInName() {
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("12345678");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79062762202");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String actual= driver.findElement(By.cssSelector(".input_type_text .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actual.trim());
    }

    @Test
    void shouldNotSubmitRequestIfNoName() {
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79062762202");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String actual= driver.findElement(By.cssSelector(".input_type_text .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", actual.trim());
    }

    @Test
    void shouldNotSubmitRequestIfMoreThan11Numbers() {
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Маргарита Кирильчук");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+790627622021234");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String actual= driver.findElement(By.cssSelector(".input_type_tel .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual.trim());
    }

    @Test
    void shouldNotSubmitRequestIfLessThan11Numbers() {
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Маргарита Кирильчук");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+790627622");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String actual= driver.findElement(By.cssSelector(".input_type_tel .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual.trim());
    }

    @Test
    void shouldNotSubmitRequestIfTelWithoutPlus() {
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Маргарита Кирильчук");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("79062762202");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String actual= driver.findElement(By.cssSelector(".input_type_tel .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual.trim());
    }

    @Test
    void shouldNotSubmitRequestIfLettersInPhone() {
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Маргарита Кирильчук");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("Маргарита");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String actual= driver.findElement(By.cssSelector(".input_type_tel .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual.trim());
    }

    @Test
    void shouldNotSubmitRequestIfInvalidSymbolsInPhone() {
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Маргарита Кирильчук");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("***");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String actual= driver.findElement(By.cssSelector(".input_type_tel .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual.trim());
    }

    @Test
    void shouldNotSubmitRequestIfNoPhoneNumber() {
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Маргарита Кирильчук");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String actual= driver.findElement(By.cssSelector(".input_type_tel .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", actual.trim());
    }

    @Test
    void shouldNotSubmitRequestIfNoAgreement() {
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Маргарита Кирильчук");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79062762202");
        form.findElement(By.cssSelector("[role=button]")).click();
        String actual= driver.findElement(By.cssSelector("[role=presentation]")).getText();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", actual.trim());
    }
}

