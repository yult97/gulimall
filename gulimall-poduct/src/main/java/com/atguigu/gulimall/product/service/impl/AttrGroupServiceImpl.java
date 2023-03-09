package com.atguigu.gulimall.product.service.impl;

import com.atguigu.common.utils.R;
import com.atguigu.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.atguigu.gulimall.product.dao.AttrDao;
import com.atguigu.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.atguigu.gulimall.product.entity.AttrEntity;
import com.atguigu.gulimall.product.vo.AttrRelationVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.product.dao.AttrGroupDao;
import com.atguigu.gulimall.product.entity.AttrGroupEntity;
import com.atguigu.gulimall.product.service.AttrGroupService;
import org.springframework.util.StringUtils;

@Slf4j
@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Autowired
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    @Autowired
    private AttrDao attrDao;

    @Autowired
    private AttrGroupDao attrGroupDao;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 根据分页参数、所属分类id 查询属性分组信息
     *
     * @param params
     * @param catelogId
     * @return
     * @author yubo
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catelogId) {
        String key = (String) params.get("key");
        //引用MyBatis-Plus组件根据所属分类id,以及分页参数进行查询
        QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<AttrGroupEntity>();
        if (!StringUtils.isEmpty(key)) {
            wrapper.and((obj) -> {
                obj.eq("attr_group_id", key).or().like("attr_group_name", key);
            });
        }
        //没有传入所属分类id,默认查询所有
        if (catelogId == 0) {
            IPage<AttrGroupEntity> page = this.page(
                    new Query<AttrGroupEntity>().getPage(params),
                    wrapper
            );
            //将分页数据返回,解析分页条数、总条数等等
            return new PageUtils(page);
        } else {
            wrapper.eq("catelog_id", catelogId);
            IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params), wrapper);
            return new PageUtils(page);
        }
    }

    /**
     * 根据属性分组编号获取属性分组的关联的所有属性
     *
     * @param attrgroupId
     * @return
     */
    @Override
    public List<AttrEntity> attrGroupRelation(Long attrgroupId) {
        //根据属性分组编号查询关联关系表信息
        List<AttrAttrgroupRelationEntity> attrgroupRelationEntityList = attrAttrgroupRelationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", attrgroupId));
        log.info("属性分组关联属性信息为{}", attrgroupRelationEntityList);
        //判断查询结果是否为空
        if (attrgroupRelationEntityList.size() > 0) {
            List<Long> collect = attrgroupRelationEntityList.stream().map((attr) -> {
                return attr.getAttrId();
            }).collect(Collectors.toList());
            List<AttrEntity> attrList = attrDao.selectList(new QueryWrapper<AttrEntity>().in("attr_id", collect));
            return attrList;
        }
        return null;
    }

    /**
     * 移除关联属性实现
     *
     * @param attrRelationVOS
     */
    @Override
    public void relationDelete(AttrRelationVO[] attrRelationVOS) {
        List<AttrAttrgroupRelationEntity> attrRelationList = Arrays.asList(attrRelationVOS).stream().map((attrRelationVO) -> {
            //此步骤原因是调用 AttrAttrgroupRelationDao 编写mapper文件,继承实体类为属性关联关系实体类
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(attrRelationVO, attrAttrgroupRelationEntity);
            return attrAttrgroupRelationEntity;
        }).collect(Collectors.toList());
        attrAttrgroupRelationDao.deletBatchList(attrRelationList);

    }


}