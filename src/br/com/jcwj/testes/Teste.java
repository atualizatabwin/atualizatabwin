package br.com.jcwj.testes;

import br.com.jcwj.util.Tabwin;
import br.com.jcwj.util.ZipUtil;

/**
 *
 * @author julio
 */
public class Teste {
    
    public static void main(String[] args) {
        //Tabwin.atualizaDefinicoesSIA();
        //ZipUtil.unExeZip("c:\\Tabwin\\download\\TAB_SIA_2013-02.exe", "C:\\Tabwin\\SIA");
        String diretorioUsuario = System.getProperty("user.home");
        String diretorioApp = System.getProperty("user.dir");
        
        System.out.println("Home: " + diretorioUsuario);
        System.out.println("Home: " + diretorioApp);
        
        System.getProperties().list(System.out);
        
    }
}