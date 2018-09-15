package com.msdsalehi.langfinder.searchengine.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msdsalehi.langfinder.model.ProgrammingLanguage;
import com.msdsalehi.langfinder.searchengine.DataProvider;
import com.msdsalehi.langfinder.throwable.error.DataSourceConfigurationError;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author masoud
 */
public class FileBasedDataProvider implements DataProvider {

    private final static Logger logger = Logger.getLogger(FileBasedDataProvider.class);
    private final String filePath;
    private final String fileName;

    public FileBasedDataProvider(String filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName;
    }

    @Override
    public List<ProgrammingLanguage> load() {
        try {
            String rawData = loadContentFromFile(fileName);
            ObjectMapper mapper = new ObjectMapper();
            Object parsedValue = mapper.readValue(rawData, new TypeReference<List<ProgrammingLanguage>>() {
            });
            return (List<ProgrammingLanguage>) parsedValue;
        } catch (IOException ex) {
            final String errorDesc = "Error in structure of data in file";
            logger.error(errorDesc, ex);
            throw new DataSourceConfigurationError(errorDesc, ex);
        }
    }

    private String loadContentFromFile(String fileName) {
        URL fileUrl = ConcurrentSearchEngine.class.getResource(filePath + fileName);
        try (
                RandomAccessFile searchSourceFile = new RandomAccessFile(fileUrl.getFile(), "r");
                FileChannel inChannel = searchSourceFile.getChannel();) {
            long fileSize = inChannel.size();
            ByteBuffer buffer = ByteBuffer.allocate((int) fileSize);
            inChannel.read(buffer);
            buffer.flip();
            return new String(buffer.array());
        } catch (FileNotFoundException ex) {
            final String errorDesc = "Error finding searech source file : "
                    + "[" + fileName + "] in path [" + filePath + "]";
            logger.error(errorDesc, ex);
            throw new DataSourceConfigurationError(errorDesc, ex);
        } catch (IOException ex) {
            final String errorDesc = "Error reading from source file : "
                    + "[" + fileName + "] in path [" + filePath + "]";
            logger.error(errorDesc, ex);
            throw new DataSourceConfigurationError(errorDesc, ex);
        } catch (Exception ex) {
            final String errorDesc = "General Error in reading source file : "
                    + "[" + fileName + "] in path [" + filePath + "]";
            logger.error(errorDesc, ex);
            throw new DataSourceConfigurationError(errorDesc, ex);
        }
    }

}
