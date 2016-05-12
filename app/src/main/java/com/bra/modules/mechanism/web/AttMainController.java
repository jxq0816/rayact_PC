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
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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

	@RequestMapping(value = {"uploadApi", ""} )
	@ResponseBody
	public Map<String, Object> uploadApi(HttpServletRequest req,
										 HttpServletResponse resp) throws IOException {
		final String size = req.getParameter(TokenController.FILE_SIZE_FIELD);
		final String fileName = req.getParameter(TokenController.FILE_NAME_FIELD);
		final String token = generateToken(fileName,size);
		Map<String, Object> json = new HashMap<>();
		FileModel fileModel = new FileModel();
		boolean success = true;
		String destPath = Global.getBaseDir();
		String tmp = destPath + "resources/www";
		File f = new File(tmp + File.separator + UploadUtils.MONTH_FORMAT.format(new Date()) + File.separator + token +"."+fileName.split("\\.")[1]);
		//解析器解析request的上下文
		CommonsMultipartResolver multipartResolver =
				new CommonsMultipartResolver(req.getSession().getServletContext());
		//先判断request中是否包涵multipart类型的数据，
		if(multipartResolver.isMultipart(req)){
			//再将request中的数据转化成multipart类型的数据
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) req;
			Iterator iter = multiRequest.getFileNames();
			while(iter.hasNext()){
				MultipartFile file = multiRequest.getFile((String)iter.next());
				if(file != null){
					//写文件到本地
					file.transferTo(f);
				}
			}
		}
		File dst = fileRepository.getFile(token, fileName);
		fileModel.setStoreType(StoreType.SYSTEM);
		fileModel.setToken(token);
		fileModel.setFilePath(dst.getAbsolutePath());
		AttMain attMain = new AttMain(fileModel);
		attMain = attMainService.saveAttMain(attMain);
		fileModel.setAttId(attMain.getId());
		if (success) {
			json.put("imagePath", com.bra.modules.sys.utils.StringUtils.ATTPATH + fileModel.getAttId());
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