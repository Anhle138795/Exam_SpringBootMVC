package shop.demo.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import shop.demo.models.Type;
import shop.demo.modelviews.PageView;
import shop.demo.modelviews.Type_mapper;

@Repository
public class TypeRepository {
    @Autowired
    JdbcTemplate db;

    public List<Type> findAll() {
        String sql = "SELECT _id, ISNULL(_title, '') AS _title FROM tbl_type";
        return db.query(sql, new Type_mapper());
    }
    
    public List<Type> findAllPaging(PageView pageItem, String searchQuery) {
        String searchSql = "";
        if (searchQuery != null && !searchQuery.isEmpty()) {
            searchSql = "WHERE _title LIKE '%" + searchQuery + "%' ";
        }
        int count = db.queryForObject("SELECT count(*) FROM tbl_type " + searchSql, Integer.class);
        int total_page = count / pageItem.getPage_size();
        pageItem.setTotal_page(total_page);
        String sql = "SELECT _id, ISNULL(_title, '') AS _title FROM tbl_type " +
                searchSql + "ORDER BY _id DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        return db.query(sql, new Type_mapper(), (pageItem.getPage_current() - 1) * pageItem.getPage_size(), pageItem.getPage_size());
    }

    public Type findById(int id) {
        return db.queryForObject("SELECT * FROM tbl_type WHERE _id = ?", new Type_mapper(), id);
    }

    public String newType(Type newItem) {
        try {
            int rowaccept = db.update("INSERT INTO tbl_type (_title) VALUES (?)",
                    newItem.getTitle());
            return rowaccept == 1 ? "success" : "failed";
        } catch (Exception e) {
            System.err.println("Error inserting new type: " + e.getMessage());
            return "insert exception data: " + e.getMessage();
        }
    }

    public String update(Type item) {
        try {
            int rowaccept = db.update("UPDATE tbl_type SET _title = ? WHERE _id = ?",
                    item.getTitle(), item.getId());
            return rowaccept == 1 ? "success" : "failed";
        } catch (Exception e) {
            System.err.println("Error updating type: " + e.getMessage());
            return "update exception data: " + e.getMessage();
        }
    }

    public String deleteType(int id) {
        try {
            int rowaccept = db.update("DELETE FROM tbl_type WHERE _id = ?", id);
            return rowaccept == 1 ? "success" : "failed";
        } catch (Exception e) {
            System.err.println("Error deleting type: " + e.getMessage());
            return "delete exception data: " + e.getMessage();
        }
    }
}
