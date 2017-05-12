/**
 * Created by stannis on 12/05/17.
 */
import java.time.Duration;
import java.time.Instant;

public class EncryptionProcess {
    private FileManipulator file_manipulator;
    private Instant begin_time;
    private String file_input;
    public EncryptionProcess(FileManipulator file_manipulator) {
        this.file_manipulator = file_manipulator;
    }

    public void BeginFileManipulationProcess(String file_input, String file_output) {
        this.BeginEvent();
        this.file_manipulator.ManipulateFile(file_input, file_output);
        this.file_input = file_input;
        this.EndEvent();
    }

    private void BeginEvent() {
        this.begin_time = Instant.now();
        System.out.println("Starting working on file: " + this.file_input);
    }

    private void EndEvent() {
        Instant end_time = Instant.now();
        System.out.println("Succesfully analized file: " + this.file_input
            + "\nTime taken: " + Duration.between(this.begin_time, end_time).toString());
    }
}
