package helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import org.json.JSONObject;

import com.constant.ServerConstants;

import java.io.InputStreamReader;

public class OsHelper {

public StringBuffer getCommandOutput(String cmd, String workingDir, int skipLine) {
    try {
    	//com.mysql.jdbc.Class.releaseHeapMemory();
//        System.out.println("CMD : " + cmd);

        Process p = Runtime.getRuntime().exec(cmd, null, new File(workingDir));
        return getProcessOutput(p, skipLine);
    } catch (IOException ex) {
//        Logger.getLogger(ExecuteDOSCommand.class.getName()).log(Level.SEVERE, null, ex);
        ex.printStackTrace();
    }
    return new StringBuffer();
}
public StringBuffer getProcessOutput(Process p, int skipLine) {

    StringBuffer sb = new StringBuffer();

    try {

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(
                p.getInputStream()));

        BufferedReader stdError = new BufferedReader(new InputStreamReader(
                p.getErrorStream()));

        // read the output from the command
        String s = null;
 
        for (int i = 0; i < skipLine; i++) {
            s = stdInput.readLine();
//            System.out.println("skipline o/p:" + s);
        }
        String prev = "";
        while ((s = stdInput.readLine()) != null) {
            if (s.trim().length() > 0) {
//                s = s.replace("\\", "");
                sb.append(s);
 
                if (prev.indexOf("Evaluation:") > -1) {
               }
                prev = s;
//                System.out.println("Command Output " + s);
                sb.append('\n');
            }
        }
        while ((s = stdError.readLine()) != null) {
            if (s.trim().length() > 0) {
                s = s.replace("\\", "");
                sb.append(s);
//                System.out.println("Error Output " + s);
                sb.append('\n');
            }
        }
    } catch (NullPointerException e) {
        System.out.println("I am in null catch");
        e.printStackTrace();
    } catch (Exception e) {
        System.out.println("I am in catch");
        e.printStackTrace();
    }

    return sb;
}

}
