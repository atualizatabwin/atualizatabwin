package br.com.jcwj.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.utils.IOUtils;

/**
 *
 * @author julio
 */
public class ZipUtil {
    /**
     * Unzip it
     * @param zipFile input zip file
     * @param output zip file output folder
     */
    public static void unZip(String zipFile, String outputFolder){
 
     byte[] buffer = new byte[1024];
 
     try{
 
    	//create output directory is not exists
    	File folder = new File(outputFolder);
    	if(!folder.exists()){
    		folder.mkdir();
    	}
 
    	//get the zip file content
    	ZipInputStream zis = 
    		new ZipInputStream(new FileInputStream(zipFile));
    	//get the zipped file list entry
    	ZipEntry ze = zis.getNextEntry();
 
    	while(ze!=null){
 
    	   String fileName = ze.getName();
           File newFile = new File(outputFolder + File.separator + fileName);
 
           //System.out.println("file unzip : "+ newFile.getAbsoluteFile());
 
            //create all non exists folders
            //else you will hit FileNotFoundException for compressed folder
            new File(newFile.getParent()).mkdirs();
 
            FileOutputStream fos = new FileOutputStream(newFile);             
 
            int len;
            while ((len = zis.read(buffer)) > 0) {
       		fos.write(buffer, 0, len);
            }
 
            fos.close();   
            ze = zis.getNextEntry();
    	}
 
        zis.closeEntry();
    	zis.close();

     }catch(IOException ex){
       //ex.printStackTrace(); 
       System.out.println("ERRO");
    }
   }  
    
   /**
     * Unzip it
     * @param zipFile input zip file
     * @param output zip file output folder
     */
    public static void unExeZip(String zipFile, String outputFolder){
 
     try{
 
    	//create output directory is not exists
    	File folder = new File(outputFolder);
    	if(!folder.exists()){
    		folder.mkdir();
    	}
 
    	File f = new File(zipFile);
        org.apache.commons.compress.archivers.zip.ZipFile zf = new
        org.apache.commons.compress.archivers.zip.ZipFile(f, "IBM850");
        
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
