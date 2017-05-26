import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.lang.Thread.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by stannis on 09/05/17.
 */
public class Model {

    private static LinkedList<Thread> threads;

    public static void DoUserRequest(UserRequestMission userRequestMission){
        threads = new LinkedList<Thread>();
        switch (userRequestMission.targetType) {
            case SINGLE_FILE:
                String outputfile = generateOutPutFileNameSingleFileTarget(userRequestMission.missionPath, userRequestMission.request);
                EncryptionProcess encryptionProcess = new EncryptionProcess(userRequestMission.missionFileHandler);
                encryptionProcess.BeginFileManipulationProcess(userRequestMission.missionPath,outputfile);
                return;
            case FOLDER_SYNC:
                break;
            case FOLDER_ASYNC:
                break;
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
                createOutputDirectory(directory_input_path, userRequestMission.request);
                File[] files = new File(directory_input_path).listFiles();
                for (File file : files) {
                    if (! file.isDirectory()) {
                        String input = file.getAbsolutePath();
                        String output = generateOutPutFileDirectoryTarget(input, userRequestMission.request);
                        file_handler.Handle(userRequestMission.missionFileHandler, input, output);
                    }
                }

                for (Thread thread : threads) {
                    try {
                        thread.join();
                    }catch (Throwable throwable) {}
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

    private static void createOutputDirectory(String input, View.UserRequestObjective goal) {
        String output_dir_name = null;
        switch (goal) {
            case ENCRYPTION:
                output_dir_name = "encrypted";
                break;
            case DECRYPTION:
                output_dir_name = "decrypted";
                break;
        }
        String new_dir = Paths.get(input).getParent().toString() + "/" + output_dir_name + "/";
        try {
            Files.createDirectories(Paths.get(new_dir));
        }
        catch (Throwable poop) {
            throw new RuntimeException();
        }
        return;
    }

    private static String generateOutPutFileDirectoryTarget(String inputfile, View.UserRequestObjective request) {
        String new_dir_name = null;
        switch (request) {
            case ENCRYPTION:
                new_dir_name = "encrypted";
                break;
            case DECRYPTION:
                new_dir_name = "decrypted";
                break;
        }
        String outputfile = Paths.get(inputfile).getParent().getParent().toString() + "/" + new_dir_name + "/" + (new File(inputfile).getName());
        return outputfile;
    }

    private static void handle_sync(FileManipulator fileManipulator, String file_input, String file_output) {
        EncryptionProcess encryptionProcess = new EncryptionProcess(fileManipulator);
        encryptionProcess.BeginFileManipulationProcess(file_input,file_output);
    }

    private static void handle_async(FileManipulator fileManipulator, String file_input, String file_output) {
        new FileHandler(fileManipulator,file_input,file_output).start();
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
            threads.add(this);
            EncryptionProcess encryptionProcess = new EncryptionProcess(fileManipulator);
            encryptionProcess.BeginFileManipulationProcess(input,output);
        }
    }
}
