package com.tazhub.graphql.springbootgraphql.service;


import com.tazhub.graphql.springbootgraphql.model.Book;
import com.tazhub.graphql.springbootgraphql.repository.BookRepository;
import com.tazhub.graphql.springbootgraphql.service.datafetcher.AllBooksDataFetcher;
import com.tazhub.graphql.springbootgraphql.service.datafetcher.BookDataFetcher;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;

import java.io.IOException;
import java.util.stream.Stream;


@Service
public class GraphQLService {
    @Autowired
    BookRepository bookRepository;

    @Value("classpath:books.graphql")
    Resource resource;
    private GraphQL graphQL;
    @Autowired
    private AllBooksDataFetcher allBooksDataFetcher;
    @Autowired
    private BookDataFetcher bookDataFetcher;

    @PostConstruct
    private void loadSchema(){
        loadDataIntoHSQL();

        File schemaFile = resource.getFile();

        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);
        RuntimeWiring wiring = buildRuntimeWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry);
        graphQL = GraphQL.newGraphQL(schema).build();
    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring -> typeWiring
                          .dataFetcher("allBooks", allBooksDataFetcher)
                          .dataFetcher("book", bookDataFetcher))
                        .build();
    }

    private void loadDataIntoHSQL(){
        Stream.of(
                new Book("123", "Book of CLouds", "kindle", new String[]{
                        "chole Ardiis"
                }, "Nov 2017" ),
                new Book("124", "Java 9", "Orelly", new String[]{
                "Sun"
        }, "Nov 2018"),
                new Book("125", "C#", "Microsoft", new  String[]{
                        "Bill gates"
                }, "Nov 2019")

        ).forEach(bookRepository::save);
    }



    public GraphQL getGraphQL(){
        return graphQL;
    }
}
