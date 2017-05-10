/**
 * Created by stannis on 09/05/17.
 */
public class Model {

    public static void DoUserRequest(UserRequestMission userRequestMission){
        String outputfile = generateOutPutFileName(userRequestMission.missionPath, userRequestMission.request);
        userRequestMission.missionFileHandler.ManipulateFile(userRequestMission.missionPath, outputfile);
    }


    private static String generateOutPutFileName(String inputfile, View.MainUserRequest request) {
        String outputfile = inputfile;
        switch (request) {
            case ENCRIPTION:
                outputfile = inputfile + ".encrypted";
                break;
            case DECRYPTION:
                outputfile = inputfile.split("\\.")[0] + "_decrypted." + inputfile.split("\\.")[1];
                break;
        }
        return outputfile;
    }
}
