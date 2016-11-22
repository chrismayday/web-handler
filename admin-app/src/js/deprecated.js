//读入共公界面元素（废弃，已使用gulp-include在编译阶段处理）
function loadPublicElement() {
    //pace进度条
    $(document).ajaxStart(function () {
        Pace.restart();
    });

    //读取模态窗口
    $(".modal-wrapper").load("modal.html");

    //读取导航栏
    $(".main-header").load("header.html", function () {
        loadStaffInfo();
        initLogout();
    });

    //读取左侧菜单
    $(".main-sidebar").load("sidebar.html", function () {

        var activeMenu = $(".sidebar-menu a[href*='" + getPageName() + "']:first").parent();
        if (activeMenu.length > 0) {
            do {
                activeMenu.addClass("active")
                activeMenu = activeMenu.parent();
            } while (!activeMenu.hasClass("sidebar-menu"));
        }
        //初始化导航栏
        initNavBar();
    });

    //读取页脚
    $(".main-footer").load("footer.html");
}
