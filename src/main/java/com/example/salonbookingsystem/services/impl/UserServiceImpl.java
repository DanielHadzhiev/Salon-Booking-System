package com.example.salonbookingsystem.services.impl;

import com.example.salonbookingsystem.model.dto.*;
import com.example.salonbookingsystem.model.entity.Role;
import com.example.salonbookingsystem.model.entity.UserEntity;
import com.example.salonbookingsystem.model.enums.GenderEnum;
import com.example.salonbookingsystem.model.enums.RolesEnum;
import com.example.salonbookingsystem.repositories.GenderRepository;
import com.example.salonbookingsystem.repositories.RoleRepository;
import com.example.salonbookingsystem.repositories.UserRepository;
import com.example.salonbookingsystem.services.UserService;
import com.example.salonbookingsystem.utils.CustomAuthentication;
import com.example.salonbookingsystem.utils.CustomUserDetails;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper,
                           GenderRepository genderRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.genderRepository = genderRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(RegisterDTO registerDTO) throws IOException {

        UserEntity user = this.modelMapper.map(registerDTO, UserEntity.class);
        user.setUserPhoto(convertMultipartFileToByteArray(registerDTO.getUserImage()));
        user.setGender(this.genderRepository.findByGender(GenderEnum.valueOf(registerDTO.getGender())));

        List<Role> rolesList = user.getRoles();

        if(registerDTO.getName().contains("admin")){

            rolesList.add(this.roleRepository.findByName(RolesEnum.ADMIN));

        }else {

            rolesList.add(this.roleRepository.findByName(RolesEnum.USER));

        }
        user.setRoles(rolesList);

        user.setPassword(this.passwordEncoder.encode(registerDTO.getPassword()));

        this.userRepository.save(user);

    }

    @Override
    public boolean loginUser(LoginDTO loginDTO) {
        Optional<UserEntity> byEmail = this.userRepository.findByEmail(loginDTO.getEmail());

        if(byEmail.isEmpty()){
            return false;
        }
        else if (!this.passwordEncoder.matches(loginDTO.getPassword(),byEmail.get().getPassword())) {
            return false;
        }
        return false;
    }

    @Override
    public ProfileDTO exportProfile() {

            Optional<UserEntity> currentUser = this.userRepository.findByEmail(getCurrentEmail());

            if (currentUser.isEmpty()) {
                return null;
            }
            ProfileDTO profileDTO = this.modelMapper.map(currentUser.get(), ProfileDTO.class);

            profileDTO.setGender(currentUser.get().getGender().getGender().name());
            profileDTO.setUserImage(Base64.getEncoder().encodeToString(currentUser.get().getUserPhoto()));

            return profileDTO;
    }

    @Override
    public boolean changePassword(ChangePasswordDTO changePasswordDTO) {

        Optional<UserEntity> user = this.userRepository.findByEmail(getCurrentEmail());

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

        Optional<UserEntity> byEmail = this.userRepository.findByEmail(getCurrentEmail());

        if(byEmail.isPresent()){

            byEmail.get().setEmail(changeEmailDTO.getNewEmail());
            changeEmail(changeEmailDTO.getNewEmail());
            this.userRepository.save(byEmail.get());

        }

    }

    @Override
    public void changeName(ChangeNameDTO changeNameDTO) {

        Optional<UserEntity> byEmail = this.userRepository.findByEmail(getCurrentEmail());

        if(byEmail.isPresent()){

            byEmail.get().setName(changeNameDTO.getNewName());
            changeName(changeNameDTO.getNewName());
            this.userRepository.save(byEmail.get());

        }
    }

    @Override
    public List<EmailsExportDTO> getAllEmails() {

        Optional<List<UserEntity>> allByEmailNot = this.userRepository
                .findAllByEmailNotAndRolesName(getCurrentEmail(),
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

        Optional<UserEntity> user = this.userRepository.findById(id);

        if(user.isPresent()){

            List<Role> roles = user.get().getRoles();

            roles.add(this.roleRepository.findByName(RolesEnum.ADMIN));

            user.get().setRoles(roles);

            this.userRepository.save(user.get());
        }

    }

    @Override
    public String getCurrentEmail() {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof CustomUserDetails customUserDetails) {

                return customUserDetails.getEmail();
            } else {
                return "";
        }
    }

    @Override
    public String getCurrentName() {

        Optional<UserEntity> byEmail = this.userRepository.findByEmail(getCurrentEmail());

        if(byEmail.isPresent()){
            return byEmail.get().getName();
        }

        return "Anonymous";

    }

    private String getCurrentPassword(){

        Optional<UserEntity> byEmail = this.userRepository.findByEmail(getCurrentEmail());

        if(byEmail.isPresent()){
            return byEmail.get().getPassword();
        }

        return "";

    }

    private void changeName(String newUsername){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null &&
                authentication.isAuthenticated() &&
                authentication.getPrincipal() instanceof CustomUserDetails customUserDetails) {

            CustomUserDetails updatedUserDetails = new CustomUserDetails(newUsername,
                    getCurrentPassword(),
                    customUserDetails.getAuthorities(),
                    customUserDetails.getEmail()
                   );

            Authentication updatedAuthentication = new CustomAuthentication(updatedUserDetails,
                    authentication.getCredentials(),
                    authentication.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(updatedAuthentication);

            }
        }

        private void changeEmail(String newEmail){

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null &&
                    authentication.isAuthenticated() &&
                    authentication.getPrincipal() instanceof CustomUserDetails customUserDetails) {

                CustomUserDetails updatedUserDetails = new CustomUserDetails(customUserDetails.getUsername(),
                        getCurrentPassword(),
                        customUserDetails.getAuthorities(),
                        newEmail
                );

                Authentication updatedAuthentication = new CustomAuthentication(updatedUserDetails,
                        authentication.getCredentials(),
                        authentication.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(updatedAuthentication);

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
