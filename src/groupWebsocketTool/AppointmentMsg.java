package groupWebsocketTool;

public class AppointmentMsg {
	private String type;
	private String sender;
	private String receiver;
	private String message;
	private String mem_ID;
	private String i_class_no;
	private String i_order_no;
	private String rDate;
	private String hrIndex;
	private String p_coin;
	private String hr;

	
	public AppointmentMsg(String type, String sender, String receiver, String message, String mem_ID, 
			String i_class_no, String i_order_no, String rDate, String hrIndex,String hr) {
		this.type = type;
		this.sender = sender;
		this.receiver = receiver;
		this.message = message;
		this.mem_ID = mem_ID;
		this.i_class_no = i_class_no;
		this.i_order_no = i_order_no;
		this.rDate = rDate;
		this.hrIndex = hrIndex;
	}
	
	public String getHr() {
		return hr;
	}
	public void setHr(String hr) {
		this.hr = hr;
	}
	public String getMem_ID() {
		return mem_ID;
	}
	public void setMem_ID(String mem_ID) {
		this.mem_ID = mem_ID;
	}
	public String getI_class_no() {
		return i_class_no;
	}
	public void setI_class_no(String i_class_no) {
		this.i_class_no = i_class_no;
	}
	public String getI_order_no() {
		return i_order_no;
	}
	public void setI_order_no(String i_order_no) {
		this.i_order_no = i_order_no;
	}
	public String getrDate() {
		return rDate;
	}
	public void setrDate(String rDate) {
		this.rDate = rDate;
	}
	public String getHrIndex() {
		return hrIndex;
	}
	public void setHrIndex(String hrIndex) {
		this.hrIndex = hrIndex;
	}
	public String getP_coin() {
		return p_coin;
	}
	public void setP_coin(String p_coin) {
		this.p_coin = p_coin;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
