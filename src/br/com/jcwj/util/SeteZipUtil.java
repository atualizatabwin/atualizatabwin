package br.com.jcwj.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import net.sf.sevenzipjbinding.ExtractOperationResult;
import net.sf.sevenzipjbinding.ISequentialOutStream;
import net.sf.sevenzipjbinding.ISevenZipInArchive;
import net.sf.sevenzipjbinding.SevenZip;
import net.sf.sevenzipjbinding.SevenZipException;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;
import net.sf.sevenzipjbinding.simple.ISimpleInArchive;
import net.sf.sevenzipjbinding.simple.ISimpleInArchiveItem;
import org.slf4j.LoggerFactory;

/**
 *
 * @author julio
 */
public class SeteZipUtil {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SeteZipUtil.class);

    public static void descompacta(String arquivoCompactado, final String pastaSaida) {

        RandomAccessFile randomAccessFile = null;
        ISevenZipInArchive inArchive = null;

        try {
            randomAccessFile = new RandomAccessFile(arquivoCompactado, "r");
            inArchive = SevenZip.openInArchive(null, new RandomAccessFileInStream(randomAccessFile));
            
            // Getting simple interface of the archive inArchive
            ISimpleInArchive simpleInArchive = inArchive.getSimpleInterface();
            
            for (final ISimpleInArchiveItem item : simpleInArchive.getArchiveItems()) {
                final int[] hash = new int[] { 0 };
                if (!item.isFolder()) {
                    ExtractOperationResult result;

                    final long[] sizeArray = new long[1];
                    result = item.extractSlow(new ISequentialOutStream() {
                        
                        @Override
                        public int write(byte[] data) throws SevenZipException {
                            
                            //Write to file
                            FileOutputStream fos;
                            try {
                                File file = new File(pastaSaida + File.separator + item.getPath());
                                file.getParentFile().mkdirs();
                                fos = new FileOutputStream(file);
                                fos.write(data);
                                fos.close();
                            } catch (FileNotFoundException e) {
                                logger.error("Erro ao criar arquivo de saida.");
                            } catch (IOException e) {
                                logger.error("Erro ao criar arquivo de saida.");
                            }
                            
                            hash[0] ^= Arrays.hashCode(data); // Consume data
                            sizeArray[0] += data.length;
                            return data.length; // Return amount of consumed data
                        }
                    });
                    if (result == ExtractOperationResult.OK) {
                        logger.debug(String.format("%9X | %10s | %s", hash[0], sizeArray[0], item.getPath()));
                    } else {
                        logger.error("Error extracting item: " + result);
                    }
                }
            }

        } catch (FileNotFoundException ex) {
            logger.error("Não foi possível encontrar o arquivo: " + arquivoCompactado);
        } catch (SevenZipException ex) {
            logger.error("Erro ao descompactar com 7zipbinding");
        } finally {
            if (inArchive != null) {
                try {
                    inArchive.close();
                } catch (SevenZipException e) {
                    logger.error("Error closing archive: " + e);
                }
            }
            if (randomAccessFile != null) {
                try {
                    randomAccessFile.close();
                } catch (IOException e) {
                    logger.error("Error closing file: " + e);
                }
            }

        }
    }
}
