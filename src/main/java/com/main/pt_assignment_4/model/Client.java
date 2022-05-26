package com.main.pt_assignment_4.model;

import java.io.Serializable;
import java.util.Objects;

public class Client implements Serializable {
    private String email;
    private String password;

    public Client(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return email.equals(client.email) && password.equals(client.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }

    @Override
    public String toString() {
        return "Client{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
