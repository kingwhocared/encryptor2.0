import java.io.File;
import java.time.Duration;
import java.time.Instant;
/**
 * Created by stannis on 09/05/17.
 */
public class Model {

    public static void DoUserRequest(UserRequestMission userRequestMission){
        switch (userRequestMission.targetType) {
            case SINGLE_FILE:
                String outputfile = generateOutPutFileNameSingleFileTarget(userRequestMission.missionPath, userRequestMission.request);
                EncryptionProcess encryptionProcess = new EncryptionProcess(userRequestMission.missionFileHandler);
                encryptionProcess.BeginFileManipulationProcess(userRequestMission.missionPath,outputfile);
                return;
            default:
                HandleFile file_handler = null;
                switch (userRequestMission.targetType) {
                    case FOLDER_SYNC:
                        file_handler = (x,y,z) -> {handle_sync(x,y,z);};
                        break;
                    case FOLDER_ASYNC:
                        file_handler = (x,y,z) -> {handle_async(x,y,z);};
                        break;
                }
                Instant begin_time = Instant.now();
                System.out.println("Beginning to work on the directory...");
                String directory_input_path = userRequestMission.missionPath;
                File[] files = new File(directory_input_path).listFiles();
                for (File file : files) {
                    if (file.isDirectory()) {
                        String input = file.getName();
                        String ouput = generateOutPutFileDirectoryTarget(input, userRequestMission.request);
                        file_handler.Handle(userRequestMission.missionFileHandler, input, ouput);
                    }
                }
                System.out.println("Finished working on the directory.");
                Instant end_time = Instant.now();
                Duration duration = Duration.between(begin_time, end_time);
                System.out.println("Time took to process directory: " + duration.getSeconds() + " seconds.");
        }


    }

    private static String generateOutPutFileNameSingleFileTarget(String inputfile, View.UserRequestObjective request) {
        String outputfile = inputfile;
        switch (request) {
            case ENCRYPTION:
                outputfile = inputfile + ".encrypted";
                break;
            case DECRYPTION:
                outputfile = inputfile.split("\\.")[0] + "_decrypted." + inputfile.split("\\.")[1];
                break;
        }
        return outputfile;
    }

    private static String generateOutPutFileDirectoryTarget(String inputfile, View.UserRequestObjective request) {
        String outputfile = inputfile;
        switch (request) {
            case ENCRYPTION:
                outputfile = inputfile + ".encrypted";
                break;
            case DECRYPTION:
                outputfile = inputfile.split("\\.")[0] + "_decrypted." + inputfile.split("\\.")[1];
                break;
        }
        return outputfile;
    }

    private static void handle_sync(FileManipulator fileManipulator, String file_input, String file_output) {
        EncryptionProcess encryptionProcess = new EncryptionProcess(fileManipulator);
        encryptionProcess.BeginFileManipulationProcess(file_input,file_output);
    }

    private static void handle_async(FileManipulator fileManipulator, String file_input, String file_output) {
        new FileHandler(fileManipulator,file_input,file_output).run();
    }

    private interface HandleFile {
        void Handle(FileManipulator fileManipulator, String file_input, String file_output);
    }

    private static class FileHandler extends Thread {
        FileManipulator fileManipulator;
        String input;
        String output;

        public FileHandler(FileManipulator fileManipulator, String input, String output) {
            this.fileManipulator = fileManipulator;
            this.input = input;
            this.output = output;
        }

        public void run() {
            EncryptionProcess encryptionProcess = new EncryptionProcess(fileManipulator);
            encryptionProcess.BeginFileManipulationProcess(input,output);
        }
    }
}
