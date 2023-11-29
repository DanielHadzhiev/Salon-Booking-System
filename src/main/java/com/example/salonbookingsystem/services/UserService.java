package com.example.salonbookingsystem.services;

import com.example.salonbookingsystem.model.dto.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface UserService {

    void registerUser(RegisterDTO registerDTO) throws IOException;

    ProfileDTO exportProfile();

    boolean loginUser(LoginDTO loginDTO);

    boolean changePassword(ChangePasswordDTO changePasswordDTO);

    void changeEmail(ChangeEmailDTO changeEmailDTO);

    void changeName(ChangeNameDTO changeNameDTO);

    List<EmailsExportDTO> getAllEmails();

    void makeAdmin(long id);

    String getCurrentEmail();

    String getCurrentName();


}
