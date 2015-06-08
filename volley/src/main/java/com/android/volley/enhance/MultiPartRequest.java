package com.android.volley.enhance;

import java.io.File;
import java.util.Map;

/**
 * Created by JiangPing on 2015/6/4.
 */
public interface MultiPartRequest {

    public void addFilesUpload(String param, File[] files);

    public void addFileUpload(String param, File file);

    public void addStringUpload(String param, String content);

    public Map<String, File[]> getFilesUploads();

    public Map<String, File> getFileUploads();

    public Map<String, String> getStringUploads();
}