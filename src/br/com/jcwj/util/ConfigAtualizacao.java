package br.com.jcwj.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author julio
 */
public class ConfigAtualizacao {

    private String pathTabwin;
    private String ufDados;
    private boolean atuTabwin;
    private boolean atuSIH;
    private boolean atuSIA;
    private boolean atuCIHA;
    
    private List<String> dadosSIH;
    private List<String> dadosSIA;
    private List<String> dadosCIHA;
    
    public ConfigAtualizacao() {
        this.dadosSIH = new ArrayList();
        this.dadosSIA = new ArrayList();
        this.dadosCIHA = new ArrayList();
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
        this.dadosSIH.add(ano);
    }
    
    public void addDadosSIA(String ano) {
        this.dadosSIA.add(ano);
    }
    
    public void addDadosCIHA(String ano) {
        this.dadosCIHA.add(ano);
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
    
}
