package kg.it_lab.backend.Osh.service.admin.impl;

import kg.it_lab.backend.Osh.dto.admin.EditorRegisterRequest;


import kg.it_lab.backend.Osh.dto.role.RoleRequest;
import kg.it_lab.backend.Osh.entities.*;
import kg.it_lab.backend.Osh.exception.BadRequestException;
import kg.it_lab.backend.Osh.exception.NotFoundException;
import kg.it_lab.backend.Osh.repository.*;
import kg.it_lab.backend.Osh.service.admin.AdminService;

import kg.it_lab.backend.Osh.service.emailSender.EmailSenderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final NewsRepository newsRepository;
    private final EventRepository eventRepository;
    private final RoleRepository roleRepository;
    private final EmailSenderService emailSenderService;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ServicesRepository servicesRepository;
    private final ActivityRepository activityRepository;
    private final SponsorshipRepository sponsorshipRepository;



    @Override
    public void addRole(RoleRequest roleRequest) {
        if (roleRequest.getRoleName().isEmpty()) {
            throw new BadRequestException("Role name can't be empty");
        }
        Optional<Role> role = roleRepository.findByName(roleRequest.getRoleName());
        if (role.isPresent()) {
            throw new BadRequestException("This role already exist");
        }
        Role role1 = new Role();
        role1.setName(roleRequest.getRoleName());
        roleRepository.save(role1);
    }

    @Override
    public void deleteRole(Long id) {
        Optional<Role>role = roleRepository.findById(id);
        if(role.isEmpty()){
            throw new NotFoundException("Role with this id: " + id + " - not found", HttpStatus.NOT_FOUND );
        }
        roleRepository.deleteById(id);
    }

    @Override
    public void registerEditor(EditorRegisterRequest editorRegisterRequest) {
        if (userRepository.findByEmail(editorRegisterRequest.getEmail()).isPresent()) {
            throw new BadRequestException("Editor with this email already exist");
        }
        if (editorRegisterRequest.getEmail().isEmpty()) {
            throw new BadRequestException("Your email can't be empty");
        }
        if (!editorRegisterRequest.getEmail().contains("@")) {
            throw new BadRequestException("Invalid email!");
        }

        User editor = new User();
        Optional<Role> role = roleRepository.findByName(editorRegisterRequest.getRole());
        if (role.isEmpty()) {
            throw new NotFoundException("Role with this name not found", HttpStatus.NOT_FOUND);
        }
        editor.setEmail(editorRegisterRequest.getEmail());
        editor.setRole(role.get());
        userRepository.save(editor);
        emailSenderService.sendPassword(editorRegisterRequest.getEmail());
    }

    @Override
    public void checker(Optional<News> news, Long id) {
        if (news.isEmpty()) {
            throw new NotFoundException("News with id " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public int imageChecker(Image image){
        int cnt = 0;
        if (newsRepository.existsByImage(image)) {
            cnt++;
            System.out.println("NEWS");
        }
        if (eventRepository.existsByImage(image)) {
            cnt++;
            System.out.println("EVENT");
        }
        if (activityRepository.existsByImage(image)) {
            cnt++;
            System.out.println("ACTIVITY");
        }
        if (projectRepository.existsByImagesContaining(image)) {
            cnt++;
            System.out.println("PROJECT");
        }
        if (servicesRepository.existsByImagesContaining(image)) {
            cnt++;
            System.out.println("SERVICES");
        }
        return cnt;
    }
}
