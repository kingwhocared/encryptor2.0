import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by stannis on 09/05/17.
 */
public class View {
    public enum MainUserRequest {ENCRIPTION, DECRYPTION}

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

    static int GetKeyForDecryption() {
        Scanner reader = new Scanner(System.in);
        System.out.println("Insert key for decryption...");
        return Integer.parseInt(reader.nextLine());
    }

    static int GetKeyForEncryption() {
        int randomNum = ThreadLocalRandom.current().nextInt(1, (1 << 8));
        System.out.println("Key Generated for encryption: " + randomNum);
        return randomNum;
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
        int key = 0;
        switch (mainUserRequest) {
            case DECRYPTION:
                key = GetKeyForDecryption();
                break;
            case ENCRIPTION:
                key = GetKeyForEncryption();
                break;
        }

        Code code = new Caesar(key);
        return code;
    }

}
