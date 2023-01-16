/**
 * 
 */

 
 $(document).ready(function(){
				
	$('.user1.list1').click(function(){
		$.ajax({
			url: '/Ch09/user1',
			method: 'GET',
			dateType: 'json',
			success: function(data){
				console.log(data);
			}
		});
	})
	
	$('.user1.list2').click(function(){
		let uid = 'A101';
		
		$.ajax({
			url: '/Ch09/user1/' + uid,
			method: 'GET',
			dateType: 'json',
			success: function(data){
				console.log(data);
			}
		});
	})
	
	$('.user1.register').click(function(){
		
		let jsonData = {
				"uid":"s101",
				"name":"김갑수",
				"hp":"010-1212-3232",
				"age":"34"
		}
		
		$.ajax({
			url: '/Ch09/user1',
			method: 'POST',
			dateType: 'json',
			data: jsonData,
			success: function(data){
				console.log(data);
			}
		});
	})
	
	$('.user1.modify').click(function(){
		
		let jsonData = {
				"uid":"s101",
				"name":"김갑수",
				"hp":"010-1212-3232",
				"age": 90
		}
		
		$.ajax({
			url: '/Ch09/user1',
			method: 'PUT',
			dateType: 'json',
			data: jsonData,
			success: function(data){
				console.log(data);
			}
		});
	})
	
	$('.user1.delete').click(function(){
		
		let uid = 's101';
		
		$.ajax({
			url: '/Ch09/user1/' + uid,
			method: 'DELETE',
			dateType: 'json',
			data: uid,
			success: function(data){
				console.log(data);
			}
		});
	})
})