package com.zheng.upms.server.controller.tourism;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.tourism.data.dao.model.TourLandscape;
import com.tourism.data.dao.model.TourLandscapeExample;
import com.tourism.data.rpc.api.TourLandscapeService;
import com.zheng.common.base.BaseController;
import com.zheng.common.validator.LengthValidator;
import com.zheng.common.validator.NotNullValidator;
import com.zheng.upms.common.constant.UpmsResult;
import com.zheng.upms.common.constant.UpmsResultConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户controller
 * Created by shuzheng on 2017/2/6.
 *tourism/landscape/index
 //import org.apache.shiro.authz.annotation.RequiresPermissions;
 */
@Controller
@Api(value = "景点管理", description = "景点管理")
@RequestMapping("/tourism/landscape")
public class LandscapeController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LandscapeController.class);

    @Autowired
    private TourLandscapeService tourLandscapeService;


    @ApiOperation(value = "景点首页")
//    @RequiresPermissions("upms:landscape:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/tourism/landscape/index.jsp";
    }


    @ApiOperation(value = "景点列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(
            @RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
            @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
            @RequestParam(required = false, defaultValue = "", value = "search") String search,
            @RequestParam(required = false, value = "sort") String sort,
            @RequestParam(required = false, value = "order") String order) {
        TourLandscapeExample upmsUserExample = new TourLandscapeExample();
        if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
            upmsUserExample.setOrderByClause(sort + " " + order);
        }
        if (StringUtils.isNotBlank(search)) {
            upmsUserExample.or()
                    .andNameLike("%" + search + "%");
            upmsUserExample.or()
                    .andNameLike("%" + search + "%");
        }
        List<TourLandscape> rows = tourLandscapeService.selectByExampleForOffsetPage(upmsUserExample, offset, limit);
        long total = tourLandscapeService.countByExample(upmsUserExample);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", rows);
        result.put("total", total);
        return result;
    }

    @ApiOperation(value = "新增景点")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create() {
        return "/tourism/landscape/create.jsp";
    }

    @ApiOperation(value = "新增景点")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Object create(TourLandscape upmsUser) {
        ComplexResult result = FluentValidator.checkAll()
                .on(upmsUser.getName(), new LengthValidator(1, 20, "帐号"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return new UpmsResult(UpmsResultConstant.INVALID_LENGTH, result.getErrors());
        }
        long time = System.currentTimeMillis();
        upmsUser.setCtime(time);
        tourLandscapeService.insert(upmsUser);
        LOGGER.info("新增景点，主键：userId={}", upmsUser.getId());
        return new UpmsResult(UpmsResultConstant.SUCCESS, 1);
    }

    @ApiOperation(value = "删除景点")
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable("ids") String ids) {
        int count = tourLandscapeService.deleteByPrimaryKeyss(ids);
        return new UpmsResult(UpmsResultConstant.SUCCESS, count);
    }

    @ApiOperation(value = "修改景点")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") String id, ModelMap modelMap) {
        TourLandscape user = tourLandscapeService.selectByPrimaryKey(id);
        modelMap.put("user", user);
        return "/tourism/landscape/update.jsp";
    }

    @ApiOperation(value = "修改景点")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable("id") int id, TourLandscape upmsUser) {
        ComplexResult result = FluentValidator.checkAll()
                .on(upmsUser.getName(), new LengthValidator(1, 20, "帐号"))
                .on(upmsUser.getName(), new NotNullValidator("姓名"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return new UpmsResult(UpmsResultConstant.INVALID_LENGTH, result.getErrors());
        }
        int count = tourLandscapeService.updateByPrimaryKeySelective(upmsUser);
        return new UpmsResult(UpmsResultConstant.SUCCESS, count);
    }

}
