package com.atguigu.srb.core.service.impl;

import com.atguigu.srb.core.enums.BorrowerStatusEnum;
import com.atguigu.srb.core.enums.IntegralEnum;
import com.atguigu.srb.core.mapper.BorrowerAttachMapper;
import com.atguigu.srb.core.mapper.UserInfoMapper;
import com.atguigu.srb.core.mapper.UserIntegralMapper;
import com.atguigu.srb.core.pojo.entity.*;
import com.atguigu.srb.core.mapper.BorrowerMapper;
import com.atguigu.srb.core.pojo.vo.BorrowerApprovalVO;
import com.atguigu.srb.core.pojo.vo.BorrowerAttachVO;
import com.atguigu.srb.core.pojo.vo.BorrowerDetailVO;
import com.atguigu.srb.core.pojo.vo.BorrowerVO;
import com.atguigu.srb.core.service.BorrowerAttachService;
import com.atguigu.srb.core.service.BorrowerService;
import com.atguigu.srb.core.service.DictService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.beans.Transient;
import java.util.List;

/**
 * <p>
 * 借款人 服务实现类
 * </p>
 *
 * @author yupengtao
 * @since 2022-03-30
 */
@Service
public class BorrowerServiceImpl extends ServiceImpl<BorrowerMapper, Borrower> implements BorrowerService {

    @Resource
    private BorrowerMapper borrowerMapper;

    @Resource
    private UserIntegralMapper userIntegralMapper;

    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private BorrowerAttachMapper borrowerAttachMapper;

    @Resource
    private DictService dictService;
    @Resource
    private BorrowerAttachService borrowerAttachService;

    @Transient()
    @Override
    public void saveBorrowerVOByUserId(BorrowerVO borrowerVO, Long userId) {

        //获取用户基本信息
        UserInfo userInfo = userInfoMapper.selectById(userId);

        Borrower borrower = new Borrower();
        BeanUtils.copyProperties(borrowerVO, borrower);
        borrower.setUserId(userId);
        borrower.setName(userInfo.getName());
//        borrower.setIdCard(userInfo.getIdCard());
        borrower.setMobile(userInfo.getMobile());
        borrower.setStatus(BorrowerStatusEnum.AUTH_RUN.getStatus());//认证中
        baseMapper.insert(borrower);

        //保存附件
        List<BorrowerAttach> borrowerAttachList = borrowerVO.getBorrowerAttachList();
        borrowerAttachList.forEach(borrowerAttach -> {
            borrowerAttach.setBorrowerId(borrower.getId());
            borrowerAttachMapper.insert(borrowerAttach);
        });

        //更新userInfo
        userInfo.setBorrowAuthStatus(BorrowerStatusEnum.AUTH_RUN.getStatus());
        userInfoMapper.updateById(userInfo);
    }

    @Override
    public Integer getStatusByUserid(Long userId) {
        QueryWrapper<Borrower> borrowerQueryWrapper = new QueryWrapper<>();
        borrowerQueryWrapper.select("status").eq("user_id", userId);
        List<Object> objects = baseMapper.selectObjs(borrowerQueryWrapper);
        if (objects.size() == 0) {
            return BorrowerStatusEnum.NO_AUTH.getStatus();
        }
        Integer status = (Integer) objects.get(0);
        return status;
    }

    @Override
    public IPage<Borrower> listPage(Page<Borrower> pageParam, String keyword) {
        if (StringUtils.isEmpty(keyword)) {
            return baseMapper.selectPage(pageParam, null);
        }
        QueryWrapper<Borrower> borrowerQueryWrapper = new QueryWrapper<>();
        borrowerQueryWrapper.like("name", keyword).or().like("id_card", keyword).or().like("mobile", keyword).orderByDesc("id");
        return baseMapper.selectPage(pageParam, borrowerQueryWrapper);
    }

    @Override
    public BorrowerDetailVO getBorrowerDetailVoById(Long id) {

        //获取借款人信息
        Borrower borrower = baseMapper.selectById(id);

        //填充基本借款人信息
        BorrowerDetailVO borrowerDetailVO = new BorrowerDetailVO();
        BeanUtils.copyProperties(borrower, borrowerDetailVO);

        //婚否
//        borrowerDetailVO.setMarry(borrower.getMarry() ? "是" : "否");
        //性别
        borrowerDetailVO.setSex(borrower.getSex() == 1 ? "男" : "女");

        //计算下拉列表选中内容

//        String education = dictService.getNameByParentDictCodeAndValue("education", borrower.getEducation());

//        String industry = dictService.getNameByParentDictCodeAndValue("moneyUse", borrower.getIndustry());

        String income = dictService.getNameByParentDictCodeAndValue("income", borrower.getIncome());

//        String returnSource = dictService.getNameByParentDictCodeAndValue("returnSource", borrower.getReturnSource());

        String contactsRelation = dictService.getNameByParentDictCodeAndValue("relation", borrower.getContactsRelation());

        //下拉列表
//        borrowerDetailVO.setEducation(education);
//        borrowerDetailVO.setIndustry(industry);
        borrowerDetailVO.setIncome(income);
//        borrowerDetailVO.setReturnSource(returnSource);
        borrowerDetailVO.setContactsRelation(contactsRelation);

        //审批状态
        String status = BorrowerStatusEnum.getMsgByStatus(borrower.getStatus());
        borrowerDetailVO.setStatus(status);

        //附件列表
        List<BorrowerAttachVO> borrowerAttachVOList = borrowerAttachService.selectBorrowerAttchVOList(id);
        borrowerDetailVO.setBorrowerAttachVOList(borrowerAttachVOList);
        return borrowerDetailVO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void approval(BorrowerApprovalVO borrowerApprovalVO) {
        //获取借款额度申请id
        Long borrowerId = borrowerApprovalVO.getBorrowerId();

        //借款额度申请对象
        Borrower borrower = baseMapper.selectById(borrowerId);

        //设置审核状态
        borrower.setStatus(borrowerApprovalVO.getStatus());
        baseMapper.updateById(borrower);

        //获取用户id
        Long userId = borrower.getUserId();

        //获取用户对象
        UserInfo userInfo = userInfoMapper.selectById(userId);


        //用户原始积分
        Integer integral = userInfo.getIntegral();

        //计算积分
        UserIntegral userIntegral = new UserIntegral();
        userIntegral.setUserId(userId);
        userIntegral.setIntegral(borrowerApprovalVO.getInfoIntegral());
        userIntegral.setContent("借款人基本信息");
        userIntegralMapper.insert(userIntegral);
        int currentintegral = integral + borrowerApprovalVO.getInfoIntegral();

        if (borrowerApprovalVO.getIsIdCardOk()) {
            userIntegral = new UserIntegral();
            userIntegral.setUserId(userId);
            userIntegral.setIntegral(IntegralEnum.BORROWER_IDCARD.getIntegral());
            userIntegral.setContent(IntegralEnum.BORROWER_IDCARD.getMsg());
            userIntegralMapper.insert(userIntegral);
            currentintegral = integral + IntegralEnum.BORROWER_IDCARD.getIntegral();
        }
        //房产信息
        if (borrowerApprovalVO.getIsHouseOk()) {
            userIntegral = new UserIntegral();
            userIntegral.setUserId(userId);
            userIntegral.setIntegral(IntegralEnum.BORROWER_HOUSE.getIntegral());
            userIntegral.setContent(IntegralEnum.BORROWER_HOUSE.getMsg());
            userIntegralMapper.insert(userIntegral);
            currentintegral = integral + IntegralEnum.BORROWER_HOUSE.getIntegral();
        }
        //车辆信息
        if (borrowerApprovalVO.getIsCarOk()) {
            userIntegral = new UserIntegral();
            userIntegral.setUserId(userId);
            userIntegral.setIntegral(IntegralEnum.BORROWER_CAR.getIntegral());
            userIntegral.setContent(IntegralEnum.BORROWER_CAR.getMsg());
            userIntegralMapper.insert(userIntegral);
            currentintegral = integral + IntegralEnum.BORROWER_CAR.getIntegral();
        }
        //设置用户总积分
        userInfo.setIntegral(currentintegral);
        //修改审核状态
        userInfo.setBorrowAuthStatus(borrowerApprovalVO.getStatus());
        //userinfo和borrower表改成真名
        userInfo.setName(borrower.getContactsName());
        borrower.setName(borrower.getContactsName());
        //更新
        userInfoMapper.updateById(userInfo);
        borrowerMapper.updateById(borrower);


    }
}
