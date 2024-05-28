package ma.m2t.chaabipay.controller;

import ma.m2t.chaabipay.entites.User;
import ma.m2t.chaabipay.security.jwt.services.UserDetailsServiceImpl;
import ma.m2t.chaabipay.security.jwt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
    //bonjour
    @Autowired
    private UserDetailsServiceImpl userService;
    @Autowired
    private UserService userService2;


    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public String moderatorAccess() {
        return "Moderator Board.";
    }


    @GetMapping("/userss")
    public List<User> findAll() {
        return userService2.findAll();
    }

    @GetMapping("/username/{username}")
    //@PreAuthorize("hasRole('ADMIN')")
    public UserDetails findByUsername(@PathVariable String username) {
        return userService.loadUserByUsername(username);
    }

    @GetMapping("/findbyusername/{username}")
    //@PreAuthorize("hasRole('ADMIN')")
    public User findByUserName(@PathVariable String username) {
        return userService.findByUsername(username);
    }

    @GetMapping("/findbyid/{id}")
    public User findById(@PathVariable Long id) {
        return userService2.findById(id);
    }


    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }
}