/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subjectpreference;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import javax.servlet.ServletContext;

public class SubjectPreferenceSettingsInfo {

    ServletContext sc = null;
    String DB_URL = null;
    String DB_DRIVER = null;
    String DB_USER = null;
    String DB_PASSWORD = null;
    Connection con = null;
    Statement stmt = null;
    
    public SubjectPreferenceSettingsInfo(ServletContext sc)
    {
        this.sc = sc;
        DB_URL = sc.getInitParameter("DB_URL");
        DB_DRIVER = sc.getInitParameter("DB_DRIVER");
        DB_USER = sc.getInitParameter("DB_USER");
        DB_PASSWORD = sc.getInitParameter("DB_PASSWORD");
    }
    
    public String[] getCurrentSettings() {
        String[] values = null;
        try {

            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("select * from subject_preference_settings where ID=1");

            if (rs.next()) {
                values = new String[2];
                values[0] = rs.getString("SemType");
                values[1] = String.valueOf(rs.getInt("Status"));
            }
            
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return values;
    }

    public ArrayList<Integer> getSelectedSubjects(int userId) {
        ArrayList<Integer> subIds = null;

        try {

            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            stmt = con.createStatement();

            System.out.println("UserID = " + userId);
            ResultSet rs = stmt.executeQuery("select SubID from subject_preferences where UserID=" + userId);
            if (rs.first()) {
                subIds = new ArrayList();
                subIds.add(rs.getInt("SubID"));
            }
            while (rs.next()) {
                subIds.add(rs.getInt("SubID"));
            }
            System.out.println("subIds = " + subIds);
            
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return subIds;
    }

    public ArrayList<String> getSelectedSubjectNames(int userId) {
        ArrayList<String> subNames = null;

        try {

            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            stmt = con.createStatement();

            System.out.println("UserID = " + userId);
            ResultSet rs = stmt.executeQuery("select SubName from subjects_master where SubID in (select SubID from subject_preferences where UserID=" + userId + ")");
            if (rs.first()) {
                subNames = new ArrayList();
                subNames.add(rs.getString("SubName"));
            }
            while (rs.next()) {
                subNames.add(rs.getString("SubName"));
            }
            System.out.println("subNames = " + subNames);
            
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return subNames;
    }

    public LinkedHashMap<String, ArrayList<String>> getTheList(String reportType) {
        LinkedHashMap<String, ArrayList<String>> listMap = null;
        int remainder = -1;
        String semType = null;
        String query = null;

        String name = null;
        String pName = null;
        ArrayList<String> list = null;

        String DB_URL = "jdbc:mysql://localhost/portal";
        try {

            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("select SemType from subject_preference_settings where ID=1");
            if (rs.next()) {
                semType = rs.getString("SemType");
            }

            rs.close();
            stmt.close();

            if (semType != null) {
                if (semType.equalsIgnoreCase("Odd")) {
                    remainder = 1;
                } else if (semType.equalsIgnoreCase("Even")) {
                    remainder = 0;
                } else {
                    remainder = -1;
                }

                stmt = con.createStatement();
                
                System.out.println("reportType = "+reportType);
                
                if (reportType.equalsIgnoreCase("sub")) {
                    
                    query = "select X.subshortname, Y.initials from "+
                            " (select P.subid, P.subshortname from subjects_master as P , semester_wise_subjects as Q where Q.offered = 1 and Q.semno % 2 = "+remainder+
                            " and P.subid=Q.subid) as X left join (select C.userid, A.subid, B.subname, D.initials from semester_wise_subjects as A, "+
                            " subjects_master as B, subject_preferences as C, users as D where (A.semno%2="+remainder+") and A.offered = 1 and "+
                            " A.subid = B.subid and C.subid = A.subid and C.userid = D.userid order by A.subid) as Y on X.subid = Y.subid;";
                    
                    /*query = "select X.subshortname, Y.initials from subjects_master as X "+
                            " left join (select C.userid, A.subid, B.subname, D.initials from "+
                            " semester_wise_subjects as A, subjects_master as B, subject_preferences as C, users as D "+
                            " where (A.semno%2=0) and A.offered = 1 and A.subid = B.subid and C.subid = A.subid "+
                            " and C.userid = D.userid order by A.subid) as Y "+
                            " on X.subid = Y.subid";*/
                }
                else if(reportType.equalsIgnoreCase("faculty"))
                {
                    query = "select X.initials, Y.subshortname from users as X "+
                            " left join (select A.userid, A.subid, D.initials, B.subshortname from "+
                            " subject_preferences as A, subjects_master as B, users as D where A.subid = B.subid and "+
                            " A.userid = D.userid ) as Y on X.userid = Y.userid order by X.userid";
                }
                else if(reportType.equalsIgnoreCase("slot"))
                {
                    
                    query = "select Y.slot, X.initials from (select A.userid, A.initials, B.Slot from users as A, "+
                            " slot_preference as B where B.userid = A.userid) as Y left join users as X on X.userid = Y.userid "+
                            " order by Y.slot;";
                    
                    
                    /*query = "select X.initials, Y.slot from users as X left join "+
                            " (select A.userid, A.initials, B.Slot from users as A, slot_preference as B where B.userid = A.userid) as Y "+
                            " on X.userid = Y.userid order by X.userid";*/
                }
                    
                System.out.println(query);
                rs = stmt.executeQuery(query);
                listMap = new LinkedHashMap();
                
                if (reportType.equalsIgnoreCase("sub")) 
                {
                    System.out.println("Subject Wise");
                    while (rs.next()) {
                        name = rs.getString("SubShortName");
                        if (!name.equals(pName)) {
                            if (list != null && pName != null) {
                                listMap.put(pName, list);
                                System.out.print(pName + " :");
                                System.out.println(list);
                            }
                            list = new ArrayList();
                        }

                        if (list != null) {
                            list.add(rs.getString("Initials"));
                        }

                        pName = name;
                    }
                    if (list != null && pName != null) {
                        listMap.put(pName, list);
                        System.out.print(pName + " :");
                        System.out.println(list);
                    }
                } 
                else if(reportType.equalsIgnoreCase("faculty")) 
                {
                    System.out.println("Faculty Wise");
                    while (rs.next()) {
                        name = rs.getString("Initials");
                        System.out.println("Name = "+name);
                        if (!name.equals(pName)) {
                            if (list != null && pName != null) {
                                listMap.put(pName, list);
                                System.out.print(pName + " :");
                                System.out.println(list);
                            }
                            list = new ArrayList();
                        }
                        if (list != null) {
                            list.add(rs.getString("SubShortName"));
                        }
                        pName = name;
                    }
                    if (list != null && pName != null) {
                        listMap.put(pName, list);
                        System.out.print(pName + " :");
                        System.out.println(list);
                    }
                }
                else if(reportType.equalsIgnoreCase("slot"))
                {
                    System.out.println("Slot Wise");
                   /* while (rs.next()) {
                        list = new ArrayList();
                        name = rs.getString("Initials");
                        pName = rs.getString("Slot");
                        list.add(pName);
                        
                        System.out.println("Name = "+name+" Slot = "+pName);
                        
                        listMap.put(name, list);
                        System.out.print(pName + " :");
                        System.out.println(list);
                    }*/
                    
                    while (rs.next()) {
                        name = rs.getString("Slot");
                        if (!name.equals(pName)) {
                            if (list != null && pName != null) {
                                listMap.put(pName, list);
                                System.out.print(pName + " :");
                                System.out.println(list);
                            }
                            list = new ArrayList();
                        }

                        if (list != null) {
                            list.add(rs.getString("Initials"));
                        }

                        pName = name;
                    }
                    if (list != null && pName != null) {
                        listMap.put(pName, list);
                        System.out.print(pName + " :");
                        System.out.println(list);
                    }

                }
                rs.close();
                stmt.close();
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return listMap;
    }
    
    public String getSlotPreference(int userID)
    {
        String slot = null;
        String query = null;
        
        try {

            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select Slot from slot_preference where UserID="+userID);
            
            if(rs.next())
            {
                slot = rs.getString("Slot");
            }
            
            rs.close();
            stmt.close();
            con.close();
        }catch(Exception e)
        {
            System.out.println(e);
        }
        
        return slot;
    }
}
 