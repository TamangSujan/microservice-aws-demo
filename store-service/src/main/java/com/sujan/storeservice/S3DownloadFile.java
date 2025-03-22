package com.sujan.storeservice;

import org.springframework.core.io.Resource;

import java.io.ByteArrayOutputStream;

public class S3DownloadFile {
    private final String contentType;
    private final long contentLength;
    private final Resource resource;
    public S3DownloadFile(String contentType, long contentLength, Resource resource){
        this.contentLength = contentLength;
        this.contentType = contentType;
        this.resource = resource;
    }

    public String getContentType(){ return contentType; }
    public long getContentLength(){ return contentLength; }
    public Resource getResource(){ return resource; }
}
