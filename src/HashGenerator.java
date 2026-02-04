import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;

public class HashGenerator {

    protected static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+=-~[]\\{};':\",./<>? ";
    protected static final String SUBSTITUTION = "zyxwvutsrqponmlkjihgfedcba9876543210?><./,:'\";}{][~-+=)_(*&^%$#@!| ";
                                                // could be better reorganized
    private static final String salt = "OSBFHtpanDe0tcSUTxQD2Q==";

    private static byte[] getMasterPasswordHashBytes(String pass) { // :)))) second hashing
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            return sha.digest(pass.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing master password:", e);
        }
    }

    // master+password => xor => substitution => Base64
    public static String encode(String plainText) {
        StringBuilder xorText = new StringBuilder();
        String textToEncode = PasswdManager.MASTER_PASSWORD_HASH + plainText;
        byte[] masterKey = getMasterPasswordHashBytes(PasswdManager.MASTER_PASSWORD_HASH);

        for (int i = 0; i < textToEncode.length(); i++) {
            char charToEncode = textToEncode.charAt(i);
            byte keyByte = masterKey[i % masterKey.length];
            xorText.append((char) (charToEncode ^ keyByte));
        }

        StringBuilder encodedText = new StringBuilder();
        String modifiedText = xorText.toString();

        for (char character : modifiedText.toCharArray()) {
            int index = ALPHABET.indexOf(character);
            encodedText.append(index != -1 ? SUBSTITUTION.charAt(index) : character);
        }

        return Base64.getEncoder().encodeToString(encodedText.toString().getBytes());
    }

    // master+password <= xor <= substitution <= Base64
    public static String decode(String encodedText) {
        StringBuilder xorText = new StringBuilder();
        byte[] decodedBytes = Base64.getDecoder().decode(encodedText);
        String substitutedText = new String(decodedBytes);
        byte[] masterKey = getMasterPasswordHashBytes(PasswdManager.USER_PASSWORD_HASH);
        if (Objects.equals(PasswdManager.USER_PASSWORD_HASH, PasswdManager.MASTER_PASSWORD_HASH)){ // even though they are equal SHA-256 will give different hashes
            masterKey = getMasterPasswordHashBytes(PasswdManager.MASTER_PASSWORD_HASH);
        }

        for (char character : substitutedText.toCharArray()) {
            int index = SUBSTITUTION.indexOf(character);
            xorText.append(index != -1 ? ALPHABET.charAt(index) : character);
        }

        String modifiedText = xorText.toString();
        StringBuilder originalText = new StringBuilder();

        for (int i = 0; i < modifiedText.length(); i++) {
            char charToDecode = modifiedText.charAt(i);
            byte keyByte = masterKey[i % masterKey.length];
            originalText.append((char) (charToDecode ^ keyByte));
        }

        return originalText.substring(PasswdManager.USER_PASSWORD_HASH.length());
    }

    public static String hash(String plainText) { // no need in decoding just real hash
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] saltBytes = Base64.getDecoder().decode(salt);
            digest.update(saltBytes);
            byte[] hashedBytes = digest.digest(plainText.getBytes());
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}