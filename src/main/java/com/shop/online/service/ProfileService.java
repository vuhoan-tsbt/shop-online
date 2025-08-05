package com.shop.online.service;

import com.shop.online.entity.User;
import com.shop.online.exception.MessageCode;
import com.shop.online.exception.ServiceApiException;
import com.shop.online.model.dto.AdminProfileDto;
import com.shop.online.model.request.UpdateProfileAdminRequest;
import com.shop.online.model.response.IdResponse;
import com.shop.online.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ProfileService extends BaseService{

    private final UserRepository userRepository;
    public AdminProfileDto getAdmin() {
        Integer userId = this.getCurrentUserId();
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ServiceApiException(MessageCode.ERRORS_AUTH_NOT_FOUND.getCode(), MessageCode.ERRORS_AUTH_NOT_FOUND.getDisplay()));
        AdminProfileDto adminProfileDto = new AdminProfileDto();
        adminProfileDto.setId(user.getId());
        adminProfileDto.setEmail(user.getEmail());
        adminProfileDto.setStatus(user.getStatus());
        adminProfileDto.setType(user.getType());
        return adminProfileDto;
    }

    @Transactional(rollbackFor = Exception.class)
    public IdResponse updateInfo(UpdateProfileAdminRequest profileAdminRequest) {
        Integer userId = this.getCurrentUserId();
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ServiceApiException(MessageCode.ERRORS_AUTH_NOT_FOUND.getCode(), MessageCode.ERRORS_AUTH_NOT_FOUND.getDisplay()));
        user.setAvatar(profileAdminRequest.getAvatar());
        user.setPhone(profileAdminRequest.getPhone());
        user.setDateOfBirth(profileAdminRequest.getDateOfBirth());
        user.setUserName(profileAdminRequest.getUserName());
        userRepository.save(user);
        return new IdResponse(user.getId());
    }
}
