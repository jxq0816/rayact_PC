package com.bra.modules.mechanism.web;

import com.bra.common.config.Global;
import com.bra.common.persistence.Page;
import com.bra.common.upload.FileModel;
import com.bra.common.upload.FileRepository;
import com.bra.common.upload.StoreType;
import com.bra.common.upload.UploadUtils;
import com.bra.common.utils.StringUtils;
import com.bra.common.web.BaseController;
import com.bra.modules.mechanism.entity.AttMain;
import com.bra.modules.mechanism.service.AttMainService;
import com.bra.modules.sys.entity.User;
import com.bra.modules.sys.service.SystemService;
import com.bra.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.codec.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 文档管理Controller
 * @author 肖斌
 * @version 2015-12-30
 */
@Controller
@RequestMapping(value = "${adminPath}/mechanism/attMain")
public class AttMainController extends BaseController {
	@Autowired
	private SystemService systemService;
	@Autowired
	private FileRepository fileRepository;


	@Autowired
	private AttMainService attMainService;
	
	@ModelAttribute
	public AttMain get(@RequestParam(required=false) String id) {
		AttMain entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = attMainService.get(id);
		}
		if (entity == null){
			entity = new AttMain();
		}
		return entity;
	}
	
	@RequiresPermissions("mechanism:attMain:view")
	@RequestMapping(value = {"list", ""})
	public String list(AttMain attMain, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AttMain> page = attMainService.findPage(new Page<AttMain>(request, response), attMain); 
		model.addAttribute("page", page);
		return "modules/mechanism/attMainList";
	}

	@RequiresPermissions("mechanism:attMain:view")
	@RequestMapping(value = "form")
	public String form(AttMain attMain, Model model) {
		model.addAttribute("attMain", attMain);
		return "modules/mechanism/attMainForm";
	}

	@RequiresPermissions("mechanism:attMain:edit")
	@RequestMapping(value = "save")
	public String save(AttMain attMain, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, attMain)){
			return form(attMain, model);
		}
		attMainService.save(attMain);
		addMessage(redirectAttributes, "保存文档管理成功");
		return "redirect:"+Global.getAdminPath()+"/mechanism/attMain/?repage";
	}
	
	@RequiresPermissions("mechanism:attMain:edit")
	@RequestMapping(value = "delete")
	public String delete(AttMain attMain, RedirectAttributes redirectAttributes) {
		attMainService.delete(attMain);
		addMessage(redirectAttributes, "删除文档管理成功");
		return "redirect:"+Global.getAdminPath()+"/mechanism/attMain/?repage";
	}

	@RequestMapping(value = "uploadApi", method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> uploadApi(HttpServletRequest req,
										 HttpServletResponse resp) throws IOException {
		String file = req.getParameter("file");
		byte[] image = Base64.decode(file);
		Map<String, Object> json = new HashMap<>();
		FileModel fileModel = new FileModel();
		boolean success = true;
		String destPath = Global.getBaseDir();
		String tmp = destPath + "resources/www";
		//解析器解析request的上下文
		CommonsMultipartResolver multipartResolver =
				new CommonsMultipartResolver(req.getSession().getServletContext());
		File f =  new File(tmp + File.separator + UploadUtils.MONTH_FORMAT.format(new Date()) + File.separator + String.valueOf(new Date().getTime())+ UserUtils.getUser().getId());
		if (!f.getParentFile().exists())
			f.getParentFile().mkdirs();
		if (!f.exists())
			f.createNewFile();
		FileOutputStream fos = new FileOutputStream(f);
		fos.write(image);
		fos.close();
		fileModel.setStoreType(StoreType.SYSTEM);
		fileModel.setToken(new Date().toString());
		fileModel.setFilePath(f.getAbsolutePath());
		AttMain attMain = new AttMain(fileModel);
		attMain = attMainService.saveAttMain(attMain);
		fileModel.setAttId(attMain.getId());
		if (success) {
			json.put("status", "success");
			json.put("imagePath", com.bra.modules.sys.utils.StringUtils.ATTPATH + fileModel.getAttId());
			User user = UserUtils.getUser();
			user.setPhoto(com.bra.modules.sys.utils.StringUtils.ATTPATH + fileModel.getAttId());
			systemService.saveUser(user);
		}else{
			json.put("status", "fail");
			json.put("imagePath", "");
		}
		return json;
	}

	private String generateToken(String name, String size) throws IOException {
		if (name == null || size == null)
			return "";
		int code = name.hashCode();
		try {
			String token = (code > 0 ? "A" : "B") + Math.abs(code) + "_"
					+ size.trim();
			/** TODO: store your token, here just create a file */
			fileRepository.storeToken(token);

			return token;
		} catch (Exception e) {
			throw new IOException(e);
		}
	}
}