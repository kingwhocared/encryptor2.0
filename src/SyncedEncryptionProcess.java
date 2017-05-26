/**
 * Created by stannis on 26/05/17.
 */
public class SyncedEncryptionProcess extends EncryptionProcess {

    public SyncedEncryptionProcess(FileManipulator file_manipulator) {
        super(file_manipulator);
    }

    @Override
    Thread BeginManipulation(String file_input, String file_output) {
        this.file_manipulator.ManipulateFile(file_input, file_output);
        return null;
    }
}
