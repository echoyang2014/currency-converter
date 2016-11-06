package de.nazaruk.services;

/**
 * Created by nazaruk on 11/5/16.
 */
public interface SecurityService {

    String findLoggedInUsername();

    void autologin(String username, String password);
}
