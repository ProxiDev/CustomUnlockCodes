# CustomUnlockCodes
Generate corresponding code in JavaScript:

```  
<script src="http://cdn.rawgit.com/h2non/jsHashes/master/hashes.js"></script>
<script>
  var sha1;
  
  function httpGet(theUrl)
{
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", theUrl, false ); // false for synchronous request
    xmlHttp.send(  );
    return xmlHttp.responseText;
}
  
  var xhr = new XMLHttpRequest();
xhr.open('GET', "https://ipinfo.io/json", true);
xhr.send();
 
xhr.onreadystatechange = processRequest;
  function processRequest(e) {
    if (xhr.readyState == 4 && xhr.status == 200) {
        var response = JSON.parse(xhr.responseText);
        console.log(new Hashes.SHA1().hex(response.hostname).substring(0,5));
    }
}



  

</script>
```
