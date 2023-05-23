import client.UserSteps;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import stellarPageObject.LoginPage;
import stellarPageObject.MainStellarBurgersPage;
import static org.junit.Assert.assertEquals;

public class LogoutFromAccountTest {

    private UserSteps userSteps;
    private String token;
    private WebDriver driver;
    private static final String PAGE_URL = "https://stellarburgers.nomoreparties.site/";

    @BeforeClass
    public static void globalSetUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        userSteps = new UserSteps();
    }

    @Test
    public void logoutFromAccountTest() {
        User user = new User("jkbnmbaisoiwyewe@yandex.ru", "wweocbwoec", "evljnkerv");
        ValidatableResponse createResponse = userSteps.create(user);
        LoginPage loginPage = new LoginPage(driver);
        MainStellarBurgersPage mainStellarBurgersPage = new MainStellarBurgersPage(driver);
        driver.get(PAGE_URL);
        loginPage.enterLoginPage(".//div/button[text()='Войти в аккаунт']");
        loginPage.loginUserPage("jkbnmbaisoiwyewe@yandex.ru", "wweocbwoec");
        mainStellarBurgersPage.checkPersonalAccount();
        mainStellarBurgersPage.logoutFromAccount();
        token = createResponse.extract().path("accessToken");

        String expectedLoginPageText = "Вход";
        String actualLoginPageText = mainStellarBurgersPage.getLoginPageTextAfterLogout();
        assertEquals(expectedLoginPageText, actualLoginPageText);
    }

    @After
    public void tearDownAndClearData() {
        driver.quit();
        userSteps.delete(token);
    }
}