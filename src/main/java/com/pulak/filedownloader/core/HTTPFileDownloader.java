package com.pulak.filedownloader.core;

import com.pulak.filedownloader.core.FileDownloader;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
 
/**
 * A utility that downloads a file from a HTTP URL.
 * @author Pulak Ghosh<ghosh.pulak91@gmail.com>
 *
 */
public class HTTPFileDownloader extends FileDownloader {
    private static final int BUFFER_SIZE = 4096;
    private static Logger LOGGER = Logger.getLogger(HTTPFileDownloader.class);

    /**
     * Downloads a file from a HTTP URL
     * @param url HTTP URL of the file to be downloaded
     * @param localPath path to save file
     * @throws IOException
     */
    public boolean download(final URL url, final String localPath) throws IOException {
        final HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        final int responseCode = httpConn.getResponseCode();
        
        if (responseCode != HttpURLConnection.HTTP_OK) {
		    LOGGER.error("No file to download. Server replied HTTP code: " + responseCode);
        	httpConn.disconnect();
        	return false;
        }

        // opens input stream from the HTTP connection
        final InputStream inputStream = httpConn.getInputStream();
        // opens an output stream to save into file
        final FileOutputStream outputStream = FileUtils.openOutputStream(new File(localPath));
        LOGGER.info("Please wait for sometime. Downloading is in-progress...");
        int bytesRead = -1;
        byte[] buffer = new byte[BUFFER_SIZE];
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.close();
        inputStream.close();
        LOGGER.info("File has been downloaded successfully.");
        httpConn.disconnect();
        return true;
    }
}
