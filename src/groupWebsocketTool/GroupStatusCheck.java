package groupWebsocketTool;

import java.io.IOException;
import java.util.*;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.group.model.GroupService;
import com.group.model.GroupVO;
import com.grouplist.model.GroupListService;
import com.grouplist.model.GroupListVO;

public class GroupStatusCheck extends HttpServlet {
	MembersCenter membersCenter = null;
	String groupmessage;
	Timer timer;
	Date today;

	public void destroy() {
		System.out.println("timer destroy!!");
		timer.cancel();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	}

	public void init() {
		timer = new Timer();
		System.out.println("task in!!");

		TimerTask taskTest = new TimerTask() {
			public void run() {
				List<GroupVO> newlist = new GroupService().getAll();
				List<GroupVO> finallist = new GroupService().getAll_G2();
				List<GroupVO> newList = new ArrayList<GroupVO>();// 過濾活動開始時間
				List<GroupVO> finalList = new ArrayList<GroupVO>();// 過濾活動結束時間
				GroupVO group = null;
				today = new Date();

				newList = newlist.stream().filter(p -> p.getGro_start().getTime() <= today.getTime())
						.collect(Collectors.toList());

				System.out.println("變更活動'進行中' = " + newList.size());

				if (finallist.size() != 0) {
					finalList = finallist.stream().filter(e -> e.getGro_end().getTime() <= today.getTime())
							.collect(Collectors.toList());
				}

				membersCenter = new MembersCenter();
//				System.out.println(membersCenter.getSessonKey()); //測試取得sessionKey
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (finalList.size() != 0) {
					for (int i = 0; i < finalList.size(); i++) {
						group = new GroupService().getOneGroup(finalList.get(i).getGro_no());
						group.setGro_status("G3");
						groupmessage = "你的" + group.getGro_name() + ",揪團活動結束囉!";

						new GroupService().updateGroup(group.getGro_no(), group.getMem_id(), group.getType_no(),
								group.getGro_name(), group.getGro_intro(), group.getGro_start(), group.getGro_end(),
								group.getGro_addr(), group.getGro_min(), group.getGro_max(), group.getGro_pic(),
								group.getGro_status(), group.getGro_lat(), group.getGro_lng());

						List<GroupListVO> groupMember = new GroupListService().findGroupMember(group.getGro_no());//找團員

						for (int j = 0; j < groupMember.size(); j++) {
							if (membersCenter.getSessonKey() != null) {
								String message = "{\"type\":\"finalStatus\",\"sender\":\""+group.getGro_no()+"\",\"receiver\":\""
										+ groupMember.get(j).getMem_id() + "\",\"message\":\"" + groupmessage + "\"}";
								System.out.println("send onmessage to Center = " + groupMember.get(j).getMem_id());
								membersCenter.onMessage(membersCenter.getSessionsMap().get(membersCenter.getSessonKey()), message);
							}
						}
					}
				}

				for (int i = 0; i < newList.size(); i++) { // 將時間到的揪團改變狀態(判斷人數 , >最少人數設為G2進行中 , <最少人數設為G1未完成下架
					group = new GroupService().getOneGroup(newList.get(i).getGro_no());
					if (new GroupListService().findGroupMember(newList.get(i).getGro_no()).size() >= group
							.getGro_min()) {
						group.setGro_status("G2");
						groupmessage = group.getGro_name() + ",人數足夠成功開團囉!";

					} else {
						group.setGro_status("G1");
						groupmessage = group.getGro_name() + ",人數不足開團失敗...";

					}
					new GroupService().updateGroup(group.getGro_no(), group.getMem_id(), group.getType_no(),
							group.getGro_name(), group.getGro_intro(), group.getGro_start(), group.getGro_end(),
							group.getGro_addr(), group.getGro_min(), group.getGro_max(), group.getGro_pic(),
							group.getGro_status(), group.getGro_lat(), group.getGro_lng());

					List<GroupListVO> groupMember = new GroupListService().findGroupMember(group.getGro_no());

					for (int j = 0; j < groupMember.size(); j++) {
						if (newList.size() != 0 && membersCenter.getSessonKey() != null) {
							String message = "{\"type\":\"statusCheck\",\"sender\":\""+group.getGro_no()+"\",\"receiver\":\""
									+ groupMember.get(j).getMem_id() + "\",\"message\":\"" + groupmessage + "\"}";
							System.out.println("send onmessage to Center = " + groupMember.get(j).getMem_id());
							membersCenter.onMessage(membersCenter.getSessionsMap().get(membersCenter.getSessonKey()),
									message);
						}
					}
				}
			}
		};
		timer.schedule(taskTest, 0, 30 * 1000);
	}
}
