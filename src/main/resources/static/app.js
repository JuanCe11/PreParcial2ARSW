var app = (function () {

    function find(nombre) {
        initMap();
        getAirportsByName(nombre,_table);
    }

    function getAirportsByName(name,callback) {
        console.log(name);
        var getPromise = $.get("http://localhost:8080/airports/"+name);
        getPromise.then(
            function(data){
                console.log(JSON.parse(data));
                _table(JSON.parse(data));
            },
            function(){
                console.log('error')
            }
        );
        return getPromise;
    }

    function _table(data){
        $("#filasAeropuerto").empty();
        data.map(function(element){
            var markup = "<tr> <td>"+ element.code +"</td> <td>"+element.name+"</td> <td>"+ element.city +"</td> <td>"+ element.countryCode +"</td> </tr>";
            $("#filasAeropuerto").append(markup)
        });

        plotMarkers(data);
    }

    return {
        init: function () {
            initMap();
        },
        find:find
    }
})();