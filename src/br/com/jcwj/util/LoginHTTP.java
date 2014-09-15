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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author julio
 */
public class LoginHTTP  {
    
    private static final Logger logger = LoggerFactory.getLogger(LoginHTTP.class);

    public String login(String usuario, String senha, String computador, String valida) throws ServidorIndisponivelException {
        
        String urlGet = "http://www.atualizatabwin.com.br/applogin";
        String charset = "UTF-8";
        String params = null;
        HttpURLConnection connection = null;
        
        try {
            params = String.format("usuario=%s&senha=%s&computador=%s&valida=%s",
                    URLEncoder.encode(usuario, charset), 
                    URLEncoder.encode(senha, charset),
                    URLEncoder.encode(computador, charset),
                    URLEncoder.encode(valida, charset));
        } catch (UnsupportedEncodingException ex) {
            logger.error("Erro ao montar parametros: ", ex);
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
                logger.debug(header.getKey() + "=" + header.getValue());
            }
                
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String retorno = CharStreams.toString(reader);
                reader.close();
                return retorno;      
            } else {
                //InputStream error = ((HttpURLConnection) connection).getErrorStream();
                logger.debug(String.valueOf(connection.getResponseCode()));
                logger.debug(connection.getResponseMessage());
                logger.debug("Retorno de Erro");
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

