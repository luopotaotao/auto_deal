package com.tt.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Created by tt on 2016/11/28.
 */
public class FileUtil {
    public static UUID saveFile(MultipartFile src, String path,Consumer<UUID> consumer) throws IOException {
        UUID uuid = UUID.randomUUID();
        String filePath = path+uuid;
        src.transferTo(new File(filePath));
        src.getOriginalFilename();
        consumer.accept(uuid);
        return uuid;
    }
}
