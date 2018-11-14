package com.zheng.upms.server.controller.tourism;

import com.zheng.common.base.BaseController;
import com.zheng.upms.common.constant.UpmsResult;
import com.zheng.upms.common.constant.UpmsResultConstant;
import com.zheng.upms.dao.model.UpmsPermission;
import com.zheng.upms.dao.model.UpmsSystem;
import com.zheng.upms.dao.model.UpmsSystemExample;
import com.zheng.upms.dao.model.UpmsUser;
import com.zheng.upms.rpc.api.UpmsApiService;
import com.zheng.upms.rpc.api.UpmsSystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * 后台controller
 * Created by ZhangShuzheng on 2017/01/19.
 */
@Controller
@RequestMapping("/tourism")
@Api(value = "后台管理", description = "后台管理")
public class TourismController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TourismController.class);

	@ApiOperation(value = "后台首页")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		String path1 = System.getProperty("user.dir");
		return "/html/index.html";
	}

	@ApiOperation(value = "后台首页")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Object list(ModelMap modelMap) {
		UpmsResult result = new UpmsResult();
		try {

		}catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}

}