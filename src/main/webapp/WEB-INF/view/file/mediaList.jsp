<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!-- MAIN -->
<div>
    <!-- MAIN CONTENT -->
    <div class="main-content">
        <div class="container-fluid">


            <div class="panel panel-headline">

                <div class="panel-heading">
                    <h3 class="panel-title">多媒体中心</h3>
                </div>
                <!-- 搜索区域 start-->
                <div class="panel-body">
                    <div class="custom-tabs-line tabs-line-bottom left-aligned">
                        <ul class="nav" role="tablist" id="resTypeList">
                            <li class="active" data-restype="video"><a href="#tab-bottom-left1" role="tab" data-toggle="tab" aria-expanded="true">视频</a></li>
                            <li class="" data-restype="image"><a href="#tab-bottom-left2" role="tab" data-toggle="tab" aria-expanded="false">照片</a></li>
                        </ul>
                    </div>
                    <br>
					<div class="input-group col-md-6 col-sm-8">
				        <div class="input-group-btn">
					        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><span  id="orderByBtn">时间升序</span> <span class="caret"></span></button>
					        <ul class="dropdown-menu" id="orderByList">
					          <li><a href="#">时间升序</a></li>
					          <li><a href="#">时间降序</a></li>
					        </ul>
                            <input type="text" id="orderBy" value="1" style="display: none;">
				        </div><!-- /btn-group -->
				       <input class="form-control" type="text" id="keyword">
				       <span class="input-group-btn"><button class="btn btn-primary" type="button" onclick="doSearch()"> 搜索 </button></span>
				    </div>                
                </div>
                <div class="panel-body">
                    <p class="subtitle">点击功能快捷搜索</p>
                    <p class="demo-button" id="byTypeButtons">
                        <button type="button" class="btn btn-xs btn-primary" data-groupby="searchlist">时间</button>
                        <button type="button" class="btn btn-xs btn-primary" data-groupby="siteid">工地</button>
                        <button type="button" class="btn btn-xs btn-primary" data-groupby="clientid">头盔IMEI</button>
                        <button type="button" class="btn btn-xs btn-primary" data-groupby="neusername">头盔账号</button>
                        <button type="button" class="btn btn-xs btn-primary" data-groupby="machinecode">车辆机号</button>
                        <button type="button" class="btn btn-xs btn-primary" data-groupby="imei">车辆盒子IMEI</button>
                        <button type="button" class="btn btn-xs btn-primary" data-groupby="svcdata">服务数据</button>
                    </p>
                    <p class="subtitle">点击标签快捷搜索</p>
                    <p class="demo-button" style="max-height: 100px; overflow: auto;" id="byTagButtons">
                        <c:forEach items="${tagList}" var="tag">
                            <button type="button" class="btn btn-xs btn-default"
                                    data-id="${tag.id}">${tag.tagName}</button>
                        </c:forEach>
                    </p>
                </div>
            </div>
            <!-- 结果区域 start -->
            <div id="mediaResult" class="col-md-12 col-xs-12"></div>
        </div>
    </div>
    <!-- END MAIN CONTENT -->
</div>
<!-- END MAIN -->
<div class="clearfix"></div>
<script>
    var _keyword = "${keyword}";
    var _orderBy = "${orderBy}";
    var _resType = "${resType}";
    var _page = ${page};

    function initUIForm() {
        $("#keyword").val(_keyword);
        var otxt = $("#orderByList>li").eq(_orderBy-1).text();
        $("#orderByBtn").text(otxt);

        $("#resTypeList>li").removeClass("active");
        $("#resTypeList>li[data-restype='"+_resType+"']").addClass("active");

        $(".page-title").text(_resType=="video"?"视频":"图片");
    }

    function initEvent() {
        $("#byTypeButtons button").off('click').click(function () {
            var _btn = $(this);
            var groupBy = _btn.attr("data-groupby");
            if("searchlist" == groupBy){
                doSearch();
            }else{
                loadMainContent("/list/video/"+groupBy);
            }
        });
        $("#byTagButtons button").off('click').click(function () {
            var _btn = $(this);
            var tag = _btn.text();
            $("#keyword").val(tag);
            doSearch();
        });

        // 搜索框 时间排序 下拉框
        $("#orderByList>li").click(function(event) {
            var oi = $(this).index()+1;
            $("#orderBy").val(oi);
            $("#orderByBtn").text($(this).text());
        });

        $("#resTypeList>li").click(function(event) {
            //此事件触发时，active的还没变，此时执行搜索会导致搜索旧的tab数据
            //所以延迟一下再执行
            setTimeout(doSearch,300);
        });
    }

    function doSearch() {
        var keyword = $.trim($("#keyword").val());
        var orderBy = $("#orderBy").val();
        var resType = $("#resTypeList>li.active").attr("data-restype");
        _keyword = keyword;
        _orderBy = orderBy;
        _resType = resType;
        loadData(_page,_resType);
        return false;
    }

    function reloadListData(){
        loadData(1,_resType);
    }
    //删除用重载页面
    function reloadListDataForDel($p){
        loadData($p,_resType);
    }
    function loadData(page,type) {
        _page = page;
        var url = "/media/load/" + type + "/" + _orderBy + "?keyword=" + _keyword + "&page=" + _page;
        console.debug("载入媒体资源数据.url="+url);
        loadContent("#mediaResult",url);
    }

    $(function () {
        initUIForm();
        initEvent();
        doSearch();
    })
</script>
