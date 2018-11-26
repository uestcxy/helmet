<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../include/common-head.jsp"></jsp:include>
<body>
<c:choose>
    <c:when test="${isLogin}">
        <jsp:include page="../include/nav-head-logined.jsp"></jsp:include>
    </c:when>
    <c:otherwise>
        <jsp:include page="../include/nav-head-notlogin.jsp"></jsp:include>
    </c:otherwise>
</c:choose>
<main>
    <div class="section">
        <!--   Icon Section   -->
        <div class="row">
            <div class="col s12"  style="margin-top: 100px; color: #000;">
                <ul class="collection with-header">
                    <li class="collection-header"><h4>访问操作！可能的原因：</h4></li>
                    <li class="collection-item">1、您尚未登录；</li>
                    <li class="collection-item">2、您的登录已过期；</li>
                    <li class="collection-item">3、您输入的网址不存在；</li>
                </ul>
            </div>
            <div class="col s12 center-align">
                <a class="waves-effect waves-light btn-large pulse" href="/index" target="_top"><i class="material-icons left">cloud</i>进入首页</a>
            </div>
        </div>
    </div>
</main>
</main>
<jsp:include page="../include/common-footer.jsp"></jsp:include>
</body>
</html>

