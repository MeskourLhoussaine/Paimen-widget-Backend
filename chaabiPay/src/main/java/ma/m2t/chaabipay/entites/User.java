package ma.m2t.chaabipay.entites;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ma.m2t.chaabipay.enums.AneeActivite;
import ma.m2t.chaabipay.enums.UserStatus;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),

      })
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 20)
    private String firstName;

    @NotBlank
    @Size(max = 20)
    private String lastName;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

  @NotBlank
  @Size(max = 100)
  @Email
  private String email;

    @NotBlank
    @Size(max = 225)
    private String password;
    @NotBlank
    @Size(max = 100)
    private String profilLogoUrl; // if marchand ==> profilLogoUrl == marchandLogoUrl

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(String username, String firstName, String lastName, String email, String password,String status,String profilLogoUrl ) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.status=UserStatus.valueOf(status);
        this.profilLogoUrl=profilLogoUrl;
    }


    public User(String username, String firstName, String lastName, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;

    }
  /*public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }*/
  // Getter for status
  public UserStatus getStatus() {
      return status;
  }

    // Setter for status
    public void setStatus(UserStatus status) {
        this.status = status;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
