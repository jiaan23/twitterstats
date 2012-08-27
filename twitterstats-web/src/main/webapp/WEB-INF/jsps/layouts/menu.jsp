<%@ include file="/WEB-INF/jsps/include.jsp" %>

<ul id="menu">
	<li <c:if test="${param['page'] == 'home'}"> class="selected"</c:if>>
		<a href="<c:url value="/" />">Home</a>
	</li>
	<li <c:if test="${param['page'] == 'settings'}"> class="selected"</c:if>>
		<a href="<c:url value="/settings"/>">Settings</a>
	</li>
	<li <c:if test="${param['page'] == 'contact'}"> class="selected"</c:if>>
		<a href="<c:url value="/contact"/>">Contact</a>
	</li>
</ul>