package stats.platforms.StatisticPlatforms;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import stats.platforms.StatisticPlatforms.table.StatisticRepo;

@SpringBootApplication
public class StatisticPlatformsApplication {

	public static void main(String[] args) {
		SpringApplication.run(StatisticPlatformsApplication.class, args);
	}

}
