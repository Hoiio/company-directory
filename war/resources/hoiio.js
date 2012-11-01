/* -------------------------------------------------------
 * hoiio.js v1.0
 * author: Ruprecht Robertson
 * ------------------------------------------------------- */

var Hoiio = function() { 
	//private variables and functionalities 
	var getExtensionsUrl= '/ui/get',
		setExtensionsUrl= '/ui/update',
		templateDigitHolder = '{digitValue}',
		tempalteForwardHolder = '{forwardValue}'
		template= '<span class="ext"><br/>'+
						'<input type="text" style="width:50px" placeholder="Digit" class="numbersOnly" value="'+templateDigitHolder+'">&nbsp;'+
						'<input type="text" placeholder="Forwards to" class="forwardTo" value="'+tempalteForwardHolder+'"></span>';

	//public variables and functionalities 
	return{
		setNumberOnlyFields: function(){
			$('.numbersOnly').keyup(function () { 
			    this.value = this.value.replace(/[^0-9\.]/g,'');
			});
		},

		fetchAndPopulateExtensions: function(totalItems){
			$('#status').html('fetching data...').fadeIn('fast');
			$.ajax({
			  	type: "GET",
			  	url: getExtensionsUrl
			}).done(function( msg ) {
			  	//alert( "Data Fetched: " + JSON.stringify(msg) );
			  	if(typeof msg != 'undefined'){
			  		var HTML = '';
			  		var count = msg.length;
					for(var i = 0; i < count; i++){
						HTML = HTML.concat(template.replace(templateDigitHolder,msg[i].key).replace(tempalteForwardHolder,msg[i].forwardTo));
					}
					totalItems = totalItems - count;
			  	}
			  	for(var i = 0; i < totalItems; i++){
			  		HTML = HTML.concat(template.replace(templateDigitHolder,'').replace(tempalteForwardHolder,''));
			  	}
			  	$('#extensions').html(HTML);
			  	$('#status').fadeOut('fast');
			  	
			});
		},

		saveAndUpdateExtensions: function(){
			$('#status').html('saving in progress...').fadeIn('fast');
			var extensionObject = [];
			$('.ext').each(function(){
			    var valueExt = $(this).find('.numbersOnly').val();
			    var valueForward = $(this).find('.forwardTo').val();
			    if(valueExt != '' || valueForward != ''){
			    	extensionObject.push({key:valueExt,forwardTo:valueForward});
				}
			});

			$.ajax({
			  	type: "POST",
			  	url: setExtensionsUrl,
			  	data: { extensions: JSON.stringify(extensionObject)}
			}).done(function( msg ) {
			  	//alert( "Data Saved: " + JSON.stringify(msg) );
			  	$('#status').fadeOut('fast');
			});
		}
	}
}();
