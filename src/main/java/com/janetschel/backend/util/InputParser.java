package com.janetschel.backend.util;

import org.springframework.stereotype.Component;

@Component
public class InputParser {
    public String parseGraphQLQuery(String query) {

        /* GraphQL query is in following format: {"query":"{\n  allCharacters { \n ... } ...\n}"}
         * Since only queries in form { allCharacters { ... } ... } are allowed these following steps are done to format the query:
         *      - remove first 10 chars (they are always the same): {"query":" gets removed
         *      - remove all \n (replacing \n with nothing)
         *      - remove all \ anywhere als in the query (happens when query searches for an id and passes it with quotation marks)
         *      - remove last 2 chars (they are always "} since the query ends this way): "}' gets removed
         * Now that the query is in the correct format it can be returned and used by the GraphQL API
         */

        return query
                .substring(10)
                .replaceAll("\\\\n", "")
                .replaceAll("\\\\", "")
                .replace("\"}", "");
    }
}
