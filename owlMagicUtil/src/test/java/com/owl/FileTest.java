package com.owl;

import com.owl.io.file.FileIO;
import org.junit.Test;

import java.io.IOException;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/12/11.
 */
public class FileTest {

    @Test
    public void t() throws IOException {
        StringBuilder a = new StringBuilder();
        a.append("<html data-ng-app=\"eolinker\" lang=\"zh-CN\" " +
                "class=\"ng-scope\"><head><style type=\"text/css\">@charset \"UTF-8\";[ng\\:cloak]," +
                "[ng-cloak],[data-ng-cloak],[x-ng-cloak],.ng-cloak,.x-ng-cloak,.ng-hide:not(.ng-hide-animate)" +
                "{display:none !important;}ng\\:form{display:block;}.ng-animate-shim{visibility:hidden;}." +
                "ng-anchor{position:absolute;}</style>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0,maximum-scale=1.0\">\n" +
                "    <meta name=\"description\" content=\"eolinker，业内领先的Api管理及测试平台，为您提供最专业" +
                "便捷的在线接口管理、测试、维护以及各类性能测试方案，帮助您高效开发、安全协作。\">\n" +
                "    <meta name=\"keywords\" content=\"api文档管理,api管理系统,接口管理,api测试,api监控,api" +
                "自动化测试,api管理平台,api网关,微服务网关,api市场,接口监控,接口文档,postman\">\n" +
                "    <meta name=\"keywords\" lang=\"en\" content=\"api,Api,API,apiManagement,apiManager\">\n");
        a.append("    <meta name=\"author\" content=\"广州银云信息科技有限公司\">\n" +
                "    <meta name=\"product-type\" content=\"1\">\n" +
                "    <meta name=\"version\" content=\"5.8.0\">\n" +
                "    <!-- Browser-360 speed mode is enabled (webkit) -->\n" +
                "    <meta name=\"renderer\" content=\"webkit\">\n" +
                "    <!-- Use IE and Chrome latest version -->\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">\n" +
                "    <title>空间概况-EOLINKER接口管理平台</title>\n" +
                "    <link href=\"assets/images/favicon.ico\" rel=\"shortcut icon\">\n" +
                "    <link rel=\"alternate\" href=\"https://www.eolinker.com\" hreflang=\"zh-CN\">\n" +
                "    <script src=\"upgrade.js\"></script>\n" +
                "\n" +
                "    <!-- <base href=\"../\"> -->\n" +
                "    <link rel=\"stylesheet\" href=\"styles/app-29aa8422d1.css\">\n" +
                "\n" +
                "    <!--[if module css]-->\n" +
                "\n");
        a.append("    <!--[end module css if]-->\n" +
                "<script src=\"libs/datepicker/lib/position.js\" async=\"\">" +
                "</script><script src=\"libs/datepicker/lib/dateparser.js\"" +
                " async=\"\"></script><script src=\"libs/datepicker/lib/datepicker.js\"" +
                " async=\"\"></script><script src=\"libs/datepicker/index.js\" async=\"\">" +
                "</script><script src=\"libs/imgCrop/ng-img-crop.js\" async=\"\"></script><script" +
                " src=\"vendor/clipboard/dist/clipboard.min.js\" async=\"\"></script><script src=" +
                "\"vendor/zepto/zepto.min.js\" async=\"\"></script><script src=\"libs/editor.md/edi" +
                "tormd.min.js?ts=20190908\" async=\"\"></script><style>.sv-helper{position: fixed !impo" +
                "rtant;z-index: 99999;margin: 0 !important;}.sv-candidate{}.sv-placeholder{}.sv-sorting-" +
                "in-progress{-webkit-user-select: none;-moz-user-select: none;-ms-user-select: none;user-sel" +
                "ect: none;}.sv-visibility-hidden{visibility: hidden !important;opacity: 0 !important;}</style" +
                "><script src=\"libs/pagination/pagination.js\" async=\"\"></script><link href=\"./theme/default." +
                "css?timestamp=20190715\" rel=\"stylesheet\" type=\"text/css\" id=\"theme_css_js\"></head>\n" +
                "<!--[if lt IE 8]>\n" +
                "        <style>html,body{overflow:hidden;height:100%}</style>\n" +
                "        <div class=\"tb-ie-updater-layer\"></div>\n" +
                "        <div class=\"tb-ie-updater-box\" data-spm=\"20161112\">\n" +
                "          <a href=\"https://www.google.cn/intl/zh-CN" +
                "/chrome/browser/desktop/\" class=\"tb-ie-updater-google\" target=\"" +
                "_blank\" data-spm-click=\"gostr=/tbieupdate;locaid=d1;name=google\">谷歌 Chrome</a>\n" +
                "          <a href=\"http://www.uc.cn/ucbrowser/downlo" +
                "ad/\" class=\"tb-ie-updater-uc\" target=\"_blank\" data-spm-c" +
                "lick=\"gostr=/tbieupdate20161112;locaid=d2;name=uc\">UC 浏览器</a>\"\n" +
                "        </div>\n" +
                "    <![endif]-->\n" +
                "\n" +
                "<body drop-down-menu-common-directive=\"\" class=\"ng-isolate-scope\">\n" +
                "    <!-- uiView: --><div class=\"base-container-div ng" +
                "-scope\" ui-view=\"\"><home class=\"ng-scope ng-isolate-scope" +
                "\"><div ng-class=\"{'eo_shrink_group_sidebar_container':$ctrl.serv" +
                "ice.sidebar.isShrink&amp;&amp;$ctrl.service.group.isShrink,'shrink-div':$ctrl" +
                ".service.sidebar.isShrink,'shrink-group-div':$ctrl.service.group.isShrink}\"><eo-" +
                "navbar5 class=\"ng-isolate-scope\"><div class=\"nav-container f_row f_js_ac n0_style\"><div class" +
                "=\"space-container\"><div class=\"n_list0_style nav-item overview_step1_class\" ng-click=\"$ctrl.fun.ch" +
                "angeSpace()\" ng-show=\"!$ctrl.service.navbar.info.navigation.query\"><span class=\"iconfont icon-yingy" +
                "ongAPP fs24 mr5\"></span> <span class=\"space-name ng-binding\">工作空间 | 夏蟬" +
                "奏的免费空间</span> <span class=\"iconfont icon-jiantou_liebiaozhankai fs12 ml5\"" +
                "></span></div></div><div class=\"f_row zindex-top-container ml240\" ng-class=\"{'ml2" +
                "40':!$ctrl.service.sidebar.isShrink,'ml50':$ctrl.service.sidebar.isShrink}\"><div c" +
                "lass=\"n_r_style nav-router-container f_row ml20\"><!-- ngRepeat: item in $ctrl.se" +
                "rvice.navbar.info.navigation.query --> <span class=\"ng-binding\"></span> <!-- ngI" +
                "f: $ctrl.service.navbar.info.navigation.extra --></div></div><div class=\"f_row" +
                " zindex-top-container\"><!-- ngIf: $ctrl.data.isUpgrade --><div class=\"sel" +
                "ect-nav-item pc_download_box n_list0_style\"><div class=\"text-ni cb\"><span c" +
                "lass=\"iconfont icon-diannao fs24\"></span> <span class=\"mr5\">免费下载客户端</span></div" +
                "><div class=\"list-ni\"><img class=\"va_top\" src=\"../assets/images/pc_download_1.jpg\"><");
        a.append("div class=\"button_box f_row f_jc_ac\"><button type=\"button\" class=\"download_pc_button\" n" +
                "g-mousedown=\"$ctrl.fun.downloadPackage('winX64_exe')\"><span class=\"iconfont icon-windows fs24 " +
                "mr5\"></span> <span class=\"fs14 va_top\">Windows</span></button> <button type=\"button\" class=\"do" +
                "wnload_pc_button ml20\" ng-mousedown=\"$ctrl.fun.downloadPackage('macOs_exe')\"><span class=\"iconfont " +
                "icon-mac fs24 mr5\"></span> <span class=\"fs14 va_top\">Mac Os</span></button></div><img class=\"va_top\"" +
                " src=\"../assets/images/pc_download_2.jpg\"></div></div><div class=\"select-nav-item overview_step7_class " +
                "n_list0_style\"><div class=\"text-ni\"><span class=\"iconfont icon-guanyu fs24\"></span> <span class=\"mr5" +
                "\">帮助</span> <span class=\"iconfont icon-xuanzeqizhankai\"></span></div><ul class=\"list-ni\"><li class=" +
                "\"item-lni n_list1_i_style\"><a href=\"http://help.eolinker.com\" onclick=\"eoSystemObj.openLink(eve" +
                "nt)\" rel=\"nofollow\">使用教程</a></li><li class=\"item-lni n_list1_i_style\"><a ng-click=\"$ctrl.fun." +
                "showOnlineService()\">自动客服</a></li><li class=\"item-lni n_list1_i_style\"><a ui-sref=\"home.feedback.ba" +
                "sic.list\" href=\"#/home/feedback/basic/?spaceKey=kyeiSGt4f31c65c124000d8ef074691ef679fb7393f364" +
                "4\">工单</a></li><li class=\"img-item-lni n_dv_c\"><img alt=\"EOLINKER-专业api接口管理平台\" src=\"" +
                "assets/images/wx_qrcode.jpg\"></li></ul></div><div class=\"special-select-nav-item n_list0_style\"><di" +
                "v class=\"text-ni f_row f_ac\"><span class=\"user-logo-tni mr5\" ng-style=\"{'background-image':$ctrl.s" +
                "ervice.navbar.info.userInfo.userImage?'url(https://data.eolinker.com/'+$ctrl.service.navbar.info" +
                ".userInfo.userImage+')':''}\" style=\"background-image: url(&quot;https://data.eolinker.com/vpWFYZ" +
                "h144708e7f98d0a2b78275da08b0f47ca3ba2a135&quot;);\">&nbsp;</span> <span class=\"mr5 ng-binding\">夏蟬奏<" +
                "/span> <button type=\"button\" ng-click=\"$ctrl.fun.news()\" class=\"unread-new-tip ml5 ng-binding\" ng-s" +
                "how=\"$ctrl.service.navbar.info.userInfo.unreadMsgNum||$ctrl.service.navbar.info.userInfo.unreadNoticeN");
        a.append("um\">12</button></div><ul class=\"list-ni n_list1_style\"><li class=\"acount-item-lni\"><p><s" +
                "pan>余额</span> <a class=\"ml5 eo-label-danger fs12 plr5 recharge_link\" ui-sref=\"transaction.rec" +
                "harge\" href=\"#/transaction/recharge\">充值</a> <span class=\"pull-right price ng-binding\" title=\"￥0" +
                ".00\">￥0.00</span></p><p class=\"eco-ailni\"><span>EOC积分</span> <a class=\"ml5 eo-label-danger fs12 " +
                "plr5 recharge_link\" target=\"_blank\" ui-sref=\"shop.default\" href=\"#/shop/\">兑换</a> <span class=\"p" +
                "ull-right ng-binding\">240</span></p></li><li class=\"item-lni btn-item-lni n_list1_i_style\" ng-click=\"$c" +
                "trl.fun.news()\"><span class=\"iconfont icon-tongzhizhongxin fs22 pull-left mr5\"></span> <sp" +
                "an>消息</span> <span class=\"unread-new-tip pull-right mt12 ng-binding\" ng-show=\"$ctrl." +
                "service.navbar.info.userInfo.unreadMsgNum||$ctrl.service.navbar.info.userInfo.unreadNoticeNu" +
                "m\">12</span></li><li class=\"n_list1_i_style item-lni btn-item-lni\" ng-click=\"$ctrl.fun.se" +
                "tTheme()\"><span class=\"iconfont icon-zhuti_yifu fs22 pull-left mr5\"></span> <span>主题配" +
                "色</span></li><li class=\"n_list1_i_style item-lni btn-item-lni\" ng-click=\"$ctrl.fun.acco" +
                "unt()\"><span class=\"iconfont icon-gerentouxiang fs22 pull-left mr5\"></span> <span>个人账户" +
                "设置</span></li><a ui-sref=\"index({status:'link-jump'})\" href=\"#/?status=link-jump\"><li class=" +
                "\"n_list1_i_style divide-top-item-lni n_dv_c item-lni btn-item-lni\"><span class=\"iconfont icon-shouye f" +
                "s22 pull-left mr5\"></span> <span>回到首页</span></li></a><li class=\"item-lni btn-item-lni n" +
                "_list1_i_style\" ng-click=\"$ctrl.fun.logout()\"><span class=\"iconfont icon-kaiguan fs22 p" +
                "ull-left mr5\"></span> <span>退出登录</span></li></ul></div></div></div></eo-navbar5><!-- ng" +
                "If: $ctrl.data.sidebarShow --><eo-sidebar ng-if=\"$ctrl.data.sidebarShow\" class=\"ng-scope" +
                " ng-isolate-scope\"><sidebar-common-component power-object=\"$ctrl.service.authority.spacePermi" +
                "ssion\" permission-object=\"{isHideAmtTips:{disabled:!$ctrl.data.isHideAmtTips}}\" main-object=\"$" +
                "ctrl.component.sidebarCommonObject.mainObject\" class=\"ng-isolate-scope\"><div class=\"eo-sidebar" +
                " s_style  default-div\" ng-class=\"{'default-div':!$ctrl.service.sidebar.isShrink,'shrink-si" +
                "    <div id=\"plug-div-js\"></div>\n" +

                "    <eo-modal class=\"ng-isolate-scope\"></eo-modal>\n" +
                "    <eo-template-init-level-directive><script type=\"text/ng-template\" id=\"paramDetail_Template_js\"><div class=\"container_pdtj\" style=\"padding-left: -{-(item.listDepth||0)*29+20-}-px\" {eoData}-show=\"item.isClick\"> <p class=\"f_row\" {eoData}-if=\"item.paramLimit\"> <span class=\"title-span mw_100\">参数限制：</span> <span class=\"wb_all\" {eoData}-class=\"{'eo-status-warning':item.mark.limit}\">-{-item.paramLimit-}-</span> </p> <p class=\"f_row\" {eoData}-if=\"$ctrl.mainObject.dbFieldObj[item.dbArr[2]]&&$ctrl.mainObject.setting.from!=='share'\"> <span class=\"title-span mw_110\">关联的数据库信息：</span> <span class=\"wb_all\">-{-$ctrl.mainObject.dbFieldObj[item.dbArr[2]].databaseName+' / '+$ctrl.mainObject.dbFieldObj[item.dbArr[2]].tableName+' / '+$ctrl.mainObject.dbFieldObj[item.dbArr[2]].fieldName-}-</span> <button type=\"button\" class=\"eo-operate-btn mw_100\" ng-click=\"$ctrl.mainObject.baseFun.showField($ctrl.mainObject.dbFieldObj[item.dbArr[2]].fieldID)\">查看字段信息</button> </p> <div class=\"f_row\" {eoData}-if=\"(item.paramValueList.length>0&&item.paramValueList[0].value)\"> <span class=\"title-span mw_100\">值可能性：</span> <table> <tr {eoData}-repeat=\"childItem in item.paramValueList track by $index\"> <td class=\"value-td\"> <span {eoData}-class=\"{'eo-status-success':childItem.mark=='add','eo-status-danger':childItem.mark=='delete'}\">-{-childItem.value-}-</span> </td> <td class=\"type-td\" {eoData}-if=\"childItem.paramType&&$ctrl.mainObject.tdList[1].selectQuery\"> <span class=\"divide-span\">|</span> <span {eoData}-class=\"{'eo-status-default cp':$ctrl.mainObject.tdList[1].selectQuery[childItem.paramType]}\" {eoData}-click=\"$ctrl.mainObject.baseFun.showDataStructure({item:childItem,$event:$event})\">-{-$ctrl.mainObject.CONST.PARAM_TYPE_OBJ[childItem.paramType]||('['+($ctrl.mainObject.tdList[1].selectQuery[childItem.paramType].structureName||'string')+']')-}-</span> </td> <td class=\"desc-td f_row f_ac\" {eoData}-class=\"{'eo-status-warning':childItem.mark=='edit'}\" {eoData}-if=\"childItem.valueDescription\"> <span class=\"divide-span\">|</span> <span>-{-childItem.valueDescription-}-</span> </td> <td class=\"default-td mw_100 f_ac\" {eoData}-if=\"item.default==$index\"> <span class=\"divide-span\">|</span> <span>默认值</span> </td> </tr> </table> </div> <p class=\"f_row\" {eoData}-if=\"item.paramValue\"> <span class=\"title-span mw_100\">参数示例：</span> <span class=\"wb_all\" {eoData}-class=\"{'eo-status-warning':item.mark.value}\">-{-item.paramValue-}-</span> </p> </div></script></eo-template-init-level-directive>\n" +
                "    <script id=\"plug-inner-script\"></script>\n" +
                "    <script type=\"text/javascript\">\n" +
                "        if (window.location.href.indexOf(\"/home/\") == -1) {\n" +
                "            var _vds = _vds || [];\n" +
                "            window._vds = _vds;\n" +
                "            (function () {\n");
        a.append("                _vds.push(['enableHT', true]);\n" + "" +
                "                _vds.push(['setAccountId', 'ab098937878b37f7']);\n" +
                "                (function () {\n" +
                "                    var vds = document.createElement('script');\n" +
                "                    vds.type = 'text/javascript';\n" +
                "                    vds.async = true;\n" +
                "                    vds.src = ('https:' == document.location.protocol ? 'https://' : 'http://') +\n" +
                "                        'dn-growing.qbox.me/vds.js';\n" +
                "                    var s = document.getElementsByTagName('script')[0];\n" +
                "                    s.parentNode.insertBefore(vds, s);\n" +
                "                })();\n" +
                "            })();\n" +
                "        }\n" +
                "    </script>\n" +
                "\n");
        a.append("<!--[if lang js]-->\n" +
                "\n" +
                "\n" +
                "<!--[end lang js if]-->\n" +
                "<script src=\"scripts/app-0ea55e1588.js\"></script>\n" +
                "\n" +
                "\n" +
                "\n" +
                "<!--[if module js]-->\n" +
                "\n" +
                "<!--[end module js if]-->\n" +
                "\n" +
                "</body></html>");
        FileIO.writeFile("F:\\工作\\中集成", "test.txt", a.toString());
    }
}
