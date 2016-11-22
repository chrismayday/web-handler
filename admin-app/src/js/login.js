//初始化界面
function initPage() {
    //初始化iCheck插件
    $('input').iCheck({
        checkboxClass: 'icheckbox_square-blue',
        radioClass: 'iradio_square-blue',
        increaseArea: '20%' // optional
    });

    //登录按钮点击
    $('#submitbtn').click(function () {
        callOauthToken();
    });


    //回车事件
    $("input").keypress(function (e) {
        if (e.which == 13) {
            callOauthToken();
            return false;
        }
    });


    //读取用户名密码
    loadUsername();
}

//读取用户名密码
function loadUsername() {
    var sUsername = localStorage.getItem("sUsername");
    var sPassword = localStorage.getItem("sPassword");
    if (sUsername != null) {
        $("#username").val(sUsername);
        $("#password").val(sPassword);
        $("#rememberme").iCheck("check");
    }
}

//调用登录服务
function callOauthToken() {
    var jsInObj = {
        client_id: "clientapp",
        client_secret: "123456",
        grant_type: "password",
        scope: "read write",
        username: $("#username").val(),
        password: $("#password").val()
    };

    $.ajax({
        type: "POST",
        url: apiBaseUrl + "/oauth/token",
        data: jsInObj,
        dataType: "json",
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

            if ($("#rememberme").is(":checked")) {
                console.debug("rememberme is on");
                localStorage.setItem("sUsername", $("#username").val());
                localStorage.setItem("sPassword", $("#password").val());
            } else {
                localStorage.removeItem("sUsername");
                localStorage.removeItem("sPassword");
            }
            callUserInfo();
        },
        complete: function (XMLHttpRequest, textStatus) {
        },
        error: function (jqXHR) {
            if (jqXHR.status == 400) {
                showModalAlert("用户名或密码错误");
            } else {
                showModalAlert("网络错误");
            }
        }
    });
}

//获取用户基本信息
function callUserInfo() {
    restTemplate("GET", "/users/current", null, function (jsOutObj) {
        console.debug(jsOutObj);
        sessionStorage.setItem("username", jsOutObj.username);
        sessionStorage.setItem("fullname", jsOutObj.fullname);
        sessionStorage.setItem("phone", jsOutObj.phone);
        sessionStorage.setItem("email", jsOutObj.email);
        sessionStorage.setItem("departId", jsOutObj.departId);
        sessionStorage.setItem("departName", jsOutObj.departName);
        succRedirect();
    });
}

//重定向
function succRedirect() {
    var redirectUrl = getUrlParam("url");
    if(redirectUrl == null){
        redirectUrl = "main.html";
    }
    location.href = redirectUrl;
}

