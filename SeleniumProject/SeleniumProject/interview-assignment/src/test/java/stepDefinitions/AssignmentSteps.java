package stepDefinitions;

import java.util.HashMap;

import org.testng.Assert;

import core.BaseSpec;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.AssignmentPage;

public class AssignmentSteps {
	
	AssignmentPage assignmentPage = new AssignmentPage(BaseSpec.testUtils);
	HashMap<String,HashMap<String,String>> tableData = new HashMap<String,HashMap<String,String>>();
	
	@When("User clicks on {string} link")
	public void userClicksOnLink(String linkName) {
		assignmentPage.clickThisLink(linkName);
	}
	
	@And("User reads the Dynamic Table Data")
	public void userReadsTheDynamicTableData() {
		tableData = assignmentPage.getDynamicTableData();
	}
	
	@Then("Verify Dynamic Table Data")
	public void verifyDynamicTableData() {
		String expectedResult = "Chrome CPU: "+tableData.get("Chrome").get("CPU");
		String actualResult = assignmentPage.getDynamicTableActualResult();
		Assert.assertTrue(expectedResult.equals(actualResult), "Expected Result:"+expectedResult+" is not equal to Actual Result:"+actualResult);
	}
}
