package application;

public final class UserSession {

    private static UserSession instance;

    private static String freshSignUpUsername="";
    private static String email="";
    private static String privileges="";
    private static String username="";
    private static Short age=-1;
    private static Float weight = -1f;
    private static Float height= -1f;
    private static String objective="";
    private static String gender="";
    private static String AMR="";

    public static String getFreshSignUpUsername() {
        return freshSignUpUsername;
    }

    public UserSession(String email, String username ,Short age, String gender, Float weight,Float height, String objective, String privileges, String AMR) {
        this.email = email;
        this.privileges = privileges;
        this.username = username;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.freshSignUpUsername= "";
        this.objective = objective;
        this.gender = gender;
        this.AMR = AMR;
    }
    public UserSession(String freshSignUpUsername) {
        this.freshSignUpUsername= freshSignUpUsername;
    }

    public UserSession() {
    }

    public static void getInstance(String email, String username, Short age,String gender, Float weight, Float height,  String objective, String privileges, String AMR) {
        cleanUserSession();
        if(instance == null) {
            instance = new UserSession( email,  username , age,  gender,  weight,  height,  objective,  privileges, AMR);
        }
    }
    public static void getInstance(String freshSignUpUsername) {
        cleanUserSession();
        if(instance == null) {
            instance = new UserSession(freshSignUpUsername);
        }
    }

    public static void getInstance() {
        cleanUserSession();
    }

    public static void cleanUserSession() {
        instance = null;
    }

    public static void echo() {
        final StringBuilder sb = new StringBuilder("UserSession{");
        sb.append("newUser='").append(freshSignUpUsername).append('\'');
        sb.append("email='").append(email).append('\'');
        sb.append(", privileges='").append(privileges).append('\'');
        sb.append(", username='").append(username).append('\'');
        sb.append(", age=").append(age);
        sb.append(", weight=").append(weight);
        sb.append(", height=").append(height);
        sb.append(", objective='").append(objective).append('\'');
        sb.append(", gender='").append(gender).append('\'');
        sb.append(", AMR='").append(AMR).append('\'');
        sb.append('}');
        System.out.println(sb.toString());
    }

    public static Float getWeight() {
        return weight;
    }

    public static Float getHeight() {
        return height;
    }
}