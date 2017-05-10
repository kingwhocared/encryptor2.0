/**
 * Created by stannis on 08/05/17.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Hello, World");
        UserRequestMission requestMission = View.GetUserRequest();
        Model.DoUserRequest(requestMission);
    }

}
