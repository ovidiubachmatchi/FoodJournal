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
    private static String weightMeasurement="";
    private static String heightMeasurement="";

    public static String getFreshSignUpUsername() {
        return freshSignUpUsername;
    }

    public UserSession(String email, String privileges, String username, Short age, Float weight, Float height, String objective, String gender,String weightMeasurement, String heightMeasurement) {
        this.email = email;
        this.privileges = privileges;
        this.username = username;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.freshSignUpUsername= "";
        this.objective = objective;
        this.gender = gender;
        this.weightMeasurement=weightMeasurement;
        this.heightMeasurement=heightMeasurement;
    }
    public UserSession(String freshSignUpUsername) {
        this.freshSignUpUsername= freshSignUpUsername;
    }

    public UserSession() {
    }

    public static void getInstance(String email, String privileges, String username, Short age, Float weight, Float height, String objective, String gender, String weightMeasurement, String heightMeasurement) {
        cleanUserSession();
        if(instance == null) {
            instance = new UserSession(email,privileges,username,age,weight,height,objective,gender,weightMeasurement,heightMeasurement);
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
        sb.append(", weightMeasurement='").append(weightMeasurement).append('\'');
        sb.append(", heightMeasurement='").append(heightMeasurement).append('\'');
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