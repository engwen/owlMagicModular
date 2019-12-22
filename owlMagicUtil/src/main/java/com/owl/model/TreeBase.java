package com.owl.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 樹狀數據工具基本類，提供自身id，父類id，子類集合的功能，方便使用工具類獲取指定集合
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/12/3.
 */
public abstract class TreeBase extends ModelPrototype {
    //自id
    private Long id;
    //父id
    private Long pid;
    //子集合
    private List<TreeBase> treeList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public List<TreeBase> getTreeList() {
        return treeList;
    }

    public void setTreeList(List<TreeBase> treeList) {
        this.treeList = treeList;
    }


    /**
     * 获取树  將一個 list 集合轉變為一顆樹
     * @param treeBases 分樹的對象
     * @return List
     */
    public static List<TreeBase> getTree(List<TreeBase> treeBases) {
        List<TreeBase> root = new ArrayList<>();
        treeBases.forEach(it -> {
            if (null == it.getPid() || it.getPid() == 0 || it.getPid() < 0) {
                root.add(getTrees(it, treeBases));
            }
        });
        return root;
    }

    /**
     * 获取子叶 為樹添加樹和子葉
     * @param root      根
     * @param treeBases 子葉
     * @return TreeBase
     */
    private static TreeBase getTrees(TreeBase root, List<TreeBase> treeBases) {
        treeBases.forEach(treeVO -> {
            if (null != treeVO.getPid() && treeVO.getPid().equals(root.getId())) {
                root.getTreeList().add(getTrees(treeVO, treeBases));
            }
        });
        return root;
    }

    /**
     * 获取对应的开始节点，并返回目标节点及以下的树id集合
     * @param aimID     目标节点
     * @param treeBases 所有的集合
     * @return List
     */
    public static List<Long> getIdList(Long aimID, List<TreeBase> treeBases) {
        List<Long> idList = new ArrayList<>();
        getTreeList(aimID, treeBases).forEach(it -> idList.add(it.getId()));
        return idList;
    }

    /**
     * 获取对应的开始节点，并返回目标节点及以下的树集合
     * @param aimID     目标节点
     * @param treeBases 所有的集合
     * @return List
     */
    public static List<TreeBase> getTreeList(Long aimID, List<TreeBase> treeBases) {
        List<TreeBase> result = new ArrayList<>();
        if (null != aimID) {
            treeBases.forEach(treeVO -> {
                if ((aimID == 0 && treeVO.getPid() == 0) || aimID.equals(treeVO.getId())) {
                    result.addAll(getListTrees(treeVO, treeBases));
                }
            });
        }
        //获取根
        return result;
    }

    /**
     * 获取节点以及以下
     * @param root       根
     * @param treeVOList 對象集合
     * @return List
     */
    private static List<TreeBase> getListTrees(TreeBase root, List<TreeBase> treeVOList) {
        List<TreeBase> result = new ArrayList<>();
        treeVOList.forEach(treeVO -> {
            if (root.getId().equals(treeVO.getPid())) {
                result.addAll(getListTrees(treeVO, treeVOList));
            }
        });
        result.add(root);
        return result;
    }

}
