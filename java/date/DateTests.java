package date;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Set;
import java.util.TimeZone;

/**
 * @Author dingc
 * @Date 2021/12/18 13:24
 */

public class DateTests {

	/**
	 * date
	 */
	@Test
	public void testDate() {
		Date currDate = new Date();
		// 国际统一时间字符串格式	Sat Dec 18 13:29:43 CST 2021, CST代表 China standard time
		System.out.println(currDate.toString());
		// 本地时间字符串格式	2021-12-18 13:31:57
		System.out.println(currDate.toLocaleString());
		// 国际统一GMT字符串格式	18 Dec 2021 05:31:57 GMT
		System.out.println(currDate.toGMTString());
	}

	/**
	 * date 也有时区和偏移量
	 */
	@Test
	public void testTimeZone() throws ParseException {
		String[] availableIDs = TimeZone.getAvailableIDs();
		TimeZone defaultTz = TimeZone.getDefault();
		int i = 0;
		for (String availableID : availableIDs) {
			if (i == 5) {
				break;
			}
			System.out.println(availableID);
			i++;
		}
		System.out.println("================");
		System.out.println(defaultTz);

		System.out.println("================");
		String patternStr = "yyyy-MM-dd HH:mm:ss";
		// 北京时间(new出来就是默认时区的时间)
		Date bjDate = new Date();
		System.out.println("时间戳: " + bjDate.getTime());

		// 得到纽约的时区
		TimeZone newYorkTimeZone = TimeZone.getTimeZone("America/New_York");
		// 根据此时区, 将北京时间转换为纽约的Date
		DateFormat newYorkDateFormat = new SimpleDateFormat(patternStr);
		newYorkDateFormat.setTimeZone(newYorkTimeZone);
		System.out.println("北京: " + new SimpleDateFormat(patternStr).format(bjDate));
		System.out.println("纽约: " + newYorkDateFormat.format(bjDate));

		// 注意:同一时刻, 不同时区的时间展现形式不一样, 但是时刻一致

		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
		sdf.format(new Date());
		sdf.parse("2021-12-18 14:16:00");
	}

	@Test
	public void testInstant() {
		// 使用UTC (世界协调时间), 而不是 GMT
		// 时间与时区关联
		Instant instant = Instant.ofEpochMilli(new Date().getTime());
		Date from = Date.from(instant);
		System.out.println(instant.toEpochMilli());
		System.out.println(from.getTime());
		System.out.println(instant);
		System.out.println(from);
		// 时间戳相同, 但是时间展现形式不同
	}

	@Test
	public void testLocalDateTime() {
		// LocalDate, LocalDateTime, LocalTime
		LocalDate date = LocalDate.now();
		LocalDateTime dateTime = LocalDateTime.now();
		LocalTime time = LocalTime.now();
		System.out.println(date);
		System.out.println(dateTime);
		System.out.println(time);
		// 本地时间, 本地的Local timezone
	}

	@Test
	public void testZoneDateTime() {
		// 时区相关
		System.out.println("=================带时区的时间ZonedDateTime=================");
		System.out.println(ZonedDateTime.now());    // 系统时区
		System.out.println(ZonedDateTime.now(ZoneId.of("America/New_York")));
		System.out.println(ZonedDateTime.now(Clock.systemUTC()));
	}

	@Test
	public void testOffsetDateTime() {
		System.out.println("=================带时区的时间OffsetDateTime=================");
		System.out.println(OffsetDateTime.now());    // 系统时区
		System.out.println(OffsetDateTime.now(ZoneId.of("America/New_York")));
		System.out.println(OffsetDateTime.now(Clock.systemUTC()));
	}

	@Test
	public void testTransferBetweenZones() {
		Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();

		// 用Instant接收时间, (db, 前端接口(String ts))
		// 此时的fromDb: 2021-12-18T07:05:33.158Z
		Instant fromDb = Instant.now();
		// 时区之间转换
		ZonedDateTime sydney = ZonedDateTime.ofInstant(fromDb, ZoneId.of("Australia/Sydney"));
		ZonedDateTime beijing = ZonedDateTime.ofInstant(fromDb, ZoneId.systemDefault());
		System.out.println(sydney.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		System.out.println(beijing.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
	}

}
