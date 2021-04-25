package spring.form;

public class AccountManagerForm {
    public static final int[] PAGE_SIZE_OPTIONS = {5, 10, 50};
    public static final String[] SORTABLE_FIELDS = {"username", "firstName", "lastName", "email", "accountType"};

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public User[] users;

    public static class User {
        private String username;
        private String authority;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAuthority() {
            return authority;
        }

        public void setAuthority(String authority) {
            this.authority = authority;
        }

    }
}