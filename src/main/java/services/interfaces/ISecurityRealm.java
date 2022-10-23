package services.interfaces;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public interface ISecurityRealm {

    /**
     * @param username
     * @param oldPassword Base64 encoded
     * @param newPassword Base64 encoded
     * @return
     */
    public String updatePassword(
		@NotNull(message = "username cannot be null [AdminService.updatePassword]") String username,
		@NotNull(message = "old password cannot be null [AdminService.updatePassword]") String oldPassword,
		@NotNull(message = "new password cannot be null [AdminService.updatePassword]")
		@Size(min = 7, max = 20, message = "new password size must be between 5-20 chars [AdminService.updatePassword]")
		@Pattern(regexp = "^[a-zA-Z0-9@%!#^?.$]+$", message = "new password must contain only valid chars [a-zA-Z0-9@%!#^?.$] [AdminService.updatePassword]")
		String newPassword
		);
}
