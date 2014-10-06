package br.com.jcwj.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import net.sf.sevenzipjbinding.ExtractAskMode;
import net.sf.sevenzipjbinding.ExtractOperationResult;
import net.sf.sevenzipjbinding.IArchiveExtractCallback;
import net.sf.sevenzipjbinding.ISequentialOutStream;
import net.sf.sevenzipjbinding.ISevenZipInArchive;
import net.sf.sevenzipjbinding.PropID;
import net.sf.sevenzipjbinding.SevenZip;
import net.sf.sevenzipjbinding.SevenZipException;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;
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
            inArchive.extract(null, false, new MyExtractCallback(inArchive, pastaSaida));
            logger.info("Descompactando arquivo: " + arquivoCompactado + " / Formato: " + inArchive.getArchiveFormat().toString());
        } catch (FileNotFoundException ex) {
            logger.error("Não foi possível encontrar o arquivo: " + arquivoCompactado, ex);
        } catch (SevenZipException ex) {
            logger.error("Erro ao descompactar com 7-Zip-Jbinding", ex);
        } finally {
            if (inArchive != null) {
                try {
                    inArchive.close();
                } catch (SevenZipException ex) {
                    logger.error("Erro ao fechar o arquivo: " + arquivoCompactado, ex);
                }
            }
            if (randomAccessFile != null) {
                try {
                    randomAccessFile.close();
                } catch (IOException ex) {
                    logger.error("Erro ao fechar o arquivo: " + arquivoCompactado, ex);
                }
            }

        }
    }

    private static class MyExtractCallback implements IArchiveExtractCallback {

        private final ISevenZipInArchive inArchive;
        private final String extractPath;
        private int index;
        private OutputStream outputStream;
        private File file;
        private boolean isFolder;

        public MyExtractCallback(ISevenZipInArchive inArchive, String extractPath) {
            this.inArchive = inArchive;
            this.extractPath = extractPath;
        }

        @Override
        public ISequentialOutStream getStream(final int index, ExtractAskMode extractAskMode) throws SevenZipException {

            this.index = index;
            this.isFolder = (Boolean) inArchive.getProperty(index, PropID.IS_FOLDER);
            String filePath = (String) inArchive.getStringProperty(index, PropID.PATH);

            if (extractAskMode != ExtractAskMode.EXTRACT) {
                return null;
            }

            logger.debug("Processando arquivo: " + filePath);

            closeOutputStream();

            file = new File(extractPath + File.separator + filePath);

            if (isFolder) {
                createDirectory(file);
                return null;
            }

            createDirectory(file.getParentFile());

            try {
                outputStream = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                logger.error("Could not create FileOutputStream", e);
            }

            return new ISequentialOutStream() {
                @Override
                public int write(byte[] data) throws SevenZipException {

                    try {
                        outputStream.write(data);
                    } catch (IOException e) {
                        logger.error("IOException while extracting " + file.getAbsolutePath());
                    }

                    return data.length;
                }
            };
        }

        private void closeOutputStream() throws SevenZipException {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                    outputStream = null;
                } catch (IOException e) {
                    logger.error("Could not close FileOutputStream", e);
                }
            }
        }

        private void createDirectory(File parentFile) throws SevenZipException {
            if (!parentFile.exists()) {
                if (!parentFile.mkdirs()) {
                    logger.error("Não foi possível criar diretório:" + parentFile.getAbsolutePath());
                }
            }
        }

        @Override
        public void prepareOperation(ExtractAskMode extractAskMode) throws SevenZipException {
        }

        @Override
        public void setOperationResult(ExtractOperationResult extractOperationResult) throws SevenZipException {
            closeOutputStream();
            String path = (String) inArchive.getProperty(index, PropID.PATH);
            if (extractOperationResult != ExtractOperationResult.OK) {
                logger.error("Não foi possível descompactar o arquivo:" + path);
            }
        }

        @Override
        public void setCompleted(long completeValue) throws SevenZipException {
        }

        @Override
        public void setTotal(long total) throws SevenZipException {
        }
    }
}
