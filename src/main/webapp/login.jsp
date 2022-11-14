<%--
  ~ This file is copied and adjust from https://github.com/kiegroup/appformer/ repository. Thanks for that!
  --%>
<%@ page import="java.util.Locale" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    Locale locale = null;
    try {
        locale = new Locale( request.getParameter( "locale" ) );
    } catch ( Exception e ) {
        locale = request.getLocale();
    }
%>
<i18n:bundle id="bundle" baseName="org.dashbuilder.client.resources.i18n.LoginConstants" locale='<%= locale %>' />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" class="login-pf">
<head>
    <title>Dashbuilder</title>
    <link rel="icon" href="favicon.ico" type="image/x-icon">
</head>

<body>
<div class="container">
    <div class="row">
        <div class="col-sm-6 col-md-5 col-lg-4 login">
            <p><strong><i18n:message key="welcome">Welcome to Wis2 !</i18n:message></strong></p>
            <c:if test="${param.message != null}">
                <div class="alert alert-danger">
                    <span class="pficon-layered">
                      <span class="pficon pficon-error-octagon"></span>
                      <span class="pficon pficon-error-exclamation"></span>
                    </span>
                    <%=request.getParameter("message")%>
                </div>
            </c:if>
            <form class="form-horizontal" role="form" action="j_security_check" method="post">
                <div class="form-group">
                    <label for="j_username" class="col-sm-2 col-md-2 control-label"><i18n:message key="userName">Username</i18n:message></label>
                    <div class="col-sm-10 col-md-10">
                        <input type="text" class="form-control" id="j_username" name="j_username" placeholder="admin" tabindex="1" autofocus>
                    </div>
                </div>
                <div class="form-group">
                    <label for="j_password" class="col-sm-2 col-md-2 control-label"><i18n:message key="password">Password</i18n:message></label>
                    <div class="col-sm-10 col-md-10">
                        <input type="password" class="form-control" id="j_password" name="j_password" placeholder="admin" tabindex="2">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-xs-8 col-sm-offset-2 col-sm-6 col-md-offset-2 col-md-6">
                        <%--
                                                <div class="checkbox">
                                                    <label>
                                                        <input id="nosplash" name="nosplash" type="checkbox" tabindex="3"> Deactivate Splash Screen
                                                    </label>
                                                </div>
                        --%>
                    </div>
                    <div class="col-xs-4 col-sm-4 col-md-4 submit">
                        <button type="submit" class="btn btn-primary btn-lg" tabindex="4"><i18n:message key="signIn">Sign In</i18n:message></button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>