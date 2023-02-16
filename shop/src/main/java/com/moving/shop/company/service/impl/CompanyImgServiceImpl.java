package com.moving.shop.company.service.impl;

import com.moving.shop.common.service.FileUploadService;
import com.moving.shop.company.domain.entity.Company;
import com.moving.shop.company.domain.entity.CompanyImg;
import com.moving.shop.company.domain.repository.CompanyImgRepository;
import com.moving.shop.company.service.CompanyImgService;
import java.io.File;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CompanyImgServiceImpl implements CompanyImgService {

  private final FileUploadService fileUploadService;
  private final CompanyImgRepository companyImgRepository;

  @Value("${companyImgLocation}")
  private String companyImgLocation;

  @Override
  public void saveCompanyImg(Company company, MultipartFile uploadFile) throws Exception {

    String originFileName = uploadFile.getOriginalFilename();

    String[] filePaths = null;

    String imgName = ""; //파일명(uuid 적용)
    String imgUrl = ""; //WebConfig 소스 적용된 url
    String originFilePath = ""; //실제 파일 저장 경로
    String withSubFolderFilePath = ""; //하위폴더 포함 경로

    if (!originFileName.isEmpty()) {
      /*
       * [0] 확장자명 포함 파일명
       * [1] 실제 파일 적재 경로
       * [2] 하위폴더 포함한 경로 (yyyy/MM/dd / 파일명.확장자)
       * */
      filePaths = fileUploadService.uploadFile(companyImgLocation, originFileName,
          uploadFile.getBytes());
      imgName = filePaths[0];
      originFilePath = filePaths[1];
      withSubFolderFilePath = filePaths[2];

      imgUrl = File.separator + "images" + File.separator + "company" + File.separator + withSubFolderFilePath;
    }

    CompanyImg companyImg = CompanyImg.builder()
        .imgName(imgName)
        .originImgPath(originFilePath)
        .urlImgPath(imgUrl)
        .companyId(company.getId())
        .build();
    companyImgRepository.save(companyImg);
  }
}
