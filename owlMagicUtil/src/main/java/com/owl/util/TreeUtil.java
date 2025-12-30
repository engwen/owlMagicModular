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
    /**
     * 生成可以操作的树集合，树状结构第一步
     * @param list   待变成树的集合
     * @param getPid 获取上级id的方式
     * @param getId  获取本级id的方式
     * @param <T>    集合泛型
     * @param <ID>   id类型
     * @return 可操作的树集合
     */
    public static <T, ID> List<TreeBase<T, ID>> buildTreeBaseList(List<T> list,
                                                                  TreeNodeGetInfoLamda<T, ID> getPid,
                                                                  TreeNodeGetInfoLamda<T, ID> getId) {
        return TreeUtil.buildTreeBaseList(list, getPid, getId, null);
    }

    /**
     * 生成可以操作的树集合，树状结构第一步
     * @param list    待变成树的集合
     * @param getPid  获取上级id的方式
     * @param getId   获取本级id的方式
     * @param getName 获取本级名称的方式
     * @param <T>     集合泛型
     * @param <ID>    id类型
     * @return 可操作的树集合
     */
    public static <T, ID> List<TreeBase<T, ID>> buildTreeBaseList(List<T> list,
                                                                  TreeNodeGetInfoLamda<T, ID> getPid,
                                                                  TreeNodeGetInfoLamda<T, ID> getId,
                                                                  TreeNodeGetInfoLamda<T, String> getName) {
        List<TreeBase<T, ID>> result = new ArrayList<>();
        if (null == getName) {
            list.forEach(it -> {
                TreeBase<T, ID> node = new TreeBase<>();
                node.setPid(getPid.getInfo(it));
                node.setId(getId.getInfo(it));
                node.setNode(it);
                result.add(node);
            });
        } else {
            list.forEach(it -> {
                TreeBase<T, ID> node = new TreeBase<>();
                node.setPid(getPid.getInfo(it));
                node.setId(getId.getInfo(it));
                node.setNode(it);
                node.setName(getName.getInfo(it));
                result.add(node);
            });
        }
        return result;
    }

    /**
     * 获取对应的开始节点，并返回目标节点以及以下的树集合，当top为null时，默认查询整棵树
     * @param top       顶级名称
     * @param treeBases 分樹的對象
     * @param <T>       树类型
     * @param <ID>      id类型
     * @return List
     */
    public static <T, ID> List<TreeBase<T, ID>> getTree(ID top, List<TreeBase<T, ID>> treeBases) {
        List<TreeBase<T, ID>> root = new ArrayList<>();
        if (top == null) {
            treeBases.stream().filter(it -> null == it.getPid() || it.getPid().toString().replace(" ", "").isEmpty()
                    || it.getPid().equals(it.getId())).forEach(it -> root.add(TreeUtil.getTrees(it, treeBases)));
//            treeBases.forEach(it -> {
//                if (null == it.getPid() || it.getPid().toString().replace(" ", "").equals("")
//                        || it.getPid().equals(it.getId())) {
//                    root.add(getTrees(it, treeBases));
//                }
//            });
        } else {
            treeBases.stream().filter(it -> it.getId().equals(top)).forEach(it -> root.add(TreeUtil.getTrees(it, treeBases)));
//            treeBases.forEach(it -> {
//                if (it.getId().equals(top)) {
//                    root.add(getTrees(it, treeBases));
//                }
//            });
        }
        return root;
    }

    /**
     * 获取子叶 為樹添加樹和子葉
     * @param root      根
     * @param treeBases 子葉
     * @param <T>       树类型
     * @param <ID>      id类型
     * @return TreeBase
     */
    private static <T, ID> TreeBase<T, ID> getTrees(TreeBase<T, ID> root, List<TreeBase<T, ID>> treeBases) {
//        treeBases.forEach(treeVO -> {
//            if (null != treeVO.getPid() && treeVO.getPid().equals(root.getId())) {
//                root.getTreeList().add(getTrees(treeVO, treeBases));
//            }
//        });
        treeBases.stream().filter(treeVO -> null != treeVO.getPid() && treeVO.getPid().equals(root.getId()))
                .forEach(treeVO -> root.getTreeList().add(getTrees(treeVO, treeBases)));
        return root;
    }

    /**
     * 获取对应的开始节点，并返回目标节点及以下的树id集合
     * @param aimID     目标节点
     * @param treeBases 所有的集合
     * @param <T>       树类型
     * @param <ID>      id类型
     * @return List
     */
    public static <T, ID> List<ID> getIdList(ID aimID, List<TreeBase<T, ID>> treeBases) {
        List<ID> idList = new ArrayList<>();
        getTreeList(aimID, treeBases).forEach(it -> idList.add(it.getId()));
        return idList;
    }

    /**
     * 获取对应的开始节点，并返回目标节点以下的树集合
     * @param aimID     目标节点
     * @param treeBases 所有的集合
     * @param <T>       树类型
     * @param <ID>      id类型
     * @return List
     */
    public static <T, ID> List<TreeBase<T, ID>> getTreeList(ID aimID, List<TreeBase<T, ID>> treeBases) {
        List<TreeBase<T, ID>> result = new ArrayList<>();
        if (null != aimID) {
            treeBases.stream().filter(it -> aimID.equals(it.getPid())).forEach(it -> result.addAll(getListTrees(it, treeBases)));
//            treeBases.forEach(treeVO -> {
//                if (aimID.equals(treeVO.getPid())) {
//                    result.addAll(getListTrees(treeVO, treeBases));
//                }
//            });
        }
        //获取根
        return result;
    }

    /**
     * 获取节点以及以下
     * @param root       根
     * @param treeVOList 對象集合
     * @param <T>        树类型
     * @param <ID>       id类型
     * @return List
     */
    private static <T, ID> List<TreeBase<T, ID>> getListTrees(TreeBase<T, ID> root, List<TreeBase<T, ID>> treeVOList) {
        List<TreeBase<T, ID>> result = new ArrayList<>();
//        treeVOList.forEach(treeVO -> {
//            if (root.getId().equals(treeVO.getPid())) {
//                result.addAll(getListTrees(treeVO, treeVOList));
//            }
//        });
        treeVOList.stream().filter(treeVO -> root.getId().equals(treeVO.getPid()))
                .forEach(treeVO -> result.addAll(getListTrees(treeVO, treeVOList)));
        result.add(root);
        return result;
    }

    /**
     * 打印树状结构
     * @param trees 树
     * @param <T>   泛型
     * @param <ID>  id类型
     * @param top   根id
     */
    public static <T, ID> void printTree(List<TreeBase<T, ID>> trees, ID top) {
        String bank = "";
        if (!trees.isEmpty()) {
            Optional<TreeBase<T, ID>> any = trees.stream().filter(it -> ListUtil.isNotEmpty(it.getTreeList())).findAny();
            if (!any.isPresent()) {
                List<TreeBase<T, ID>> treeList = getTree(top, trees);
                printTree(bank + "\u0020\u0020", treeList);
                return;
            }
        }
        printTree(bank + "\u0020\u0020", trees);
    }

    private static <T, ID> void printTree(String bank, List<TreeBase<T, ID>> trees) {
        for (TreeBase<T, ID> it : trees) {
            System.out.print(bank);
            System.out.println(it.getName());
            if (ListUtil.isNotEmpty(it.getTreeList())) {
                printTree(bank + "\u0020\u0020", it.getTreeList());
            }
        }
    }
}
