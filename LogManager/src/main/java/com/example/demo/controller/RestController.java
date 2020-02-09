package com.example.demo.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@org.springframework.web.bind.annotation.RestController
public class RestController {
	
	@CrossOrigin(origins = "*")
	@RequestMapping("/get/filelogs")
	public HashMap<String, ArrayList<HashMap<String, String>>> getProjects(@RequestParam(name="name", required=false, defaultValue="Unknown") String name) {
		
		HashMap<String,ArrayList<HashMap<String,String>>> map = new HashMap<String,ArrayList<HashMap<String,String>>>();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(
					"FileTrackerLog_20200209"));
			String line = reader.readLine();
			while (line != null) {
				System.out.println(line);
				String[] sec = line.split(" :: ");
				String timestamp = sec[0];
				String data = sec[1];
				String[] dataArr = data.split(" ");
				String event = dataArr[0];
				String folder = dataArr[1];
				String filename = "";
				for(int i=2;i<dataArr.length;i++) {
					if(i!=dataArr.length-1) {
						filename += dataArr[i] + " ";
					}else {
						filename += dataArr[i];
					}
				}
				
				HashMap<String,String> mapper = new HashMap<String,String>();
				mapper.put("timestamp", timestamp);
				mapper.put("event", event);
				//mapper.put("folder", folder);
				mapper.put("filename", filename);
				if((event.equalsIgnoreCase("CREATE") || event.equalsIgnoreCase("MOVED_TO"))){
					if(map.containsKey(folder)) {
						ArrayList<HashMap<String,String>> temp = map.get(folder);
						temp.add(mapper);
						map.put(folder, temp);
						
					}else {
						ArrayList<HashMap<String,String>> temp = new ArrayList<HashMap<String,String>>();
						temp.add(mapper);
						map.put(folder, temp);
					}
				}
				
				
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return map;
	}

	
}