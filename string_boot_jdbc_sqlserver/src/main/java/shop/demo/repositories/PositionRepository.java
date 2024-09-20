package shop.demo.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import shop.demo.models.Position;
import shop.demo.modelviews.PageView;
import shop.demo.modelviews.Position_mapper;

@Repository
public class PositionRepository {
    @Autowired
    JdbcTemplate db;

    public List<Position> findAllPaging(PageView pageItem, String searchQuery) {
        String searchSql = "";
        if (searchQuery != null && !searchQuery.isEmpty()) {
            searchSql = "WHERE _title LIKE '%" + searchQuery + "%' ";
        }
        int count = db.queryForObject("SELECT count(*) FROM tbl_position " + searchSql, Integer.class);
        int total_page = count / pageItem.getPage_size();
        pageItem.setTotal_page(total_page);
        String sql = "SELECT _id, ISNULL(_title, '') AS _title, ISNULL(_status, 0) AS _status FROM tbl_position " +
                searchSql + "ORDER BY _id DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        return db.query(sql, new Position_mapper(), (pageItem.getPage_current() - 1) * pageItem.getPage_size(), pageItem.getPage_size());
    }

    public Position findById(int id) {
        return db.queryForObject("SELECT * FROM tbl_position WHERE _id = ?", new Position_mapper(), id);
    }

    public String newPosition(Position newItem) {
        try {
            int rowaccept = db.update("INSERT INTO tbl_position (_title, _status) VALUES (?, ?)",
                    newItem.getTitle(), newItem.getStatus());
            return rowaccept == 1 ? "success" : "failed";
        } catch (Exception e) {
            System.err.println("Error inserting new position: " + e.getMessage());
            return "insert exception data: " + e.getMessage();
        }
    }

    public String update(Position item) {
        try {
            int rowaccept = db.update("UPDATE tbl_position SET _title = ?, _status = ? WHERE _id = ?",
                    item.getTitle(), item.getStatus(), item.getId());
            return rowaccept == 1 ? "success" : "failed";
        } catch (Exception e) {
            System.err.println("Error updating position: " + e.getMessage());
            return "update exception data: " + e.getMessage();
        }
    }

    public String deletePosition(int id) {
        try {
            int rowaccept = db.update("DELETE FROM tbl_position WHERE _id = ?", id);
            return rowaccept == 1 ? "success" : "failed";
        } catch (Exception e) {
            System.err.println("Error deleting position: " + e.getMessage());
            return "delete exception data: " + e.getMessage();
        }
    }

    public String updateStatus(int id, int status) {
        try {
            int rowaccept = db.update("UPDATE tbl_position SET _status = ? WHERE _id = ?",
                    status, id);
            return rowaccept == 1 ? "success" : "failed";
        } catch (Exception e) {
            System.err.println("Error updating status: " + e.getMessage());
            return "update exception data: " + e.getMessage();
        }
    }
}
