package services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.SecurityContext;

import dtos.Common.UserInfoDTO;
import entity.Lector;
import entity.Student;
import entity.StudyCourse;
import services.interfaces.IPublicService;
import services.interfaces.ISecurityRealm;

public class PublicServiceBean implements IPublicService {

    @Inject
	ISecurityRealm security;
    
    @PersistenceContext(unitName = "primary")
    EntityManager em;

    public UserInfo getUserInfo(SecurityContext ctx) {
        String user = ctx.getUserPrincipal() == null ? "Anonymous" : ctx.getUserPrincipal().getName();
        return ctx.getUserPrincipal() == null ? new UserInfo(user, new ArrayList<>()) : new UserInfo(user, security.getRoles(user));
    }

    public StudyCourse getStudyCourse(long uid) {
        return em.find(StudyCourse.class, uid);
    }
    
    public String updatePassword(SecurityContext ctx, String oldPassword, String newPassword) {
        if (ctx.getUserPrincipal() == null)
            return "Unathenticated user";

        return security.updatePassword(ctx.getUserPrincipal().getName(), oldPassword, newPassword);
    }

    @Override
    public List<StudyCourse> getStudyCourses() {
        TypedQuery<StudyCourse> query = em.createNamedQuery("StudyCourse.getAll", StudyCourse.class);
        return query.getResultList();
    }

    @Override
    public UserInfoDTO getNames(SecurityContext ctx) {

        if (ctx.getUserPrincipal().getName().equals("admin")) {
            UserInfoDTO dto = new UserInfoDTO("admin", "admin");
            return dto;
        }

        TypedQuery<Lector> query = em.createNamedQuery("Lector.findUid", Lector.class);
        query.setParameter("username", ctx.getUserPrincipal().getName());
        List<Lector> reply = query.getResultList();
        if (!reply.isEmpty()){
            UserInfoDTO dto = new UserInfoDTO(reply.get(0).getName(), reply.get(0).getSurname());
            return dto;
        }

        TypedQuery<Student> query2 = em.createNamedQuery("Student.findUid", Student.class);
        query2.setParameter("username", ctx.getUserPrincipal().getName());
        List<Student> reply2 = query2.getResultList();
        if (!reply2.isEmpty()){
            UserInfoDTO dto = new UserInfoDTO(reply2.get(0).getName(), reply2.get(0).getSurname());
            return dto;
        } 

        return null;
    }
}
