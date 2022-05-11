/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javacvtesting;

import org.bytedeco.javacpp.*;
import static org.bytedeco.javacpp.lept.*;
import static org.bytedeco.javacpp.tesseract.*;

/**
 *
 * @author technowings
 */
public class TesseractExample {
    
    public static void main(String[] args) {
        
        BytePointer outText;
//        System.setProperty("TESSDATA_PREFIX", "E:\\work\\big-project\\tessdata-master\\tessdata-master\\");
        TessBaseAPI api = new TessBaseAPI();
        // Initialize tesseract-ocr with English, without specifying tessdata path
        if (api.Init(null, "eng") != 0) {
            System.err.println("Could not initialize tesseract.");
            System.exit(1);
        }

        // Open input image with leptonica library
        PIX image = pixRead( "C:\\Users\\rajesh\\Pictures\\Screenshots\\Screenshot (79).png");
        api.SetImage(image);
        // Get OCR result
        outText = api.GetUTF8Text();
        System.out.println("OCR output:\n" + outText.getString());

        // Destroy used object and release memory
        api.End();
        outText.deallocate();
        pixDestroy(image);
    }
}
