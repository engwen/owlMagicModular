package com.owl.shiro.controller;

import com.owl.comment.annotations.OwlCheckParams;
import com.owl.mvc.controller.CellBaseControllerAb;
import com.owl.mvc.dto.PageDTO;
import com.owl.mvc.dto.RelationDTO;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.mvc.vo.PageVO;
import com.owl.shiro.model.OwlPage;
import com.owl.shiro.model.OwlPageMenu;
import com.owl.shiro.service.OwlPageService;
import com.owl.shiro.service.RePageMenuService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/4/29.
 */
@RestController
@RequestMapping(value = "page", method = RequestMethod.POST, consumes = "application/json")
public class PageController extends CellBaseControllerAb<OwlPageService, OwlPage> {

    @Resource
    private OwlPageService owlPageService;
    @Resource
    private RePageMenuService rePageMenuService;

    @Override
    @RequestMapping("create")
    @OwlCheckParams(notNull = {"name"})
    public MsgResultVO<OwlPage> create(@RequestBody OwlPage model) {
        return owlPageService.create(model);
    }

    @Override
    @RequestMapping("delete")
    @OwlCheckParams(notNull = {"id"})
    public MsgResultVO delete(@RequestBody OwlPage model) {
        return owlPageService.delete(model);
    }

    @Override
    @RequestMapping("update")
    @OwlCheckParams(notNull = {"id"})
    public MsgResultVO<?> update(@RequestBody OwlPage model) {
        return owlPageService.update(model);
    }

    @Override
    @RequestMapping("list")
    public MsgResultVO<PageVO<OwlPage>> list(@RequestBody PageDTO<OwlPage> pageDTO) {
        return owlPageService.list(pageDTO);
    }


    /*--------------------------------------   菜单页面   ---------------------------------------*/

    /**
     * 获取页面对应的菜单
     * @param page 角色ID  權限ID集合
     * @return
     */
    @RequestMapping("pageMenuList")
    @OwlCheckParams(notNull = {"id"})
    public MsgResultVO pageMenuList(@RequestBody OwlPage page) {
        return owlPageService.pageMenuList(page);
    }

    /**
     * 為指定的頁面添加菜單
     * @param pageMenu 頁面啊ID  菜單ID
     * @return
     */
    @RequestMapping("addMenu")
    @OwlCheckParams(notNull = {"pageId", "menuId"})
    public MsgResultVO addMenu(@RequestBody OwlPageMenu pageMenu) {
        return rePageMenuService.insert(pageMenu);
    }

    /**
     * 為指定的頁面添加菜單
     * @param relationDTO 頁面啊ID  菜單ID集合
     * @return
     */
    @RequestMapping("addMenuList")
    @OwlCheckParams(notNull = {"id", "idList"})
    public MsgResultVO addMenuList(@RequestBody RelationDTO relationDTO) {
        return rePageMenuService.insertRelation(relationDTO);
    }

    /**
     * 刪除頁面的菜單
     * @param pageMenu 頁面啊ID  菜單ID
     * @return
     */
    @RequestMapping("removeMenu")
    @OwlCheckParams(notNull = {"pageId", "menuId"})
    public MsgResultVO removeMenu(@RequestBody OwlPageMenu pageMenu) {
        return rePageMenuService.delete(pageMenu);
    }

    /**
     * 刪除頁面的菜單集合
     * @param relationDTO 頁面啊ID  菜單ID集合
     * @return
     */
    @RequestMapping("removeMenuList")
    @OwlCheckParams(notNull = {"id", "idList"})
    public MsgResultVO removeMenu(@RequestBody RelationDTO relationDTO) {
        return rePageMenuService.deleteRelation(relationDTO);
    }

    /**
     * 更新頁面的菜單集合
     * @param relationDTO 頁面啊ID  菜單ID集合
     * @return
     */
    @RequestMapping("updateMenuList")
    @OwlCheckParams(notNull = {"id", "idList"})
    public MsgResultVO updateMenuList(@RequestBody RelationDTO relationDTO) {
        return rePageMenuService.update(relationDTO);
    }
}
