var mapStyles = [{"featureType":"road","elementType":"geometry","stylers":[{"lightness":100},{"visibility":"simplified"}]},{"featureType":"water","elementType":"geometry","stylers":[{"visibility":"on"},{"color":"#C6E2FF"}]},{"featureType":"poi","elementType":"geometry.fill","stylers":[{"color":"#C5E3BF"}]},{"featureType":"road","elementType":"geometry.fill","stylers":[{"color":"#D1D1B8"}]}];

$(document).ready(function($) {
    "use strict";

 //  Map in item view --------------------------------------------------------------------------------------------------

    $(".item .mark-circle.map").on("click", function(){
        var _latitude = $(this).closest(".item").attr("data-map-latitude");
        var _longitude = $(this).closest(".item").attr("data-map-longitude");
        var _id =  $(this).closest(".item").attr("data-id");
        $(this).closest(".item").find(".map-wrapper").attr("id", "map"+_id);
        var _this = "map"+_id;
        simpleMap(_latitude,_longitude, _this);
        $(this).closest(".item").addClass("show-map");
        $(this).closest(".item").find(".btn-close").on("click", function(){
            $(this).closest(".item").removeClass("show-map");
        });
    });

});

// Simple map ----------------------------------------------------------------------------------------------------------

function simpleMap(_latitude,_longitude, element, markerDrag){
    if (!markerDrag){
        markerDrag = false;
    }
    var mapCenter = new google.maps.LatLng(_latitude,_longitude);
    var mapOptions = {
        zoom: 9,
        center: mapCenter,
        disableDefaultUI: true,
        scrollwheel: true,
        styles: mapStyles
    };
    var mapElement = document.getElementById(element);
    var map = new google.maps.Map(mapElement, mapOptions);

    var marker = new MarkerWithLabel({
        position: new google.maps.LatLng( _latitude,_longitude ),
        map: map,
        icon: 'assets/img/marker.png',
        labelAnchor: new google.maps.Point(50, 0),
        draggable: markerDrag
    });

    google.maps.event.addListener(marker, "mouseup", function (event) {
        var latitude = this.position.lat();
        var longitude = this.position.lng();
        $('#latitude').val( this.position.lat() );
        $('#longitude').val( this.position.lng() );
    });

    autoComplete(map, marker);
    weather(_latitude, _longitude);
}

// Weather -------------------------------------------------------------------------------------------------------------

function weather(_latitude, _longitude){

    if( $(".weather").length ){

        var geocoder;
        var latlng = new google.maps.LatLng(_latitude, _longitude);
        var city, country, street;
        geocoder = new google.maps.Geocoder();

        geocoder.geocode(
            {'latLng': latlng},
            function(results, status) {
                if (status == google.maps.GeocoderStatus.OK) {
                    if (results[0]) {
                        var add= results[0].formatted_address ;
                        var  value=add.split(",");
                        count = value.length;
                        country = value[count-1];
                        city = value[count-2].replace(/\d+/g, '');
                        street = value[count-3];

                        $.simpleWeather({
                            location: city + ", " + country,
                            woeid: '',
                            unit: 'c',
                            success: function(weather) {
                                var html = '<div class="left"><i class="icon-'+weather.code+'"></i><span>'+weather.temp+'&deg;'+weather.units.temp+'</span>' +
                                    '</div><div class="right"><ul><li>'+weather.city+', '+weather.region+'</li><li class="currently">'+weather.currently+'</li></ul></div>';
                                $(".weather-detail").html(html);
                            },
                            error: function(error) {
                                $(".weather-detail").html('<p>'+error+'</p>');
                            }
                        });
                    }
                    else  {
                        console.log("address not found");
                    }
                }
                else {
                    console.log("Geocoder failed due to: " + status);
                }
            }
        );
    }
}

//Autocomplete ---------------------------------------------------------------------------------------------------------

function autoComplete(map, marker){
    if( $("#address-autocomplete").length ){
        var input = /** @type {HTMLInputElement} */( document.getElementById('address-autocomplete') );
        var autocomplete = new google.maps.places.Autocomplete(input);
        autocomplete.bindTo('bounds', map);
        google.maps.event.addListener(autocomplete, 'place_changed', function() {
            var place = autocomplete.getPlace();
            if (!place.geometry) {
                return;
            }
            if (place.geometry.viewport) {
                map.fitBounds(place.geometry.viewport);
            } else {
                map.setCenter(place.geometry.location);
                map.setZoom(17);
            }
            if( marker ){
                marker.setPosition(place.geometry.location);
                marker.setVisible(true);
                $('#latitude').val( marker.getPosition().lat() );
                $('#longitude').val( marker.getPosition().lng() );
            }
            var address = '';
            if (place.address_components) {
                address = [
                    (place.address_components[0] && place.address_components[0].short_name || ''),
                    (place.address_components[1] && place.address_components[1].short_name || ''),
                    (place.address_components[2] && place.address_components[2].short_name || '')
                ].join(' ');
            }
        });

        function success(position) {
            map.setCenter(new google.maps.LatLng(position.coords.latitude, position.coords.longitude));
            //initSubmitMap(position.coords.latitude, position.coords.longitude);
            $('#latitude').val( position.coords.latitude );
            $('#longitude').val( position.coords.longitude );
        }

        $('.geo-location').on("click", function() {
            if (navigator.geolocation) {
                $('#'+element).addClass('fade-map');
                navigator.geolocation.getCurrentPosition(success);
            } else {
                console.log('Geo Location is not supported');
            }
        });
    }
}

// Big Map on Home -----------------------------------------------------------------------------------------------------

function bigMap( element,useAjax){
    if (document.getElementById(element) != null) {
        // Thay đổi nội dung hiển thị
        var bannerContent = document.createElement('div');
        bannerContent.innerHTML = '<img style="height: 660px; width: 1519px" src="/assets/img/banner.jpg" alt="Banner Image">';

        // Tìm phần tử HTML với id "map-item" và thay thế nó bằng hình ảnh banner
        var mapItemElement = document.getElementById(element);
        mapItemElement.innerHTML = '';
        mapItemElement.appendChild(bannerContent);
    }
}
