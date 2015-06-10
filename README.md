<h3>what is it?</h3>

Simple universal mocking tool for http requests
- requires java 8
- set up expectations using json files in $PROJECT/everest_data directory
-- or start java with -Deverest.data=[..] for a different data directory
- start using gradlew run
- replay 

<h3>Sample json</h3>
{<br/>
"id": "91f83cd9-a0a5-49f5-b740-78ba8f504797",<br/>
"name": "wehkamp.nl",<br/>
"url": "http://www.wehkamp.nl",<br/>
"method": "GET",<br/>
"requestHeaders": {<br/>
&nbsp;&nbsp;"Accept": ["application/json"]<br/>
&nbsp;&nbsp;},<br/>
"response": "<html>",<br/>
"responseStatus": 200<br/>
}<br/>

- url can be java regex expression

<h3>TODO's</h3>
- implement response headers
- build a proxy that generates json
