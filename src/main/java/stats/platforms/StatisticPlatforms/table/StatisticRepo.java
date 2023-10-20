package stats.platforms.StatisticPlatforms.table;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.DeleteResult;

@Repository
public class StatisticRepo {
		
		@Autowired
		private MongoTemplate temp;
		private Class<StatisticTable> table = StatisticTable.class;
		
		private Query quickQuery(UUID id) {
			return new Query(Criteria.where("_id").is(id));
		}
		
		public List<StatisticTable> getAll(String collection) {
			return temp.findAll(table, collection);
		}
		
		public void insertNewTable(StatisticTable table, String collection) {
			temp.insert(table, collection);
		}
		
		public boolean valueExists(String field, String value) {
			Query query = new Query(Criteria.where(field).is(value));
			long count = temp.count(query, table);
			
			if (count > 0) return true;
			else return false;
		}
		
		public Object getValue(UUID id, String collection, String command) {
			Query query = quickQuery(id);
			StatisticTable currentTable = temp.findOne(query, table, collection);
			if (table != null) {
				switch (command) {
					case "get-date":
						return currentTable.getDateOfTest();
						
					case "get-url":
						return currentTable.getTestURL();
				
					case "get-test":
						return currentTable.getNameOfTest();
						
					default: return "Unknown command";
				}
			} else {
			    return "Document not found";
			}

		}
		
		public String updateValue(UUID id, String collection, String command, Optional<String> value, Optional<LocalDate> date, Optional<List<String>> log) {
			Query query = quickQuery(id);
			StatisticTable currentTable = temp.findOne(query, table, collection);
			if (currentTable != null) {
		        switch (command) {
		            case "set-nameOfTest":
		                value.ifPresent(v -> currentTable.setNameOfTest(v));
		                break;

		            case "set-dateOfTest":
		                date.ifPresent(d -> currentTable.setDateOfTest(d));
		                break;

		            case "set-testurl":
		                value.ifPresent(v -> currentTable.setTestURL(v));
		                break;

		            case "set-testName":
		                value.ifPresent(v -> currentTable.setTestName(v));
		                break;
		                
		            case "set-testDate":
		                date.ifPresent(d -> currentTable.setTestDate(d));
		                break;
		                
		            case "set-testAddress":
		                value.ifPresent(v -> currentTable.setTestAddress(v));
		                break;
		                
		            case "set-testlogs":
		                log.ifPresent(l -> currentTable.setLogs(l));
		                break;
		                
		            default: return "Unknown command";
		        }
		        
			} else {
			    return "Document not found";
			}
			return null;
}
		
		public String deleteTable(UUID id, String collection) {
			Query q = quickQuery(id);
			DeleteResult result = temp.remove(q, collection);
			return "Deleted "+result.getDeletedCount();
		}
		
		public List<String> testMethods(Optional<String> testUrl, String method) throws IOException {
			List<String> result = new ArrayList<>();
			try {
				URL url = new URL(testUrl.get());
	            HttpURLConnection connection;
				connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod(method);
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	            String line;
	            while ((line = reader.readLine()) != null) {
	                result.add(line); 
	            }
	            reader.close();
	            connection.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return result;

		}

	}

