<%@ include file="/WEB-INF/jsps/include.jsp" %>

<c:if test="${not empty allAccounts}">
	<table border="1">
	<tr><th>id</th><th>Screen Name</th><th>accessToken</th><th>accessTokenSecret</th></tr>
	<c:forEach var="account" items="${allAccounts}">
		<tr><td>${account.id}</td><td>${account.screenName}</td>
		<td>${account.accessToken}</td><td>${account.accessTokenSecret}</td>
		</tr>
	</c:forEach>
	</table>
</c:if>
