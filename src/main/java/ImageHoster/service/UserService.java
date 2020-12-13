package ImageHoster.service;

import ImageHoster.model.User;
import ImageHoster.repository.UserRepository;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //Call the registerUser() method in the UserRepository class to persist the user record in the database
    public boolean registerUser(User newUser) {
        String password = newUser.getPassword();
        boolean strongPassword = checkPasswordStrength(password);
        if (strongPassword) {
            userRepository.registerUser(newUser);
            return true;
        } else {
            return false;
        }
    }

    private boolean checkPasswordStrength(String password) {

        // Regex to check valid password.
        String regex = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*()_+]).{3,}$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // If the password is empty return false
        if (password == null) {
            return false;
        }

        // Pattern class contains matcher() method
        // to find matching between given password
        // and regular expression.
        Matcher m = p.matcher(password);

        // Return if the password
        // matched the ReGex
        return m.matches();
    }

    //Since we did not have any user in the database, therefore the user with username 'upgrad' and password 'password' was hard-coded
    //This method returned true if the username was 'upgrad' and password is 'password'
    //But now let us change the implementation of this method
    //This method receives the User type object
    //Calls the checkUser() method in the Repository passing the username and password which checks the username and password in the database
    //The Repository returns User type object if user with entered username and password exists in the database
    //Else returns null
    public User login(User user) {
        User existingUser = userRepository.checkUser(user.getUsername(), user.getPassword());
        if (existingUser != null) {
            return existingUser;
        } else {
            return null;
        }
    }

}
