package services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Stateless
public class SecurityRealmBean implements IAdminSecurityRealm, ISecurityRealm {

	private final String USERS_FILE = "/application-users.properties";
	private final String ROLES_FILE = "/application-roles.properties";
	private final String APPLICATION_REALM = ":ApplicationRealm:";
	private final String PATH = System.getProperty("jboss.server.config.dir");

	private String encode(final String username, final String password) throws NoSuchAlgorithmException {

		String clearText = username + APPLICATION_REALM + password;
		byte messageDigest[] = MessageDigest.getInstance("MD5").digest(clearText.getBytes(StandardCharsets.UTF_8));

		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < messageDigest.length; i++)
			hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
		return hexString.toString();
	}

	private List<String> getRoles(final String line, final String username) {
		String stringRoles = line.substring((username + "=").length());
		List<String> allRoles = new ArrayList<>(Arrays.asList(stringRoles.split(",")));

		return allRoles;
	}

	@RolesAllowed("admin")
	public String addRoles(
		@NotNull(message = "username cannot be null [AdminService.addRoles]") final String username,
		@NotNull(message = "roles cannot be null [AdminService.addRoles]") final List<String> roles) {
		String previousUser = null;
		String newFile = "";
		String serializedRoles = roles.stream()
				.collect(Collectors.joining(","));

		try (BufferedReader reader = new BufferedReader(new FileReader(PATH + ROLES_FILE))) {
			
			String line;
			while ((line = reader.readLine()) != null) {
				line = line.trim();

				if (line.startsWith(username + "="))
					previousUser = line;
				else
					newFile += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		if (previousUser != null) {
			List<String> allRoles = getRoles(previousUser, username);
			allRoles.removeAll(roles);
			allRoles.addAll(roles);
			serializedRoles = allRoles.stream().collect(Collectors.joining(","));
		}

		previousUser = username + "=" + serializedRoles;

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH + ROLES_FILE, false))) {

			writer.write(newFile);
			
			writer.newLine();
			writer.write(previousUser);
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}

		return null;
	}

	@RolesAllowed("admin")
	public String removeRoles(
		@NotNull(message = "username cannot be null [AdminService.removeRoles]") final String username,
		@NotNull(message = "roles cannot be null [AdminService.removeRoles]") final List<String> toRemove
		) {
		String previousUser = null;
		String newFile = "";

		try (BufferedReader reader = new BufferedReader(new FileReader(PATH + ROLES_FILE))) {
			
			String line;
			while ((line = reader.readLine()) != null) {
				line = line.trim();

				if (line.startsWith(username + "="))
					previousUser = line;
				else
					newFile += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}

		if (previousUser == null)
			return "User with username (" + username + ") has no roles";

			
		List<String> allRoles = getRoles(previousUser, username);
		allRoles.removeAll(toRemove);
		String remainingRoles = allRoles.stream()
											.collect(Collectors.joining(","));

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH + ROLES_FILE, false))) {

			writer.write(newFile);
			
			writer.newLine();
			writer.write(username + "=" + remainingRoles);
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}

		return null;
	}

	private String removeUserFromFile(
		final String username,
		final String Path
		) {

		String newFile = "";
		try (Stream<String> stream = Files.lines(Paths.get(Path))) {

			newFile = stream.filter(line -> !line.startsWith(username + "="))
						.collect(Collectors.joining("\n"));

		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(Path, false))) {

			writer.write(newFile);
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}

		return null;
	}

	@RolesAllowed("admin")
	public String removeUser(
		@NotNull(message = "username cannot be null [AdminService.removeUser]") final String username
		) {

		String reply = removeUserFromFile(username, PATH + USERS_FILE);

		if (reply != null)
			return reply;

		return removeUserFromFile(username, PATH + ROLES_FILE);
	}

	@RolesAllowed("admin")
	public String addUser(
		@NotNull(message = "username cannot be null [AdminService.addUser]")
		@Size(min = 5, max = 20, message = "username size must be between 5-20 chars [AdminService.addUser]")
		@Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]+$", message = "username must contain only valid chars [a-zA-Z0-9] and start with char[AdminService.addUser]")
		String username,
		@NotNull(message = "password cannot be null [AdminService.addUser]")
		@Size(min = 7, max = 20, message = "password size must be between 5-20 chars [AdminService.addUser]")
		@Pattern(regexp = "^[a-zA-Z0-9@%!#^?.$]+$", message = "password must contain only valid chars [a-zA-Z0-9@%!#^?.$] [AdminService.addUser]")
		String password
		) {

		try (Stream<String> stream = Files.lines(Paths.get(PATH + USERS_FILE))) {
			if (stream.anyMatch(line -> line.trim().startsWith(username + "=")))
				throw new Exception("User with username (" + username + ") already exists");
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH + USERS_FILE, true))) {

			writer.newLine();
			writer.write(username + "=" + encode(username, password));
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}

		return null;
	}

	public String updatePassword(
		@NotNull(message = "username cannot be null [AdminService.updatePassword]") String username,
		@NotNull(message = "old password cannot be null [AdminService.updatePassword]") String oldPassword,
		@NotNull(message = "new password cannot be null [AdminService.updatePassword]")
		@Size(min = 7, max = 20, message = "new password size must be between 5-20 chars [AdminService.updatePassword]")
		@Pattern(regexp = "^[a-zA-Z0-9@%!#^?.$]+$", message = "new password must contain only valid chars [a-zA-Z0-9@%!#^?.$] [AdminService.updatePassword]")
		String newPassword
		) {
		String newFile = "";
		
		try (BufferedReader reader = new BufferedReader(new FileReader(PATH + USERS_FILE))) {
			
			final String lineToRemove = username + "=" + encode(username, oldPassword);

			String line;
			boolean match = false;
			while ((line = reader.readLine()) != null) {
				line = line.trim();

				if (line.equals(lineToRemove))
					match = true;
				else
					newFile += line;
			}
			if (!match)
				throw new Exception("Old password does not match");

		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH + USERS_FILE, false))) {
			writer.write(newFile);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}

		return addUser(username, newPassword);
	}
}
