import { Checkbox, Slider } from "antd"; // Utilisez les composants de votre choix

const CommerceFilters = ({ onDistanceChange, onOpenNowChange, distance }) => {
    return (
        <div className="flex-1 flex items-center justify-center gap-20 w-full">
            <div>
                <Checkbox className="font-bold" onChange={(e) => onOpenNowChange(e.target.checked)}>
                    Ouvert maintenant
                </Checkbox>
            </div>
            <div>
                <span className="text-center">Distance: {distance} km</span>
                <Slider
                    min={5}
                    max={200}
                    defaultValue={distance}
                    onChange={onDistanceChange}
                    style={{
                        width: "300px", // Set your custom width here
                        marginTop: "8px", // Add some spacing if needed
                    }}
                    // trackStyle={{
                    //     backgroundColor: "orange", // Set the track color
                    // }}
                    // handleStyle={{
                    //     borderColor: "orange", // Set the handle border color
                    //     backgroundColor: "orange", // Set the handle color
                    // }}
                />
            </div>
        </div>
    );
};

export default CommerceFilters;
