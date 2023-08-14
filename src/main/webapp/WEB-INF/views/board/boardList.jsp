<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param value="�Խ���" name="title"/>
</jsp:include>

<section>
	<h1>�Խ����Դϴ�</h1>
	<ul class="list-group">
		<c:if test="${empty boards}">
			<li>��ȸ�� �Խñ��� �����ϴ�.</li>
		</c:if>
		<c:if test="${not empty boards}">
			<c:forEach items="${boards}" var="board">
				<li class="list-group-item">
				${board.title}<br>
				${board.postCreatedAt} �͸�
				</li>
			</c:forEach>
		</c:if>
	</ul>
</section>

<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>