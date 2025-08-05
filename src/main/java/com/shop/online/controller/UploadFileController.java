package com.shop.online.controller;

import com.shop.online.model.APIResponse;
import com.shop.online.model.ListFileRequest;
import com.shop.online.service.S3Service;
import java.io.IOException;
import java.util.List;

import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping({"api/v1/upload-file"})
@RequiredArgsConstructor
public class UploadFileController {
   private final S3Service s3Service;

   @PostMapping({"/upload"})
   public APIResponse<?> uploadImage(@ModelAttribute ListFileRequest fileRequest) throws IOException {
      return APIResponse.okStatus(this.s3Service.uploadFile(fileRequest.getFile(), fileRequest.getFolder()));
   }
   @PostMapping({"/upload-list"})
   public APIResponse<?> uploadImageList(List<MultipartFile> files, String folder) throws IOException {
      return APIResponse.okStatus(this.s3Service.uploadFileList(files, folder));
   }

}
