import NewsSlider from "./NewsSlider";
import Map from "./Map";

import offre_img from "../images/offre_1.png";

function FirstScreen() {
  return (
    <div className="grid md:grid-cols-12 grid-cols-3 md:gap-12 gap-4 my-12 mx-auto">
      <div className="md:col-span-4 col-span-3 bg-shopgray order-4 md:order-1 relative flex flex-col rounded">
        <NewsSlider />
      </div>
      <div className="md:col-span-5 col-span-3 order-3 md:order-2 rounded">
        <Map />
      </div>
      <div className="md:col-span-3 col-span-1 bg-shopgray flex justify-center items-center rounded order-1 md:order-3">
        <div className="">
          <img src={offre_img} alt="" />
        </div>
      </div>
    </div>
  );
}

export default FirstScreen;
