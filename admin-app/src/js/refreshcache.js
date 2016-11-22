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
            "lengthChange": false,
            "searching": false,
            "processing": true,
            "serverSide": true,
            "iDisplayLength": 10,
            "order": [[0, "desc"]],
            "ajax": {
                url: apiBaseUrl + "/v1" + "/deploys",
                type: "GET",
                headers: {"Authorization": "Bearer " + accessToken},
                data: dataTableData,
                dataSrc: dataTableDataSrc
            },
            "columns": [
                {"data": "id"},
                {"data": "deployName"},
                {"data": "deployDesc"},
                {"data": "ip"},
                {"data": "port"},
                {"data": "refreshUrl"}
            ],
            "columnDefs": [
                {
                    "targets": [6],
                    "data": "id",
                    "render": function (data, type, full) {
                        return "<a class='fa fa-refresh' href='#' onclick='refreshCache(" + data + ")'></a>";
                    }
                }
            ]
        });
    });
}

//刷新事件
function refreshCache(id) {
    restTemplate("PUT", "/deploys/actions/refresh-cache/" + id, null, function () {
        showModalAlert("缓存刷新成功");
    });
}