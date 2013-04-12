package br.com.jcwj.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 *
 * @author julio
 */
public class alteraDef {
    
    public static void alteraCaminhoDbc(String arqDef, String caminhoDbc) {
        try {
            FileInputStream fstream;
            fstream = new FileInputStream(arqDef);
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "ISO8859_1"));

            String strLine;
            StringBuilder fileContent = new StringBuilder();
            
            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                // Print the content on the console
                //System.out.println(strLine);
                
                if (strLine.startsWith("A")) {
                    String newLine = "A" + caminhoDbc;
                    fileContent.append(newLine);
                    fileContent.append("\r\n");
                } else {
                    // update content as it is
                    fileContent.append(strLine);
                    fileContent.append("\r\n");
                }
             }
            in.close();
            
            // Now fileContent will have updated content , which you can override into file
            FileOutputStream fileOut;
            fileOut = new FileOutputStream(arqDef);
            Writer out = new OutputStreamWriter(fileOut, "ISO8859_1");
            out.write(fileContent.toString());
            out.flush();
            out.close();
            
            
        
        } catch (Exception ex) {
            System.err.println("Error: " + ex.getMessage());
        }
    }
}
