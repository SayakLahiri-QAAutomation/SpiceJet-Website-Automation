package code.selenium.spicejet;

//java imports 
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


///apache poi imports
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


//selenium webdriver imports
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class spiceJetAutomationDemo {

	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		
		System.setProperty("webdriver.chrome.driver", "D:\\selenium\\chromedriver.exe");
		
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
        //driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		
		
		//URL in the browser
		
		driver.get("http://spicejet.com");
		
		
		//Select Origin	
		
		driver.findElement(By.id("ctl00_mainContent_ddl_originStation1_CTXT")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[@value='BLR']")).click();
		
		
		//Select Destination
		
		Thread.sleep(3000);
		driver.findElement(By.xpath("(//a[@value='BHO'])[2]")).click();
		Thread.sleep(2000);
		
		
		//selecting date
		
		while (!driver.findElement(By.cssSelector("[class='ui-datepicker-title'] [class='ui-datepicker-month']"))
		.getText().contains("April")) {
			driver.findElement(By.cssSelector("[class='ui-datepicker-title'] th[class='ui-datepicker-month']")).click();
			Thread.sleep(3000);
		}

		int count = driver.findElements(By.className("ui-state-default")).size();

		for (int i = 0; i < count; i++) {
			String text = driver.findElements(By.className("ui-state-default")).get(i).getText();
			if (text.equalsIgnoreCase("21")) {
				driver.findElements(By.className("ui-state-default")).get(i).click();
				break;
			}
		}
		
		
		//Choose type of passenger from drop down in the spiceJet page
		
		driver.findElement(By.id("divpaxinfo")).click();
		Thread.sleep(2000);
		
		System.out.println(driver.findElement(By.id("divpaxinfo")).getText()); //before selecting

		driver.findElement(By.id("ctl00_mainContent_ddl_Adult")).click();
		
		for (int i = 1; i < 5; i++) {
			driver.findElement(By.id("ctl00_mainContent_ddl_Adult")).sendKeys(Keys.ARROW_DOWN);
			Thread.sleep(2000);
		}

		driver.findElement(By.id("ctl00_mainContent_ddl_Adult")).click();
		Thread.sleep(2000);
		System.out.println(driver.findElement(By.id("divpaxinfo")).getText()); //after selecting
		Thread.sleep(2000);
		
		
		//dropDown with the static type using select tag
		
		WebElement staticdropdown = driver.findElement(By.name("ctl00$mainContent$DropDownListCurrency"));

		Select dropdown = new Select(staticdropdown); // selenium have select class implicitly

		System.out.println(dropdown.getFirstSelectedOption().getText());
		dropdown.selectByVisibleText("AED"); //choosing dropdown value using the selctByVisibleText mothod in driver
		
		
		//To select the Check box in the spiceJet page
		
		driver.findElement(By.xpath("//input[contains(@name,'chk_Senior')]")).click();
		Thread.sleep(3000);	
		
		
		//Click on the Search flight button
		
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[contains(@id,'FindFlights')]")).click();  // Search flights using regular expression
		Thread.sleep(3000);
		
		
		//Navigate to login page in spiceJet
		
		driver.navigate().to("https://book.spicejet.com/Login.aspx");
		
		
		//Data driven testing in the Login_Page using Apache Poi
		
		File path = new File("C:\\Users\\ASUS\\Desktop\\Spicejet.xlsx");
		FileInputStream inputstream = new FileInputStream(path);

		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(inputstream);

		XSSFSheet sheet = workbook.getSheetAt(0);

		// loop to iterate throught the rows and its cells from the Xsls sheet
		int rows = sheet.getLastRowNum();
		int cols = sheet.getRow(1).getLastCellNum();

		for (int r = 1; r <= rows; r++) {
			XSSFRow row = sheet.getRow(r);

			for (int c = 0; c < cols; c++) {
				XSSFCell cell = row.getCell(c);
				String cellValue = cell.getStringCellValue();
				System.out.println(cellValue);
				if (c == 0) {
					WebElement user = driver.findElement(By.cssSelector("input[id*='TextBoxUserID']"));
					user.clear();
					Thread.sleep(500);
					user.sendKeys(cellValue);
					Thread.sleep(2000);
				} else {
					WebElement pass = driver.findElement(By.cssSelector("input[id*='PasswordFieldPassword']"));
					pass.clear();
					Thread.sleep(500);
					pass.sendKeys(cellValue);
					Thread.sleep(2000);
				}

			}
			driver.findElement(By.cssSelector("input[id*='LoginView_ButtonLogIn']")).click();
			Thread.sleep(3000);
		}

	}

}


