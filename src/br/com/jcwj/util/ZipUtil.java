package br.com.jcwj.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.compress.utils.IOUtils;

/**
 *
 * @author julio
 */
public class ZipUtil {
       
   /**
     * Unzip it
     * @param zipFile input zip file
     * @param outputFolder zip file output folder
     */
    public static void unExeZip(String zipFile, String outputFolder){
 
     try{
    	File folder = new File(outputFolder);
    	if(!folder.exists()){
    		folder.mkdir();
    	}
 
    	File f = new File(zipFile);
        ZipFile zf = new ZipFile(f, "IBM850");
        
        for (Enumeration<ZipArchiveEntry> files = zf.getEntries(); files.hasMoreElements();)
          {
              ZipArchiveEntry zae = files.nextElement();
              String zipname = zae.getName();
              ZipArchiveEntry packinfo = zf.getEntry(zipname);
              File chemin = new File(folder+"\\"+zipname);
              if (packinfo.isDirectory())
              {
                  chemin.mkdirs();
              }
              else
              {
                  if (!chemin.getParentFile().exists()) {
                      chemin.getParentFile().mkdirs();
                  }
                  String fn = folder + File.separator + zipname;
                  FileOutputStream fos = new FileOutputStream(fn);
                  InputStream is = zf.getInputStream(packinfo);
                  IOUtils.copy(is, fos);
                  is.close();
                  fos.flush();
                  fos.close();
              }
          }

        zf.close();
 
    }catch(IOException ex){
       System.out.println("ERRO:" + ex.getMessage());
    }
   }  
}
