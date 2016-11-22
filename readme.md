WEB项目脚手架
==========

## 项目背景
WEB项目脚手架

## 功能特性
- 模块化
- 前后端分离，使用反向代理方式使前后端运行在同一域下。同时额外提供CORS跨域访问支持。
- 前端采用纯HTML多页面方式，使用gulp简单构建。直接使用Nginx输出，未使用node渲染。计划后期重构为SPA单页应用。
- 后端使用Spring Boot快速配置构建，生成可执行jar包简化部署。
- 使用Spring MVC对外提供REST服务。
- 使用Spring Security及Oauth2进行安全访问控制。
- 使用Spring Data JPA + Hibernate简化DAO层开发，报表部分使用更加灵活的JDBC Template。
- 使用Hibernate Validator进行后端验证。
- 使用SpringFox自动生成可测试的API文档，并导出MarkDown格式的静态文档。
- 默认使用Druid数据源，提供SQL监控界面，另外可配置使用更快的HikariCP。
- 使用基于BootStrap的AdminLTE作为前端模版。
- 使用Gradle作为JAVA构建工具，gulp前端构建，Git版本控制。
- API模块自带Thymeleaf的简单界面，用于手工测试API，监控SQL等。
- 提供可选的HTTPS支持，Ehcache缓存支持。

## 模块简介
- repository - 公共Domain类,DTO类,Repository类
- common - 公共Util类,组件,Exception类,Handler类等
- tv-api - TV版REST服务
- admin-api - 管理平台REST服务
- tv-app - TV版前端
- admin-app - 管理平台前端
- resources - 图片等资源文件
- cordova-app - Cordova工程，用于生成Android APP

## 第三方依赖
- [Spring系列](http://spring.io) - Spring Boot以及其它Pivotal旗下框架 (如Spring MVC, Spring Data JPA, Spring Secruity, Spring Security Oauth2等)
- [Hibernate](http://hibernate.org) - 流行的ORM框架,支持JPA,另外可选择EclipseLink
- [Druid](https://github.com/alibaba/druid) - 阿里巴巴公司为监控而生的数据库连接池
- [HikariCP](https://github.com/brettwooldridge/HikariCP) - 高性能JDBC连接池[可选]
- [H2 Database](https://github.com/h2database/h2database) - 内嵌式内存库,提供WEB控制台,测试用
- [Hibernate Validator](http://hibernate.org/validator) - JSR 303 Bean Validation校验,使用注解方式对字段进行长度,非空,正则等校验
- [Ehcache2](http://www.ehcache.org) - JAVA环境下高性能缓存,支持集群布署
- [SpringFox](http://springfox.github.io/springfox) - 自动生成JSON API,提供可测试的页面,可导出,ASCII或MarkDown静态文档
- [Jackson](https://github.com/FasterXML/jackson) - 快速的JSON序列化/反序列化工具
- [Thymeleaf](http://www.thymeleaf.org) - 替代JSP的新一代Java模板引擎,对于前端原始代码无入侵
- [Bootstrap](https://getbootstrap.com) - Twitter的栅格概念的响应式布局框架
- [AdminLTE](https://almsaeedstudio.com) - 基于Bootstrap的模板库
- [dataTables](https://datatables.net) - 基于jquery表格的插件
- [ckeditor](http://ckeditor.com) - 全功能的所见即所得编辑器
- [font-awesome](http://fontawesome.io) - 图标字体库
- [ionicons](http://ionicons.com) - 图标字体库
- [pace](http://github.hubspot.com/pace/docs/welcome) - 页面读取进度条
- [iCheck](http://icheck.fronteed.com) - checkbox样式插件
- [bootstrap-slider](http://seiyria.com/bootstrap-slider/) - 划动条插件
- [jquery.inputmask](https://github.com/RobinHerbots/Inputmask) - 前端输入框实时校验插件
- [bootstrap-switch](http://www.bootstrap-switch.org) - checkbox开关样式插件
- [select2](http://https://select2.github.io) - 下拉列表样式插件
- [jQuery-File-Upload](https://blueimp.github.io/jQuery-File-Upload) - 文件上传插件

## 基础环境
请确保以下基础环境及工具已经安装
- [MySQL](https://www.mysql.com/) - 流行的开源关系型数据库
- [Gradle](https://gradle.org) - 基于Groovy的项目构建工具,配置简洁灵活
- [Nexus](http://www.sonatype.org/nexus) - Maven私服
- [Git](https://git-scm.com) - 分布式版本控制系统
- [IntelliJ IDEA](https://www.jetbrains.com/idea) - 可选，推荐的IDE
- [node](https://nodejs.org) - JavaScript运行环境
- [npm](https://www.npmjs.com) - node包管理器
- [Cordova](https://cordova.apache.org) - 基于node的多平台HTML-APP生成器
- [gulp](http://gulpjs.com) - 前端构建工具

## 构建
### SpringBoot配置文件
```
src/main/resources/application.yml，修改首行的profile配置，例如：
spring.profiles.active: dev,druid,cache
dev/tst/prd，用于确定开发/测试/生产环境。
其它可选参数用配置缓存/连接池/HTTPS等
```

### Cordova编译
```
cordova build android -release
```

### 前端构建
```
gulp build   - 一次性构建
gulp   - 在开发过程中实时监控文件变化
```

### 后端构建
```
gradle build   - 打包
gradle bootRun   - 运行
```

## 配置及参数
### Nginx配置
```
http {
    include    mime.types;
    index    index.html index.htm index.php;

    default_type     application/octet-stream;
    sendfile    on;
    tcp_nopush    on;

    proxy_set_header  X-Real-IP $remote_addr;
    proxy_set_header  X-Forwarded-For $proxy_add_x_forwarded_for;
    proxy_set_header  Host $http_host;

    server_names_hash_bucket_size 128;
    client_header_buffer_size 32k;
    large_client_header_buffers 4 32k;
    client_max_body_size 8m;
    client_body_buffer_size 128k;

    keepalive_timeout 60;
    tcp_nodelay on;

    server {
        listen       10001;
        server_name  woego-tv;

        location / {
            return 301 $scheme://$host:10001/app/login.html;
        }
        location /woegotv/ {
            return 301 $scheme://$host:10001/app/login.html;
        }
        location /app/ {
            alias /home/woegotv/deploy/tv-app/;
        }
        location /res/ {
            alias /home/woegotv/deploy/resources/;
        }
        location /api/ {
            proxy_pass http://localhost:10021/api/;
        }
    }

    server {
        listen       10002;
        server_name  woego-admin;

        location / {
            return 301 $scheme://$host:10002/app/login.html;
        }
        location /woegoadmin/ {
            return 301 $scheme://$host:10002/app/login.html;
        }
        location /app/ {
            alias /home/woegotv/deploy/admin-app/;
        }
        location /res/ {
            alias /home/woegotv/deploy/resources/;
        }
        location /api/ {
            proxy_pass http://localhost:10022/api/;
        }
    }
}
```

### MySQL字符集配置
```
my.cnf
sql_mode=NO_ENGINE_SUBSTITUTION,STRICT_TRANS_TABLES
lower_case_table_names=1
character_set_server=utf8
```