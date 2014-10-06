package br.com.jcwj.util;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import static java.nio.file.FileVisitResult.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import org.slf4j.LoggerFactory;

/**
 *
 * @author julio
 */
public class ArqUtil {
    
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ArqUtil.class);
    
    public static void deletaPasta(String pasta) {
        try {
            Path dir = Paths.get(pasta);
            Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult visitFile(Path file,
                        BasicFileAttributes attrs) throws IOException {

                    logger.debug("Deletando arquivo: " + file);
                    Files.delete(file);
                    return CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir,
                        IOException exc) throws IOException {

                    logger.debug("Deletando diretório: " + dir);
                    if (exc == null) {
                        Files.delete(dir);
                        return CONTINUE;
                    } else {
                        throw exc;
                    }
                }

            });
        } catch (IOException e) {
            logger.error("Erro ao deletar a pasta: " + pasta);
        }

    }
}
