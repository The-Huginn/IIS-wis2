/**
 * @author Rastislav Budinsky
 * @file LoginServlet.java
 * @brief This file is copied and adjust from https://github.com/kiegroup/appformer/ repository. Thanks for that!
 */

package security;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        
    }

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,
               resp);
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp) throws ServletException, IOException {
        // perform optional check and redirect in case no authenticated request is available
        if (req.getUserPrincipal() == null) {
            try {
                // clean up session
                req.logout();
                req.getSession().invalidate();
            } catch (Exception e) {
                // to avoid cases where logout causes issues for first request
            }
            resp.sendError(400, "Unauthenticated");;

            return;
        }

        // StringBuilder redirectTarget = new StringBuilder(displayAfterLoginUri);
        // resp.sendRedirect(redirectTarget.toString());
        resp.setStatus(200);
    }
}
