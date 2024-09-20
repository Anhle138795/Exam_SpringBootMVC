package shop.demo.models;

public class Category {
    private int _id;
    private String _title;
    private String _image;
    private int _no;
    private int _id_type;
    private int _status;
    private String _type_title;

    public Category() {
        super();
        this._id = 0;
        this._title = "";
        this._image = "";
        this._no = 0;
        this._id_type = 0;
        this._status = 1;
    }

    public Category(int new_id, String new_title, String new_image, int new_no, int new_id_type, int new_status) {
        this._id = new_id;
        this._title = new_title;
        this._image = new_image;
        this._no = new_no;
        this._id_type = new_id_type;
        this._status = new_status;
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

    public String getImage() {
        return _image;
    }

    public void setImage(String _image) {
        this._image = _image;
    }

    public int getNo() {
        return _no;
    }

    public void setNo(int _no) {
        this._no = _no;
    }

    public int getIdType() {
        return _id_type;
    }

    public void setIdType(int _id_type) {
        this._id_type = _id_type;
    }

    public int getStatus() {
        return _status;
    }

    public void setStatus(int _status) {
        this._status = _status;
    }

    public String getTypeTitle() {
        return _type_title;
    }

    public void setTypeTitle(String _type_title) {
        this._type_title = _type_title;
    }
    
}
