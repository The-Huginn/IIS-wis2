package services;

import java.util.List;

public interface IAdminSecurityRealm {

	public String addRoles(final String username, final List<String> roles);

    public String removeRoles(final String username, final List<String> toRemove);

    public String removeUser(final String username);

    public String addUser(final String username, final String password);
}
