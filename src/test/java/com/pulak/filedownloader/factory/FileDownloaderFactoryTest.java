package com.pulak.filedownloader.factory;

import com.pulak.filedownloader.core.FTPFileDownloader;
import com.pulak.filedownloader.core.FileDownloader;
import com.pulak.filedownloader.core.HTTPFileDownloader;
import com.pulak.filedownloader.core.SFTPFileDownloader;
import com.pulak.filedownloader.enums.Protocol;
import com.pulak.filedownloader.exception.UnsupportedProtocolException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileDownloaderFactoryTest {

    @Test
    void testGetHTTP() throws UnsupportedProtocolException {
        final FileDownloader fileDownloader = FileDownloaderFactory.get(Protocol.HTTP);
        Assertions.assertEquals(true, fileDownloader instanceof HTTPFileDownloader);
    }

    @Test
    void testGetHFTP() throws UnsupportedProtocolException {
        final FileDownloader fileDownloader = FileDownloaderFactory.get(Protocol.FTP);
        Assertions.assertEquals(true, fileDownloader instanceof FTPFileDownloader);
    }

    @Test
    void testGetSFTP() throws UnsupportedProtocolException {
        final FileDownloader fileDownloader = FileDownloaderFactory.get(Protocol.SFTP);
        Assertions.assertEquals(true, fileDownloader instanceof SFTPFileDownloader);
    }
}
