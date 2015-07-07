package org.ike.youme.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import org.ike.youme.entity.Activity;
import org.ike.youme.entity.Attend;
import org.ike.youme.entity.Type;
import org.ike.youme.entity.User;
import org.ike.youme.service.ActivityService;
import org.ike.youme.service.AttendService;
import org.ike.youme.service.BrowseService;
import org.ike.youme.service.CollectService;
import org.ike.youme.service.CommentService;
import org.ike.youme.service.FootprintService;
import org.ike.youme.service.PhotoService;
import org.ike.youme.service.TypeService;
import org.ike.youme.service.UserService;
import org.ike.youme.util.DataCollection;
import org.ike.youme.util.Tool;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 测试访问控制类(类资源访问：/test/*)
 * 
 * @author 林玉生
 * 
 * @since 2015-06-01
 * 
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private ActivityService activityService;
	@Autowired
	private AttendService attendService;
	@Autowired
	private UserService userService;
	@Autowired
	private FootprintService footprintService;
	@Autowired
	private PhotoService photoService;
	@Autowired
	private CollectService collectService;
	@Autowired
	private BrowseService browseService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private TypeService typeService;
	
	@ResponseBody
	@RequestMapping("/addUser")
	public void addUser() {
		for (int i = 0; i < 99; i++) {
			User user = new User();
			if (i< 10) {
				user.setAccount("1831602260"+i);
			}else {
				user.setAccount("183160226"+i);
			}
			user.setPwd("123456");
			user.setHead("/images/head/default.jpg");
			user.setAge(0);
			user.setSex("男");
			user.setNickname("呵呵");
			user.setSign("他很懒，什么也没留下~");
			user.setCity("韶关");
			user.setCreateTime(Tool.getCurrentTime());
			userService.save(user);
		}
	}
	@ResponseBody
	@RequestMapping("/addAttend")
	public void addAttend() {
		for (int i = 0; i < 50; i++) {
			Attend attend = new Attend();
			Activity activity = new Activity();
			activity.setActivityId(1);
			User user = new User();
			user.setUserId(i+1);
			attend.setActivity(activity);
			attend.setUser(user);
			attendService.save(attend);
		}
	}
	@ResponseBody
	@RequestMapping("/addActivity")
	public String addActivity() throws Exception{
		for (int i = 2; i < 12; i++) {
			String url = "http://www.huodongxing.com/eventlist?orderby=r&tag=%E6%A0%A1%E5%9B%AD&city=%E5%85%A8%E9%83%A8&page="+i;
			
			Document document = Jsoup.connect(url).get();
			Elements elements = document.select(".event-horizontal-list-new li");
			for (Element element : elements) {
				String title = element.select("h3 a").html();
				String imgSrc = element.select("img").attr("src");
				System.out.println("正在下载图片：----"+imgSrc);
				DataCollection.downImages("E://GitHub/YouMe/WebRoot/images/poster/", imgSrc);
				imgSrc = "/images/poster"+imgSrc.substring(imgSrc.lastIndexOf("/"));
				
				Activity activity = new Activity();
				Type type = new Type();
				User user = new User();
				type.setTypeId(13);
				user.setUserId(13);
				activity.setType(type);
				activity.setUser(user);
				activity.setTitle(title);
				activity.setPoster(imgSrc);
				activity.setCity("广州");
				activity.setPlace("韶关学院");
				activity.setDescription("活动对象： 	韶关学院全日制在校学生 活动时间： 	2014年11—12月 活动要求： 1、作品要以思想性、艺术性和观赏性统一的原则，反映大学生现实生活，围绕学习就业、社团生活、志愿者活动、心理健康、校园文化、团日活动、创新创业等方面，表现时代精神和“最美青春”的比赛主题，展现青年大学生践行核心价值观、追逐青春梦想的历程。 2、主题鲜明，情节合理，贴近生活。要有完整的剧情主线，具备内容丰富，构思新颖，在拍摄上具备可操作性。 3、剧本必须为原创，作者享有独立的版权，内容不得涉及侵权问题，不得由他人代替，不得抄袭，可参考网上的素材，但所占作品内容不得超过20%，否则视为抄袭，一经发现，立即取消参赛资格。若是借用网上素材，建议附交所采用素材说明。不接受已完成拍摄的微电影剧本。 4、剧本可供拍摄时长限度在10分钟以内，字数5000字以内。不接受剧本大纲、创意、故事梗概等文字过于简略的稿件以及小说、散文、电视剧等其他体裁的稿件。 5、剧本提交格式：①封面：注明剧本名称、编剧姓名、联系电话、电子邮箱；②扉页：剧本大纲和主要人物简介表；③剧本正文。 6、剧本类别：心理健康类、社团生活类、校园生活类志愿服务类、创新创业类、校园文化类。");
				activity.setLatitude(39.9824);
				activity.setLongitude(116.3053);
				activity.setCreateTime(Tool.getCurrentTime());
				activity.setStartTime(Tool.getCurrentTime());
				activity.setEndTime(Tool.getCurrentTime());
				activity.setAttendNum(0);
				activity.setBrowseNum(0);
				activityService.save(activity);
			}
		}
		
		return "yes";
	}
}
