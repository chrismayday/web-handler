//初始化界面
function initPage() {
    //初始化划动条
    $(".slider").slider();
    $("#status").bootstrapSwitch();

    //绑定点击事件
    $('#submitbtn').click(function () {
        callSaveCateg();
    });

    //设置所有输入框Mask
    setInputsMask();

    //初始化编辑页面
    initModify();
}

//初始化编辑页面
function initModify() {
    if (getUrlParam("action") == "modify") {
        var id = getUrlParam("id");
        if (id != null) {
            restTemplate("GET", "/categs/" + id, null, function (jsOutObj) {
                console.debug(jsOutObj);
                $("#categName").val(jsOutObj.categName);
                $("#categDesc").val(jsOutObj.categDesc);
                $("#listOrder").slider('setValue', jsOutObj.listOrder, true);
                $("#mainOrder").slider('setValue', jsOutObj.mainOrder, true);
                $("#picName").val(jsOutObj.picName);
                $("#brandId").val(jsOutObj.brandId);
                $("#status").bootstrapSwitch("state", jsOutObj.status);
            });
        }
    }
}

//设置所有输入框Mask
function setInputsMask() {
    $("#listOrder").inputmask("9{1,4}");
    $("#mainOrder").inputmask("9{1,4}");
}

//保存
function callSaveCateg() {
    //表单校验
    if (!checkInputsMask()) return false;

    var jsInObj = {
        categName: $("#categName").val().trim(),
        categDesc: $("#categDesc").val().trim(),
        listOrder: $("#listOrder").val().trim(),
        mainOrder: $("#mainOrder").val().trim(),
        picName: $("#picName").val().trim(),
        brandId: $("#brandId").val().trim(),
        status: $("#status").bootstrapSwitch("state")
    };
    console.debug(jsInObj);

    if (getUrlParam("action") == "modify") {
        //修改保存
        var id = getUrlParam("id");
        if (id != null) {
            jsInObj.id = id;
            restTemplate("PUT", "/categs/" + id, jsInObj, function (jsOutObj) {
                showModalAlert("修改成功", function () {
                    location.href = "categlist.html";
                });
            });
        }
    } else {
        //新增保存
        restTemplate("POST", "/categs", jsInObj, function (jsOutObj) {
            showModalAlert("添加成功，点击确定继续添加", function () {
                $("#categName").val("");
                $("#categDesc").val("");
                $("#picName").val("");
                $("#brandId").val("");
            });
        });
    }


}


