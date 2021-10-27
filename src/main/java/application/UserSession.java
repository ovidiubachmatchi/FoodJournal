package application;

public final class UserSession {

    private static UserSession instance;

    private static String email="";
    private static String privileges="";
    private static String username="";
    private static Short age=-1;
    private static Short weight=-1;
    private static Short height=-1;
    private static String objective="";

    public UserSession(String email, String privileges, String username, Short age, Short weight, Short height, String objective) {
        this.email = email;
        this.privileges = privileges;
        this.username = username;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.objective = objective;
    }

    public static UserSession getInstance(String email, String privileges, String username, Short age, Short weight, Short height, String objective) {
        if(instance == null) {
            instance = new UserSession(email,privileges,username,age,weight,height,objective);
        }
        return instance;
    }

    public static void cleanUserSession() {
        email = "";
        privileges = "";
        username = "";
        age = -1;
        weight = -1;
        height = -1;
        objective = "";
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
        sb.append('}');
        System.out.println(sb.toString());
    }
}