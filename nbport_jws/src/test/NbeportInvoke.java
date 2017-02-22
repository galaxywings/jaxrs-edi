package test;


import java.util.Date;
import service.EportWebServiceDelegate;
import service.EportWebServiceService;


public class NbeportInvoke {
	//wsimport -keep -d C:\Users\wangwy.WJE-LOGISTICS\workspace\springmvc\src\main\java -p com.edi.demo.nbport.webservice.client http://www.yibutong.com.cn/eport-dataservices/EportWebServicePort?wsdl
	public static void main(String[] args) {
		EportWebServiceService ss = new service.EportWebServiceService();
		EportWebServiceDelegate service = ss.getEportWebServicePort();
		String usr = "WS_WJDS";
		String pwd  = "64a7909b3b3abf538f7fe1f746aa155660d6bfa457b53da44495609845642823";//SHA-256
		String serviceID = "QueryLogisticsStatus";
//		String applyData = "{vessel_uncode:\"\",voyage:\"\",direction:\"E\",bl_no:\"HDMUNXCL0772075\",status:[\"01\"],ctn_no_list:[]}";
//		String applyData = "{direction:'E',bl_no:'COSU6126486030',ctn_no_list:[{ctn_no:'FCIU7020858',status:['01']}]}";
		String applyData = "{direction:'E',bl_no:'COSU6126486030',status:['00']}";
//		String applyData = "{arrival_port:'CNNGB',depend_plan_time_start:'20161101',depend_plan_time_end:'20161231'}";
//		String serviceID = "QueryVoyageInfo";
//		String serviceID = "QueryDatResult";
//		String applyData = "{bill_no:\"COSU6126486030\"}";
	    Date start = new Date();
		String result = service.callService(usr, pwd, serviceID, applyData);
//		JSONArray json = JSONArray.fromObject(result);
		Date end = new Date();
		System.out.println((end.getTime()-start.getTime()));
		System.out.println(result);
	}
}
