package br.com.jcwj.util;

import br.com.jcwj.UI.AtualizaWorker;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.CountingOutputStream;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

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
    
    /**
    * Faz Download de um arquivo
    * @param ftpClient an instance of org.apache.commons.net.ftp.FTPClient class.
    * @param arquivoRemoto Caminho completo do arquivo remoto
    * @param arquivoLocal Caminho completo do arquivo local
    * @throws IOException if any network or IO error occurred.
    */
    public static boolean downloadArquivo(FTPClient ftpClient, String arquivoRemoto,
        final String arquivoLocal, final AtualizaWorker work) throws IOException {
        
        final int totBytesArq;
        final String nomeArq;
        FTPFile[] arqRem = ftpClient.listFiles(arquivoRemoto);
        totBytesArq = (int) arqRem[0].getSize();

        File downloadFile = new File(arquivoLocal);
        nomeArq = downloadFile.getName();
        work.aMsg(nomeArq + " - 0/" + totBytesArq, 0);
        final long start = System.nanoTime();
        OutputStream outputStream = new BufferedOutputStream(
            new FileOutputStream(downloadFile));
        
        CountingOutputStream cos = new CountingOutputStream(outputStream){
            @Override
            protected void beforeWrite(int n){
                super.beforeWrite(n);
                double speed = 1000000000.0 * getCount() / (System.nanoTime() - start + 1);
                work.aMsg(nomeArq + " - " + getCount() + "/" + totBytesArq + " (" +FileUtils.byteCountToDisplaySize((long) speed) + "/s)" , Math.round(((float)getCount() / totBytesArq)*100));
            }
        };
        
        
        try {
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            return ftpClient.retrieveFile(arquivoRemoto, cos);
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
