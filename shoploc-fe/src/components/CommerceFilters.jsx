import { Checkbox, Slider } from "antd"; // Utilisez les composants de votre choix

const CommerceFilters = ({ onDistanceChange, onOpenNowChange, distance }) => {
    return (
        <div className="flex-1 flex items-center justify-center gap-20 w-full">
            <div>
                <Checkbox onChange={(e) => onOpenNowChange(e.target.checked)}>
                    Ouvert maintenant
                </Checkbox>
            </div>
            <div>
                <span>Distance: {distance} km</span>
                <Slider
                    min={5}
                    max={200}
                    defaultValue={distance}
                    onChange={onDistanceChange}
                />
            </div>
        </div>
    );
};

export default CommerceFilters;
