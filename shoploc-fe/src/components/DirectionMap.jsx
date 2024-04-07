import React, { useEffect, useState } from "react";
import {
    APIProvider,
    Map,
    useMapsLibrary,
    useMap,
} from "@vis.gl/react-google-maps";

const API_KEY = "AIzaSyAJfrIKx0yKqwq5rmUqCuMDn4pbuYcJiKo";

const DirectionMap = ({ origin, destination }) => (
    <APIProvider apiKey={API_KEY}>
        <Map
            defaultCenter={{ lat: 50.62925, lng: 3.057256 }}
            defaultZoom={9}
            gestureHandling={"greedy"}
            fullscreenControl={false}
        >
            <Directions origin={origin} destination={destination} />
        </Map>
    </APIProvider>
);

const Directions =({ origin, destination })=> {
    const map = useMap();
    const routesLibrary = useMapsLibrary("routes");
    const [directionsService, setDirectionsService] = useState();
    const [directionsRenderer, setDirectionsRenderer] = useState();
    const [routes, setRoutes] = useState([]);
    const [routeIndex, setRouteIndex] = useState(0);
    const selected = routes[routeIndex];
    const leg = selected?.legs[0];
 console.log(selected)
    // Initialize directions service and renderer
    useEffect(() => {
        if (!routesLibrary || !map) return;
        setDirectionsService(new routesLibrary.DirectionsService());
        setDirectionsRenderer(new routesLibrary.DirectionsRenderer({ map }));
    }, [routesLibrary, map]);

    // Use directions service
    useEffect(() => {
        if (!directionsService || !directionsRenderer) return;

        const request = {
            // origin: { lat: 50.62925, lng: 3.057256 },
            origin: origin,
            destination: destination,
            travelMode: window.google.maps.TravelMode.DRIVING,
            provideRouteAlternatives: true,
        };

        directionsService.route(request).then(response => {
        directionsRenderer.setDirections(response);
        setRoutes(response.routes);
      });

        return () => directionsRenderer.setMap(null);
    }, [directionsService, directionsRenderer,origin,destination]);

    // Update direction route
    useEffect(() => {
        if (!directionsRenderer) return;
        directionsRenderer.setRouteIndex(routeIndex);
    }, [routeIndex, directionsRenderer]);

    if (!leg) return null;

    return (
        <div className="absolute w-86 top-0 mt-1 right-0 px-4  py-1 text-white bg-gray-700 rounded">
            <h2 className="text-2xl font-bold">{selected.summary}</h2>
            <p>
                {leg.start_address.split(",")[0]} to{" "}
                {leg.end_address.split(",")[0]}
            </p>
            <p><b>Distance</b>: {leg.distance?.text}</p>
            <p><b>Durée</b> : {leg.duration?.text}</p>

            <h2 className="text-2xl font-bold">Autres itinéraires</h2>
            <ul className="flex items-center gap-4 text-yellow-400">
                {routes.map((route, index) => (
                    <li key={route.summary}>
                        <button onClick={() => setRouteIndex(index)}>
                            {route.summary}
                        </button>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default DirectionMap;
