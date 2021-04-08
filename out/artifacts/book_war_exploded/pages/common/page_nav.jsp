<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 2021/2/20
  Time: 18:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--分页条的开始--%>
<div id="page_nav">
    <%--大于才显示--%>
    <C:if test="${requestScope.page.pageNo > 1}">
        <a href="${requestScope.page.url}&pageNo=1">首页</a>
        <a href="${requestScope.page.url}&pageNo=${requestScope.page.pageNo-1}">上一页</a>
    </C:if>


    <%--页码输出的范围+++++--%>
    <C:choose>
        <%--情况 1：如果总页码小于等于 5 的情况，页码的范围是：1-总页码--%>
        <C:when test="${ requestScope.page.pageTotal <= 5 }">
            <C:set var="begin" value="1"/>
            <C:set var="end" value="${requestScope.page.pageTotal}"/>
        </C:when>
        <%--情况 2：总页码大于 5 的情况--%>
        <C:when test="${requestScope.page.pageTotal > 5}">
            <C:choose>
                <%--小情况 1：当前页码为前面 3 个：1，2，3 的情况，页码范围是：1-5.--%>
                <C:when test="${requestScope.page.pageNo <= 3}">
                    <C:set var="begin" value="1"/>
                    <C:set var="end" value="5"/>
                </C:when>
                <%--小情况 2：当前页码为最后 3 个，8，9，10，页码范围是：总页码减 4 - 总页码--%>
                <C:when test="${requestScope.page.pageNo > requestScope.page.pageTotal-3}">
                    <C:set var="begin" value="${requestScope.page.pageTotal-4}"/>
                    <C:set var="end" value="${requestScope.page.pageTotal}"/>
                </C:when>
                <%--小情况 3：4，5，6，7，页码范围是：当前页码减 2 - 当前页码加 2--%>
                <C:otherwise>
                    <C:set var="begin" value="${requestScope.page.pageNo-2}"/>
                    <C:set var="end" value="${requestScope.page.pageNo+2}"/>
                </C:otherwise>
            </C:choose>
        </C:when>
    </C:choose>

    <C:forEach begin="${begin}" end="${end}" var="i">
        <C:if test="${i == requestScope.page.pageNo}">
            【${i}】
        </C:if>
        <C:if test="${i != requestScope.page.pageNo}">
            <a href="${requestScope.page.url}&pageNo=${i}">${i}</a>
        </C:if>
    </C:forEach>
    <%--页码输出的范围+++++++--%>



    <%--最后一页则不显示下页 末页--%>
    <C:if test="${requestScope.page.pageNo < requestScope.page.pageTotal}">
        <a href="${requestScope.page.url}&pageNo=${requestScope.page.pageNo+1}">下一页</a>
        <a href="${requestScope.page.url}&pageNo=${requestScope.page.pageTotal}">末页</a>
    </C:if>

    共${requestScope.page.pageTotal}页，${requestScope.page.pageTotalCount}条记录 到第<input value="${param.pageNo}" name="pn" id="pn_input"/>页
    <input id="searchPageBtn" type="button" value="确定">
    <script type="text/javascript">
        $(function () {
            //跳到指定的页码
            $("#searchPageBtn").click(function () {
                let pageNo = $("#pn_input").val();
                <%--let pageTotal = ${requestScope.page.pageTotal};--%>

                //JavaScript语言提供了一个location地址栏对象
                //他有一个属性 他可以获取浏览器地址栏里的地址
                //href 可读可写
                location.href = "${requestScope.basePath}${requestScope.page.url}&pageNo=" + pageNo;
            });
        });
    </script>
</div>
<%--分页条的结束--%>