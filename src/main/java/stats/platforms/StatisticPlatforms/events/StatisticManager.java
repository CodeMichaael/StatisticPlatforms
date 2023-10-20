package stats.platforms.StatisticPlatforms.events;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import stats.platforms.StatisticPlatforms.table.StatisticRepo;
import stats.platforms.StatisticPlatforms.table.StatisticTable;

@RestController
@RequestMapping("/statistic-manager/")
public class StatisticManager {
	
	@Autowired
	private StatisticRepo repo;
	private StatisticTable currentTable;
	
	@PostMapping("/create-test")
	public String newTest(
	        @RequestParam("name") String name,
	        @RequestParam("date") LocalDate date,
	        @RequestParam("testurl") String testUrl
	    ) {
		UUID uuid = UUID.randomUUID();
		currentTable.setUnit(uuid);
		currentTable.setNameOfTest(name);
		currentTable.setDateOfTest(date);
		currentTable.setTestURL(testUrl);
		
		repo.insertNewTable(currentTable, testUrl);
		return "Created new test.";
		
	}
	
	@GetMapping("/admin/runtests")
	public List<String> runAndSaveTests(
	    @RequestParam("unit") UUID unit,
	    @RequestParam("testurl") Optional<String> testUrl,
	    @RequestParam("testname") Optional<String> testname,
	    @RequestParam("method") String method) throws IOException {

	    updateStatisticValues(unit, testname, testUrl);
	    return repo.testMethods(testUrl, method);
	}

	private void updateStatisticValues(UUID unit, Optional<String> testname, Optional<String> testUrl) throws IOException {
		repo.updateValue(unit, "statistic_manager", "setdateOfTest", null, null, null);
		repo.updateValue(unit, "statistic_manager", "set-testName", testname, null, null);
		repo.updateValue(unit, "statistic_manager", "set-testDate", null, null, null);
		repo.updateValue(unit, "statistic_manager", "set-testAddress", testUrl, null, null);
	}

	@PostMapping("remove-test")
	public String removeTest(@RequestParam("unit") UUID unit, @RequestParam("collection") String collection) {
		return repo.deleteTable(unit, collection);
	}
	
	@PostMapping("/getall-tests")
	public List<StatisticTable> getAll(@RequestBody String collection) {
		return repo.getAll(collection);
	}

}
