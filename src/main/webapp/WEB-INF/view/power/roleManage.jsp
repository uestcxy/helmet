<%--
  Created by IntelliJ IDEA.
  User: wenxinyan
  Date: 2018/10/9
  Time: 15:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" href="/static/bootstrap-select/bootstrap-select.min.css">

<div class="main-content">
    <div class="panel clearfix" id="searchSection">
        <div class="panel-heading">
            <h3 class="panel-title"></h3>
        </div>

        <div class="panel-body col-md-6" id="searchDiv">
            <div class="input-group">
                <input type="text" id="roleName" class="form-control" placeholder="请输入角色名称" value="" />
                <label for="roleName" class="input-group-addon">角色名称</label>

                <span class="input-group-btn">
                    <button class="btn btn btn-primary" type="submit" id="search-button" title="点击搜索">
                        <i class="fa fa-search"></i>
                    </button>
                </span>

                <span class="input-group-btn">
                    <button class="btn btn btn-danger pull-right" type="button" id="add-button" title="新增角色">
                        <i class="fa fa-plus"></i>
                    </button>
                </span>
            </div>
        </div>
    </div>

    <div class="panel-body" id="contentSection">
        <div class="panel-heading">
            <h3 class="panel-title">角色列表</h3>
        </div>
        <div class="panel-body">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>角色名称</th>
                    <%--<th>接口权限</th>--%>
                    <th>一级菜单权限</th>
                    <th>二级菜单权限</th>
                    <th>三级菜单权限</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>

        <!-- 分页 -->
        <div class="panel-headline" id="new-page-pager">
            <div class="panel-body">
                <p class="demo-button">
                    <span id="pageInfo">共 0 条数据 , 第 0 页 , 共 0 页</span>
                    , 跳转到
                    <input type="text" style="width:50px;" id="toPage"> 页
                    <button type="button" class="btn btn-xs btn-primary" id="toPageButton">GO</button>
                    &nbsp;
                    <!-- 禁用按钮 -->
                    <button type="button" class="btn btn-xs btn-primary" id="prePageButton">上一页</button>
                    &nbsp;
                    <!-- 可用按钮 -->
                    <button type="button" class="btn btn-xs btn-primary" id="nextPageButton">下一页</button>
                </p>
            </div>
        </div>

    </div>
</div>

<!-- END MAIN -->
<div class="clearfix"></div>
<!-- END WRAPPER -->

<div id="addRoleModal" class="modal" style="top: 20%">
    <div class="panel panel-body center-block" style="width: 50%;">
        <div>
            <h4 id="addRoleModalTitle">添加角色信息</h4>
            <p></p>
            <div id="addForm">
                <div class="input-group">
                    <input type="text" id="add_roleName" placeholder="角色名称" class="form-control" value="" />
                    <span class="input-group-addon">角色名称</span>
                </div>
            </div>
        </div>

        <div class="modal-footer">
            <a href="javascript:void(0)" id="addsubmit-button" class="btn btn-primary">确定</a>
            <a href="javascript:void(0)" class="btn" data-dismiss="modal" aria-label="Close">关闭</a>
        </div>
    </div>
</div>

<div id="editModal" class="modal" style="top: 20%">
    <div class="panel panel-body center-block" style="width: 50%;">
        <div>
            <h4 id="editModalTitle">修改角色权限</h4>
            <p></p>
            <div class="input-group" style="display: none">
                <input type="text" id="edit_id" placeholder="单位ID" class="form-control" value="" readonly />
            </div>
            <div class="input-group">
                <input type="text" id="edit_roleName" placeholder="角色名称" class="form-control" value="" />
                <span class="input-group-addon">角色名称</span>
            </div>
            <p style="display: none">接口权限分配</p>
            <div class="input-group" style="display: none">
                <select class="form-control selectpicker" id="edit_api" title="请选择接口" multiple>
                </select>
                <span class="input-group-addon">接口信息</span>
            </div>
            <p>功能权限分配</p>
            <div class="input-group">
                <select id="edit_menu" class="form-control selectpicker" title="请选择功能" multiple >
                </select>
                <span class="input-group-addon">功能信息</span>
            </div>
        </div>

        <div class="modal-footer">
            <a href="javascript:void(0)" id="editsubmit-button" class="btn btn-primary">确定</a>
            <a href="javascript:void(0)" class="btn" data-dismiss="modal" aria-label="Close">关闭</a>
        </div>
    </div>
</div>

<script src="/static/bootstrap-select/bootstrap-select.min.js"></script>
<script>
    var roleList = {};
    var newPagePager = {};
    var menuArray = new Array;
    var apiArray = new Array;
</script>
<script src="/static/js/roleManage.js?v=${version}"></script>