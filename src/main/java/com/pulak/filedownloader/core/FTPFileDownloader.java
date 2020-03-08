package com.pulak.filedownloader.core;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;

/**
 * A utility that downloads a file from a FTP URL.
 * @author Pulak Ghosh<ghosh.pulak91@gmail.com>
 *
 */
public class FTPFileDownloader extends FileDownloader {
    private static Logger LOGGER = Logger.getLogger(FTPFileDownloader.class);

    /**
     * Downloads a file from a FTP URL
     * @param url FTP URL of the file to be downloaded
     * @param localPath path to save file
     * @throws IOException
     */
    public boolean download(final URL url, final String localPath)
            throws IOException {
        boolean success = false;
        // FTP url must be like ftp://user:password@host:port/path
        final String server = url.getHost();
        final int port = url.getPort();
        final String remoteFilePath = url.getPath();
        final String userInfo = url.getUserInfo();
        final String[] userInfoArray = userInfo.split(":");
        final String user = userInfoArray[0];
        final String pass = userInfoArray[1];
        final FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            OutputStream outputStream = new BufferedOutputStream(FileUtils.openOutputStream(new File(localPath)));
            LOGGER.info("Please wait for sometime. Downloading is in-progress...");
            success = ftpClient.retrieveFile(remoteFilePath, outputStream);
            outputStream.close();
            if (success) {
                LOGGER.info("File has been downloaded successfully.");
            }
        } catch (IOException ex) {
            LOGGER.error("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return success;
    }
}
