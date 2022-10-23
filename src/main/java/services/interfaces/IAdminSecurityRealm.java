package services.interfaces;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public interface IAdminSecurityRealm {

    public String addRoles(
            @NotNull(message = "username cannot be null [AdminService.addRoles]") final String username,
            @NotNull(message = "roles cannot be null [AdminService.addRoles]") final List<String> roles);

    public String removeRoles(
            @NotNull(message = "username cannot be null [AdminService.removeRoles]") final String username,
            @NotNull(message = "roles cannot be null [AdminService.removeRoles]") final List<String> toRemove);

    public String removeUser(
            @NotNull(message = "username cannot be null [AdminService.removeUser]") final String username);

    /**
     * @param username
     * @param password Base64 encoded
     * @return
     */
    public String addUser(
            @NotNull(message = "username cannot be null [AdminService.addUser]")
            @Size(min = 5, max = 20, message = "username size must be between 5-20 chars [AdminService.addUser]")
            @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]+$", message = "username must contain only valid chars [a-zA-Z0-9] and start with char[AdminService.addUser]")
            String username,
            @NotNull(message = "password cannot be null [AdminService.addUser]")
            @Size(min = 7, max = 20, message = "password size must be between 5-20 chars [AdminService.addUser]")
            @Pattern(regexp = "^[a-zA-Z0-9@%!#^?.$]+$", message = "password must contain only valid chars [a-zA-Z0-9@%!#^?.$] [AdminService.addUser]")
            String password);

    public List<String> getUsers();

    public List<String> getRoles(@NotNull final String username);
}
