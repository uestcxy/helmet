package com.tianyi.helmet.service.log;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tianyi.helmet.server.controller.interceptor.OperaLogHolder;
import com.tianyi.helmet.server.entity.log.OperaLog;
import com.tianyi.helmet.server.util.Dates;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tianyi.helmet.BaseServiceTest;
import com.tianyi.helmet.server.service.log.OperaLogService;

public class OperaLogServiceTest extends BaseServiceTest {

	@Autowired
	private OperaLogService operaLogService;

	@Test
	public void testInsert() {
//		for(int i =0;i<100;i++){
//			OperaLog log = new OperaLog();
//			log.setClientId("yujiawei"+i);
//			log.setCreateTime(LocalDateTime.now());
//			log.setLogContent("正在进行数据查询操作!!");
//			helmetOperaLogService.insert(log);
//		}

		OperaLog log = new OperaLog();
		log.setClientId("wxy1000");
		log.setCreateTime(LocalDateTime.now());
		log.setUUID("2135613");
		log.setDeviceType("FWQ");
		log.setLogType("UPLOAD");
		log.setLogflowId("2165135165");
		log.setOrderNo(0);
		log.setLogDate(LocalDate.now());
		log.setLogTime(LocalDateTime.now());
		log.setLogContent("测试测试");
		log.setLogNature(0);
		operaLogService.insert(log);

	}

	@Test
	public void testListBy() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", "wxy");
		//params.put("start", 1);
		//params.put("length", 10);
		List<OperaLog> list = operaLogService.listBy(params);
		for (OperaLog log : list) {
			System.out.println(log.getLogTimeString());
		}
	}

	@Test
	public void testCountBy() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("logContentlike", "正在进行数据");
		int num = operaLogService.countBy(params);
		System.out.println(num);
	}

	@Test
	public void testSTElse1() {
		LocalDateTime dt = Dates.toLocalDateTime(new Date(Long.parseLong("20180823155610120")));
		LocalDate d = dt.toLocalDate();
		LocalTime t = dt.toLocalTime();
		System.out.println(dt.toString());
		System.out.println(d.toString());
		System.out.println(t.toString());
	}

	@Test
	public void testSTElse2() {
		String body = "1234,5678";
		String key = body.split(",")[0];
		String value = body.split(",")[1];
		System.out.println(key);
		System.out.println(value);
	}

	@Test
	public void testSTElse3() {
		long time1 = 0L;
		System.out.println(time1 != 0L);
	}

	@Test
	public void testSTElse4() {
		OperaLog log = new OperaLog();
		log.setClientId("wxy1000");
		log.setCreateTime(LocalDateTime.now());
		log.setUUID("2135613");
		log.setDeviceType("FWQ");
		log.setLogType("UPLOAD");
		log.setLogflowId("2165135165");
		log.setOrderNo(0);
		log.setLogDate(LocalDate.now());
		log.setLogTime(LocalDateTime.now());
		log.setLogContent("测试测试");
		log.setLogNature(0);

		OperaLogHolder.set(log);

		if(OperaLogHolder.get() != null)
		{
			System.out.print(1);
		}
		else
		{
			System.out.print(0);
		}

		OperaLogHolder.remove();
	}
}
