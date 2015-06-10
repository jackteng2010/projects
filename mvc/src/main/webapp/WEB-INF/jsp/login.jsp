<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="common/header.jsp" %>
    <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Please Sign In</h3>
                    </div>
                    <div class="panel-body">

						<c:if test="${not empty param.error}">
							<div class="alert alert-danger" role="alert">
							  <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
							  <span class="sr-only">Error:</span>
							  ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}  
							</div>
						</c:if>
												
                        <form method="POST" action="${pageContext.request.contextPath}/j_spring_security_check" role="form">
                            <fieldset>
                                <div class="form-group">
                                   <input type="text" name="j_username" autofocus  class="form-control" />
                                </div>
                                <div class="form-group">
                                    <input type="password" name="j_password"  class="form-control" />
                                </div>
                                <div class="checkbox">
                                    <label>
                                        <input name="remember" type="checkbox" value="Remember Me">Remember Me
                                    </label>
                                </div>
                                <input type="submit" value="Login" class="btn btn-lg btn-success btn-block"/>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

<%@include file="common/footer.jsp" %>