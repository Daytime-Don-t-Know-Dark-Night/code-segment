package java8.reflex;

public class Target1 {

    public String userId;

    public String name;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * *****************************************************************************************************************
     */

    public int func11() {
        return 0;
    }

    public void func12() {
        System.out.println("1-2");
    }

    public String func13(String params) {
        return params + ": invoke";
    }

    public String func14(String... params) {
        return params[0];
    }

}
