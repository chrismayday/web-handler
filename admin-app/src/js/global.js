//全局变量
var apiBaseUrl = '/api';

$(function () {
    //初始化共公界面元素
    initPublicElement();
    //初始化界面
    initPage();
});

//初始化共公界面元素
function initPublicElement() {
    initPace();
    initSideBar();
    initStaffInfo();
    initNavBar();
}

//初始化pace进度条
function initPace() {
    $(document).ajaxStart(function () {
        Pace.restart();
    });

}

//初始化左侧菜单选中样式
function initSideBar() {
    var activeMenu = $(".sidebar-menu a[href*='" + getPageName() + "']:first").parent();
    if (activeMenu.length > 0) {
        do {
            activeMenu.addClass("active")
            activeMenu = activeMenu.parent();
        } while (!activeMenu.hasClass("sidebar-menu"));
    }
}

//初始化右上角用户信息
function initStaffInfo() {
    var accessToken = sessionStorage.getItem("accessToken");
    var username = sessionStorage.getItem("username");
    var fullname = sessionStorage.getItem("fullname");
    var departName = sessionStorage.getItem("departName");

    if (accessToken == null) {
        $("#alert-modal .modal-footer .btn-default").click(function () {
            location.href = "login.html";
        });
    }
    $(".user-menu a span").empty().append(fullname);
    $(".user-header p span").empty().append(username);
    $(".user-header p small").empty().append(departName);

    $(".user-footer .pull-right a").click(function () {
        sessionStorage.clear();
        location.href = "login.html";
    });
}

//初始化上方导航栏
function initNavBar() {

    if (getUrlParam("action") == "modify") {
        var title = "修改";
    } else {
        var title = $(".sidebar-menu a[href*='" + getPageName() + "']:first").text();
    }
    $(".content-header h1").text(title);
    $(".content-header ol li.active").text(title);
    // $(".content-header").load("navbar.html", function () {});
}


//获取URL参数
function getUrlParam(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;
    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');
        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
}

//获取URL文件名
function getPageName() {
    var url = window.location.href.split("?")[0];
    var urlSlashCount = url.split('/').length;
    return url.split('/')[urlSlashCount - 1].toLowerCase();
}

//显示模态窗口
function showModalAlert(msg, callback, type) {
    var btnYes = $("#alert-modal .modal-footer button.btn-yes");
    var btnNo = $("#alert-modal .modal-footer button.btn-no");
    $("#alert-modal .modal-body").empty().append(msg);

    if (callback != null) {
        btnYes.unbind("click").click(function () {
            callback();
        });
    } else {
        btnYes.unbind("click");
    }

    //设置样式
    $("#alert-modal").removeClass("modal-danger modal-success modal-warning modal-info modal-primary");
    if (type == null || type == "alert") {
        btnNo.addClass("hidden");
    } else {
        btnNo.removeClass("hidden");
        $("#alert-modal").addClass("modal-" + type);
    }

    $("#alert-modal").modal({backdrop: 'static', keyboard: false});
    $(".modal-backdrop").hide();
}

//网络请求模板
function restTemplate(httpMethod, svcName, jsInObj, callback) {
    getToken(function (accessToken) {
        if (httpMethod == "POST" || httpMethod == "PUT") {
            jsInObj = JSON.stringify(jsInObj);
        }
        $.ajax({
            type: httpMethod,
            url: apiBaseUrl + "/v1" + svcName,
            data: jsInObj,
            datatype: "json",
            contentType: "application/json; charset=utf-8",
            headers: {"Authorization": "Bearer " + accessToken},
            beforeSend: function () {
            },
            success: function (jsOutObj) {
                callback(jsOutObj);
            },
            complete: function (XMLHttpRequest, textStatus) {
            },
            error: function (jqXHR) {
                console.debug(jqXHR);
                if (jqXHR.status == 401) {
                    showModalAlert("token验证失败,请重新登录", function () {
                        location.href = "login.html?url=" + window.location.pathname;
                    });
                } else {
                    var status = jqXHR.responseJSON.status;
                    var code = jqXHR.responseJSON.code;
                    var message = jqXHR.responseJSON.message;
                    var alertMsg = message == null ? "未知错误，可能是外部系统系统接口不稳定，请重试" : message;
                    if (code == "801") alertMsg = "SQL执行失败，有可能是名称重复或字段过长";
                    showModalAlert(alertMsg);
                }
            }
        });
    });
}

//回调方式获取accessToken
function getToken(callback) {
    var refreshTime = sessionStorage.getItem("refreshTime");
    var nowTime = (new Date()).getTime();
    if (refreshTime - nowTime < 0) {  //需要刷新
        var jsInObj = {};
        jsInObj.client_id = "clientapp";
        jsInObj.client_secret = "123456";
        jsInObj.grant_type = "refresh_token";
        jsInObj.refresh_token = sessionStorage.getItem("refreshToken");
        $.ajax({
            type: "POST",
            url: apiBaseUrl + "/oauth/token",
            data: jsInObj,
            username: "clientapp",
            password: "123456",
            beforeSend: function () {
            },
            success: function (jsOutObj) {
                console.debug(jsOutObj);
                var accessToken = jsOutObj.access_token;
                var refreshToken = jsOutObj.refresh_token;
                var expiresIn = jsOutObj.expires_in;
                sessionStorage.setItem("accessToken", accessToken);
                sessionStorage.setItem("refreshToken", refreshToken);
                sessionStorage.setItem("expiresIn", expiresIn);
                sessionStorage.setItem("refreshTime", (new Date()).getTime() + Math.floor(expiresIn * 1000 / 2));
                console.debug("刷新token成功");
                callback(accessToken);
            },
            complete: function (XMLHttpRequest, textStatus) {
            },
            error: function (jqXHR) {
                showModalAlert("刷新token失败,请重新登录");
                location.href = "login.html?url=" + window.location.pathname;
            }
        });
    } else {  //不需要刷新
        callback(sessionStorage.getItem("accessToken"));
    }
}

//DataTable中文显示
var dataTableLanguage = {
    "sProcessing": "正在加载中......",
    "sLengthMenu": "每页显示 _MENU_ 条记录",
    "sZeroRecords": "对不起，查询不到相关数据！",
    "sEmptyTable": "表中无数据存在！",
    "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
    "sInfoFiltered": "数据表中共为 _MAX_ 条记录",
    "sSearch": "搜索",
    "oPaginate": {
        "sFirst": "首页",
        "sPrevious": "上一页",
        "sNext": "下一页",
        "sLast": "末页"
    }
};

//DataTable请求参数设置
var dataTableData = function (d, settings) {
    var api = new $.fn.dataTable.Api(settings);
    d.page = Math.min(Math.max(0, Math.round(d.start / api.page.len())), api.page.info().pages);
    d.size = d.length;
    d.sort = d.columns[d.order[0].column].data + "," + d.order[0].dir;
    d.searchValue = d.search["value"];
    return d;
};

//DataTable响应参数设置
var dataTableDataSrc = function (json) {
    // console.debug(json);
    json["data"] = json["content"];
    json["recordsTotal"] = json["totalElements"];
    json["recordsFiltered"] = json["totalElements"];
    return json.data;
};

//DataTable异常处理
function dataTableErrorHandler() {
    $.fn.dataTable.ext.errMode = 'none';
    $(document.body).on('xhr.dt', function (e, settings, json, xhr) {
        if (xhr.status >= 200 && xhr.status < 300) {
            //success
        } else if (xhr.status == 401) {
            var api = new $.fn.dataTable.Api(settings);
            showModalAlert("刷新token失败,请重新登录", function () {
                location.href = "login.html?url=" + window.location.pathname;
            });
        } else if (xhr.status == 404) {
            // console.log("DataTable查询结果为空");
            var searchText = $("#datatable_filter input").val();
            console.debug(searchText);
            if (searchText == null || searchText == "") {
                $("#datatable tbody").empty();
                $("#datatable_paginate").empty();
                $("#datatable_info").empty().append("暂无数据");
            } else {
                // $("#datatable_filter input").val(searchText.substr(0,searchText.length-1));
            }
        } else {
            console.log("DataTable Error " + xhr.status);
        }
    });

}

//表单校验，检查所有输入框Mask
function checkInputsMask() {
    var inputs = $(".box-body input");
    for (var i = 0; i < inputs.length; i++) {
        if (!checkInputMask($(inputs.get(i)))) {
            return false;
        }
    }
    return true;
}

//表单校验，检查单个输入框Mask
function checkInputMask(element) {
    if (!element.inputmask("isComplete")) {
        showModalAlert("参数不符合格式");
        addInputWarning(element);
        return false;
    } else {
        return true;
    }
}

//表单校验，添加输入框警告
function addInputWarning(element) {
    element.parent().addClass("has-error");
    element.click(function () {
        element.parent().removeClass("has-error");
    });

}