package com.atguigu.gulimall.product.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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
     * @return
     */
    @Override
    public List<CategoryEntity> listWithTree() {
        //查询所有菜单信息
        List<CategoryEntity> categoryEntityList = baseMapper.selectList(null);
        //在所有菜单信息中过滤父级编号为0
        //过滤完成后,将子节点过滤菜单信息放入集合中  也就是将父菜单传入进去查询子菜单
        List<CategoryEntity> categoryList = categoryEntityList.stream().filter(CategoryEntity -> CategoryEntity.getParentCid() == 0)
                .map((menu)-> {
                    menu.setChildren(getChildrens(menu,categoryEntityList));
                    return menu;
                })
                .sorted((menu1,menu2)->{
                    return (menu1.getSort()==null?0:menu1.getSort())-(menu2.getSort()==null?0:menu2.getSort());
                })
                .collect(Collectors.toList());
        return categoryList;
    }

    /**
     * 查询所有子菜单名称
     * @param root
     * @param all
     * @author yubo
     * @return
     */
    private List<CategoryEntity> getChildrens(CategoryEntity root, List<CategoryEntity> all) {
        //根据参数传递所有菜单,筛选父级菜单和
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

}