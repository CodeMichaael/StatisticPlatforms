package stats.platforms.StatisticPlatforms.table;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "statistic_manager")
public class StatisticTable {
	
	@Id
	UUID unit;
	
	@Field("awaitingTests")
	String nameOfTest;
	LocalDate dateOfTest;
	// Decided not to change "testurl" to a URL type.
	String testURL;
	
	@Field("completedTests")
	String testName;
	LocalDate testDate;
	String testAddress;
	List<String> logs;
	
	public StatisticTable(UUID unit, String nameOfTest, LocalDate dateOfTest, String testURL, String testName,
			LocalDate testDate, String testAddress, List<String> logs) {
		super();
		this.unit = unit;
		this.nameOfTest = nameOfTest;
		this.dateOfTest = dateOfTest;
		this.testURL = testURL;
		this.testName = testName;
		this.testDate = testDate;
		this.testAddress = testAddress;
		this.logs = logs;
	}
	
	
	
	public UUID getUnit() {
		return unit;
	}
	
	public String getNameOfTest() {
		return nameOfTest;
	}

	public LocalDate getDateOfTest() {
		return dateOfTest;
	}

	public String getTestURL() {
		return testURL;
	}

	public String getTestName() {
		return testName;
	}

	public LocalDate getTestDate() {
		return testDate;
	}

	public String getTestAddress() {
		return testAddress;
	}

	public List<String> getLogs() {
		return logs;
	}

	public void setUnit(UUID unit) {
		this.unit = unit;
	}
	public void setNameOfTest(String nameOfTest) {
		this.nameOfTest = nameOfTest;
	}
	public void setDateOfTest(LocalDate dateOfTest) {
		this.dateOfTest = dateOfTest;
	}
	public void setTestURL(String testURL) {
		this.testURL = testURL;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public void setTestDate(LocalDate testDate) {
		this.testDate = testDate;
	}
	public void setTestAddress(String testAddress) {
		this.testAddress = testAddress;
	}
	public void setLogs(List<String> logs) {
		this.logs = logs;
	}
	
	
}
