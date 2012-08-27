<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

home<br />
<c:choose>
<c:when test="${not empty screenName}">
	<div>${screenName}</div>
	<div>Following:	<c:forEach var="following" items="${followings}">${following}, </c:forEach></div>


</c:when>
<c:otherwise>
	<a href="<c:url value="/twitter/auth" />"><img src="<c:url value="/resources/img/sign-in-with-twitter-l.png" />" /></a>
</c:otherwise>
</c:choose>

