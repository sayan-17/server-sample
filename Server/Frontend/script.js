function post(){

  var xhttp = new XMLHttpRequest();
  var language = document.getElementById("language-post").value;
  var year = document.getElementById("year-post").value;
  xhttp.open("POST", "http://127.0.0.1:8080");
  xhttp.send(language + "," + year);

  xhttp.onreadystatechange = function() {
    if(this.readyState == 4 && this.status == 200){
      document.getElementById("text-post").innerHTML = "Thanl You For Entering the Record";
      console.log("Recieved");
    }else{
      document.getElementById("text-post").innerHTML = "Some error occured";
      console.log("Not Recieved");
    }
  }
}

function get() {
  var xhttp = new XMLHttpRequest();
  var language = document.getElementById("language-get").value;
  xhttp.open("GET", "http://127.0.0.1:8080/find?language=" + language);
  xhttp.send();

  xhttp.onreadystatechange = function() {
    if(this.readyState == 4 && this.status == 200){
      document.getElementById("text-get").innerHTML = language + " was released on " + this.responseText;
      console.log("Recieved");
    }else{
      document.getElementById("text-get").innerHTML = "Some error occured";
      console.log("Not Recieved");
    }
  }
}
