package shop.demo.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ch.qos.logback.core.util.FileUtil;
import shop.demo.models.Category;
import shop.demo.modelviews.Category_mapper;
import shop.demo.utils.FileUtility;
import shop.demo.utils.Views;

@Repository
public class CategoryRepository {
    @Autowired
    JdbcTemplate db; // initialize object

    public List<Category> findAll() {
        String str_query = String.format("select c.*,t.%s from %s c, %s t where c.%s=t.%s", 
            Views.COL_TYPE_TITLE, 
            Views.TBL_CATEGORY, 
            Views.TBL_TYPE, 
            Views.COL_CATEGORY_ID_TYPE, 
            Views.COL_TYPE_ID);
        return db.query(str_query, new Category_mapper());
    }
    
    public Category findById(int id) {
    	String str_query = String.format("select * from %s where %s=?", Views.TBL_CATEGORY, Views.COL_CATEGORY_ID);
    	return db.queryForObject(str_query, new Category_mapper(), new Object[] {id});
    }

    public String newCategory(Category newItem) {
        try {
            String str_query = String.format("insert into %s values(?,?,?,?,?)", Views.TBL_CATEGORY);
            int rowaccept = db.update(str_query, new Object[] { 
                newItem.getTitle(), 
                newItem.getImage(), 
                newItem.getNo(), 
                newItem.getIdType(), 
                newItem.getStatus() 
            });
            if (rowaccept == 1) {
                return "success";
            }
            return "failed";
        } catch (Exception e) {
            // TODO: handle exception
        }
        return "insert exception data";
    }
    
    public String updateCategory(Category updatedItem) {
        try {
            String str_query = String.format("update %s set %s=?, %s=?, %s=?, %s=?, %s=? where %s=?", 
                Views.TBL_CATEGORY, 
                Views.COL_CATEGORY_TITLE, 
                Views.COL_CATEGORY_IMAGE, 
                Views.COL_CATEGORY_NO, 
                Views.COL_CATEGORY_ID_TYPE, 
                Views.COL_CATEGORY_STATUS,
                Views.COL_CATEGORY_ID);
            int rowaccept = db.update(str_query, new Object[] { 
                updatedItem.getTitle(), 
                updatedItem.getImage(), 
                updatedItem.getNo(), 
                updatedItem.getIdType(), 
                updatedItem.getStatus(),
                updatedItem.getId()
            });
            if (rowaccept == 1) {
                return "success";
            }
            return "failed";
        } catch (Exception e) {
            // TODO: handle exception
        }
        return "update exception data";
    }


    
    public String deleteCategory(int id) {
    	String str_query = String.format("delete from %s where %s = ?", Views.TBL_CATEGORY, Views.COL_CATEGORY_ID);
    	Category cateItemDel = findById(id);
    	try {
    		int rowaccept = db.update(str_query, new Object[] {id});
    		if(rowaccept == 1) {
    			FileUtility.deleteFile("uploads", cateItemDel.getImage());
    			return "success";
    		}
    		return "failed";
    	}
    	catch(Exception e) {
    		
    	}
    	return "detele exception data";
    }
}


