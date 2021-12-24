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
    private static int dailyKcal=0;

    public static String getFreshSignUpUsername() {
        return freshSignUpUsername;
    }

    public UserSession(String email, String username ,Short age, String gender, Float weight,Float height, String objective, String privileges, String AMR, int dailyKcal) {
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
        this.dailyKcal = dailyKcal;
    }
    public UserSession(String freshSignUpUsername) {
        this.freshSignUpUsername= freshSignUpUsername;
    }

    public UserSession() {
    }

    public static void setDailyKcal(int dailyKcal) {
        UserSession.dailyKcal = dailyKcal;
    }

    public static void setUsername(String username) {
        UserSession.username = username;
    }

    public static void setAge(Short age) {
        UserSession.age = age;
    }

    public static void setObjective(String objective) {
        UserSession.objective = objective;
    }

    public static void setGender(String gender) {
        UserSession.gender = gender;
    }

    public static int getDailyKcal() {
        return dailyKcal;
    }

    public static void setWeight(Float weight) {
        UserSession.weight = weight;
    }

    public static void setHeight(Float height) {
        UserSession.height = height;
    }

    public static String getEmail(){
        return email;
    }

    public static String getPrivileges() {
        return privileges;
    }

    public static String getUsername() {
        return username;
    }

    public static Short getAge() {
        return age;
    }

    public static String getObjective() {
        return objective;
    }

    public static String getGender() {
        return gender;
    }

    public static String getAMR() {
        return AMR;
    }

    public static void getInstance(String email, String username, Short age, String gender, Float weight, Float height, String objective, String privileges, String AMR, int dailyKcal) {
        cleanUserSession();
        if(instance == null) {
            instance = new UserSession( email,  username , age,  gender,  weight,  height,  objective,  privileges, AMR, dailyKcal);
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

    public static Float getWeight() {
        return weight;
    }

    public static Float getHeight() {
        return height;
    }

    public static void setAMR(String AMR) {
        UserSession.AMR = AMR;
    }
}