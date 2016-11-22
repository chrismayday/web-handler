//初始化界面
function initPage() {
    //初始化文本编辑器
    setupCkEditor();

    //初始化划动条
    $('.slider').slider();
    $("#status").bootstrapSwitch();

    //设置所有输入框Mask
    setInputsMask();

    //初始化编辑页面
    initEdit();

    //绑定提交按钮
    bindSubmit();

    //初始化文件上传插件
    setupFileUpload();
}

//初始化文件上传插件
function setupFileUpload() {
    getToken(function (accessToken) {
        $('#fileupload').fileupload({
            add: function (e, data) {
                var uploadErrors = [];
                var acceptFileTypes = /(\.|\/)(gif|jpe?g|png)$/i;
                console.debug(data);
                if (!acceptFileTypes.test(data.originalFiles[0].name)) {
                    uploadErrors.push('图片类型不正确');
                }
                if (data.originalFiles[0]['size'] > 3000000) {
                    uploadErrors.push('图片尺寸过大');
                }
                if (uploadErrors.length > 0) {
                    showModalAlert(uploadErrors.join("\n"));
                } else {
                    data.submit();
                }
            },
            url: apiBaseUrl + "/v1/upload/images",
            dataType: "json",
            headers: {"Authorization": "Bearer " + accessToken},
            done: function (e, data) {
                $("#picLink").val(data.result.url);
            },
            fail: function (e, data) {
                showModalAlert("文件上传失败");
            },
            progressall: function (e, data) {
                var progress = parseInt(data.loaded / data.total * 100, 10);
                $('#progress .progress-bar').css('width', progress + '%');
            }
        }).prop('disabled', !$.support.fileInput)
            .parent().addClass($.support.fileInput ? undefined : 'disabled');
    });
}

//初始化文本编辑器
function setupCkEditor() {
    var detailEditor = CKEDITOR.replace('detailHtml');
    var specEditor = CKEDITOR.replace('specHtml');
    //设置传入token
    getToken(function (accessToken) {
        detailEditor.on('fileUploadRequest', function (evt) {
            evt.data.fileLoader.xhr.setRequestHeader('Authorization', 'Bearer ' + accessToken);
        });
        specEditor.on('fileUploadRequest', function (evt) {
            evt.data.fileLoader.xhr.setRequestHeader('Authorization', 'Bearer ' + accessToken);
        });
    });
}

//初始化编辑页面
function initEdit() {
    if (getUrlParam("action") == "modify") {
        var id = getUrlParam("id");
        if (id != null) {
            restTemplate("GET", "/gdses/" + id, null, function (jsOutObj) {
                console.debug(jsOutObj);
                $("#gdsName").val(jsOutObj.gdsName);
                $("#gdsSecName").val(jsOutObj.gdsSecName);
                $("#gdsId").val(jsOutObj.gdsId);
                $("#skuId").val(jsOutObj.skuId);
                $("#catId").val(jsOutObj.catId);
                $("#shopLocaleCode").val(jsOutObj.shopLocaleCode);
                $("#gdsPrice").val(jsOutObj.gdsPrice / 100);
                $("#gdsRealPrice").val(jsOutObj.gdsRealPrice / 100);
                $("#stockCount").slider('setValue', jsOutObj.stockCount, true);
                $("#orderDef").slider('setValue', jsOutObj.orderDef, true);
                $("#orderSale").slider('setValue', jsOutObj.orderSale, true);
                if (jsOutObj.gdsPics.length > 0) {
                    $("#picLink").val(jsOutObj.gdsPics[0].picLink);
                    $("#gdsPicId").val(jsOutObj.gdsPics[0].id);  //隐藏字段
                }
                if (jsOutObj.gdsDetail != null) {
                    $("#detailLink").val(jsOutObj.gdsDetail.detailLink);
                    $("#specLink").val(jsOutObj.gdsDetail.specLink);
                    CKEDITOR.instances.detailHtml.setData(jsOutObj.gdsDetail.detailHtml);
                    CKEDITOR.instances.specHtml.setData(jsOutObj.gdsDetail.specHtml);
                    $("#gdsDetailId").val(jsOutObj.gdsDetail.id);  //隐藏字段
                }
                //获取类别下拉列表，并设置选中
                initCategSelect(jsOutObj.categs);
                //状态
                $("#status").bootstrapSwitch("state", jsOutObj.status);
            });
        }
    } else {
        //获取类别下拉列表
        initCategSelect();
    }
}

//获取类别下拉列表
function initCategSelect(selectedCategs) {
    var jsInObj = {
        size: 2000
    };
    restTemplate("GET", "/categs/", jsInObj, function (jsOutObj) {
        // console.debug(selectedCategs);
        // console.debug(jsOutObj);
        var categArr = jsOutObj.content;
        var optionStr;
        $("#categs").empty();
        for (var i = 0; i < categArr.length; i++) {
            optionStr = '<option value="' + categArr[i].id + '">' + categArr[i].categName + '</option>';
            $("#categs").append(optionStr);
        }
        if (selectedCategs != null) {
            for (var i = 0; i < selectedCategs.length; i++) {
                $("#categs option[value=" + selectedCategs[i].id + "]").attr("selected", "selected");
            }
        }
        $(".select2").select2();
    });
}


//绑定提交按钮
function bindSubmit() {
    $("#submitbtn").click(function () {
        callSaveCateg();
    });

    $("#fillbtn").click(function () {
        autoFillForm();
    });
}

//设置所有输入框Mask
function setInputsMask() {
    $("#gdsPrice").inputmask("9{1,6}");
    $("#gdsRealPrice").inputmask("9{1,6}");
    $("#gdsId").inputmask("*{0,20}");
    $("#skuId").inputmask("*{0,20}");
    $("#catId").inputmask("*{0,20}");
    $("#shopLocaleCode").inputmask("*{0,20}");
}

//检查所有输入框Mask
function checkInputsMask() {
    var element = ".box-body"
    var inputs = $(".box-body input");
    for (var i = 0; i < inputs.length; i++) {
        if (!checkInputMask($(inputs.get(i)))) {
            return false;
        }
    }
    return true;
}

//检查单个输入框Mask
function checkInputMask(element) {
    if (!element.inputmask("isComplete")) {
        showModalAlert("参数不符合格式");
        addInputWarning(element);
        return false;
    } else {
        return true;
    }
}

//添加输入框警告
function addInputWarning(element) {
    element.parent().addClass("has-error");
    element.click(function () {
        element.parent().removeClass("has-error");
    });
}


//保存类别
function callSaveCateg() {
    if (!checkInputsMask()) return false;

    //获取类别
    var categOptions = $("#categs option:selected");
    console.debug($(categOptions));
    var categs = [];
    for (i = 0; i < categOptions.length; i++) {
        var categ = {"id": categOptions[i].value}
        categs.push(categ);
    }
    //获取图片
    var gdsPics = new Array({
        id: $("#gdsPicId").val(),
        picName: "主图",
        picLink: $("#picLink").val().trim()
    });
    //获取详情
    var gdsDetail = {
        id: $("#gdsDetailId").val().trim(),
        detailLink: $("#detailLink").val().trim(),
        specLink: $("#specLink").val().trim(),
        detailHtml: CKEDITOR.instances.detailHtml.getData(),
        specHtml: CKEDITOR.instances.specHtml.getData()
    };
    var jsInObj = {
        gdsName: $("#gdsName").val().trim(),
        gdsSecName: $("#gdsSecName").val().trim(),
        gdsId: $("#gdsId").val().trim(),
        skuId: $("#skuId").val().trim(),
        catId: $("#catId").val().trim(),
        shopLocaleCode: $("#shopLocaleCode").val().trim(),
        gdsPrice: $("#gdsPrice").val() * 100,
        gdsRealPrice: $("#gdsRealPrice").val() * 100,
        stockCount: $("#stockCount").val().trim(),
        orderDef: $("#orderDef").val().trim(),
        orderSale: $("#orderSale").val().trim(),
        gdsPics: gdsPics,
        gdsDetail: gdsDetail,
        categs: categs,
        status: $("#status").bootstrapSwitch("state")
    };
    console.debug(jsInObj);

    if (getUrlParam("action") == "modify") {//修改
        var id = getUrlParam("id");
        jsInObj.id = id;
        restTemplate("PUT", "/gdses/" + id, jsInObj, function (jsOutObj) {
            showModalAlert("修改成功", function () {
                location.href = "gdslist.html";
            });
        });
    } else {  //新增
        restTemplate("POST", "/gdses", jsInObj, function (jsOutObj) {
            showModalAlert("添加成功，点击确定继续添加", function () {
                clearPage();
            });
        });
    }
}

//清空页面
function clearPage() {
    $("#gdsDetailId").val("");
    $("#gdsPicId").val("");
    $("#woegoUrl").val("");

    $("#gdsName").val("");
    $("#gdsSecName").val("");
    $("#gdsId").val("");
    $("#skuId").val("");
    $("#catId").val("");
    $("#shopLocaleCode").val("");
    $("#gdsPrice").val("");
    $("#gdsRealPrice").val("");
    $("#picLink").val("");
    $("#detailLink").val("");
    $("#specLink").val("");

    CKEDITOR.instances.detailHtml.setData("");
    CKEDITOR.instances.specHtml.setData("");

    $("#stockCount").slider('setValue', 50, true);
    $("#orderDef").slider('setValue', 50, true);
    $("#orderSale").slider('setValue', 50, true);
    $("html, body").animate({scrollTop: 0}, "slow");

    initCategSelect();

    $('#progress .progress-bar').css('width', progress + '%');
}

//获取URL参数
function getWoegoParam(sPageURL, sParam) {
    var sPageURL = sPageURL.split('?')[1];
    var sURLVariables = sPageURL.split('&');
    var sParameterName, i;
    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');
        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
}

//自动填表
function autoFillForm() {
    var woegoUrl = $("#woegoUrl").val();  //http://www.woego.cn/woego/goods/pageInit?gdsId=1152042&skuId=1240362&shopLocaleCode=17

    var gdsId, shopLocaleCode;
    try {
        gdsId = getWoegoParam(woegoUrl, "gdsId");
        shopLocaleCode = getWoegoParam(woegoUrl, "shopLocaleCode");
    } catch (err) {
        showModalAlert("URL分析失败，请复制正确的外部系统系统商品URL");
        return;
    }

    if (gdsId == null || skuId == null || shopLocaleCode == null) {
        showModalAlert("URL分析失败，参数中必须存在gdsId和shopLocaleCode");
        return;
    }
    console.debug("gdsId=" + gdsId + ",shopLocaleCode=" + shopLocaleCode);

    var jsInobj = {
        chnlId: "17b3rov",  //暂时默认17b3rov
        shopLocaleCode: shopLocaleCode
    };
    restTemplate("GET", "/woego/gdses/" + gdsId, jsInobj, function (jsOutObj) {
        console.debug(jsOutObj);
        $("#gdsName").val(jsOutObj.gdsName);
        $("#gdsSecName").val(jsOutObj.gdsSecName);
        $("#gdsId").val(jsOutObj.gdsId);
        $("#skuId").val(jsOutObj.skuId);
        $("#catId").val(jsOutObj.catId);
        $("#shopLocaleCode").val(jsOutObj.shopLocaleCode);
        $("#gdsPrice").val(jsOutObj.gdsPrice / 100);
        $("#gdsRealPrice").val(jsOutObj.gdsRealPrice / 100);
        $("#picLink").val(jsOutObj.picLink);
        $("#detailLink").val(jsOutObj.detailLink);
        $("#specLink").val(jsOutObj.specLink);
        CKEDITOR.instances.detailHtml.setData(jsOutObj.detailHtml);
        CKEDITOR.instances.specHtml.setData(jsOutObj.specHtml);
    });
}
