﻿<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>景点管理</title>
    <jsp:include page="/resources/inc/head.jsp" flush="true"/>

    <style type="text/css">
        .table tbody tr td{
            /*overflow: hidden;*/
            /*text-overflow:ellipsis;*/
            /*white-space: nowrap;*/
            overflow: hidden; /*自动隐藏文字*/
            text-overflow: ellipsis;/*文字隐藏后添加省略号*/
            /*white-space: nowrap;!*强制不换行*!*/
            /*white-space: pre-line;*/
            /*height: 50px;*/
            width: 20em;/*不允许出现半汉字截断*/
            /*color:#6699ff;border:1px #ff8000 dashed;*/
        }
    </style>
</head>
<body>
<div id="main">
    <div id="toolbar">
      <%--  <shiro:hasPermission name="upms:landscape:create"><a class="waves-effect waves-button" href="javascript:;"
                                                             onclick="createAction()"><i class="zmdi zmdi-plus"></i>
            新增景点</a></shiro:hasPermission>
        <shiro:hasPermission name="upms:landscape:update"><a class="waves-effect waves-button" href="javascript:;"
                                                             onclick="updateAction()"><i class="zmdi zmdi-edit"></i>
            编辑景点</a></shiro:hasPermission>
        <shiro:hasPermission name="upms:landscape:delete"><a class="waves-effect waves-button" href="javascript:;"
                                                             onclick="deleteAction()"><i class="zmdi zmdi-close"></i>
            删除景点</a></shiro:hasPermission>--%>


          <a class="waves-effect waves-button" href="javascript:;"
                                                               onclick="createAction()"><i class="zmdi zmdi-plus"></i>
              新增景点</a>
          <a class="waves-effect waves-button" href="javascript:;"
                                                               onclick="updateAction()"><i class="zmdi zmdi-edit"></i>
              编辑景点</a>
           <a class="waves-effect waves-button" href="javascript:;"
                                                               onclick="deleteAction()"><i class="zmdi zmdi-close"></i>
              删除景点</a>
    </div>
    <%--<table id="table" data-height="467" data-mobile-responsive="true" style="table-layout: fixed;">--%>
    <table id="table"  class="table table-bordered" style='table-layout:fixed;'  >
    </table>
</div>
<jsp:include page="/resources/inc/footer.jsp" flush="true"/>
<script>
    var $table = $('#table');
    $(function () {
        // bootstrap table初始化
        $table.bootstrapTable({
            url: '${basePath}/tourism/landscape/list',
            height: getHeight(),
            striped: true,
            search: true,
            showRefresh: true,
            showColumns: true,
            minimumCountColumns: 2,
            clickToSelect: false,
            detailView: true,
            detailFormatter: 'detailFormatter',
            pagination: true,
            paginationLoop: false,
            sidePagination: 'server',
            silentSort: false,
            smartDisplay: false,
            escape: true,
            searchOnEnterKey: true,
            idField: 'id',
            maintainSelected: true,
            toolbar: '#toolbar',
            columns: [
                {field: 'ck', checkbox: true},
                {field: 'id', title: '编号', sortable: true, align: 'center',visible:false},//colspan: 1,width:100,
                {field: 'pictures', title: '图片', align: 'left', formatter: 'avatarFormatter'},
                {field: 'name', title: '景点名称'},
                {field: 'location', title: '景点位置'},
                {field: 'price', title: '门票价钱'},
                {field: 'devtime', title: '开发时间', formatter: 'timeFormatter'},
                {field: 'category', title: '景点类别'},
                {field: 'introduce', title: '景点介绍', formatter: 'introduceFormatter' },
                {field: 'videos', title: '视频', formatter: 'videoFormatter'},
                {field: 'locked', title: '状态', sortable: true, align: 'center', formatter: 'lockedFormatter'},
                {
                    field: 'action',
                    title: '操作',
                    align: 'center',
                    formatter: 'actionFormatter',
                    events: 'actionEvents',
                    clickToSelect: false,
                    colspan: 1,
                    width:100
                }
            ]
        });
    });

    // 格式化操作按钮
    function actionFormatter(value, row, index) {
        return [
            '<a class="update" href="javascript:;" onclick="updateAction()" data-toggle="tooltip" title="Edit"><i class="glyphicon glyphicon-edit"></i></a>　',
            '<a class="delete" href="javascript:;" onclick="deleteAction()" data-toggle="tooltip" title="Remove"><i class="glyphicon glyphicon-remove"></i></a>'
        ].join('');
    }

    // 格式化图标
    function avatarFormatter(value, row, index) {
        return '<img src="${basePath}' + value + '" style="width:80px;height:60px;"/>';
    }
    // 格式化视频
    function videoFormatter(value, row, index) {
        return '<video    src="${basePath}' + value + '" style="width:80px;height:60px;"/>';
    }
    // 格式化time
    function timeFormatter(value, row, index) {
        var now = new Date(value);
        var   year=now.getFullYear();
        var   month=now.getMonth()+1;
        var   date=now.getDate();
        // var   hour=now.getHours();
        // var   minute=now.getMinutes();
        // var   second=now.getSeconds();
        return   year+"-"+month+"-"+date ;
    }

    // 格式化状态
    function lockedFormatter(value, row, index) {
        if (value == 1) {
            return '<span class="label label-default">锁定</span>';
        } else {
            return '<span class="label label-success">正常</span>';
        }
    }
    function introduceFormatter(value, row, index) {
        var values = value ;
        if (value.length>19){
            values = value.substring(0,19)+"....";
        }
       return values;
    }
    // 新增
    var createDialog;

    function createAction() {
        createDialog = $.dialog({
            animationSpeed: 300,
            title: '新增用户',
            content: 'url:${basePath}/tourism/landscape/create',
            onContentReady: function () {
                initMaterialInput();
                initUploader();
            }
        });
    }

    // 编辑
    var updateDialog;

    function updateAction() {
        var rows = $table.bootstrapTable('getSelections');
        if (rows.length != 1) {
            $.confirm({
                title: false,
                content: '请选择一条记录！',
                autoClose: 'cancel|3000',
                backgroundDismiss: true,
                buttons: {
                    cancel: {
                        text: '取消',
                        btnClass: 'waves-effect waves-button'
                    }
                }
            });
        } else {
            updateDialog = $.dialog({
                animationSpeed: 300,
                title: '编辑景点',
                content: 'url:${basePath}/tourism/landscape/update/' + rows[0].id,
                onContentReady: function () {
                    initMaterialInput();
                    initUploader();
                }
            });
        }
    }

    // 删除
    var deleteDialog;

    function deleteAction() {
        var rows = $table.bootstrapTable('getSelections');
        if (rows.length == 0) {
            $.confirm({
                title: false,
                content: '请至少选择一条记录！',
                autoClose: 'cancel|3000',
                backgroundDismiss: true,
                buttons: {
                    cancel: {
                        text: '取消',
                        btnClass: 'waves-effect waves-button'
                    }
                }
            });
        } else {
            deleteDialog = $.confirm({
                type: 'red',
                animationSpeed: 300,
                title: false,
                content: '确认删除该用户吗？',
                buttons: {
                    confirm: {
                        text: '确认',
                        btnClass: 'waves-effect waves-button',
                        action: function () {
                            var ids = new Array();
                            for (var i in rows) {
                                ids.push(rows[i].id);
                            }
                            $.ajax({
                                type: 'get',
                                url: '${basePath}/tourism/landscape/delete/' + ids.join("-"),
                                success: function (result) {
                                    if (result.code != 1) {
                                        if (result.data instanceof Array) {
                                            $.each(result.data, function (index, value) {
                                                $.confirm({
                                                    theme: 'dark',
                                                    animation: 'rotateX',
                                                    closeAnimation: 'rotateX',
                                                    title: false,
                                                    content: value.errorMsg,
                                                    buttons: {
                                                        confirm: {
                                                            text: '确认',
                                                            btnClass: 'waves-effect waves-button waves-light'
                                                        }
                                                    }
                                                });
                                            });
                                        } else {
                                            $.confirm({
                                                theme: 'dark',
                                                animation: 'rotateX',
                                                closeAnimation: 'rotateX',
                                                title: false,
                                                content: result.data.errorMsg,
                                                buttons: {
                                                    confirm: {
                                                        text: '确认',
                                                        btnClass: 'waves-effect waves-button waves-light'
                                                    }
                                                }
                                            });
                                        }
                                    } else {
                                        deleteDialog.close();
                                        $table.bootstrapTable('refresh');
                                    }
                                },
                                error: function (XMLHttpRequest, textStatus, errorThrown) {
                                    $.confirm({
                                        theme: 'dark',
                                        animation: 'rotateX',
                                        closeAnimation: 'rotateX',
                                        title: false,
                                        content: textStatus,
                                        buttons: {
                                            confirm: {
                                                text: '确认',
                                                btnClass: 'waves-effect waves-button waves-light'
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    },
                    cancel: {
                        text: '取消',
                        btnClass: 'waves-effect waves-button'
                    }
                }
            });
        }
    }
</script>
</body>
</html>