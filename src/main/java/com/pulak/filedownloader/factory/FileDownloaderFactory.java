package com.pulak.filedownloader.factory;

import com.pulak.filedownloader.core.FTPFileDownloader;
import com.pulak.filedownloader.core.FileDownloader;
import com.pulak.filedownloader.core.HTTPFileDownloader;
import com.pulak.filedownloader.core.SFTPFileDownloader;
import com.pulak.filedownloader.enums.Protocol;
import com.pulak.filedownloader.exception.UnsupportedProtocolException;

/**
 * Factory design pattern.
 * Factory class to get FileDownloader based on protocol.
 * @author Pulak Ghosh<ghosh.pulak91@gmail.com>
 *
 */
public class FileDownloaderFactory {
    /**
     * To get FileDownloader based on protocol
     * @param protocol Protocol based on URL to be downloaded
     */
    public static FileDownloader get(final Protocol protocol)
            throws UnsupportedProtocolException {
        FileDownloader fileDownloader = null;
        switch (protocol) {
            case HTTP:
                fileDownloader = new HTTPFileDownloader();
                break;
            case FTP:
                fileDownloader = new FTPFileDownloader();
                break;
            case SFTP:
                fileDownloader = new SFTPFileDownloader();
                break;
            default:
                throw new UnsupportedProtocolException(protocol.toString() +
                        "is not supported yet.");
        }
        return fileDownloader;
    }
}