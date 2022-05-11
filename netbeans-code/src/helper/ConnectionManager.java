package helper;

import helper.ExpModel;
import helper.UserModel;
import helper.SongModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

//import org.apache.commons.dbutils.DbUtils;
import com.constant.ServerConstants;
import helper.StringHelper;
//import com.sun.org.apache.xpath.internal.operations.String;

public class ConnectionManager extends DBUtils {

    public static Connection getDBConnection() {
        Connection conn = null;
        try {
            System.out.println("Getting connection");
            Class.forName(ServerConstants.db_driver);
            conn = DriverManager.getConnection(ServerConstants.db_url,
                    ServerConstants.db_user, ServerConstants.db_pwd);
            System.out.println("Got Connection");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return conn;
    }

    public static UserModel checkLoginDB(HashMap parameters) {

        String uname = StringHelper.n2s(parameters.get("uname"));
        String upass = StringHelper.n2s(parameters.get("password"));
        UserModel um = null;
        String query = "select * from useraccount where uname like '" + uname
                + "' and password like '" + upass + "'";
        List l = DBUtils.getBeanList(UserModel.class, query);

        if (l != null || l.size() > 0) {
            um = (UserModel) l.get(0);
            return um;
        } else {
            return null;
        }

    }

    public static String insertUser(HashMap parameters) {

//        uid, fname, email, phone, uname, password, lname, udate
        String fname = StringHelper.n2s(parameters.get("fname"));
        String phone = StringHelper.n2s(parameters.get("phone"));
        String uname = StringHelper.n2s(parameters.get("uname"));
        String password = StringHelper.n2s(parameters.get("password"));
        String address = StringHelper.n2s(parameters.get("address"));
        String um = "";
        String query = "select * from useraccount where uname like '" + uname
                + "' and password like '" + password + "'";
        List l = DBUtils.getBeanList(UserModel.class, query);

        if (l != null || l.size() > 0) {

            String sql = "insert into  useraccount (fname, phone, uname, password, address) values(?,?,?,?,?)";
            System.out.println("sql :" + sql + " -- ");
            int rows = DBUtils.executeUpdate(sql, fname, phone, uname, password, address);
            if (rows > 0) {
                um = "1";
            } else {
                um = "Error..?? Somethings is wrong..??";
            }

        } else {
            um = "user Already Present Try diffrent UserName ";
        }
        return um;
    }

    public static void addExpDB(ArrayList a) {
        String data = "";
        if (a.size() > 0) {
            for (int i = 0; i < a.size(); i++) {
                data += (String) a.get(i) + ",";

            }
            try {
                String sql = "insert into  expression  (exp) values(?)";
                System.out.println("sql :" + sql + " -- ");
                int rows = DBUtils.executeUpdate(sql, data);
                if (rows > 0) {
//			                 System.out.println("1");
                } else {
                    System.out.println("Error..?? Somethings is wrong..??");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static HashMap<String, Integer> getEmotion() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
//           ['angry','disgust','fear','happy','sad','surprise','neutral']
        map.put("angry", 0);
        map.put("disgust", 0);
        map.put("fear", 0);
        map.put("happy", 0);
        map.put("sad", 0);
        map.put("surprise", 0);
        map.put("neutral", 0);
        return map;
    }

    public static HashMap<String, Integer> getGenres() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
//           ['party','jazz','classic','rock','pop']
        map.put("party", 0);
        map.put("jazz", 0);
        map.put("classic", 0);
        map.put("rock", 0);
        map.put("pop", 0);

        return map;
    }

    public static HashMap getMoodList() {
        String result = "";
        String sql = "select * from mooddetails";

        List list = DBUtils.getBeanList(SongModel.class, sql);
               System.out.println("Record Found : " + list.size());
               HashMap<Integer, String> hm = new HashMap<Integer, String>();
               if(list.size() > 0){
                   for(int i=0; i<list.size();i++){
                    SongModel sm = (SongModel)list.get(i);
                    hm.put(i, sm.getMood().toString());
                   }
               }
                System.out.println("Returning this hashmap : " + hm);
        return hm;
    }
     public static HashMap getGenreList() {
        String result = "";
        String sql = "select * from genredetails";

        List list = DBUtils.getBeanList(SongModel.class, sql);
               System.out.println("Record Found : " + list.size());
               HashMap<Integer, String> hm = new HashMap<Integer, String>();
               if(list.size() > 0){
                   for(int i=0; i<list.size();i++){
                    SongModel sm = (SongModel)list.get(i);
                    hm.put(i, sm.getGenre().toString());
                   }
               }
                System.out.println("Returning this hashmap : " + hm);
        return hm;
    }
    
  

   
    public static void main(String[] args) {
        getGraphicalData("2019-03-05 15:27:24", "2019-03-05 15:27:24");
    }

    public static String getGraphicalData(String startDate, String endDate) {
        HashMap<String, Integer> map = getEmotion();
        HashMap emotionMap = new HashMap();
        HashMap<String, Double> finalMap = new HashMap<String, Double>();
        String result = "";
        int faceCount = 0;
        System.out.println("Fetching Data Between :'" + startDate + "'and '" + endDate + "' ");
        String query = "SELECT * FROM `expression` where  udate between '" + startDate + "'and '" + endDate + "' ;";
        List expressionDataList = DBUtils.getBeanList(ExpModel.class, query);
        System.out.println("Record Found : " + expressionDataList.size());
        if (expressionDataList.size() > 0) {
            for (int i = 0; i < expressionDataList.size(); i++) {
                ExpModel e = (ExpModel) expressionDataList.get(i);
                String exp = e.getExp();
                String arrExp[] = exp.split(",");
                faceCount += arrExp.length;
                for (int j = 0; j < arrExp.length; j++) {
                    if (map.get(arrExp[j]) != 0) {
                        map.put(arrExp[j], map.get(arrExp[j]) + 1);
                        emotionMap.put(arrExp[j], "");
                    } else {
                        map.put(arrExp[j], 1);
                        emotionMap.put(arrExp[j], "");
                    }

                }
            }
            System.out.println("Average Face Found " + faceCount);
            System.out.println("map : " + map + "\n exps : " + emotionMap);
            double avgArray = 0.0;
            Set<String> keyset = emotionMap.keySet();
            double facecount = faceCount;
            double maxData = 0.0;
            String maxS = "";

            for (String string : keyset) {
                avgArray = (map.get(string) / facecount);
                if (avgArray > maxData) {
                    maxData = avgArray;
                    maxS = string;
                }
                result += string + "_" + avgArray + ",";
//                finalMap.put(string, avgArray);
            }
//            finalMap.put("FaceCount",facecount);
////            finalMap.put(maxS,maxData);
//            finalMap.put("Rating",maxEmotionCase(maxS));
            result += "=>" + faceCount + "," + maxS + "," + maxEmotionCase(maxS);
        } else {
            result = "0";
        }
        System.out.println("feedBack " + result);
        return result;
    }

    public static double maxEmotionCase(String maxS) {
        double result = 0.0;
        switch (maxS) {
            case "happy":
                result = 8;
                break;
            case "angry":
                result = 1;
                break;
            case "disgust":
                result = 2;
                break;
            case "fear":
                result = 4;
                break;
            case "sad":
                result = 3;
                break;
            case "surprise":
                result = 7;
                break;
            case "neutral":
                result = 6;
                break;

        }
        return result;
    }

    public static void main1(String[] args) {
        ArrayList a = new ArrayList();
        a.add("happy");
        a.add("sad");
        addExpDB(a);

    }

    public static void closeConnection(Connection conn) {
        try {
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
