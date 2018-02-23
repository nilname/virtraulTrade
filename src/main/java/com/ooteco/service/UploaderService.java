package com.ooteco.service;

import org.springframework.stereotype.Service;

/**
 * Created by shenyu on 2018/02/12.
 */
@Service
public class UploaderService {

    public boolean validImageFileSize(long fileSize) {
        if (fileSize / 1024 > 5120L)
            return false;
        return true;
    }

    public boolean validImageFileType(String suffix) {
        String type = suffix.toUpperCase();
        boolean result;
        switch (type) {
            case "JPG":
                result = true;
                break;
            case "JPEG":
                result = true;
                break;
            case "PNG":
                result = true;
                break;
            case "GIF":
                result = true;
                break;
            case "BMP":
                result = true;
                break;
            default:
                result = false;
                break;
        }
        return result;
    }

}
