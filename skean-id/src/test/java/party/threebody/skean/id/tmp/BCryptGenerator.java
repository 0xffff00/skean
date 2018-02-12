package party.threebody.skean.id.tmp;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptGenerator {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        for (int i = 0; i < 5; i++) {
            System.out.println(encoder.encode("123456"));
        }
        for (int i = 0; i < 5; i++) {
            System.out.println(encoder.encode("1"));
        }
    }
}
