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
        
        try(FileInputStream fis = new FileInputStream(arquivo)) {
          String md5String = DigestUtils.md5Hex(fis);
          return md5String;
        } catch (FileNotFoundException e) {
          return "ERRO";
        } catch (IOException e) {
          return "ERRO";
        }
    }
    
    /**
     * Encodes a string
     * 
     * @param str String to encode
     * @return Encoded String
     */
    public static String geraMd5Str(String str) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("String to encript cannot be null or zero length");
        }

        String md5String = DigestUtils.md5Hex(str);
        return md5String;
    }
    
}
