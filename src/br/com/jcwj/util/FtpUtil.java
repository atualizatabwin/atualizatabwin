package br.com.jcwj.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author julio
 */
public class FtpUtil {
    
    /**
    * Faz Download de um arquivo
    * @param ftpClient an instance of org.apache.commons.net.ftp.FTPClient class.
    * @param arquivoRemoto Caminho completo do arquivo remoto
    * @param arquivoLocal Caminho completo do arquivo local
    * @throws IOException if any network or IO error occurred.
    */
    public static boolean downloadArquivo(FTPClient ftpClient, String arquivoRemoto,
        String arquivoLocal) throws IOException {
        
        File downloadFile = new File(arquivoLocal);
        OutputStream outputStream = new BufferedOutputStream(
            new FileOutputStream(downloadFile));
        
        //CountingOutputStream cos = new CountingOutputStream(outputStream){
        //    @Override
        //    protected void beforeWrite(int n){
        //        super.beforeWrite(n);
        //        System.out.println("Downloaded " + getCount());
        //}
        //};
        
        try {
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            
            return ftpClient.retrieveFile(arquivoRemoto, outputStream);
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }
    
    public static boolean setaDataHoraArquivo(String arquivoLocal, Calendar dataHora ){
        File arquivo = new File(arquivoLocal);
        return arquivo.setLastModified(dataHora.getTimeInMillis());
    }
    
    
}
