/**
 * Created by stannis on 12/05/17.
 */
import java.time.Duration;
import java.time.Instant;

public abstract class EncryptionProcess {
    protected FileManipulator file_manipulator;
    protected Instant begin_time;
    protected String file_input;

    public EncryptionProcess(FileManipulator file_manipulator) {
        this.file_manipulator = file_manipulator;
    }

    public Thread BeginFileManipulationProcess(String file_input, String file_output) {
        this.file_input = file_input;
        this.BeginEvent();
        Thread thread = this.BeginManipulation(file_input, file_output);
        this.EndEvent();
        return thread;
    }

    abstract Thread BeginManipulation(String file_input, String file_output);

    private void BeginEvent() {
        this.begin_time = Instant.now();
        System.out.println("Starting working on file: " + this.file_input);
    }

    private void EndEvent() {
        Instant end_time = Instant.now();
        System.out.println("Successfully analyzed file: " + this.file_input
            + "\nTime taken: " + Duration.between(this.begin_time, end_time).getSeconds() + " seconds");
    }
}
