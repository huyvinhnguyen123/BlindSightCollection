package todo.advance.blindsight.util.generate.code;

import java.util.Random;

public class CodeGenerator {
    // generate random Code without special characters
	public static final String generateRandomCodeWithoutSpecialCharacters(int len) {
		String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijk"
          +"lmnopqrstuvwxyz";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(chars.charAt(rnd.nextInt(chars.length())));
		return sb.toString();
	}
// =========================================================================================================================
	// generate random code
    public static final char[] generateRandomCode(int length) {
		String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
	    String specialCharacters = "!@#$";
	    String numbers = "1234567890";
	    
	    // combine all characters
	    String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
	    
	    // create random
	    Random random = new Random();
	    
	    // create length for password
	    char[] password = new char[length];

	    // generate random value in index position
	    password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
	    password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
	    password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
	    password[3] = numbers.charAt(random.nextInt(numbers.length()));
	   
	    // loop index position
	    for(int i = 4; i< length ; i++) {
	    	// combine generate random value in index position 
	    	password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
	    }
	    return password;
	}
// =========================================================================================================================
	// generate ramdom otp code
	private static final int OTP_LENGTH = 6;
    private static final String OTP_CHARACTER = "0123456789";

    public static String generateOTP() {
        StringBuilder otp = new StringBuilder();
        Random rnd = new Random();
        for(int i = 0; i < OTP_LENGTH; i++) {
            otp.append(OTP_CHARACTER.charAt(rnd.nextInt(OTP_CHARACTER.length())));
        }
        return otp.toString();
    }
}
