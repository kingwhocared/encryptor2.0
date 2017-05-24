import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by stannis on 09/05/17.
 */
public class View {
    public enum UserRequestObjective {ENCRYPTION, DECRYPTION}
    public enum KeyType {SINGLE_KEY, DUEL_KEY}

    private static UserRequestMission.UserRequestTargetType GetTargetType(String target_path) {
        if (target_path.endsWith("/")) {
            System.out.println("Sync/unsynced?");
            System.out.println("1).Sync");
            System.out.println("2).Async");
            Scanner reader = new Scanner(System.in);
            int user_choice = Integer.parseInt(reader.nextLine());
            switch (user_choice) {
                case 1:
                    return UserRequestMission.UserRequestTargetType.FOLDER_SYNC;
                case 2:
                    return UserRequestMission.UserRequestTargetType.FOLDER_ASYNC;
            }
        }
        else {
            return UserRequestMission.UserRequestTargetType.SINGLE_FILE;
        }
        throw new RuntimeException();
    }
    public static UserRequestMission GetUserRequest() {
        UserRequestObjective userRequestObjective = GetMissionObjective();
        String target_path = GetUserRequestFilePath();
        UserRequestMission.UserRequestTargetType target_type = GetTargetType(target_path);
        Code code = GetRequestCode(userRequestObjective);
        FileManipulator manipulator = new FileManipulator(code, userRequestObjective);
        UserRequestMission userRequestMission = new UserRequestMission(manipulator, target_path, userRequestObjective, target_type);
        return userRequestMission;
    }

    static UserRequestObjective GetMissionObjective() {
        System.out.println("[Main Menu]:");
        System.out.println("1).Encryption");
        System.out.println("2).Decryption");

        Scanner reader = new Scanner(System.in);
        int user_choice = Integer.parseInt(reader.nextLine());
        UserRequestObjective userRequestObjective;

        switch (user_choice) {
            case 1:
                userRequestObjective = UserRequestObjective.ENCRYPTION;
                break;
            case 2:
                userRequestObjective = UserRequestObjective.DECRYPTION;
                break;
            default:
                throw new RuntimeException("invalid choice");
        }
        return userRequestObjective;
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

    static Code GetRequestCode(UserRequestObjective userRequestObjective) {
        System.out.println("Choose Encryption Algorithm:");
        System.out.println("1). Simple Encryption");
        System.out.println("2). Duel Encryption");
        System.out.println("3). Reverse Encryption");
        Scanner reader = new Scanner(System.in);
        Key key;
        int enc_alg_choice = Integer.parseInt(reader.nextLine());
        switch (enc_alg_choice) {
            case 1:
                key = GetKey(userRequestObjective, KeyType.SINGLE_KEY);
                return GetSimpleCode(userRequestObjective, key);
            case 2:
                key = GetKey(userRequestObjective, KeyType.DUEL_KEY);
                return GetDuelCode(userRequestObjective, key);
            case 3:
                return new ReverseEncryptionCode(GetRequestCode(userRequestObjective));
        }
        throw new RuntimeException("invalid encryption Algorithm choice");
    }

    static Code GetSimpleCode(UserRequestObjective userRequestObjective, Key key) {
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

    static Code GetDuelCode(UserRequestObjective userRequestObjective, Key key) {
        System.out.println("Choose Duel Encryption Algorithm:");
        System.out.println("1). Double Encryption");
        System.out.println("2). Split Encryption");
        Scanner reader = new Scanner(System.in);
        int enc_alg_choice = Integer.parseInt(reader.nextLine());
        Code code1;
        Code code2;
        Key key1 = ((DuelEncriptionKey) key).singleEncryptionKey_1;
        Key key2 = ((DuelEncriptionKey) key).singleEncryptionKey_2;

        code1 = GetSimpleCode(userRequestObjective, key1);
        code2 = GetSimpleCode(userRequestObjective, key2);

        switch (enc_alg_choice) {
            case 1:
                return new DoubleEncryptionCode(code1, code2);
            case 2:
                return new SplitEncryptionCode(code1, code2);
        }
        throw new RuntimeException("invalid encryption Algorithm choice");
    }

    static Key GetKey(UserRequestObjective userRequestObjective, KeyType keyType) {
        Key key = null;
        switch (userRequestObjective) {
            case DECRYPTION:
                key = GetKeyForDecryption();
                break;
            case ENCRYPTION:
                key = GetKeyForEncryption(keyType);
                break;
        }
        return key;
    }
}
