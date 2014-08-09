<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.codehaus.jackson.map.ObjectMapper,java.util.Map" %>
<% 
String callback = request.getParameter("callback");
Map<String, Object> model = (Map<String, Object>) request.getAttribute("org.springframework.web.servlet.ViewRendererServlet.MODEL");
model.put("status", "1");
String json = new ObjectMapper().writeValueAsString(model);
if(callback != null && !callback.isEmpty()){
	json = callback + "(" + json + ")";
}
response.setContentType("application/json;charset=UTF-8");
response.getWriter().write(json);
%>