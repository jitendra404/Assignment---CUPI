package Flipkart;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Flipkart_Automation2 {

	public static void main(String[] args) throws IOException, InterruptedException {

		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));

		// Go to Flipkart India.
		driver.get("https://www.flipkart.com/");

		// Close the login popup if it appears.
		

		// In the search bar, enter "Bluetooth Speakers" and search.
		driver.findElement(By.name("q")).sendKeys("Bluetooth Speakers");
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		// Apply filters:
		// Brand → boAt

		driver.findElement(By.xpath("//div[text()='Brand']")).click();
		driver.findElement(By.xpath("//div[text()='boAt']")).click();

		// Customer Ratings → 4★ & above
		Actions act = new Actions(driver);
		act.scrollToElement(driver.findElement(By.xpath("//div[text()='4★ & above']"))).perform();
		driver.findElement(By.xpath("//div[text()='4★ & above']")).click();

		// Sort results by Price — Low to High.
		WebElement lowtohigh = driver.findElement(By.xpath("//div[text()='Price -- Low to High']"));
		Actions act1 = new Actions(driver);
		act1.scrollToElement(lowtohigh).perform();
		lowtohigh.click();
		
		// From the 1st product on the search results page:
		// Open the product in a new tab.

		Actions act2=new Actions(driver);
		act2.scrollToElement(driver.findElement(By.linkText("boAt Stone 310, 16 Hrs Playback, 45mm Drivers, TWS Conn..."))).perform();
		driver.findElement(By.linkText("boAt Stone 310, 16 Hrs Playback, 45mm Drivers, TWS Conn...")).click();
		
		String parentid = driver.getWindowHandle();
		Set<String> allId = driver.getWindowHandles();
		allId.remove(parentid);
		for (String id : allId) {
			driver.switchTo().window(id);
		}

		// On the product page, check if the “Available offers” section exists.
		String actualData = "Available offers";

		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Available offers']")));
		String expectedData =driver.findElement(By.xpath("//div[text()='Available offers']")).getText();

		if (actualData.equals(expectedData)) {
			System.out.println("Available offers section exists.");

			// If yes → print the number of offers in the console

			WebElement moreoptions = driver.findElement(By.xpath("//span[contains(text(),'more offers')]"));

			if (moreoptions.isDisplayed()) {
				moreoptions.click();
	
				List<WebElement> offers = driver.findElements(By.xpath("//span[@class='T7pkhK row']"));
				System.out.println("Available offers count: " + offers.size());
			} else {
				System.out.println("not exists");
			}
		}

		// If the product has an “Add to Cart” button:
		// Click Add to Cart
		Actions act4=new Actions(driver);
		act4.scrollToElement(driver.findElement(By.xpath("//button[text()='Add to cart']/..")));
		driver.findElement(By.xpath("//button[text()='Add to cart']")).click();

		// Take a screenshot and save it as cart_result.png and share with us screenshot
		// and whole project
		Actions act5=new Actions(driver);
		act5.scrollToElement(driver.findElement(By.xpath("//a[text()='boAt Stone 310, 16 Hrs Playback, 45mm Drivers, TWS Connectivity 5 W Bluetooth Speaker']"))).perform();
		
		TakesScreenshot ts = (TakesScreenshot) driver;
		File temp = ts.getScreenshotAs(OutputType.FILE);
		File src = new File("./errorShot/cart_results.png");
		FileHandler.copy(temp, src);

		driver.quit();
	}

}
