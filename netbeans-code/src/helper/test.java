/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.io.File;

/**
 *
 * @author Technowings
 */
public class test {
    public static void main(String[] args) {
                File[] folderFiles = FileHelper.getFileList("D:\\temp\\Angry", "png");
                File file = folderFiles[0];
        System.out.println("fileaname = "+file.getName());
    }
}
