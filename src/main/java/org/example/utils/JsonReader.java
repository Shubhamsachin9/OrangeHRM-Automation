package org.example.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Employee;

import java.io.InputStream;

public class JsonReader {

    public static Employee getEmployeeData(){
        try{
            ObjectMapper objectMapper=new ObjectMapper();
            InputStream inputStream=JsonReader.class.getClassLoader().getResourceAsStream("employee.json");
            if (inputStream == null) {
                throw new RuntimeException("employee.json not found");
            }
            return objectMapper.readValue(inputStream, Employee.class);

        }catch (Exception e) {
            throw new RuntimeException("Failed to read employee.json", e);
        }
    }
}
