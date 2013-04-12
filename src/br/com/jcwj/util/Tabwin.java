package br.com.jcwj.util;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;

/**
 *
 * @author julio
 */
public class Tabwin {
    
    public static void criaPastas(String caminho) {
        File dirTabwin = new File(caminho);
        if(!dirTabwin.exists()){
            dirTabwin.mkdir();
        }
        
        File dirDownload = new File(caminho + "\\download");
        if (!dirDownload.exists()) {
            dirDownload.mkdir();
        }

        File dirDados = new File(caminho + "\\Dados");
        if (!dirDados.exists()){
            dirDados.mkdir();
        }
        
        File dirDadosSIH = new File(caminho + "\\Dados\\SIH");
        if (!dirDadosSIH.exists()){
            dirDadosSIH.mkdir();
        }
        
        File dirDadosSIA = new File(caminho + "\\Dados\\SIA");
        if (!dirDadosSIA.exists()) {
            dirDadosSIA.mkdir();
        }
        
        File dirDadosCIHA = new File(caminho + "\\Dados\\CIHA");
        if (!dirDadosCIHA.exists()){
            dirDadosCIHA.mkdir();    
        }
        
    }
    
    public static void instalaTabwin(String caminho, FTPClient ftpClient) throws IOException {
        FtpUtil.downloadArquivo(ftpClient, "/tabwin/tabwin/tab36b.zip", caminho + "\\download\\tab36b.zip");
        ZipUtil.unZip(caminho + "\\download\\tab36b.zip", caminho);

    }
    
    public static void atualizaDefinicoesSIH(String caminho, FTPClient ftpClient) throws IOException {
        FtpUtil.downloadArquivo(ftpClient, "/dissemin/publicos/SIHSUS/200801_/Auxiliar/TAB_SIH_2013-01.exe", caminho + "\\download\\TAB_SIH_2013-01.exe");
        ZipUtil.unExeZip(caminho + "\\download\\TAB_SIH_2013-01.exe", caminho + "\\SIH");
        alteraDef.alteraCaminhoDbc(caminho + "\\SIH\\RD2008.DEF", caminho + "\\Dados\\SIH\\RD*.DBC");
    }
    
    public static void atualizaDefinicoesSIA(String caminho, FTPClient ftpClient) throws IOException {
        FtpUtil.downloadArquivo(ftpClient, "/dissemin/publicos/siasus/200801_/Auxiliar/TAB_SIA_2013-02.exe", caminho + "\\download\\TAB_SIA_2013-02.exe");
        ZipUtil.unExeZip(caminho + "\\download\\TAB_SIA_2013-02.exe", caminho + "\\SIA");
        alteraDef.alteraCaminhoDbc(caminho + "\\SIA\\Produção_2008.DEF", caminho + "\\Dados\\SIA\\PA*.DBC");
    }
    
    public static void atualizaDefinicoesCIHA(String caminho, FTPClient ftpClient) throws IOException {
        FtpUtil.downloadArquivo(ftpClient, "/dissemin/publicos/CIHA/201101_/Auxiliar/tab_ciha_201302.exe", caminho + "\\download\\tab_ciha_201302.exe");
        ZipUtil.unExeZip(caminho + "\\download\\tab_ciha_201302.exe", caminho + "\\CIHA");
        alteraDef.alteraCaminhoDbc(caminho + "\\CIHA\\CIHA.DEF", caminho + "\\Dados\\CIHA\\CIHA*.DBC");
        
    }
    
    public static void downloadDados(FTPClient ftpClient, String dirRemoto, String dirLocal, final String regexArq) throws IOException{
        
        ftpClient.changeWorkingDirectory(dirRemoto);
        FTPFileFilter ff = new FTPFileFilter() {

                @Override
                public boolean accept(FTPFile file) {
                    return Pattern.matches(regexArq, file.getName());
                }
            };
        FTPFile[] arquivos = ftpClient.listFiles(dirRemoto, ff);  
            for( int i=0; i < arquivos.length; i++ ) {  
              //System.out.println( arquivos[i].getName() + " / " + arquivos[i].getSize() + " / " + String.format("%1$td/%1$tm/%1$tY %1$tH:%1$tM", arquivos[i].getTimestamp()) );  
              FtpUtil.downloadArquivo(ftpClient, arquivos[i].getName(), dirLocal + arquivos[i].getName());
              FtpUtil.setaDataHoraArquivo(dirLocal + arquivos[i].getName(), arquivos[i].getTimestamp());
            }  
        
    }
    
}
