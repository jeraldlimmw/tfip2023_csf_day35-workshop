package day35workshop.findbook.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import day35workshop.findbook.repositories.FindBookRepository;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

@RestController
@RequestMapping(path="/api/book", produces=MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins="http://localhost:4200")
public class FindBookController {

    @Autowired
    private FindBookRepository repo;

    @GetMapping(path="/search")
    @ResponseBody
    public ResponseEntity<String> getBooksByTitle(@RequestParam String query) {

        List<JsonObject> books = repo.getBookByTitle(query)
            .stream()
            .map(bk ->
                Json.createObjectBuilder()
                    .add("bookID", bk.bookID())
                    .add("title", bk.title())
                    .build()
            )
            .toList();

        JsonArray resp = Json.createArrayBuilder(books).build();
        
        return ResponseEntity.ok(resp.toString());
    }
    
}
