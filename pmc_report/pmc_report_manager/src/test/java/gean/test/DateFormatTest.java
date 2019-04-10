package gean.test;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import gean.pmc_report_common.common.utils.DateUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DateFormatTest {

	@Test
	public void test() {
	
		Date d = new Date();
		String da = DateUtils.format(d, DateUtils.DATE_TIME_PATTERN);
		System.out.println(da);
		
		Timestamp tp = new Timestamp(d.getTime());
		String ts = DateUtils.format(tp, DateUtils.DATE_TIME_PATTERN);
		System.out.println(tp);
		
		
		
	}
	
	@Test
	public void test1() {
	
		String str = "KT01_L/NA/KT01_R/NA";
		
		String str1 = "KT01_L";
		
		String[] spsNoArrys=str1.split("/");
		
		String spsNo="";
		
		for(int i=0;i<spsNoArrys.length;i++){
			if(!spsNoArrys[i].equals("NA")){
				spsNo=spsNoArrys[i];
				break;
			}
		}

		System.out.println(spsNo);
		
		
	}
}
