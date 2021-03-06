package ru.otus.lesson17hw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.lesson17hw.domain.Author;
import ru.otus.lesson17hw.repository.AuthorReactiveRepository;

@Service
public class AuthorService {

    @Autowired
    private AuthorReactiveRepository authorRepository;
    @Autowired
    private ReactiveMongoOperations mongoOperations;

    private final static String ID = "_id";
    private final static String FIRST_NAME = "firstName";
    private final static String SECOND_NAME = "secondName";

    public Flux<Author> getAuthors(){
        return authorRepository.findAll();
    }

    public Flux<Author> deleteAuthor(String id){
        return  authorRepository.deleteById(id).thenMany(authorRepository.findAll()).cache();
    }

    public Mono<Author> editAuthor(Author author) {
        Query query = new Query();
        query.addCriteria(Criteria.where(ID).is(author.getId()));
        Update update = new Update();
        update.set(FIRST_NAME, author.getFirstName());
        update.set(SECOND_NAME, author.getSecondName());
        return mongoOperations.updateFirst(query, update, Author.class)
                .then(authorRepository.findById(author.getId()))
                .cache();
    }

    public Flux<Author> createNewAuthor(Author author){
        return authorRepository.save(author).thenMany(authorRepository.findAll()).cache();
    }
}
