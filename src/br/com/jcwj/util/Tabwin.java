package br.com.jcwj.util;

import br.com.jcwj.UI.AtualizaWorker;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.joda.time.DateTime;

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
    
    public static boolean instalaTabwin(String caminho, FTPClient ftpClient, AtualizaWorker work) throws IOException {
        boolean downArq;
        downArq = FtpUtil.downloadArquivo(ftpClient, "/tabwin/tabwin/tab36b.zip", caminho + "\\download\\tab36b.zip", work);    
        ZipUtil.unZip(caminho + "\\download\\tab36b.zip", caminho);
        return downArq;
    }
    
    public static void atualizaDefinicoesSIH(String caminho, FTPClient ftpClient, AtualizaWorker work) throws IOException {
        
        String dirRemoto = "/dissemin/publicos/SIHSUS/200801_/Auxiliar/";
        String dirLocal = caminho + "\\download\\";
        String nomeArq;
        ftpClient.changeWorkingDirectory(dirRemoto);
        
        FTPFileFilter ff = new FTPFileFilter() {
                @Override
                public boolean accept(FTPFile file) {
                    return Pattern.matches("[Tt][Aa][Bb]_[Ss][Ii][Hh]_[0-9]{4}[-_]?[0-9]{2}.[Ee][Xx][Ee]", file.getName());
                }
            };
        FTPFile[] arquivos = ftpClient.listFiles(dirRemoto, ff);  
        for( int i=0; i < arquivos.length; i++ ) {
            nomeArq = arquivos[i].getName();
            FtpUtil.downloadArquivo(ftpClient, nomeArq, dirLocal + nomeArq, work);
            ZipUtil.unExeZip(dirLocal + nomeArq, caminho + "\\SIH");
        }
        //FtpUtil.downloadArquivo(ftpClient, "/dissemin/publicos/SIHSUS/200801_/Auxiliar/TAB_SIH_2013-01.exe", caminho + "\\download\\TAB_SIH_2013-01.exe", work);
        //ZipUtil.unExeZip(caminho + "\\download\\TAB_SIH_2013-01.exe", caminho + "\\SIH");
        alteraDef.alteraCaminhoDbc(caminho + "\\SIH\\RD2008.DEF", caminho + "\\Dados\\SIH\\RD*.DBC");
    }
    
    public static void atualizaDefinicoesSIA(String caminho, FTPClient ftpClient, AtualizaWorker work) throws IOException {
        
        String dirRemoto = "/dissemin/publicos/siasus/200801_/Auxiliar/";
        String dirLocal = caminho + "\\download\\";
        String nomeArq;
        ftpClient.changeWorkingDirectory(dirRemoto);
        
        FTPFileFilter ff = new FTPFileFilter() {
                @Override
                public boolean accept(FTPFile file) {
                    return Pattern.matches("[Tt][Aa][Bb]_[Ss][Ii][Aa]_[0-9]{4}[-_]?[0-9]{2}.[Ee][Xx][Ee]", file.getName());
                }
            };
        FTPFile[] arquivos = ftpClient.listFiles(dirRemoto, ff);
        for( int i=0; i < arquivos.length; i++ ) {
            nomeArq = arquivos[i].getName();
            FtpUtil.downloadArquivo(ftpClient, nomeArq, dirLocal + nomeArq, work);
            ZipUtil.unExeZip(dirLocal + nomeArq, caminho + "\\SIA");
        }
        //FtpUtil.downloadArquivo(ftpClient, "/dissemin/publicos/siasus/200801_/Auxiliar/TAB_SIA_2013-02.exe", caminho + "\\download\\TAB_SIA_2013-02.exe", work);
        //ZipUtil.unExeZip(caminho + "\\download\\TAB_SIA_2013-02.exe", caminho + "\\SIA");
        alteraDef.alteraCaminhoDbc(caminho + "\\SIA\\Produção_2008.DEF", caminho + "\\Dados\\SIA\\PA*.DBC");
    }
    
    public static void atualizaDefinicoesCIHA(String caminho, FTPClient ftpClient, AtualizaWorker work) throws IOException {
        
        String dirRemoto = "/dissemin/publicos/CIHA/201101_/Auxiliar/";
        String dirLocal = caminho + "\\download\\";
        String nomeArq;
        ftpClient.changeWorkingDirectory(dirRemoto);
        
        FTPFileFilter ff = new FTPFileFilter() {
                @Override
                public boolean accept(FTPFile file) {
                    return Pattern.matches("[Tt][Aa][Bb]_[Cc][Ii][Hh][Aa]_[0-9]{4}[-_]?[0-9]{2}.[Ee][Xx][Ee]", file.getName());
                }
            };
        FTPFile[] arquivos = ftpClient.listFiles(dirRemoto, ff);
        for( int i=0; i < arquivos.length; i++ ) {
            nomeArq = arquivos[i].getName();
            FtpUtil.downloadArquivo(ftpClient, nomeArq, dirLocal + nomeArq, work);
            ZipUtil.unExeZip(dirLocal + nomeArq, caminho + "\\CIHA");
        }
        //FtpUtil.downloadArquivo(ftpClient, "/dissemin/publicos/CIHA/201101_/Auxiliar/tab_ciha_201304.exe", caminho + "\\download\\tab_ciha_201304.exe", work);
        //ZipUtil.unExeZip(caminho + "\\download\\tab_ciha_201304.exe", caminho + "\\CIHA");
        alteraDef.alteraCaminhoDbc(caminho + "\\CIHA\\CIHA.DEF", caminho + "\\Dados\\CIHA\\CIHA*.DBC");
        
    }
    
    public static void downloadDados(FTPClient ftpClient, boolean verData, String dirRemoto, String dirLocal, final String regexArq, AtualizaWorker work) throws IOException{
        
        ftpClient.changeWorkingDirectory(dirRemoto);
        FTPFileFilter ff = new FTPFileFilter() {

                @Override
                public boolean accept(FTPFile file) {
                    return Pattern.matches(regexArq, file.getName());
                }
            };
        FTPFile[] arquivos = ftpClient.listFiles(dirRemoto, ff);  
            for( int i=0; i < arquivos.length; i++ ) {
                DateTime dataRemoto = new DateTime(arquivos[i].getTimestamp());

                File arqDados = new File(dirLocal + arquivos[i].getName());
                if(arqDados.exists() && verData){
                    DateTime dataLocal = new DateTime(arqDados.lastModified());
                    if (dataRemoto.isAfter(dataLocal)) {
                        System.out.println("O arquivo remoto é mais novo");
                        FtpUtil.downloadArquivo(ftpClient, arquivos[i].getName(), dirLocal + arquivos[i].getName(), work);
                        FtpUtil.setaDataHoraArquivo(dirLocal + arquivos[i].getName(), arquivos[i].getTimestamp());
                    } else {
                        System.out.println("Arquivo já esta atualizado");
                    }
                } else {
                    //System.out.println( arquivos[i].getName() + " / " + arquivos[i].getSize() + " / " + String.format("%1$td/%1$tm/%1$tY %1$tH:%1$tM", arquivos[i].getTimestamp()) );  
                    FtpUtil.downloadArquivo(ftpClient, arquivos[i].getName(), dirLocal + arquivos[i].getName(), work);
                    FtpUtil.setaDataHoraArquivo(dirLocal + arquivos[i].getName(), arquivos[i].getTimestamp());
                }
            }  
        
    }
    
}
