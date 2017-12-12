import java.net.URL;
import java.util.List;
import java.net.MalformedURLException;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;

import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;


public class BrowserStackIOS {
    public static String accessKey = "BROWSERSTACK_USERNAME";
    public static String userName = "BROWSERSTACK_ACCESS_KEY";

    public static void main(String args[]) throws MalformedURLException, InterruptedException {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("device", "iPhone 7");
        capabilities.setCapability("app", "bs://<hashed app-id>");

        IOSDriver<IOSElement> driver = new IOSDriver<IOSElement>(new URL("http://"+userName+":"+accessKey+"@hub.browserstack.com/wd/hub"), capabilities);

        IOSElement loginButton = (IOSElement) new WebDriverWait(driver, 30).until(
            ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("Log In")));
        loginButton.click();
        IOSElement emailTextField = (IOSElement) new WebDriverWait(driver, 30).until(
            ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("Email address")));
        emailTextField.sendKeys("hello@browserstack.com");

        driver.findElementByAccessibilityId("Next").click();      
        Thread.sleep(5000);

        List<IOSElement> textElements = driver.findElementsByXPath("//XCUIElementTypeStaticText");
        assert(textElements.size() > 0);
        String matchedString = "";
        for(IOSElement textElement : textElements) {
          String textContent = textElement.getText();
          if(textContent != null && textContent.contains("not registered")) {
            matchedString = textContent;
          }
        }

        System.out.println(matchedString);
        assert(matchedString.contains("not registered on WordPress.com"));

        driver.quit();
    }
}
