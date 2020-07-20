package com.owl.util;

import com.owl.model.TreeBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2020/7/3.
 */
public abstract class TreeUtil {
    /**
     * 获取树  將一個 list 集合轉變為一顆樹
     * @param treeBases 分樹的對象
     * @return List
     */
    public static <T extends TreeBase<T>> List<T> getTree(List<T> treeBases) {
        List<T> root = new ArrayList<>();
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
    private static <T extends TreeBase<T>> T getTrees(T root, List<T> treeBases) {
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
    public static <T extends TreeBase<T>> List<Long> getIdList(Long aimID, List<T> treeBases) {
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
    public static <T extends TreeBase<T>> List<T> getTreeList(Long aimID, List<T> treeBases) {
        List<T> result = new ArrayList<>();
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
    private static <T extends TreeBase<T>> List<T> getListTrees(T root, List<T> treeVOList) {
        List<T> result = new ArrayList<>();
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
    public static <T extends TreeBase<T>> void printTree(List<T> trees) {
        String bank = "";
        if (trees.size() > 0) {
            Optional<T> any = trees.stream().filter(it -> it.getTreeList().size() > 0).findAny();
            if (!any.isPresent()) {
                List<T> treeList = getTree(trees);
                printTree(bank + "\u0020\u0020", treeList);
                return;
            }
        }
        printTree(bank + "\u0020\u0020", trees);
    }

    private static <T extends TreeBase<T>> void printTree(String bank, List<T> trees) {
        for (T it : trees) {
            System.out.print(bank);
            System.out.println(it.getName());
            if (it.getTreeList().size() > 0) {
                printTree(bank + "\u0020\u0020", it.getTreeList());
            }
        }
    }
}
