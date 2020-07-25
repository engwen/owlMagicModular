package com.owl.util;

import com.owl.model.TreeBase;
import com.owl.pattern.function.tree.TreeNodeGetInfoLamda;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2020/7/3.
 */
public abstract class TreeUtil {

    public static <T, ID> List<TreeBase<T, ID>> buildTreeBaseList(List<T> list, TreeNodeGetInfoLamda<T, ID> getPid, TreeNodeGetInfoLamda<T, ID> getId) {
        List<TreeBase<T, ID>> result = new ArrayList<>();
        list.forEach(it -> {
            TreeBase<T, ID> node = new TreeBase<>();
            node.setPid(getPid.getInfo(it));
            node.setId(getId.getInfo(it));
            node.setNode(it);
            result.add(node);
        });
        return result;
    }

    /**
     * 获取树  將一個 list 集合轉變為一顆樹
     * @param treeBases 分樹的對象
     * @return List
     */
    public static <T, ID> List<TreeBase<T, ID>> getTree(List<TreeBase<T, ID>> treeBases, ID top) {
        List<TreeBase<T, ID>> root = new ArrayList<>();
        if (top == null) {
            return root;
        }
        treeBases.forEach(it -> {
            if (null == it.getPid() || it.getPid().equals(top)) {
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
    private static <T, ID> TreeBase<T, ID> getTrees(TreeBase<T, ID> root, List<TreeBase<T, ID>> treeBases) {
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
    public static <T, ID> List<ID> getIdList(ID aimID, List<TreeBase<T, ID>> treeBases) {
        List<ID> idList = new ArrayList<>();
        getTreeList(aimID, treeBases).forEach(it -> idList.add(it.getId()));
        return idList;
    }

    /**
     * 获取对应的开始节点，并返回目标节点及以下的树集合
     * @param aimID     目标节点
     * @param treeBases 所有的集合
     * @return List
     */
    public static <T, ID> List<TreeBase<T, ID>> getTreeList(ID aimID, List<TreeBase<T, ID>> treeBases) {
        List<TreeBase<T, ID>> result = new ArrayList<>();
        if (null != aimID) {
            treeBases.forEach(treeVO -> {
                if (aimID.equals(treeVO.getId())) {
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
    private static <T, ID> List<TreeBase<T, ID>> getListTrees(TreeBase<T, ID> root, List<TreeBase<T, ID>> treeVOList) {
        List<TreeBase<T, ID>> result = new ArrayList<>();
        treeVOList.forEach(treeVO -> {
            if (root.getId().equals(treeVO.getPid())) {
                result.addAll(getListTrees(treeVO, treeVOList));
            }
        });
        result.add(root);
        return result;
    }

    /**
     * 打印树状结构
     * @param trees 树
     * @param <T>   泛型
     */
    public static <T, ID> void printTree(List<TreeBase<T, ID>> trees, ID top) {
        String bank = "";
        if (trees.size() > 0) {
            Optional<TreeBase<T, ID>> any = trees.stream().filter(it -> it.getTreeList().size() > 0).findAny();
            if (!any.isPresent()) {
                List<TreeBase<T, ID>> treeList = getTree(trees, top);
                printTree(bank + "\u0020\u0020", treeList);
                return;
            }
        }
        printTree(bank + "\u0020\u0020", trees);
    }

    private static <T, ID> void printTree(String bank, List<TreeBase<T, ID>> trees) {
        for (TreeBase<T, ID> it : trees) {
            System.out.print(bank);
            System.out.println(it.getId());
            if (it.getTreeList().size() > 0) {
                printTree(bank + "\u0020\u0020", it.getTreeList());
            }
        }
    }
}
