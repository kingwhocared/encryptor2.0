/**
 * Created by stannis on 10/05/17.
 */
public class UserRequestMission {
    public FileManipulator missionFileHandler;
    public String missionPath;
    public View.UserRequest request;

    public UserRequestMission(FileManipulator missionFileHandler, String missionPath, View.UserRequest request) {
        this.missionFileHandler = missionFileHandler;
        this.missionPath = missionPath;
        this.request = request;
    }
}
