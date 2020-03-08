package com.pulak.filedownloader;

import com.pulak.filedownloader.core.FileDownloader;
import com.pulak.filedownloader.enums.Protocol;
import com.pulak.filedownloader.exception.UnsupportedProtocolException;
import com.pulak.filedownloader.factory.FileDownloaderFactory;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * A tool to which can be used to download data from multiple sources and protocols to local disk.
 * The URLs will be passed as input from command line(space separated).
 * Ex: <code> <url1> <url2> <url3>
 * @author Pulak Ghosh<ghosh.pulak91@gmail.com>
 *
 */
public class DownloadFile {
    private static Logger LOGGER = Logger.getLogger(DownloadFile.class);

    final private static String CONFIG_FILE_PATH = System.getProperty("user.home") +
            File.separator + ".config/FileDownloader/config.properties";

    /**
     * Entry method for this tool.
     * @param args An array of command line arguments.
     */
    public static void main(String[] args){
        try {
            final Properties properties = getProperties(CONFIG_FILE_PATH);
            for(int i=0; i < args.length; i++) {
                final String stringURL = args[i];
                final URL url = new URL(stringURL);
                download(url, properties);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * To get FileDownloader based on protocol
     * @param url URL to be downloaded
     * @param properties Configs
     */
    public static void download(final URL url, final Properties properties) {
        boolean success = false;
        try {
            final String downloadDir = (String) properties.get("downloadDir");
            final String tempDir = (String) properties.get("tempDir");
            final Protocol protocol = getProtocol(url);
            LOGGER.info("=====================================");
            LOGGER.info("Trying to download URL: " + url);
            LOGGER.info("Protocol: " + url.getProtocol());
            LOGGER.info("Download directory: " + downloadDir);
            LOGGER.info("Temp directory: " + tempDir);

            final FileDownloader fileDownloader = FileDownloaderFactory.get(protocol);
            final String downloadPath = fileDownloader.getLocalPath(url, downloadDir);
            final String tempPath = fileDownloader.getLocalPath(url, tempDir);
            LOGGER.info("Download path: " + downloadPath);
            LOGGER.info("Temp path: " + tempPath);

            // Downloading to a temp path
            success = fileDownloader.download(url, tempPath);
            if (success) {
                // Copying to final path
                FileUtils.copyFile(new File(tempPath), new File(downloadPath));
                LOGGER.info("Download is successful. Final local file path is " + downloadPath);
            } else {
                LOGGER.error("Not able to download url.");
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * To get configs
     * @param configPath Config file path
     */
    private static Properties getProperties(final String configPath)
            throws FileNotFoundException, IOException {
        final File configFile = new File(configPath);
        final FileReader reader = new FileReader(configFile);
        final Properties properties = new Properties();
        properties.load(reader);
        reader.close();
        return properties;
    }

    /**
     * To get protocol enum from URL
     * @param url URL to be downloaded
     * @return Protocol
     */
    private static Protocol getProtocol(final URL url)
            throws UnsupportedProtocolException {
        Protocol protocol = null;
        try {
            protocol = Protocol.valueOf(url.getProtocol().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UnsupportedProtocolException(url.getProtocol() + " is not supported yet.");
        }
        return protocol;
    }
}