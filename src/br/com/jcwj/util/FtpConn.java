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
    
    public FTPClient getConn() {
        
        this.ftp = new FTPClient();
        int reply;
        try {
            ftp.connect("ftp.datasus.gov.br");
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
                    // do nothing
                    }
                }
		System.err.println("Ocorreu um erro: " + e);
		System.exit(2);
        }
        
        return ftp;
    }
    
}
