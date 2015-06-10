<h3>what is it?</h3>

Simple universal mocking tool for http requests

- set up expectations using json files in $PROJECT/everest directory
- start using gradlew run
- replay 

<h3>Sample json</h3>
{
"id": "91f83cd9-a0a5-49f5-b740-78ba8f504797",
"name": "wehkamp.nl",
"url": "http://www.wehkamp.nl",
"method": "GET",
"requestHeaders": {
  "Accept": ["application/json"]
  },
"response": "<html>",
"responseStatus": 200
}

- url can be java regex expression

<h3>TODO's</h3>
- implement response headers
- build a proxy that generates json
