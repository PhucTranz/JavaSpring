package qltb;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Passwordaa {
	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String s = "123";
		String s2= encoder.encode(s);
		System.out.print(s2);
	}
}
