package br.com.jcwj.testes;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.io.output.CountingOutputStream;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 *
 * @author julio
 */
public class Teste {
    
    public static void main(String[] args) throws FileNotFoundException {
        try {
            //Tabwin.atualizaDefinicoesSIA();
            //ZipUtil.unExeZip("c:\\Tabwin\\download\\TAB_SIA_2013-02.exe", "C:\\Tabwin\\SIA");
            //String diretorioUsuario = System.getProperty("user.home");
            //String diretorioApp = System.getProperty("user.dir");
            //System.out.println("Home: " + diretorioUsuario);
            //System.out.println("Home: " + diretorioApp);
            //System.getProperties().list(System.out);
            FTPClient ftp = new FTPClient();
            int reply;
            try {
                ftp.connect("ftp.datasus.gov.br");
                //ftp.connect("ftp.datasus.gov.br");
                //ftp.connect("msbbs.datasus.gov.br"); 
                reply = ftp.getReplyCode();
                if (!FTPReply.isPositiveCompletion(reply))
                {
                    ftp.disconnect();
                    System.err.println("FTP server refused connection.");
                    System.exit(1);
                }
                if (!ftp.login("anonymous", "tabwin@datasus.gov.br"))
                {
                    ftp.logout();
                    ftp.disconnect();
                    System.err.println("Erro no Login");
                    System.exit(1);
                }
            } catch (Exception e) {
                    if (ftp.isConnected()){
                        try {
                            ftp.disconnect();
                        }
                        catch (IOException f) {
                            System.err.println("Erro na conexão com o FTP");
                        }
                    }
                    System.err.println("Ocorreu um erro: " + e);
                    System.exit(2);
            }
            File downloadFile = new File("C:\\Tabwin\\download\\tabwin36b.zip");
            OutputStream outputStream = new BufferedOutputStream(
                new FileOutputStream(downloadFile));
            
            CountingOutputStream cos = new CountingOutputStream(outputStream){
                @Override
                protected void beforeWrite(int n){
                    super.beforeWrite(n);
                    System.out.println("Downloaded " + getCount());
            }
            };
            
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            
            boolean teste;
            teste = ftp.retrieveFile("/tabwin/tabwin/tab36b.zip", cos);
            if (teste) {
               System.out.println("OK");
            } else {
               System.out.println("FALHOU");
            }
            
            
            outputStream.close();
        } catch (IOException ex) {
            System.out.println("ERRO");
        }
       
    }
}