 
package javaux;

public class User {
    private String username;
    private String password;
    private String email;
    private String question;
    private String answer;
    private String birthday;
    private String gender;

    // Constructor
    public User(String username, String password, String email, String question, String answer, String birthday, String gender) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.question = question;
        this.answer = answer;
        this.birthday = birthday;
        this.gender = gender;
    }

    // Getters for all fields
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getGender() {
        return gender;
    }
    
}
