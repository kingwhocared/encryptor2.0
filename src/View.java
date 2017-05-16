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

        FileManipulator manipulator = new RegularManipulator(code, mainUserRequest);
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
        key.WriteToFile("key.bin");
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
        System.out.println("Choose Encryption Algorithem:");
        System.out.println("1). Caesar");
        System.out.println("2). Multplication");
        System.out.println("3). XOR");
        Scanner reader = new Scanner(System.in);
        int enc_alg_choice = Integer.parseInt(reader.nextLine());
        int key;
        Code code;
        switch (enc_alg_choice) {
            case 1:
                key = GetKey(mainUserRequest);
                code = new Caesar(key);
                return code;
            case 2:
                key = GetKey(mainUserRequest);
                code = new Multiplication(key);
                return code;
            case 3:
                key = GetKey(mainUserRequest);
                code = new XOR(key);
                return code;
        }
        throw new RuntimeException("invalid encryption algorithem choice");
    }

    static Key GetKey(MainUserRequest mainUserRequest) {
        Key key = null;
        switch (mainUserRequest) {
            case DECRYPTION:
                key = GetKeyForDecryption();
                break;
            case ENCRIPTION:
                key = GetKeyForEncryption();
                break;
        }
        return key;
    }
}
