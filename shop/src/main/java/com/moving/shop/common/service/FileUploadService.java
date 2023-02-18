package com.moving.shop.common.service;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class FileUploadService {

  public String[] uploadFile(String uploadPath, String originFileName, byte[] fileData) throws Exception{

    UUID uuid = UUID.randomUUID();
    String extension = originFileName.substring(originFileName.lastIndexOf("."));

    String savedFileName = uuid.toString() + extension; //파일명 (확장자 포함)

    String folderPath = makeDir(uploadPath);

    String fileUploadFullUrl = uploadPath + File.separator + folderPath + File.separator + savedFileName; //실제 파일 적재 경로

    String withFolderPath = folderPath + File.separator + savedFileName;

    FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
      fos.write(fileData);
      fos.close();

    String[] result = {savedFileName, fileUploadFullUrl, withFolderPath};
    return result;
}

  private String makeDir(String uploadPath) {

    String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

    String folderPath = str.replace("/", File.separator);

    //uploadPath 내에 yyyy/MM/dd 형식의 하위 폴더 생성
    File uploadPathFolder = new File(uploadPath, folderPath);

    if (uploadPathFolder.exists() == false) {
      uploadPathFolder.mkdirs();
    }

    return folderPath;
  }
}
