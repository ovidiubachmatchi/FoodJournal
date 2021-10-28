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

    public static String getFreshSignUpUsername() {
        return freshSignUpUsername;
    }

    public UserSession(String email, String privileges, String username, Short age, Float weight, Float height, String objective, String gender) {
        cleanUserSession();
        this.email = email;
        this.privileges = privileges;
        this.username = username;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.freshSignUpUsername= "";
        this.objective = objective;
        this.gender = gender;
    }
    public UserSession(String freshSignUpUsername) {
        cleanUserSession();
        this.freshSignUpUsername= freshSignUpUsername;
    }

    public static UserSession getInstance(String email, String privileges, String username, Short age, Float weight, Float height, String objective, String gender) {
        if(instance == null) {
            instance = new UserSession(email,privileges,username,age,weight,height,objective,gender);
        }
        return instance;
    }
    public static UserSession getInstance(String freshSignUpUsername) {
        if(instance == null) {
            instance = new UserSession(freshSignUpUsername);
        }
        return instance;
    }

    public static void cleanUserSession() {
        email = "";
        privileges = "";
        username = "";
        age = -1;
        weight = -1f;
        height = -1f;
        objective = "";
        gender = "";
        freshSignUpUsername="";
    }

    public static void echo() {
        final StringBuilder sb = new StringBuilder("UserSession{");
        sb.append("email='").append(email).append('\'');
        sb.append(", privileges='").append(privileges).append('\'');
        sb.append(", username='").append(username).append('\'');
        sb.append(", age=").append(age);
        sb.append(", weight=").append(weight);
        sb.append(", height=").append(height);
        sb.append(", objective='").append(objective).append('\'');
        sb.append(", gender='").append(gender).append('\'');
        sb.append('}');
        System.out.println(sb.toString());
    }
}