import java.io.File;
import java.util.Scanner;

/**
 * Created by stannis on 09/05/17.
 */
public class View {
    public enum UserRequest {ENCRIPTION, DECRYPTION}

    public static UserRequestMission GetUserRequest() {
        System.out.println("[Main Menu]:");
        System.out.println("1).Encryption");
        System.out.println("2).Decryption");

        Scanner reader = new Scanner(System.in);
        int user_choice = Integer.parseInt(reader.nextLine());
        View.UserRequest mainMenuRequest;

        switch (user_choice) {
            case 1:
                mainMenuRequest = View.UserRequest.ENCRIPTION;
                break;
            case 2:
                mainMenuRequest = View.UserRequest.DECRYPTION;
                break;
            default:
                throw new RuntimeException("invalid choice");
        }

        File varTmpDir;
        String filepath;
        do {
            System.out.println("Enter file path:");
            filepath = reader.nextLine();
            varTmpDir = new File(filepath);
        } while (! (varTmpDir.exists()));

        Code code = new Caesar();

        FileManipulator manipulator = new RegularManipulator(code, mainMenuRequest);
        UserRequestMission userRequestMission = new UserRequestMission(manipulator, filepath, mainMenuRequest);
        return userRequestMission;
    }
}
