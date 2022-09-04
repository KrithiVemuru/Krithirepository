package pages;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import utils.TestUtils;

public class AssignmentPage extends BasePage {

	public AssignmentPage(TestUtils testUtils) {
		super(testUtils);
		this.testUtils = testUtils;
	}

	public WebElement getLink(String linkText) {
		return testUtils.getWebDriverWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='" + linkText + "']")));
	}

	public List<WebElement> getColumnHeader() {
		return testUtils.getWebDriverWait()
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//span[@role='columnheader']")));
	}

	public List<WebElement> getColumnValues() {
		return testUtils.getWebDriverWait()
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//span[@role='cell']")));
	}

	public WebElement getDynamicTableActualResultElement() {
		return testUtils.getWebDriverWait()
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@class='bg-warning']")));
	}

	public void clickThisLink(String linkText) {
		getLink(linkText).click();
	}

	public String getDynamicTableActualResult() {
		return getDynamicTableActualResultElement().getText();
	}

	public HashMap<String, HashMap<String, String>> getDynamicTableData() {
		int counter=1;
		HashMap<String, HashMap<String, String>> tableData = new HashMap<String, HashMap<String, String>>();
		HashMap<String, String> columnData = new HashMap<String, String>();
		List<WebElement> columnHeaders = getColumnHeader();
		List<WebElement> columnValues = getColumnValues();
		for (int i = 0; i <columnValues.size(); i = i + (columnValues.size() / (columnHeaders.size()-1))) {
			for (int j = i + 1; j <= (i+columnHeaders.size()-1); j++) {
				columnData.put(columnHeaders.get(counter).getText(), columnValues.get(j).getText());
				counter++;
			}
			tableData.put(columnValues.get(i).getText(), columnData);
			columnData = new HashMap<>();
			counter=1;
		}
		return tableData;
	}
}