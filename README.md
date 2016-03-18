# blog-with-in-place-comments
##Problem statement: 
    
    An admin should be able to add blog posts. Blog posts have a unique random identifier, a title and plain text where paragraphs are separated by two new-line characters.

    A viewer should be able to view all blog posts (list-mode) starting with first 5 and then the next 5 and so on. This view will not have any comments.

    A viewer should be able to click on one of these blogs to view it in full-mode. In full-mode, all past comments on the text are visible next to the text. Also, the viewer is able to comment on a paragraph of text. In essence, the comment is on a paragraph.

Ehcache has been used as an intermediary between database and dao layers.
I assume that the JSON input to the API will have \n\n as a double newline as a bifurcation between two paragraphs.
Let's say you deployed the application on localhost on port 8080.

##APIs

### Add a blog post

URL: 

      http://localhost:8080/blog-with-comments/blog/add

HTTP METHOD: POST

Sample Request body:
{"title":"Intro",
"plainText":"This is Zafar.\n\nLots of people have done this."}

Sample Response:
{
  "status": "OK",
  "data": {
    "id": "86067141",
    "title": "Intro",
    "plainText": "This is Zafar.\n\nLots of people have done this."
  }
}

### View all blog posts in paginated view of 5 blogs per API call (this limit is configurable in the property file app.properties)

The first page will be returned if called without any page number in the URL.

URL: 

    http://localhost:8080/blog-with-comments/blog/all
    http://localhost:8080/blog-with-comments/blog/all/2

HTTP METHOD: GET

Sample Response:
{
  "status": "OK",
  "data": {
    "currentPage": 1,
    "totalPages": 2,
    "blogs": [
      {
        "id": "48807131",
        "title": "second",
        "plainText": "this is akshat. secondly"
      },
      {
        "id": "66022187",
        "title": "first",
        "plainText": "this is zafar\n\nsecondly"
      },
      {
        "id": "71254775",
        "title": "third",
        "plainText": "this is rahul. secondly"
      },
      {
        "id": "86067141",
        "title": "Intro",
        "plainText": "This is Zafar.\n\nLots of people have done this."
      },
      {
        "id": "87394575",
        "title": "hello",
        "plainText": "this is zafar"
      }
    ]
  }
}

### View detailed view of a blog post (with comments).

The URL expects a blog id.

URL: 

      http://localhost:8080/blog-with-comments/blog/87394575

HTTP METHOD: GET

Sample Response:
{
  "status": "OK",
  "data": {
    "id": "87394575",
    "title": "hello",
    "plainText": "this is zafar",
    "paragraphs": [
      {
        "paragraphId": "0",
        "paragraph": "this is zafar",
        "comments": []
      }
    ]
  }
}

### Add a comment to a paragraph in a blog.

The URL expects a blog id and a paragraph id. This paragraph id is returned by API #3. This blog Id is returned by API #1 and #2.

URL:

    http://localhost:8080/blog-with-comments/blog/87394575/paragraph/0

HTTP METHOD: POST

Sample Request:
{
"comment":"this is a comment."
}

Sample Response:
{
  "status": "OK",
  "data": "this is a comment."
}


