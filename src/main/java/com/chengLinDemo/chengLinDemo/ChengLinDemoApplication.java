package com.chengLinDemo.chengLinDemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ChengLinDemoApplication {

    public final static String SUCCESS_STRING = "Success";
    public final static String FAILED_STRING = "Failed";

    public final static String SUCCESS_CODE = "0x000";
    public final static String FAILED_CODE = "0x001";
    public DBManager dbManager = null;

    public static void main(String[] args) {
        SpringApplication.run(ChengLinDemoApplication.class, args);
    }

    public ChengLinDemoApplication() {
        dbManager = new DBManager();
    }

    @PostMapping("/hello")
    public String postBody(@RequestBody String fullName) {
        return fullName;
    }

    /**
     * 
     * @param body { title: 'Test title', content: 'Test content' }
     * @return
     */
    @PostMapping("/addNoteBoard")
    public String addNoteBoard(@RequestBody String body) {
        dbManager.addNoteBoard(new JSONObject(body));
        return generateSuccessOutput(new JSONArray()).toString();
    }

    @PostMapping("/deleteNoteBoard")
    public String deleteNoteBoard(@RequestBody String body) {
        dbManager.deleteNoteBoard(new JSONObject(body).optJSONArray("idArray"));
        return generateSuccessOutput(new JSONArray()).toString();
    }

    @PostMapping("/getNoteBoard")
    public String getNoteBoard() {
        return generateSuccessOutput(dbManager.getNoteBoardList()).toString();
    }

    public JSONObject generateSuccessOutput(JSONArray outputJSONArray) {
        JSONObject rJSON = new JSONObject();
        try {
            rJSON.put("Status", ChengLinDemoApplication.SUCCESS_STRING);
            rJSON.put("Code", ChengLinDemoApplication.SUCCESS_CODE);
            rJSON.put("Data", outputJSONArray);
        } catch (Exception e) {
        }
        return rJSON;
    }

    public JSONObject generateFailedOutput() {
        JSONObject rJSON = new JSONObject();
        try {
            rJSON.put("Status", ChengLinDemoApplication.FAILED_STRING).put("Code", ChengLinDemoApplication.FAILED_CODE);
        } catch (Exception e) {
        }
        return rJSON;
    }
}
