package com.pulak.filedownloader.core;

import com.pulak.filedownloader.enums.Protocol;
import com.pulak.filedownloader.factory.FileDownloaderFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;

public class FileDownloaderTest {
    private static FileDownloader fileDownloader = null;
    private static String localDir = "/home/abc/Downloads";
    private static String stringURL = "ftp://www.example.com/file.jpg";
    private static URL url = null;

    @BeforeEach
    void setUp() throws Exception {
        fileDownloader = FileDownloaderFactory.get(Protocol.FTP);
        url = new URL(stringURL);
    }

    @Test
    void testGetLocalPath() {
        final String localPath = fileDownloader.getLocalPath(url, localDir);
        final String expectedPath = localDir + File.separator + "file.jpg";
        Assertions.assertEquals(expectedPath, localPath);
    }

    @Test
    void testGetFileName() {
        final String fileName = fileDownloader.getFileName(url);
        final String expectedFileName = "file.jpg";
        Assertions.assertEquals(expectedFileName, fileName);
    }
}
