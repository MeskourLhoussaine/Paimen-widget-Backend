package ma.m2t.chaabipay.controller;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import ma.m2t.chaabipay.emailing.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("mail")
public class MailApi {
@Autowired
    private EmailService emailService;

    //POST
    @PostMapping("/verification")
    public String sentVerificationMail(String toEmail) throws MessagingException {
        this.emailService.sendDemandeVerificationEmail(toEmail);
        return "Success verification";
    }

    @PostMapping("/accepted")
    public String sentAcceptedMail(String toEmail) throws MessagingException {
        this.emailService.sendValidationAcceptedEmail(toEmail);
        return "Success accepted";
    }

    @PostMapping("/rejected")
    public String sentRejectedMail(String toEmail) throws MessagingException {
        this.emailService.sendValidationRejectedEmail(toEmail);
        return "Success Rejected";
    }

    //Send Mail
    @PostMapping("/send-email")
    public void sendEmail(
            @RequestParam("subject") String subject,
            @RequestParam("nom") String nom,
            @RequestParam("message") String message) {
        emailService.sendEmail(subject, nom, message);
    }
/*
    @PostMapping("/send-email-contact")
    public void sendContact(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("email") String email) {
        // Construisez le contenu de l'email avec le nom d'utilisateur et le mot de passe généré
        String subject = "Création de compte sur votre plateforme";
        String message = "Bonjour " + username + ",\n\n"
                + "Votre compte a été créé avec succès sur notre plateforme.\n\n"
                + "Voici vos informations de connexion :\n\n"
                + "Nom d'utilisateur : " + username + "\n"
                + "Mot de passe : " + password + "\n\n"
                + "Vous pouvez maintenant vous connecter à votre compte.\n\n"
                + "Cordialement,\nVotre plateforme";

        // Envoyez l'email avec le contenu construit
        emailService.sendEmail(subject, email, message);
    }*/
}
