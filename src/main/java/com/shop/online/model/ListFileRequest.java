package com.shop.online.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ListFileRequest {
    MultipartFile file;
    String folder;
}
