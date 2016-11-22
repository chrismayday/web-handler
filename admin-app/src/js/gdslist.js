//初始化界面
function initPage() {
    //获取类别下拉列表
    initCategSelect();

    //读入表格
    loadDataTable();

    $("#status").bootstrapSwitch();

}


//读入表格
function loadDataTable() {
    dataTableErrorHandler();
    getToken(function (accessToken) {
        $("#datatable").DataTable({
            "oLanguage": dataTableLanguage,
            "lengthChange": true,
            "searching": true,
            "processing": true,
            "serverSide": true,
            "iDisplayLength": 10,
            "order": [[0, "desc"]],
            "ajax": {
                url: apiBaseUrl + "/v1" + "/gdses",
                type: "GET",
                headers: {"Authorization": "Bearer " + accessToken},
                data: dataTableData,
                dataSrc: dataTableDataSrc
            },
            "fnDrawCallback": function () {
                $(".status").bootstrapSwitch();
                $(".status").on("switchChange.bootstrapSwitch", function (event, state) {
                    switchStatus($(this).data("id"), state, this);
                });
            },
            "columns": [
                {"data": "id", "bSortable": true},
                {"data": "gdsName"},
                {"data": "gdsPrice"},
                {"data": "gdsRealPrice"},
                {"data": "stockCount"},
                {"data": "orderDef"},
                {"data": "orderSale"}
            ],
            "columnDefs": [
                {
                    "targets": [2],
                    "data": "gdsPrice",
                    "render": function (data, type, full) {
                        return data / 100;
                    }
                }, {
                    "targets": [3],
                    "data": "gdsRealPrice",
                    "render": function (data, type, full) {
                        return data / 100;
                    }
                }, {
                    "targets": [7],
                    // "data": "status",
                    data: function (row, type, val, meta) {
                        return row;
                    },
                    "render": function (data, type, full) {
                        // console.debug(data);
                        var checkBoxStr = '<input type="checkbox" data-id="' + data.id + '" class="status" data-on-text="生效" data-off-text="失效" data-size="mini"';
                        if (data.status == true) {
                            checkBoxStr += " checked"
                        }
                        checkBoxStr += ">";
                        return checkBoxStr;
                    },
                    "sClass": "column-center"
                }, {
                    "targets": [8],
                    "data": "id",
                    "render": function (data, type, full) {
                        return "<a class='fa fa-lg fa-edit text-light-blue' href='gdssave.html?action=modify&id=" + data + "'></a>";
                    },
                    "sClass": "column-center"
                }, {
                    "targets": [9],
                    "data": "id",
                    "render": function (data, type, full) {
                        return "<a class='fa fa-lg fa-warning text-red' href='#' onclick='delGds(" + data + ")'></a>";
                    },
                    "sClass": "column-center"
                }
            ]
        });
    });

}

//获取类别下拉列表
function initCategSelect(selectedCategs) {
    var jsInObj = {size: 2000};
    restTemplate("GET", "/categs/", jsInObj, function (jsOutObj) {
        // console.debug(selectedCategs);
        // console.debug(jsOutObj);
        var categArr = jsOutObj.content;
        var optionStr;
        //读取列表
        $("#categs").empty();
        for (var i = 0; i < categArr.length; i++) {
            optionStr = '<option value="' + categArr[i].id + '">' + categArr[i].categName + '</option>';
            $("#categs").append(optionStr);
        }
        $("#categs").append('<option value="-1">未分类</option>');
        //设置选中
        if (selectedCategs != null) {
            for (var i = 0; i < selectedCategs.length; i++) {
                $("#categs option[value=" + selectedCategs[i].id + "]").attr("selected", "selected");
            }
        }
        $("#categs").select2();
    });
    //下拉列表切换事件
    $("#categs").on("change", function () {
        var categId = $(this).val();
        reloadDataTable(categId);
    });
}

//刷新表格
function reloadDataTable(categId) {
    $("#datatable").dataTable().fnFilter("");
    var ajaxUrl = "";
    if (categId == null || categId == 1) {
        ajaxUrl = apiBaseUrl + "/v1" + "/gdses";
    } else {
        ajaxUrl = apiBaseUrl + "/v1" + "/categs/" + categId + "/gdses";
    }
    var dtb = $("#datatable").DataTable();
    dtb.ajax.url(ajaxUrl);
    dtb.ajax.reload(null, true);
}

//删除事件
function delGds(id) {
    showModalAlert("危险，确认要删除ID=" + id + "的记录吗？", function () {
        restTemplate("DELETE", "/gdses/" + id, null, function (jsOutObj) {
            $("#datatable").DataTable().ajax.reload(null, false);
        });
    }, "danger");
}

//更改状态
function switchStatus(id, status, switchBox) {
    restTemplate("PUT", "/gdses/" + id + "/status/" + status, null, function () {
        $($(switchBox)).bootstrapSwitch("state", status);
    });
}