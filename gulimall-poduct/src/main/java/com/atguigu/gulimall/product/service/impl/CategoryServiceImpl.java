package com.atguigu.gulimall.product.service.impl;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.product.dao.CategoryDao;
import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.atguigu.gulimall.product.service.CategoryService;

/**
 * 商品菜单父类和子类查询
 *
 * @author yubo
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 查询商品所有分类，以及子类，并按照属性结构展示
     *
     * @return
     */
    @Override
    public List<CategoryEntity> listWithTree() {
        //查询所有菜单信息
        List<CategoryEntity> categoryEntityList = baseMapper.selectList(null);
        //在所有菜单信息中过滤父级编号为0
        //过滤完成后,将子节点过滤菜单信息放入集合中  也就是将父菜单传入进去查询子菜单
        //菜单顺序排序,最后将集合封装返回至 List 集合中
        List<CategoryEntity> categoryList = categoryEntityList.stream().filter(CategoryEntity -> CategoryEntity.getParentCid() == 0)
                .map((menu) -> {
                    menu.setChildren(getChildrens(menu, categoryEntityList));
                    return menu;
                })
                .sorted((menu1, menu2) -> {
                    return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
                })
                .collect(Collectors.toList());
        return categoryList;
    }

    /**
     * 查询所有子菜单名称
     *
     * @param root
     * @param all
     * @return
     * @author yubo
     */
    private List<CategoryEntity> getChildrens(CategoryEntity root, List<CategoryEntity> all) {
        //根据参数传递所有菜单,筛选父级菜单下面子菜单
        List<CategoryEntity> list = all.stream().filter(categoryEntity -> categoryEntity.getParentCid().equals(root.getCatId()))
                .map(categoryEntity -> {
                    categoryEntity.setChildren(getChildrens(categoryEntity, all));
                    return categoryEntity;
                })
                .sorted((menu1, menu2) -> {
                    return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
                })
                .collect(Collectors.toList());
        return list;
    }

    /**
     * 删除商品分类
     *
     * @param categoryId
     */
    @Override
    public void deleteProduct(Long[] categoryId) {
        //todo 校验删除节点是否被引用
        baseMapper.deleteBatchIds(Arrays.asList(categoryId));
    }

    /**
     * 查询属性分组全路径信息
     *
     * @param catelogId
     * @return 例如：[2, 34, 255]
     */
    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> pathsList = new ArrayList();
        List<Long> catelogPaths = findCatelogPaths(pathsList, catelogId);
        //将完整路径进行排序
        Collections.reverse(catelogPaths);
        return catelogPaths.toArray(new Long[0]);
    }

    /**
     * 根据路径递归查询属性分组信息
     *
     * @param pathsList
     * @param catelogId
     * @return
     */
    public List<Long> findCatelogPaths(List<Long> pathsList, Long catelogId) {
        //将传入进来分类id方入至全路径 List 中
        pathsList.add(catelogId);
        //根据属性id 查询是否存在父类id
        CategoryEntity byId = this.getById(catelogId);
        if (byId.getParentCid() != 0) {
            findCatelogPaths(pathsList, byId.getParentCid());
        }
        return pathsList;
    }


}