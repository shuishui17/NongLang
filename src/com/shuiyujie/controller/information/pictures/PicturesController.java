package com.shuiyujie.controller.information.pictures;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.shuiyujie.controller.base.BaseController;
import com.shuiyujie.service.information.pictures.PicturesManager;
import com.shuiyujie.util.AppUtil;
import com.shuiyujie.util.Const;
import com.shuiyujie.util.DateUtil;
import com.shuiyujie.util.FileUpload;
import com.shuiyujie.util.GetWeb;
import com.shuiyujie.util.PageData;
import com.shuiyujie.util.PathUtil;
import com.shuiyujie.util.Tools;

/**
 * 爬去网页
 * @author 弄浪的鱼
 * @date 2017年5月24日
 */
@Controller
@RequestMapping(value="/pictures")
public class PicturesController extends BaseController {
	
	@Resource(name="picturesService")
	private PicturesManager picturesService;

	/**
	 * 跳转图片爬虫页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goImageCrawler")
	public ModelAndView goImageCrawler() throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("information/pictures/imageCrawler");
		return mv;
	}
	
	@RequestMapping(value="/getImagePath")
	@ResponseBody
	public Object getImagePath(){
		
		Map<String,Object> map = new HashMap<String,Object>();
		List<String> imgList = new ArrayList<String>(); // 图片列表
		PageData pd = this.getPageData();
		String errInfo = "success"; //返回信息
		String serverUrl = pd.getString("serverUrl");// 网页地址
		String msg = pd.getString("msg"); //msg:save 保存至服务器
		if(! serverUrl.startsWith("http://")){ //不是以 http 无效地址
			errInfo = "error";  
		}else{
			try {
				imgList = GetWeb.getImagePathList(serverUrl);
				if("save".equals(msg)){
					String ffile = DateUtil.getDays();
					String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile;		//文件上传路径
					for(int i=0;i<imgList.size();i++){	//把网络图片保存到服务器硬盘，并数据库记录
						String fileName = FileUpload.getHtmlPicture(imgList.get(i),filePath,null);								//下载网络图片上传到服务器上
						//保存到数据库
						pd.put("PICTURES_ID", this.get32UUID());			//主键
						pd.put("TITLE", "图片");								//标题
						pd.put("NAME", fileName);							//文件名
						pd.put("PATH", ffile + "/" + fileName);				//路径
						pd.put("CREATETIME", Tools.date2Str(new Date()));	//创建时间
						pd.put("MASTER_ID", "1");							//附属与
						pd.put("BZ", serverUrl+"爬取");						//备注
						//Watermark.setWatemark(PathUtil.getClasspath() + Const.FILEPATHIMG + ffile + "/" + fileName);//加水印
						picturesService.save(pd);
					}
				}
			} catch (Exception e) {
				errInfo = "error";						//出错
			}
		}
		
		map.put("imgList", imgList);					//图片集合
		map.put("result", errInfo);						//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
}
