if (window.console) {
  console.log("Welcome to your Play application's JavaScript!");
}

function disable(page,total){
	
	if(page<1){
		$('#prev').attr('disabled','disabled');
		
	}
	else{
		$('#prev').removeAttr('disabled');
	}
	if((page+1)*5>=total){
		$('#next').attr('disabled','disabled');
	}
	else{
		$('#next').removeAttr('disabled');
	}
}

function search(){
	var search = $('#searchBox').val();
	window.location.href = "/show?search="+search;
	
}
/**
 * 
 * Map canvas starts here
 */
function initialize() {
	var myLatlng = new google.maps.LatLng(28.645,77.17685);
    var mapCanvas = document.getElementById('map-canvas');
    var mapOptions = {
      center: myLatlng,
      zoom: 14,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    }
    var map = new google.maps.Map(mapCanvas, mapOptions);

    var marker = new google.maps.Marker({
        position: myLatlng,
        map: map,
        title: 'Knoldus Software LLP'
    });
  }
