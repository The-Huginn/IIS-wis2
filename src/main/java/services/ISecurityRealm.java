package services;

public interface ISecurityRealm {
    public String updatePassword(final String username, final String oldPassword, final String newPassword);
}
