package ulipsu_video;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;
public class Test2_videoplayonline {    //ILM...

    public static void main(String[] args) throws InterruptedException, IOException {
        List<String> urls = readUrlsFromExcel("C:\\Users\\manoj\\Downloads\\TEST_VIDEO1.xlsx");
        int batchSize = 2;
        int playTimeInSeconds = 10;

        for (int i = 0; i < urls.size(); i += batchSize) {
            List<String> batch = urls.subList(i, Math.min(i + batchSize, urls.size()));
            List<WebDriver> drivers = new ArrayList<>();
            for (String url : batch) {
                WebDriver driver = new ChromeDriver();
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                driver.manage().window().maximize();
                drivers.add(driver);
                driver.get(url);
                Actions actions = new Actions(driver);
                actions.sendKeys(Keys.SPACE).build().perform();
            }
            
            Thread.sleep(playTimeInSeconds * 1000);
            for (WebDriver driver : drivers) {
                driver.quit();
            }

            Thread.sleep(1000);
        }
    }

    public static List<String> readUrlsFromExcel(String filePath) {
        List<String> urls = new ArrayList<>();

        try (FileInputStream file = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0);  

            for (Row row : sheet) {
                String url = row.getCell(0).getStringCellValue(); 
                urls.add(url);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return urls;
    }
}