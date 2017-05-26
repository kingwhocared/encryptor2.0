import java.time.Duration;
import java.time.Instant;

/**
 * Created by stannis on 26/05/17.
 */
public class ASyncEncryptionProcess extends EncryptionProcess {

    public ASyncEncryptionProcess(FileManipulator file_manipulator) {
        super(file_manipulator);
    }

    @Override
    public Thread BeginFileManipulationProcess(String file_input, String file_output) {
        this.file_input = file_input;
        Thread thread = this.BeginManipulation(file_input, file_output);
        return thread;
    }

    @Override
    Thread BeginManipulation(String file_input, String file_output) {
        Thread thread = new FileHandler(this.file_manipulator, file_input, file_output);
        thread.start();
        return thread;
    }

    private class FileHandler extends Thread {
        FileManipulator fileManipulator;
        String input;
        String output;

        public FileHandler(FileManipulator fileManipulator, String input, String output) {
            this.fileManipulator = fileManipulator;
            this.input = input;
            this.output = output;
        }

        public void run() {
            EncryptionProcess robb_stark = new SyncedEncryptionProcess(this.fileManipulator);
            robb_stark.BeginFileManipulationProcess(this.input, this.output);
        }
    }
}
