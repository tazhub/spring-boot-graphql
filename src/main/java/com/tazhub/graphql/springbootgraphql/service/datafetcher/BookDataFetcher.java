package com.tazhub.graphql.springbootgraphql.service.datafetcher;

import com.tazhub.graphql.springbootgraphql.model.Book;
import com.tazhub.graphql.springbootgraphql.repository.BookRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookDataFetcher implements DataFetcher<Book> {
    @Autowired
    BookRepository bookRepository;
    @Override
    public Book get(DataFetchingEnvironment dataFetchingEnvironment){
        String isn = dataFetchingEnvironment.getArgument("id");
        return bookRepository.findOne(isn);
    }
}
