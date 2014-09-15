package br.com.jcwj.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author julio
 */
public class ConfigAtualizacao {
    
    Properties properties;

    private String pathTabwin;
    private String ufDados;
    private String ufDadosRegex;
    private boolean atuTabwin;
    private boolean atuSIH;
    private boolean atuSIA;
    private boolean atuCIHA;
    private boolean usarMsbbsSih;
    private boolean verDataFtp;
    
    private List<String> dadosSIH;
    private List<String> dadosSIA;
    private List<String> dadosCIHA;
    
    private final String[] listaEstados;
    private final String[] listaEstadosRegex;
    private int estadoSelecionado;
    
    private final String diretorioUsuario;
    private final String arquivoConfig;
    
    public ConfigAtualizacao() {
        this.dadosSIH = new ArrayList<String>();
        this.dadosSIA = new ArrayList<String>();
        this.dadosCIHA = new ArrayList<String>();
        
        this.listaEstados = new String[]{"AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RR", "RS", "SC", "SE", "SP", "TO"};
        this.listaEstadosRegex = new String[]{"[Aa][Cc]", "[Aa][Ll]", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RR", "RS", "[Ss][Cc]", "SE", "SP", "TO"};
       
        this.diretorioUsuario = System.getProperty("user.home");
        this.arquivoConfig = diretorioUsuario + "\\atualizatabwin.cfg";
    }
    
    public boolean temOpcaoSelecionada() {
        
        if (atuTabwin || atuSIH || atuSIA || atuCIHA ||
                dadosSIH.size() > 0 || dadosSIA.size() > 0 || 
                dadosCIHA.size() > 0) {
            return true;
        } else {
            return false;
        }
        
    }
    
    public void salvarConfiguracao(){
        properties = new Properties();
        
        properties.setProperty("ufDados", ufDados);
        properties.setProperty("pathTabwin", pathTabwin);
        properties.setProperty("atuTabwin", Boolean.toString(atuTabwin));
        properties.setProperty("atuSIH", Boolean.toString(atuSIH));
        properties.setProperty("atuSIA", Boolean.toString(atuSIA));
        properties.setProperty("atuCIHA", Boolean.toString(atuCIHA));
        properties.setProperty("usarMsbbsSih", Boolean.toString(usarMsbbsSih));
        properties.setProperty("verDataFtp", Boolean.toString(verDataFtp));
        properties.setProperty("estadoSelecionado", Integer.toString(estadoSelecionado));
        properties.setProperty("anosSIH", StringUtils.join(dadosSIH, "|"));
        properties.setProperty("anosSIA", StringUtils.join(dadosSIA, "|"));
        properties.setProperty("anosCIHA", StringUtils.join(dadosCIHA, "|"));

        try {
            FileOutputStream fos = new FileOutputStream(arquivoConfig);
            properties.store(fos, "CONFIG Atualiza Tabwin");
            fos.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void carregarConfiguracao(){
        properties = new Properties();
 
        try {
            FileInputStream fis = new FileInputStream(arquivoConfig);
            properties.load(fis);
        } catch (IOException e) {
            System.out.println("Arquivo de configuracao nao encontrado");
        }        
        
        this.ufDados = properties.getProperty("ufDados", "SC");
        this.pathTabwin = properties.getProperty("pathTabwin", "C:\\Tabwin");
        this.atuTabwin = Boolean.parseBoolean(properties.getProperty("atuTabwin", "true"));
        this.atuSIH = Boolean.parseBoolean(properties.getProperty("atuSIH", "true"));
        this.atuSIA = Boolean.parseBoolean(properties.getProperty("atuSIA", "true"));
        this.atuCIHA = Boolean.parseBoolean(properties.getProperty("atuCIHA", "true"));
        this.usarMsbbsSih = Boolean.parseBoolean(properties.getProperty("usarMsbbsSih", "false"));
        this.verDataFtp = Boolean.parseBoolean(properties.getProperty("verDataFtp", "false"));
        this.estadoSelecionado = Integer.parseInt(properties.getProperty("estadoSelecionado", "23"));
        this.dadosSIH = new ArrayList<String>(Arrays.asList(StringUtils.split(properties.getProperty("anosSIH", "12|13"), "|")));
        this.dadosSIA = new ArrayList<String>(Arrays.asList(StringUtils.split(properties.getProperty("anosSIA", "12|13"), "|")));
        this.dadosCIHA = new ArrayList<String>(Arrays.asList(StringUtils.split(properties.getProperty("anosCIHA", "12|13"), "|")));

    }
    
    public String[] getEstados() {
        return listaEstados;
    }
    
    public String[] getEstadosRegex() {
        return listaEstadosRegex;
    }
    
    public void setEstadoSelecionado(int estadoSelecionado){
        this.estadoSelecionado = estadoSelecionado;
    }
    
    public int getEstadoSelecionado() {
        return estadoSelecionado;
    }

    public List<String> getDadosSIH() {
        return dadosSIH;
    }
    
    public List<String> getDadosSIA() {
        return dadosSIA;
    }
    
    public List<String> getDadosCIHA() {
        return dadosCIHA;
    }
    
    public void addDadosSIH(String ano) {
        if (!dadosSIH.contains(ano)) {
            this.dadosSIH.add(ano);            
        }

    }
    
    public void delDadosSIH(String ano) {
        if (dadosSIH.contains(ano)) {
            dadosSIH.remove(ano);
        }

    }
    
    public void addDadosSIA(String ano) {
        if (!dadosSIA.contains(ano)) {
            this.dadosSIA.add(ano);
        }
    }
    
    public void delDadosSIA(String ano) {
        if (dadosSIA.contains(ano)) {
            this.dadosSIA.remove(ano);
        }
    }
    
    public void addDadosCIHA(String ano) {
        if (!dadosCIHA.contains(ano)) {
            this.dadosCIHA.add(ano);
        }
    }
    
    public void delDadosCIHA(String ano) {
        if (dadosCIHA.contains(ano)) {
            this.dadosCIHA.remove(ano);
        }
    }

    public String getPathTabwin() {
        return pathTabwin;
    }

    public void setPathTabwin(String pathTabwin) {
        this.pathTabwin = pathTabwin;
    }

    public String getUfDados() {
        return ufDados;
    }

    public void setUfDados(String ufDados) {
        this.ufDados = ufDados;
    }

    public String getUfDadosRegex() {
        return ufDadosRegex;
    }

    public void setUfDadosRegex(String ufDadosRegex) {
        this.ufDadosRegex = ufDadosRegex;
    }
    
    public boolean isAtuTabwin() {
        return atuTabwin;
    }

    public void setAtuTabwin(boolean atuTabwin) {
        this.atuTabwin = atuTabwin;
    }

    public boolean isAtuSIH() {
        return atuSIH;
    }

    public void setAtuSIH(boolean atuSIH) {
        this.atuSIH = atuSIH;
    }

    public boolean isAtuSIA() {
        return atuSIA;
    }

    public void setAtuSIA(boolean atuSIA) {
        this.atuSIA = atuSIA;
    }

    public boolean isAtuCIHA() {
        return atuCIHA;
    }

    public void setAtuCIHA(boolean atuCIHA) {
        this.atuCIHA = atuCIHA;
    }
    
    public boolean isUsarMsbbsSih() {
        return usarMsbbsSih;
    }

    public void setUsarMsbbsSih(boolean usarMsbbsSih) {
        this.usarMsbbsSih = usarMsbbsSih;
    }

    public boolean isVerDataFtp() {
        return verDataFtp;
    }

    public void setVerDataFtp(boolean verDataFtp) {
        this.verDataFtp = verDataFtp;
    }
     
}
