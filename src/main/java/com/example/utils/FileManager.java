package com.example.utils;

import com.example.exceptions.MyException;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileManager {
    private static final String RESOURCE_PATH = "src\\main\\webapp\\static\\images\\";

    private static String generateFullCatalogPath(String baseCatalog) throws IllegalArgumentException {
        if (!baseCatalog.contains("target")) {
            throw new IllegalArgumentException("NIEPRAWIDLOWA SCIEZKA APLIKACJI");
        }
        String[] pathArr = baseCatalog.split("target");
        return pathArr[0] + RESOURCE_PATH;
    }

    private static String generateFullFilename(MultipartFile multipartFile) {
        String filename = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"));
        String[] originalFilenameElements = multipartFile.getOriginalFilename().split("\\.");
        return filename + "." + originalFilenameElements[originalFilenameElements.length - 1];
    }

    public static String addResource(MultipartFile multipartFile, String baseCatalog) {
        try {
            if (multipartFile != null && multipartFile.getBytes().length != 0) {
                // 1. generujemy sciezke
                String fullCatalogPath = generateFullCatalogPath(baseCatalog);

                // 2. wygeneruj nazwe pliku
                String fullFilename = generateFullFilename(multipartFile);

                // 3. kompletna sciezka pliku
                String fullPath = fullCatalogPath + fullFilename;

                // 4. kopiowanie
                FileCopyUtils.copy(multipartFile.getBytes(), new File(fullPath));


                return fullFilename;
            }
            return "default.jpg";
        } catch (Exception e) {
            throw new MyException("FILE MANAGER, ADD RESOURCE EXCEPTION", LocalDateTime.now());
        }
    }

    public static String updateResource(MultipartFile multipartFile, String baseCatalog, String filename) {
        try {
            if (multipartFile != null && multipartFile.getBytes().length != 0) {
                // 1. generujemy sciezke
                String fullCatalogPath = generateFullCatalogPath(baseCatalog);

                // 2. kompletna sciezka pliku
                String fullFilename = (filename == null || filename.isEmpty()) ? generateFullFilename(multipartFile) : filename;
                String fullPath = fullCatalogPath + fullFilename;

                // 3. kopiowanie
                FileCopyUtils.copy(multipartFile.getBytes(), new File(fullPath));

                return fullFilename;
            }
            return "default.jpg";
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("FILE MANAGER, UPDATE RESOURCE EXCEPTION", LocalDateTime.now());
        }
    }
}
