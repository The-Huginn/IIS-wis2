package services.interfaces;

import java.io.Serializable;
import java.util.List;

import javax.ws.rs.core.SecurityContext;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import entity.StudyCourse;

public interface IPublicService {

    @XmlRootElement
    public class UserInfo implements Serializable {
        @XmlElement
        private String username;
        @XmlElement
        private List<String> roles;

        public UserInfo(String username_, List<String> roles_) {
            username = username_;
            roles = roles_;
        }
    }

    public UserInfo getUserInfo(SecurityContext ctx);

    public List<StudyCourse> getStudyCourses();

    public StudyCourse getStudyCourse(long uid);

    public String updatePassword(SecurityContext ctx, String oldPassword, String newPassword);
}
