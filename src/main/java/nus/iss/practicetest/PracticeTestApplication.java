package nus.iss.practicetest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import nus.iss.practicetest.model.Task;
import nus.iss.practicetest.service.TaskService;

@SpringBootApplication
public class PracticeTestApplication implements CommandLineRunner {

	@Autowired
	TaskService taskSvc;

	public static void main(String[] args) {
		SpringApplication.run(PracticeTestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// read todo.txt into a String
		InputStream is = new ClassPathResource("/static/todos.txt").getInputStream();
		InputStreamReader reader = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(reader);

		StringBuilder text = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			text.append(line);
		}
		br.close();

		// replace all non-Json characters
		String finalText = text.toString();
		finalText = finalText.replace("”", "\"");
		finalText = finalText.replace('\u00A0', ' ');

		// read String into Json Array (array of Json Objects)
		JsonReader jReader = Json.createReader(new StringReader(finalText));
		JsonArray jsonArray = jReader.readArray();
		jReader.close();

		// create Task objects and add to Redis (using service)
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonObject jObject = jsonArray.get(i).asJsonObject();

			Task task = new Task();
			task.setId(jObject.get("id").toString());
			task.setName(jObject.get("name").toString());
			task.setDescription(jObject.get("description").toString());
			task.setPriority(jObject.get("priority_level").toString());
			task.setStatus(jObject.get("status").toString());

			String dDateStr = jObject.get("due_date").toString().replace("\"", "");
			String cDateStr = jObject.get("created_at").toString().replace("\"", "");
			String uDateStr = jObject.get("updated_at").toString().replace("\"", "");
			SimpleDateFormat sdf = new SimpleDateFormat("EEE, MM/dd/yyyy");
			Date dDate = sdf.parse(dDateStr);
			Date cDate = sdf.parse(cDateStr);
			Date uDate = sdf.parse(uDateStr);
			task.setDueDate(dDate);
			task.setCreatedAt(cDate);
			task.setUpdatedAt(uDate);

			taskSvc.createTask(task);
			// System.out.println(taskSvc.retrieveTask(task.getId()));
		}

	}

}
