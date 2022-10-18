package selenium_basic;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class open_google {

	@Test
	public void movieDetails() throws InterruptedException {
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver=new ChromeDriver();
		driver.manage().window().maximize();
		
		
		String Movie="Pushpa: The Rise - Part 1";
			//K.G.F: Chapter 1
		//Pushpa: The Rise - Part 1
		
		driver.get("https://www.imdb.com/");
		Actions a= new Actions(driver);
		a.moveToElement(driver.findElement(By.id("suggestion-search"))).click().sendKeys(Movie).build().perform();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[text()='"+Movie+"']/ancestor::a")).click();
		a.moveToElement(driver.findElement(By.xpath("//span[text()='Country of origin']/parent::li"))).build().perform();
		String imdbReleaseRaw=driver.findElement(By.xpath("//a[text()='Release date']/following-sibling::div//a")).getText().replace(",", "");
		String month=imdbReleaseRaw.split(" ")[0];
		String date=imdbReleaseRaw.split(" ")[1];
		String year=imdbReleaseRaw.split(" ")[2];
		String imdbReleaseDate=date+" "+month+" "+year;
		System.out.println(imdbReleaseDate);
		String imdbCountry=driver.findElement(By.xpath("//span[text()='Country of origin']/following-sibling::div//a")).getText().trim();
		System.out.println(imdbCountry);
		

		
		driver.get("https://en.wikipedia.org/");
		driver.findElement(By.id("searchInput")).sendKeys(Movie);
		driver.findElement(By.id("searchButton")).click();
		a.moveToElement(driver.findElement(By.xpath("//th[text()='Country']/following-sibling::td"))).build().perform();
		List<WebElement> wikiReleaseRaw=driver.findElements(By.xpath("//div[contains(text(),'Release date')]/parent::th/following-sibling::td//li"));
		
		List<String> wikiReleaseDateList=new ArrayList<String>();
		for(WebElement e: wikiReleaseRaw) {
			String date1=e.getText().trim().split(" ")[0];
			String month1=e.getText().trim().split(" ")[1];
			String year1=e.getText().trim().split(" ")[2];
			String wikiReleaseDate=date1+" "+month1+" "+year1;
			wikiReleaseDateList.add(wikiReleaseDate);
		}
		for(String s: wikiReleaseDateList) {
			System.out.println(s);
		}
		
		String wikiCountry=driver.findElement(By.xpath("//th[text()='Country']/following-sibling::td")).getText().trim();
		System.out.println(wikiCountry);
		
		boolean releaseDate = false;
		for(String s: wikiReleaseDateList) {
			if(imdbReleaseDate.equalsIgnoreCase(s)) {
				releaseDate=true;
				break;
			}
		}
		Assert.assertTrue(releaseDate);
		Assert.assertTrue(imdbCountry.equalsIgnoreCase(wikiCountry));
		
		
		
		driver.close();
		
		
	}

}
