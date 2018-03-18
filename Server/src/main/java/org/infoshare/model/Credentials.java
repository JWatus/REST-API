package org.infoshare.model;

import java.io.Serializable;

public final class Credentials implements Serializable {
    private static final long serialVersionUID = -6470090944414208496L;
    private String username;
    private String password;

    public Credentials() {
    }

    public Credentials(String user, String password) {
        this.username = user;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
