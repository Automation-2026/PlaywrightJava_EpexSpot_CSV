package tests;

import com.microsoft.playwright.*;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.util.Arrays;

/**
 * All the common methods are available under BaseTest
 * The launching of the url and teardown for all the test cases is handled here
 * As per the parameter passed through testng the browser will be launched!
 */

public class BaseTest {

    Playwright playwright;
    Browser browser;
    BrowserContext browserContext;
    Page page;

    @BeforeMethod
    @Parameters({"browserName"})
    public void setup(String browserName){

        if (browserName == null || browserName.isEmpty()) {
            throw new SkipException(
                    "Browser parameter is missing in testng.xml. Skipping execution."
            );
        }
        playwright = Playwright.create();
        switch (browserName.toLowerCase()) {
            case "chrome":
                browser = playwright.chromium().launch(
                        new BrowserType.LaunchOptions()
                                .setHeadless(false)
                                .setArgs(Arrays.asList("--start-maximized")));
                break;
            case "firefox":
                browser = playwright.firefox().launch();
                break;
            case "webkit":
                browser = playwright.webkit().launch();
                break;
            default:
                throw new SkipException("Invalid browser: " + browserName);
        }
        browserContext = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
        page = browserContext.newPage();
        page.setDefaultTimeout(30000);
        page.navigate("https://www.epexspot.com/en/market-results?market_area=DE&auction=&trading_date=&delivery_date=2026-04-26&underlying_year=&modality=Continuous&sub_modality=&technology=&data_mode=table&period=&production_period=&product=60");
    }

    @AfterMethod
    public void teardown(){
        page.close();
    }
}
