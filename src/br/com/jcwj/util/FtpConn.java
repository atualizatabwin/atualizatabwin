package br.com.jcwj.util;

import java.io.IOException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 *
 * @author julio
 */
public class FtpConn {
    
    private FTPClient ftp;
    
    public FTPClient getConn(String urlFtp) {
        
        this.ftp = new FTPClient();
        int reply;
        try {
            ftp.connect(urlFtp);
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
        ftp.enterLocalPassiveMode();
        ftp.setControlKeepAliveTimeout(300);
        return ftp;
    }
    
}
