# spongebob-characters-backend
This repository serves as a proof of concept for `GraphQL`. It also serves a little bit like a personal wiki to keep track of this. 
It was randomly chosen that Spongebob characters should serve as placeholders here.

## Table of contents
[How it works](#howitworks)  
.....[How the data is structured](#structure)  
[Example requests and responses](#example)  
.....[Known queries](#knownqueries)  
.....[Valid requests](#valid)  
..........[First request](#first)  
..........[Same endpoint, different data - Second request](#second)  
..........[Search via ID](#idsearch)  
..........[Concatenation of queries](#concat)  
.....[Invalid requests](#invalid)  

<a name="howitworks"/>

## How it works

This API only serves one endpoint: `POST` request to `/api/characters` with  
- `200 OK` on success with respective data or 
- `500 INTERNAL SERVER ERROR` when provided with falsy input

The user has to provide an GraphQL body (plain text body if there is no support for GraphQL bodies) to the `POST` request.  
The response will contain all the fields the body specified.
<br />

<a name="structure"/>

The characters are stored in the database structured like this:
```json
{
  "id": "5f2aa6632332d8b438db53a5",
  "firstName": "Spongebob",
  "lastName": "Squarepants",
  "voicedBy": "Tom Kenny",
  "role": "Main character",
  "firstAppearance": "Season 1, Episode 1"
}
```

**Note** that `id` is of type `String`
<br />

<a name="example">
  
## Example requests and responses

<a name="knownqueries">
  
The API knows following queries:
- `allCharacters`
- `character(id: String)`  

This implies that all characters can be returned (with specified fields) or only one character if searched by id

<a name="valid">
  
### Valid requests

<a name="first">
  
**Request**:  
`POST` to `/api/characters` with body:
```graphql
{
  allCharacters {
    firstName
    lastName
  }
}
```

**Response**:  
`200 OK` with body
```json
{
  "allCharacters": [
    {
      "firstName": "Spongebob",
      "lastName": "Squarepants"
    },
    {
      "firstName": "Patrick",
      "lastName": "Star"
    }
  ]
}
```
<br />

<a name="second">

The interesting part about GraphQL is that a second request with a different body to **the same endpoint** of `/api/characters` will return different data.


**Request**:  
`POST` to `/api/characters` with body:
```graphql
{
  allCharacters {
    id
    firstName
    role
  }
}
```

**Response**:  
`200 OK` with body
```json
{
  "allCharacters": [
    {
      "id": "5f2aa6632332d8b438db53a5",
      "firstName": "Spongebob",
      "role": "Main character"
    },
    {
      "id": "5f2aa6cd2332d8b438db53a6",
      "firstName": "Patrick",
      "role": "Side character"
    }
  ]
}
```

<br />

<a name="idsearch">

Lets search for a single character with the id of Spongebob. The request will look like this:


**Request**:  
`POST` to `/api/characters` with body:
```graphql
{
  character(id: "5f2aa6632332d8b438db53a5") {
    firstName
    lastName
    firstAppearance
  }
}
```

**Response**:  
`200 OK` with body
```json
{
  "character": {
    "firstName": "Spongebob",
    "lastName": "Squarepants",
    "firstAppearance": "Season 1 Episode 1"
  }
}
```

<a name="concat">

<br />

These queries can also be concatenated.


**Request**:  
`POST` to `/api/characters` with body:
```graphql
{
  allCharacters {
    id
    firstName
  }
  
  character(id: "5f2aa6632332d8b438db53a5") {
    firstName
    lastName
    firstAppearance
  }
}
```

**Response**:  
`200 OK` with body
```json
{
  "allCharacters": [
    {
      "id": "5f2aa6632332d8b438db53a5",
      "firstName": "Spongebob",
    },
    {
      "id": "5f2aa6cd2332d8b438db53a6",
      "firstName": "Patrick",
    }
  ],
  
  "character": {
    "firstName": "Spongebob",
    "lastName": "Squarepants",
    "firstAppearance": "Season 1 Episode 1"
  }
}
```

<a name="invalid">

### Invalid Requests
A `POST` request to `/api/characters` with a body that contains a filed which is not present in the database (henceforth cannot be mapped to the entity) will result in an `500 INTERNAL SERVER ERROR`

```graphql
{
  allCharacters {
    id
    firstName
    fieldThatDoesNotExist # this will trigger the 500 response
  }
}
```
