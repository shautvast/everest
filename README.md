<h3>what is it?</h3>

Simple universal mocking tool for http requests
- requires java 8
- set up expectations using json files in $PROJECT/everest_data directory
- or start java with -Deverest.data=[..] for a different data directory
- start using gradlew run
- replay 

<h3>Sample json</h3>
{<br/>
"name": "wehkamp.nl",<br/>
"url": "http://www.wehkamp.nl",<br/>
"method": "GET",<br/>
"requestHeaders": {<br/>
&nbsp;&nbsp;"Accept": ["application/json"]<br/>
&nbsp;&nbsp;},<br/>
"response": "<html>",<br/>
"responseStatus": 200<br/>
"responseHeaders": <br/>
&nbsp;&nbsp;"Accept": ["application/json"]<br/>
&nbsp;&nbsp;},<br/>
}<br/>

- url can be java regex expression

<h4>api for json upload</h4>
- the JSON above can be uploaded to a running server using a POST to http://[server]:[port]/__api/upload
- use Content-Type=application/json  

<h3>TODO's</h3>
- reload json files on the fly
- build a proxy that generates the json from actual requests/responses
