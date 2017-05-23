import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by stannis on 09/05/17.
 */
public class View {
    public enum MainUserRequest {ENCRIPTION, DECRYPTION}
    public enum KeyType {SINGLE_KEY, DUEL_KEY}

    public static UserRequestMission GetUserRequest() {
        MainUserRequest mainUserRequest = GetMissionObjective();
        String filepath = GetUserRequestFilePath();

        Code code = GetRequestCode(mainUserRequest);


        FileManipulator manipulator = new FileManipulator(code, mainUserRequest);
        UserRequestMission userRequestMission = new UserRequestMission(manipulator, filepath, mainUserRequest);
        return userRequestMission;
    }

    static MainUserRequest GetMissionObjective() {
        System.out.println("[Main Menu]:");
        System.out.println("1).Encryption");
        System.out.println("2).Decryption");

        Scanner reader = new Scanner(System.in);
        int user_choice = Integer.parseInt(reader.nextLine());
        MainUserRequest mainUserRequest;

        switch (user_choice) {
            case 1:
                mainUserRequest = MainUserRequest.ENCRIPTION;
                break;
            case 2:
                mainUserRequest = MainUserRequest.DECRYPTION;
                break;
            default:
                throw new RuntimeException("invalid choice");
        }
        return mainUserRequest;
    }

    static Key GetKeyForDecryption() {
        Scanner reader = new Scanner(System.in);
        System.out.println("Insert path of decryption key...");
        String path = reader.nextLine();
        return Key.ReadFromFile(path);
    }

    static Key GetKeyForEncryption(KeyType keyType) {
        Key key = null;

        switch (keyType) {
            case SINGLE_KEY:
                int randomNum = ThreadLocalRandom.current().nextInt(1, (1 << 8));
                System.out.println("Key Generated for encryption: " + randomNum);
                key = new SingleEncryptionKey(randomNum);
                break;
            case DUEL_KEY:
                int randomNum1 = ThreadLocalRandom.current().nextInt(1, (1 << 8));
                int randomNum2 = ThreadLocalRandom.current().nextInt(1, (1 << 8));
                System.out.println("Key Generated for encryption: " + randomNum1 + ", " + randomNum2);
                key = new DuelEncriptionKey(randomNum1, randomNum2);
                break;
        }
        key.WriteToFile();
        return key;
    }

    static String GetUserRequestFilePath() {
        File varTmpDir;
        String filepath;
        Scanner reader = new Scanner(System.in);
        do {
            System.out.println("Enter file path:");
            filepath = reader.nextLine();
            varTmpDir = new File(filepath);
        } while (! (varTmpDir.exists()));
        return filepath;
    }

    static Code GetRequestCode(MainUserRequest mainUserRequest) {
        System.out.println("Choose Encryption Algorithm:");
        System.out.println("1). Simple Encryption");
        System.out.println("2). Duel Encryption");
        System.out.println("3). Reverse Encryption");
        Scanner reader = new Scanner(System.in);
        Key key;
        int enc_alg_choice = Integer.parseInt(reader.nextLine());
        switch (enc_alg_choice) {
            case 1:
                key = GetKey(mainUserRequest, KeyType.SINGLE_KEY);
                return GetSimpleCode(mainUserRequest, key);
            case 2:
                key = GetKey(mainUserRequest, KeyType.DUEL_KEY);
                return GetDuelCode(mainUserRequest, key);
            case 3:
                return new ReverseEncryptionCode(GetRequestCode(mainUserRequest));
        }
        throw new RuntimeException("invalid encryption Algorithm choice");
    }

    static Code GetSimpleCode(MainUserRequest mainUserRequest, Key key) {
        System.out.println("Choose Encryption Algorithm:");
        System.out.println("1). Caesar");
        System.out.println("2). Multplication");
        System.out.println("3). XOR");
        Scanner reader = new Scanner(System.in);
        int enc_alg_choice = Integer.parseInt(reader.nextLine());
        Code code;
        switch (enc_alg_choice) {
            case 1:
                code = new Caesar(key);
                return code;
            case 2:
                code = new Multiplication(key);
                return code;
            case 3:
                code = new XOR(key);
                return code;
        }
        throw new RuntimeException("invalid encryption Algorithm choice");
    }

    static Code GetDuelCode(MainUserRequest mainUserRequest, Key key) {
        System.out.println("Choose Duel Encryption Algorithm:");
        System.out.println("1). Double Encryption");
        System.out.println("2). Split Encryption");
        Scanner reader = new Scanner(System.in);
        int enc_alg_choice = Integer.parseInt(reader.nextLine());
        Code code1;
        Code code2;
        Key key1 = ((DuelEncriptionKey) key).singleEncryptionKey_1;
        Key key2 = ((DuelEncriptionKey) key).singleEncryptionKey_2;

        code1 = GetSimpleCode(mainUserRequest, key1);
        code2 = GetSimpleCode(mainUserRequest, key2);

        switch (enc_alg_choice) {
            case 1:
                return new DoubleEncryptionCode(code1, code2);
            case 2:
                return new SplitEncryptionCode(code1, code2);
        }
        throw new RuntimeException("invalid encryption Algorithm choice");
    }

    static Key GetKey(MainUserRequest mainUserRequest, KeyType keyType) {
        Key key = null;
        switch (mainUserRequest) {
            case DECRYPTION:
                key = GetKeyForDecryption();
                break;
            case ENCRIPTION:
                key = GetKeyForEncryption(keyType);
                break;
        }
        return key;
    }
}
