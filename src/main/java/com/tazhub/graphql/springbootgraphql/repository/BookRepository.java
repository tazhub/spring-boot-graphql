package com.tazhub.graphql.springbootgraphql.repository;

import com.tazhub.graphql.springbootgraphql.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {
}
