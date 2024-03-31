package kg.it_lab.backend.Osh.service.emailSender;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;

@Service
public class GeneratorService {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789/?!<>[]{}";

    public String generatePassword(){

        SecureRandom random  = new SecureRandom();
        StringBuilder password = new StringBuilder();




            for(int i = 0 ; i<9 ;  i++){
                int randomIndex = random.nextInt(CHARACTERS.length());
                password.append(CHARACTERS.charAt(randomIndex));
            }


        return password.toString();

    }
    public String generateLogin(){
        String code = "";
        Random random = new Random();
        for (int k = 0; k < 7; k++) {

            code += (char) (random.nextInt(26) + 65);

        }
        return code;
    }
    public static boolean isStrongPassword(String password){

        if (password.length() < 8) {
            return false;
        }
        boolean hasDigit = false, hasUpperCase = false, hasLowerCase = false, hasSpecialChar = false;
        for (char ch : password.toCharArray()) {
            if (Character.isDigit(ch)) {
                hasDigit = true;
            } else if (Character.isUpperCase(ch)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(ch)) {
                hasLowerCase = true;
            } else if (CHARACTERS.indexOf(ch) != -1) {
                hasSpecialChar = true;
            }
        }


        if (!hasDigit || !hasUpperCase || !hasLowerCase || !hasSpecialChar) {
            return false;
        }

        if (password.matches("^[a-zA-Z0-9/?!<>\\[\\]{}]+$")) {
            return false;
        }

        return true;
    }



}
