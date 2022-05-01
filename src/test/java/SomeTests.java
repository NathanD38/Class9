import org.apache.commons.io.FileUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class SomeTests {
    private static WebDriver driver;

    @BeforeClass
    public static void runOnceBeforeClass() {
        System.setProperty("webdriver.chrome.driver", Constants.CHROMEDRIVER_PATH);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void test_01() {
        driver.get("https://dgotlieb.github.io/WebCalculator/");
        WebElement button7 = driver.findElement(By.id("seven"));
        System.out.println("7 button's dimensions are: " + button7.getSize());
        WebElement button6 = driver.findElement((By.id("six")));
        System.out.println("6 button is displayed: " + button6.isDisplayed());
        String button = "7";
        WebElement button3 = driver.findElement(By.id("three"));
        button3.click();
        WebElement plus = driver.findElement(By.id("add"));
        plus.click();
        WebElement button4 = driver.findElement(By.id("four"));
        button4.click();
        WebElement equal = driver.findElement(By.id("equal"));
        equal.click();
        WebElement screen = driver.findElement(By.id("screen"));
        String result = screen.getText();
        Assert.assertEquals(button, result);
    }

    @Test
    public void test_02() {
        driver.get("https://dgotlieb.github.io/WebCalculator/");
        String URL = "https://dgotlieb.github.io/WebCalculator/";
        String siteURL = driver.getCurrentUrl();
        Assert.assertEquals(siteURL, URL);
    }

    @Test
    public void test_03() {
        driver.get("https://dgotlieb.github.io/WebCalculator/");
        String title = "Calculator";
        driver.navigate().refresh();
        String siteTitle = driver.getTitle();
        Assert.assertEquals(siteTitle, title);
    }

    @Test
    public void test_04() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-extensions");
        driver = new ChromeDriver(options);
    }

    @Test
    public void test_05() throws InterruptedException {
        driver.get("https://dgotlieb.github.io/Actions/");
        takeScreenshot(driver.findElement(By.id("div1")));
        WebElement myDemo = driver.findElement(By.xpath("/html/body/p[2]"));
        Actions doubleClick = new Actions(driver);
        doubleClick.doubleClick(myDemo).build().perform();

        String result = driver.findElement(By.id("demo")).getText();
        Assert.assertEquals("You double clicked", result);

        Actions hoverAction = new Actions(driver);
        WebElement small = driver.findElement(By.id("demo"));
        WebElement big = driver.findElement(By.id("close"));
        hoverAction.moveToElement(small).moveToElement(big).click().build().perform();

        List<WebElement> elementList = driver.findElements(By.name("kind"));
        Actions multiple = new Actions(driver);
        multiple.clickAndHold(elementList.get(0)).clickAndHold(elementList.get(1));
        multiple.build().perform();

        driver.findElement(By.name("pic")).sendKeys("C:\\Users\\Nate\\IdeaProjects\\Class9\\box-screenshot.png");

        WebElement finalButton = driver.findElement(By.id("clickMe"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", finalButton); //preferable
//        js.executeScript("javascript:window.scrollBy(250,350)"); //Bad idea
    }

    @Test
    public void test_06() {
        driver.get("https://dgotlieb.github.io/Controllers/");
        List<WebElement> radioList = driver.findElements(By.name("group1"));
        for (WebElement item : radioList) {
            if (item.getAttribute("value").equals("Cheese")) {
                item.click();
            }
            System.out.println(item.getAttribute("value"));
        }

        Select s2 = new Select(driver.findElement(By.name("dropdownmenu")));
        s2.selectByValue("Milk");
        System.out.println(s2.getFirstSelectedOption().getText());
    }

    @Test
    public void test_07() {
        driver.get("https://dgotlieb.github.io/WebCalculator/");
        WebElement button2 = driver.findElement(By.id("two"));
        System.out.println("Button2 height is: " + button2.getRect().getHeight());
        WebElement button6 = driver.findElement(By.id("six"));
        System.out.println("Button6 width is: " + button6.getRect().getWidth());
    }

    @Test
    public void challenge_08() {
        driver.get("https://www.themarker.com");

        //This will get the page source, then split the string whenever it finds the match,
        //then counts the number of splits it made.
        //source: https://stackoverflow.com/questions/18442895/how-many-times-a-text-appears-in-webpage-selenium-webdriver
        String source = driver.getPageSource();
        int count = source.split("news").length - 1;
        System.out.println(count);
    }

    @Test
    public void challenge_09() {
        //printing without interacting with the print window.
        //source: https://stackoverflow.com/questions/56405067/how-to-print-the-page-and-close-the-print-window-in-selenium
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--kiosk-printing");
        driver = new ChromeDriver(options);
        driver.get("https://www.wsj.com");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.print();");
    }



    private static void takeScreenshot(WebElement element) {
        File screenShotFile = element.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenShotFile, new File("box-screenshot.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



//    @AfterClass
//    public static void afterAll() {
//        driver.quit();
//    }

}
