package com.chengLinDemo.chengLinDemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ChengLinDemoApplication {

    private static final String ACCOUNT = "postgres";
    private static final String PASSWORD = "11111111";

    public final static String SUCCESS_STRING = "Success";
    public final static String FAILED_STRING = "Failed";

    public final static String SUCCESS_CODE = "0x000";
    public final static String FAILED_CODE = "0x001";

    public static void main(String[] args) {
        SpringApplication.run(ChengLinDemoApplication.class, args);
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
        Connection connection = null;
        Statement statement = null;
        boolean isSuccess = false;

        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/postgres";
            String title = new JSONObject(body).getString("title");
            String content = new JSONObject(body).getString("content");
            connection = DriverManager.getConnection(url, ChengLinDemoApplication.ACCOUNT, ChengLinDemoApplication.PASSWORD);
            statement = connection.createStatement();
            String sql = "INSERT INTO \"NoteBoard\"(id, timestamp_without_timezone, title, content) VALUES (gen_random_uuid(), now(), '" + title + "', '" + content + "')";
            statement.executeUpdate(sql);
            isSuccess = true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isSuccess ? generateSuccessOutput(new JSONArray()).toString() : generateFailedOutput().toString();
    }

    /**
     * 
     * @param body
     * @return
     */
    @PostMapping("/deleteNoteBoard")
    public String deleteNoteBoard(@RequestBody String body) {
        Connection connection = null;
        Statement statement = null;
        boolean isSuccess = false;

        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/postgres";
            JSONArray idArray = new JSONObject(body).optJSONArray("idArray");
            connection = DriverManager.getConnection(url, ChengLinDemoApplication.ACCOUNT, ChengLinDemoApplication.PASSWORD);
            statement = connection.createStatement();
            String sql = "DELETE FROM \"NoteBoard\" WHERE id IN (" + idArray.join(",").replaceAll("\"", "'") + ")";
            statement.executeUpdate(sql);
            isSuccess = true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isSuccess ? generateSuccessOutput(new JSONArray()).toString() : generateFailedOutput().toString();
    }

    @PostMapping("/getNoteBoard")
    /**
     * 
     * @return
     */
    public String getNoteBoard() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        boolean isSuccess = false;
        JSONArray outputJSONArray = new JSONArray();

        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/postgres";
            connection = DriverManager.getConnection(url, ChengLinDemoApplication.ACCOUNT, ChengLinDemoApplication.PASSWORD);
            statement = connection.createStatement();
            String sql = "SELECT * FROM \"NoteBoard\"";
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                // 根據欄位名稱取得該欄位的值
                String id = resultSet.getString("id");
                String timestamp_without_timezone = resultSet.getString("timestamp_without_timezone");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                JSONObject outputJSONObj = new JSONObject();
                outputJSONObj.put("id", id);
                outputJSONObj.put("timestamp_without_timezone", timestamp_without_timezone);
                outputJSONObj.put("title", title);
                outputJSONObj.put("content", content);
                outputJSONArray.put(outputJSONObj);
            }
            isSuccess = true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isSuccess ? generateSuccessOutput(outputJSONArray).toString() : generateFailedOutput().toString();
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
