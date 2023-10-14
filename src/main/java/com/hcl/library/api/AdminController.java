package com.hcl.library.api;


import com.hcl.library.entity.Book;
import com.hcl.library.entity.Category;
import com.hcl.library.entity.Token;
import com.hcl.library.entity.User;
import com.hcl.library.model.BookInput;
import com.hcl.library.repository.BookRepository;
import com.hcl.library.repository.CategoryRepository;
import com.hcl.library.repository.TokenRepository;
import com.hcl.library.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/admin/add/book")
    public ResponseEntity<Object> addBook(@RequestBody BookInput bookInput , HttpSession session) {

        Object session_obj = session.getAttribute("session-id");
        if(session_obj==null || this.tokenRepository.findByToken(session_obj.toString())==null) {

            return new ResponseEntity<>("Not authorized" , HttpStatusCode.valueOf(401));
        }

        Token token = this.tokenRepository.findByToken(session_obj.toString());

        String username = token.getUsername();

        User user = this.userRepository.findByUsername(username);

        if(user.getRole().getRoleName().equals("Admin")) {

            return new ResponseEntity<>("Not authorized" , HttpStatusCode.valueOf(401));
        }

        Category category_obj = this.categoryRepository.findByName(bookInput.getCategory());

        if(category_obj==null || bookInput.getAuthor().isEmpty() || bookInput.getTitle().isEmpty()) {

            return new ResponseEntity<>("Invalid Category !" , HttpStatusCode.valueOf(400));
        }

        Book book = new Book(null , bookInput.getTitle(), bookInput.getAuthor(), category_obj);

        this.bookRepository.save(book);

        return new ResponseEntity<>("Book was added successfully !" , HttpStatusCode.valueOf(200));
    }

    
}
