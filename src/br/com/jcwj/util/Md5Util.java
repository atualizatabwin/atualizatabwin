/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jcwj.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author julio
 */
public class Md5Util {
    
    public static String geraMd5Arquivo(String arquivo){
        FileInputStream fis;
        try {
          fis = new FileInputStream(arquivo);
          String md5String = DigestUtils.md5Hex(fis);
          return md5String;
        } catch (FileNotFoundException e) {
          return "ERRO";
        } catch (IOException e) {
          return "ERRO";
        }
    }
    
}
