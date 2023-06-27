package bzu.student;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Selenium {

    public static float webTestConversion(float amount, String from, String to) {
        System.setProperty("webdriver.firefox.driver","./geckodriver");
        WebDriver driver = new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("localhost:8000");
        driver.findElement(By.name("amount")).sendKeys(String.valueOf(amount));
        Select fromSelect = new Select(driver.findElement(By.name("from")));
        fromSelect.selectByValue(from);
        Select toSelect = new Select(driver.findElement(By.name("to")));
        toSelect.selectByValue(to);
        driver.findElement(By.id("convert")).click();
        float result = Float.parseFloat(driver.findElement(By.id("result")).getAttribute("value"));
        driver.quit();
        return result;
    }

}
