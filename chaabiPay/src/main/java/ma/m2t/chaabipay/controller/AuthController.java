package ma.m2t.chaabipay.controller;

import jakarta.validation.Valid;
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
import ma.m2t.chaabipay.services.implement.ChangePasswordRequest;
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
   AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

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

        User user = userRepository.getReferenceById(userId);

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
    public ResponseEntity<?> updateUserProfileById(@Valid @RequestBody UpdateProfileRequest signupRequest, @PathVariable Long id) {
        // Rechercher l'utilisateur par ID dans la base de données
        User currentUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        // Mettre à jour les informations de l'utilisateur
        currentUser.setFirstName(signupRequest.getFirstName());
        currentUser.setLastName(signupRequest.getLastName());
        currentUser.setUsername(signupRequest.getUsername());
      //  currentUser.setProfilLogoUrl(signupRequest.getProfilLogoUrl());
       currentUser.setEmail(signupRequest.getEmail());
        currentUser.setStatus(UserStatus.valueOf(signupRequest.getStatus())); // Mettre à jour le statut

        // Mettre à jour les rôles de l'utilisateur
        Set<Role> roles = new HashSet<>(signupRequest.getRoles());
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

        // Créer un nouvel utilisateur avec le statut par défaut "Inactive"
        signUpRequest.setStatus(UserStatus.Inactive);

        // Générer un mot de passe aléatoire
     //   String generatedPassword = generateRandomPassword();

        // Créer un nouvel utilisateur à partir des informations fournies dans la requête
        User user = new User(
                signUpRequest.getUsername(),
                signUpRequest.getFirstName(),
                signUpRequest.getLastName(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()), // Utilisez le mot de passe aléatoire généré
                signUpRequest.getStatus().toString(),
                signUpRequest.getProfilLogoUrl()
        );

        // Définir les rôles de l'utilisateur
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_COMERCIAL)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "marchand":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MARCHAND)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_COMERCIAL)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    // Méthode pour générer un mot de passe aléatoire
    private String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        byte[] passwordBytes = new byte[8]; // Longueur du mot de passe
        random.nextBytes(passwordBytes);
        return Base64.getEncoder().encodeToString(passwordBytes);
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Assurez-vous que l'utilisateur a le rôle approprié
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
