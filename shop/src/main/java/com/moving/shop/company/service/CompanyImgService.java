package com.moving.shop.company.service;

import com.moving.shop.company.domain.entity.Company;
import org.springframework.web.multipart.MultipartFile;

public interface CompanyImgService {

  void saveCompanyImg(Company company, MultipartFile uploadFile) throws Exception;
}
