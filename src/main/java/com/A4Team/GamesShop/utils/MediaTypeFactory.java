package com.A4Team.GamesShop.utils;

import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MediaTypeFactory {
    private static final Map<String, MediaType> MEDIA_TYPE_MAP = new HashMap<>();

    static {
        // Image MIME Types
        MEDIA_TYPE_MAP.put("jpg", MediaType.IMAGE_JPEG);
        MEDIA_TYPE_MAP.put("jpeg", MediaType.IMAGE_JPEG);
        MEDIA_TYPE_MAP.put("png", MediaType.IMAGE_PNG);
        MEDIA_TYPE_MAP.put("gif", MediaType.IMAGE_GIF);
        MEDIA_TYPE_MAP.put("bmp", MediaType.valueOf("image/bmp"));
        MEDIA_TYPE_MAP.put("webp", MediaType.valueOf("image/webp"));

        // Game File MIME Types
        MEDIA_TYPE_MAP.put("zip", MediaType.valueOf("application/zip"));
        MEDIA_TYPE_MAP.put("rar", MediaType.valueOf("application/x-rar-compressed"));
        MEDIA_TYPE_MAP.put("exe", MediaType.valueOf("application/x-msdownload"));
        MEDIA_TYPE_MAP.put("iso", MediaType.valueOf("application/x-iso9660-image"));
        MEDIA_TYPE_MAP.put("apk", MediaType.valueOf("application/vnd.android.package-archive"));
        MEDIA_TYPE_MAP.put("tar.gz", MediaType.valueOf("application/gzip"));
    }

    public static MediaType getMediaType(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return MediaType.APPLICATION_OCTET_STREAM; // Unknown file type
        }

        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        return Optional.ofNullable(MEDIA_TYPE_MAP.get(extension))
                .orElse(MediaType.APPLICATION_OCTET_STREAM);
    }
}
