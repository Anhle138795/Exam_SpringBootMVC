package shop.demo.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class FileUtility {

    private static final Logger logger = LoggerFactory.getLogger(FileUtility.class);

    public static String uploadFileImage(MultipartFile file, String folderName) {
        String fileName = "";
        try {
            String folderUpload = System.getProperty("user.dir") + "/" + folderName;
            File directory = new File(folderUpload);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            String strpath = String.format("%s/%s", folderUpload, fileName);
            byte[] data = file.getBytes();
            try (FileOutputStream fout = new FileOutputStream(strpath);
                 BufferedOutputStream buf = new BufferedOutputStream(fout)) {
                buf.write(data);
                buf.flush();
            }
            return fileName;
        } catch (IOException e) {
            logger.error("Failed to upload file: " + file.getOriginalFilename(), e);
            return "";
        }
    }

    public static String getBase64EncodedImage(String folderName, String imageName) {
        String base64Image = "";
        String folderUpload = System.getProperty("user.dir") + "/" + folderName;
        try (FileInputStream fin = new FileInputStream(folderUpload + "/" + imageName)) {
            byte[] data = fin.readAllBytes();
            base64Image = Base64.getEncoder().encodeToString(data);
        } catch (FileNotFoundException e) {
            logger.error("File not found: " + imageName, e);
        } catch (IOException e) {
            logger.error("Failed to read file: " + imageName, e);
        }
        return base64Image;
    }

    public static boolean deleteFile(String folderName, String fileName) {
        boolean isDeleted = false;
        try {
            Path pathFile = Paths.get(System.getProperty("user.dir"), folderName, fileName);
            isDeleted = Files.deleteIfExists(pathFile);
        } catch (IOException e) {
            logger.error("Failed to delete file: " + fileName, e);
        }
        return isDeleted;
    }
}
