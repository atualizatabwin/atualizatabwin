package br.com.jcwj.UI;

import br.com.jcwj.util.ConfigAtualizacao;
import br.com.jcwj.util.FtpConn;
import br.com.jcwj.util.Tabwin;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author julio
 * Poderia usar "implements PropertyChangeListener"
 */
public class AtualizaWorker extends SwingWorker<String, String>  {
    
    private static void failIfInterrupted() throws InterruptedException {
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException("Atualização Interrompida.");
        }
    }

    private final JTextArea log;
    private final ConfigAtualizacao config;
    private final JLabel downArq;
    
    /**
     *
     * @param log
     */
    public AtualizaWorker(final JTextArea log, final ConfigAtualizacao config, JLabel downArq){
        this.log = log;
        this.config = config;
        this.downArq = downArq;
    }
    
    public void aMsg(String msg, int bytes){
        downArq.setText(msg);
        firePropertyChange("fileProgress",(Integer) 0,(Integer) bytes);
    }
    
    @Override
    protected String doInBackground() throws Exception {
        
        String pathTabwin = config.getPathTabwin();
        String ufDados = config.getUfDados();
        String ufDadosRegex = config.getUfDadosRegex();
                
        publish("Conectando ao FTP: ftp.datasus.gov.br...");
        FTPClient ftp = new FtpConn().getConn("ftp.datasus.gov.br");
        publish("OK \n");
        setProgress(5);
        AtualizaWorker.failIfInterrupted();
        
        Tabwin.criaPastas(pathTabwin);
        setProgress(10);
        
        if (config.isAtuTabwin()) {
            publish("Instalando Aplicativo Tabwin em : " + pathTabwin + "...");
            if (Tabwin.instalaTabwin(pathTabwin, ftp, this)) {
                publish("OK \n");
            } else {
                publish("ERRO \n");
            }
        }
        setProgress(20);
        
        if (config.isAtuSIH()) {
            publish("Atualizando Definições SIH...");
            Tabwin.atualizaDefinicoesSIH(pathTabwin, ftp, this);
            publish("OK \n");
        }
        setProgress(30);
        
        if (config.isAtuSIA()) {
            publish("Atualizando Definições SIA...");
            Tabwin.atualizaDefinicoesSIA(pathTabwin, ftp, this);
            publish("OK \n");
        }
        setProgress(40);
        
        if (config.isAtuCIHA()) {
            publish("Atualizando Definições CIHA...");
            Tabwin.atualizaDefinicoesCIHA(pathTabwin, ftp, this);
            publish("OK \n");
        }
        setProgress(50);
        
        int i;       
        
        List<String> dadosSIH = config.getDadosSIH();
        for (i=0; i < dadosSIH.size(); i++) {
            publish("Atualizando Dados SIH do estado: " + ufDados + " do ano: 20" + dadosSIH.get(i) + "...");
            Tabwin.downloadDados(ftp, config.isVerDataFtp(), "/dissemin/publicos/SIHSUS/200801_/Dados", pathTabwin + "\\Dados\\SIH\\", "[Rr][Dd]" + ufDadosRegex + dadosSIH.get(i) + "[0-9]{2}.[Dd][Bb][Cc]", this);
            publish("OK \n");
        }
        
        setProgress(65);
        
        List<String> dadosSIA = config.getDadosSIA();
        for (i=0; i < dadosSIA.size(); i++) {
            publish("Atualizando Dados SIA do estado: " + ufDados + " do ano: 20" + dadosSIA.get(i) + "...");
            Tabwin.downloadDados(ftp, config.isVerDataFtp(), "/dissemin/publicos/SIASUS/200801_/Dados", pathTabwin + "\\Dados\\SIA\\", "PA" + ufDadosRegex + dadosSIA.get(i) + "[0-9]{2}.[Dd][Bb][Cc]", this);
            publish("OK \n");
        }
        setProgress(75);
        
        List<String> dadosCIHA = config.getDadosCIHA();
        for (i=0; i < dadosCIHA.size(); i++) {
            publish("Atualizando Dados CIHA do estado: " + ufDados + " do ano: 20" + dadosCIHA.get(i) + "...");
            Tabwin.downloadDados(ftp, config.isVerDataFtp(), "/dissemin/publicos/CIHA/201101_/Dados", pathTabwin + "\\Dados\\CIHA\\", "CIHA" + ufDadosRegex + dadosCIHA.get(i) + "[0-9]{2}.[Dd][Bb][Cc]", this);
            publish("OK \n");
        }
        setProgress(85);
        
        publish("Desconectando do FTP: ftp.datasus.gov.br...");
        ftp.logout();
        ftp.disconnect();
        publish("OK \n");
        setProgress(100);
        
        return "Sucesso";
        
    }
    
    @Override
    protected void process(final List<String> chunks) {
        // Messages received from the doInBackground() (when invoking the publish() method)
        for (final String msgLog : chunks) {
            log.append(msgLog);
            //log.append("\n");
        }
    }
    
    @Override
    protected void done() {
        try {
            log.append(get());
        } catch (InterruptedException ex) {
            Logger.getLogger(AtualizaWorker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(AtualizaWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
