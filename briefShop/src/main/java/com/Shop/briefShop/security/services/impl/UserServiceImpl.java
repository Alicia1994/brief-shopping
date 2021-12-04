package com.Shop.briefShop.security.services.impl;

import com.Shop.briefShop.models.ERole;
import com.Shop.briefShop.models.Role;
import com.Shop.briefShop.models.User;
import com.Shop.briefShop.repositorys.RoleRepository;
import com.Shop.briefShop.repositorys.UserRepository;
import com.Shop.briefShop.security.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public User createNewAdmin(User user, String username){
        Optional<User> userOptional = userRepository.findByUsername(username);
        User newUser = null;
        Optional<Role> roleAdmin = roleRepository.findByName(ERole.ROLE_ADMIN);
        if(userOptional.isPresent() && roleAdmin.isPresent()){
            newUser = userRepository.save(user);
        }
        return newUser;
    }

    @Override
    public Iterable<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUser(final Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User saveUser(User u) {
        User save = userRepository.save(u);
        return save;
    }


    @Override
    public User updateUsersimple(User user) {
        User userr = modelMapper.map(user, User.class);
        return userRepository.save(userr);
    }
  /*  @Override
    public User updateUserDto(UserDto userDto) {//nouvelle que je test
        User user = modelMapper.map(userDto, User.class);
        return userRepository.save(user);
    }*/
    public User updateUser(String username, User user){//nouvelle que je test
        Optional<User> currentUser = userRepository.findByUsername(username);
        User userr = modelMapper.map(currentUser, User.class);
        return userRepository.save(userr);
    }

    /*
    public User deleteProjectAdmin(Long id, String username){
        Optional<User> userOptional = userRepository.findByUsername(username);
        User user = null;
        Optional<Role> adminRole = roleRepository.findByName(ERole.ROLE_ADMIN);
        if (userOptional.isPresent() && adminRole.isPresent()){
            Optional<Project> projetOptional = projectRepository.findById(id);
            user = userOptional.get();
            user.getProjects().remove(projetOptional.get());
            return userRepository.save(user);
        }
        return user;
    }
* */
    /*    public User saveProject(ProjectDto projectDto, Long idUser){
        Optional<User> userOptional = userRepository.findById(idUser);
        Project project = modelMapper.map(projectDto, Project.class);
        User user = null;
        if (userOptional.isPresent()){
            user = userOptional.get();
            user.getProjects().add(project);
            return userRepository.save(user);
        }
        return user;
    }*/
    /* essai
       public User deleteProjectPro(Long id, String username){
       Optional<User> userOptional = userRepository.findByUsername(username);
       Optional<Project> projetOptional = projectRepository.findById(id);
       User user = userOptional.get();
       Long userId = user.getId();
       Long projectUserId = projetOptional.get().getUserId();
       if (userOptional.isPresent() && userId == projectUserId){
           user.getProjects().remove(projetOptional.get());
           return userRepository.save(user); }
       return user;} */
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User deleteProject(Long id, String username) {
        return null;
    }

}