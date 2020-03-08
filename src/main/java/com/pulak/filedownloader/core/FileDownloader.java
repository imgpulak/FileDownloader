package com.pulak.filedownloader.core;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public abstract class FileDownloader {
	public String getLocalPath(final URL url, final String localDir){
		String filePath = localDir + File.separator + getFileName(url);
		return filePath;
	}

	public String getFileName(final URL url){
		final String[] strs =  url.getPath().split("/");
		return strs[strs.length - 1];
	}

	abstract public boolean download(final URL url, final String localPath) throws IOException;
}
