/**
 *  AJAX API
 */

// AJAX API
function ajaxPost(url, jsonData) {
	return new Promise(function(resolve, rejext) {
		const xhr = new XMLHttpRequest();

		xhr.onload = function() {
			if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
				resolve(xhr.response);
			} else {
				reject({ status: xhr.status, statusText: xhr.statusText });
			}
		}

		xhr.open("POST", contextPath + url);
		xhr.setRequestHeader("Content-Type", "application/json");
		//	xhr.setRequestHeader("uid", "uid"); GET 방식 
		xhr.responseType = "json";
		xhr.send(JSON.stringify(jsonData)); //post body json 방식 일때
	});
};