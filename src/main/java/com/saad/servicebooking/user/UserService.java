package com.saad.servicebooking.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private  UserRepo userRepo;
    @Autowired
    private JavaMailSender mailSender;
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
    //Add Users
    public ResponseEntity<?> addUser(User user) {
         userRepo.save(user);
         return ResponseEntity.ok("User Added Successfully");
    }
   //Get All Users
    public List<User> getUser() {
        return userRepo.findAll();
    }
    // Delete Users

    public ResponseEntity<?> deleteUser(long id){
        userRepo.deleteById(id);
        return ResponseEntity.ok("Deleted Successfully");
    }

    public boolean findByEmail(String email){
     User user =userRepo.findByEmail(email).orElseThrow(()-> new RuntimeException("Email is already existed"));
      if (user !=null)
      {
          return false;
      }
        return true;
    }
// update
    public ResponseEntity<User> updateUser(long id, User user) {
       User userById= userRepo.findById(id).orElseThrow(()-> new RuntimeException("User with this id is not existed"));
       userRepo.deleteById(userById.getId());
       userById.setFirstname(user.getFirstname());
       userById.setLastname(user.getLastname());
       userById.setEmail(user.getEmail());
       userById.setPassword(user.getPassword());
       userById.setRole(user.getRole());
       userRepo.save(user);
       return ResponseEntity.ok(userById);
    }




    @Transactional
    public User registerUser(User user) {
        User savedUser = userRepo.save(user);

        sendActivationEmail(user);

        return savedUser;
    }


    private void sendActivationEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Activation Email");
        message.setText("Hello " + user.getFirstname() +" "+ user.getLastname()+ ",\n\n You have Successfully Created Your account At Service Booking.com:\n\n" +
                "your user name is " + user.getEmail() +" Your password is "+user.getPassword());
        message.setFrom("khawajasaadqayyum4110@gmail.com");
        try {
            mailSender.send(message);
            System.out.println("Email sent successfully.");
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
            e.printStackTrace();
        }

//        mailSender.send(message);
    }
}
