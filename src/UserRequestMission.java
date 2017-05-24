/**
 * Created by stannis on 10/05/17.
 */
public class UserRequestMission {
    public enum UserRequestTargetType {SINGLE_FILE, FOLDER_SYNC, FOLDER_ASYNC}

    public FileManipulator missionFileHandler;
    public String missionPath;
    public View.UserRequestObjective request;
    public UserRequestTargetType targetType;

    public UserRequestMission(FileManipulator missionFileHandler, String missionPath, View.UserRequestObjective request, UserRequestTargetType targetType) {
        this.missionFileHandler = missionFileHandler;
        this.missionPath = missionPath;
        this.request = request;
        this.targetType = targetType;
    }
}
