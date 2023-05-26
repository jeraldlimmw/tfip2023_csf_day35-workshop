package day35workshop.findbook.repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import day35workshop.findbook.models.BookSummary;

@Repository
public class FindBookRepository {
    
    @Autowired
    private MongoTemplate template;

    public List<BookSummary> getBookByTitle(String title) {
        
        Query query = new Query();
		query.addCriteria(Criteria.where("title").regex(title, "i"))
            .with(Sort.by(Direction.ASC, "title"))
            .limit(30);
        query.fields()
            .exclude("_id")
            .include("bookID", "title");
        
        return template.find(query, Document.class,"books")
            .stream()
            .map(d -> new BookSummary(d.getInteger("bookID"), d.getString("title")))
            .toList();
    }
}
