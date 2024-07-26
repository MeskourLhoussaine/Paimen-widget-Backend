package ma.m2t.chaabipay.controller;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import ma.m2t.chaabipay.entites.Role;
import ma.m2t.chaabipay.entites.User;
import ma.m2t.chaabipay.enums.ERole;
import ma.m2t.chaabipay.enums.UserStatus;
import ma.m2t.chaabipay.payload.request.LoginRequest;
import ma.m2t.chaabipay.payload.request.SignupRequest;
import ma.m2t.chaabipay.payload.request.UpdateProfileRequest;
import ma.m2t.chaabipay.payload.response.JwtResponse;
import ma.m2t.chaabipay.payload.response.MessageResponse;
import ma.m2t.chaabipay.repositories.RoleRepository;
import ma.m2t.chaabipay.repositories.UserRepository;
import ma.m2t.chaabipay.security.jwt.JwtUtils;
import ma.m2t.chaabipay.security.jwt.services.UserDetailsImpl;
import ma.m2t.chaabipay.security.jwt.services.UserService;
import ma.m2t.chaabipay.services.implement.ChangePasswordRequest;
import ma.m2t.chaabipay.services.implement.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")


public class AuthController {
   @Autowired
    private UserService userService;
   @Autowired
   private AuthenticationManager authenticationManager;

   @Autowired
   private  UserRepository userRepository;

    @Autowired
   private  RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private EmailService emailService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        System.out.println(jwt+"   "+userDetails.getUsername());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getFirstName(),
                userDetails.getLastName(),

                roles));


    }

    @PutMapping("/users/{userId}/password")
    public ResponseEntity<?> updatePassword(
            @PathVariable Long userId, @Valid @RequestBody ChangePasswordRequest updatePasswordRequest) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        if (!encoder.matches(updatePasswordRequest.getOldPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid old password");
        }

        user.setPassword(encoder.encode(updatePasswordRequest.getNewPassword()));
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Password updated successfully"));
    }

    @GetMapping("/users")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<User>> findAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/admin")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<User>> findAdmin() {
        List<User> users = userRepository.findAdmin();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/mod")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<User>> findMod() {
        List<User> users = userRepository.findMod();
        return ResponseEntity.ok(users);
    }

   // Importez la classe List si ce n'est pas déjà fait

    @GetMapping("/users/roles/{username}") // Modifiez le chemin de l'endpoint pour refléter qu'il peut renvoyer plusieurs rôles
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<ERole>> findRoles(@PathVariable String username) {
        List<ERole> roles = userRepository.findRolesByUsername(username); // Assurez-vous que la méthode findRoles de votre repository retourne une liste de rôles
        return ResponseEntity.ok(roles);
    }


    @GetMapping("/users/user")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<User>> findUser() {
        List<User> users = userRepository.findUser();
        return ResponseEntity.ok(users);
    }



    @PutMapping("/updateprofil/{username}")
    public ResponseEntity<?> updateUserProfile(@Valid @RequestBody UpdateProfileRequest signupRequest, @PathVariable String username) {
        // Retrieve the current authenticated user
        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = username;
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + currentUsername));

        // Update user information meskourth
        currentUser.setFirstName(signupRequest.getFirstName());
        currentUser.setLastName(signupRequest.getLastName());
        currentUser.setUsername(signupRequest.getUsername());

        if(!encoder.matches(signupRequest.getOldPassword(), currentUser.getPassword())){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Old password is incorrect."));
        }
        // Update password if provided
        if (!signupRequest.getNewPassword().equals("")) {

            if (encoder.matches(signupRequest.getOldPassword(), currentUser.getPassword())) {
                currentUser.setPassword(encoder.encode(signupRequest.getNewPassword()));
            } else {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Old password is incorrect."));
            }
        }

        // Save the updated user
        userRepository.save(currentUser);

        return ResponseEntity.ok(new MessageResponse("User profile updated successfully!"));
    }



    //update by id
    @PutMapping("/updateprofile/{id}")
    public ResponseEntity<?> updateUserProfileById(@Valid @RequestBody UpdateProfileRequest updateProfileRequest, @PathVariable Long id) {
        // Rechercher l'utilisateur par ID dans la base de données
        User currentUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        // Mettre à jour les informations de l'utilisateur
        currentUser.setFirstName(updateProfileRequest.getFirstName());
        currentUser.setLastName(updateProfileRequest.getLastName());
        currentUser.setUsername(updateProfileRequest.getUsername());
        currentUser.setProfilLogoUrl(updateProfileRequest.getProfilLogoUrl());
       currentUser.setEmail(updateProfileRequest.getEmail());
        currentUser.setStatus(UserStatus.valueOf(updateProfileRequest.getStatus())); // Mettre à jour le statut

        // Mettre à jour les rôles de l'utilisateur
        Set<Role> roles = new HashSet<>(updateProfileRequest.getRoles());
        currentUser.setRoles(roles);

        // Enregistrer l'utilisateur mis à jour dans la base de données
        userRepository.save(currentUser);

        return ResponseEntity.ok(new MessageResponse("User profile updated successfully!"));
    }



    @PostMapping("/signup")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already taken!"));
        }

        // Créer un nouvel utilisateur avec le statut par défaut "Inactive"
        User user = new User(
                signUpRequest.getUsername(),
                signUpRequest.getFirstName(),
                signUpRequest.getLastName(),
                UserStatus.Inactive,
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()), // Utilisez le mot de passe fourni
                signUpRequest.getProfilLogoUrl(),
                new HashSet<>() // Créez un ensemble vide pour les rôles
        );

        // Définir les rôles de l'utilisateur
        setRoles(signUpRequest.getRoles(), user.getRoles());

        userRepository.save(user);

        // Envoyer l'email basé sur le rôle de l'utilisateur
        sendRoleBasedEmail(user, signUpRequest.getPassword());

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    //cette fonction est utilisée pour définir les rôles d'un utilisateur lors de son inscription

    private void setRoles(Set<String> strRoles, Set<Role> roles) {
        if (strRoles == null || strRoles.isEmpty()) {
            Role defaultRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(defaultRole);
        } else {
            strRoles.forEach(role -> {
                Role userRole = roleRepository.findByName(ERole.valueOf(role))
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            });
        }
    }
//Cette  méthode est utiliser pour envoyer des emails basés sur le rôle
    private void sendRoleBasedEmail(User user, String password) {
        boolean isMarchand = user.getRoles().stream()
                .anyMatch(role -> role.getName().equals(ERole.ROLE_MARCHAND));

        boolean isCommercial = user.getRoles().stream()
                .anyMatch(role -> role.getName().equals(ERole.ROLE_COMERCIAL));

        try {
            if (isMarchand) {
                emailService.sendPasswordMarchandEmail(user.getEmail(), user.getUsername(), password);
            } else if (isCommercial) {
                emailService.sendPasswordCommercialEmail(user.getEmail(), user.getUsername(), password);
            }
        } catch (MessagingException e) {
            // Gérer les exceptions d'envoi d'email
            throw new RuntimeException("User registered but failed to send email: " + e.getMessage());
        }
    }



    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Assurez-vous que l'utilisateur a le rôle approprié
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();

    }
    @GetMapping("/findbyid/{id}")
    public User findById(@PathVariable Long id) {
        return userService.findById(id);
    }

}
