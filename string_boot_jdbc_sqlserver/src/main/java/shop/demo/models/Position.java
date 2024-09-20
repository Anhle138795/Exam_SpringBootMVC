package shop.demo.models;

public class Position {
	private int _id;
	private String _title;
	private int _status;
	public Position() {
		super();
	}
	public int getId() {
		return _id;
	}
	public void setId(int _id) {
		this._id = _id;
	}
	public String getTitle() {
		return _title;
	}
	public void setTitle(String _title) {
		this._title = _title;
	}
	public int getStatus() {
		return _status;
	}
	public void setStatus(int _status) {
		this._status = _status;
	}
}
