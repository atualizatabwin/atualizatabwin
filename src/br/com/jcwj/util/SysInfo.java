package br.com.jcwj.util;

public class SysInfo {
    
    public static final String nomeComputador() {
        return System.getenv("COMPUTERNAME");
    }
    
    public static final String nomeUsuario() {
        return System.getProperty("user.name");
    }
    
    public static final String sistemaOperacional() {
        return System.getProperty("os.name");
    }
    
    public static final String versaoJava() {
        return System.getProperty("java.version");
    }
    
}
