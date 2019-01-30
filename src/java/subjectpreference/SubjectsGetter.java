/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subjectpreference;

import gen.Subject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import javax.servlet.ServletContext;


public class SubjectsGetter {

    private LinkedHashMap<String, ArrayList<Subject>> subjectsMap;
    Connection con;
    Statement stmt;
    String whichSem;
    
    String DB_URL = null;
    String DB_DRIVER = null;
    String DB_USER = null;
    String DB_PASSWORD = null;

    public SubjectsGetter(ServletContext sc) {
        try {

            DB_URL = sc.getInitParameter("DB_URL");
            DB_DRIVER = sc.getInitParameter("DB_DRIVER");
            DB_USER = sc.getInitParameter("DB_USER");
            DB_PASSWORD = sc.getInitParameter("DB_PASSWORD");

            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public SubjectsGetter(String sem, ServletContext sc) {
        
        this(sc);
        whichSem = sem;
    }

    public LinkedHashMap fetchSubjects() {

        if(whichSem == null)
            whichSem = fetchSemType();
        
        if (whichSem != null) {

            subjectsMap = new LinkedHashMap();
            ArrayList<Subject> subjects = new ArrayList();
            ArrayList<Integer> semesters = new ArrayList();
            int remainder = 0;
            ResultSet rs = null;
            
            //System.out.println("SemType = " + whichSem);
            if (whichSem.equalsIgnoreCase("Odd")) {
                remainder = 1;
            } else {
                remainder = 0;
            }

            if (con != null) {
                try {
                    stmt = con.createStatement();
                    int status = 1;
                    rs = stmt.executeQuery("select * from semester_wise_subjects where SemNo % 2= " + remainder);

                    while (rs.next()) {
                        //System.out.println("in side while loop");
                        Subject sub = new Subject();
                        sub.setSubId(rs.getInt("SubID"));
                        sub.setOffered(rs.getInt("Offered"));
                        sub.setSemester(rs.getInt("SemNo"));
                        subjects.add(sub);
                    }
                    rs.close();
                    stmt.close();

                    //System.out.println(subjects);  
                    for (int i = 0; i < subjects.size(); i++) {
                        Subject s = subjects.get(i);
                        int id = s.getSubId();
                        stmt = con.createStatement();
                        rs = stmt.executeQuery("select * from subjects_master where SubID= " + id);

                        if (rs.next()) {
                            s.setSubName(rs.getString("SubName").trim());
                            s.setSubCode(rs.getString("SubCode"));
                        }
                        subjects.set(i, s);
                        rs.close();
                        stmt.close();
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            //System.out.println(subjects);
            if (con != null) {
                try {
                    stmt = con.createStatement();
                    rs = stmt.executeQuery("select distinct SemNo from semester_wise_subjects where SemNo % 2= " + remainder);

                    while (rs.next()) {
                        semesters.add(Integer.valueOf(rs.getInt("SemNo")));
                        //System.out.println(rs.getInt("SemNo"));
                    }

                    rs.close();
                    stmt.close();
                    con.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            //System.out.println(semesters.toString());
            for (int j = 0; j < semesters.size(); j++) {
                String semName = null;
                ArrayList<Subject> semSubjects = new ArrayList();
                int semNo = semesters.get(j);

                if (semNo == 9 || semNo == 10) {
                    semName = "M.Tech. " + (semNo - 8);
                } else {
                    semName = "B.Tech. " + semNo;
                }

                for (Subject s : subjects) {

                    if (s.getSemester() == semNo) {
                        semSubjects.add(s);
                    }
                }

                subjectsMap.put(semName, semSubjects);
                //System.out.println(semName);
                //System.out.println(semSubjects);
            }
        }

        return subjectsMap;
    }

    private String fetchSemType() {
        String sem = null;
        ResultSet rs = null;

        //System.out.println("in side fetchSemtype()");
        if (con != null) {
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery("select * from subject_preference_settings where Status=1");

                if (rs.next()) {
                    sem = rs.getString("SemType");
                }
                rs.close();
                stmt.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        //System.out.println("SemType = "+sem);
        return sem;
    }
}
