import { MapContainer, TileLayer, Marker, Popup } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import L from "leaflet";
import customMarkerSvg from "../images/marker.svg";

const position = [50.62925, 3.057256]; // Lille
const merchants = [
    { id: 1, name: "Auchan", coordinates: [50.62925, 3.057256] },
    { id: 2, name: "Le Roy Merlin", coordinates: [50.631008, 3.058092] },
    { id: 3, name: "Planet Bain", coordinates: [50.625018, 3.065499] },
];

export const Map = () => {
    const customIcon = new L.Icon({
        iconUrl: customMarkerSvg,
        iconSize: [32, 32], // Adjust the size as needed
        iconAnchor: [16, 32], // Adjust the anchor point as needed
        popupAnchor: [0, -32], // Adjust the popup anchor point as needed
    });
    return (
        <MapContainer
            center={position}
            zoom={13}
            style={{ height: "400px", width: "100%" }}
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
        </MapContainer>
    );
};
