package com.example.salonbookingsystem.services.impl;

import com.example.salonbookingsystem.model.dto.*;
import com.example.salonbookingsystem.model.entity.User;
import com.example.salonbookingsystem.model.enums.GenderEnum;
import com.example.salonbookingsystem.model.enums.RolesEnum;
import com.example.salonbookingsystem.repositories.GenderRepository;
import com.example.salonbookingsystem.repositories.RoleRepository;
import com.example.salonbookingsystem.repositories.UserRepository;
import com.example.salonbookingsystem.services.UserService;
import com.example.salonbookingsystem.session.LoggedUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final GenderRepository genderRepository;

    private final ModelMapper modelMapper;

    private final LoggedUser loggedUser;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper,
                           GenderRepository genderRepository,
                           LoggedUser loggedUser,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.genderRepository = genderRepository;
        this.loggedUser = loggedUser;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(RegisterDTO registerDTO) throws IOException {

        User user = this.modelMapper.map(registerDTO, User.class);
        user.setUserPhoto(convertMultipartFileToByteArray(registerDTO.getUserImage()));
        user.setGender(this.genderRepository.findByGender(GenderEnum.valueOf(registerDTO.getGender())));

        if(registerDTO.getName().contains("admin")){
            user.setRole(this.roleRepository.findByName(RolesEnum.ADMIN));
        }else {
            user.setRole(this.roleRepository.findByName(RolesEnum.USER));
        }

        user.setPassword(this.passwordEncoder.encode(registerDTO.getPassword()));

        this.userRepository.save(user);

    }

    @Override
    public boolean loginUser(LoginDTO loginDTO) {
        Optional<User> byEmail = this.userRepository.findByEmail(loginDTO.getEmail());

        if(byEmail.isEmpty()){
            return false;
        }
        else if (!this.passwordEncoder.matches(loginDTO.getPassword(),byEmail.get().getPassword())) {
            return false;
        }
        else {
            this.loggedUser.login(byEmail.get());
            return true;
        }
    }

    @Override
    public void logoutUser() {
        this.loggedUser.logout();
    }

    @Override
    public boolean isUserLogged() {
        return this.loggedUser.getId()>0;
    }

    @Override
    public ProfileDTO exportProfile() {
        Optional<User> currentUser = this.userRepository.findByEmail(this.loggedUser.getEmail());

        if(currentUser.isEmpty()){
            return null;
        }
        ProfileDTO profileDTO = this.modelMapper.map(currentUser.get(), ProfileDTO.class);

        profileDTO.setGender(currentUser.get().getGender().getGender().name());
        profileDTO.setUserImage(Base64.getEncoder().encodeToString(currentUser.get().getUserPhoto()));

        return profileDTO;
    }

    @Override
    public boolean changePassword(ChangePasswordDTO changePasswordDTO) {

        Optional<User> user = this.userRepository.findByEmail(this.loggedUser.getEmail());

        if(user.isEmpty()){
            return false;
        }
        if(!this.passwordEncoder.matches(changePasswordDTO.getOldPassword(),user.get().getPassword())){

            return false;

        }
        user.get().setPassword(this.passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        this.userRepository.save(user.get());
return true;
    }

    @Override
    public void changeEmail(ChangeEmailDTO changeEmailDTO) {

        Optional<User> byEmail = this.userRepository.findByEmail(this.loggedUser.getEmail());

        if(byEmail.isPresent()){

            byEmail.get().setEmail(changeEmailDTO.getNewEmail());
            this.loggedUser.setEmail(changeEmailDTO.getNewEmail());
            this.userRepository.save(byEmail.get());

        }

    }

    @Override
    public void changeName(ChangeNameDTO changeNameDTO) {

        Optional<User> byEmail = this.userRepository.findByEmail(this.loggedUser.getEmail());

        if(byEmail.isPresent()){

            byEmail.get().setName(changeNameDTO.getNewName());
            this.loggedUser.setName(changeNameDTO.getNewName());
            this.userRepository.save(byEmail.get());

        }
    }

    @Override
    public List<EmailsExportDTO> getAllEmails() {

        Optional<List<User>> allByEmailNot = this.userRepository
                .findAllByEmailNotAndRoleName(this.loggedUser.getEmail(),
                        RolesEnum.USER);

        if(allByEmailNot.isEmpty()){
            return new ArrayList<>();
        }

        List<EmailsExportDTO>emailsExportDTOS = new ArrayList<>();

        allByEmailNot.get().forEach(e->emailsExportDTOS.add(this.modelMapper.map(e, EmailsExportDTO.class)));

        return emailsExportDTOS;
    }

    @Override
    public void makeAdmin(long id) {

        Optional<User> user = this.userRepository.findById(id);

        if(user.isPresent()){
            user.get().setRole(this.roleRepository.findByName(RolesEnum.ADMIN));
            this.userRepository.save(user.get());
        }

    }


    private byte[] convertMultipartFileToByteArray(MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            return file.getBytes();
        } else {
            return null;
        }
    }
}
