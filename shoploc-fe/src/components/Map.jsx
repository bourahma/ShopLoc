import React, { useEffect, useState } from "react";
import { MapContainer, Marker, Popup, TileLayer } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import L from "leaflet";
import customMarkerSvg from "../images/marker.svg";
import customUserPositionSvg from "../images/user_marker.svg";

const merchants = [
  { id: 1, name: "Auchan", coordinates: [50.62925, 3.057256] },
  { id: 2, name: "Le Roy Merlin", coordinates: [50.631008, 3.058092] },
  { id: 3, name: "Planet Bain", coordinates: [50.625018, 3.065499] },
];
const customIcon = new L.Icon({
  iconUrl: customMarkerSvg,
  iconSize: [32, 32], // Adjust the size as needed
  iconAnchor: [16, 32], // Adjust the anchor point as needed
  popupAnchor: [0, -32], // Adjust the popup anchor point as needed
});

const customUserPositionIcon = new L.Icon({
  iconUrl: customUserPositionSvg,
  iconSize: [32, 32], // Adjust the size as needed
  iconAnchor: [16, 32], // Adjust the anchor point as needed
  popupAnchor: [0, -32], // Adjust the popup anchor point as needed
});

const Map = () => {
  const [userLocation, setUserLocation] = useState(null);

  useEffect(() => {
    // Get user's location using geolocation API
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          const { latitude, longitude } = position.coords;
          setUserLocation([latitude, longitude]);
        },
        (error) => {
          console.error("Error getting user location:", error);
        }
      );
    } else {
      console.error("Geolocation is not supported by your browser");
    }
  }, []);

  useEffect(() => {
    // If userLocation is available, set the view to the user's location
    if (userLocation) {
      mapRef.current.setView(userLocation, 12);
    }
  }, [userLocation]);

  const position = userLocation || [50.62925, 3.057256];
  const mapRef = React.useRef();
  return (
    <MapContainer
      center={position}
      zoom={12}
      style={{ height: "400px", width: "100%" }}
      ref={mapRef}
    >
      <TileLayer
        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
      />
      {merchants.map((merchant) => (
        <Marker
          key={merchant.id}
          position={merchant.coordinates}
          icon={customIcon}
        >
          <Popup>{merchant.name}</Popup>
        </Marker>
      ))}
      {userLocation && (
        <Marker position={userLocation} icon={customUserPositionIcon}>
          <Popup>Your Position</Popup>
        </Marker>
      )}
    </MapContainer>
  );
};

export default Map;
