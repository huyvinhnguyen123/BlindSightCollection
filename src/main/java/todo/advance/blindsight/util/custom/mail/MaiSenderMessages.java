package todo.advance.blindsight.util.custom.mail;

public class MaiSenderMessages {

    private static final String EMAIL="mailto:atifarlunar.official@gmail.com";
    private static final String TARGET="_blank";
    
    public static final String verifyEmailMessage() {
        return  "<html><body>"
        + "<p><strong>Dear User</strong></p>"
        + "<p>To continue using BlindSight, please verify your email address.</p>"
        + "<p>Click on this link or Copy this link to new tab and open this link to confirm your email address</p>"
        + "<a> http://localhost:3000/login </a>"
        + "<p>Thank you!</p>"
        + "<P>Best Regard,</p>"
        + "<p><strong>The BlindSight Team :D</strong></p>"
        + "</body></html>";
    } 

    public static final String resetPasswordMessage(String otp) {
        return  "<html><body>"
        + "<p><strong>Dear User</strong></p>"
        + "<p>A request has been received to change the password for your BlindSight account. </p>"
        + "<p>This is your OTP code: <strong>"+ otp +"</strong></p>"
        + "<p><strong>Don't share your OTP code with anyone.</strong></p>"
        + "<p>If you did not initlate this request, please contact us at: <a href="+EMAIL+" target="+TARGET+">atifarlunar.official@gmail.com</a></p>"
        + "<p>Thank you!</p>"
        + "<P>Best Regard,</p>"
        + "<p><strong>The BlindSight Team :D</strong></p>"
        + "</body></html>";
    }
}
