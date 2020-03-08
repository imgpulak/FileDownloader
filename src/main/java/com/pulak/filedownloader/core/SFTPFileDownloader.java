package com.pulak.filedownloader.core;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;

import java.io.IOException;
import java.net.URL;

/**
 * A utility that downloads a file from a SFTP URL.
 * @author Pulak Ghosh<ghosh.pulak91@gmail.com>
 *
 */
public class SFTPFileDownloader extends FileDownloader {
    /**
     * Downloads a file from a SFTP URL
     * @param url SFTP URL of the file to be downloaded
     * @param localPath path to save file
     * @throws IOException
     */
    public boolean download(final URL url, final String localPath)
            throws IOException {
        boolean success = false;
        SSHClient sshClient = setupSSHJ(url);
        SFTPClient sftpClient = sshClient.newSFTPClient();
        sftpClient.get(url.getFile(), localPath);
        sftpClient.close();
        sshClient.disconnect();
        success = true;
        return success;
    }

    /**
     * Setup SSH Client
     * @param url HTTP URL of the file to be downloaded
     * @throws IOException
     */
    private SSHClient setupSSHJ(final URL url) throws IOException {
        final String userInfo = url.getUserInfo();
        final String[] userInfoArray = userInfo.split(":");
        final String username = userInfoArray[0];
        final String password = userInfoArray[1];
        SSHClient client = new SSHClient();
        client.addHostKeyVerifier(new PromiscuousVerifier());
        client.connect(url.getHost());
        client.authPassword(username, password);
        return client;
    }
}
