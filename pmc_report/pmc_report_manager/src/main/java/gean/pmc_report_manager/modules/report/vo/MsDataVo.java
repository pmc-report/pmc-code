package gean.pmc_report_manager.modules.report.vo;

public class MsDataVo {
	
	private String equipment;

	private Integer workDay;
	
	private Float tarTechAvail;
	
	private Float techAvail;
	
	private Integer goodPartCount;
	
	private Float downTime;
	
	private Integer faultOcc;
	
	private Float buildTime;

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public Integer getWorkDay() {
		return workDay;
	}

	public void setWorkDay(Integer workDay) {
		this.workDay = workDay;
	}

	public Float getTarTechAvail() {
		return tarTechAvail;
	}

	public void setTarTechAvail(Float tarTechAvail) {
		this.tarTechAvail = tarTechAvail;
	}

	public Float getTechAvail() {
		return techAvail;
	}

	public void setTechAvail(Float techAvail) {
		this.techAvail = techAvail;
	}

	public Integer getGoodPartCount() {
		return goodPartCount;
	}

	public void setGoodPartCount(Integer goodPartCount) {
		this.goodPartCount = goodPartCount;
	}

	public Float getDownTime() {
		return downTime;
	}

	public void setDownTime(Float downTime) {
		this.downTime = downTime;
	}

	public Integer getFaultOcc() {
		return faultOcc;
	}

	public void setFaultOcc(Integer faultOcc) {
		this.faultOcc = faultOcc;
	}

	public Float getBuildTime() {
		return buildTime;
	}

	public void setBuildTime(Float buildTime) {
		this.buildTime = buildTime;
	}
	
	
}
