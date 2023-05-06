package com.chengLinDemo.chengLinDemo;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.json.JSONArray;
import org.json.JSONObject;

public class DBManager {

    private static final String ACCOUNT = "postgres";
    private static final String PASSWORD = "11111111";
    private Configuration configuration = null;
    private SessionFactory sessionFactory = null;

    public DBManager() {
        configuration = new Configuration();
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/postgres");
        configuration.setProperty("hibernate.connection.username", DBManager.ACCOUNT);
        configuration.setProperty("hibernate.connection.password", DBManager.PASSWORD);
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.format_sql", "true");
        configuration.addAnnotatedClass(NoteBoard.class);
        sessionFactory = configuration.buildSessionFactory();
    }

    public Session generateSession() {
        return sessionFactory.openSession();
    }

    public void deleteNoteBoard(JSONArray idArray) {
        Session session = null;
        try {
            session = this.generateSession();
            for (int i = 0; i < idArray.length(); i++) {
                Transaction transaction = null;
                try {
                    String uuid = idArray.optString(i);
                    transaction = session.beginTransaction();
                    NoteBoard noteBoard = session.get(NoteBoard.class, UUID.fromString(uuid));
                    if (noteBoard == null) {
                        continue;
                    }
                    session.delete(noteBoard);
                    transaction.commit();

                } catch (Exception e) {
                    if (transaction != null) {
                        transaction.rollback();
                    }
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeSession(session);
        }
    }

    public void addNoteBoard(JSONObject contentObj) {
        Session session = null;
        try {
            String title = contentObj.optString("title");
            String content = contentObj.optString("content");
            session = this.generateSession();
            Transaction transaction = session.beginTransaction();
            NoteBoard entity = new NoteBoard();
            entity.setTitle(title);
            entity.setContent(content);
            session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeSession(session);
        }
    }

    public JSONArray getNoteBoardList() {
        JSONArray outputJSONArray = new JSONArray();
        Session session = null;
        try {
            session = this.generateSession();
            CriteriaQuery<NoteBoard> query = session.getCriteriaBuilder().createQuery(NoteBoard.class);
            query.select(query.from(NoteBoard.class));
            for (NoteBoard noteBoard : session.createQuery(query).getResultList()) {
                UUID uuid = noteBoard.getUUID();
                Timestamp timestamp_without_timezone = noteBoard.getTimestamp_without_timezone();
                String title = noteBoard.getTitle();
                String content = noteBoard.getContent();
                JSONObject outputJSONObj = new JSONObject();
                outputJSONObj.put("id", uuid);
                outputJSONObj.put("timestamp_without_timezone", timestamp_without_timezone.toString());
                outputJSONObj.put("title", title);
                outputJSONObj.put("content", content);
                outputJSONArray.put(outputJSONObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeSession(session);
        }
        return outputJSONArray;
    }

    public void closeSession(Session session) {
        try {
            if (session != null) {
                session.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
