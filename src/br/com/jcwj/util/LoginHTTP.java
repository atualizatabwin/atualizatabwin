package br.com.jcwj.util;

import com.google.common.io.CharStreams;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author julio
 */
public class LoginHTTP  {

    public String login(String cnpj, String senha) throws ServidorIndisponivelException {
        
        String urlGet = "http://www.atualizatabwin.com.br/applogin";
        String charset = "UTF-8";
        String params = null;
        HttpURLConnection connection = null; 
        
        try {
            params = String.format("cnpj=%s&senha=%s",
                    URLEncoder.encode(cnpj, charset), 
                    URLEncoder.encode(senha, charset));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(LoginHTTP.class.getName()).log(Level.SEVERE, null, ex);
        }
                       
        try {
            URL url = new URL(urlGet);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestProperty("Accept-Charset", charset);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
            connection.setRequestProperty("User-Agent", "AtualizaTabwin/1.0");
            connection.setRequestMethod("POST");
            
            try (OutputStream output = connection.getOutputStream()) {
                output.write(params.getBytes(charset));
            }
            
            for (Entry<String, List<String>> header : connection.getHeaderFields().entrySet()) {
                System.out.println(header.getKey() + "=" + header.getValue());
            }
                
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String retorno = CharStreams.toString(reader);
                reader.close();
                return retorno;      
            } else {
                //InputStream error = ((HttpURLConnection) connection).getErrorStream();
                System.out.println(connection.getResponseCode());
                System.out.println(connection.getResponseMessage());
                System.out.println("Retorno de Erro");
                return "ERRO";
            }
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("A url " + urlGet + " está inválida, corrija-a!", e);
        } catch (IOException e) {
            throw new ServidorIndisponivelException(urlGet, e);
        } finally {
            if(connection != null) {
                connection.disconnect(); 
            }
        }
        
    }

}

