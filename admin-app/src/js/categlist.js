//初始化界面
function initPage() {
    //读入表格
    loadDataTable()
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
                url: apiBaseUrl + "/v1" + "/categs",
                type: "GET",
                headers: {"Authorization": "Bearer " + accessToken},
                data: dataTableData,
                dataSrc: dataTableDataSrc
            },
            "columns": [
                {"data": "id", "bSortable": true},
                {"data": "categName"},
                {"data": "categDesc"},
                // {"data": "brandId"},
                {"data": "listOrder"},
                {"data": "mainOrder"},
                {"data": "picName"}
            ],
            "columnDefs": [
                {
                    "targets": [6],
                    "data": "id",
                    "render": function (data, type, full) {
                        return "<a class='fa fa-lg fa-edit text-light-blue' href='categsave.html?action=modify&id=" + data + "'></a>";
                    },
                    "sClass": "column-center"
                },
                {
                    "targets": [7],
                    "data": "id",
                    "render": function (data, type, full) {
                        return "<a class='fa fa-lg fa-warning text-red' href='#' onclick='delCateg(" + data + ")'></a>";
                    },
                    "sClass": "column-center"
                }
            ]
        });
    });
}

//删除事件
function delCateg(id) {
    showModalAlert("危险，确认要删除ID=" + id + "的记录吗？", function () {
        restTemplate("DELETE", "/categs/" + id, null, function (jsOutObj) {
            $("#datatable").DataTable().ajax.reload(null, false);
        });
    }, "danger");
}